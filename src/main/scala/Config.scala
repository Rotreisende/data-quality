import com.typesafe.config.{Config, ConfigFactory}

import scala.util.{Failure, Success, Try}

object Config {
  lazy val roots: Roots = load(safelyReadConfig())

  private def apply(factory: Config): Roots = {
    Roots(factory.getString("dataRoot"),
      factory.getString("incorrectDataRoot"),
      factory.getString("correctDataRoot"))
  }

  private def safelyReadConfig(): Try[Config] = {
    Try {
      ConfigFactory.load()
    }
  }

  private def load(config: Try[Config]): Roots = {
    config match {
      case Success(value) => apply(value)
      case Failure(exception) => throw exception
    }
  }
}
