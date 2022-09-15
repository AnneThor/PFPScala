package shop.http.routes

import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import shop.services.Brands

final case class BrandRoutes[F[_]: Monad](
    brands: Brands[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/brands"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    // Ok applies a code 200
    // this is equivalent to brands.findAll.flatMap(Ok(_))
    // simple format is preferred for straightforward routes
    Ok(brands.findAll)
  // if we were to update this to handle errors we could say
  // brands
  //  .findAll
  //  .flatMap(Ok(_))
  //  .handleErrorWith {
  //    case UesrNotAuthenticated(_) => Forbidden()
  //  }
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )

}
