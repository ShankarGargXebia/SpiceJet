/**
 * 
 */
package com.tribune.uiautomation.testscripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dbunit.dataset.DataSetException;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import Common.Property;
import Common.Utility;
import dataReader.ReaderUtility;
import reporting.LogFile;

/**
 * @author Hemasundar
 * 
 */
public class GenerateXML {

    public LogFile objLog;
    XMLOutputFactory factory;
    XMLStreamWriter writer;
    private String[] indTagNames;
    private ReaderUtility objReader;
    public ArrayList<String> TestCaseIDsForExecution;
    private String[] indBrowsers;
    private List<String> indBrowsersasList;
    private List<String> indTagNamesasList;
    public Utility utility;
    public Property property;

    /**
    * 
    */
    public GenerateXML() {

	// Creating class objects for classes within framework
	objLog = new LogFile();
	property = new Property();
	utility = new Utility(property);

	// Creating Java variables
	TestCaseIDsForExecution = new ArrayList<String>();
    }

    @Test
    public void name() throws Exception {

	// Loading uiautomation.properties file into prop
	utility.collectKeyValuePair();
	/*
	 * Populate the values of uiautomation.properties values into a hashmap
	 * (globalVarMap). H=This HashMap is initialised in Properties class
	 */
	utility.populateGlobalMap();
	// Get all the System properties into a new properties object
	Properties prop = System.getProperties();
	/*
	 * Add the properties into the globalVarMap HashMap. Now it has
	 * uiautomation.properties & System properties
	 */
	utility.addExternalProperties(prop);

	String test = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("AUT"));
	property.aut = ((test == null) ? "" : test.toLowerCase());

	switch (property.aut) {
	case "web":
	    property.TestSuite = "Tribune_Web_Automation";
	    break;
	case "ct":
	    property.TestSuite = "CT_Native_Automation";
	    break;
	case "rest":
	    property.TestSuite = "REST_Services_Automation";
	    break;
	default:
	    property.TestSuite = "Tribune_Web_Automation";
	    break;
	}

	objReader = new ReaderUtility(property);
	Properties prop1 = System.getProperties();
	/*
	 * prop1.setProperty("testName", "test1,test2");
	 * prop1.setProperty("testGroup", "VerifyOutFit250,VerifyOutFit600");
	 * prop1.setProperty("browser", "firefox,Chrome");
	 */

	/*
	 * prop.setProperty("testGroups",
	 * "Outfits:chromeTag,firefoxTag:chrome,firefox");
	 * prop.setProperty("browser", "firefox");
	 */
	String rawTestGroup = prop1.getProperty("testGroups");
	String commonBrowser = prop1.getProperty("browser");
	String isParallel = prop1.getProperty("isParallel");
	if (isParallel == null) {
	    isParallel = "";
	}

