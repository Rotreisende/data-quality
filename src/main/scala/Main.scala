import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.annotation.tailrec
import Headers._

object Main extends App {
  val rows = safelyUsage(CSVReader.open("src/main/resources/data.csv"))(x => materialize(x.iterator, write))

  println(rows)



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

  def write(value: Seq[String]): Unit = {
    safelyUsage(CSVWriter.open("src/main/resources/IncorrectSubjects.csv", append = true))(x => x.writeRow(value))
  }

  def materialize(iterator: Iterator[Seq[String]], f: Seq[String] => Unit): List[Map[String, String]] = {
    @tailrec
    def loop(iterator: Iterator[Seq[String]], accumulator: List[Map[String, String]]): List[Map[String, String]] = {
      iterator.nextOption() match {
        case Some(value) =>
          if (isValidFieldCount(value)){
            loop(iterator, Instrument.createRow(value) :: accumulator)
          } else {
            f(value)
            loop(iterator, accumulator)
          }

        case None => accumulator
      }
    }
    loop(iterator, List.empty)
  }
}
