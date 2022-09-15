package shop.http.routes

import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import shop.services.Items
import cats.syntax.all._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.impl.OptionalQueryParamDecoderMatcher
import org.http4s.server.Router
import shop.domain.brand.BrandParam

final case class ItemRoutes[F[_]: Monad](
    items: Items[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/items"

  object BrandQueryParam extends OptionalQueryParamDecoderMatcher[BrandParam]("brand")

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {

    case GET -> Root :? BrandQueryParam(brand) =>
      items.findAll.flatMap(Ok(_))

  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )

}
