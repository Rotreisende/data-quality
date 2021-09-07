trait Functor[F[_]] {
  def map[A, B](v: F[A])(f: A => B): F[B]
}

import DataType._

object Functor {
  implicit case object IdFunctor extends Functor[Id] {
    override def map[A, B](v: Id[A])(f: A => B): Id[B] = {
      f(v)
    }
  }

  implicit case object OptionFunctor extends Functor[Option] {
    override def map[A, B](v: Option[A])(f: A => B): Option[B] = {
      v match {
        case Some(item) => Some(f(item))
        case None => None
      }
    }
  }

  def apply[F[_] : Functor]: Functor[F] = implicitly[Functor[F]]
}
