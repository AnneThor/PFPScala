package shop.domain

import cats.Show
import io.estatico.newtype.macros.newtype
import shop.domain.checkout.Card
import shop.domain.user.UserId
import squants.market.Money

import java.util.UUID

object payment {

  @newtype case class PaymentId(value: UUID)

  implicit val showPaymentId: Show[PaymentId] = Show.show(pid => pid.value.toString)

  case class Payment(
      id: UserId,
      total: Money,
      card: Card
  )

}
