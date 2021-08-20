import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.annotation.tailrec
import Headers._

object Main extends App {
  val data = safelyUsage(CSVReader.open("src/main/resources/data.csv"))(x => materialize(x.iterator))
  var rows = data.map(createRow)

  println(rows)

  def createRow(fields: Seq[String]) = getHeadersRow().zip(fields).toMap

  def safelyUsage[A <: AutoCloseable, B](resource: A)(usage: A => B): B = {
    try {
      usage(resource)
    } finally {
      resource.close()
    }
  }

  def isValidFieldCount(list: Seq[String], countFields: Int): Boolean = {
    list.size == countFields
  }

  def materialize(iterator: Iterator[Seq[String]]): List[Seq[String]] = {
    @tailrec
    def loop(iterator: Iterator[Seq[String]], accumulator: List[Seq[String]]): List[Seq[String]] = {
      iterator.nextOption() match {
        case Some(value) =>
          if (isValidFieldCount(value, getHeadersRow().size)){
            loop(iterator, value :: accumulator)
          } else {
            safelyUsage(CSVWriter.open("src/main/resources/IncorrectSubjects.csv", append = true))(x => x.writeRow(value))
            loop(iterator, accumulator)
          }

        case None => accumulator
      }
    }
    loop(iterator, List.empty).reverse
  }
}
