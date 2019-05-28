package com.bodastage

import scala.io.Source
import scala.xml.pull._
import scala.collection.mutable.ArrayBuffer
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import scala.xml.XML
import scala.collection.mutable.ListBuffer
import scopt.OParser
import java.util.zip.GZIPInputStream
import java.io.BufferedInputStream
import java.io.FileInputStream

case class Config(
                   in: File = new File("."),
                   //   out: File = new File(".")
                 )


class MeasDataCollection {

  def main(args: Array[String]): Unit = {

    val builder = OParser.builder[Config]
    val parser1 = {
      import builder._
      OParser.sequence(
        programName("boda-measdatacollectionparser"),
        head("boda-measdatacollectionparser", "0.0.3"),
        opt[File]('i', "in")
          .required()
          .valueName("<file>")
          .action((x, c) => c.copy(in = x))
          .validate(f =>
            if( (!Files.isRegularFile(f.toPath) && !Files.isDirectory(f.toPath))
              && !Files.isReadable(f.toPath)) failure(s"Failed to access input file/directory called ${f.getName}")
            else success
          )
          .text("input file or directory, required."),
        help("help").text("prints this usage text"),
        note(sys.props("line.separator")),
        note("Parses 3GPP XML performance management files to csv. It processes plain text XML and gzipped XML files."),
        note("Examples:"),
        note("java -jar boda-measdatacollectionparser.jar -i FILENAME.xml"),
        note("java -jar boda-measdatacollectionparser.jar -i FILENAME.gz"),
        note(sys.props("line.separator")),
        note("Copyright (c) 2019 Bodastage Solutions(http://www.bodastage.com)")

      )
    }

    var inputFile : String = ""
    OParser.parse(parser1, args, Config()) match {
      case Some(config) =>
        inputFile = config.in.getAbsolutePath

      case _ =>
        // arguments are bad, error message will have been displayed
        sys.exit(1)
    }

    try{

      println("")
      println("filename,start_time,interval,base_id,local_moid,ne_type,measurement_type,counter_id,counter_value")

      this.processFileOrDirectory(inputFile)

    }catch{
      case ex: Exception => {
        println("Error accessing file")
        sys.exit(1)
      }
    }

  }

  /**
    * Get file base name
    *
    * @param fileName
    * @return
    */
  def getFileBaseName(fileName: String): String ={
    try{
      return new File(fileName).getName
    }catch{
      case ex: Exception => {
        return fileName
      }
    }
  }

  def processFileOrDirectory(inputPath: String): Unit ={


    val file : Path = Paths.get(inputPath)
    val isRegularExecutableFile : Boolean = Files.isRegularFile(file) & Files.isReadable(file)
    val isReadableDirectory = Files.isDirectory(file) & Files.isReadable(file)

    if (isRegularExecutableFile) {
      this.parseFile(inputPath)
    }

    if (isReadableDirectory) {
      val directory = new File(inputPath)

      val fList = directory.listFiles
      for(f:File <- fList){
        this.parseFile(f.getAbsolutePath)
      }
    }

  }

