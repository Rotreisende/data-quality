import DataType.Row
import Headers.headers
import DataType._
import Rules.DataQuality
import Rules._

import scala.util.matching.Regex

package object Instrument {
  val nameLength = 40
  val idTypeLength = 2
  val idNumberLength = 8
  val idAuthorityLength = 200
  val charPattern: Regex = "^[a-zA-Z]+$".r
  def createRow(fields: Seq[String]): Map[String, String] = headers.zip(fields).toMap
  def toStringFromRow(row: Row): String = {
    val newHeaders = headers.drop(1)
    newHeaders.foldLeft(s"${row(headers.head)}")((acc, value) => acc.concat(s",${row(value)}"))
  }

  def switchValidator(row: Row, field: String): Boolean = {
    field match {
      case "surname" => DataQuality.forAll(row(field): Id[String], NameRules.identity)
      case "name" => DataQuality.forAll(row(field): Id[String], NameRules.identity)
      case "patronymic" => DataQuality.forAll(Option(row(field)), NameRules.option)
      case "birthday" => DataQuality.forAll(row(field): Id[String], DateRules.identity)
      case "primaryIdType" => DataQuality.forAll(row(field): Id[String], IdTypeRules.identity)
      case "primaryIdNumber" => DataQuality.forAll(row(field): Id[String], IdNumberRules.identity)
      case "primaryIdIssueDate" => DataQuality.forAll(row(field): Id[String], DateRules.identity)
      case "primaryIdAuthority" => DataQuality.forAll(row(field): Id[String], IdAuthorityRules.identity)
      case "secondaryIdType" => DataQuality.forAll(Option(row(field)), IdTypeRules.option)
      case "secondaryIdNumber" => DataQuality.forAll(Option(row(field)), IdNumberRules.option)
      case "secondaryIdIssueDate" => DataQuality.forAll(Option(row(field)), DateRules.option)
      case "secondaryIdAuthority" => DataQuality.forAll(Option(row(field)), IdAuthorityRules.option)
    }
  }

  def mapContainer[F[_] : Functor](value: F[String])(func: String => String): F[String] = {
    Functor[F].map(value)(func)
  }

  def safelyUsage[A <: AutoCloseable, B](resource: A)(usage: A => B): B = {
    try {
      usage(resource)
    } finally {
      resource.close()
    }
  }
}