package db.entity

import java.sql.ResultSet

/**
 * Entity of a game
 *
 * @param id id of the game
 * @param name name of the game
 */
case class GameEntity(
  id: Long,
  name: String,
)

object GameEntity {

  /**
    * Get a list of game entities from a result set
    *
    * @param resultSet result set
    * @return
    */
  def fromResultSet(resultSet: ResultSet): Option[List[GameEntity]] = {

    def createEntity(resultSet: ResultSet): GameEntity = {

      new GameEntity(
        id = resultSet.getLong("id"),
        name = resultSet.getString("name")
      )
    }

    def getList(resultSet: ResultSet, list: List[GameEntity] = List()): List[GameEntity] = {

      val newList = list.+:(createEntity(resultSet))

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
