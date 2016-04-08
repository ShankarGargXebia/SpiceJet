package Common;

import java.util.HashMap;

import reporting.LogFile;

/**
 * @author Property : All data content in framework.
 * 
 */
public class Property {
    public LogFile objLog;

    // Get the file separator as they may vary from environment to environment.
    public ThreadLocal<String> FileSeperator;
    public String testNGXMLFileLocation;
    // Name of sheet that has details about locators / Object Repository.
    public String ObjectRepositorySheet;
    // Name of sheet that details about test cases / test steps / test data.
    public String TestCaseSheet;

    public String localGrid;
    public String expectedImageJenkinPath;
    public String actualImageJenkinPath;

    // variable which used in curtrequired implementation of ios
    public String iphonePortraitCropFromTop;
    public String iphonePortraitCropFromBottom;
    public String iphoneLandscapeCropFromTop;
    public String otherDeviceCropFromTop;
    public String trbHeader;

    public static int ExecutionCount = 0;
    public String forgeURL;

    // Name of Test Data sheet
    public String TestDataSheet;

    private ThreadLocal<String> actualImageName;
    private ThreadLocal<String> screenshotName;

    public String HistoryReport;
    public String BDDFileQuery;
    // Dynamic JDBC Connection strings declarations.
    public String JDBCConnectionStringObjectRepository;

    public String JDBCConnectionStringTestCase;

    public String JDBCConnectionStringTestData;

    public String JDBCConnectionStringBDDFile;

    public String IsSauceLabExecution;

    public String generateImage;
    public String breakPoint;
    public String IsRemoteExecution;
    public String RemoteURL;
    public int maxTimeOut;
    public int maxTimeOutiOS;
    private static String executionTime;

    // Native App Automation properties
    public String platform;
    public String deviceName;
    public String platformVersion;
    public String orientation;

    // Name and location of Property file location (uiautomation.properties)
    public String PropertyFileLocation;

    // Name and location of Object Repository.
    public String ObjectRepositoryFileLocation;

    private ThreadLocal<String> browserName;

    public String TestSuite;

    public String aut;

    public String BDDFileName;

    /*
     * HashMap that will have data from uiautomation.properties. It will be
     * populated through Utility.populateGlobalMap() method.
     */
    private ThreadLocal<HashMap<String, String>> globalVarMap;

    // HashMap that will store Test Case Execution status, Test Case ID.
    // public static Map<String, ArrayList<String>> TCStatusMap=
    // new TreeMap<String, ArrayList<String>>();;

    public String REUSABLE_INVOKE_KEYWORD;

    public String REUSABLE_FILE_NAME;

    public String FILE_EXTENSION;

    public String IMAGE_TYPE;

    public ThreadLocal<Integer> screenShotCounter;

    /*
     * Location of test cases. Will be used in setFileLocation() method of
     * ReaderUtility.java
     */
    public String TESTCASE_LOC;

    private ThreadLocal<String> remarks;

    public final String PASS;

    public final String FAIL;

    public final String IGNORE;

    public final String SKIP;

    private ThreadLocal<Integer> tcPassed;

    private ThreadLocal<Integer> tcFailed;

    private ThreadLocal<String> stepStatus;

    private ThreadLocal<String> diffImage;

    private ThreadLocal<String> actualImage;
    private ThreadLocal<String> fullScreenShotImage;
    private ThreadLocal<String> expectedImage;
    private ThreadLocal<String> applicationURL;
    private ThreadLocal<String> buildEnv;
    private ThreadLocal<String> testName;

    public String SEPERATOR;
    private ThreadLocal<String> stepDescription;
    public String SyncTimeOut;
    public String LogFileName;
    public String LogFile;
    public String BDD_StepName;

    public String BDD_FileFormat;

    public String BDDFile_Location;
    public String BDD_FileName;
    public String BDD_ExcelSheet;

    public String BDD_FileExtension;

    private ThreadLocal<String> executionStartTime;

    private ThreadLocal<String> executionEndTime;

    public String JobStartTime;

    public String JobEndTime;

    public String test_Report_Logs_Path;

    private ThreadLocal<String> osString;

    public String MachineIp;

    private ThreadLocal<String> stepExecutionTime;

    private ThreadLocal<String> keyIgnoreStep;

    public String dbDB2Driver;

    public String dbPostgresDriver;
    public String dbOracleDriver;

    public String dbSQLDriver;
    public String dbConnString;
    public String dbName;
    public String dbUsername;
    public String dbPassword;
    public String dbQuery;

