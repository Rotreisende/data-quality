import Headers.headers

package object Instrument {
  def createRow(fields: Seq[String]) = headers.zip(fields).toMap
}
