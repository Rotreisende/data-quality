import Headers.headers

package object Instrument {
  val validLength = 40
  def createRow(fields: Seq[String]): Map[String, String] = headers.zip(fields).toMap
}
