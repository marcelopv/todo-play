package model

import play.api.libs.json.Json

case class Todo(id: Long, description: String)

object Todo {
  implicit val writes = Json.writes[Todo]
  implicit val reads = Json.reads[Todo]
}

