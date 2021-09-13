package Rules

import DataType.Id
import Checker._

object IdTypeRules {
  val identity: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(isValidSize(Instrument.idTypeLength, equals = true), ""),
    DataQuality(isValidInt, "")
  )

  val option = List(
    DataQuality(ruleOp(isValidSize(Instrument.idTypeLength, equals = true)), ""),
    DataQuality(ruleOp(isValidInt), "")
  )
}
