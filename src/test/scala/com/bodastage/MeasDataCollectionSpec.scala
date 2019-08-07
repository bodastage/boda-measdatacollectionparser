package com.bodastage

import org.scalatest._

import scala.io.Source
import java.io.File

class MeasDataCollectionSpec extends FunSuite {
  test("MeasDataCollection") {

    val resourcePath = getClass.getResource("/A1.xml")
    var inputFile : String = resourcePath.getPath
    val outDir = System.getProperty("java.io.tmpdir")

    var parser = MeasDataCollection;
    var args : Array[String] = Array("-i", inputFile, "-o", outDir);
    parser.main(args);

    val expectedCSV = "file_name,file_format_version,vendor_name,file_header_dnprefix,file_sender_localdn,element_type,collection_begin_time,collection_end_time,managed_element_localdn,ne_software_version,meas_infoid,meas_timestamp,jobid,gran_period_duration,gran_period_endtime,reporting_period,managed_element_userlabel,meas_objldn,meas_type,meas_result,suspect\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attTCHSeizures,234,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succTCHSeizures,345,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attImmediateAssignProcs,567,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succImmediateAssignProcs,789,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attTCHSeizures,890,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succTCHSeizures,901,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attImmediateAssignProcs,123,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succImmediateAssignProcs,234,\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attTCHSeizures,456,true\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succTCHSeizures,567,true\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attImmediateAssignProcs,678,true\nA1.xml,,,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",,,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succImmediateAssignProcs,789,true".replace("\r", "")

    val sourceCSV = Source.fromFile(outDir + File.separator + "A1.csv").getLines().mkString("\n");
    assert(expectedCSV == sourceCSV)

  }

}
