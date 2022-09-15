package shop.programs

import cats.MonadThrow
import cats.data.NonEmptyList
import shop.domain.checkout.Card
import shop.domain.errors.{EmptyCartError, OrderError, PaymentError}
import shop.domain.order.OrderId
import shop.domain.payment.{Payment, PaymentId}
import shop.domain.user.UserId
import shop.services.{Orders => ShopOrders, PaymentClient, ShoppingCart}
import cats.syntax.all._
import org.typelevel.log4cats.Logger
import retry.RetryPolicies.{exponentialBackoff, limitRetries}
import shop.domain.Retriable.{Orders, Payments}
import shop.domain.cart.{CartItem, CartTotal}
import squants.market.Money

import scala.concurrent.duration.DurationInt

final case class Checkout[F[_]: Background: Logger: MonadThrow: Retry](
    payments: PaymentClient[F],
    cart: ShoppingCart[F],
    orders: ShopOrders[F]
    // policy: RetryPolicy[F]
) {

  val retryPolicy =
    limitRetries[F](3) |+| exponentialBackoff[F](10.milliseconds)

  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(
      NonEmptyList.fromList(xs),
      EmptyCartError
    )

  def processPayment(in: Payment): F[PaymentId] =
    Retry[F]
      .retry(retryPolicy, Payments())(payments.process(in))
      .adaptError {
        // e.getMessage is inside an option bc it may be null
        // transforms the error from the payment client into our custom PaymentError
        case e => PaymentError(Option(e.getMessage).getOrElse("Unknown Error"))
      }

  def createOrder(
      userId: UserId,
      paymentId: PaymentId,
      items: NonEmptyList[CartItem],
      total: Money
  ): F[OrderId] = {
    val action = Retry[F]
      .retry(retryPolicy, Orders())(orders.create(userId, paymentId, items, total))
      .adaptError { case e: Throwable =>
        OrderError(Option(e.getMessage).getOrElse("Unknown Error"))
      }

    def bgAction(fa: F[OrderId]): F[OrderId] =
      fa.onError { case _ =>
        Logger[F].error(
          s"Failed to create order for: ${paymentId.show}"
        ) *> Background[F].schedule(bgAction(fa), 1.hour)
      }

    bgAction(action)
  }

  def process(userId: UserId, card: Card): F[OrderId] =
    cart.get(userId).flatMap { case CartTotal(items, total) =>
      for {
        cartItems <- ensureNonEmpty(items)
        paymentId <- processPayment(Payment(userId, total, card))
        orderId <- createOrder(userId, paymentId, cartItems, total)
        _ <- cart.delete(userId).attempt.void
      } yield orderId
    }

}
