package shop.domain

import derevo.circe.magnolia.encoder
import derevo.derive
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.estatico.newtype.macros.newtype

object health {

  @derive(encoder)
  @newtype case class RedisStatus(value: String)

  @derive(encoder)
  @newtype case class PostgresStatus(value: String)

  case class AppStatus(
      postgres: PostgresStatus,
      redis: RedisStatus
  )
  // the status are isomorphic to Boolean, so we can use Iso here to simplify

  object AppStatus {
    implicit val appStatusDecoder: Decoder[AppStatus] = deriveDecoder[AppStatus]
    implicit val appStatusEncoder: Encoder[AppStatus] = deriveEncoder[AppStatus]
  }

}
