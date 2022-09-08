package shop.domain

import io.circe.Encoder
import monocle.Iso

// sealed trait can be used like enum
// must be entirely defined within the same file
sealed trait Status

object Status {

  case object Okay extends Status
  case object Unreachable extends Status

  val _Bool: Iso[Status, Boolean] =
    Iso[Status, Boolean] {
      case Okay        => true
      case Unreachable => false
    }(if (_) Okay else Unreachable)

  implicit val jsonencoder: Encoder[Status] =
    Encoder.forProduct1("status")(_.toString)

}
