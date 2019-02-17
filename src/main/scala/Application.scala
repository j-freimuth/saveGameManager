import `type`.DateTime
import db.SqliteDatabase
import db.entity.FileEntity

object Application extends App {

  val db = new SqliteDatabase
  db.createDb

  val entity = FileEntity(0L, "/path/to/file", Some("md5#231231892389"), true, DateTime.now, false)
  db.addFile(entity)
}
