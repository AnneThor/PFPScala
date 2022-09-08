package shop.programs

import cats.MonadThrow
import cats.data.NonEmptyList
import shop.domain.checkout.Card
import shop.domain.errors.EmptyCartError
import shop.domain.order.OrderId
import shop.domain.payment.{Payment, PaymentId}
import shop.domain.user.UserId
import shop.services.{Orders, PaymentClient, ShoppingCart}
import cats.syntax.all._
import retry.RetryPolicies.{exponentialBackoff, limitRetries}
import shop.domain.Retriable
import shop.domain.Retriable.Payments

import scala.concurrent.duration.DurationInt

final case class Checkout[F[_]: MonadThrow: Retry](
  payments: PaymentClient[F],
  cart: ShoppingCart[F],
  orders: Orders[F]
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
      .retry(retryPolicy, Payments)(payments.process(in))
      .adapterror {
            // e.getMessage is inside an option bc it may be null
        case e => PaymentError(Option(e.getMessage)).getOrElse("Unknown Error")
      }

  def process(userId: UserId, card: Card): F[OrderId] = {
    for {
      cartTotal <- cart.get(userId)
      cartItems <- ensureNonEmpty(cartTotal.items)
      pid <- payments.process(Payment(userId, cartTotal.total, card))
      oid <- orders.create(userId, pid, cartItems, cartTotal.total)
      _ <- cart.delete(userId).attempt.void
    } yield oid
  }

}