import org.scalatest.flatspec._
import org.scalatest.matchers._

class ParserSpec extends AnyFlatSpec with should.Matchers {
  "Method createRow" should "create row by headers" in {
    val seq = Vector("1", "2", "3")
    Instrument.createRow(seq) should be (Map("surname" -> "1", "name" -> "2", "patronymic" -> "3"))
  }
}
