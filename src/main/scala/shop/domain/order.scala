package shop.domain

import cats.data.NonEmptyList
import io.estatico.newtype.macros.newtype
import shop.domain.cart.CartItem
import shop.domain.payment.PaymentId
import squants.market.Money

import java.util.UUID

object order {

  @newtype case class OrderId(value: UUID)

  case class Order(
      id: OrderId,
      paymentId: PaymentId,
      items: NonEmptyList[CartItem],
      total: Money
  )

}
