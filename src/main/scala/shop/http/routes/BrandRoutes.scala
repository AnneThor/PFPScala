package shop.http.routes

import cats.Monad
import shop.services.Brands

final case class BrandRoutes[F[_]: Monad](
    brand: Brands[F]
)
