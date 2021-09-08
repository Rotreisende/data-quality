import java.time.LocalDate
import scala.util.Try

trait FieldEncoder[T] {
  def encode(value: String): Either[String, T]
}

object FieldEncoder {
  def apply[T : FieldEncoder]: FieldEncoder[T] = implicitly[FieldEncoder[T]]

  implicit case object IntEncoder extends FieldEncoder[Int] {
    override def encode(value: String): Either[String, Int] = {
      Try {
        value.toInt
      }.toEither.left.map(_.getMessage)
    }
  }

  implicit case object DateEncoder extends FieldEncoder[LocalDate] {
    override def encode(value: String): Either[String, LocalDate] = {
      Try {
        LocalDate.parse(value)
      }.toEither.left.map(_.getMessage)
    }
  }

  implicit class FieldEncoderOps(value: String) {
    def encode[T : FieldEncoder]: Either[String, T] = FieldEncoder[T].encode(value)
  }
}
