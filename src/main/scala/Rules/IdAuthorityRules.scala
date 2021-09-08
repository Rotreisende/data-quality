package Rules

import DataType.Id
import Checker._

object IdAuthorityRules {
  val identity: List[DataQuality[Id]] = List[DataQuality[Id]](
    DataQuality(isValidSize(Instrument.idAuthorityLength, equals = false), "primary id authority incorrect size")
  )

  val option: List[DataQuality[Option]] = List[DataQuality[Option]](
    DataQuality(ruleOp(isValidSize(Instrument.idAuthorityLength, equals = false)), "primary id authority incorrect size")
  )
}
