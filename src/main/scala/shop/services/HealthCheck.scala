package shop.services

import shop.domain.health.AppStatus

// GET endpoint that can return the app status

trait HealthCheck[F[_]] {

  def status: F[AppStatus]

}
