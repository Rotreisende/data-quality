object Headers {
  private lazy val headers = Vector("surname", "name", "patronymic")

  def getHeadersRow(): Seq[String] = {
    headers
  }
}
