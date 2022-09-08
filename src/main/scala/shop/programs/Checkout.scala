package shop.programs

import cats.MonadThrow
import cats.data.NonEmptyList
import shop.domain.checkout.Card
import shop.domain.errors.EmptyCartError
import shop.domain.order.OrderId
import shop.domain.payment.Payment
import shop.domain.user.UserId
import shop.services.{Orders, PaymentClient, ShoppingCart}
import cats.syntax.all._

final case class Checkout[F[_]: MonadThrow](
  payments: PaymentClient[F],
  cart: ShoppingCart[F],
  orders: Orders[F]
  ) {

  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(
      NonEmptyList.fromList(xs),
      EmptyCartError
    )

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