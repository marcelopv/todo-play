package model.api

import play.api.libs.json.Json

case class AddTodoRequest (description: String)

object AddTodoRequest {
  implicit val reads = Json.reads[AddTodoRequest]
}
