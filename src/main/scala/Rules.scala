object Rules {
  lazy val mapRule = Map(
    "surname" -> nameRules,
    "name" -> nameRules,
    "patronymic" -> optionNameRules
  )

  val nameRules = List(
    DataQuality(sizeRule, "Illegal length value"),
    DataQuality(onlyCharRule, "Exist not only char"))

  val optionNameRules = List(DataQuality(optionNameRule, "Illegal option name"))

  private val functor = new Functor[Option] {
    override def map[A, B](f: A => B, v: Option[A]): Option[B] = v match {
      case Some(x) => Some(f(x))
      case None => None
    }
  }

  def optionNameRule(value: Option[String]): Boolean = {
    value match {
      case Some("") => true
      case Some(_) => DataQuality.forAll(value, nameRules)
      case None => false
    }
  }

  def sizeRule(value: Option[String]): Boolean = {
    value match {
      case Some(value) => value.length < Instrument.validLength
      case None => false
    }
  }

  def onlyCharRule(value: Option[String]): Boolean = {
    value match {
      case Some(value) => Checker.onlyChars(value)
      case None => false
    }
  }
}
