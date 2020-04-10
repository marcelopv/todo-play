package model.api

import play.api.libs.json.Json

case class UpdateTodoRequest (id: Long, description: String)

object UpdateTodoRequest {
  implicit val reads = Json.reads[UpdateTodoRequest]
}
