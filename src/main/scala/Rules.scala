import DataType._
import Validators.NameValidator.OptionValidator
import Validators._

object Rules {
  def functor[F[_] : Functor](value: F[String])(func: String => String): F[String] = {
    Functor[F].map(value)(func)
  }

  val nameRules: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(SizeValidator[Id].validate(Instrument.nameLength, equals = false), ""),
    DataQuality(NameValidator[Id].validate, "")
  )

  val nameOpsRules = List(
    DataQuality(SizeValidator[Option].validate(Instrument.nameLength, equals = false), ""),
    DataQuality(NameValidator[Option].validate, "")
  )

  val dateRules: List[DataQuality[Id]] = List[DataQuality[Id]](DataQuality(DateValidator[Id].validate, ""))

  val idTypeRules: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(SizeValidator[Id].validate(Instrument.idTypeLength, equals = true), ""),
    DataQuality(IntValidator[Id].validate, "")
  )

  val idNumberRules: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(SizeValidator[Id].validate(Instrument.idNumberLength, equals = true), "primary id number incorrect size"),
    DataQuality(IntValidator[Id].validate, "primary id number incorrect format")
  )

  val idAuthorityRules: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(SizeValidator[Id].validate(Instrument.idAuthorityLength, equals = false), "primary id authority incorrect size")
  )

  val idTypeOpRules = List(
    DataQuality(SizeValidator[Option].validate(Instrument.idTypeLength, equals = true), ""),
    DataQuality(IntValidator[Option].validate, "")
  )

  val idNumberOpRules = List(
    DataQuality(SizeValidator[Option].validate(Instrument.idNumberLength, equals = true), "primary id number incorrect size"),
    DataQuality(IntValidator[Option].validate, "primary id number incorrect format")
  )

  val dateOpRules = List(DataQuality(DateValidator[Option].validate, ""))

  val idAuthorityOpRules = List(
    DataQuality(SizeValidator[Option].validate(Instrument.idAuthorityLength, equals = false), "primary id authority incorrect size")
  )
}
