package shop.domain

object errors {

  trait ShoppingCartError extends Throwable
  case object EmptyCartError extends ShoppingCartError

}
