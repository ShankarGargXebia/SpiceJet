package reporting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import Common.Property;
import Common.Utility;

public class ReportCreation {
    Property property;
    Utility utility;
    public LogFile objLog;
    @SuppressWarnings("unused")
    private String History_Report;
    private String text;
    private String Main_Frame_String;

    private String Automation_Report_String;

    private String Html_Suite_String;
    private String Html_Execution_String;

    private String Html_Summary_String;

    /**
    * 
    */
    public ReportCreation(Property property, Utility utility) {

	// Creating class objects for classes within framework
	objLog = new LogFile();
	this.property = property;
	this.utility = utility;

	// Creating Java variables
	History_Report = "<html><head>" + "<title>Results for Execution</title>" + "<script type='text/javascript'>" + "</script>" + "</head>" + "<body>";
	text = "";
	Main_Frame_String = "<html><head>" + "<title>Results for Execution</title>" + "<script type='text/javascript'>" + "</script>" + "</head>" + "<body>"
	        + "Date/Time: $DATETIME <br/>" + "<br />" + "<br />" + "<br />" + "Select a Result on the Left-Hand Pane." + "<div style='align: right'>"
	        + "<a target='_blank' href='$HISTORYREPORT' >View Previous Execution Reports</a>" + "</div>" + "</body></html>";
	Automation_Report_String = "<html><head>" + "<title>Execution Report</title>" + "</head> <frameset cols='40%,60%'>"
	        + "<frame src='ExecutionList.html' name='navFrame'>" + "<frame src='main.html' name='mainFrame'>" + "</frameset>" + "</html>";
	Html_Suite_String = "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Suite Report</font>"
	        + "<table width='100%' style='border:1px solid #8E001D'>"
	        + "<tr style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
	        + "<td width='10%'>&nbsp;S.No</td>" + "<td width='30%'>&nbsp;Suite</td>" + "<td width='20%'>&nbsp;Total</td>"
	        + "<td width='20%'>&nbsp;Passed</td>" + "<td width='20%'>&nbsp;Failed</td>" + "<td width='20%'>Host name</td>" + "</tr>";
	Html_Execution_String = "";
	Html_Summary_String = "<html>" + " <head>" + "<title>Execution List</title>" + "<meta charset=UTF-8>" + "</head>" + "<body>"
	        + "<font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Summary Report</font>"
	        + "<table width='100%' style='border:1px solid #8E001D'>"
	        + "<tr style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
	        + "<td>&nbsp;Total</td>" + "<td>&nbsp;Passed</td>" + "<td>&nbsp;Failed</td>" + "<td>&nbsp;Execution Time</td>" + "</tr>"
	        + "<tr style='font-family: Arial; background-color: #FFF8C6'>"
	        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;$TCCount</td>"
	        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;$PASSEDCOUNT</td>"
	        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;$FAILCOUNT</td>"
	        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;$TOTALTIME</td>" + "</tr></table>" + "<p></p>";
    }

