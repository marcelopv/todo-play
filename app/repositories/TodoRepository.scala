package repositories

import anorm.SqlParser._
import anorm._
import javax.inject.Inject
import model.api.{AddTodoRequest, UpdateTodoRequest}
import model.Todo
import play.api.db.DBApi

import scala.language.postfixOps;

@javax.inject.Singleton
class TodoRepository @Inject()(dbapi: DBApi)() {

  private val db = dbapi.database("todos")

  val rowParser: RowParser[Todo] = {
      get[Long]("id") ~
      get[String]("description") map {
      case id~description => Todo(id, description)
    }
  }

  def list(): List[Todo] ={
    db.withConnection { implicit connection =>
      SQL("""
        select * from todos
        """
      ).as(rowParser *)
    }
  }

  def find(id: Long): Option[Todo] = {
    db.withConnection { implicit  c =>
      SQL("select * from todos where id = {id}")
        .on("id" -> id)
        .as(rowParser.singleOpt)
    }
  }

  def add(request: AddTodoRequest): Unit ={
    db.withConnection { implicit c =>
      SQL("insert into todos(description) values ({description})")
        .on("description" -> request.description)
        .executeInsert(scalar[String].single)
    }
  }

  def update(request: UpdateTodoRequest): Unit ={
    db.withConnection { implicit c =>
      SQL(s"update todos set description = {description} where id = {id}")
        .on("description" -> request.description, "id" -> request.id)
        .executeUpdate()
    }
  }
}
