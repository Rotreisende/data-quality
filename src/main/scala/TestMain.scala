import Rules._

object TestMain extends App {
  val rules = List(DataQuality(sizeRule, "Illegal length value"), DataQuality(onlyCharRule, "Exist not only char"))

  val op = Some("123")
  println(DataQuality.forAll(op, mapRule("patronymic")))

  //println(DataQuality.forAll(Some("hello"), rules))

  //println(functorMaybe.map[Int, String](x => x.toString.concat("ass"), Some(5)))

  //println("2016-08-16".encode[LocalDate])
}
