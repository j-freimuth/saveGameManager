package db.entity

import java.sql.ResultSet

/**
 * Entity of a file
 *
 * @param id id of the game file
 * @param fileId id of the file
 * @param gameId id of the game
 * @param uploaded whether the file was already uploaded
 */
case class GameFileEntity(
  id: Long,
  fileId: Long,
  gameId: Long,
  uploaded: Boolean,
)

object GameFileEntity {

  /**
    * Creates a list of game file entities from a result set
    *
    * @param resultSet result set
    * @return
    */
  def fromResultSet(resultSet: ResultSet): Option[List[GameFileEntity]] = {

    def createEntity(resultSet: ResultSet): GameFileEntity = {

      new GameFileEntity(
        id = resultSet.getLong("id"),
        fileId = resultSet.getLong("file_id"),
        gameId = resultSet.getLong("game_id"),
        uploaded = resultSet.getBoolean("uploaded")
      )
    }

    def getList(resultSet: ResultSet, list: List[GameFileEntity] = List()): List[GameFileEntity] = {

      val newList = list :+ createEntity(resultSet)

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