    public String buildEnvFileStruc;
    public String ScreenShotPath;

    public String getactualImage() {
	return actualImage.get();
    }

    /**
     * @return the actualImageName
     */
    public String getActualImageName() {

	return actualImageName.get();
    }

    /**
     * @return the fullScreenShotImage
     */
    public String getFullScreenShotImage() {
	return fullScreenShotImage.get();
    }

    /**
     * @return the applicationURL
     */
    public String getApplicationURL() {
	return applicationURL.get();
    }

    /**
     * @return the browserNameS
     */
    public String getBrowserName() {
	return browserName.get();
    }

    public String getBuildEnv() {
	return buildEnv.get();
    }

    public String getBuildEnvFileStruc() {
	return buildEnvFileStruc;
    }

    public String getdiffImage() {
	return diffImage.get();
    }

    /**
     * @return the executionEndTime
     */
    public String getExecutionEndTime() {
	return executionEndTime.get();
    }

    /**
     * @return the executionStartTime
     */
    public String getExecutionStartTime() {
	return executionStartTime.get();
    }

    public static String getExecutionTime() {
	return executionTime;
    }

    public String getexpectedImage() {
	return expectedImage.get();
    }

    /**
     * @return the globalVarMap
     */
    public HashMap<String, String> getGlobalVarMap() {
	return globalVarMap.get();
    }

    public String getJobEndTime() {
	return JobEndTime;
    }

    public String getJobStartTime() {
	return JobStartTime;
    }

    /**
     * @return the keyIgnoreStep
     */
    public String getKeyIgnoreStep() {
	return keyIgnoreStep.get();
    }

    /**
     * @return the osString
     */
    public String getOsString() {
	return osString.get();
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
	return remarks.get();
    }

    public Integer getScreenShotCounter() {
	return screenShotCounter.get();
    }

    public String getScreenshotName() {
	return screenshotName.get();
    }

    /**
     * @return the stepDescription
     */
    public String getStepDescription() {
	return stepDescription.get();
    }

    /**
     * @return the stepExecutionTime
     */
    public String getStepExecutionTime() {
	return stepExecutionTime.get();
    }

    /**
     * @return the stepStatus
     */
    public String getStepStatus() {
	return stepStatus.get();
    }

    /**
     * @return the tcFailed
     */
    public Integer getTcFailed() {
	return tcFailed.get();
    }

    /**
     * @return the tcPassed
     */
    public Integer getTcPassed() {
	return tcPassed.get();
    }

    /**
     * @return the testName
     */
    public String getTestName() {
	return testName.get();
    }

    public void removeactualImage() {
	actualImage.remove();
    }

    public void removeActualImageName() {
	actualImageName.remove();
    }

    public void removefullScreenShotImage() {
	fullScreenShotImage.remove();
    }

    public void removeApplicationURL() {
	applicationURL.remove();
    }

    public void removeBrowserName() {
	browserName.remove();
    }

    private void removeBuildEnv() {
	buildEnv.remove();

    }

    public void removediffImage() {
	diffImage.remove();
    }

    public void removeExecutionEndTime() {
	executionEndTime.remove();
    }

    public void removeExecutionStartTime() {
	executionStartTime.remove();
    }

    public void removeexpectedImage() {
	expectedImage.remove();
    }

    public void removeGlobalVarMap() {
	globalVarMap.remove();
    }

    public void removeKeyIgnoreStep() {
	keyIgnoreStep.remove();
    }

    public void removeOsString() {
	osString.remove();
    }

    public void removeRemarks() {
	remarks.remove();
    }

    public void removeScreenshotName() {
	screenshotName.remove();
    }

    public void removeStepDescription() {
	stepDescription.remove();
    }

    public void removeStepExecutionTime() {
	stepExecutionTime.remove();
    }

    public void removeStepStatus() {
	stepStatus.remove();
    }

    public void removeTcFailed() {
	tcFailed.remove();
    }

    public void removeTcPassed() {
	tcPassed.remove();
    }

    public void removeTestName() {
	testName.remove();
    }

    public void removingAllThreadLocalVariable() {
	removeactualImage();
	removeActualImageName();
	removeApplicationURL();
	removeBrowserName();
	removediffImage();
	removeExecutionEndTime();
	removeExecutionStartTime();
	removeexpectedImage();
	removeGlobalVarMap();
	removefullScreenShotImage();
	removeKeyIgnoreStep();
	removeOsString();
	removeRemarks();
	removeStepDescription();
	removeStepExecutionTime();
	removeStepStatus();
	removeTcFailed();
	removeTcPassed();
	removeTestName();
	removeBuildEnv();
	removeScreenshotName();
    }

