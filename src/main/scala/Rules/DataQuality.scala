package Rules

case class DataQuality[F[_]](rule: F[String] => Boolean, err: String) {
  def validate(value: F[String]): Either[String, F[String]] = {
    Either.cond(rule(value), value, err)
  }
}

object DataQuality {
  def forAll[F[_]](value: F[String], list: List[DataQuality[F]]): Boolean = {
    val (errors, _) = list.map(_.validate(value)).partition(_.isLeft)
    errors.isEmpty
  }
}
