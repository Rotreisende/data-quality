trait Functor[F[_]] {
  def map[A, B](f: A => B, v: F[A]): F[B]
  def lift[A, B](f: A => B): F[A] => F[B] = {
    (v: F[A]) => map(f, v)
  }
}
