package shop.services

import shop.domain.payment.{Payment, PaymentId}

trait PaymentClient[F[_]] {

  def process(payment: Payment): F[PaymentId]

}
