package shop.domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.estatico.newtype.macros.newtype
import shop.domain.brand.{Brand, BrandId}
import shop.domain.category.{Category, CategoryId}
import squants.market.Money

import java.util.UUID

object item {

  @newtype case class ItemId(value: UUID)
  @newtype case class ItemName(value: String)
  @newtype case class ItemDescription(value: String)

  case class Item(
      id: ItemId,
      name: ItemName,
      description: ItemDescription,
      price: Money,
      brand: Brand,
      category: Category
  )

  implicit val itemEncoder: Encoder[Item] = deriveEncoder[Item]
  implicit val itemDecoder: Decoder[Item] = deriveDecoder[Item]

  case class CreateItem(
      name: ItemName,
      description: ItemDescription,
      price: Money,
      brandId: BrandId,
      categoryId: CategoryId
  )

  case class UpdateItem(
      id: ItemId,
      price: Money
  )

}
