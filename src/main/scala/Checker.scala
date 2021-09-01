import scala.util.matching.Regex

object Checker {
  val charPattern: Regex = "^[a-zA-Z]+$".r
  val sizeName = 40

  def size(line: String): Boolean = {
    line.length < sizeName
  }

  def onlyChars(line: String): Boolean = {
    charPattern.findFirstMatchIn(line) match {
      case Some(_) => true
      case None => false
    }
  }
}
