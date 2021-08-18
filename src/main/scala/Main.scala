import scala.collection.mutable
import scala.io.Source

object Main extends App {
  type Row = mutable.Map[String, Either[Option[String], String]]

  val source = Source.fromFile("src/main/resources/data.csv")
  var dataSets = materialize[String](source.getLines())

  val rows = dataSets.map(item => {
    item.split(",") match {
      case _@Array(surname, name, part) =>
        val row = mutable.Map[String, Either[Option[String], String]]()
        row("surname") = Right(surname)
        row("name") = Right(name)
        row("part") = Left(Some(part))
        row

      case _ => None
    }
  })
  println(rows)

  def materialize[T](it: Iterator[T]): List[T] = {
    it.foldRight(List.empty[T]) {
      (accumulator, elem) => accumulator :: elem
    }
  }
}
