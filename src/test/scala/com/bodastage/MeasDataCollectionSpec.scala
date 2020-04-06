package com.bodastage

import org.scalatest._

import scala.io.Source
import java.io.File

class MeasDataCollectionSpec extends FunSuite {
  test("A1.xml") {

    val resourcePath = getClass.getResource("/A1.xml")
    var inputFile : String = resourcePath.getPath
    val outDir = System.getProperty("java.io.tmpdir")

    var parser = MeasDataCollection;
    var args : Array[String] = Array("-i", inputFile, "-o", outDir);
    parser.main(args);

    var expectedCSV = "";
    expectedCSV += "file_name,file_format_version,vendor_name,file_header_dnprefix,file_sender_localdn,element_type,collection_begin_time,collection_end_time,managed_element_localdn,ne_software_version,meas_infoid,meas_timestamp,jobid,gran_period_duration,gran_period_endtime,reporting_period,managed_element_userlabel,meas_objldn,meas_type,meas_result,suspect\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attTCHSeizures,234,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succTCHSeizures,345,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attImmediateAssignProcs,567,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succImmediateAssignProcs,789,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attTCHSeizures,890,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succTCHSeizures,901,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attImmediateAssignProcs,123,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succImmediateAssignProcs,234,\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attTCHSeizures,456,true\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succTCHSeizures,567,true\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attImmediateAssignProcs,678,true\n"
    expectedCSV += "A1.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succImmediateAssignProcs,789,true"

    val sourceCSV = Source.fromFile(outDir + File.separator + "A1.csv").getLines().mkString("\n");
    assert(expectedCSV == sourceCSV)

  }

  test("A2.xml - Positional MeasTypes and measValues") {

    val resourcePath = getClass.getResource("/A2.xml")
    var inputFile : String = resourcePath.getPath
    val outDir = System.getProperty("java.io.tmpdir")

    var parser = MeasDataCollection;
    var args : Array[String] = Array("-i", inputFile, "-o", outDir);
    parser.main(args);

    var expectedCSV = "";

    expectedCSV += "file_name,file_format_version,vendor_name,file_header_dnprefix,file_sender_localdn,element_type,collection_begin_time,collection_end_time,managed_element_localdn,ne_software_version,meas_infoid,meas_timestamp,jobid,gran_period_duration,gran_period_endtime,reporting_period,managed_element_userlabel,meas_objldn,meas_type,meas_result,suspect\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attTCHSeizures,234,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succTCHSeizures,345,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attImmediateAssignProcs,567,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succImmediateAssignProcs,789,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attTCHSeizures,890,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succTCHSeizures,901,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attImmediateAssignProcs,123,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succImmediateAssignProcs,234,\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attTCHSeizures,456,true\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succTCHSeizures,567,true\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attImmediateAssignProcs,678,true\n"
    expectedCSV += "A2.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succImmediateAssignProcs,789,true"

    val sourceCSV = Source.fromFile(outDir + File.separator + "A2.csv").getLines().mkString("\n");
    assert(expectedCSV == sourceCSV)

  }
  
  test("A3.xml") {

    val resourcePath = getClass.getResource("/A3.xml")
    var inputFile : String = resourcePath.getPath
    val outDir = System.getProperty("java.io.tmpdir")

    var parser = MeasDataCollection;
    var args : Array[String] = Array("-i", inputFile, "-o", outDir);
    parser.main(args);

    var expectedCSV = "";

    expectedCSV += "file_name,file_format_version,vendor_name,file_header_dnprefix,file_sender_localdn,element_type,collection_begin_time,collection_end_time,managed_element_localdn,ne_software_version,meas_infoid,meas_timestamp,jobid,gran_period_duration,gran_period_endtime,reporting_period,managed_element_userlabel,meas_objldn,meas_type,meas_result,suspect\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category A,,01,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=1,MM.AttGprsAttach,10,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category A,,01,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=1,MM.SuccGprsAttach,20,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category A,,01,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=1,MM.AbortedGprsAttach,30,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category A,,01,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=1,MM.AttIntraSgsnRaUpdate,40,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category B,,02,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=2,MM.AttCombiAttach,10,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category B,,02,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=2,MM.SuccCombiAttach,20,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category B,,02,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=2,MM.AbortedCombiAttach,30,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category B,,02,PT900S,2005-06-09T13:15:00-06:00,PT1800S,SGSN,SgsnFunction=2,MM.AttCombiDetachMs,40,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category C,,03,PT1800S,2005-06-09T13:15:00-06:00,PT900S,SGSN,SgsnFunction=3,MM.AttPsPagingProcIu,25,\n"
    expectedCSV += "A3.xml,32.435 v6.1,Company NN,SubNetwork=1,OMC_PS=10,Element Manager,2005-06-09T13:00:00-05:00,2005-06-09T13:15:00-06:00,ManagedElement=PS_Core,R30.1.5,Category C,,03,PT1800S,2005-06-09T13:15:00-06:00,PT900S,SGSN,SgsnFunction=3,MM.SuccPsPagingProcIu,25,"

    val sourceCSV = Source.fromFile(outDir + File.separator + "A3.csv").getLines().mkString("\n");
    assert(expectedCSV == sourceCSV)

  }
  
  test("A4.xml - Positional measTypes and measValues with missing results") {

    val resourcePath = getClass.getResource("/A4.xml")
    var inputFile : String = resourcePath.getPath
    val outDir = System.getProperty("java.io.tmpdir")

    var parser = MeasDataCollection;
    var args : Array[String] = Array("-i", inputFile, "-o", outDir);
    parser.main(args);

    var expectedCSV = "";

    expectedCSV += "file_name,file_format_version,vendor_name,file_header_dnprefix,file_sender_localdn,element_type,collection_begin_time,collection_end_time,managed_element_localdn,ne_software_version,meas_infoid,meas_timestamp,jobid,gran_period_duration,gran_period_endtime,reporting_period,managed_element_userlabel,meas_objldn,meas_type,meas_result,suspect\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",attTCHSeizures,234,\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succTCHSeizures,345,\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-997\",succImmediateAssignProcs,789,\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attTCHSeizures,890,\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",succTCHSeizures,901,\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-998\",attImmediateAssignProcs,123,\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succTCHSeizures,567,true\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",attImmediateAssignProcs,678,true\n"
    expectedCSV += "A4.xml,32.435 V7.0,Company NN,\"DC=a1.companyNN.com,SubNetwork=1,IRPAgent=1\",\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",RNC,2000-03-01T14:00:00+02:00,2000-03-01T14:15:00+02:00,\"SubNetwork=CountryNN,MeContext=MEC-Gbg-1,ManagedElement=RNC-Gbg-1\",,,,1231,PT900S,2000-03-01T14:14:30+02:00,PT1800S,RNC Telecomville,\"RncFunction=RF-1,UtranCell=Gbg-999\",succImmediateAssignProcs,789,true"


    val sourceCSV = Source.fromFile(outDir + File.separator + "A4.csv").getLines().mkString("\n");
    assert(expectedCSV == sourceCSV)

  }

}
