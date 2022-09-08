package shop.services

import shop.domain.user.{EncryptedPassword, UserId, UserName, UserWithPassword}

// system should store basic info about users
// username and encrypted password

trait Users[F[_]] {

  def find(
      username: UserName
  ): F[Option[UserWithPassword]]

  def create(
      username: UserName,
      password: EncryptedPassword
  ): F[UserId]

}
