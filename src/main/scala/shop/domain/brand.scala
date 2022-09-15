package shop.domain

import derevo.cats.show
import derevo.derive
import eu.timepit.refined.auto.autoUnwrap
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.refined._
import io.estatico.newtype.macros.newtype

import java.util.UUID

object brand {

  @newtype case class BrandId(value: UUID)

  @newtype case class BrandName(value: String)

  case class Brand(id: BrandId, name: BrandName)

  object Brand {

    implicit val brandEncoder: Encoder[Brand] = deriveEncoder[Brand]
    implicit val brandDecoder: Decoder[Brand] = deriveDecoder[Brand]

  }

  @derive(show)
  @newtype
  case class BrandParam(value: NonEmptyString) {
    def toDomain: BrandName = BrandName(value.toLowerCase.capitalize)
  }

  object BrandParam {

    implicit val brandParamEncoder: Encoder[BrandParam] =
      Encoder.forProduct1("name")(bp => bp.value)
    implicit val brandParamDecoder: Decoder[BrandParam] =
      Decoder.forProduct1("name")(BrandParam.apply)

  }

}
