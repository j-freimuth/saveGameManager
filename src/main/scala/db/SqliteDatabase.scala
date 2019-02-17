package db

import java.sql.{Connection, DriverManager, Statement}

import db.entity.FileEntity

import scala.util.Try

class SqliteDatabase {

    val url: String = "jdbc:sqlite:db.sqlite"
    val con: Connection = DriverManager.getConnection(url)
    val statement: Statement = con.createStatement()

    /**
     * Create a database if it doesn't exist yet
     */
    def createDb(): Unit = {

        /* Create files table */
        val createGame = """
            |CREATE TABLE IF NOT EXISTS game (
                |id INTEGER PRIMARY KEY AUTOINCREMENT,
                |name VARCHAR(255) NULL
            |);
          """.stripMargin

        val createFile = """
              |CREATE TABLE IF NOT EXISTS file (
                |id INTEGER PRIMARY KEY AUTOINCREMENT,
                |file_path VARCHAR(260) NOT NULL,
                |hash VARCHAR(255) NULL,
                |uploaded TINYINT(1) DEFAULT 0 NOT NULL,
                |last_update DATETIME NULL,
                |is_directory TINYINT(1) DEFAULT 0 NOT NULL
              |);
            """.stripMargin

        val createGameFile = """
             |CREATE TABLE IF NOT EXISTS game_file (
               |id INTEGER PRIMARY KEY AUTOINCREMENT,
               |file_id INTEGER NOT NULL,
               |game_id INTEGER NOT NULL,
               |uploaded TINYINT(1) DEFAULT 0 NOT NULL,
               |FOREIGN KEY(file_id) REFERENCES file(id),
               |FOREIGN KEY(game_id) REFERENCES game(id)
             |);
           """.stripMargin


        statement.executeUpdate(createGame)
        statement.executeUpdate(createFile)
        statement.executeUpdate(createGameFile)

        ()
    }

    // pragma mark File

    /**
      * Adds a file to the database
      *
      * @param entity file entity
      * @return
      */
    def addFile(entity: FileEntity): Try[Boolean] = Try {

        val statement = con.prepareStatement(
          """
            |INSERT INTO file(file_path, hash, uploaded, last_update, is_directory)
            |VALUES (?,?,?,?,?)
          """.stripMargin
        )

        statement.setString(1, entity.filePath)
        statement.setString(2, entity.fileHash.getOrElse("NULL"))
        statement.setBoolean(3, entity.uploaded)
        statement.setTimestamp(4, entity.lastUpdate.toSqlTimestamp)
        statement.setBoolean(5, entity.isDirectory)

        statement.execute()
    }

    /**
      * Updates a preexisting file entry
      *
      * @param entity file entity
      * @return
      */
    def updateFile(entity: FileEntity): Try[Boolean] = Try {

        val statement = con.prepareStatement(
            """
              |UPDATE file SET
              |file_path = ?,
              |hash = ?,
              |uploaded = ?,
              |last_update = ?,
              |is_directory = ?
              |WHERE id = ?;
            """.stripMargin
        )

        statement.setString(1, entity.filePath)
        statement.setString(2, entity.fileHash.getOrElse("NULL"))
        statement.setBoolean(3, entity.uploaded)
        statement.setTimestamp(4, entity.lastUpdate.toSqlTimestamp)
        statement.setBoolean(5, entity.isDirectory)
        statement.setLong(6, entity.id)

        statement.execute()
    }

    /**
      * Delete files from db
      *
      * @param entities entities to be deleted
      * @return
      */
    def deleteFile(entities: List[FileEntity]): Try[Boolean] = Try {

        val statement = con.prepareStatement(
            """
              |DELETE FROM file
              |WHERE id in (?);
            """.stripMargin
        )

        statement.setString(1, entities.map(_.id).mkString(","))

        statement.execute()
    }

    def selectFiles(entities: Option[List[FileEntity]]): Option[List[FileEntity]] = {

        val statement = con.prepareStatement(s"SELECT * FROM file ${if (entities.nonEmpty) "WHERE id in (?);" else ";"}")

        if (entities.nonEmpty) {

            statement.setString(1, entities.get.map(_.id).mkString(","))
        }

        FileEntity.fromResultSet(statement.executeQuery())
    }

    // pragma mark Game
    def addGame = ???
    def updateGame = ???
    def deleteGame = ???

    // pragma mark GameFile
    def addGameFile = ???
    def updateGameFile = ???
    def deleteGameFile = ???
}
