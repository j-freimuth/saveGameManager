package db.entity

import java.sql.ResultSet

import `type`.DateTime

import scala.util.Try

/**
 * Entity of a file
 *
 * @param id id of the file
 * @param filePath path of the file
 * @param fileHash hash of the file
 * @param uploaded whether the file was already uploaded
 * @param lastUpdate when the file was found first with this hash
 * @param isDirectory whether we are dealing with a file or a directory
 */
case class FileEntity(
  id: Long,
  filePath: String,
  fileHash: Option[String],
  uploaded: Boolean,
  lastUpdate: DateTime,
  isDirectory: Boolean
)

object FileEntity {

  /**
    * Creates a list of file entities from a result set
    *
    * @param resultSet result set to create entities
    * @return
    */
  def fromResultSet(resultSet: ResultSet): Option[List[FileEntity]] = {

    def getEntity(resultSet: ResultSet): FileEntity = {

      new FileEntity(
        id = resultSet.getLong("id"),
        filePath = resultSet.getString("file_path"),
        fileHash = Try(resultSet.getString("hash")).toOption,
        uploaded = resultSet.getBoolean("uploaded"),
        lastUpdate = DateTime.fromTimestamp(resultSet.getTimestamp("last_update")),
        isDirectory = resultSet.getBoolean("is_directory")
      )
    }

    def getList(resultSet: ResultSet, list: List[FileEntity] = List()): List[FileEntity] = {

      val newList = list :+ getEntity(resultSet)

      if (resultSet.next) {

        getList(resultSet, newList)
      } else {

        newList
      }
    }

    if (resultSet.next) {

      Some(getList(resultSet))
    } else {

      None
    }
  }
}
