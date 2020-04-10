package controllers

import exceptions.TodoErrorResponseException
import javax.inject._
import model.Todo
import model.api.{AddTodoRequest, UpdateTodoRequest}
import play.api.libs.json.Json
import play.api.mvc._
import repositories.TodoRepository

@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents, val todoRepository: TodoRepository) extends BaseController {

  def list() = Action { implicit request: Request[AnyContent] =>
    val list = todoRepository.list()
      .map((todoElem:Todo) => Todo(todoElem.id, todoElem.description)):List[Todo]

    Ok(Json.toJson(list))
  }

  def getTodo(id: Long) = Action { implicit  request: Request[AnyContent] =>
    val resultDB = todoRepository.find(id)

    try {
      val result = resultDB match {
        case Some(resultDB) => resultDB
        case None => throw new TodoErrorResponseException("user.not.found", "could not find user with id " + id)
      }
      Ok(Json.toJson(result))
    } catch {
      case e: TodoErrorResponseException => Ok(Json.toJson(e))
    }

  }

  def add() = Action(parse.json(AddTodoRequest.reads)) { implicit request =>
    todoRepository.add(request.body)
    Ok
  }

  def update() = Action(parse.json(UpdateTodoRequest.reads)) { implicit  request =>
    todoRepository.update(request.body)
    Ok
  }

}
