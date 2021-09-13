import InstrumentTest.emptyMethod
import DataType.Id
import Instrument.safelyUsage
import Main.{materialize, write}
import Rules.{DataQuality, NameRules}
import com.github.tototoshi.csv.CSVReader
import org.scalatest.flatspec.{AnyFlatSpec, _}
import org.scalatest.matchers._

class DataQualitySpec extends AnyFlatSpec with should.Matchers {
  "Method DataQuality.forAll" should "check value on list rules" in {
    DataQuality.forAll("name": Id[String], NameRules.identity) should be (true)
  }
}
