package shop.http.routes

import cats.implicits.toBifunctorOps
import eu.timepit.refined.api.Validate
import eu.timepit.refined.refineV
import org.http4s.{ParseFailure, QueryParamDecoder}

object Helpers {

  implicit def refinedParamDecoder[T: QueryParamDecoder, P](implicit
      ev: Validate[T, P]
  ): Unit =
    QueryParamDecoder[T].emap(
      refineV[P](_).leftMap(m => ParseFailure(m, m))
    )

}
