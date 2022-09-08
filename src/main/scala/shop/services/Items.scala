package shop.services

import shop.domain.brand.BrandName
import shop.domain.item.{CreateItem, Item, ItemId, UpdateItem}

// Servicing
// GET endpoints
// Get a list of all the items
// Get items by brand filter
// POST endpoint (admin)
// create an item
// PUT endpoint (admin)
// update an item

trait Items[F[_]] {

  def findAll: F[List[Item]]

  def findBy(brand: BrandName): F[List[Item]]

  def findById(itemId: ItemId): F[Option[Item]]

  def create(item: CreateItem): F[ItemId]

  def update(item: UpdateItem): F[Unit]

}
