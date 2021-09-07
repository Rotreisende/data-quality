package Validators

import DataType._

trait NameValidator[F[_]] {
  def validate(value: F[String]): Boolean
}

object NameValidator {
  def apply[F[_] : NameValidator]: NameValidator[F] = implicitly[NameValidator[F]]

  implicit case object IdValidator extends NameValidator[Id] {
    override def validate(value: Id[String]): Boolean = {
      Checker.onlyChars(value)
    }
  }

  implicit case object OptionValidator extends NameValidator[Option] {
    override def validate(value: Option[String]): Boolean = {
      value match {
        case Some("") => true
        case Some(item) => IdValidator.validate(item)
        case _ => false
      }
    }
  }
}