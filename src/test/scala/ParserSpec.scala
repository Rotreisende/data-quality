import InstrumentTest.emptyMethod
import DataType.Row
import Instrument.safelyUsage
import Main.{correctRowRec, isValidRow, materialize, write}
import com.github.tototoshi.csv.CSVReader
import org.scalatest.flatspec._
import org.scalatest.matchers._
import Main._

class ParserSpec extends AnyFlatSpec with should.Matchers {
  "Method createRow" should "create row by headers" in {
    val seq = Vector("Ivanov", "Ivan", "Ivanovich")
    Instrument.createRow(seq) should be (Map("surname" -> "Ivanov", "name" -> "Ivan", "patronymic" -> "Ivanovich"))
  }

  "Method correctRowRec" should "return two lists correct and incorrect rows" in {
    val rows: List[Row] = safelyUsage(CSVReader.open(Config.roots.data))(x => materialize(x.iterator, emptyMethod))
    val (correct, incorrect) = correctRowRec(rows)
    correct.forall(isValidRow) should be (true)
    incorrect.forall(isValidRow) should be (false)
  }
}
