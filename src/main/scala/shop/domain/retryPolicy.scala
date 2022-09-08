//package shop.domain
//
//import cats.implicits.catsSyntaxSemigroup
//import retry.RetryPolicies.{exponentialBackoff, limitRetries}
//
//import scala.concurrent.duration.DurationInt
//
//object retryPolicy {
//
//  val retryPolicy =
//    limitRetries[F](3) |+| exponentialBackoff[F](10.milliseconds)
//
//}
