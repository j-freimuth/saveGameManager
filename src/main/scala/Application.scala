import `type`.DateTime
import db.SqliteDatabase
import db.entity.{FileEntity, GameEntity}

object Application extends App {

  val db = new SqliteDatabase
  db.createDb

  val entity = GameEntity(1L, "US MARINES: Fubar!")
  db.addGame(entity)
  db.addGame(entity)
  db.addGame(entity)
  db.addGame(entity)
  db.addGame(entity)

  db.selectGames(None).get.foreach(println)
}
