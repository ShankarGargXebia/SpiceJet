package com.tribune.uiautomation.testscripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import Common.Property;
import Common.TCStatusMap;
import Common.Utility;
import dataReader.ReaderUtility;
import io.appium.java_client.AppiumDriver;
import reporting.BddStepCreationControler;
import reporting.HTMLTestCaseCreation;
import reporting.LogFile;
import reporting.ReportCreation;
import testDriver.Actions;

/**
 * This class has JUnit methods like @Before, @Test. When we run our project as
 * jUnit test, then this is the class that gets executed first.
 * 
 */
public class TestEngine {

    public LogFile objLog;
    Boolean isAnyStepFailed;
    public Actions objAction;
    private ReaderUtility objReader;
    public String StepAction;
    public Property property;
    public Utility utility;
    /*
     * An instance of LogFile to create a log file for every test suite run (Ex.
     * SearchTestSuite_Wed Apr 16 140047 IST 2014.txt)
     */
    // List to contain TestCase Ids that will be executed.
    public ArrayList<String> testCaseIDsForExecution;
    public ArrayList<String> suiteExecutionDetails;
    boolean isWriteStep;
    Boolean tCPassFail;
    int currentCount;
    BddStepCreationControler objController;
    private WebDriver driver;
    HTMLTestCaseCreation htmlReportobj;
    ReportCreation reportObj;
    private List<String[]> tcData, orData;

    // public static Map<String, ArrayList<String>> tCStatusMap;

    /**
    * 
    */
    public TestEngine() {

	// Creating Java variables
	isAnyStepFailed = false;
	testCaseIDsForExecution = new ArrayList<String>();
	suiteExecutionDetails = new ArrayList<>();
	isWriteStep = false;
	tCPassFail = false;

	// Creating class objects for classes within framework
	objLog = new LogFile();
	property = new Property();
	utility = new Utility(property);
	reportObj = new ReportCreation(property, utility);
	// tCStatusMap = new TreeMap<String, ArrayList<String>>();
	htmlReportobj = new HTMLTestCaseCreation(property, utility);
	currentCount = 0;

    }

