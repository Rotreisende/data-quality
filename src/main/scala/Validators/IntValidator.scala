package Validators

import DataType._
import FieldEncoder._

trait IntValidator[F[_]] {
  def validate(value: F[String]): Boolean
}

object IntValidator {
  def apply[F[_] : IntValidator]: IntValidator[F] = implicitly[IntValidator[F]]

  implicit case object IdValidator extends IntValidator[Id] {
    override def validate(value: Id[String]): Boolean = {
      IntEncoder.encode(value).isRight
    }
  }

  implicit case object OptionValidator extends IntValidator[Option] {
    override def validate(value: Option[String]): Boolean = {
      value match {
        case Some("") => true
        case Some(item) => IdValidator.validate(item)
        case _ => false
      }
    }
  }
}
