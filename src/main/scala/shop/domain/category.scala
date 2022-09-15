package shop.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.estatico.newtype.macros.newtype

import java.util.UUID

object category {

  @newtype case class CategoryId(value: UUID)
  @newtype case class CategoryName(value: String)

  case class Category(
      id: CategoryId,
      name: CategoryName
  )

  implicit val encodeCategory: Encoder[Category] = deriveEncoder[Category]
  implicit val decodeCategory: Decoder[Category] = deriveDecoder[Category]

}