	// if (rawTestGroup == null || commonBrowser == null || testName ==
	// null) {
	if (rawTestGroup == null) {
	    System.out.println("User haven't specified the parameters for execution. So executing the TestNG.xml generated in last execution");
	} else {

	    factory = XMLOutputFactory.newInstance();
	    writer = factory.createXMLStreamWriter(new FileOutputStream(property.testNGXMLFileLocation));
	    writer.writeStartDocument();
	    writer.writeStartElement("suite");
	    writer.writeAttribute("name", "Parallel Tests");
	    writer.writeAttribute("verbose", "1");
	    if (isParallel.equalsIgnoreCase("true")) {
		writer.writeAttribute("thread-count", "30");
		writer.writeAttribute("parallel", "tests");
	    }
	    writer.writeStartElement("tests");
	    /*
	     * // User has specified comma separated values of TestGroups if
	     * (testGroup.contains(",")) { String[] testGroups =
	     * testGroup.split(","); String[] browsers = browser.split(",");
	     * String[] testNames = testName.split(",");
	     * 
	     * for (int i = 0; i < testGroups.length; i++) {
	     * 
	     * testGroups[i] = testGroups[i].trim(); browsers[i] =
	     * browsers[i].trim(); testNames[i] = testNames[i].trim();
	     * createTestTag(testNames[i], browsers[i], testGroups[i], ""); } }
	     * // User has specified just one TestGroup else {
	     * createTestTag(testName, browser, testGroup, ""); }
	     */
	    if (rawTestGroup != null) {
		String[] testGroups = rawTestGroup.split(";");
		for (String indRawTestGroup : testGroups) {
		    indRawTestGroup = indRawTestGroup.trim();
		    createTagsForSingleTestGroup(indRawTestGroup, commonBrowser);
		}
	    }
	    writer.writeEndElement();
	    writer.writeEndElement();
	    writer.writeEndDocument();

	    writer.flush();
	    writer.close();
	    Thread.sleep(3000);

	    formatXMLFile(property.testNGXMLFileLocation);

	}
	executingXML(property.testNGXMLFileLocation);
    }

    /**
     * @param prop1
     * @param commonBrowser
     * @throws Exception
     * @throws DataSetException
     * @throws XMLStreamException
     */
    private void createTagsForSingleTestGroup(String rawTestGroup, String commonBrowser) throws Exception, XMLStreamException {

	String[] sample = rawTestGroup.split(":");
	String TestGroup = sample[0];
	String testTagNames = sample[1];
	String browsers = null;
	try {
	    browsers = sample[2];
	} catch (Exception e1) {
	    objLog.writeInfo("No TestGroup specific browsers are mentioned");
	}

	if (rawTestGroup.contains(",")) {
	    if (browsers != null) {
		indBrowsers = browsers.split(",");
		indBrowsersasList = Arrays.asList(indBrowsers);
	    } else if (browsers == null && commonBrowser != null) {
		indBrowsersasList = new ArrayList<String>();
		indBrowsersasList.add(commonBrowser);
	    } else {
		// get default browser from UIAutomation.properties
	    }
	    if (testTagNames != null) {
		indTagNames = testTagNames.split(",");
		indTagNamesasList = Arrays.asList(indTagNames);

		objLog.writeInfo("Test Groups are " + TestGroup);
		objLog.writeInfo("Browsers are " + browsers);
		objLog.writeInfo("Tag names are " + testTagNames);

	    }
	    // User has specified just one TestGroup
	    if (!(TestGroup == null)) {
		boolean reusableFlag = false;
		objReader.setFileLocation(reusableFlag);
		List<String[]> rs = objReader.getTestCaseData();
		int tcRowCount = rs.size();
		int tcIndex = 0;
		while (tcIndex < tcRowCount) {
		    try {
			String tGrp = objReader.getCellValue(rs, tcIndex, "TestGroups");
			/*
			 * Check if the testGroups from Excell contains comma
			 * separated values.
			 */
			String[] tCGrpofID = tGrp.split(",");
			List<String> tCGrpofIDAsList = Arrays.asList(tCGrpofID);
			for (String Grp : tCGrpofIDAsList) {
			    Grp = Grp.trim();
			    if (Grp.equalsIgnoreCase(TestGroup)) {
				String tid = objReader.getCellValue(rs, tcIndex, "testcase_id");
				if (!(tid == null)) {
				    TestCaseIDsForExecution.add(tid);
				}
			    }
			}
		    } catch (NullPointerException e) {
		    }
		    tcIndex++;
		}
	    }
	    // objLog.writeSysOut(TestCaseIDsForExecution);
	    Property.ExecutionCount = Property.ExecutionCount + TestCaseIDsForExecution.size();
	    int indBrowsersasListsize = indBrowsersasList.size();
	    System.out.println("Number of testCases per each tag for current testGroup is " + TestCaseIDsForExecution.size() / indBrowsersasListsize);

	    int noTcsperTag = TestCaseIDsForExecution.size() / indBrowsersasListsize;
	    int remainingTestCases = TestCaseIDsForExecution.size() % indBrowsersasListsize;
	    String TestCases = "";

	    for (int i = 0; i < indBrowsersasListsize; i++) {
		for (int j = 0; j < noTcsperTag; j++) {

		    TestCases = TestCases + "," + TestCaseIDsForExecution.get(0);
		    if (TestCases.startsWith(",")) {
			TestCases = TestCases.replace(",", "");
		    }
		    TestCaseIDsForExecution.remove(0);

		}
		if (remainingTestCases != 0) {
		    TestCases = TestCases + "," + TestCaseIDsForExecution.get(TestCaseIDsForExecution.size() - 1);
		    TestCaseIDsForExecution.remove(TestCaseIDsForExecution.size() - 1);
		    remainingTestCases--;
		}

		/*
		 * if (i == indBrowsersasListsize - 1) { for (int k = 0; k <
		 * remainingTestCases; k++) {
		 * 
		 * TestCases = TestCases + "," + TestCaseIDsForExecution.get(0);
		 * if (TestCases.startsWith(",")) { TestCases =
		 * TestCases.replace(",", ""); }
		 * TestCaseIDsForExecution.remove(0);
		 * 
		 * } }
		 */
		createTestTag(indTagNamesasList.get(i), indBrowsersasList.get(i), "", TestCases, String.valueOf(TestCases.split(",").length));
		TestCases = "";
	    }
	} else {
	    createTestTag(testTagNames, browsers, TestGroup, "", "");
	}

    }

    /**
     * 
     * @param xMLPath
     *            Pass the path of the XML file that needs to be executed
     */
    private void executingXML(String xMLPath) {
	TestListenerAdapter tla = new TestListenerAdapter();
	TestNG tng = new TestNG();
	List<String> suites = Lists.newArrayList();
	suites.add(xMLPath);// path to xml..
	tng.setTestSuites(suites);
	tng.addListener(tla);
	tng.run();
    }

    /**
     * @param TestCaseCount
     * @param testCase
     * @throws XMLStreamException
     */
    private void createTestTag(String testName, String browser, String TestGroup, String TestCaseID, String TestCaseCount) throws XMLStreamException {

	writer.writeStartElement("test");
	writer.writeAttribute("name", testName);
	writer.writeStartElement("parameters");

	writer.writeStartElement("parameter");
	writer.writeAttribute("name", "AUT");
	writer.writeAttribute("value", property.aut);
	writer.writeEndElement();

	writer.writeStartElement("parameter");
	writer.writeAttribute("name", "browser");
	writer.writeAttribute("value", browser.trim());
	writer.writeEndElement();

	writer.writeStartElement("parameter");
	writer.writeAttribute("name", "TestGroup");
	writer.writeAttribute("value", TestGroup);
	writer.writeEndElement();

	writer.writeStartElement("parameter");
	writer.writeAttribute("name", "TestCaseID");
	writer.writeAttribute("value", TestCaseID);
	writer.writeEndElement();

	writer.writeStartElement("parameter");
	writer.writeAttribute("name", "TestCaseCount");
	writer.writeAttribute("value", TestCaseCount);
	writer.writeEndElement();

	writer.writeEndElement();
	writer.writeStartElement("classes");
	writer.writeStartElement("class");
	writer.writeAttribute("name", "com.tribune.uiautomation.testscripts.TestEngine");
	writer.writeEndElement();
	writer.writeEndElement();
	writer.writeEndElement();
    }

    public void formatXMLFile(String file) throws Exception {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));

	Transformer xformer = TransformerFactory.newInstance().newTransformer();
	xformer.setOutputProperty(OutputKeys.METHOD, "xml");
	xformer.setOutputProperty(OutputKeys.INDENT, "yes");
	xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	Source source = new DOMSource(document);
	Result result = new StreamResult(new File(file));
	xformer.transform(source, result);
    }
}