import db.SqliteDatabase

object Application extends App {

  val db = new SqliteDatabase
  db.createDb()

  db.selectGameFiles(None).get.foreach(println)
}
