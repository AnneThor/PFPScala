package shop.services

import shop.domain.cart.{Cart, CartTotal, Quantity}
import shop.domain.item.ItemId
import shop.domain.user.UserId

// GET endpoint
// retrieve the shopping cart of the current user
// POST endpoint
// add items to the cart
// PUT endpoint
// edit quantity of any item
// DELETE endpoint
// remove an item from the cart

trait ShoppingCart[F[_]] {

  def add(
      userId: UserId,
      itemId: ItemId,
      quantity: Quantity
  ): F[Unit]

  def get(userId: UserId): F[CartTotal]

  def delete(userId: UserId): F[Unit]

  def removeItem(userId: UserId, itemId: ItemId): F[Unit]

  def update(userId: UserId, cart: Cart): F[Unit]

}