    @BeforeSuite
    public void beforeSuit() {

	try {
	    // Set the Start time for the Job
	    property.setJobStartTime(utility.getCurrentDateAndTime());
	    Property.setExecutionTime(utility.getCDTTime());
	    objLog.writeInfo("Total Test Case allocated for execution: " + Property.ExecutionCount);
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
    }

    /**
     * @Before Method that is run by JUnit before @Test method. Populates Global
     *         hash map with the Key/Value given in uiautomation.properties file
     *         and initializes different variables defined in Property.java
     *         like: OSString, TestSuite, BrowserName, SyncTimeOut,
     *         IsRemoteExecution, IsSauceLabExecution, ApplicationURL,
     *         RemoteURL, BDD_FileFormat, BDD_FileName Also initializes
     *         variables required for BDDType reporting
     */
    @BeforeTest
    @Parameters({ "browser", "applicationURL", "AUT" })
    public void beforeTest(String browser, @Optional() String applicationURL, String aut) {
	try {
	    /*
	     * prop = System.getProperties(); prop.load(new
	     * FileInputStream(Property.PropertyFileLocation));
	     * Property.setBrowserName(browser); objLog.writeSysOut(
	     * "BrowserName got from XML is " +Property.getBrowserName());
	     */
	    utility.collectKeyValuePair();
	    utility.populateGlobalMap();
	    Properties prop = System.getProperties();// Get all the System prop
	    utility.addExternalProperties(prop);
	    property.setBrowserName(browser.toLowerCase());
	    String mavenString = System.getProperty("sun.java.command");
	    String generateImage = prop.getProperty("generateImage");
	    String breakPoint = prop.getProperty("breakPoint");
	    // read native platform values form command line
	    this.property.platform = prop.getProperty("platform");
	    this.property.platformVersion = prop.getProperty("platformVersion");
	    this.property.deviceName = prop.getProperty("deviceName");
	    this.property.orientation = prop.getProperty("orientation");

	    utility.fetchAndSaveExternalParameters(mavenString);
	    isWriteStep = true;
	    property.setOsString(System.getProperty("os.name") + " " + System.getProperty("os.version"));
	    property.aut = ((aut == null || aut.equalsIgnoreCase("")) ? utility.replaceVariableInString(utility.getValueFromGlobalVarMap("AUT")).toLowerCase()
	            : aut.toLowerCase());

	    switch (property.aut) {
	    case "web":
		property.TestSuite = "Web_Automation";
		break;
	    case "native":
		property.TestSuite = "Native_Automation";
		break;
	    default:
		property.TestSuite = "Web_Automation";
		break;
	    }

	    property.setApplicationURL("http://www.SpiceJet.com/");

	    // Below information is only for Web Test cases.
	    if (!this.property.getBrowserName().contains("native")) {
		// Assigning generateImage
		if (generateImage == null || generateImage.equals("")) {
		    property.generateImage = utility.getValueFromGlobalVarMap("generateImage");
		    objLog.writeInfo("Generating Images Feature Disabled.");
		} else {
		    objLog.writeInfo("Generate Images Feature Enabled");
		    property.generateImage = generateImage;
		}
		// Assigning breakPoint
		if (breakPoint == null || breakPoint.equals("")) {
		    property.breakPoint = getbreakPoint(utility.getValueFromGlobalVarMap("breakPoint"));
		} else {
		    property.breakPoint = getbreakPoint(breakPoint);
		}
		objLog.writeInfo("Break Point used: " + property.breakPoint);
	    }
	    // Below information is only for Native Test cases.
	    if (this.property.getBrowserName().contains("native") || this.property.getBrowserName().contains("web")) {
		// Assigning platform
		if (property.platform == null) {
		    property.platform = getNativeplatform();
		}
		// Assigning platformVersion
		if (property.platformVersion == null) {
		    property.platformVersion = getNativeplatformVersion();
		}
		// Assigning DeviceName
		if (property.deviceName == null) {
		    property.deviceName = getNativeDeviceName();
		}
		// Assigning DeviceName
		if (property.orientation == null) {
		    property.orientation = getNativeOrientation();
		}
		objLog.writeInfo("platform: " + property.platform);
		objLog.writeInfo("platformVersion: " + property.platformVersion);
		objLog.writeInfo("Device: " + property.deviceName);
		objLog.writeInfo("Orientation: " + property.orientation);
	    }
	    property.setBuildEnv(utility.replaceVariableInString(utility.getValueFromGlobalVarMap("buildEnv")));
	    property.SyncTimeOut = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("MaximumTimeout"));
	    property.IsRemoteExecution = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("IsRemoteExecution"));
	    property.IsSauceLabExecution = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("SaucelabExecution"));
	    // objLog.writeSysOut("Application url is :
	    // "+Property.getApplicationURL());
	    property.RemoteURL = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("RemoteUrl"));
	    property.localGrid = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("localGrid"));

	    // variable which used in curtrequired implementation of ios
	    property.iphonePortraitCropFromTop = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("iphonePortraitCropFromTop"));
	    property.iphonePortraitCropFromBottom = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("iphonePortraitCropFromBottom"));
	    property.iphoneLandscapeCropFromTop = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("iphoneLandscapeCropFromTop"));
	    property.otherDeviceCropFromTop = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("otherDeviceCropFromTop"));
	    property.trbHeader = utility.replaceVariableInString(utility.getValueFromGlobalVarMap("trbHeader"));
	    property.maxTimeOut = Integer.parseInt(utility.replaceVariableInString(utility.getValueFromGlobalVarMap("MaximumTimeout")));
	    property.maxTimeOutiOS = Integer.parseInt(utility.replaceVariableInString(utility.getValueFromGlobalVarMap("MaximumTimeoutiOS")));
	    property.dbConnString = utility.getValueFromGlobalVarMap("db.conn.string");
	    property.dbName = utility.getValueFromGlobalVarMap("db.name");
	    property.dbUsername = utility.getValueFromGlobalVarMap("db.username");
	    property.dbPassword = utility.getValueFromGlobalVarMap("db.password");
	    property.setScreenShotCounter(0);
	    // Property.setBuildEnvFileStruc(createEnvfolderstructure());
	    objLog.writeInfo("Remote execution is " + this.property.IsRemoteExecution);
	} catch (Exception e) {
	    objLog.writeInfo(e.getMessage());
	}
    }

    /**
     * Fetches OR data based on 'parent' and 'testobject'
     * 
     * @param parent
     *            refers to 'page'
     * @param testObject
     *            refers to logical name of 'locator'
     * @return HashMap containing parent, testobject, how, what related to
     *         locator info specified in a row in OR sheet.
     * @throws Exception
     */
    public HashMap<String, String> getORData(String parent, String testObject) throws Exception {
	HashMap<String, String> objDef = new HashMap<String, String>();

	String[] splitTestObject = testObject.split("#");

	try {
	    int rowCount = orData.size();
	    int iterativeRow = 0;
	    while (iterativeRow < rowCount) {
		if (objReader.getCellValue(orData, iterativeRow, "parent").equals(parent)
		        && objReader.getCellValue(orData, iterativeRow, "testObject").equals(splitTestObject[0])) {
		    String how = objReader.getCellValue(orData, iterativeRow, "how");
		    String what = objReader.getCellValue(orData, iterativeRow, "what");
		    how = utility.replaceVariableInString(how);
		    what = utility.replaceVariableInString(what);

		    objDef.put("parent", parent);
		    objDef.put("testObject", splitTestObject[0]);
		    objDef.put("how", how);
		    objDef.put("what", what);
		    try {
			objDef.put("nthElement", splitTestObject[1]);
		    } catch (ArrayIndexOutOfBoundsException e) {
			objDef.put("nthElement", "0");
		    }

		    break;
		}
		iterativeRow++;
	    }
	    return objDef;

	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * Executes given testcaseid. Once it finds the matching TestCaseID in test
     * case sheet, it executes all the steps defined in that case in the
     * testcase sheet.
     * 
     * @param TestCaseID
     *            Text Value of TestCaseID that needs to be executed.
     * @param reusableFlag
     *            boolean value that determines which file to be used for
     *            reading testcase and testdata info.
     * @throws Exception
     */
    public ArrayList<String> ExecuteTestCase(String TestCaseID, boolean reusableFlag) throws Exception {
	ArrayList<String> TestCase = new ArrayList<String>();
	try {
	    String failedstep = "none";
	    String TestCaseLine = "";
	    String TestCaseLog = "";
	    if (!reusableFlag)
		TestCaseLog = TestCaseID;
	    objAction = null;
	    Boolean IsTestidFound = false;
	    int contentRow = 0;
	    int testCaseRowCount = tcData.size();

	    while (contentRow < testCaseRowCount) {
		String testid = "";

		try {
		    testid = objReader.getCellValue(tcData, contentRow, "testcase_id");
		} catch (NullPointerException ne) {
		    testid = "";
		}

		if (testid.equalsIgnoreCase(TestCaseID)) {
		    IsTestidFound = true;
		}

		if (IsTestidFound) {
		    /*
		     * Check if 'testid' is not blank but it is not equal to
		     * given 'TestCaseID'
		     */
		    if (!testid.trim().equals("") && !testid.equals(TestCaseID)) {
			IsTestidFound = false;
			TestCase.add(failedstep + "!" + isAnyStepFailed.toString());
			return TestCase;

		    } else {
			property.setStepStatus("");
			String BDD_Step = "";
			String parent = "";
			String testObject = "";
			String DataContent = "";
			String StepAction = "";
			String options = "";
			/*
			 * Read values of parent, testobject, data, stepaction,
			 * options and step from the row where we have found
			 * presence of given 'TestCaseID'
			 */
			try {
			    BDD_Step = objReader.getCellValue(tcData, contentRow, "step");
			} catch (NullPointerException ne) {
			    ne.getStackTrace();
			}
			try {
			    parent = objReader.getCellValue(tcData, contentRow, "parent");
			} catch (NullPointerException ne) {
			    ne.getStackTrace();
			}
			try {
			    testObject = objReader.getCellValue(tcData, contentRow, "testObject");
			} catch (NullPointerException ne) {
			    ne.getStackTrace();
			}
			try {
			    DataContent = objReader.getCellValue(tcData, contentRow, "data");
			} catch (NullPointerException ne) {
			    ne.getStackTrace();
			}
			try {
			    StepAction = objReader.getCellValue(tcData, contentRow, "stepaction");
			} catch (NullPointerException ne) {
			    ne.getStackTrace();
			}
			try {
			    options = objReader.getCellValue(tcData, contentRow, "options");
			} catch (NullPointerException ne) {
			    ne.getStackTrace();
			}

			DataContent = utility.replaceVariableInString(DataContent);
			parent = utility.replaceVariableInString(parent);
			testObject = utility.replaceVariableInString(testObject);
			options = utility.replaceVariableInString(options);

			objLog.writeInfo("Page : " + parent);
			objLog.writeStepLogBeforeStep(TestCaseID, StepAction, property.getStepStatus(), property.getRemarks(), BDD_Step, isWriteStep);
			objAction = new Actions(driver, utility, property);
			objAction.objDataRow = getORData(parent, testObject);

			BDD_Step = utility.replaceVariableInString(BDD_Step);

			try {
			    if (objAction.objDataRow.isEmpty()) {
				property.setRemarks("Element Locator not present in Object Repository");
				property.setStepStatus(property.FAIL);
			    } else {
				driver = objAction.DO(driver, StepAction, DataContent, parent, testObject, options);
			    }
			} catch (Exception e) {
			    if (e.getMessage().equalsIgnoreCase("No Such Action")) {

				/*
				 * If datacontent would have contained {$var}
				 * then it should have replaced so checking for
				 * arguments only.
				 */
				if (DataContent != null) {
				    if (DataContent.contains("{") && DataContent.contains("}")) {
					String[] arguments = DataContent.split(",");
					for (int i = 0; i <= arguments.length - 1; i++) {
					    arguments[i] = arguments[i].replace("{", "");
					    arguments[i] = arguments[i].replace("}", "");
					    utility.setKeyValueInGlobalVarMap("argument" + i, arguments[i]);
					}
				    }
				}

				ExecuteTestCase(StepAction, true);
				/*
				 * Need to loook if arguments need to be
				 * deleted.
				 */
				isWriteStep = false;
			    }
			}

			objLog.writeStepLogAfterStep(TestCaseID, StepAction, property.getStepStatus(), property.getRemarks(), BDD_Step, isWriteStep);
			TestCaseLine = TestCaseLog + "!!" + StepAction + "!!" + BDD_Step + "!!" + property.getStepStatus() + "!!" + property.getRemarks()
			        + "!!" + property.getStepExecutionTime();
			TestCase.add(TestCaseLine);
			isWriteStep = true;
			TestCaseLog = "";

			if (property.getStepStatus() == property.FAIL) {
			    failedstep = StepAction;
			    isAnyStepFailed = true;
			    break;
			} else {
			    isAnyStepFailed = false;
			}

		    }
		}

		contentRow++;
	    }
	    TestCase.add(failedstep + "!" + isAnyStepFailed.toString());

	} catch (NullPointerException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}
	return TestCase;

    }

    /**
     * Executes all test cases or list of test cases specified by user.
     * 
     * @throws Exception
     */
    @org.testng.annotations.Test
    @Parameters({ "TestGroup", "TestCaseID" })
    @DataProvider
    public void Execution(String TestGroup, String TestCaseID, ITestContext context) throws Exception {
	ArrayList<String> TCDetails;
	String SuiteName = context.getCurrentXmlTest().getName();

	try {
	    /*
	     * objLog.writeSysOut("TestCase need to execute : " + TestCaseID +
	     * " for Thread " + Thread.currentThread().getId());
	     * objLog.writeSysOut("TestGroups need to execute : " + TestGroup +
	     * " for Thread " + Thread.currentThread().getId());
	     */
	    String TestSuite = utility.getValueFromGlobalVarMap("TestSuite");
	    if (TestGroup.equalsIgnoreCase("") && TestCaseID.equalsIgnoreCase("")) {
		TestGroup = utility.getValueFromGlobalVarMap("TestGroups");
		TestCaseID = utility.getValueFromGlobalVarMap("TestCases");
	    }
	    if (TestGroup != "") {
		objLog.writeInfo("TestGroup is: " + TestGroup);
	    }
	    String LogFileName = TestSuite + "_" + utility.getCurrentTimeStamp() + ".log";// Log
	    // file
	    // name
	    property.LogFileName = property.LogFile.replace("{0}", LogFileName);// Log
	    // Log file saving location
	    objLog.prepareLogger(property.LogFileName);
	    // objLog.prepareHeader();

	    objReader = new ReaderUtility(property);
	    /*
	     * Set the location of all the external files (TestCase, TestData &
	     * ObjectRepository) needed for the framework.
	     */
	    objReader.setFileLocation(false);
	    // Get OR Data, TestCase Data and Test Data in tabular format
	    orData = objReader.getORData();
	    tcData = objReader.getTestCaseData();

	    /*
	     * If TestSuite is not null and user has not specified anything in
	     * TestCase in uiautomation.properties then read all rows of
	     * TestCase sheet and add all Test Case Ids to list
	     * 'TestCaseIDsForExecution'
	     */
	    if (!(TestSuite == null) && (TestCaseID == null || TestCaseID == "")) {
		/*
		 * ITable rs = objReader.getTestCaseData(); int tcRowCount =
		 * rs.getRowCount(); int tcIndex = 0; while(tcIndex <
		 * tcRowCount){ Object tid = rs.getValue(tcIndex,
		 * "testcase_id"); if(!(tid == null)){
		 * TestCaseIDsForExecution.add(tid); } tcIndex++; }
		 */
	    }
	    // User has specified comma separated values of TestCaseIDs
	    else if (!(TestSuite == null) && (TestCaseID.contains(","))) {
		String[] TCids = TestCaseID.split(",");
		for (String ID : TCids) {
		    ID = ID.trim();
		    testCaseIDsForExecution.add(ID);
		}
	    }
	    // User has specified just one TestCaseID
	    else {
		testCaseIDsForExecution.add(TestCaseID);
	    }
	    if (TestGroup != null && TestGroup != "") {
		TestGroup = TestGroup.trim();
		// User has specified comma separated values of TestGroups
		if (TestGroup.contains(",")) {
		    String[] TcGrps = TestGroup.split(",");
		    objLog.writeInfo("Test Groups after splitting are: " + TcGrps);
		    for (String Group : TcGrps) {
			Group = Group.trim();
			int tcRowCount = tcData.size();
			int tcIndex = 0;
			while (tcIndex < tcRowCount) {
			    try {
				String tGrp = objReader.getCellValue(tcData, tcIndex, "TestGroups").trim();
				objLog.writeInfo("TestGroup from Excel is :" + tGrp);
				System.out.println("TestGroup from Prop file is :" + Group.toString());
				/*
				 * Check if the testGroups from Excell file
				 * contains comma separated values
				 */
				if (tGrp.contains(",")) {
				    String[] TCGrpofID = tGrp.split(",");

				    for (String Grp : TCGrpofID)
					Grp = Grp.trim();
				    List<String> TCGrpofIDList = Arrays.asList(TCGrpofID);
				    for (String Grp : TCGrpofIDList) {
					Grp = Grp.trim();
					if (Grp.equalsIgnoreCase(Group)) {
					    String tid = objReader.getCellValue(tcData, tcIndex, "testcase_id");
					    if (!(tid == null)) {
						testCaseIDsForExecution.add(tid.toString());
					    }
					}
				    }
				} else if (tGrp.trim().equalsIgnoreCase(Group)) {
				    String tid = objReader.getCellValue(tcData, tcIndex, "testcase_id");
				    if (!(tid == null)) {
					testCaseIDsForExecution.add(tid.toString());
				    }
				}
			    } catch (NullPointerException e) {
			    }

			    tcIndex++;
			}
		    }
		}
		// User has specified just one TestGroup
		else {
		    int tcRowCount = tcData.size();
		    int tcIndex = 0;
		    while (tcIndex < tcRowCount) {
			try {
			    String tGrp = objReader.getCellValue(tcData, tcIndex, "TestGroups");
			    /*
			     * Check if the testGroups from Excell contains
			     * comma separated values
			     */
			    if (tGrp.contains(",")) {

				String[] TCGrpofID = tGrp.split(",");
				List<String> TCGrpofIDList = Arrays.asList(TCGrpofID);
				for (String Grp : TCGrpofIDList) {
				    Grp = Grp.trim();
				    if (Grp.equalsIgnoreCase(TestGroup)) {
					String tid = objReader.getCellValue(tcData, tcIndex, "testcase_id");
					if (!(tid == null)) {
					    testCaseIDsForExecution.add(tid.toString());
					}
				    }
				}

			    } else if (tGrp.equalsIgnoreCase(TestGroup)) {
				String tid = objReader.getCellValue(tcData, tcIndex, "testcase_id");
				if (!(tid == null)) {
				    testCaseIDsForExecution.add(tid);
				}
			    }
			} catch (NullPointerException e) {
			}

			tcIndex++;
		    }
		}
	    }
	    /*
	     * objLog.writeSysOut("Testcase values are :" +
	     * TestCaseIDsForExecution);
	     */

	    for (int i = 0; i <= (testCaseIDsForExecution.size() - 1); i++) {
		property.setExecutionStartTime(utility.getCurrentDateAndTime());
		String CurrentTestCaseID = testCaseIDsForExecution.get(i);
		property.setTestName(CurrentTestCaseID);
		// String LogFileName = CurrentTestCaseID + "_" +
		// Utility.getCurrentTimeStamp() +".txt";
		// String LogFile = Property.LogFile.replace("{0}",
		// LogFileName);
		// objLog.prepareLogger(LogFile);
		objLog.writeInfo("Execution start for " + CurrentTestCaseID);

		TCDetails = ExecuteTestCase(CurrentTestCaseID, false);
		property.setExecutionEndTime(utility.getCurrentDateAndTime());

		suiteExecutionDetails.add(TestGroup + "!" + CurrentTestCaseID + "!" + property.getBrowserName() + "!" + property.breakPoint + "!"
		        + TCDetails.get(TCDetails.size() - 1));

		htmlReportobj.HTMLReport(TCDetails);
		currentCount += 1;
		objLog.writeInfo("Current Execution Count: " + currentCount + "/" + Property.ExecutionCount);
		objLog.writeInfo("Execution ends for " + CurrentTestCaseID);
	    }

	    property.setExecutionEndTime(utility.getCurrentDateAndTime());

	} catch (Exception e) {
	    objLog.writeInfo("Unknown Exception occured: " + e);
	    // ReportCreation.WriteLogMessage(e.getMessage());
	}

	if (isAnyStepFailed) {
	    /*
	     * If IsAnyStepFailed=true, Assert condition will fail and execution
	     * will stop
	     */
	    Assert.assertEquals(isAnyStepFailed, Boolean.FALSE);
	}

    }

    @AfterTest
    public void afterTest(ITestContext context) throws InterruptedException {
	try {
	    String SuiteName = context.getCurrentXmlTest().getName();
	    // tCStatusMap.put(SuiteName, suiteExecutionDetails);
	    TCStatusMap.settCStatusMap(SuiteName, suiteExecutionDetails);
	    if (property.getBrowserName().toLowerCase().contains("iphone") || property.getBrowserName().equalsIgnoreCase("ipad")
	            || property.getBrowserName().equalsIgnoreCase("ios")) {
		if (driver != null)
		    driver.quit();
	    } else if (!property.getBrowserName().contains("androidPhonePortrait") && !property.getBrowserName().equalsIgnoreCase("androidPhoneLandscape")
	            && !property.getBrowserName().equalsIgnoreCase("androidTabletPortrait")
	            && !property.getBrowserName().equalsIgnoreCase("androidTabletLandscape")) {
		if (driver != null) {
		    if (!this.property.getBrowserName().toLowerCase().contains("native")) {
			objAction.switchToMostRecentWindow();
			driver.close();
		    }
		}
	    } else if (this.property.getBrowserName().toLowerCase().contains("native")) {
		if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
		    ((AppiumDriver) driver).rotate(ScreenOrientation.PORTRAIT);
		}
		driver.quit();
	    }
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	}
	Thread.sleep(2000);
	// driver.quit();
	/*
	 * Removing all Thread specific variables, to make sure that the
	 * variables won't retain the values in next reused thread resources.
	 */
	property.removingAllThreadLocalVariable();
    }

    @SuppressWarnings("unused")
    private String createEnvfolderstructure() {

	String envFldList[] = property.getBuildEnv().split("\\.");
	String path = "";
	for (String tx : envFldList) {
	    path = path + property.FileSeperator.get() + tx;
	}
	return path + property.FileSeperator.get();
    }

    private String getbreakPoint(String bp) {
	String breakPoint = "";
	try {
	    if (bp.equalsIgnoreCase("mobile")) {
		breakPoint = "360,500";
	    } else if (bp.equalsIgnoreCase("420BP")) {
		breakPoint = "540,900";
	    } else if (bp.equalsIgnoreCase("840BP")) {
		breakPoint = "960,900";
	    } else if (bp.equalsIgnoreCase("1060BP")) {
		breakPoint = "1140,900";
	    } else if (bp.equalsIgnoreCase("1280BP")) {
		breakPoint = "1440,900";
	    } else if (bp.equalsIgnoreCase("3000BP")) {
		breakPoint = "1500,3000";
	    } else if (bp.equalsIgnoreCase("4500BP")) {
		breakPoint = "1500,4500";
	    } else if (bp.equalsIgnoreCase("maximize")) {
		breakPoint = "0,0";
	    }
	    return breakPoint;
	} catch (Exception e) {
	    objLog.writeInfo(e.toString());
	    return null;
	}

    }

    private String getNativeplatform() {
	String platform = "";
	if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("native")) {
	    platform = "ANDROID";
	} else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("native")) {
	    platform = "iOS";
	} else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("web")) {
	    platform = "ANDROID";
	}
	return platform;
    }

    private String getNativeplatformVersion() {
	String platformVersion = "";
	if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("native")) {
	    platformVersion = "5.0";
	} else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("native")) {
	    platformVersion = "9.0";
	} else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("web")) {
	    platformVersion = "5.0";
	}
	return platformVersion;
    }

    private String getNativeDeviceName() {
	String deviceName = "";
	if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("native")) {
	    deviceName = "AndroidPhone";
	} else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("native")) {
	    if (this.property.getBrowserName().toLowerCase().contains("phone")) {
		deviceName = "iPhone 6";
	    } else if (this.property.getBrowserName().toLowerCase().contains("tablet")) {
		deviceName = "iPad Air";
	    }
	} else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("web")) {
	    deviceName = "AndroidPhone";
	}
	return deviceName;
    }

    private String getNativeOrientation() {
	return "PORTRAIT";
    }

    @AfterSuite
    public void aftersuite1() {
	// long time = tr.getEndMillis() - tr.getStartMillis();
	if (driver != null)
	    driver.quit();
    }

    @AfterSuite
    public void afterSuit() throws Exception {

	for (String k : TCStatusMap.gettCStatusMap().keySet()) {

	    // ArrayList<String> tt = tCStatusMap.get(k);
	    ArrayList<String> tt = TCStatusMap.gettCStatusMapValue(k);
	    for (int i = 0; i <= (tt.size() - 1); i++) {
		objLog.writeInfo(tt.get(i));
	    }
	}

	property.setJobEndTime(utility.getCurrentDateAndTime());

	// Generating consolidated HTML Report
	// reportObj.prepareHtmlContent(tCStatusMap);
	reportObj.prepareHtmlContent(TCStatusMap.gettCStatusMap());
    }
}