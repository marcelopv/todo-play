package exceptions

import play.api.libs.json.Json

case class TodoErrorResponseException(key: String, message: String) extends Exception

object TodoErrorResponseException {
  implicit val writes = Json.writes[TodoErrorResponseException]
  implicit val reads = Json.reads[TodoErrorResponseException]
}