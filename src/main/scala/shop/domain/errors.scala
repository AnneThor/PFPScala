package shop.domain

object errors {

  trait ShoppingCartError extends Throwable
  case object EmptyCartError extends ShoppingCartError
  case class PaymentError(value: String) extends ShoppingCartError
  case class OrderError(value: String) extends ShoppingCartError

}
