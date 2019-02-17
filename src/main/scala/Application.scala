import `type`.DateTime
import db.SqliteDatabase
import db.entity.FileEntity

object Application extends App {

  val db = new SqliteDatabase
  db.createDb

  val entity = FileEntity(0L, "/path/to/new/file", Some("md5#new232313221231"), false, DateTime.now, true)
  db.addFile(entity)
  db.selectFiles(None).get.foreach(println)
}
