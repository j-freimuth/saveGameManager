package Directory

import java.io.File
import scala.language.postfixOps

class Directory {

  /**
    * Recurse through a directory
    *
    * @param file file that is the base of the recurse
    */
  def getFileList(file: File): List[File] = {

    val files = file.listFiles.filter(_.isFile)
    val directories = file.listFiles.filter(_.isDirectory).flatMap(getFileList)
    files ++ directories toList
  }
}
