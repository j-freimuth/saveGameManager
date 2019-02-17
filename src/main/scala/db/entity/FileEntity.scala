package db.entity

import `type`.DateTime

/**
 * Entity of a file
 *
 * @param id id of the file
 * @param filePath path of the file
 * @param fileHash hash of the file
 * @param uploaded whether the file was already uploaded
 * @param lastUpdate when the file was found first with this hash
 * @param isDirectory wheather we are dealing with a file or a directory
 */
case class FileEntity(
  id: Long,
  filePath: String,
  fileHash: Option[String],
  uploaded: Boolean,
  lastUpdate: DateTime,
  isDirectory: Boolean
)
