package Rules

import DataType.Id
import Checker._

object IdNumberRules {
  val identity: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(isValidSize(Instrument.idNumberLength, equals = true), "primary id number incorrect size"),
    DataQuality(isValidInt, "primary id number incorrect format")
  )

  val option = List(
    DataQuality(ruleOp(isValidSize(Instrument.idNumberLength, equals = true)), "primary id number incorrect size"),
    DataQuality(ruleOp(isValidInt), "primary id number incorrect format")
  )
}
