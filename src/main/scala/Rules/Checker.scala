import Instrument._
import DataType._
import FieldEncoder._

package object Checker {
  def ruleOp(f: Id[String] => Boolean)(op: Option[String]): Boolean = {
    op.exists(f)
  }

  def isValidSize(max: Int, equals: Boolean)(line: Id[String]): Boolean = {
    if (equals) line.length == max else line.length < max
  }

  def isOnlyChars(line: Id[String]): Boolean = {
    charPattern.findFirstMatchIn(line) match {
      case Some(_) => true
      case None => false
    }
  }

  def isValidDate(line: Id[String]): Boolean = {
    DateEncoder.encode(line).isRight
  }

  def isValidInt(line: Id[String]): Boolean = {
    IntEncoder.encode(line).isRight
  }
}