  /**
    * Parse a file
    * @param filename
    */
  def parseFile(fileName: String) : Unit = {

    var fileFormatVersion: String = "";
    var vendorName: String = "";
    var fileHeaderDnPrefix: String = "";
    var fileSenderLocalDn: String= "";
    var elementType: String = ""
    var collectionBeginTime:String = "";
    var managedElementLocalDn : String= "";
    var neSoftwareVersion: String= "";
    var measInfoId: String = ""
    var measTimeStamp: String = ""
    var jobId: String = ""
    var granPeriodDuration: String = ""
    var granPeriodEndTime: String = ""
    var reportingPeriod: String = ""
    var measTypes: String = ""
    var measObjLdn: String = ""
    var measResults: String = ""
    var managedElementUserLabel: String = ""
    var suspect: String = ""

    var measTypesList = new ListBuffer[String] // Counters
    var measResultsList = new ListBuffer[String] //Values

    val contentType = Files.probeContentType(Paths.get(fileName))

    var xml = new XMLEventReader(Source.fromFile(fileName))

    if(contentType == "application/x-gzip"){
      xml = new XMLEventReader(Source.fromInputStream(this.getGZIPInputStream(fileName)))
    }
    var buf = ArrayBuffer[String]()

    for(event <- xml) {
      event match {
        case EvElemStart(_, tag, attrs, _) => {
          buf.clear

          if (tag == "fileHeader") {
            for (m <- attrs) {
              if (m.key == "localDn") fileSenderLocalDn = m.value.toString()
              if (m.key == "elementType") elementType = m.value.toString()
              if (m.key == "dnPrefix") fileHeaderDnPrefix = m.value.toString()
            }
          }

          if (tag == "fileSender") {
            for (m <- attrs) {
              if (m.key == "fileFormatVersion") fileFormatVersion = m.value.toString()
              if (m.key == "vendorName") vendorName = m.value.toString()
            }
          }

          if (tag == "measCollec") {
            for (m <- attrs) {
              if (m.key == "beginTime") collectionBeginTime = m.value.toString()
            }
          }

          if (tag == "managedElement") {
            for (m <- attrs) {
              if (m.key == "localDn") managedElementLocalDn = m.value.toString()
              if (m.key == "userLabel") managedElementUserLabel = m.value.toString()
            }
          }


          if (tag == "measInfo") {
            for (m <- attrs) {
              if (m.key == "measInfoId") measInfoId = m.value.toString()
            }
          }

          if (tag == "job") {
            for (m <- attrs) {
              if (m.key == "jobId") jobId = m.value.toString()
            }
          }

          if (tag == "granPeriod") {
            for (m <- attrs) {
              if (m.key == "duration") granPeriodDuration = m.value.toString()
              if (m.key == "endTime") granPeriodEndTime = m.value.toString()
            }
          }

          if (tag == "repPeriod") {
            for (m <- attrs) {
              if (m.key == "duration") reportingPeriod = m.value.toString()
            }
          }

          if (tag == "measValue") {
            for (m <- attrs) {
              if (m.key == "measObjLdn") measObjLdn = m.value.toString()
            }
          }

        }
        case EvText(t) => {
          buf += (t)
        }

        case EvElemEnd(_, tag) => {
          if (tag == "measTypes") {
            measTypes = buf.mkString
            val msTypes = buf.mkString.trim.split(" ")
            measTypesList = ListBuffer(msTypes: _ *)
          }

          if (tag == "suspect") {
            suspect = buf.mkString
          }

          if (tag == "measResults") {
            measResults = buf.mkString
            val msResults = buf.mkString.trim.split(" ")
            val measResultsList = ListBuffer(msResults: _ *)
          }

          if (tag == "measValue") {
            var i = 0;
            for(i <- 0 to measTypesList.size){
              var measResult : String = measResultsList(i).toString
              var measType : String  = measTypesList(i)

              println(
                s"${getFileBaseName(fileName)}," +
                s"$fileFormatVersion," +
                s"$vendorName," +
                s"$fileHeaderDnPrefix" +
                s"$fileSenderLocalDn" +
                s"$elementType," +
                s"$collectionBeginTime," +
                s"$managedElementLocalDn," +
                s"$neSoftwareVersion," +
                s"$measInfoId," +
                s"$measTimeStamp," +
                s"$jobId," +
                s"$granPeriodDuration," +
                s"$granPeriodEndTime," +
                s"$reportingPeriod," +
                s"$managedElementUserLabel," +
                s"$measObjLdn," +
                s"$measType," +
                s"$measResult" +
                s"")
            }

           // println(s"${getFileBaseName(fileName)},$startTime,$interval,$moBaseId,$moLocalMoId,$neType,$measType,$counterId,$counterValue")
          }

          buf.clear
        }

        case _ =>
      }
    }
  }

  /**
    * Returns InputFreamFrom
    * @param s
    * @return
    */
  def getGZIPInputStream(s: String) = new GZIPInputStream(new BufferedInputStream(new FileInputStream(s)))

  def toCSVFormat(s: String): String = {
    var csvValue: String = s

    if(s.matches(".*,.*")){
      csvValue = "\"" + s + "\""
    }

    if(s.matches(""".*".*""")){
      csvValue = "\"" + s.replace("\"", "\"\"") + "\""
    }

    return csvValue
  }

}
