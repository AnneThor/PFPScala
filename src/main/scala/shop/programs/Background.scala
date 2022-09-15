package shop.programs

import cats.effect.Temporal
import cats.effect.std.Supervisor
import cats.syntax.all._

import scala.concurrent.duration.FiniteDuration

// benefits to this over using Temporal directly are
// restrict what final user can do
// avoid having more constraints in the program
// achieve better testability
trait Background[F[_]] {

  def schedule[A](
                 fa: F[A],
                 duration: FiniteDuration
                 ): F[Unit]

}

object Background {

  def apply[F[_]: Background]: Background[F] = implicitly

  implicit def bgInstance[F[_]](
                               implicit S: Supervisor[F],
                               T: Temporal[F]
                               ): Background[F] =
    new Background[F] {
      def schedule[A](
                     fa: F[A],
                     duration: FiniteDuration
                     ): F[Unit] =
        S.supervise(T.sleep(duration) *> fa).void
    }

}
