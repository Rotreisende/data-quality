import com.typesafe.config.ConfigFactory

object Config {
  lazy val roots: Map[String, String] = apply()

  private def apply(): Map[String, String] = {
    val factory = ConfigFactory.load()
    val dataRoot = factory.getString("dataRoot")
    val incorrectDataRoot = factory.getString("incorrectDataRoot")
    val correctDataRoot = factory.getString("correctDataRoot")
    Map("data" -> dataRoot, "incorrect" -> incorrectDataRoot, "correct" -> correctDataRoot)
  }
}