    public void prepareHtmlContent(Map<String, ArrayList<String>> tCStatusMap) {
	int i = 1;
	int suiteCount = 1;
	property.setTcPassed(0);
	property.setTcFailed(0);

	Html_Execution_String = Html_Execution_String
	        + " <font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Execution Report</font>"
	        + "<table width='100%' style='border:1px solid #8E001D'>"
	        + "<tr style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
	        + "<td width='5%'>S.No</td>" + "<td width='7%'>Suite</td>" + "<td width='45%'>&nbsp;Test Name</td>" + "<td width='8%'>&nbsp;Test Group</td>"
	        + "<td width='8%'>&nbsp;Browser</td>" + "<td width='8%'>&nbsp;BreakPoint</td>" + "<td width='8%'>&nbsp;Status</td>"
	        + "<td width='10%'>&nbsp;Failed Step</td>" + "<td width='8%'>&nbsp;Result</td>" + "</tr>";

	try {
	    for (String key : tCStatusMap.keySet()) {
		int suitePassCount = 0;
		int suiteFailCount = 0;

		ArrayList<String> keyArray = tCStatusMap.get(key);

		for (int j = 0; j <= (keyArray.size() - 1); j++) {
		    String Text[] = keyArray.get(j).split("!");
		    String TestGroup = Text[0];
		    String TestCaseID = Text[1];
		    String Status = Text[5];
		    String TStatus = "";
		    String failedStep = Text[4];
		    String browser = Text[2];
		    String breakPoint = Text[3];
		    if (TestCaseID != null && !TestCaseID.equals(" ")) {

			// Table row TestCase.
			Html_Execution_String = Html_Execution_String + "<tr style='font-family: Arial; background-color: #FFF8C6'>";
			// FirstCoulmn.
			Html_Execution_String = Html_Execution_String
			        + "<td width='2%' align='center' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>";
			Html_Execution_String = Html_Execution_String + i + "</td>";
			i++;

			Html_Execution_String = Html_Execution_String
			        + "<td width='2%' align='center' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>";
			Html_Execution_String = Html_Execution_String + key + "</td>";

			Html_Execution_String = Html_Execution_String
			        + "<td width='45%' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + TestCaseID
			        + "</td>";

			Html_Execution_String = Html_Execution_String
			        + "<td width='8%' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + TestGroup
			        + "</td>";

			Html_Execution_String = Html_Execution_String
			        + "<td width='8%' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + browser
			        + "</td>";

			Html_Execution_String = Html_Execution_String
			        + "<td width='8%' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + breakPoint
			        + "</td>";

			// ThirdColumn
			String CStatus;
			if (Status.equals("true")) {
			    CStatus = "red";
			    TStatus = "Failed";
			    // Property.TCFailed+=1;
			    property.setTcFailed(property.getTcFailed() + 1);
			    suiteFailCount += 1;
			} else {
			    CStatus = "green";
			    TStatus = "Passed";
			    suitePassCount += 1;
			    // Property.TCPassed+=1;
			    property.setTcPassed(property.getTcPassed() + 1);
			}
			Html_Execution_String = Html_Execution_String
			        + "<td width='8%' style='font-weight: bold; font-size: 12px; font-style:normal; '><span id='span_id' style='color: " + CStatus
			        + ";'>&nbsp;" + TStatus + "</span></td>";
			Html_Execution_String = Html_Execution_String
			        + "<td width='8%' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + failedStep
			        + "</td>";
			Html_Execution_String = Html_Execution_String
			        + "<td width='10%' style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;" + "<a href='"
			        + TestCaseID + ".html' target='mainFrame' >Result</a>" + "</td></tr>";
		    }
		}

		Html_Suite_String = Html_Suite_String + "<tr style='font-family: Arial; background-color: #FFF8C6'>"
		        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + suiteCount + "</td>"
		        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + key + "</td>"
		        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + keyArray.size() + "</td>"
		        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + suitePassCount + "</td>"
		        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;" + suiteFailCount + "</td>"
		        + "<td style='font-weight: normal; font-size: 12px; font-style:normal; color: black'>&nbsp;&nbsp;"
		        + InetAddress.getLocalHost().getHostName() + "</td>";

		suiteCount += 1;
	    }

	    Html_Execution_String = Html_Execution_String + "</table></body></html>";

	    Html_Suite_String = Html_Suite_String + "</tr></table>";

	    File filelist = new File(System.getProperty("user.dir") + property.test_Report_Logs_Path + "ExecutionList.html");
	    File fileReport = new File(System.getProperty("user.dir") + property.test_Report_Logs_Path + "AutomationReport.html");
	    File fileMainFrame = new File(System.getProperty("user.dir") + property.test_Report_Logs_Path + "main.html");

	    try {
		if (filelist.exists()) {
		    filelist.delete();

		}
		if (fileReport.exists()) {
		    fileReport.delete();
		}
		if (fileMainFrame.exists()) {
		    fileMainFrame.delete();
		}

		fileMainFrame.createNewFile();
		filelist.createNewFile();
		fileReport.createNewFile();

		replaceHtmlContent();

		FileWriter ReportWriter = new FileWriter(fileReport);
		ReportWriter.write(Automation_Report_String);
		ReportWriter.close();

		FileWriter MainFrameWriter = new FileWriter(fileMainFrame);
		MainFrameWriter.write(Main_Frame_String);
		MainFrameWriter.close();

		FileWriter fw = new FileWriter(filelist.getAbsoluteFile());
		fw.write(Html_Summary_String);
		fw.write(Html_Suite_String);
		fw.write(Html_Execution_String);
		fw.close();
		if (property.IsRemoteExecution.equalsIgnoreCase("true") && property.localGrid.equalsIgnoreCase("false")) {
		    // saveAutomationReportOnJenkins();
		    saveTestExecutionReportOnJenkins();
		    updateHistoryReport();
		    // SaveTestNGXML();
		}

	    } catch (NoSuchFileException x) {
		System.err.format("%s: No such" + " file or directory%n", property.test_Report_Logs_Path);
	    }
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}

    }

