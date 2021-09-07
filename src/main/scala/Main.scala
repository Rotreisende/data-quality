import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.annotation.tailrec
import Headers._
import Rules._
import DataType._
import Instrument._

object Main extends App {
  val rows = safelyUsage(CSVReader.open(Config.roots.data))(x => materialize(x.iterator, writeIncorrect))

  val (correct, incorrect) = correctRowRec(rows)
  incorrect.map(toStringFromRow).foreach(writeIncorrect)
  correct.map(toStringFromRow).foreach(writeCorrect)

  def isValidField(row: Row): Boolean = {
    headers.forall(field => switchValidator(row, field))
  }

  def correctRowRec(rows: List[Row]): (List[Row], List[Row]) = {
    @tailrec
    def loop(iterator: Iterator[Row], correctList: List[Row], incorrectList: List[Row]): (List[Row], List[Row]) = {
      iterator.nextOption() match {
        case Some(value) =>
          if (isValidField(value)) {
            loop(iterator, value :: correctList, incorrectList)
          } else {
            loop(iterator, correctList, value :: incorrectList)
          }

        case None => (correctList, incorrectList)
      }
    }
    loop(rows.iterator, List.empty, List.empty)
  }

  def safelyUsage[A <: AutoCloseable, B](resource: A)(usage: A => B): B = {
    try {
      usage(resource)
    } finally {
      resource.close()
    }
  }

  def isValidFieldCount(list: Seq[String]): Boolean = {
    list.size == headers.size
  }

  def writeIncorrect(value: String): Unit = {
    safelyUsage(CSVWriter.open(Config.roots.incorrect, append = true))(_.writeRow(value.split(",")))
  }

  def writeCorrect(value: String): Unit = {
    safelyUsage(CSVWriter.open(Config.roots.correct, append = true))(_.writeRow(value.split(",")))
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
