package Validators

import DataType._

trait SizeValidator[F[_]] {
  def validate(max: Int, equals: Boolean)(value: F[String]): Boolean
}

object SizeValidator {
  def apply[F[_] : SizeValidator]: SizeValidator[F] = implicitly[SizeValidator[F]]

  implicit case object IdValidator extends SizeValidator[Id] {
    override def validate(max: Int, equals: Boolean)(value: Id[String]): Boolean = {
      if (equals) {
        value.length == max
      } else {
        value.length < max
      }
    }
  }

  implicit case object OptionValidator extends SizeValidator[Option] {
    override def validate(max: Int, equals: Boolean)(value: Option[String]): Boolean = {
      value match {
        case Some("") => true
        case Some(item) => IdValidator.validate(max, equals)(item)
        case _ => false
      }
    }
  }
}