    private void saveTestExecutionReportOnJenkins() {
	// Code for copying HTML reports
	File jfile = new File(property.expectedImageJenkinPath, "Test_Report" + property.FileSeperator.get() + utility.getDate() + property.FileSeperator.get()
	        + Property.getExecutionTime());

	File file = new File(System.getProperty("user.dir") + property.test_Report_Logs_Path);
	try {
	    FileUtils.copyDirectory(file, jfile);
	} catch (IOException e) {
	    objLog.writeInfo(e.toString());
	}
	// Code for Copying TestNG.xml file
	File xMLFile = new File(System.getProperty("user.dir") + property.FileSeperator.get() + property.testNGXMLFileLocation);

	try {
	    FileUtils.copyFileToDirectory(xMLFile, jfile);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// Code for copying log file
	File logFile = new File(System.getProperty("user.dir") + property.FileSeperator.get() + property.LogFileName);

	try {
	    FileUtils.copyFileToDirectory(logFile, jfile);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private void updateHistoryReport() {
	try {
	    File file = new File(property.expectedImageJenkinPath + "Test_Report" + property.FileSeperator.get() + "History_Report.html");
	    objLog.writeInfo(file.getAbsolutePath());
	    if (!file.exists()) {
		if (file.createNewFile()) {
		    objLog.writeInfo("New File Created");
		    text = text
			    + " <font style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black;'>Previous Execution Report Links</font>"
			    + "<table width='80%' style='border:1px solid #8E001D'>"
			    + "<tr align='center' style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
			    + "<td width='15%'>Job Name</td>"
			    + "<td width='30%'>Links</td>"
			    + "<td width='10%'>Total Executed</td>"
			    + "<td width='10%'>Passed</td>"
			    + "<td width='10%'>Failed</td>"
			    + "<td width='15%'>Time Duration</td>"
			    + "</tr>"
			    + "<tr align='center' style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
			    + "<td><a target ='_blank' " + "href='" + property.forgeURL + "Test_Report/" + utility.getDate() + "/"
			    + Property.getExecutionTime() + "/AutomationReport.html'>" + Property.getExecutionTime() + "</a></td>" + "</td>"
			    + "<td>$TCCount</td>" + "<td>$PASSEDCOUNT</td>" + "<td>$FAILCount</td>" + "<td>$TOTALTIME</td>" + "</tr>";
		} else {
		    objLog.writeInfo("Unable to create file:");
		}
	    } else {
		objLog.writeInfo("File Exists");
		FileReader fileReader = new FileReader(file.getAbsolutePath());
		BufferedReader bufferReader = new BufferedReader(fileReader);
		String line = "";
		// String text = "";
		while ((line = bufferReader.readLine()) != null) {
		    // objLog.writeInfo(" line: " + line);
		    String tx[] = line.split("</tr>");

		    for (int j = 0; j <= tx.length - 1; j++) {
			// objLog.writeInfo(j + " tx: " + tx[j]);
			if (j == 0) {
			    text = text
				    + tx[j]
				    + "</tr><tr align='center' style='font-weight: bold; font-size: 14px; font-style:normal; font-family: Arial; color: black; background-color: #f3f3f3'>"
				    + "<td width='15%'>$JOBNAME</td>" + "<td width='30%'><a target ='_blank' " + "href='" + property.forgeURL + "Test_Report/"
				    + utility.getDate() + "/" + Property.getExecutionTime() + "/AutomationReport.html'>" + Property.getExecutionTime()
				    + "</a></td>" + "<td width='10%'>$TCCount</td>" + "<td width='10%'>$PASSEDCOUNT</td>" + "<td width='10%'>$FAILCOUNT</td>"
				    + "<td width='15%'>$TOTALTIME</td>" + "</tr>";
			} else {
			    text = text + tx[j];
			}
		    }
		}
		replaceHtmlContent();
		bufferReader.close();
		fileReader.close();
		FileWriter fileWritter = new FileWriter(file.getAbsolutePath());
		fileWritter.write(text);
		fileWritter.close();

	    }
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

    private void SaveTestNGXML() {
	try {

	    File TestNG = new File(property.testNGXMLFileLocation);
	    File Log4j = new File("Logs" + property.FileSeperator + property.LogFileName);
	    File jenkinLog4jPath = new File(property.expectedImageJenkinPath + "Test_Report" + property.FileSeperator.get() + utility.getDate()
		    + property.FileSeperator.get() + Property.getExecutionTime() + property.FileSeperator + property.LogFileName);
	    File jenkinTestNGPath = new File(property.expectedImageJenkinPath + "Test_Report" + property.FileSeperator.get() + utility.getDate()
		    + property.FileSeperator.get() + Property.getExecutionTime() + property.FileSeperator + property.testNGXMLFileLocation);

	    FileUtils.copyFile(Log4j, jenkinLog4jPath);
	    FileUtils.copyFile(TestNG, jenkinTestNGPath);

	} catch (Exception e) {
	    objLog.writeInfo("Unable to Save TestNG XML or Log4j File " + e);
	}

    }

    private void saveAutomationReportOnJenkins() {

	try {
	    File jfilelist = new File(property.expectedImageJenkinPath + "Test_Report" + property.FileSeperator.get() + utility.getDate()
		    + property.FileSeperator.get() + Property.getExecutionTime() + property.FileSeperator.get() + "ExecutionList.html");
	    File jfileReport = new File(property.expectedImageJenkinPath + "Test_Report" + property.FileSeperator.get() + utility.getDate()
		    + property.FileSeperator.get() + Property.getExecutionTime() + property.FileSeperator.get() + "AutomationReport.html");
	    File jfileMainFrame = new File(property.expectedImageJenkinPath + "Test_Report" + property.FileSeperator.get() + utility.getDate()
		    + property.FileSeperator.get() + Property.getExecutionTime() + property.FileSeperator.get() + "main.html");

	    if (jfilelist.exists()) {
		jfilelist.delete();
	    } else {
		if (!jfilelist.getParentFile().exists()) {
		    jfilelist.getParentFile().mkdirs();
		}
	    }
	    if (jfileReport.exists()) {
		jfileReport.delete();
	    }
	    if (jfileMainFrame.exists()) {
		jfileMainFrame.delete();
	    }

	    jfileMainFrame.createNewFile();
	    jfilelist.createNewFile();
	    jfileReport.createNewFile();

	    FileWriter jReportWriter = new FileWriter(jfileReport);
	    jReportWriter.write(Automation_Report_String);
	    jReportWriter.close();

	    FileWriter jMainFrameWriter = new FileWriter(jfileMainFrame);
	    jMainFrameWriter.write(Main_Frame_String);
	    jMainFrameWriter.close();

	    FileWriter jfw = new FileWriter(jfilelist.getAbsoluteFile());
	    jfw.write(Html_Summary_String);
	    jfw.write(Html_Suite_String);
	    jfw.write(Html_Execution_String);
	    jfw.close();

	} catch (NoSuchFileException x) {
	    System.err.format("%s: No such" + " file or directory%n", property.test_Report_Logs_Path);
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

    private void replaceHtmlContent() {

	try {
	    SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
	    Html_Summary_String = Html_Summary_String.replace("$TCCount", String.valueOf((property.getTcFailed() + property.getTcPassed())));
	    Html_Summary_String = Html_Summary_String.replace("$PASSEDCOUNT", property.getTcPassed().toString());
	    Html_Summary_String = Html_Summary_String.replace("$FAILCOUNT", property.getTcFailed().toString());

	    Date JobStartTime = format.parse(property.getJobStartTime());
	    Date JobEndTime = format.parse(property.getJobEndTime());
	    long diff = (JobEndTime.getTime() - JobStartTime.getTime());
	    long diffSeconds = diff / 1000 % 60;
	    long diffMinutes = diff / (60 * 1000) % 60;
	    long diffHours = diff / (60 * 60 * 1000) % 24;

	    Html_Summary_String = Html_Summary_String.replace("$TOTALTIME",
		    String.valueOf(diffHours + " Hours : " + diffMinutes + " mins : " + diffSeconds + " sec"));
	    Main_Frame_String = Main_Frame_String.replace("$HISTORYREPORT", property.forgeURL + "Test_Report/" + property.HistoryReport + ".html");
	    Main_Frame_String = Main_Frame_String.replace("$DATETIME", Property.getExecutionTime());
	    text = text.replace("$TCCount", String.valueOf((property.getTcFailed() + property.getTcPassed())));
	    text = text.replace("$PASSEDCOUNT", property.getTcPassed().toString());
	    text = text.replace("$FAILCOUNT", property.getTcFailed().toString());
	    text = text.replace("$TOTALTIME", String.valueOf(diffHours + " Hours : " + diffMinutes + " mins : " + diffSeconds + " sec"));
	    text = text.replace("$JOBNAME", System.getProperty("user.dir").substring(System.getProperty("user.dir").lastIndexOf("/") + 1));
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }
}
