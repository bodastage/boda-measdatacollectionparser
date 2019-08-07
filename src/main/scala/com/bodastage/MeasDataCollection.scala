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
import java.io._

case class Config(
                   in: File = new File("."),
                   out: File = null
                 )


object MeasDataCollection {

  var outputFolder: String = "";

  def main(args: Array[String]): Unit = {

    val builder = OParser.builder[Config]
    val parser1 = {
      import builder._
      OParser.sequence(
        programName("boda-measdatacollectionparser"),
        head("boda-measdatacollectionparser", "0.1.0"),
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
        opt[File]('o', "out")
          .valueName("<file>")
          .action((x, c) => c.copy(out = x))
          .validate(f =>
            if( !Files.isDirectory(f.toPath ) && !Files.isReadable(f.toPath)) failure(s"Failed to access outputdirectory called ${f.getName}")
            else success
          )
          .text("output directory required."),
        help("help").text("prints this usage text"),
        note(sys.props("line.separator")),
        note("Parses 3GPP XML performance management files to csv. It processes plain text XML and gzipped XML files."),
        note("Examples:"),
        note("java -jar boda-measdatacollectionparser.jar -i FILENAME.xml"),
        note("java -jar boda-measdatacollectionparser.jar -i FILENAME.gz"),
        note("java -jar boda-measdatacollectionparser.jar -i FILENAME.gz -o outputFolder"),
        note("java -jar boda-measdatacollectionparser.jar -i FILENAME.xml -o outputFolder"),
        note(sys.props("line.separator")),
        note("Copyright (c) 2019 Bodastage Solutions(https://www.bodastage.com)")

      )
    }

      var inputFile : String = ""
      var outFile : File = null;
      OParser.parse(parser1, args, Config()) match {
        case Some(config) =>
          inputFile = config.in.getAbsolutePath
          outFile = config.out
        case _ =>
          // arguments are bad, error message will have been displayed
          sys.exit(1)
      }

    try{

      if(outFile != null) outputFolder = outFile.getAbsoluteFile().toString

     if(outputFolder.length == 0){
       val header : String =
         "file_name," +
           "file_format_version," +
           "vendor_name," +
           "file_header_dnprefix," +
           "file_sender_localdn," +
           "element_type," +
           "collection_begin_time," +
           "collection_end_time," +
           "managed_element_localdn," +
           "ne_software_version," +
           "meas_infoid," +
           "meas_timestamp," +
           "jobid," +
           "gran_period_duration," +
           "gran_period_endtime," +
           "reporting_period," +
           "managed_element_userlabel," +
           "meas_objldn," +
           "meas_type," +
           "meas_result," +
            "suspect";
         println(header)
     }else{
       outputFolder = outFile.getAbsolutePath();
     }


      this.processFileOrDirectory(inputFile)

    }catch{
      case ex: Exception => {
        println("Error accessing file:" + ex.toString )
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
    * Extact the measurement collection start and end time
    *
    * @param fileName
    *
    * @return array [startTime, endTime]
    */
  def extractMeasCollectTime(fileName : String) : Array[String] = {
    val contentType = Files.probeContentType(Paths.get(fileName))

    var xml = new XMLEventReader(Source.fromFile(fileName))

    if(contentType == "application/x-gzip"){
      xml = new XMLEventReader(Source.fromInputStream(this.getGZIPInputStream(fileName)))
    }

    var beginTime:String = "";
    var endTime:String = "";

    var measCollectionTime:Array[String] = new Array[String](2)

    for(event <- xml) {
      event match {
        case EvElemStart(_, tag, attrs, _) => {
          if (tag == "measCollec") {
            for (m <- attrs) {
              if (m.key == "beginTime") beginTime = m.value.toString()
              if (m.key == "endTime") endTime = m.value.toString()
            }
          }
        }
        case _ =>
      }
    }

    measCollectionTime(0) = beginTime;
    measCollectionTime(1) = endTime;

    return measCollectionTime;
  }

  /**
    * Parse a file
    * @param filename
    */
  def parseFile(fileName: String) = {

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


    val fileBaseName: String  = getFileBaseName(fileName);
    var pw : PrintWriter = null;

    //Get collection time
    var measCollectionTime:Array[String] = new Array[String](2);
    measCollectionTime = extractMeasCollectTime(fileName)


    if(outputFolder.length > 0){

      val csvFile : String = outputFolder + File.separator + fileBaseName.replaceAll(".(xml|gz)$",".csv");

      pw  = new PrintWriter(new File(csvFile));
      val header : String =
        "file_name," +
        "file_format_version," +
        "vendor_name," +
        "file_header_dnprefix," +
        "file_sender_localdn," +
        "element_type," +
        "collection_begin_time," +
        "collection_end_time," +
        "managed_element_localdn," +
        "ne_software_version," +
        "meas_infoid," +
        "meas_timestamp," +
        "jobid," +
        "gran_period_duration," +
        "gran_period_endtime," +
        "reporting_period," +
        "managed_element_userlabel," +
        "meas_objldn," +
        "meas_type," +
        "meas_result," +
        "suspect";
      pw.write(header + "\n");
    }

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
            suspect = "";
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
            measTypes = buf.mkString.replaceAll("\\s+"," ").trim()
            val msTypes = measTypes.split(" ")
            measTypesList = ListBuffer(msTypes: _ *)
          }

          if (tag == "suspect") {
            suspect = buf.mkString
          }

          if (tag == "measResults") {
            measResults = buf.mkString.replaceAll("\\s+"," ").trim()
            val msResults = measResults.split(" ")
            measResultsList = ListBuffer(msResults: _ *)
          }

          if (tag == "measValue") {
            var i = 0;
            for(i <- 0 to measTypesList.length-1){
              var measResult : String = measResultsList(i).toString
              var measType : String  = measTypesList(i)

              val csvRow : String =
                s"${getFileBaseName(fileName)}," +
                s"${fileFormatVersion}," +
                s"${vendorName}," +
                s"${toCSVFormat(fileHeaderDnPrefix)}," +
                s"${toCSVFormat(fileSenderLocalDn)}," +
                s"${elementType}," +
                s"${collectionBeginTime}," +
                  s"${measCollectionTime(1)}," +
                s"${toCSVFormat(managedElementLocalDn)}," +
                s"${neSoftwareVersion}," +
                s"${measInfoId}," +
                s"${measTimeStamp}," +
                s"${jobId}," +
                s"${granPeriodDuration}," +
                s"${granPeriodEndTime}," +
                s"${reportingPeriod}," +
                s"${toCSVFormat(managedElementUserLabel)}," +
                s"${toCSVFormat(measObjLdn)}," +
                s"${measType}," +
                s"${measResult}," +
                s"${suspect}" ;

              if(outputFolder.length == 0) {
                println(csvRow);
              }else{
                pw.write(csvRow + "\n");
              }
            }

          }

          buf.clear
        }

        case _ =>
      }
    }


    if(pw != null ) pw.close();
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
