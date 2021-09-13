import org.scalatest.flatspec._
import org.scalatest.matchers._
import Checker._

class RulesSpec extends AnyFlatSpec with should.Matchers {
  "Method isValidSize" should "return false on value size more max value" in {
    isValidSize(3, equals = false)("qert") should be (false)
  }

  "Method isValidSize" should "return true on value size less max value" in {
    isValidSize(3, equals = false)("qw") should be (true)
  }

  "Method isValidSize" should "return true on value size equals max value" in {
    isValidSize(3, equals = true)("121") should be (true)
  }

  "Method isValidSize" should "return false on value size not equals max value" in {
    isValidSize(3, equals = true)("1213") should be (false)
  }

  "Method isOnlyChars" should "return false if string not exists only chars [a-z][A-Z]" in {
    isOnlyChars("in_correct") should be (false)
  }

  "Method isOnlyChars" should "return true if string exists only chars [a-z][A-Z]" in {
    isOnlyChars("correct") should be (true)
  }

  "Method isValidDate" should "return false if string not converted to date" in {
    isValidDate("123-11231-23") should be (false)
  }

  "Method isValidDate" should "return true if string converted to date" in {
    isValidDate("2001-01-01") should be (true)
  }

  "Method isValidInt" should "return true if string converted to Int" in {
    isValidInt("123") should be (true)
  }

  "Method isValidInt" should "return false if string not converted to Int" in {
    isValidInt("not int") should be (false)
  }
}
