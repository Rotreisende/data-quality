import com.github.tototoshi.csv.CSVReader

import java.io.File
import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.Source

object Main extends App {
  type Row = mutable.Map[String, String]

  var dataSets = materialize(read("src/main/resources/data.csv"))

  val rows = dataSets.map {
    case _@Seq(surname, name, part) =>
      val row = mutable.Map[String, String]()
      row("surname") = surname
      row("name") = name
      row("part") = part
      row

    case _ => None
  }

  println(rows)

  def read(root: String) = {
    val reader = CSVReader.open(new File(root))
    val iterator = reader.iterator
    iterator
  }

  def materialize(iterator: Iterator[Seq[String]]): List[Seq[String]] = {
    @tailrec
    def loop(iterator: Iterator[Seq[String]], accumulator: List[Seq[String]]): List[Seq[String]] = {
      if (iterator.hasNext) {
        loop(iterator, iterator.next() :: accumulator)
      } else {
        accumulator
      }
    }
    loop(iterator, List.empty).reverse
  }
}
