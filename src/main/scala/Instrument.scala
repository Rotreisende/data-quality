import DataType.Row
import Headers.headers
import DataType._
import Rules._

package object Instrument {
  val nameLength = 40
  val idTypeLength = 2
  val idNumberLength = 8
  val idAuthorityLength = 200
  def createRow(fields: Seq[String]): Map[String, String] = headers.zip(fields).toMap
  def toStringFromRow(row: Row): String = {
    val newHeaders = headers.drop(1)
    newHeaders.foldLeft(s"${row(headers.head)}")((acc, value) => acc.concat(s",${row(value)}"))
  }

  def switchValidator(row: Row, field: String): Boolean = {
    field match {
      case "surname" => DataQuality.forAll(row(field): Id[String], nameRules)
      case "name" => DataQuality.forAll(row(field): Id[String], nameRules)
      case "patronymic" => DataQuality.forAll(Option(row(field)), nameOpsRules)
      case "birthday" => DataQuality.forAll(row(field): Id[String], dateRules)
      case "primaryIdType" => DataQuality.forAll(row(field): Id[String], idTypeRules)
      case "primaryIdNumber" => DataQuality.forAll(row(field): Id[String], idNumberRules)
      case "primaryIdIssueDate" => DataQuality.forAll(row(field): Id[String], dateRules)
      case "primaryIdAuthority" => DataQuality.forAll(row(field): Id[String], idAuthorityRules)
      case "secondaryIdType" => DataQuality.forAll(Option(row(field)), idTypeOpRules)
      case "secondaryIdNumber" => DataQuality.forAll(Option(row(field)), idNumberOpRules)
      case "secondaryIdIssueDate" => DataQuality.forAll(Option(row(field)), dateOpRules)
      case "secondaryIdAuthority" => DataQuality.forAll(Option(row(field)), idAuthorityOpRules)
    }
  }
}