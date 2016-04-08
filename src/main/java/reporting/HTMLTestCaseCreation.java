package reporting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Common.Property;
import Common.Utility;

public class HTMLTestCaseCreation {

    public LogFile objLog;
    public String Html_Header_String;
    public String Html_Content_String;
    Property property;
    Utility utility;

    public HTMLTestCaseCreation(Property property, Utility utility) {

	// Creating class objects for classes within framework
	objLog = new LogFile();
	this.property = property;
	this.utility = utility;

	// Creating Java variables
	Html_Header_String = "";
	Html_Content_String = "";
    }

    public void HTMLReport(ArrayList<String> TestDetails) {
	String[] item;
	String tcName = "";
	Html_Content_String = "";
	Html_Header_String = "<html>"
	        + " <head>"
	        + "<title>$TITLE</title>"
	        + "<meta charset=UTF-8>"
	        + "<script type='text/javascript'> function expand(TCID,TRID){	"
	        + "var element = document.getElementById(TCID.id);	"
	        + "var trelement=document.getElementById(TRID);"
	        + "	if(element.style.display=='table-row')	{"
	        + "		element.style.display='none';"
	        + "		trelement.innerHTML='[+]';"
	        + "	}	else"
	        + "	{"
	        + "		element.style.display='table-row';"
	        + "		trelement.innerHTML='[-]';"
	        + "	}"
	        + "}</script>"
	        + "</head>"
	        + "<body>"
	        + "<div>"
	        + "<p>"
	        +

	        "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Test Case Name :  </font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: blue;'>$TESTNAME</font></p><p>"
	        // +"<font align ='right' style='font-weight: bold; font-size:
	        // 14px; font-style:normal; font-family: Arial; color:
	        // black;'><a href='$HOMEURL'>Home Page</a></font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Test Case Status :</font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial;  color:$TCSTATUSCOLOR;'>&nbsp;$TESTSTATUS</font>&nbsp;&nbsp;&nbsp;"
	        + "</font></p><p>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Execution Started at: </font>"
	        +

	        "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: blue;'>$STARTTIME</font>&nbsp;&nbsp;&nbsp;"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Execution Finished at: </font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: blue;'>$FINISHTIME</font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>&nbsp;&nbsp;Environment: </font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: blue;'>$ENVIRONMENT</font></p>"
	        + "<p><font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Total Run Duration: </font>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: blue;'>$RUNDURATION</font>"
	        + "</p>"
	        // +
	        // "<font style='font-weight: bold; font-size: 14px;
	        // font-style:normal; font-family: Arial; color: black;'>Report
	        // Location: </font>"
	        // +
	        // "<font style='font-weight: bold; font-size: 14px;
	        // font-style:normal; font-family: Arial; color:
	        // blue;'>$REPORT_LOCATION"
	        // + " </p>"
	        + " </div><p>"
	        + " <font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Execution Report</font>"
	        + "<table width='100%' style='border:1px solid #8E001D'>"
	        + "<tr style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
	        + "<td width='5%'>Test Name</td>" + "<td width='15%'>&nbsp;Test Step</td>" + "<td width='40%'>&nbsp;Test Details</td>"
	        + "<td width='5%'>&nbsp;Status</td>" + "<td width='15%'>&nbsp;Execution Date and Time</td>" + "<td width='25%'>&nbsp;Remarks</td></tr>";

	try {
	    for (String Step : TestDetails) {

		item = Step.split("!!");
		if (item.length == 1) {
		    break;
		}
		String testName = item[0];
		String testStep = item[1];
		String testDetail = item[2];
		String testStatus = item[3];
		String testRemarks = item[4];
		String testTime = item[5];
		// if condition to fetch Test Case name.
		// ignore the last item as it contains the Test Case execution
		// status.

		Html_Content_String = Html_Content_String + "<tr style='font-family: Arial; background-color: #FFF8C6'>";
		Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
		        + testName + "</td>";
		Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
		        + testStep + "</td>";
		Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
		        + testDetail + "</td>";

		if (testStatus.equals(property.PASS)) {
		    Html_Content_String = Html_Content_String
			    + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;<span id='span_id' style='color: green;'>&nbsp; Pass </span></td>";
		} else if (testStatus.equals(property.FAIL)) {
		    Html_Content_String = Html_Content_String
			    + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;<span id='span_id' style='color: red;'>&nbsp; Fail </span></td>";
		} else if (testStatus.equals(property.IGNORE)) {
		    Html_Content_String = Html_Content_String
			    + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;<span id='span_id' style='color: grey;'>&nbsp; Ignored </span></td>";
		} else if (testStatus.equals(property.SKIP)) {
		    Html_Content_String = Html_Content_String
			    + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;<span id='span_id' style='color: grey;'>&nbsp; Skipped </span></td>";
		} else {
		    Html_Content_String = Html_Content_String
			    + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;<span id='span_id' style='color: grey;'>&nbsp;</span></td>";
		}
		Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
		        + testTime + "</td>";

		if (testStep.equals("compareImages") && property.generateImage.equalsIgnoreCase("false")) {
		    Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
			    + testRemarks + "<a href='" + property.getexpectedImage() + "' target='_blank'>Expected Image</a><br>" + "<a href='"
			    + property.getactualImage() + "' target='_blank' >Actual Image</a><br>";
		    if (testStatus.equals(property.FAIL)) {
			Html_Content_String = Html_Content_String + "<a href='" + property.getdiffImage() + "' target='_blank'>Diff Image</a><br />";
		    }
		    Html_Content_String = Html_Content_String + "<a href='" + property.getFullScreenShotImage() + "' target='_blank'>Full Screeshot</a><br />";
		    Html_Content_String = Html_Content_String + "<a href='http://forgehub.tribpub.com/jsp/DisplayHtmlNext.jsp?imagenm="
			    + property.getdiffImage().split("/")[property.getdiffImage().split("/").length - 1] + "&date=" + utility.getDate() + "&time="
			    + Property.getExecutionTime() + "' target='_blank'>Replace Image</a></td>";

		} else if (testRemarks.contains("screenshot")) {
		    Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
			    + testRemarks.substring(0, testRemarks.lastIndexOf(" ")) + "&nbsp;";
		    if (property.getScreenshotName() != null) {
			Html_Content_String = Html_Content_String + "<a href='" + property.getScreenshotName() + "' target='_blank'>ScreenShot</a>";
		    }
		    Html_Content_String = Html_Content_String + "</td>";
		} else if (testStep.equalsIgnoreCase("openbrowser") && testStatus.equals(property.PASS)) {
		    Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
			    + "&nbsp;<a href='" + testRemarks + "' target='_blank'>Site Url</a></td>";
		} else if (testStep.equalsIgnoreCase("cutRequiredImage") && testStatus.equals(property.FAIL)) {
		    Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
			    + testRemarks + "&nbsp;<a href='" + property.getFullScreenShotImage() + "' target='_blank'>Full Screenshot</a></td>";
		} else {
		    Html_Content_String = Html_Content_String + "<td  style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;"
			    + testRemarks + "</td>";
		}

		Html_Content_String = Html_Content_String + "</tr>";
	    }
	    Html_Content_String = Html_Content_String + "</table></body></html>";
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
	String tcStatus = "Pass";
	if (TestDetails.get(TestDetails.size() - 1).split("!")[1].equalsIgnoreCase("true")) {
	    tcStatus = "Fail";
	}
	tcName = TestDetails.get(0).split("!")[0];
	replaceHtmlContent(tcName, tcStatus);
	// if (property.IsRemoteExecution.equalsIgnoreCase("true") &&
	// property.localGrid.equalsIgnoreCase("false")) {
	// createJenkinsReport(tcName);
	// }

	try {
	    File file = new File(System.getProperty("user.dir") + property.test_Report_Logs_Path + tcName + ".html");
	    if (file.exists()) {
		file.delete();
	    }
	    file.getParentFile().mkdirs();
	    file.createNewFile();

	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    fw.write(Html_Header_String);
	    fw.write(Html_Content_String);
	    fw.close();
	} catch (NoSuchFileException x) {
	    System.err.format("%s: No such" + " file or directory%n", property.test_Report_Logs_Path);
	} catch (IOException x) {
	    objLog.writeInfo(x.getMessage());
	}
    }

    private void createJenkinsReport(String tcName) {

	try {
	    File jfile = new File(property.expectedImageJenkinPath, "Test_Report_Logs" + property.FileSeperator.get() + utility.getDate()
		    + property.FileSeperator.get() + Property.getExecutionTime() + property.FileSeperator.get() + tcName + ".html");
	    if (jfile.exists()) {
		jfile.delete();
	    }
	    jfile.getParentFile().mkdirs();
	    jfile.createNewFile();

	    FileWriter jfw = new FileWriter(jfile.getAbsoluteFile());
	    jfw.write(Html_Header_String);
	    jfw.write(Html_Content_String);
	    jfw.close();
	} catch (NoSuchFileException x) {
	    System.err.format("%s: No such" + " file or directory%n", property.test_Report_Logs_Path);
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

    private void replaceHtmlContent(String tcName, String tcStatus) {

	try {
	    SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
	    Html_Header_String = Html_Header_String.replace("$STARTTIME", property.getExecutionStartTime());
	    Html_Header_String = Html_Header_String.replace("$FINISHTIME", property.getExecutionEndTime());
	    Html_Header_String = Html_Header_String.replace("$TESTNAME", tcName);
	    Date ExecutionStartTime = format.parse(property.getExecutionStartTime());
	    Date ExecutionEndTime = format.parse(property.getExecutionEndTime());
	    long diff = (ExecutionEndTime.getTime() - ExecutionStartTime.getTime());
	    long diffSeconds = diff / 1000 % 60;
	    long diffMinutes = diff / (60 * 1000) % 60;
	    long diffHours = diff / (60 * 60 * 1000) % 24;
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	    objLog.writeInfo(diffDays + " Days : " + diffHours + " Hours : " + diffMinutes + " mins : " + diffSeconds + " sec");
	    Html_Header_String = Html_Header_String.replace("$RUNDURATION",
		    String.valueOf(diffDays + " Days : " + diffHours + " Hours : " + diffMinutes + " mins : " + diffSeconds + " sec"));
	    Html_Header_String = Html_Header_String.replace("$ENVIRONMENT", property.getOsString());
	    Html_Header_String = Html_Header_String.replace("$TESTSTATUS", tcStatus);
	    Html_Header_String = Html_Header_String.replace("$REPORT_LOCATION", property.test_Report_Logs_Path + tcName + ".html");
	    Html_Header_String = Html_Header_String.replace("$TITLE", tcName);
	    if (tcStatus.equalsIgnoreCase("pass")) {
		Html_Header_String = Html_Header_String.replace("$TCSTATUSCOLOR", "Green");
	    } else {
		Html_Header_String = Html_Header_String.replace("$TCSTATUSCOLOR", "Red");

		Html_Header_String = Html_Header_String.replace("$HOMEURL", System.getProperty("user.dir") + property.test_Report_Logs_Path + "main.html");
	    }

	}

	catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

}