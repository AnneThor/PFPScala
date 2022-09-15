package shop.domain

import cats.Show


sealed trait Retriable

object Retriable {
  case class Orders() extends Retriable
  case class Payments() extends Retriable

  implicit val showRetriable: Show[Retriable] = Show.show {
    case _: Payments => "Payments"
    case _: Orders => "Orders"
  }

}
