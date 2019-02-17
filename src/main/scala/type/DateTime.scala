package `type`

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * DateTime Type
 *
 * @param value the actual DateTime object
 */
case class DateTime(value: LocalDateTime) {

  /**
   * Create a string formatted for use in SQL
   *
   * @return
   */
  def toSqlString: String = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

  /**
   * Create a timestamp usable for SQL
   *
   * @return
   */
  def toSqlTimestamp: Timestamp = Timestamp.valueOf(value)
}

object DateTime {

  /**
   * Create a new DateTime object of right now
   *
   * @return
   */
  def now = new DateTime(LocalDateTime.now)

  /**
    * Create a new DateTime object from Timestamp
    *
    * @return
    */
  def fromTimestamp(timestamp: Timestamp) = DateTime(timestamp.toLocalDateTime)
}
