package shop.services

import shop.domain.auth.JwtToken
import shop.domain.user.{Password, User, UserName}

// We will have auth endpoints
// We will use JSON web tokens as the auth method
// sketching out basic functionality for now

trait Auth[F[_]] {

  def findUser(token: JwtToken): F[Option[User]]

  def newUser(
      username: UserName,
      password: Password
  ): F[JwtToken]

  def login(
      userName: UserName,
      password: Password
  ): F[JwtToken]

  def logout(
      token: JwtToken,
      userName: UserName
  ): F[Unit]

}
