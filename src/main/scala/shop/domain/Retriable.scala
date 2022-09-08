package shop.domain

import cats.Show


sealed trait Retriable

object Retriable {
  case class Orders(str: "Orders") extends Retriable
  case class Payments(str: "Payments") extends Retriable

  implicit val showRetriable: Show[Retriable] = Show.show(r => {
    r match {
      case orders: Orders => orders.str
      case payments: Payments => payments.str
    }
  })
}