    /**
     * @param tcPassed
     *            the tcPassed to set
     */
    public void setactualImage(String actualImage) {
	this.actualImage.set(actualImage);
    }

    /**
     * @param actualImageName
     *            the actualImageName to set
     */
    public void setActualImageName(String actualImageName) {
	this.actualImageName.set(actualImageName);
    }

    /**
     * @param actualImageName
     *            the actualImageName to set
     */
    public void setFullScreenShotImage(String fullScreenShotImage) {
	this.fullScreenShotImage.set(fullScreenShotImage);
    }

    /**
     * @param applicationURL
     *            the applicationURL to set
     */
    public void setApplicationURL(String applicationURL) {
	this.applicationURL.set(applicationURL);
    }

    /**
     * @param browserName
     *            the browserName to set
     */
    public void setBrowserName(String browserName) {
	this.browserName.set(browserName);
    }

    /**
     * 
     * @param buildEnv
     */
    public void setBuildEnv(String buildEnv) {
	this.buildEnv.set(buildEnv);
    }

    public void setBuildEnvFileStruc(String buildEnvFileStruc) {
	this.buildEnvFileStruc = buildEnvFileStruc;
    }

    /**
     * @param tcPassed
     *            the tcPassed to set
     */
    public void setdiffImage(String diffImage) {
	this.diffImage.set(diffImage);
    }

    /**
     * @param executionEndTime
     *            the executionEndTime to set
     */
    public void setExecutionEndTime(String executionEndTime) {
	this.executionEndTime.set(executionEndTime);
    }

    /**
     * @param executionStartTime
     *            the executionStartTime to set
     */
    public void setExecutionStartTime(String executionStartTime) {
	this.executionStartTime.set(executionStartTime);
    }

    public static void setExecutionTime(String executionTime) {
	Property.executionTime = executionTime;
    }

    /**
     * @param tcPassed
     *            the tcPassed to set
     */
    public void setexpectedImage(String expectedImage) {
	this.expectedImage.set(expectedImage);
    }

    public void setJobEndTime(String jobEndTime) {
	JobEndTime = jobEndTime;
    }

    public void setJobStartTime(String jobStartTime) {
	JobStartTime = jobStartTime;
    }

    /**
     * @param keyIgnoreStep
     *            the keyIgnoreStep to set
     */
    public void setKeyIgnoreStep(String keyIgnoreStep) {
	this.keyIgnoreStep.set(keyIgnoreStep);
    }

    /**
     * @param osString
     *            the osString to set
     */
    public void setOsString(String osString) {
	this.osString.set(osString);
    }

    /**
     * @param remarks
     *            the remarks to set
     */
    public void setRemarks(String remarks) {
	this.remarks.set(remarks);
    }

    public void setScreenShotCounter(Integer screenShotCounter) {
	this.screenShotCounter.set(screenShotCounter);
    }

    // + Property.FileSeperator.get() + "src"
    // + Property.FileSeperator.get() + "test"
    // + Property.FileSeperator.get() + "resources"
    // + Property.FileSeperator.get() + "screenshots";

    public void setScreenshotName(String screenshotName) {
	this.screenshotName.set(screenshotName);
    }

    /**
     * @param stepDescription
     *            the stepDescription to set
     */
    public void setStepDescription(String stepDescription) {
	this.stepDescription.set(stepDescription);
    }

    /**
     * @param stepExecutionTime
     *            the stepExecutionTime to set
     */
    public void setStepExecutionTime(String stepExecutionTime) {
	this.stepExecutionTime.set(stepExecutionTime);
    }

    /**
     * @param stepStatus
     *            the stepStatus to set
     */
    public void setStepStatus(String stepStatus) {
	this.stepStatus.set(stepStatus);
    }

    /**
     * @param tcFailed
     *            the tcFailed to set
     */
    public void setTcFailed(Integer tcFailed) {
	this.tcFailed.set(tcFailed);
    }

    /**
     * @param tcPassed
     *            the tcPassed to set
     */
    public void setTcPassed(Integer tcPassed) {
	this.tcPassed.set(tcPassed);
    }

    /**
     * @param testName
     *            the testName to set
     */
    public void setTestName(String testName) {
	this.testName.set(testName);
    }

