package Validators

import DataType._
import FieldEncoder._

trait DateValidator[F[_]] {
  def validate(value: F[String]): Boolean
}

object DateValidator {
  def apply[F[_] : DateValidator]: DateValidator[F] = implicitly[DateValidator[F]]

  implicit case object IdValidator extends DateValidator[Id] {
    override def validate(value: Id[String]): Boolean = {
      DateEncoder.encode(value).isRight
    }
  }

  implicit case object OptionValidator extends DateValidator[Option] {
    override def validate(value: Option[String]): Boolean = {
      value match {
        case Some("") => true
        case Some(item) => IdValidator.validate(item)
        case _ => false
      }
    }
  }
}
