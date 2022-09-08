package shop.services

import shop.domain.brand.{Brand, BrandId, BrandName}

// brand domains will have two endpoints
// GET to retrieve list of brands
// POST to create brands (available to admin only)
trait Brands[F[_]] {

  def findAll: F[List[Brand]]

  def create(name: BrandName): F[BrandId]

}
