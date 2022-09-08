package shop.services

import cats.data.NonEmptyList
import shop.domain.cart.CartItem
import shop.domain.order.{Order, OrderId}
import shop.domain.payment.PaymentId
import shop.domain.user.UserId
import squants.market.Money

// once we process payment, we need to persist the order
// also want ability to query past orders
trait Orders[F[_]] {

  def get(
      userId: UserId,
      orderId: OrderId
  ): F[Option[Order]]

  def findBy(userId: UserId): F[List[Order]]

  def create(
      userId: UserId,
      paymentId: PaymentId,
      items: NonEmptyList[CartItem],
      total: Money
  ): F[OrderId]

}
