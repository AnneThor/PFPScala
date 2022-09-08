package shop.domain

import derevo.circe.magnolia.encoder
import derevo.derive
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

}
