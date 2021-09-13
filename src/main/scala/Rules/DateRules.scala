package Rules

import DataType.Id
import Checker._

object DateRules {
  val identity: List[DataQuality[Id]] = List[DataQuality[Id]](DataQuality(isValidDate, ""))
  val option = List(DataQuality(ruleOp(isValidDate), ""))
}
