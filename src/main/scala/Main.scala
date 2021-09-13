import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.annotation.tailrec
import Headers._
import DataType._
import Instrument._

object Main extends App {
  val rows = safelyUsage(CSVReader.open(Config.roots.data))(x => materialize(x.iterator, write(Config.roots.incorrect)))

  val (correct, incorrect) = correctRowRec(rows)
  incorrect.map(toStringFromRow).foreach(write(Config.roots.incorrect))
  correct.map(toStringFromRow).foreach(write(Config.roots.correct))

  def isValidRow(row: Row): Boolean = {
    headers.forall(field => switchValidator(row, field))
  }

  def correctRowRec(rows: List[Row]): (List[Row], List[Row]) = {
    @tailrec
    def loop(iterator: Iterator[Row], correctList: List[Row], incorrectList: List[Row]): (List[Row], List[Row]) = {
      iterator.nextOption() match {
        case Some(value) =>
          if (isValidRow(value)) {
            loop(iterator, value :: correctList, incorrectList)
          } else {
            loop(iterator, correctList, value :: incorrectList)
          }

        case None => (correctList, incorrectList)
      }
    }
    loop(rows.iterator, List.empty, List.empty)
  }

  def isValidFieldCount(list: Seq[String]): Boolean = {
    list.size == headers.size
  }

  def write(root: String)(value: String): Unit = {
    safelyUsage(CSVWriter.open(root, append = true))(_.writeRow(value.split(",")))
  }

  def materialize(iterator: Iterator[Seq[String]], f: String => Unit): List[Row] = {
    @tailrec
    def loop(iterator: Iterator[Seq[String]], accumulator: List[Row]): List[Row] = {
      iterator.nextOption() match {
        case Some(value) =>
          if (isValidFieldCount(value)){
            loop(iterator, Instrument.createRow(value) :: accumulator)
          } else {
            f(value.mkString(","))
            loop(iterator, accumulator)
          }

        case None => accumulator
      }
    }
    loop(iterator, List.empty)
  }
}
