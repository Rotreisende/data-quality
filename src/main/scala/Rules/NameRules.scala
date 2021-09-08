package Rules

import DataType.Id
import Checker._

object NameRules {
  val identity: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(isOnlyChars, ""),
    DataQuality(isValidSize(Instrument.nameLength, equals = false), "")
  )

  val option = List(
    DataQuality(ruleOp(isOnlyChars), ""),
    DataQuality(ruleOp(isValidSize(Instrument.nameLength, equals = false)), "")
  )
}