    /**
    * 
    */
    public Property() {
	objLog = new LogFile();
	FileSeperator = new ThreadLocal<String>() {
	    @Override
	    protected String initialValue() {
		return System.getProperty("file.separator");
	    }
	};
	testNGXMLFileLocation = "TestNG.xml";
	ObjectRepositorySheet = "object_definition";
	TestCaseSheet = "test_flow";
	expectedImageJenkinPath = "/jenkins_archive/forge-images/";
	actualImageJenkinPath = "/jenkins_archive/forge-images/Test_Report/";
	forgeURL = "http://forgeimg.tribdev.com/";
	TestDataSheet = "test_data";
	actualImageName = new ThreadLocal<String>();
	fullScreenShotImage = new ThreadLocal<String>();
	HistoryReport = "History_Report";
	BDDFileQuery = "select * from [Steps$]";
	JDBCConnectionStringObjectRepository = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ={0};READONLY=TRUE";
	JDBCConnectionStringTestCase = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ=" + "{0}" + ";READONLY=TRUE";
	JDBCConnectionStringTestData = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ={0};READONLY=TRUE";
	JDBCConnectionStringBDDFile = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ={0};READONLY=TRUE";
	PropertyFileLocation = "src" + FileSeperator.get() + "main" + FileSeperator.get() + "resources" + FileSeperator.get() + "uiautomation.properties";
	ObjectRepositoryFileLocation = "src" + FileSeperator.get() + "main" + FileSeperator.get() + "resources" + FileSeperator.get() + "ObjectRepository"
	        + FileSeperator.get() + "ObjectRepository";
	browserName = new ThreadLocal<String>();
	IsSauceLabExecution = "false";
	globalVarMap = new ThreadLocal<HashMap<String, String>>() {
	    @Override
	    protected HashMap<String, String> initialValue() {
		HashMap<String, String> local = new HashMap<String, String>();
		local.put("key", "value");
		return local;
	    }
	};
	IsSauceLabExecution = "false";
	REUSABLE_INVOKE_KEYWORD = "runreusabletestcase";
	REUSABLE_FILE_NAME = "GlobalActionFile";
	FILE_EXTENSION = ".csv";
	IMAGE_TYPE = "png";
	screenShotCounter = new ThreadLocal<Integer>();
	TESTCASE_LOC = "src" + FileSeperator.get() + "main" + FileSeperator.get() + "resources" + FileSeperator.get() + "TestCase" + FileSeperator.get();
	remarks = new ThreadLocal<String>();
	PASS = "pass";
	FAIL = "fail";
	IGNORE = "ignored";
	SKIP = "skipped";
	tcPassed = new ThreadLocal<Integer>();
	tcFailed = new ThreadLocal<Integer>();
	stepStatus = new ThreadLocal<String>();
	diffImage = new ThreadLocal<String>();
	actualImage = new ThreadLocal<String>();
	expectedImage = new ThreadLocal<String>();
	applicationURL = new ThreadLocal<String>();
	buildEnv = new ThreadLocal<String>();
	testName = new ThreadLocal<String>();
	screenshotName = new ThreadLocal<String>();
	SEPERATOR = "#";
	stepDescription = new ThreadLocal<String>() {
	    @Override
	    protected String initialValue() {
		return "";
	    }
	};
	SyncTimeOut = "";
	LogFileName = "";
	LogFile = "Logs" + FileSeperator.get() + "{0}";
	BDD_StepName = "";
	BDD_FileFormat = "";
	BDDFile_Location = "src" + FileSeperator.get() + "main" + FileSeperator.get() + "resources" + FileSeperator.get() + "BDDStepRecord"
	        + FileSeperator.get();
	BDD_FileName = "";
	BDD_ExcelSheet = "";
	BDD_FileExtension = ".xlsx";
	executionStartTime = new ThreadLocal<String>();
	executionEndTime = new ThreadLocal<String>();
	test_Report_Logs_Path = FileSeperator.get() + "Test_Report" + FileSeperator.get();
	osString = new ThreadLocal<String>();
	MachineIp = "";
	stepExecutionTime = new ThreadLocal<String>();
	keyIgnoreStep = new ThreadLocal<String>() {
	    @Override
	    protected String initialValue() {
		return "ignorestep";
	    }
	};
	dbDB2Driver = "com.ibm.db2.jcc.DB2Driver";
	dbPostgresDriver = "org.postgresql.Driver";
	dbOracleDriver = "";
	dbSQLDriver = "";
	dbConnString = "";
	dbName = "";
	dbUsername = "";
	dbPassword = "";
	dbQuery = "";
	ScreenShotPath = "src/test/resources/screenshots";
    }

}