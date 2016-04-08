/**************************************
 testDriver.Actions.java : Kind of interface that prepare objects and data before execution goes
 to perform application's operations.
 **************************************/

package testDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import reporting.LogFile;
import Common.Property;
import Common.Utility;
import dataReader.DBReader;

@SuppressWarnings("unused")
public class Actions {
    private static final String JSON_HEIGHT_KEY = "height";
    private static final String JSON_WIDTH_KEY = "width";
    private static final String JSON_SIZE_KEY = "size";
    private static final String JSON_URL_KEY = "url";
    private static final String JSON_NAMESPACE_KEY = "namespace";
    private static final String JSON_SLUG_KEY = "slug";

    public LogFile objLog;
    public String remoteURL;
    public boolean isRemoteTrue;
    public Platform currentOS;
    public WebDriverWait wait;
    private Integer imageNumber;
    public HashMap<String, String> objDataRow;
    private String attribute;
    private String[] allAttributes;
    private String attributeType;
    private int nthElement;
    private List<WebElement> findElements = null;
    WebElement testObject;
    List<WebElement> testObjects;
    public int exp;
    public boolean verification;
    // public boolean enable = true;
    public boolean skip;

    public boolean ignore;
    public ArrayList<String> optionsList;
    private WebDriver driver;
    private int testObjectsCount;
    private int rec;

    public Property property;
    public Utility utility;

    static int loopCounter = 1;
    public static HashMap<Integer, Object> var2ShareAmongTestSteps = new HashMap<>();

    public boolean swipeOnScreen = true;
    public String androidAppActivity;

    public Actions(WebDriver driver2, Utility utility, Property property) {

	// Creating Java variables
	currentOS = Platform.getCurrent();
	isRemoteTrue = false;
	objDataRow = new HashMap<String, String>();
	verification = true;
	skip = false;
	ignore = false;
	optionsList = new ArrayList<String>();

	// Creating class objects for classes within framework
	objLog = new LogFile();
	this.driver = driver2;
	this.utility = utility;
	this.property = property;

	try {
	    if (this.driver != null) {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
		        && !this.property.getBrowserName().contains("native")) {
		    driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
		}
		if (this.property.getBrowserName().contains("ios") && this.property.getBrowserName().contains("native")) {
		    wait = new WebDriverWait(driver, property.maxTimeOutiOS);
		} else {
		    wait = new WebDriverWait(driver, property.maxTimeOut);
		}
	    }

	} catch (SessionNotFoundException e) {
	    if (this.property.getBrowserName().contains("native")) {
		// do nothing
	    } else {
		verification = false;
	    }

	}
    }

    public WebDriver DO(WebDriver driver, String stepAction, String Data, String parent, String child, String modifier) throws Exception {
	exp = 1;
	String stepStatus = "";
	String TestObject = child;
	property.setRemarks("");

	// ----Parse Options field----
	optionsList.clear();
	int stIndex;
	int endIndex;
	if (modifier != null) {
	    modifier = modifier.toLowerCase().trim();
	    for (int v = 0;; v++) {
		if (modifier.contains("{")) {
		    stIndex = modifier.indexOf('{');
		    modifier = modifier.replaceFirst("\\{", "");
		    endIndex = modifier.indexOf('}');
		    String KeyVariable = modifier.substring(stIndex, endIndex);
		    optionsList.add(KeyVariable);
		    modifier = modifier.replaceFirst("\\}", "");
		} else {
		    break;
		}
	    }
	}
	utility.driverOptionList = null;
	utility.driverOptionList = optionsList;

	// ----Parse Data field----
	// Multiple values are separated with # in Excel sheet)

	String[] DataContents = null;
	String dataContentFirst = Data;
	String dataContentSecond = "";
	String dataContentThird = "";
	String dataContentFourth = "";
	String dataContentFifth = "";
	String dataContentSix = "";
	if (Data != null) {
	    DataContents = Data.split(property.SEPERATOR);
	    switch (DataContents.length) {
	    case 1:
		dataContentFirst = DataContents[0];
		dataContentFirst = dataContentFirst.trim();
		break;
	    case 2:
		dataContentFirst = DataContents[0];
		dataContentSecond = DataContents[1];
		dataContentFirst = dataContentFirst.trim();
		dataContentSecond = dataContentSecond.trim();
		break;
	    case 3:
		dataContentFirst = DataContents[0];
		dataContentSecond = DataContents[1];
		dataContentThird = DataContents[2];
		dataContentFirst = dataContentFirst.trim();
		dataContentSecond = dataContentSecond.trim();
		dataContentThird = dataContentThird.trim();
		break;
	    case 5:
		dataContentFirst = DataContents[0];
		dataContentSecond = DataContents[1];
		dataContentThird = DataContents[2];
		dataContentFourth = DataContents[3];
		dataContentFifth = DataContents[4];
		dataContentFirst = dataContentFirst.trim();
		dataContentSecond = dataContentSecond.trim();
		dataContentThird = dataContentThird.trim();
		dataContentFourth = dataContentFourth.trim();
		dataContentFifth = dataContentFifth.trim();
		break;
	    case 6:
		dataContentFirst = DataContents[0];
		dataContentSecond = DataContents[1];
		dataContentThird = DataContents[2];
		dataContentFourth = DataContents[3];
		dataContentFifth = DataContents[4];
		dataContentSix = DataContents[5];
		dataContentFirst = dataContentFirst.trim();
		dataContentSecond = dataContentSecond.trim();
		dataContentThird = dataContentThird.trim();
		dataContentFourth = dataContentFourth.trim();
		dataContentFifth = dataContentFifth.trim();
		dataContentSix = dataContentSix.trim();
		break;
	    default:
		break;
	    }
	    dataContentFirst = utility.replaceVariableInString(dataContentFirst);
	    dataContentSecond = utility.replaceVariableInString(dataContentSecond);
	    dataContentThird = utility.replaceVariableInString(dataContentThird);
	}

	// ----Get current Date and Time for logging

	this.property.setStepExecutionTime(utility.getCurrentDateAndTime());

	/*********************************************************************************************
	 * Step action name: Ignore Test Step Description: When option column
	 * contains '{IgnoreStep}' the Test Step will be ignored Input
	 * variables: None Content of variable: NA Output: Test step is ignored
	 * and will be passed
	 *********************************************************************************************/

	if (optionsList.contains(property.getKeyIgnoreStep().toLowerCase())) {
	    property.setRemarks("(Non-Runnable)This step was marked to be ignored.");
	    ignore = true;
	}

	try {
	    setObjectDataRow(objDataRow);
	    String step = stepAction.toLowerCase();
	    String property = "";
	    String propertyvariable = "";
	    String propertyvalue = "";
	    DBReader db = null;

	    switch (step) {
	    default:
		this.property.setStepStatus(this.property.FAIL);
		this.property.setRemarks("Test Step not defined");
		throw new NoSuchMethodException("No Such Action");

	    case "openbrowser":
		if (this.property.getBrowserName().contains("native")) {
		    this.property.setStepDescription("Opening the native app.");
		    if (driver != null) {
			driver.quit();
			driver = null;
		    }
		    //for android Onboarding
		    if (dataContentFirst.equalsIgnoreCase("onboarding")) {
			androidAppActivity="com.mckinsey.insights.BootStrapActivity";
		    }
		    else{
			androidAppActivity="com.mckinsey.insights.home.HomeActivity";
			//com.mckinsey.insights.home.MostPopularActivity
		    }
		    
		    //for iOS Onboarding
		    if (!dataContentSecond.equalsIgnoreCase("")) {
			driver = initDriverIOS(dataContentSecond);
		    }
		    else {
			driver = initDriver();
		    }
		    
		} 
		//when browser is not native
		else {
		    // dataContentFirst variable will be used to set URL
		    dataContentFirst = "http://www.mckinsey.com/";
		    // call init method of seleniumImplemention here.
		    this.property.setStepDescription("Open a new browser and navigate to URL '" + dataContentFirst + "'");
		    if (driver == null) {
			driver = initDriver();
		    }
		    driver = openBrowser(dataContentFirst, driver);
		}
		break;

	    case "signinpopup":
		this.property.setStepDescription("Closing sign in pop");
		// verification = signInPopUp();
		verification = true;
		skip = true;
		break;

	    case "brknwspopup":
		this.property.setStepDescription("Closing breaking news pop");
		// verification = brkNwsPopUp();
		verification = true;
		skip = true;
		break;

	    case "goback":
		this.property.setStepDescription("Go backward in browser");
		verification = goBack();
		break;

	    case "changeorientation":
		this.property.setStepDescription("Changing the orientation");
		verification = changeOrientation(dataContentFirst);
		break;

	    case "scroll":
		this.property.setStepDescription("Scrolling");
		verification = scroll(dataContentFirst);
		break;

	    case "goforward":
		this.property.setStepDescription("Go forward in browser");
		verification = goForward();
		break;

	    case "navigateurl":
		this.property.setStepDescription("Navigate to Url '" + dataContentFirst + "' in currently opened browser");
		verification = navigateUrl(dataContentFirst);
		break;

	    case "refresh":
		this.property.setStepDescription("Refresh browser");
		verification = refreshBrowser();
		break;

	    case "check":
		this.property.setStepDescription("Check '" + TestObject + "'");
		verification = check();
		break;

	    case "uncheck":
		this.property.setStepDescription("Uncheck '" + TestObject + "'");
		verification = uncheck();
		break;

	    case "closebrowser":
		this.property.setStepDescription("Close Browser");
		verification = closeBrowser();
		break;

	    case "closeallbrowsers":
		this.property.setStepDescription("Close All Browser");
		verification = closeAllBrowsers();
		break;

	    case "clickobject":
		this.property.setStepDescription("Click on '" + TestObject + "'");

		String imageNo = dataContentFirst;

		if (imageNo.equalsIgnoreCase("")) {
		    verification = clickObject();
		} else if (!imageNo.equalsIgnoreCase("")) {
		    int imageCount = Integer.parseInt(dataContentFirst);
		    verification = clickObject(imageCount);
		}
		break;

	    case "mouseover":
		this.property.setStepDescription("Mouse overing on '" + TestObject + "'");
		verification = mouseOver();
		break;

	    case "scrolltoelement":
		this.property.setStepDescription("scrollToElement");
		if (dataContentSecond.isEmpty()) {
		    dataContentSecond = "0";
		}
		if (dataContentThird.isEmpty()) {
		    dataContentThird = "0";
		}
		verification = scrollToElement(dataContentFirst, Integer.parseInt(dataContentSecond), Integer.parseInt(dataContentThird));
		break;

	    case "swiperighttoleft":
		this.property.setStepDescription("swipe right to left");
		if (dataContentFirst.isEmpty()) {
		    verification = swipeRightToLeft();
		} else if (!dataContentFirst.isEmpty() && dataContentSecond.isEmpty()) {
		    verification = swipeRightToLeft(Integer.parseInt(dataContentFirst));
		} else if (!dataContentFirst.isEmpty() && !dataContentSecond.isEmpty()) {
		    verification = swipeRightToLeft(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "swipelefttoright":
		this.property.setStepDescription("swipe left to right");
		if (dataContentFirst.isEmpty()) {
		    verification = swipeLeftToRight();
		} else if (!dataContentFirst.isEmpty() && dataContentSecond.isEmpty()) {
		    verification = swipeLeftToRight(Integer.parseInt(dataContentFirst));
		} else if (!dataContentFirst.isEmpty() && !dataContentSecond.isEmpty()) {
		    verification = swipeLeftToRight(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "swipetoptobottom":
		this.property.setStepDescription("swipe Top to Bottom");
		if (dataContentFirst.isEmpty()) {
		    verification = swipeTopToBottom();
		} else if (!dataContentFirst.isEmpty() && !dataContentSecond.isEmpty()) {
		    verification = swipeTopToBottom(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "swipebottomtotop":
		this.property.setStepDescription("swipe bottom to Top");
		if (dataContentFirst.isEmpty()) {
		    verification = swipeBottomToTop();
		} else if (!dataContentFirst.isEmpty() && dataContentSecond.isEmpty()) {
		    verification = swipeBottomToTop(dataContentFirst);
		} else if (!dataContentFirst.isEmpty() && !dataContentSecond.isEmpty()) {
		    verification = swipeBottomToTop(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "switchoffwifi":
		this.property.setStepDescription("switching off wifi connection");
		verification = switchOffWifi();
		break;
	    case "switchonwifi":
		this.property.setStepDescription("switching on wifi connection");
		verification = switchOnWifi();
		break;
	    case "swipeelementtoelement":
		this.property.setStepDescription("swipeelementtoelement");
		verification = swipeelEment2Element(dataContentFirst);
		break;
	    case "swipetocordinates":
		this.property.setStepDescription("swipetocordinates");
		verification = swipeToCordinates(dataContentFirst, dataContentSecond, dataContentThird, dataContentFourth, dataContentFifth);
		break;
	    case "doubleclick":
		this.property.setStepDescription("Double click on '" + TestObject + "'");
		verification = doubleClick();
		break;

	    case "enterdata":
	    case "type":
		this.property.setStepDescription("Enter Text '" + dataContentFirst + "' in '" + TestObject + "'");
		verification = sendKeys(dataContentFirst);
		utility.setKeyValueInGlobalVarMap(TestObject, dataContentFirst);
		break;

	    case "typehash":
		this.property.setStepDescription("Enter Text  # " + " in " + TestObject + "'");
		sendKeys("#\n");
		break;

	    case "cleartextfield":
		this.property.setStepDescription("clear Text in '" + TestObject + "'");
		verification = clearTextField();
		break;

	    case "enterrandomemailidintextfield":
		this.property.setStepDescription("enter Random EmailID in TextField '" + TestObject + "'");
		verification = enterRandomEmailIDinTextField();
		break;

	    case "fireevent":
		this.property.setStepDescription("Fire '" + dataContentFirst + "' on '" + TestObject + "'");
		Boolean IsPlain_JS = false;
		if (objDataRow.size() == 0) {
		    IsPlain_JS = true;
		}
		fireEvent(dataContentFirst, IsPlain_JS);
		break;

	    case "enteruniquedata":
		break;

	    case "keypress":
		this.property.setStepDescription("Press Keyboard key '" + dataContentFirst + "' on '" + TestObject + "'");
		keyPress(dataContentFirst);
		break;

	    case "wait":
		this.property.setStepDescription("Waiting for '" + (Integer.parseInt(dataContentFirst) / 1000) + " seconds'");
		Thread.sleep(Long.parseLong(dataContentFirst));
		break;

	    case "selectitem":
		this.property.setStepDescription("Select '" + dataContentFirst + "' in '" + TestObject + "'");
		verification = selectItem(dataContentFirst);
		break;

	    case "selectitembyindex":
		this.property.setStepDescription("Select '" + dataContentFirst + "th' item from '" + TestObject);
		String optionValue = selectElementByIndex(dataContentFirst);
		utility.setKeyValueInGlobalVarMap(TestObject, optionValue);
		break;

	    case "getobjectproperty":
	    case "getattribute":
		property = dataContentFirst.trim();
		propertyvariable = TestObject + "." + property;
		if (dataContentSecond != "" && dataContentSecond != null) {
		    propertyvariable = dataContentSecond.trim();
		}
		this.property.setStepDescription("Get Property of '" + TestObject + "' and set its value in '" + propertyvariable + "'");
		String propertyValue = getObjectProperty(property);
		utility.setKeyValueInGlobalVarMap(propertyvariable, propertyValue);
		break;

	    case "setvariable":
		this.property.setStepDescription("Set the variable '" + dataContentFirst + "' with value '" + dataContentSecond + "'");
		utility.setKeyValueInGlobalVarMap(dataContentFirst, dataContentSecond);
		break;

	    case "getpageproperty":
		property = dataContentFirst.trim();
		propertyValue = getPageProperty(property);
		this.property.setStepDescription("Get Page Property of '" + property + "' and propertyValue '" + propertyValue + "'");
		utility.setKeyValueInGlobalVarMap(property, propertyValue);
		break;

	    case "verifytextpresent":
		this.property.setStepDescription("Verify if text '" + dataContentFirst + "' with '" + optionsList + "' and size '" + verification
		        + "' is present");

		if (objDataRow.size() == 0) {
		    verification = verifyTextPresentOnPage(dataContentFirst, optionsList);
		} else {
		    verification = verifyObjectProperty("text", dataContentFirst, optionsList);
		}
		break;

	    case "verifytextnotpresent":
		this.property.setStepDescription("Verify if text '" + dataContentFirst + "' with '" + optionsList + "' and size '" + verification
		        + "' is not present");

		verification = verifyTextNotPresentOnPage(dataContentFirst, optionsList);
		break;

	    case "comparedbresults":

		this.property.setStepDescription("Verify if text '" + dataContentFirst + "' is the same as  '" + dataContentSecond + "'");

		verification = compareDBResults(dataContentFirst, dataContentSecond, optionsList);
		break;

	    case "inputfilename":
		this.property.setStepDescription("Uploading file '" + dataContentFirst + "'");
		verification = fileUpload(dataContentFirst);
		break;

	    case "randomnamegenerator":

		this.property.setStepDescription("Generate a random name of'" + dataContentFirst + "' characters '");

		utility.setKeyValueInGlobalVarMap(TestObject, randomNameGenerator());
		break;

	    case "verifyobjectproperty":

		this.property.setStepDescription("Verify Object Properties '" + dataContentFirst + "'-'" + dataContentSecond + "'-'" + optionsList
		        + "' are present");

		verification = verifyObjectProperty(dataContentFirst, dataContentSecond, optionsList);
		break;

	    case "verifypageproperty":
		this.property.setStepDescription("Verify Page Properties '" + dataContentFirst + "'-'" + dataContentSecond + "'-'" + optionsList
		        + "' are present");
		verification = verifyPageProperty(dataContentFirst, dataContentSecond, optionsList);
		break;

	    case "verifyobjectpresent":
		this.property.setStepDescription("Verify object '" + TestObject + "' present");
		verification = verifyObjectPresent();
		break;

	    case "verifyobjectnotpresent":
		this.property.setStepDescription("Verify object '" + TestObject + "' is not present");
		verification = verifyObjectNotPresent();
		break;

	    case "waitforelement":
		this.property.setStepDescription("Wait for the'" + TestObject + "' element");
		waitAndGetElement();
		break;

	    case "getresultfromdb":
		this.property.setStepDescription("Get the result from db");

		db = new DBReader();
		try {
		    utility.setKeyValueInGlobalVarMap(TestObject, db.getQueryResult(dataContentFirst));

		    objLog.writeInfo(utility.getValueFromGlobalVarMap(TestObject));
		} catch (ClassNotFoundException e) {
		    objLog.writeInfo(e.getMessage());
		} catch (SQLException e) {
		    objLog.writeInfo(e.getMessage());
		}
		break;

	    case "updatedb":
		this.property.setStepDescription("Update value in db");

		db = new DBReader();
		try {
		    utility.setKeyValueInGlobalVarMap(TestObject, db.updateQuery(dataContentFirst));

		    objLog.writeInfo(utility.getValueFromGlobalVarMap(TestObject));
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		break;

	    case "cutrequiredimage":
		this.property.setStepDescription("Cut Required Image");
		if (this.property.getBrowserName().equalsIgnoreCase("firefox")) {
		    verification = cutRequiredImage(dataContentFirst);
		} else if (this.property.getBrowserName().equalsIgnoreCase("chrome")) {
		    verification = cutRequiredImage(dataContentFirst);

		} else if (this.property.getBrowserName().equalsIgnoreCase("IE")) {
		    verification = cutRequiredImage(dataContentFirst);

		    /*
		     * Code to check whether the image is cut properly or not.
		     * This code will check for the black portion in the image
		     * for the first 100 px height. If the black portion exists
		     * then we try to take the screenshot again for a max of 5
		     * times.
		     */

		    for (int i = 0; i < 5; i++) {
			if (check4BlackImage()) {
			    verification = cutRequiredImage(dataContentFirst);
			} else {
			    break;
			}
		    }
		    if (check4BlackImage()) {
			this.property.setRemarks("Actual Image not created properly.");
			verification = false;
		    }
		} else if ((this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web"))
		        || this.property.getBrowserName().equalsIgnoreCase("safari")) {
		    skip=true;
		    //verification = cutRequiredImageSafari(dataContentFirst);
		} else if ((this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("web"))) {
		    skip=true;
		    //verification = cutRequiredImageAndroidChrome(dataContentFirst);
		} else {

		    skip = true;
		}
		break;

	    case "compareimages":
		if (this.property.generateImage.equalsIgnoreCase("false")) {
		    if (this.property.getBrowserName().equalsIgnoreCase("firefox") || this.property.getBrowserName().equalsIgnoreCase("IE")
		            || this.property.getBrowserName().equalsIgnoreCase("chrome")) {
			this.property.setStepDescription("Compare Image");
			verification = compareImages(dataContentFirst, this.property.getActualImageName());
		    } else {
			skip = true;
		    }
		} else {
		    skip = true;
		}
		break;

	    case "verifyobjectsize":
		this.property.setStepDescription("Verify object size");
		verification = verifyObjectProperty(dataContentFirst, "data-adloader-size");
		break;

	    case "verifyfontname":
		this.property.setStepDescription("Verify font name");
		if (this.property.getBrowserName().equalsIgnoreCase("ie")) {
		    dataContentFirst = dataContentFirst.toLowerCase();
		}
		verification = verifyObjectProperty(dataContentFirst, "font-family");
		break;

	    case "verifyfontsize":
		this.property.setStepDescription("Verify font size");
		/*
		 * if
		 * (this.property.getBrowserName().equalsIgnoreCase("chrome")) {
		 * verification = verifyObjectProperty(dataContentSecond,
		 * "font-size"); } else
		 */if ((this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web"))) {
		    verification = verifySize(dataContentFourth);
		} else {
		    verification = verifyObjectProperty(dataContentFirst, "font-size");
		}
		break;

	    case "verifyfontweight":
		this.property.setStepDescription("Verify font weight");
		if (this.property.getBrowserName().equalsIgnoreCase("chrome") || this.property.getBrowserName().equalsIgnoreCase("safari")
		        || (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web"))) {
		    if (dataContentFirst.equalsIgnoreCase("400")) {
			dataContentFirst = "normal";
		    } else if (dataContentFirst.equalsIgnoreCase("700")) {
			dataContentFirst = "bold";
		    }
		}
		verification = verifyObjectProperty(dataContentFirst, "font-weight");
		break;

	    case "verifycolor":
		this.property.setStepDescription("Verify color");
		verification = verifyObjectProperty(dataContentFirst, "color");
		break;

	    case "presentedbyurlinspect":
		this.property.setStepDescription("Verify presented by URL inspect");
		verification = verifyObjectProperty(dataContentFirst, "data-outfits-clicktrackingurl");
		break;

	    case "verifysize":
		this.property.setStepDescription("Verify size");
		if ((this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web"))) {
		    verification = verifySize(dataContentSecond);
		} else {
		    verification = verifySize(dataContentFirst);
		}
		break;

	    case "verifycolorchange":
		this.property.setStepDescription("Verify color change");
		verification = verifyColorChange(dataContentFirst);
		break;

	    case "switchtoiframe":
		this.property.setStepDescription("Switch to iframe");
		verification = switchToIframe();
		break;

	    case "verifyservercall":
		this.property.setStepDescription("Verify server call");
		verification = verifyServerCall(dataContentFirst);
		break;

	    case "windowresize":
		this.property.setStepDescription("Window resize");
		if ((this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web"))) {
		    this.property.setRemarks("Window resize not supported for mobile");
		    skip = true;
		} else {
		    verification = windowResize(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "verifytitle":
		this.property.setStepDescription("Verify Title");
		verification = verifyTitle(dataContentFirst);
		break;

	    case "verifyurl":
		this.property.setStepDescription("Verify URL");
		verification = verifyURL(dataContentFirst);
		break;
	    case "verifyheight":
		this.property.setStepDescription("Verify Height");
		if (this.property.getBrowserName().equalsIgnoreCase("iOSPhonePortraitWeb")
		        || this.property.getBrowserName().equalsIgnoreCase("iOSPhoneLandscapeWeb")
		        || this.property.getBrowserName().equalsIgnoreCase("iOSTabletPortraitWeb")) {
		    verification = verifyHeight(dataContentSecond);
		} else if (this.property.getBrowserName().equalsIgnoreCase("iOSTabletLandscapeWeb")) {
		    verification = verifyHeight(dataContentThird);
		} else {
		    verification = verifyHeight(dataContentFirst);
		}
		break;
	    case "verifyimagepixel":
		this.property.setStepDescription("Verify image pixel");
		verification = verifyImagepixel(dataContentFirst);
		break;

	    case "verifytitlepresenceredirectedpage":
		this.property.setStepDescription("verify Title Presence on Redirected Page");
		verification = verifyTitlePresenceRedirectedPage(dataContentFirst);

		break;

	    case "verifyurlpresenceredirectedpage":
		this.property.setStepDescription("Verify URL Presence on Redirected Page");
		verification = verifyURLPresenceRedirectedPage(dataContentFirst);
		break;

	    case "explicitwait":
		this.property.setStepDescription("Explicit Wait");
		verification = explicitWait(dataContentFirst, dataContentSecond);
		break;

	    case "getimagenumberingallery":
		this.property.setStepDescription("Verify object '" + TestObject + "' present");
		explicitWait("elementisvisible", null);
		String number = testObject.getAttribute("data-pagecount");

		imageNumber = Integer.parseInt(number);
		this.property.setRemarks(imageNumber.toString());
		break;

	    case "clicktilllastimage":
		this.property.setStepDescription("Verify object '" + TestObject + "' present");
		explicitWait("elementisvisible", null);

		for (int i = 1; i <= imageNumber; i++) {
		    clickObject();
		}
		break;

	    case "verifyelementcount":
		try {
		    this.property.setStepDescription("Verify number of '" + TestObject + "' elements present");

		    verification = verifylEmentCount(dataContentFirst);
		} catch (Exception e) {
		    exceptionMessage(e);
		}
		break;

	    case "verifyobjectnonclickable":
		this.property.setStepDescription("Verify Object " + TestObject + " is non clickable.");
		verification = verifyObjectNonclickable();
		break;

	    case "verifyobjectattribute":
		this.property.setStepDescription("Verify Object Property");
		verification = verifyObjectAttribute(dataContentFirst, dataContentSecond);
		break;

	    case "verifyobjectcss":
		this.property.setStepDescription("Verify Object CSS");
		verification = verifyObjectCSS(dataContentFirst, dataContentSecond);
		break;

	    case "verifyparameterinurl":

		this.property.setStepDescription("Verify " + dataContentFirst + " value in the open url");
		verification = verifyParameterinURL(dataContentFirst, dataContentSecond);
		break;

	    case "getrequest":

		this.property.setStepDescription("Send Get Request to: " + dataContentFirst);
		verification = getRequest(dataContentFirst, dataContentSecond, dataContentThird);
		break;

	    case "getrequestafteraction":

		this.property.setStepDescription("Send Get Request to: " + dataContentFirst);
		verification = getRequestAfterAction(dataContentFirst, dataContentSecond, dataContentThird);
		break;

	    case "verifyresponsestatusrest":

		this.property.setStepDescription("Verfiy status code " + dataContentFirst);
		verification = verifyStatusCodeREST(dataContentFirst);
		break;

	    case "verifyresponsetyperest":

		this.property.setStepDescription("Verfiy status type " + dataContentFirst);
		verification = verifyResponseTypeREST(dataContentFirst);
		break;

	    case "verifyresponsecontent":
		this.property.setStepDescription("Verfiy response content " + dataContentFirst);
		verification = verifyResponseData(dataContentFirst);
		break;

	    case "postrequest":

		this.property.setStepDescription("Send Post Request to " + dataContentFirst);

		verification = postRequest(dataContentFirst, dataContentSecond, dataContentThird, dataContentFourth, dataContentFifth);
		break;

	    case "deleterequest":

		this.property.setStepDescription("Send delete Request to " + dataContentFirst);

		verification = deleteRequestAfterAction(dataContentFirst, dataContentSecond, dataContentThird);
		break;

	    case "updaterequest":

		this.property.setStepDescription("Send update Request to " + dataContentFirst);

		verification = updateRequestAfterAction(dataContentFirst, dataContentSecond, dataContentThird, dataContentFourth, dataContentFifth);
		break;

	    case "switchtoandroidwebview":

		this.property.setStepDescription("Switch Android view to " + dataContentFirst);

		verification = switchToAndroidWebView(dataContentFirst);
		break;

	    case "switchtoioswebview":

		this.property.setStepDescription("Switch iOS view to " + dataContentFirst);

		verification = switchToiOSWebView(dataContentFirst, dataContentSecond);
		break;

	    case "switchtonativeview":

		this.property.setStepDescription("Switch to native view");

		verification = switchToNativeView();
		break;

	    case "verifyarticledatepattern":

		this.property.setStepDescription("Match value with given pattern");

		verification = verifyArticleDatePattern();
		break;

	    case "verifyelementlength":

		this.property.setStepDescription("Actual Length should not be more than Expected Length");

		verification = verifyElementLength(dataContentFirst);
		break;

	    case "findarticlewithtopicios":
		this.property.setStepDescription("find an article with topic name in ios");
		verification = findArticleWithTopiciOS(dataContentFirst);
		break;

	    case "findandclickonarticlewithphotogalleryios":
		this.property.setStepDescription("find and click on article with photo gallery in ios");
		verification = findAndClickOnArticleWithPhotoGalleryiOS();
		break;

	    case "findandclickonarticlewithvideoios":
		this.property.setStepDescription("find and click on article with video in ios");
		verification = findAndClickOnArticleWithVideoiOS();
		break;

	    case "clickvideogallerydonebuttonios":
		this.property.setStepDescription("click video gallery done button in ios");
		verification = clickVideoGalleryDoneButtoniOS();
		break;

	    case "waittillvideototaltimeios":
		this.property.setStepDescription("wait till video total time in ios");
		verification = waitTillVideoTotalTimeiOS();
		break;

	    case "verifytopicnameisdifferent":
		this.property.setStepDescription("verify topic name is different");
		verification = verifyTopicNameisDifferent();
		break;

	    case "createelementfromdatacontent":
		this.property.setStepDescription("create element from data content");
		createElementfromDataContent(dataContentFirst, dataContentSecond, dataContentThird);
		break;

	    case "clickfollowanarticleandriod":
		this.property.setStepDescription("click follow an article in andriod");
		verification = clickFollowAnArticleAndriod();
		break;

	    case "clickphotogalleryandroid":
		this.property.setStepDescription("Clicking on the first photoGallery in " + dataContentFirst + "catogory");
		verification = clickPhotoGalleryAndroid();
		break;

	    case "verifysectiontitlepresent":
		this.property.setStepDescription("Verify whether the " + dataContentFirst + " section title is displayed or not");
		verification = verifySectionTitlePresent(dataContentFirst);
		break;

	    case "verifysectiontitlenotpresent":
		this.property.setStepDescription("Verify whether the " + dataContentFirst + " section title is displayed or not");
		verification = !verifySectionTitlePresent(dataContentFirst);
		break;

	    case "clickonsectiontitle":
		this.property.setStepDescription("click on Section Title");
		verification = clickOnSectionTitle();
		break;

	    case "verifyobjectpresentandroid":
		this.property.setStepDescription("Verify object '" + TestObject + "' present");
		verification = verifyObjectPresentAndroid();
		break;

	    case "setsocialsettingsios":
		this.property.setStepDescription("setting preconditions for IOS for sharing");
		verification = setSettingsIOS();
		break;

	    case "saveobjecttextvalueinmap":
		this.property.setStepDescription("Save Text Value of " + TestObject);
		verification = SaveObjectTextValueinMap();
		break;

	    case "verifyobjecttextvalueinmap":
		this.property.setStepDescription("Verify value of " + TestObject);
		verification = VerifyObjectTextValueinMap();
		break;

	    case "verifyobjecttextvaluedontmatchinmap":
		this.property.setStepDescription("Verify value of " + TestObject);
		verification = VerifyObjectTextValueDontMatchinMap();
		break;

	    case "acceptalert":
		this.property.setStepDescription("Switch to alert and accept the alert");
		verification = wait4AlertAndAccept();
		break;
	    case "navigatetonextpremiumarticle":
		this.property.setStepDescription("reading more the premium articles until all the credits completed");
		verification = navigateToNextPremiumArticle();
		break;
	    case "readfirstpremiumarticleios":
		this.property.setStepDescription("reading more the premium articles until all the credits completed");
		verification = readFirstPremiumArticleiOS();
		break;

	    case "clickreadpremiumarticle":
		this.property.setStepDescription("reading more the premium articles until all the credits completed");
		verification = clickReadPremiumArticle();
		break;

	    case "tapobject":
		this.property.setStepDescription("tapping on the mobile object" + TestObject);
		if (dataContentFirst.equalsIgnoreCase("")) {
		    verification = tapObject();
		} else {
		    verification = tapObject(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "longtap":
		this.property.setStepDescription("long tap");
		if (dataContentFirst.equalsIgnoreCase("")) {
		    verification = longTap();
		} else {
		    verification = longTap(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		}
		break;

	    case "threadsleepnative":
		this.property.setStepDescription("thread sleep native");
		ThreadSleepNative(Long.parseLong(dataContentFirst));
		break;

	    case "verifytextintitle":
		this.property.setStepDescription("verify text in title");
		verification = verifyTextInTitle(dataContentFirst);
		break;
	    case "switchtoalert":
		this.property.setStepDescription("switch to alert");
		verification = SwitchToAlert();
		break;
	    case "tapatelement":
		this.property.setStepDescription("tapping on the mobile object" + TestObject);
		verification = tapAtElement(Integer.parseInt(dataContentFirst), Integer.parseInt(dataContentSecond));
		break;

	    case "openvideogalleryarticle":
		this.property.setStepDescription("Clicking on the first videoGallery in " + dataContentFirst + "catogory");
		verification = clickVideoGalleryArticleAndroid();
		break;

	    case "openvideogalleryarticlelandscape":
		this.property.setStepDescription("Clicking on the first videoGallery in " + dataContentFirst + "catogory");
		verification = clickVideoGalleryArticleLandscapeAndroid();
		break;
	    case "apendrandomtextfieldvalue":
		this.property.setStepDescription("Enter Text '" + dataContentFirst + "' in '" + TestObject + "'");
		verification = apendRandomTextFieldValue(dataContentFirst);
		utility.setKeyValueInGlobalVarMap(TestObject, dataContentFirst);
		break;

	    case "clickfacebookpost":
		this.property.setStepDescription("Click FB POST");
		verification = clickFacebookPOST();
		break;
	    }

	    if (ignore) {
		stepStatus = this.property.IGNORE;
	    } else if (skip) {
		this.property.setRemarks("");
		stepStatus = this.property.SKIP;
	    } else if (verification) {
		stepStatus = this.property.PASS;
	    } else {
		if (optionsList.contains("optional")) {
		    stepStatus = this.property.SKIP;
		} else {
		    stepStatus = this.property.FAIL;
		}
	    }

	} catch (

	NoSuchMethodException exception)

	{
	    stepAction = property.FAIL;
	    throw exception;
	} catch (

	Exception e)

	{
	    exceptionMessage(e);
	    if (optionsList.contains("optional")) {
		stepStatus = property.SKIP;
	    } else {
		stepStatus = property.FAIL;
	    }
	}

	this.property.setStepStatus(stepStatus);
	return driver;

    }

    /**
     * @return
     * @throws Exception
     */
    public boolean swipeelEment2Element(String targetElement) throws Exception {
	try {
	    WebElement targetWebElement = driver.findElement(MobileBy.IosUIAutomation(targetElement));
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size = driver.manage().window().getSize();
	    int startX = testObject.getLocation().getX();
	    int endX = targetWebElement.getLocation().getX();
	    int startY = testObject.getLocation().getY();
	    int endY = targetWebElement.getLocation().getY();
	    ((AppiumDriver) driver).swipe(startX, startY, endX, endY, 1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean swipeToCordinates(String x1, String y1, String x2, String y2, String seconds) throws Exception {
	try {
	    Thread.sleep(2000);
	    ((AppiumDriver) driver).swipe(Integer.parseInt(x1), Integer.parseInt(y1), Integer.parseInt(x2), Integer.parseInt(y2), Integer.parseInt(seconds));
	    Thread.sleep(2000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * @return
     * @throws Exception
     * @throws IOException
     *             Code to check whether the image is cut properly or not. This
     *             code will check for the black portion in the image for the
     *             first 100px height.
     */
    public boolean check4BlackImage() throws Exception {
	String cutImageName = this.property.getactualImage();
	File cutImageNameFile = new File(cutImageName);
	int sample = 0;
	try {
	    BufferedImage cutBuffImg = ImageIO.read(cutImageNameFile);
	    for (int i = 0; i < cutBuffImg.getWidth(); i++) {
		for (int j = 0; j < 150; j++) {
		    if (!(cutBuffImg.getRGB(i, j) == -16777216)) {
			sample++;
		    }
		}
	    }
	} catch (IOException e) {
	    exceptionMessage(e);
	}
	if (sample == 0) {
	    return true;
	} else {
	    return false;
	}

    }

    /*********************************************************************************************
     * Function name: initDriver Description: Initialize the driver for the
     * current (remote) Browser Input variables: None Content of variable: NA
     * Output: driver
     * 
     * @return
     * @throws IOException
     *********************************************************************************************/

    public WebDriver initDriver() {
	try {
	    // for Remote Selenium Grid Execution
	    if (this.property.IsRemoteExecution.equalsIgnoreCase("true")) {

		String remoteURL = this.property.RemoteURL + "/wd/hub";
		URL uri = new URL(remoteURL);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = creatingCapabilityForRemoteBrowser(capabilities);

		// Android
		if (this.property.getBrowserName().toLowerCase().contains("android")) {

		    driver = new AndroidDriver(uri, capabilities);
		}
		// iOS
		else if (this.property.getBrowserName().toLowerCase().contains("ios")) {

		    try {
			driver = new IOSDriver(uri, capabilities);
			ThreadSleep(2500);
		    } catch (Exception e) {
			driver = new IOSDriver(uri, capabilities);
			ThreadSleep(2500);
		    }
		}
		// For all other Browsers in Grid
		else {
		    driver = new RemoteWebDriver(uri, capabilities);
		}
	    }
	    // For local Execution
	    else {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if (this.property.getBrowserName().equalsIgnoreCase("firefox")) {
		    FirefoxProfile ffprofile;
		    ffprofile = new FirefoxProfile();
		    ffprofile.setPreference("webdriver_assume_untrusted_issuer", "false");
		    driver = new FirefoxDriver(ffprofile);

		} else if (this.property.getBrowserName().equalsIgnoreCase("IE")) {
		    final File file = new File("src/main/resources/drivers/IEDriverServer_32.exe");
		    System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		    final DesiredCapabilities iecapabilities = DesiredCapabilities.internetExplorer();
		    iecapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		    iecapabilities.setCapability("ensureCleanSession", true);
		    driver = new InternetExplorerDriver(iecapabilities);

		} else if (this.property.getBrowserName().equalsIgnoreCase("chrome")) {

		    DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
		    chromeCapabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));

		    chromeCapabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
		    ChromeOptions chromeOptions = new ChromeOptions();
		    chromeOptions.addArguments(Arrays.asList("--test-type"));
		    chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		    // Check if the OS is Mac and use OS X Chromedriver
		    if (System.getProperty("os.name").contains("Mac")) {
			ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort()
			        .usingDriverExecutable(new File("src/main/resources/drivers/chromedriver")).build();
			service.start();
			driver = new ChromeDriver(service, chromeCapabilities);

		    } else {
			ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort()
			        .usingDriverExecutable(new File("src/main/resources/drivers/chromedriver.exe")).build();
			service.start();
			driver = new ChromeDriver(service, chromeCapabilities);
		    }

		} else if (this.property.getBrowserName().equalsIgnoreCase("safari")) {
		    System.setProperty("webdriver.safari.noinstall", "true");
		    driver = new SafariDriver();
		}
		// iOS safari
		else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web")) {
		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
		    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		    caps.setCapability(CapabilityType.BROWSER_NAME, "Safari");
		    caps.setCapability("safariIgnoreFraudWarning", "true");

		    if (this.property.getBrowserName().toLowerCase().contains("phone")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
		    } else if (this.property.getBrowserName().toLowerCase().contains("tablet")) {
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad 2");
		    }

		    if (this.property.getBrowserName().toLowerCase().contains("portrait")) {
			caps.setCapability("orientation", "PORTRAIT");
		    } else if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
			caps.setCapability("orientation", "LANDSCAPE");
		    }
		    driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		}
		// Android chrome
		else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("web")) {

		    capabilities.setCapability("browserName", "chrome");
		    capabilities.setCapability("platform", this.property.platform);
		    capabilities.setCapability("deviceName", this.property.deviceName);
		    capabilities.setCapability("platformVersion", this.property.platformVersion);
		    // capabilities.setCapability("avd", "AndroidPhone");
		    // capabilities.setCapability("fullReset", "true");

		    if (this.property.getBrowserName().toLowerCase().contains("portrait")) {
			capabilities.setCapability("orientation", "PORTRAIT");
		    } else if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
			capabilities.setCapability("orientation", "LANDSCAPE");
		    }

		    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		    ThreadSleep(2500);
		}
		// Android Native
		else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("native")) {

		    final File classpathRoot = new File(System.getProperty("user.dir"));
		    final File appDir = new File(classpathRoot, "src/main/resources/apps/android");
		    final File app = new File(appDir, "mckinsey.mckinseyinsights.apk");

		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability("browserName", "");
		    caps.setCapability("platform", this.property.platform);
		    caps.setCapability("deviceName", this.property.deviceName);
		    caps.setCapability("platformVersion", this.property.platformVersion);
		    caps.setCapability("autoAcceptAlerts", true);
		    // caps.setCapability("udid", "192.168.56.101:5555");
		    // caps.setCapability("avd", "AndroidPhone");
		    // capabilities.setCapability("fullReset", "true");
		    caps.setCapability("appPackage", "com.mckinsey.mckinseyinsights");
		    caps.setCapability("appActivity", androidAppActivity);
		    caps.setCapability("app", app.getAbsolutePath());

		    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		    ThreadSleepNative(2500);
		    //driver.findElement(By.name("YES")).click();
		    //ThreadSleepNative(2500);
		}
		// iOS Native
		else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("native")) {

		    final File classpathRoot = new File(System.getProperty("user.dir"));
		    final File appDir = new File(classpathRoot, "src/main/resources/apps/ios/");
		    final File app = new File(appDir, "ChicagoTribune.app");

		    capabilities.setCapability("platformVersion", this.property.platformVersion);
		    capabilities.setCapability("platformName", this.property.platform);
		    // capabilities.setCapability("fullReset", "true");
		    capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
		    capabilities.setCapability("app", app.getAbsolutePath());
		    capabilities.setCapability("deviceName", this.property.deviceName);

		    // if(this.property.getBrowserName().toLowerCase().contains("phone")){
		    // capabilities.setCapability("deviceName",
		    // this.property.deviceName);
		    // }else
		    // if(this.property.getBrowserName().toLowerCase().contains("tablet")){
		    // capabilities.setCapability("deviceName", "iPad Air");
		    // }

		    if (this.property.getBrowserName().toLowerCase().contains("portrait")) {
			capabilities.setCapability("orientation", "PORTRAIT");
		    } else if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
			capabilities.setCapability("orientation", "LANDSCAPE");
		    }

		    driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		    ThreadSleep(2500);
		}
	    }
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
	            && !this.property.getBrowserName().contains("native")) {
		driver.manage().timeouts().pageLoadTimeout(this.property.maxTimeOut, TimeUnit.SECONDS);
	    }
	    if (this.property.getBrowserName().contains("ios") && this.property.getBrowserName().contains("native")) {
		wait = new WebDriverWait(driver, property.maxTimeOutiOS);
	    } else {
		wait = new WebDriverWait(driver, property.maxTimeOut);
	    }
	    // fix for iOS Onboarding
	    if (this.property.getBrowserName().contains("ios") && this.property.getBrowserName().contains("native")) {
		try {
		    for (int i = 0; i < 4; i++) {

			wait.until(ExpectedConditions.elementToBeClickable(MobileBy.IosUIAutomation(".buttons()[0]")));
			driver.findElement(MobileBy.IosUIAutomation(".buttons()[0]")).click();
		    }
		} catch (TimeoutException | NoSuchElementException e) {
		    e.printStackTrace();
		}
	    }
	    // fix for Android Landscape mode
	    if (this.property.getBrowserName().contains("android") && this.property.getBrowserName().contains("native")) {
		if (this.property.getBrowserName().contains("landscape")) {
		    Thread.sleep(2500);
		    ((AppiumDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
		    ThreadSleep(1500);
		} else {
		    Thread.sleep(2500);
		    ((AppiumDriver) driver).rotate(ScreenOrientation.PORTRAIT);
		    ThreadSleep(1500);
		}

	    }
	} catch (NullPointerException e) {
	    this.property.setStepDescription("Exception occurred at driver initialization: " + e.getMessage());
	    this.property.setRemarks("Driver initialization failed. ");
	    objLog.writeError("Exception occurred at driver initialization: " + e.getMessage());
	    verification = false;
	} catch (Exception e) {
	    this.property.setStepDescription("Exception occurred at driver initialization: " + e.getMessage());
	    this.property.setRemarks("Driver initialization failed. ");
	    objLog.writeError("Exception occurred at driver initialization: " + e.getMessage());
	    verification = false;
	}
	return driver;
    }

    /*********************************************************************************************
     * Function name: creatingCapabilityForRemoteBrowser Description: Creating
     * capabilities for the current (remote) Browser Input variables: None
     * Content of variable: NA Output: NA
     * 
     * @param capabilities
     * @return
     * @throws IOException
     *********************************************************************************************/
    private DesiredCapabilities creatingCapabilityForRemoteBrowser(DesiredCapabilities capabilities) {

	if (this.property.getBrowserName().equalsIgnoreCase("IE")) {
	    objLog.writeInfo("Remote browser is internet explorer");
	    capabilities = DesiredCapabilities.internetExplorer();
	    capabilities.setPlatform(Platform.ANY);
	    capabilities.setCapability("ignoreProtectedModeSettings", true);
	    capabilities.setCapability("ensureCleanSession", true);
	    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

	} else if (this.property.getBrowserName().equalsIgnoreCase("safari")) {
	    objLog.writeInfo("Remote browser is safari");
	    System.setProperty("webdriver.safari.noinstall", "true");
	    capabilities = DesiredCapabilities.safari();
	    capabilities.setCapability("webdriver.safari.noinstall", "true");
	    capabilities.setPlatform(Platform.ANY);

	} else if (this.property.getBrowserName().equalsIgnoreCase("firefox")) {
	    objLog.writeInfo("Remote browser is firefox");
	    capabilities = DesiredCapabilities.firefox();
	    capabilities.setPlatform(Platform.ANY);

	} else if (this.property.getBrowserName().equalsIgnoreCase("chrome")) {
	    objLog.writeInfo("Remote browser is Chrome");
	    capabilities = DesiredCapabilities.chrome();
	    capabilities.setPlatform(Platform.ANY);
	} // iOS safari
	else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("web")) {

	    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
	    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
	    capabilities.setCapability(CapabilityType.BROWSER_NAME, "Safari");
	    capabilities.setCapability("safariIgnoreFraudWarning", "true");
	    capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10 * 60);

	    if (this.property.getBrowserName().toLowerCase().contains("phone")) {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
	    } else if (this.property.getBrowserName().toLowerCase().contains("tablet")) {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad 2");
	    }

	    if (this.property.getBrowserName().toLowerCase().contains("portrait")) {
		capabilities.setCapability("orientation", "PORTRAIT");
	    } else if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
		capabilities.setCapability("orientation", "LANDSCAPE");
	    }
	}
	// Android chrome
	else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("web")) {

	    capabilities.setCapability("browserName", "chrome");
	    capabilities.setCapability("platform", this.property.platform);
	    capabilities.setCapability("deviceName", this.property.deviceName);
	    capabilities.setCapability("platformVersion", this.property.platformVersion);
	    // capabilities.setCapability("avd", "AndroidPhone");
	    // capabilities.setCapability("fullReset", "true");

	    if (this.property.getBrowserName().toLowerCase().contains("portrait")) {
		capabilities.setCapability("orientation", "PORTRAIT");
	    } else if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
		capabilities.setCapability("orientation", "LANDSCAPE");
	    }

	    if (this.property.getBrowserName().toLowerCase().contains("phone")) {
		capabilities.setCapability("udid", "192.168.56.101:5555");
	    } else if (this.property.getBrowserName().toLowerCase().contains("tablet")) {
		capabilities.setCapability("udid", "192.168.56.102:5555");
	    }
	} // Android Native
	else if (this.property.getBrowserName().toLowerCase().contains("android") && this.property.getBrowserName().toLowerCase().contains("native")) {

	    final File classpathRoot = new File(System.getProperty("user.dir"));
	    final File appDir = new File(classpathRoot, "src/main/resources/apps/android");
	    final File app = new File(appDir, "mckinsey.mckinseyinsights.apk");

	    capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platform", this.property.platform);
	    capabilities.setCapability("deviceName", this.property.deviceName);
	    capabilities.setCapability("platformVersion", this.property.platformVersion);
	    // capabilities.setCapability("avd", "AndroidPhone");
	    // capabilities.setCapability("fullReset", "true");
	 
	    capabilities.setCapability("appPackage", "com.mckinsey.mckinseyinsights");
	    capabilities.setCapability("appActivity", androidAppActivity);
	    capabilities.setCapability("app", app.getAbsolutePath());

	    if (this.property.getBrowserName().toLowerCase().contains("phone")) {
		capabilities.setCapability("udid", "192.168.56.101:5555");
	    } else if (this.property.getBrowserName().toLowerCase().contains("tablet")) {
		capabilities.setCapability("udid", "192.168.56.102:5555");
	    }
	} // iOS Native
	else if (this.property.getBrowserName().toLowerCase().contains("ios") && this.property.getBrowserName().toLowerCase().contains("native")) {

	    final File appDir = new File("/Users/forgeuser/Documents/forge-grid");
	    final File app = new File(appDir, "ChicagoTribune.app");

	    capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platformVersion", this.property.platformVersion);
	    capabilities.setCapability("platformName", this.property.platform);
	    capabilities.setCapability("platform", "MAC");
	    // capabilities.setCapability("fullReset", "true");
	    capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
	    capabilities.setCapability("app", app.getAbsolutePath());
	    capabilities.setCapability("deviceName", this.property.deviceName);

	    if (this.property.getBrowserName().toLowerCase().contains("portrait")) {
		capabilities.setCapability("orientation", "PORTRAIT");
	    } else if (this.property.getBrowserName().toLowerCase().contains("landscape")) {
		capabilities.setCapability("orientation", "LANDSCAPE");
	    }
	}
	return capabilities;
    }

    /*********************************************************************************************
     * Function name: brkNwsPopUp Description: Handle the unwanted pop-ups on
     * the page Input variables: NA Content of variable: Output: Handle the
     * unwanted pop-ups on the page
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean brkNwsPopUp() throws Exception {
	try {
	    ThreadSleep(1500);
	    new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(byLocator(attributeType, attribute)));
	    driver.findElement(byLocator(attributeType, attribute)).click();

	} catch (NoSuchElementException e) {
	    System.out.println("Object not displayed.");
	    skip = true;

	} catch (Exception e) {
	    skip = true;
	}
	return true;
    }

    /*********************************************************************************************
     * Function name: byLocator Description: By Locator based on attribute tyoe
     * and atribute value Output: NA
     *********************************************************************************************/

    public By byLocator(String AttributeType, String Attribute) throws Exception {
	String attr = AttributeType.toLowerCase();
	By by = null;

	try {
	    switch (attr) {
	    default:
		throw new Exception("Incorrect Attribute type mentioned");
	    case "css":
		by = By.cssSelector(Attribute);
		break;
	    case "xpath":
		by = By.xpath(Attribute);
		break;
	    case "class":
		by = By.className(Attribute);
		break;
	    case "id":
		by = By.id(Attribute);
		break;
	    case "name":
		by = By.name(Attribute);
		break;
	    case "text":
		by = By.linkText(Attribute);
		break;
	    case "ios":
		by = MobileBy.IosUIAutomation(Attribute);
		break;
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	}
	return by;
    }

    /*********************************************************************************************
     * Function name: check() Description: Check Checkbox GUI item Input
     * variables: TestObject Content of variable: Testobject name of Checkbox
     * Output: testObject.click();
     *********************************************************************************************/

    public boolean check() throws Exception {
	try {
	    explicitWait("elementtobeclickable", null);
	    if (!testObject.isSelected()) {
		testObject.click();
	    }
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: clickObject Description: Click on GUI item Input
     * variables: TestObject Content of variable: Testobject name of GUI item
     * Output: waitAndGetElement(); testObject.click();
     *********************************************************************************************/

    public boolean clickObject() throws Exception {
	ThreadSleep(2000);
	ThreadSleep(5000);
	try {
	    explicitWait("elementtobeclickable", null);
	    testObject.click();
	    ThreadSleep(2000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: clickObject Description: Click on GUI item Input
     * variables: TestObject Content of variable: Testobject name of GUI item
     * Output: waitAndGetElement(); testObject.click();
     *********************************************************************************************/

    private boolean clickObject(int imageCount) throws Exception {
	ThreadSleep(2000);
	ThreadSleep(5000);
	try {
	    explicitWait("elementtobeclickable", null);
	    for (int count = 1; count <= imageCount; count++) {
		testObject.click();
	    }
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: closeallbrowsers Description: Close all currently opened
     * browsers Input variables: None Content of variable: NA Output:
     * driver.quit() driver.close();
     *********************************************************************************************/

    public boolean closeAllBrowsers() throws Exception {
	try {

	    try {
		driver.quit();
		driver = null;
	    } catch (Exception e) {
		if (!this.property.getBrowserName().contains("native")) {
		    Set<String> windwHandles = getWindowHandles();
		    for (String Handle : windwHandles) {
			driver.switchTo().window(Handle);
			try {
			    driver.close();
			    driver = null;
			} catch (Exception ex) {
			}
		    }
		}

	    }
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /*********************************************************************************************
     * Function name: closebrowser Description: Close currently opened browser
     * Input variables: None Content of variable: NA Output: closeBrowser()
     *********************************************************************************************/

    public boolean closeBrowser() throws Exception {
	try {
	    switchToMostRecentWindow();
	    driver.close();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Step action name: CompareDBResults Description: Compare two DB Results
     * (strings) Input variables: text optionsList Content of variable: Value of
     * Text Property {partialmatch}{ignorecase}{ignorestep} Output:
     * isPropertyVerified
     *********************************************************************************************/
    public boolean compareDBResults(String Property_one, String Property_two, ArrayList<String> options) throws Exception {
	try {
	    boolean isPropertyVerified = false;

	    if (!options.isEmpty()) {
		isPropertyVerified = utility.doMatchBasedOnOptions(Property_one, Property_two);
	    } else {
		isPropertyVerified = Property_one.equals(Property_two);
	    }
	    if (!isPropertyVerified) {
		property.setRemarks("' actual value - '" + Property_one + "' doesn't match with expected value - '" + Property_two + "'.");
	    }
	    return isPropertyVerified;

	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Function name: compareImages Description: Compare the image difference
     * Input variables: Property PropertyValue Content of variable: Output:
     * Differences between Expected and Actual image
     * 
     * @param expImg
     * @param actualImg
     * @return
     * @throws Exception
     *********************************************************************************************/
    public boolean compareImages(String expImg, String actualImg) throws Exception {

	Boolean isImageverify = false;
	File imageDir = null;
	File diffimageDir = null;
	String diffImage = "";
	File jenkinsExpectedImageDir = null;
	File jenkinsActualImageDir = null;
	String imgBrowser = null;
	String expectedImage = null;
	String actualImage = null;
	property.setdiffImage(null);
	String viewPort = getViewPort();
	viewPort = viewPort.replace("(", "").replace(")", "");
	int w = Integer.parseInt(viewPort.split(",")[0].trim());
	int h = Integer.parseInt(viewPort.split(",")[1].trim());
	imgBrowser = property.getBrowserName();
	File jfile = new File(property.actualImageJenkinPath, utility.getDate() + property.FileSeperator.get() + Property.getExecutionTime());

	imageDir = new File(System.getProperty("user.dir"), "src/test/resources/images/" + imgBrowser + "/");
	diffimageDir = new File(System.getProperty("user.dir"), "src/test/resources/images/" + imgBrowser + "/output/");
	jenkinsExpectedImageDir = new File("/apps/forge-images/" + imgBrowser + "/");
	jenkinsActualImageDir = new File("/apps/forge-images/Test_Report/" + utility.getDate() + "/" + Property.getExecutionTime() + "/" + imgBrowser + "/");

	if (!imageDir.exists()) {
	    imageDir.mkdirs();
	}

	if (!diffimageDir.exists()) {
	    diffimageDir.mkdirs();
	}

	if (property.IsRemoteExecution.equalsIgnoreCase("true") && property.localGrid.equalsIgnoreCase("false")) {

	    expectedImage = jenkinsExpectedImageDir + property.FileSeperator.get() + expImg + "_" + imgBrowser + "_" + w + "x" + h + ".png";
	    actualImage = jenkinsActualImageDir + property.FileSeperator.get() + actualImg;
	    diffImage = jenkinsActualImageDir + property.FileSeperator.get() + "output" + property.FileSeperator.get() + "diffImage_" + expImg + "_"
	            + imgBrowser + "_" + w + "x" + h + ".png";
	    objLog.writeInfo("Expected Image path: " + expectedImage);
	    objLog.writeInfo("Actual Image path: " + actualImage);
	    objLog.writeInfo("Diff Image path: " + diffImage);

	} else {
	    expectedImage = imageDir + property.FileSeperator.get() + expImg + "_" + imgBrowser + "_" + w + "x" + h + ".png";
	    actualImage = imageDir + property.FileSeperator.get() + actualImg;
	    diffImage = diffimageDir + property.FileSeperator.get() + "diffImage_" + expImg + "_" + imgBrowser + "_" + w + "x" + h + ".png";
	}

	Dimension expImgDim, actImgDim;
	try {
	    File expImgFile = new File(expectedImage);
	    File actImgFile = new File(actualImage);
	    File diffImgFile = new File(diffImage);

	    int sample = 0;

	    if (expImgFile.exists()) {

		expImgDim = getImageDim(expImgFile);
		actImgDim = getImageDim(actImgFile);

		BufferedImage expBuffImg = ImageIO.read(expImgFile);
		BufferedImage actBuffImg = ImageIO.read(actImgFile);

		if (expBuffImg.getHeight() != actBuffImg.getHeight() || expBuffImg.getWidth() > actBuffImg.getWidth()) {
		    if (expBuffImg.getHeight() > actBuffImg.getHeight()) {
			FileUtils.copyFile(expImgFile, diffImgFile);
		    } else {
			FileUtils.copyFile(actImgFile, diffImgFile);
		    }
		    int loopwidth = expBuffImg.getWidth();
		    int loopheight = expBuffImg.getHeight();
		    if (expBuffImg.getWidth() > actBuffImg.getWidth()) {
			loopwidth = actBuffImg.getWidth();
		    }
		    if (expBuffImg.getHeight() > actBuffImg.getHeight()) {
			loopheight = actBuffImg.getHeight();
		    }
		    objLog.writeInfo(String.valueOf(loopwidth));
		    objLog.writeInfo(String.valueOf(loopheight));

		    BufferedImage diffBuffImg = ImageIO.read(diffImgFile);

		    if (!diffimageDir.exists()) {
			diffimageDir.mkdir();
		    }

		    objLog.writeInfo("Comparing Started....");
		    // if (!this.property.getBrowserName().equalsIgnoreCase(
		    // "safari")) {
		    for (int i = 0; i < loopwidth; i++) {
			for (int j = 100; j < loopheight; j++) {
			    if (!(expBuffImg.getRGB(i, j) == actBuffImg.getRGB(i, j))) {
				diffBuffImg.setRGB(i, j, -147220175);
				diffBuffImg.flush();
				sample++;
			    }
			    if (j == loopheight - 1) {
				diffBuffImg.setRGB(i, j, -147220175);
			    }
			}
		    }
		    FileOutputStream strm = new FileOutputStream(diffImgFile);
		    ImageIO.write(diffBuffImg, "png", strm);
		    strm.close();
		    // }
		} else {
		    FileUtils.copyFile(expImgFile, diffImgFile);

		    BufferedImage diffBuffImg = ImageIO.read(diffImgFile);

		    if (!diffimageDir.exists()) {
			diffimageDir.mkdir();
		    }

		    // if (!this.property.getBrowserName().equalsIgnoreCase(
		    // "safari")) {
		    objLog.writeInfo("Comparing Started....");
		    for (int i = 0; i < expBuffImg.getWidth(); i++) {
			for (int j = 100; j < expBuffImg.getHeight(); j++) {
			    if (!(expBuffImg.getRGB(i, j) == actBuffImg.getRGB(i, j))) {
				diffBuffImg.setRGB(i, j, -147220175);
				diffBuffImg.flush();
				sample++;
			    }
			}
		    }
		    FileOutputStream strm = new FileOutputStream(diffImgFile);
		    ImageIO.write(diffBuffImg, "png", strm);
		    strm.close();
		}
		// }
	    } else {
		property.setStepDescription("Expected Image is not present at location:-  " + imageDir);
		property.setRemarks("Expected Image is not present at location:-  " + imageDir);
	    }
	    if (sample != 0) {
		if (diffImgFile.exists()) {
		    property.setStepDescription("There is diff in actual and expected images.");
		    property.setRemarks("Mismatch in the Actual and Expected Image: ");

		    property.setdiffImage(getJenkinsURL(diffImage));
		    property.setactualImage(getJenkinsURL(actualImage));
		    property.setexpectedImage(getJenkinsURL(expectedImage));
		} else {
		    isImageverify = true;
		    property.setactualImage(getJenkinsURL(actualImage));
		    property.setexpectedImage(getJenkinsURL(expectedImage));
		}
	    } else {
		isImageverify = true;
		property.setactualImage(getJenkinsURL(actualImage));
		property.setexpectedImage(getJenkinsURL(expectedImage));
		property.setdiffImage(getJenkinsURL(diffImage));
		diffImgFile.delete();
	    }
	    if (actImgFile.exists() && !expImgFile.exists()) {
		actImgFile.renameTo(expImgFile);
		property.setStepDescription("Expected screenshot not found.\n New expected screenshot created: " + expImgFile);
		property.setRemarks("Expected screenshot not found.\n New expected screenshot created: ");
		isImageverify = true;
		property.setactualImage(getJenkinsURL(actualImage));
		property.setexpectedImage(getJenkinsURL(expectedImage));
		property.setdiffImage(getJenkinsURL(diffImage));
	    }

	} catch (Exception e) {
	    // exceptionMessage(e);
	    objLog.writeError("Exception occurred: " + e);
	    property.setRemarks("Unexpected error occurred:" + e.getMessage());
	}
	return isImageverify;
    }

    /*********************************************************************************************
     * Function name: getViewPort Description: return current ViewPort. Output:
     * return current ViewPort.
     * 
     *********************************************************************************************/

    private String getViewPort() {
	if (!property.getBrowserName().toLowerCase().contains("android")) {
	    return driver.manage().window().getSize().toString();
	} else {
	    // for Android we are just returning some dummy value. As it will
	    // throw the WebDriverException
	    return "(20, 20)";
	}
    }

    /*********************************************************************************************
     * Function name: cutRequiredImage Description: Cut the required image from
     * the web page screenshot Input variables: Property Content of variable:
     * Output: Cut the required image from the web page screenshot
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean cutRequiredImage(String ExpectedImage) throws Exception {

	explicitWait("elementisvisible", null);
	BufferedImage img = null;
	BufferedImage dest = null;
	File screen = null;
	File imageDir = null;
	String browserDir = null;
	String actualImagefileName = "";
	String viewPort = getViewPort();
	String actualFilePath = null;
	String fullScreenShot = null;
	viewPort = viewPort.replace("(", "").replace(")", "");
	int w = Integer.parseInt(viewPort.split(",")[0].trim());
	int h = Integer.parseInt(viewPort.split(",")[1].trim());
	File jfile = new File(property.actualImageJenkinPath, utility.getDate() + property.FileSeperator.get() + Property.getExecutionTime());
	if (!jfile.exists()) {
	    if (jfile.mkdirs()) {
		objLog.writeInfo("File Created Successfully: " + jfile);
	    }
	}
	try {
	    browserDir = property.getBrowserName();
	    // Create image path for local and Jenkins
	    if (property.IsRemoteExecution.equalsIgnoreCase("true") && property.localGrid.equalsIgnoreCase("false")) {
		// "/jenkins_archive/forge-images/
		if (property.generateImage.equalsIgnoreCase("true")) {
		    imageDir = new File(property.expectedImageJenkinPath + browserDir);
		} else {
		    imageDir = new File(jfile, browserDir);
		}
	    } else {
		imageDir = new File(System.getProperty("user.dir"), "src" + property.FileSeperator.get() + "test" + property.FileSeperator.get() + "resources"
		        + property.FileSeperator.get() + "images" + property.FileSeperator.get() + browserDir);
	    }

	    if (this.property.getBrowserName().equalsIgnoreCase("firefox") || this.property.getBrowserName().equalsIgnoreCase("chrome")
	            || this.property.getBrowserName().equalsIgnoreCase("IE")) {

		ThreadSleep(5000);
		screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Point p = testObject.getLocation();

		int width = testObject.getSize().getWidth();
		int height = testObject.getSize().getHeight();

		objLog.writeInfo("TestObject location:" + p.getX() + "," + p.getY());
		objLog.writeInfo("TestObject width:" + width);
		objLog.writeInfo("TestObject height:" + height);

		img = ImageIO.read(screen);

		fullScreenShot = imageDir + property.FileSeperator.get() + "fs" + property.FileSeperator.get() + ExpectedImage + "_" + browserDir + "_" + w
		        + "x" + h + ".png";
		File fullScreenShotFile = new File(fullScreenShot);
		if (!fullScreenShotFile.getParentFile().exists()) {
		    fullScreenShotFile.getParentFile().mkdirs();
		    objLog.writeInfo("File Created Successfully: " + fullScreenShot);
		}

		ImageIO.write(img, "png", fullScreenShotFile);
		ThreadSleep(2000);
		property.setFullScreenShotImage(getJenkinsURL(fullScreenShot));

		try {
		    dest = img.getSubimage(p.getX(), p.getY(), width, height);
		} catch (RasterFormatException rfe) {
		    objLog.writeInfo(rfe.getMessage());
		    property.setRemarks("Div is in more than one viewport.");
		    verification = false;
		    return false;
		}
	    }

	    // If condition to manage Image Generation
	    if (property.generateImage.equalsIgnoreCase("true")) {
		actualImagefileName = ExpectedImage + "_" + browserDir + "_" + w + "x" + h + ".png";
	    } else {
		actualImagefileName = "actualImage_" + ExpectedImage + "_" + browserDir + "_" + w + "x" + h + ".png";
	    }

	    actualFilePath = imageDir + property.FileSeperator.get() + actualImagefileName;
	    File f = new File(actualFilePath);

	    if (!f.getParentFile().exists()) {
		f.getParentFile().mkdirs();
	    }

	    // writing actual Image..
	    ImageIO.write(dest, "png", f);
	    ThreadSleep(2000);
	    property.setActualImageName(actualImagefileName);
	    property.setactualImage(actualFilePath);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: cutRequiredImageIos Description: Cut the required image
     * from the web page screenshot Input variables: Property Content of
     * variable: Output: Cut the required image from the web page screenshot
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean cutRequiredImageSafari(String ExpectedImage) throws Exception {

	explicitWait("elementisvisible", null);
	BufferedImage img = null;
	BufferedImage dest = null;
	File screen = null;
	File imageDir = null;
	String browserDir = null;
	String actualImagefileName = "";
	String viewPort = getViewPort();
	String actualFilePath = null;
	String fullScreenShot = null;
	viewPort = viewPort.replace("(", "").replace(")", "");
	int w = Integer.parseInt(viewPort.split(",")[0].trim());
	int h = Integer.parseInt(viewPort.split(",")[1].trim());
	File jfile = new File(property.actualImageJenkinPath, utility.getDate() + property.FileSeperator.get() + Property.getExecutionTime());
	if (!jfile.exists()) {
	    jfile.mkdirs();
	    objLog.writeInfo("File Created Successfully: " + jfile);
	}
	try {
	    browserDir = property.getBrowserName();
	    // Create image path for local and Jenkins
	    if (property.IsRemoteExecution.equalsIgnoreCase("true") && property.localGrid.equalsIgnoreCase("false")) {
		if (property.generateImage.equalsIgnoreCase("true")) {
		    imageDir = new File(property.expectedImageJenkinPath + browserDir);
		} else {
		    imageDir = new File(jfile, browserDir);
		}

	    } else {
		imageDir = new File(System.getProperty("user.dir"), "src" + property.FileSeperator.get() + "test" + property.FileSeperator.get() + "resources"
		        + property.FileSeperator.get() + "images" + property.FileSeperator.get() + browserDir);
	    }

	    // If condition to manage Image Generation
	    if (property.generateImage.equalsIgnoreCase("true")) {
		actualImagefileName = ExpectedImage + "_" + browserDir + "_" + w + "x" + h + ".png";
	    } else {
		actualImagefileName = "actualImage_" + ExpectedImage + "_" + browserDir + "_" + w + "x" + h + ".png";
	    }

	    actualFilePath = imageDir + property.FileSeperator.get() + actualImagefileName;
	    File f = new File(actualFilePath);

	    if (!f.getParentFile().exists()) {
		f.getParentFile().mkdirs();
		objLog.writeInfo("File Created Successfully: " + f);
	    }

	    ThreadSleep(5000);
	    screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    Point p = testObject.getLocation();

	    int width = testObject.getSize().getWidth();
	    int height = testObject.getSize().getHeight();

	    img = ImageIO.read(screen);

	    fullScreenShot = imageDir + property.FileSeperator.get() + "fs" + property.FileSeperator.get() + ExpectedImage + "_" + browserDir + "_" + w + "x"
	            + h + ".png";
	    File fullScreenShotFile = new File(fullScreenShot);
	    if (!fullScreenShotFile.getParentFile().exists()) {
		fullScreenShotFile.getParentFile().mkdirs();
		objLog.writeInfo("File Created Successfully: " + fullScreenShot);
	    }

	    ImageIO.write(img, "png", fullScreenShotFile);
	    ThreadSleep(2000);
	    property.setFullScreenShotImage(getJenkinsURL(fullScreenShot));

	    int dWidth = driver.manage().window().getSize().width;
	    int dHeight = driver.manage().window().getSize().height;

	    int scroll = dHeight;
	    int lastScroll = height % scroll;
	    int imgNo = 1;
	    int cropFromTop, cropFromBottom = 0;

	    if (this.property.getBrowserName().equalsIgnoreCase("iOSPhonePortraitWeb")) {
		cropFromTop = Integer.parseInt(property.iphonePortraitCropFromTop);
		cropFromBottom = Integer.parseInt(property.iphonePortraitCropFromBottom);
		;
	    } else if (this.property.getBrowserName().equalsIgnoreCase("iOSPhoneLandscapeWeb")) {
		cropFromTop = Integer.parseInt(property.iphoneLandscapeCropFromTop);
	    } else {
		cropFromTop = Integer.parseInt(property.otherDeviceCropFromTop);
	    }

	    ((JavascriptExecutor) driver).executeScript("$('" + property.trbHeader + "').hide();");
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", testObject);
	    ThreadSleep(2000);

	    int totalScreenShots = (int) Math.round(Math.ceil((double) height / scroll));

	    BufferedImage resultImage = null;
	    Graphics g = null;

	    // variable for stitched for actual image
	    // BufferedImage resultImage2 = null;
	    // Graphics g2 = null;

	    for (int scrCount = totalScreenShots, y = 0/* , y2 = 0 */; scrCount >= 1; scrCount--, y += dest.getHeight()) {

		// ExpectedCondition<Boolean> pageLoadCondition = new
		// ExpectedCondition<Boolean>() {
		// public Boolean apply(WebDriver driver) {
		// return ((JavascriptExecutor) driver).executeScript(
		// "return document.readyState")
		// .equals("complete");
		// }
		// };
		// WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait.until(pageLoadCondition);

		ThreadSleep(2000);
		screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		img = ImageIO.read(screen);

		// code for generate actual image for multiple screenshots
		// ImageIO.write(img, "png", new File(imageDir
		// + property.FileSeperator.get() + "actualImage_"
		// + ExpectedImage + "_" + browserDir + "_" + w + "x" + h
		// + "_" + imgNo + ".png"));
		if (this.property.getBrowserName().equalsIgnoreCase("safari")) {
		    if (height < dHeight) {
			dest = img.getSubimage(0, 0, width, height);
		    } else {
			dest = img;
		    }
		} else {
		    dest = img.getSubimage(0, cropFromTop, img.getWidth(), img.getHeight() - cropFromTop - cropFromBottom);
		}

		// code for generate cropped image for multiple screenshots
		// ImageIO.write(dest, "png", new File(imageDir
		// + property.FileSeperator.get() + "cropImage_"
		// + ExpectedImage + "_" + browserDir + "_" + w + "x" + h
		// + "_" + imgNo + ".png"));

		if (scrCount == totalScreenShots) {
		    resultImage = new BufferedImage(dest.getWidth(), (dest.getHeight()) * totalScreenShots, BufferedImage.TYPE_INT_RGB);
		    g = resultImage.getGraphics();
		    // code to generate graphic for actual stitched image
		    // resultImage2 = new BufferedImage(img.getWidth(),
		    // (img.getHeight()) * totalScreenShots,
		    // BufferedImage.TYPE_INT_RGB);
		    // g2 = resultImage2.getGraphics();
		}

		g.drawImage(dest, 0, y, null);
		// code for draw for actual image
		// g2.drawImage(img, 0, y2, null);

		if (scrCount == 2) {
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + lastScroll + ")");
		} else {
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scroll + ")");
		}
		imgNo++;
	    }

	    ImageIO.write(resultImage, "png", f);

	    // code for generate stitched cropped image
	    // ImageIO.write(resultImage2, "png", new File(imageDir
	    // + property.FileSeperator.get() + "actualImage_"
	    // + ExpectedImage + "_" + browserDir + "_" + w + "x" + h
	    // + "_" + imgNo + "MERGED.png"));

	    imgNo = 1;

	    ThreadSleep(2000);
	    property.setActualImageName(actualImagefileName);
	    property.setactualImage(actualFilePath);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean cutRequiredImageAndroidChrome(String ExpectedImage) throws Exception {

	explicitWait("elementisvisible", null);
	BufferedImage screenShotBufferedObject = null;
	BufferedImage dest = null;
	File screenShotObject = null;
	File imageDir = null;
	String browserDir = null;
	String actualImagefileName = "";
	String viewPort = "(400,512)";
	String actualImageFilePath = null;
	String fullScreenShot = null;
	viewPort = viewPort.replace("(", "").replace(")", "");
	int browserWidth = Integer.parseInt(viewPort.split(",")[0].trim());
	int browserHeight = Integer.parseInt(viewPort.split(",")[1].trim());

	browserDir = property.getBrowserName();

	try {
	    // Create image path for local and Jenkins
	    if (property.IsRemoteExecution.equalsIgnoreCase("true") && property.localGrid.equalsIgnoreCase("false")) {
		File jfile = new File(property.actualImageJenkinPath, utility.getDate() + property.FileSeperator.get() + Property.getExecutionTime());
		if (!jfile.exists()) {
		    jfile.mkdirs();
		    objLog.writeInfo("File Created Successfully: " + jfile);
		}
		if (property.generateImage.equalsIgnoreCase("true")) {
		    imageDir = new File(property.expectedImageJenkinPath + browserDir);
		} else {
		    imageDir = new File(jfile, browserDir);
		}

	    } else {
		imageDir = new File(System.getProperty("user.dir"), "src" + property.FileSeperator.get() + "test" + property.FileSeperator.get() + "resources"
		        + property.FileSeperator.get() + "images" + property.FileSeperator.get() + browserDir);
	    }

	    // If condition to manage Image Generation
	    if (property.generateImage.equalsIgnoreCase("true")) {
		actualImagefileName = ExpectedImage + "_" + browserDir + "_" + browserWidth + "x" + browserHeight + ".png";
	    } else {
		actualImagefileName = "actualImage_" + ExpectedImage + "_" + browserDir + "_" + browserWidth + "x" + browserHeight + ".png";
	    }

	    actualImageFilePath = imageDir + property.FileSeperator.get() + actualImagefileName;
	    File actualImageFileObject = new File(actualImageFilePath);

	    if (!actualImageFileObject.getParentFile().exists()) {
		actualImageFileObject.getParentFile().mkdirs();
		objLog.writeInfo("File Created Successfully: " + actualImageFileObject);
	    }

	    ThreadSleep(5000);
	    screenShotObject = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	    Point objectDimensions = testObject.getLocation();
	    int objectWidth = testObject.getSize().getWidth();
	    int objectHeight = testObject.getSize().getHeight();

	    screenShotBufferedObject = ImageIO.read(screenShotObject);

	    fullScreenShot = imageDir + property.FileSeperator.get() + "fs" + property.FileSeperator.get() + ExpectedImage + "_" + browserDir + "_"
	            + browserWidth + "x"
	            + browserHeight + ".png";
	    File fullScreenShotFile = new File(fullScreenShot);
	    if (!fullScreenShotFile.getParentFile().exists()) {
		fullScreenShotFile.getParentFile().mkdirs();
		objLog.writeInfo("File Created Successfully: " + fullScreenShot);
	    }

	    ImageIO.write(screenShotBufferedObject, "png", fullScreenShotFile);
	    ThreadSleep(2000);
	    property.setFullScreenShotImage(getJenkinsURL(fullScreenShot));

	    int scrollHeight = browserHeight;
	    int lastScrollHeight = objectHeight % scrollHeight;
	    int imgNo = 1;
	    int cropFromTop, cropFromBottom = 0;

	    if (this.property.getBrowserName().equalsIgnoreCase("iOSPhonePortraitWeb")) {
		cropFromTop = Integer.parseInt(property.iphonePortraitCropFromTop);
		cropFromBottom = Integer.parseInt(property.iphonePortraitCropFromBottom);

	    } else if (this.property.getBrowserName().equalsIgnoreCase("iOSPhoneLandscapeWeb")) {
		cropFromTop = Integer.parseInt(property.iphoneLandscapeCropFromTop);
	    } else {
		cropFromTop = Integer.parseInt(property.otherDeviceCropFromTop);
	    }

	    ((JavascriptExecutor) driver).executeScript("$('" + property.trbHeader + "').hide();");
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", testObject);
	    ThreadSleep(2000);

	    int totalNumberOfScreenShots = (int) Math.round(Math.ceil((double) objectHeight / scrollHeight));

	    BufferedImage resultActualImage = null;
	    Graphics g = null;

	    // variable for stitched for actual image
	    for (int scrCount = totalNumberOfScreenShots, y = 0; scrCount >= 1; scrCount--, y += dest.getHeight()) {

		ThreadSleep(2000);
		screenShotObject = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		screenShotBufferedObject = ImageIO.read(screenShotObject);

		System.out.println("Screen width is : " + screenShotBufferedObject.getWidth() + " and height is : " + screenShotBufferedObject.getHeight());
		ImageIO.write(screenShotBufferedObject, "png", actualImageFileObject);
		// code for generate actual image for multiple screenshots
		if (this.property.getBrowserName().equalsIgnoreCase("AndroidPhonePortraitWeb")) {
		    if (objectHeight < browserHeight) {
			dest = screenShotBufferedObject.getSubimage(0, 0, objectWidth * 3, objectHeight * 3);
		    } else {
			dest = screenShotBufferedObject;
		    }
		} else {
		    dest = screenShotBufferedObject.getSubimage(0, cropFromTop, screenShotBufferedObject.getWidth(),
		            screenShotBufferedObject.getHeight() - cropFromTop - cropFromBottom);
		}

		ImageIO.write(dest, "png", actualImageFileObject);
		// code for generate cropped image for multiple screenshots
		if (scrCount == totalNumberOfScreenShots) {
		    resultActualImage = new BufferedImage(dest.getWidth(), (dest.getHeight()) * totalNumberOfScreenShots, BufferedImage.TYPE_INT_RGB);
		    g = resultActualImage.getGraphics();
		}

		g.drawImage(dest, 0, y, null);

		// code for draw for actual image
		if (scrCount == 2) {
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + lastScrollHeight + ")");
		    Thread.sleep(2500);
		} else {
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scrollHeight + ")");
		}
		Thread.sleep(2500);
		imgNo++;
	    }

	    ImageIO.write(resultActualImage, "png", actualImageFileObject);

	    // code for generate stitched cropped image
	    imgNo = 1;

	    ThreadSleep(2000);
	    property.setActualImageName(actualImagefileName);
	    property.setactualImage(actualImageFilePath);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: DeleteAllCookies Description: Get and delete cookies in
     * selected browser Input variables: TestObject Content of variable:
     * Testobject name of GUI item Output: waitAndGetElement();
     * testObject.click();
     *********************************************************************************************/

    public void deleteAllCookies() {
	try {
	    driver.manage().deleteAllCookies();
	    Set<Cookie> cookies = driver.manage().getCookies();
	    for (Cookie cookie : cookies) {
		driver.manage().deleteCookie(cookie);
	    }

	} catch (Exception e) {
	    // Nothing to throw;
	}

    }

    /*********************************************************************************************
     * Function name: DoubleClick() Description: Doubleclick on GUI item Input
     * variables: attribute Content of variable: Testobject name of GUI item
     * Output: selenium.doubleClick(attribute);
     *********************************************************************************************/

    public boolean doubleClick() throws Exception {
	try {
	    explicitWait("elementtobeclickable", null);
	    testObject.click();
	    testObject.click();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: ExecuteScript Description: Execute script on (remote)
     * browser that is currently used Input variables: tObject Script Content of
     * variable: testObject Script code Output: scrResult.toString
     *********************************************************************************************/

    public String executeScript(WebElement tObject, String Script) throws Exception {
	try {
	    Object scrResult = null;
	    if (property.IsRemoteExecution.equalsIgnoreCase("false")) {
		if (property.getBrowserName().equalsIgnoreCase("firefox")) {
		    FirefoxDriver ffdriver = (FirefoxDriver) driver;
		    scrResult = ffdriver.executeScript(Script, tObject);
		    if (scrResult != null) {
			return scrResult.toString();
		    }
		} else if (property.getBrowserName().equalsIgnoreCase("IE")) {
		    InternetExplorerDriver iedriver = (InternetExplorerDriver) driver;
		    scrResult = iedriver.executeScript(Script, tObject);
		    if (scrResult != null) {
			return scrResult.toString();
		    }
		} else if (property.getBrowserName().equalsIgnoreCase("chrome")) {
		    ChromeDriver chromeDriver = (ChromeDriver) driver;
		    scrResult = chromeDriver.executeScript(Script, tObject);
		    if (scrResult != null) {
			return scrResult.toString();
		    }
		} else {
		    return "";
		}

	    } else {
		RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
		scrResult = remoteDriver.executeScript(Script, tObject);
		if (scrResult != null) {
		    return scrResult.toString();
		}
	    }

	    return "";

	} catch (Exception e) {
	    exceptionMessage(e);
	    if (e.getMessage().contains("No response from server for url")) {
		throw new Exception("No response from the Driver");
	    }
	    throw e;
	}

    }

    /*********************************************************************************************
     * Function name: explicitWait Description: Wait until expected condition is
     * satisfied, TestObject Content of variable: Properties of Webelement
     * Output: NA
     *********************************************************************************************/

    public boolean explicitWait(String condition, String input) throws Exception {
	try {
	    String cond = condition.toLowerCase();
	    switch (cond) {
	    default:
		throw new Exception("No Such Condition");
	    case "elementispresent":
		wait.until(ExpectedConditions.presenceOfElementLocated(byLocator(attributeType, attribute)));
		break;
	    case "elementisvisible":
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, attribute)));
		break;
	    case "elementisinvisible":
		wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator(attributeType, attribute)));
		break;
	    case "elementtobeclickable":
		wait.until(ExpectedConditions.elementToBeClickable(byLocator(attributeType, attribute)));
		break;
	    case "elementtobeselected":
		wait.until(ExpectedConditions.elementToBeSelected(byLocator(attributeType, attribute)));
		break;
	    case "textpresentinelement":
		wait.until(ExpectedConditions.textToBePresentInElementLocated(byLocator(attributeType, attribute), input));
		break;
	    case "valuepresentinelement":
		wait.until(ExpectedConditions.textToBePresentInElementValue(byLocator(attributeType, attribute), input));
		break;
	    case "titlecontains":
		wait.until(ExpectedConditions.titleContains(input));
		break;
	    case "titleis":
		wait.until(ExpectedConditions.titleIs(input));
		break;
	    case "alertispresent":
		wait.until(ExpectedConditions.alertIsPresent());
		break;
	    case "frametobeavailable":
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(byLocator(attributeType, attribute)));
		break;
	    case "elementsarepresent":
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byLocator(attributeType, attribute)));
		break;
	    case "elementsarevisible":
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator(attributeType, attribute)));
		break;
	    case "elementisnotpresent":
		wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(byLocator(attributeType, attribute))));
		break;
	    case "elementisnotvisible":
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, attribute))));
		break;
	    case "elementisnotinvisible":
		wait.until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(byLocator(attributeType, attribute))));
		break;
	    case "elementnottobeclickable":
		wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(byLocator(attributeType, attribute))));
		break;
	    case "elementnottobeselected":
		wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(byLocator(attributeType, attribute))));
		break;
	    case "textnotpresentinelement":
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(byLocator(attributeType, attribute), input)));
		break;
	    case "valuenotpresentinelement":
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(byLocator(attributeType, attribute), input)));
		break;
	    case "titlenotcontains":
		wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(input)));
		break;
	    case "titleisnot":
		wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(input)));
		break;
	    case "alertisnotpresent":
		wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
		break;
	    case "framenottobeavailable":
		wait.until(ExpectedConditions.not(ExpectedConditions.frameToBeAvailableAndSwitchToIt(byLocator(attributeType, attribute))));
		break;
	    case "elementsarenotpresent":
		wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(byLocator(attributeType, attribute))));
		break;
	    case "elementsarenotvisible":
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator(attributeType, attribute))));
	    case "urlcontains":
		wait.until(ExpectedConditions.urlContains(input));
		break;
	    }

	    testObject = getElement();
	    // testObject = driver.findElement(byLocator(attributeType,
	    // attribute));
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Step action name: InputFileName Description: Enter filename of file to
     * upload Input variables: text Content of variable: Filename Output:
     * objSeleniumDriver.FileUpload
     *********************************************************************************************/

    public boolean fileUpload(String UploadFileName) throws Exception {
	boolean upload = false;
	for (int i = 0; i < UploadFileName.length(); i++) {
	    char KeyBoardInput = UploadFileName.charAt(i);
	    Robot r;
	    try {
		r = new Robot();
		switch (KeyBoardInput) {
		case 'A':
		    r.keyPress(KeyEvent.VK_A);
		    r.keyRelease(KeyEvent.VK_A);
		    break;
		case 'B':
		    r.keyPress(KeyEvent.VK_B);
		    r.keyRelease(KeyEvent.VK_B);
		    break;
		case 'C':
		    r.keyPress(KeyEvent.VK_C);
		    r.keyRelease(KeyEvent.VK_C);
		    break;
		case 'D':
		    r.keyPress(KeyEvent.VK_D);
		    r.keyRelease(KeyEvent.VK_D);
		    break;
		case 'E':
		    r.keyPress(KeyEvent.VK_E);
		    r.keyRelease(KeyEvent.VK_E);
		    break;
		case 'F':
		    r.keyPress(KeyEvent.VK_F);
		    r.keyRelease(KeyEvent.VK_F);
		    break;
		case 'G':
		    r.keyPress(KeyEvent.VK_G);
		    r.keyRelease(KeyEvent.VK_G);
		    break;
		case 'H':
		    r.keyPress(KeyEvent.VK_H);
		    r.keyRelease(KeyEvent.VK_H);
		    break;
		case 'I':
		    r.keyPress(KeyEvent.VK_I);
		    r.keyRelease(KeyEvent.VK_I);
		    break;
		case 'J':
		    r.keyPress(KeyEvent.VK_J);
		    r.keyRelease(KeyEvent.VK_J);
		    break;
		case 'K':
		    r.keyPress(KeyEvent.VK_K);
		    r.keyRelease(KeyEvent.VK_K);
		    break;
		case 'L':
		    r.keyPress(KeyEvent.VK_L);
		    r.keyRelease(KeyEvent.VK_L);
		    break;
		case 'M':
		    r.keyPress(KeyEvent.VK_M);
		    r.keyRelease(KeyEvent.VK_M);
		    break;
		case 'N':
		    r.keyPress(KeyEvent.VK_N);
		    r.keyRelease(KeyEvent.VK_N);
		    break;
		case 'O':
		    r.keyPress(KeyEvent.VK_O);
		    r.keyRelease(KeyEvent.VK_O);
		    break;
		case 'P':
		    r.keyPress(KeyEvent.VK_P);
		    r.keyRelease(KeyEvent.VK_P);
		    break;
		case 'Q':
		    r.keyPress(KeyEvent.VK_Q);
		    r.keyRelease(KeyEvent.VK_Q);
		    break;
		case 'R':
		    r.keyPress(KeyEvent.VK_R);
		    r.keyRelease(KeyEvent.VK_R);
		    break;
		case 'S':
		    r.keyPress(KeyEvent.VK_S);
		    r.keyRelease(KeyEvent.VK_S);
		    break;
		case 'T':
		    r.keyPress(KeyEvent.VK_T);
		    r.keyRelease(KeyEvent.VK_T);
		    break;
		case 'U':
		    r.keyPress(KeyEvent.VK_U);
		    r.keyRelease(KeyEvent.VK_U);
		    break;
		case 'V':
		    r.keyPress(KeyEvent.VK_V);
		    r.keyRelease(KeyEvent.VK_V);
		    break;
		case 'W':
		    r.keyPress(KeyEvent.VK_W);
		    r.keyRelease(KeyEvent.VK_W);
		    break;
		case 'X':
		    r.keyPress(KeyEvent.VK_X);
		    r.keyRelease(KeyEvent.VK_X);
		    break;
		case 'Y':
		    r.keyPress(KeyEvent.VK_Y);
		    r.keyRelease(KeyEvent.VK_Y);
		    break;
		case 'Z':
		    r.keyPress(KeyEvent.VK_Z);
		    r.keyRelease(KeyEvent.VK_Z);
		    break;
		case '1':
		    r.keyPress(KeyEvent.VK_1);
		    r.keyRelease(KeyEvent.VK_1);
		    break;
		case '2':
		    r.keyPress(KeyEvent.VK_2);
		    r.keyRelease(KeyEvent.VK_2);
		    break;
		case '3':
		    r.keyPress(KeyEvent.VK_3);
		    r.keyRelease(KeyEvent.VK_3);
		    break;
		case '4':
		    r.keyPress(KeyEvent.VK_4);
		    r.keyRelease(KeyEvent.VK_4);
		    break;
		case '5':
		    r.keyPress(KeyEvent.VK_5);
		    r.keyRelease(KeyEvent.VK_5);
		    break;
		case '6':
		    r.keyPress(KeyEvent.VK_6);
		    r.keyRelease(KeyEvent.VK_6);
		    break;
		case '7':
		    r.keyPress(KeyEvent.VK_7);
		    r.keyRelease(KeyEvent.VK_7);
		    break;
		case '8':
		    r.keyPress(KeyEvent.VK_8);
		    r.keyRelease(KeyEvent.VK_8);
		    break;
		case '9':
		    r.keyPress(KeyEvent.VK_9);
		    r.keyRelease(KeyEvent.VK_9);
		    break;
		case '0':
		    r.keyPress(KeyEvent.VK_0);
		    r.keyRelease(KeyEvent.VK_0);
		    break;
		case ':':
		    r.keyPress(KeyEvent.VK_SHIFT);
		    r.keyPress(KeyEvent.VK_SEMICOLON);
		    r.keyRelease(KeyEvent.VK_SEMICOLON);
		    r.keyRelease(KeyEvent.VK_SHIFT);
		    break;
		case '\\':
		    r.keyPress(KeyEvent.VK_BACK_SLASH);
		    r.keyRelease(KeyEvent.VK_BACK_SLASH);
		    break;
		case '.':
		    r.keyPress(KeyEvent.VK_PERIOD);
		    r.keyRelease(KeyEvent.VK_PERIOD);
		    break;
		case '!':
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    break;
		default:
		    r.keyPress(KeyEvent.VK_ENTER);
		    r.keyRelease(KeyEvent.VK_ENTER);
		    break;
		}
		upload = true;

	    } catch (Exception e) {
		exceptionMessage(e);
		upload = false;
	    }
	}
	return upload;
    }

    /*********************************************************************************************
     * Function name: fireevent Description: Select data in GUI item Input
     * variables: TestObject AND String EventName AND Boolean IsPlain_JS Content
     * of variable: Testobject name of GUI item Name of item that will be
     * selected in GUI Output: ((JavascriptExecutor)
     * driver).executeScript(EventName);
     * 
     * onEventName = EventName; EventName = EventName.substring(2);
     * 
     * ExecuteScript(testObject, script);
     *********************************************************************************************/

    public void fireEvent(String EventName, Boolean IsPlain_JS) throws Exception {
	String script;
	String onEventName;
	try {
	    if (IsPlain_JS) {
		((JavascriptExecutor) driver).executeScript(EventName);
	    } else {
		testObject = waitAndGetElement();
		EventName = EventName.trim().toLowerCase();
		// Event name should starts with on for internet explorer.
		if (EventName.startsWith("on")) {
		    onEventName = EventName;
		    EventName = EventName.substring(2);
		} else {
		    onEventName = "on" + EventName;
		}
		script = "var canBubble = false;var element = arguments[0];if (document.createEventObject()) {var evt = document.createEventObject();arguments[0].fireEvent('"
		        + onEventName
		        + "', evt);}    else {var evt = document.createEvent(\"HTMLEvents\");evt.initEvent('"
		        + EventName
		        + "', true, true);arguments[0].dispatchEvent(evt);}";

		// Firefox and other browser has to force this script.
		if (!property.getBrowserName().equalsIgnoreCase("IE")) {
		    script = "var evt = document.createEvent(\"HTMLEvents\"); evt.initEvent(\"" + EventName
		            + "\", true, true );return !arguments[0].dispatchEvent(evt);";
		}
		executeScript(testObject, script);
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	}
    }

    /*********************************************************************************************
     * Function name: getCSSProperty Description: Get css value Input variables:
     * TestObject Content of variable: Testobject name of GUI item Output: NA
     *********************************************************************************************/

    public String getCSSProperty(String attribute) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    return testObject.getCssValue(attribute);
	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Function name: getImageDim Description: Get image dimension Input
     * variables: NA Content of variable: Output: Image dimension
     * 
     * @throws Exception
     *********************************************************************************************/

    public Dimension getImageDim(File expImgFile) throws IOException {
	ImageInputStream in;
	ImageReader reader = null;
	try {
	    in = ImageIO.createImageInputStream(expImgFile);
	    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
	    if (readers.hasNext()) {
		reader = readers.next();
		reader.setInput(in);
	    }
	} catch (Exception e) {
	    property.setStepDescription("Exception occurred: " + e);
	} finally {
	    reader.dispose();
	}
	return new Dimension(reader.getWidth(0), reader.getHeight(0));
    }

    /*********************************************************************************************
     * Function name: getJenkinsURL Description: Get Jenkins url Input
     * variables: - NA Content of variable: Output: Jenkins url.
     *********************************************************************************************/

    public String getJenkinsURL(String url) {
	String text = "";
	String urlPath = "";
	String jenkinsURL = "";

	try {
	    if (url.contains("forge-images")) {
		String block[] = url.split("/");
		for (String st : block) {
		    text = text + st + "/";
		    if (st.equals("forge-images")) {
			urlPath = url.replace(text, "");
		    }
		}
		jenkinsURL = property.forgeURL + urlPath;
	    } else {
		jenkinsURL = url;
	    }
	} catch (Exception e) {
	    property.setStepDescription("Exception occurred: " + e);
	}
	return jenkinsURL;
    }

    /*********************************************************************************************
     * Function name: GetObjectProperty Description: Get an object property
     * Input variables: TestObject AND property Content of variable: Testobject
     * name of GUI item Item number of data that need the be selected Output:
     * ActualPropertyValue
     *********************************************************************************************/

    public String getObjectProperty(String property) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String ActualPropertyValue = "";
	    Boolean enable;
	    String JavaScript = "";
	    if (property.equalsIgnoreCase("text")) {
		ActualPropertyValue = testObject.getText();

	    } else if (property.equalsIgnoreCase("style.background") || property.equalsIgnoreCase("style.backgroundimage")
	            || property.equalsIgnoreCase("style.background-image")) {
		if (this.property.getBrowserName().equalsIgnoreCase("firefox")) {
		    JavaScript = "return document.defaultView.getComputedStyle(arguments[0], '').getPropertyValue(\"background-image\");";
		} else if (this.property.getBrowserName().equalsIgnoreCase("IE")) {
		    JavaScript = "return arguments[0].currentStyle.backgroundImage;";
		} else {
		    JavaScript = "return arguments[0].currentStyle.backgroundImage;";
		}
		// ActualPropertyValue = this.ExecuteScript(JavaScript);

	    } else if (property.equalsIgnoreCase("tagname")) {
		ActualPropertyValue = testObject.getTagName();
	    } else if (property.equalsIgnoreCase("isEnable")) {
		enable = testObject.isEnabled();
		ActualPropertyValue = enable.toString();
	    } else if (property.equalsIgnoreCase("background-color")) {
		ActualPropertyValue = testObject.getCssValue(property);
	    } else if (property.equalsIgnoreCase("bounds")) {

		Point upperLeftCornerlocation = testObject.getLocation();
		int startX = upperLeftCornerlocation.getX();
		int startY = upperLeftCornerlocation.getY();

		org.openqa.selenium.Dimension elementSize = testObject.getSize();
		int elementWidthX = elementSize.getWidth();
		int elementHeightY = elementSize.getHeight();

		ActualPropertyValue = "[" + startX + "," + startY + "][" + (startX + elementWidthX) + "," + (startY + elementHeightY) + "]";

	    } else {
		ActualPropertyValue = testObject.getAttribute(property);
	    }
	    return ActualPropertyValue;

	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Step action name: GetPageProperty Description: Get a Page property Input
     * variables: Property Content of variable: title url Output:
     * driver.getTitle() driver.getCurrentUrl()
     *********************************************************************************************/

    public String getPageProperty(String Property) throws Exception {
	try {
	    if (Property.equalsIgnoreCase("title")) {
		return driver.getTitle();
	    } else if (Property.equalsIgnoreCase("url")) {
		return driver.getCurrentUrl();
	    } else {
		return null;
	    }
	} catch (WebDriverException e) {
	    if (e.getMessage().contains("No response from server for url")) {
		throw new WebDriverException("No response from driver");
	    }
	    throw new Exception("No response from driver. Session not exist.");
	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}

    }

    /*********************************************************************************************
     * Function name: getWindowHandles Description: Get WindowHandles unique
     * name Input variables: None Content of variable: NA Output: windowHandles
     *********************************************************************************************/

    public Set<String> getWindowHandles() throws Exception {
	try {
	    if (!this.property.getBrowserName().contains("native")) {
		Set<String> windowHandles = driver.getWindowHandles();
		return windowHandles;
	    } else {
		return null;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return null;
	}

    }

    /*********************************************************************************************
     * Function name: goback Description: Go one page backward in browser Input
     * variables: None Content of variable: NA Output: driver.navigate().back();
     *********************************************************************************************/

    public boolean goBack() throws Exception {
	try {
	    driver.navigate().back();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: goforward Description: Go one page forward in browser
     * Input variables: None Content of variable: NA Output:
     * driver.navigate().forward();
     *********************************************************************************************/

    public boolean goForward() throws Exception {
	try {
	    driver.navigate().forward();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: isEnable Description: check if TestObject is enabled Input
     * variables: TestObject Content of variable: Testobject name of GUI item
     * Output: true/false
     *********************************************************************************************/

    public boolean isEnable() throws Exception {
	boolean elEnable = false;
	try {
	    explicitWait("elementisvisible", null);
	    if (testObject.isEnabled()) {
		elEnable = true;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    elEnable = false;
	}
	return elEnable;
    }

    /*********************************************************************************************
     * Function name: isNotEnable Description: check if a TestObject is not
     * enabled Input variables: TestObject Content of variable: Testobject name
     * of GUI item Output: true/false
     *********************************************************************************************/

    public boolean isNotEnable() throws Exception {
	boolean elEnable = false;
	try {
	    explicitWait("elementisvisible", null);
	    if (!testObject.isEnabled()) {
		elEnable = true;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    elEnable = false;
	}
	return elEnable;
    }

    /*********************************************************************************************
     * Function name: keypress Description: Press specific key on GUI item Input
     * variables: TestObject AND String Key Content of variable: Testobject name
     * of GUI item Keyboard key that will be used in GUI Output:
     * testObject.sendKeys(Keys.ENTER); testObject.sendKeys(Keys.ARROW_DOWN);
     * testObject.sendKeys(Keys.BACK_SPACE);
     *********************************************************************************************/

    public void keyPress(String Key) throws Exception {
	explicitWait("elementisvisible", null);
	if (Key.equalsIgnoreCase("enter")) {
	    testObject.sendKeys(Keys.ENTER);
	} else if (Key.equalsIgnoreCase("arrowdown")) {
	    testObject.sendKeys(Keys.ARROW_DOWN);
	} else if (Key.equalsIgnoreCase("backspace")) {
	    testObject.sendKeys(Keys.BACK_SPACE);
	}
    }

    /*********************************************************************************************
     * Function name: mouseOver Description: Mouse over on element Input
     * variables: TestObject Content of variable: Testobject name of GUI item
     * Output: NA
     *********************************************************************************************/

    public boolean mouseOver() throws Exception {
	try {
	    explicitWait("elementisvisible", null);

	    org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
	    action.moveToElement(testObject).build().perform();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: navigateurl Description: Navigate to URL in currently
     * opened browser Input variables: String Url Content of variable: URL
     * Output: driver.navigate().to(Url);
     *********************************************************************************************/

    public boolean navigateUrl(String Url) throws Exception {
	try {
	    driver.navigate().to(Url);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Step action name: openBrowser Description: Open a new browser and
     * navigate to URL Input variables: Url BrowserName Content of variable: URL
     * Output: objSeleniumImplementation
     * 
     * @throws Exception
     *********************************************************************************************/

    // This method is not even called for Native App Automation
    public WebDriver openBrowser(String Url, WebDriver driver) throws Exception {
	try {
	    // if (!property.getBrowserName().equalsIgnoreCase("IE")) {
	    this.deleteAllCookies();
	    // }

	    objLog.writeInfo("Current ViewPort is: " + getViewPort());

	    try {
		int w = Integer.parseInt(property.breakPoint.split(",")[0]);
		int h = Integer.parseInt(property.breakPoint.split(",")[1]);
		objLog.writeInfo("Resizing window to " + property.breakPoint + " : " + w + "x" + h);

		/**
		 * This code is the work around of the error that java script
		 * stops loading. Need to find the proper solution later.
		 */
		/*
		 * if (!property.getBrowserName().equalsIgnoreCase("firefox")) {
		 */
		windowResize(w, h);
		/*
		 * } else { objLog.writeInfo(
		 * "Not resizing the browser window for firefox browser."); }
		 */
	    } catch (WebDriverException e) {
		System.out.println("Exception occured while resizing/maximizing the browser window.");
	    }
	    String viewPort;

	    objLog.writeInfo("New ViewPort is: " + getViewPort());
	    viewPort = getViewPort();

	    try {
		viewPort = viewPort.replace("(", "").replace(")", "");
		int w = Integer.parseInt(viewPort.split(",")[0].trim());
		int h = Integer.parseInt(viewPort.split(",")[1].trim());
		property.breakPoint = w + "," + h;
	    } catch (NumberFormatException e1) {
		System.out.println("Number format exception occurred.");
	    }

	    objLog.writeInfo("Opening..." + Url);

	    try {
		driver.get(Url);
		System.out.println(driver.getCurrentUrl());
	    } catch (UnhandledAlertException e) {
		System.out.println(e);
		driver.navigate().refresh();
		Thread.sleep(5000);
		final File file = new File("src/main/resources/utils/iExploreScript_Modal.exe");
		Process p = Runtime.getRuntime().exec(file.getAbsolutePath());
		p.waitFor();
		if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
		        && !this.property.getBrowserName().contains("native"))
		    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	    }

	    property.setRemarks(Url);
	    rec = 0;
	} catch (WebDriverException e) {
	    if (rec < 5) {
		rec++;
		driver.close();
		driver = null;
		driver = initDriver();
		openBrowser(Url, driver);
	    } else {
		verification = false;
		exceptionMessage(e);
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	    verification = false;
	}
	return driver;
    }

    public WebDriver initDriverIOS(String data) {
	try {
	    if (this.property.IsRemoteExecution.equalsIgnoreCase("true")) {

		objLog.writeInfo("Remote execution is true");

		String remoteURL = this.property.RemoteURL + "/wd/hub";
		URL uri = new URL(remoteURL);
		final File appDir = new File("/Users/forgeuser/Documents/forge-grid");
		final File app = new File(appDir, "ChicagoTribune.app");
		DesiredCapabilities createCapabilities = createCapabilities(app.getAbsolutePath(), data);
		try {
		    driver = new IOSDriver(uri, createCapabilities);
		    ThreadSleep(2500);
		} catch (Exception e) {
		    driver = new IOSDriver(uri, createCapabilities);
		    ThreadSleep(2500);
		}
	    } else {
		objLog.writeInfo("Remote execution is false");
		final File classpathRoot = new File(System.getProperty("user.dir"));
		final File appDir = new File(classpathRoot, "src/main/resources/apps/ios/");
		final File app = new File(appDir, "ChicagoTribune.app");

		DesiredCapabilities createCapabilities = createCapabilities(app.getAbsolutePath(), data);

		driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), createCapabilities);
		ThreadSleep(2500);

	    }
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
	            && !this.property.getBrowserName().contains("native")) {
		driver.manage().timeouts().pageLoadTimeout(this.property.maxTimeOut, TimeUnit.SECONDS);
	    }
	    // if (this.property.getBrowserName().contains("ios") &&
	    // this.property.getBrowserName().contains("native")) {
	    // wait = new WebDriverWait(driver, iOSNativeAppWait);
	    // } else {
	    wait = new WebDriverWait(driver, property.maxTimeOut);
	    // }

	    if (!data.equalsIgnoreCase("withOnboard")) {
		// fix for iOS Onboarding
		if (this.property.getBrowserName().contains("ios") && this.property.getBrowserName().contains("native")) {
		    try {
			for (int i = 0; i < 4; i++) {

			    wait.until(ExpectedConditions.elementToBeClickable(MobileBy.IosUIAutomation(".buttons()[0]")));
			    driver.findElement(MobileBy.IosUIAutomation(".buttons()[0]")).click();
			}
		    } catch (TimeoutException | NoSuchElementException e) {
			e.printStackTrace();
		    }
		}
	    }

	} catch (NullPointerException e) {
	    this.property.setStepDescription("Exception occurred at driver initialization: " + e.getMessage());
	    this.property.setRemarks("Driver initialization failed. ");
	    objLog.writeError("Exception occurred at driver initialization: " + e.getMessage());
	    verification = false;
	} catch (Exception e) {
	    this.property.setStepDescription("Exception occurred at driver initialization: " + e.getMessage());
	    this.property.setRemarks("Driver initialization failed. ");
	    objLog.writeError("Exception occurred at driver initialization: " + e.getMessage());
	    verification = false;
	}
	return driver;
    }

    /*********************************************************************************************
     * Step action name: RandomNameGenerator Description: Generates a Random
     * name Input variables: NA Content of variable: NA Output: Random Name
     *********************************************************************************************/

    public static String randomNameGenerator() throws Exception {

	Random rand = new Random();
	String randomName = "";
	int n = rand.nextInt(10000) + 1;
	randomName = n + "";

	return randomName;

    }

    /*********************************************************************************************
     * Function name: refreshBrowser Description: Refresh currently opened
     * browser Input variables: None Content of variable: NA Output:
     * driver.navigate().refresh();
     *********************************************************************************************/

    public boolean refreshBrowser() throws Exception {
	try {
	    driver.navigate().refresh();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Step action name: SelectElementByIndex Description: Select specific item
     * by index number Input variables: TestObject AND Index Content of
     * variable: Testobject name of GUI item Item number of data that need the
     * be selected Output: testObject.getAttribute("value")
     * options.get(index).getText();
     *********************************************************************************************/

    public String selectElementByIndex(String Index) throws Exception {
	try {

	    int index = Integer.parseInt(Index) - 1;
	    explicitWait("elementisvisible", null);
	    if (testObject.getAttribute("type").equalsIgnoreCase("radio")) {
		testObject = testObjects.get(index);
		this.executeScript(testObject, "arguments[0].click();");
		return testObject.getAttribute("value");
	    }

	    else {
		Select select = new Select(testObject);
		select.selectByIndex(index);
		List<WebElement> options = select.getOptions();
		return options.get(index).getText();
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Function name: selectitem Description: Select specific item from dropdown
     * menu Input variables: TestObject AND ItemtoSelect Content of variable:
     * Testobject name of dropdown menu Data that need the be selected from menu
     * Output: selectedOption
     *********************************************************************************************/

    public boolean selectItem(String itemtoSelect) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    ThreadSleep(1000);
	    Select objSelect = new Select(testObject);
	    ThreadSleep(1000);
	    objSelect.selectByVisibleText(itemtoSelect);
	    return true;

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: sendKeys Description: Enter/type data in GUI item Input
     * variables: String Text Content of variable: Value that will be entered in
     * GUI item Output: this.check(); this.Uncheck();
     * this.ExecuteScript(testObject, "arguments[0].value=\"\";");
     * testObject.sendKeys(Text); testObject = waitAndGetElement();
     * testObject.clear(); testObject.click();
     *********************************************************************************************/

    public Boolean sendKeys(String Text) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    // testObject = getElement();
	    testObject.clear();
	    testObject.sendKeys(Text);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public Boolean clearTextField() throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    testObject.clear();
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public Boolean enterRandomEmailIDinTextField() throws Exception {
	try {
	    explicitWait("elementisvisible", null);

	    Random rand = null;
	    int randomNum = 10 + (int) (Math.random() * ((100000 - 10) + 1));

	    testObject.clear();
	    testObject.sendKeys("xebiarandom" + String.valueOf(randomNum) + "@gmail.com");

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: SetAttribute Description: ? Input variables: Property
     * PropertyValue Content of variable: ? Output: ?
     *********************************************************************************************/

    public void setAttribute(String Property, String PropertyValue) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String JavaScript = "return arguments[0]." + Property + " = \"" + PropertyValue + "\";";
	    this.executeScript(testObject, JavaScript);

	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}

    }

    /*********************************************************************************************
     * Function name: setObjectDataRow Description: Sets objDatarow with the
     * content of the 'how'[id/css/xpath/name] and 'what'[locator attribute]
     * cells of every step Input variables: HashMap<String, String> objDatarow.
     * This represents the data from a particular row from a TC. Content of
     * variable: Location of content Output: objDatarow of this class
     *********************************************************************************************/

    public void setObjectDataRow(HashMap<String, String> objDatarow) {
	if (objDatarow != null) {
	    attributeType = utility.replaceVariableInString(objDatarow.get("how"));
	    allAttributes = utility.replaceVariableInString(objDatarow.get("what")).split("#");
	    attribute = allAttributes[0];
	    nthElement = Integer.parseInt(utility.replaceVariableInString(objDatarow.get("nthElement")));
	    // Will add more if needed.
	}
	objDataRow = objDatarow;
    }

    /*********************************************************************************************
     * Function name: shutDown Description: Close Browser window Input
     * variables: None Content of variable: NA Output: driver.close() or
     * driver.quit()
     *********************************************************************************************/
    public void shutDown() throws Exception {
	try {

	    if (driver.getTitle() != null || driver.getTitle() != "") {
		driver.close();
		try {
		    driver.quit();
		} catch (Exception e) {
		}
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Function name: signInPopUp Description: Handle the unwanted pop-ups on
     * the page Input variables:
     * 
     * Content of variable: Output: Handle the unwanted pop-ups on the page
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean signInPopUp() throws Exception {
	try {
	    ThreadSleep(1500);

	    new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(byLocator(attributeType, attribute)));

	    driver.findElement(byLocator(attributeType, attribute)).click();

	} catch (NoSuchElementException e) {
	    System.out.println("Object not displayed.");
	    skip = true;
	} catch (Exception e) {
	    skip = true;
	}
	return true;
    }

    /*********************************************************************************************
     * Function name: switchToIframe Description: Switch to iframe Input
     * variables: NA Content of variable: Output:NA
     * 
     * @return
     *********************************************************************************************/
    public boolean switchToIframe() throws Exception {
	explicitWait("elementisvisible", null);
	try {
	    driver.switchTo().frame(testObject);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: SwitchToMostRecentWindow Description: Switch to most
     * recent Window Input variables: None Content of variable: NA Output:
     * driver.getWindowHandle
     *********************************************************************************************/

    public String switchToMostRecentWindow() throws Exception {
	try {
	    if (!this.property.getBrowserName().contains("native")) {
		Set<String> windowHandles = getWindowHandles();
		for (String window : windowHandles) {
		    driver.switchTo().window(window);
		}
		if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")) {
		    driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
		}
	    } else {
		return null;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return "";

	}

	return driver.getWindowHandle();
    }

    /*********************************************************************************************
     * Function name: takeScreenShot Description: Takes screenshot of the
     * current browser page the page Input variables: - Test Case Name Content
     * of variable: Output: Save the Screenshot to the screenshot folder inside
     * test/resources as testcase name.
     * 
     * @throws IOException
     * 
     * @throws Exception
     *********************************************************************************************/
    public String takeScreenShot() throws WebDriverException, IOException {

	String shotName = "";
	File jenkinsImageDir = null;
	File localImageDir = null;
	File file = null;
	File screenshot = null;
	String viewPort = getViewPort();
	viewPort = viewPort.replace("(", "").replace(")", "");
	int w = Integer.parseInt(viewPort.split(",")[0].trim());
	int h = Integer.parseInt(viewPort.split(",")[1].trim());
	File jfile = new File(property.actualImageJenkinPath, utility.getDate() + property.FileSeperator.get() + Property.getExecutionTime());
	try {
	    screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    if (property.IsRemoteExecution.equalsIgnoreCase("true") && property.localGrid.equalsIgnoreCase("false")) {
		shotName = property.FileSeperator.get() + property.getTestName() + "_" + property.getBrowserName() + "_" + w + "x" + h + "_"
		        + property.getScreenShotCounter() + "." + property.IMAGE_TYPE;
		file = new File(jfile + property.FileSeperator.get() + "Screenshot/", shotName);
	    } else {
		shotName = property.ScreenShotPath // +
		        // Property.getBuildEnvFileStruc()
		        + property.getTestName() + "_" + property.getBrowserName() + "_" + w + "x" + h + "_" + property.getScreenShotCounter()
		        + "."
		        + property.IMAGE_TYPE;
		file = new File(System.getProperty("user.dir"), shotName);
	    }

	    objLog.writeInfo("Screenshot path : " + file.getAbsolutePath());
	    if (file.exists()) {
		file.delete();
	    }
	    FileUtils.copyFile(screenshot, file);
	    return getJenkinsURL(file.toURI().toString());
	} catch (WebDriverException e) {
	    property.setStepDescription("Screenshot capture Failed:  " + e.getMessage());
	    objLog.writeError("Screenshot capture Failed:  " + e.getMessage());
	    property.setRemarks("Screenshot capture Failed.");
	    return null;
	}
    }

    /*********************************************************************************************
     * Function name: Uncheck() Description: Uncheck Checkbox GUI item Input
     * variables: TestObject Content of variable: Testobject name of Checkbox
     * Output: testObject.click();
     *********************************************************************************************/

    public boolean uncheck() throws Exception {
	explicitWait("elementtobeclickable", null);
	try {
	    if (testObject.isSelected()) {
		testObject.click();
	    }
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyImagepixel Description: Verify image pixel
     * variables: - NA Content of variable: Output: NA.
     *********************************************************************************************/

    public boolean verifyColorChange(String data) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String beforecolor = getCSSProperty("background-color");
	    mouseOver();
	    String aftercolor = getCSSProperty("background-color");

	    if (!aftercolor.equalsIgnoreCase(data)) {
		return true;
	    } else {
		property.setRemarks("Expected is: " + data + " but Actual is :" + aftercolor);
		return false;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    // FireEvent.
    // KeyPress.
    // GetElementFromFrames.

    /*********************************************************************************************
     * Function name: verifyImagepixel Description: Verify image pixel
     * variables: - NA Content of variable: Output: NA.
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean verifyImagepixel(String pixel) throws Exception {
	boolean verify = false;

	try {
	    explicitWait("elementisvisible", null);
	    String h = getCSSProperty("height");
	    String w = getCSSProperty("width");
	    if ((h + "x" + w).equals(pixel)) {
		verify = true;
	    }

	} catch (Exception e) {
	    property.setStepDescription("Exception occurred: " + e);
	    exceptionMessage(e);
	    verify = false;
	}
	return verify;
    }

    /*********************************************************************************************
     * Function name: verifyObjectAtrribute Description: Get an attribute value
     * Input variables: TestObject, DataContentFirst AND property Content of
     * variable: Testobject name of GUI item Item number of data that need the
     * be selected Output: NA
     *********************************************************************************************/

    public boolean verifyObjectAttribute(String property, String value) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String ActualPropertyValue;
	    if (property.equalsIgnoreCase("bounds")) {

		Point upperLeftCornerlocation = testObject.getLocation();
		int startX = upperLeftCornerlocation.getX();
		int startY = upperLeftCornerlocation.getY();

		org.openqa.selenium.Dimension elementSize = testObject.getSize();
		int elementWidthX = elementSize.getWidth();
		int elementHeightY = elementSize.getHeight();

		ActualPropertyValue = "[" + startX + "," + startY + "][" + (startX + elementWidthX) + "," + (startY + elementHeightY) + "]";

	    } else {
		ActualPropertyValue = testObject.getAttribute(property);
	    }

	    return verifyStatus(value, ActualPropertyValue);
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyObjectCSS Description: Get an css property Input
     * variables: TestObject, DataContentFirst AND property Content of variable:
     * Testobject name of GUI item Item number of data that need the be selected
     * Output: NA
     *********************************************************************************************/

    public boolean verifyObjectCSS(String property, String value) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String ActualPropertyValue = testObject.getCssValue(property);
	    return verifyStatus(value, ActualPropertyValue);
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyObjectNonclickable Description: Verify whether
     * object is non clickable variables: TestObject Content of variable:
     * Testobject name of GUI item Output: waitAndGetElement();
     * testObject.click();
     *********************************************************************************************/

    public boolean verifyObjectNonclickable() throws Exception {
	try {
	    // wait.until(ExpectedConditions.elementToBeClickable(testObject));
	    new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(byLocator(attributeType, attribute)));
	    property.setScreenshotName(takeScreenShot());
	    property.setStepDescription("Object is Clickable");
	    property.setRemarks("Object is Clickable:  screenshot");

	    return false;
	} catch (Exception e) {
	    return true;
	}
    }

    /*********************************************************************************************
     * Function name: verifyObjectNotPresent Description: Verify if Test Object
     * is not present on browser screen Input variables: TestObject Content of
     * variable: Testobject name of GUI item Output: status
     *********************************************************************************************/

    public boolean verifyObjectNotPresent() throws Exception {
	try {
	    if (this.property.getBrowserName().contains("native") && this.property.getBrowserName().contains("ios")) {
		new WebDriverWait(driver, property.maxTimeOutiOS).until(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, attribute)));
	    } else {
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, attribute)));
	    }
	    property.setScreenshotName(takeScreenShot());
	    property.setRemarks("Element is present on the page. screenshot");
	} catch (Exception e) {
	    return true;
	}
	return false;
    }

    /*********************************************************************************************
     * Function name: verifyObjectPresent Description: Verify if Test Object is
     * present on browser screen Input variables: TestObject Content of
     * variable: Testobject name of GUI item Output: status
     *********************************************************************************************/

    public boolean verifyObjectPresent() throws Exception {
	try {
	    ThreadSleep(3000);
	    ThreadSleep(5000);
	    explicitWait("elementsarevisible", null);
	    ThreadSleep(5000);
	    boolean status = testObject.isDisplayed();

	    if (status) {
		return true;
	    } else {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Element is not present on the page. screenshot");
		return false;
	    }

	} catch (NoSuchElementException e) {
	    exceptionMessage(e);
	    return false;
	}

	catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: VerifyObjectProperty Description: Get an css property
     * Input variables: TestObject, DataContentFirst AND property Content of
     * variable: Testobject name of GUI item Item number of data that need the
     * be selected Output: NA
     * 
     * @return
     *********************************************************************************************/

    public boolean verifyObjectProperty(String data, String property) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String ActualPropertyValue = "";
	    if (property.equalsIgnoreCase("data-adloader-size") || property.equalsIgnoreCase("data-outfits-clicktrackingurl")) {
		ActualPropertyValue = testObject.getAttribute(property);
	    } else if (property.equalsIgnoreCase("font-family") || property.equalsIgnoreCase("font-size") || property.equalsIgnoreCase("font-weight")
	            || property.equalsIgnoreCase("color")) {
		ActualPropertyValue = testObject.getCssValue(property);
	    }
	    return verifyStatus(data, ActualPropertyValue);

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyObjectProperty Description: Verify if given text is
     * present on browser screen Input variables: Property PropertyValue
     * ArrayList Content of variable: Check content of value Value of Text
     * Property Options like: {partialmatch}{ignorecase}{ignorestep} Output:
     * isPropertyVerified
     *********************************************************************************************/

    public boolean verifyObjectProperty(String Prop, String PropertyValue, ArrayList<String> options) throws Exception {
	try {
	    boolean isPropertyVerified = false;
	    String actualPropertyValue = this.getObjectProperty(Prop);

	    if (!options.isEmpty()) {
		isPropertyVerified = utility.doMatchBasedOnOptions(PropertyValue, actualPropertyValue);
	    } else {
		isPropertyVerified = actualPropertyValue.contains(PropertyValue);
	    }
	    if (!isPropertyVerified) {
		property.setScreenshotName(takeScreenShot());
		property.setStepDescription("Property - '" + Prop + "' , actual value - '" + actualPropertyValue + "' doesn't match with expected value - '"
		        + PropertyValue + "'. screenshot");
		property.setRemarks("Property - '" + Prop + "' , actual value - '" + actualPropertyValue + "' doesn't match with expected value - '"
		        + PropertyValue + "'. screenshot");
	    }
	    return isPropertyVerified;

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyPageDisplayed Description: ?? Input variables: None
     * Content of variable: NA Output: driver.getCurrentUrl().matches(attribute)
     *********************************************************************************************/

    public boolean verifyPageDisplayed() throws Exception {
	try {

	    return driver.getCurrentUrl().matches(attribute);

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyPageProperty Description: Verify if Page Property is
     * present on browser screen Input variables: Property PropertyValue
     * optionsList Content of variable: Name of Page Property (for example: URL,
     * title) Value of Page Property (for example: name of URL, name of URL)
     * {partialmatch}{ignorecase}{ignorestep} Output: IsPagePropertyVerify
     *********************************************************************************************/

    public boolean verifyPageProperty(String Property, String PropertyValue, ArrayList<String> options) throws Exception {
	try {
	    String ActualValue = this.getPageProperty(Property);
	    boolean IsPagePropertyVerify = false;
	    if (!options.isEmpty()) {
		IsPagePropertyVerify = utility.doMatchBasedOnOptions(PropertyValue, ActualValue);
	    }

	    else {
		if (ActualValue.contains(PropertyValue)) {
		    IsPagePropertyVerify = true;
		}
	    }

	    if (IsPagePropertyVerify) {
		return true;
	    } else {
		return false;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Function name: verifyParameterinURL Description: Verify Parameter in URL
     * variables: dataContentFirst Content of variable: Output:NA
     * 
     * @return
     *********************************************************************************************/

    private boolean verifyParameterinURL(String param, String value) throws Exception {
	try {
	    ThreadSleep(3000);
	    switchToMostRecentWindow();
	    ThreadSleep(3000);
	    String url = driver.getCurrentUrl();
	    String[] splitUrl = url.split("&");

	    for (String s : splitUrl) {
		if (s.contains(param + "=")) {
		    return verifyStatus(value, s.split("=")[1]);
		}
	    }
	    property.setRemarks("Parameter " + param + " not found in the url.");
	} catch (Exception e) {
	    exceptionMessage(e);
	}
	return false;
    }

    /*********************************************************************************************
     * Function name: verifyServerCall Description: Verify server call
     * variables: dataContentFirst Content of variable: Output:NA
     * 
     * @return
     *********************************************************************************************/

    public boolean verifyServerCall(String data) throws Exception {
	try {
	    explicitWait("elementisvisible", null);

	    String URL = testObject.getAttribute("src");
	    driver.switchTo().defaultContent();

	    return verifyStatus(data, URL);
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /*********************************************************************************************
     * Function name: verifySize Description: Verify size of element Input
     * variables: TestObject, DataContentFirst AND property Content of variable:
     * Testobject name of GUI item Item number of data that need the be selected
     * Output: NA
     *********************************************************************************************/

    public boolean verifySize(String data) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String elementSize = testObject.getSize().toString();

	    return verifyStatus(data, elementSize);

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyTextPresenceInRedirectedURL Description: Verify text
     * presence in url variables: - NA Content of variable: Output: NA.
     *********************************************************************************************/

    public boolean verifyURLPresenceRedirectedPage(String data) throws Exception {
	try {
	    if (!this.property.getBrowserName().contains("native")) {

		ThreadSleep(8000);
		if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")) {
		    driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
		}
		Set<String> windowHandles = getWindowHandles();
		boolean status = false;
		if (windowHandles.size() > 1) {
		    for (String window : windowHandles) {
			driver.switchTo().window(window);
		    }
		    status = verifyURL(data);
		    driver.close();
		    switchToMostRecentWindow();
		} else {
		    property.setRemarks("Click did not open a new window.");
		}
		return status;
	    } else {
		return true;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Step action name: verifyTextPresentOnPage Description: Verify if given
     * text is present on browser screen Input variables: text optionsList
     * Content of variable: Value of Text Property
     * {partialmatch}{ignorecase}{ignorestep} Output: isTextVerified
     *********************************************************************************************/

    public boolean verifyTextPresentOnPage(String text, ArrayList<String> options) throws Exception {
	try {
	    boolean isTextVerified = false;
	    text = text.trim();
	    text = utility.replaceVariableInString(text);
	    explicitWait("elementisvisible", null);

	    String pageText = testObject.getText();

	    objLog.writeInfo(pageText);

	    // if options contain either of
	    // {partialmatch}{ignorecase}{ignorestep} then doMatchBasedOnOptions
	    if (!options.isEmpty()) {
		isTextVerified = utility.doMatchBasedOnOptions(text, pageText);
	    } else {
		isTextVerified = pageText.contains(text);
	    }

	    if (!isTextVerified) {
		property.setRemarks("Text : " + text + " is not found on page");
	    }
	    return isTextVerified;
	} catch (WebDriverException e) {
	    exceptionMessage(e);
	    if (e.getMessage().contains("No response from server for url")) {
		throw new Exception("No response from Driver");
	    }
	    throw new Exception("No response from the Driver. Session not exists.");
	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    public boolean verifyTextNotPresentOnPage(String text, ArrayList<String> options) throws Exception {
	try {
	    boolean isTextVerified = false;
	    text = text.trim();
	    text = utility.replaceVariableInString(text);
	    explicitWait("elementisvisible", null);
	    if (nthElement == -1) {
		for (WebElement currectElement : findElements) {
		    String pageText = currectElement.getText();

		    objLog.writeInfo(pageText);
		    isTextVerified = pageText.contains(text);
		    if (isTextVerified) {
			break;
		    }
		}
	    } else {
		String pageText = testObject.getText();

		objLog.writeInfo(pageText);
		isTextVerified = pageText.contains(text);
	    }

	    if (isTextVerified == false) {
		property.setScreenshotName(takeScreenShot());
		objLog.writeError("Text : " + text + " is present on page");
		property.setRemarks("Text : " + text + " is present on page" + " screenshot");
	    }
	    return !isTextVerified;
	} catch (WebDriverException e) {
	    exceptionMessage(e);
	    if (e.getMessage().contains("No response from server for url")) {
		throw new Exception("No response from Driver");
	    }
	    throw new Exception("No response from the Driver. Session not exists.");
	} catch (Exception e) {
	    exceptionMessage(e);
	    throw e;
	}
    }

    /*********************************************************************************************
     * Function name: verifyTitle Description: verify title of page Input
     * variables: TestObject, DataContentFirst AND property Content of variable:
     * Testobject name of GUI item Item number of data that need the be selected
     * Output: NA
     *********************************************************************************************/

    public boolean verifyTitle(String data) throws Exception {
	try {

	    ThreadSleep(3000);
	    ThreadSleep(5000);
	    if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
	            && !this.property.getBrowserName().contains("native")) {
		driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
	    }
	    String actualTitle = driver.getTitle();

	    for (int i = 0; i < 5; i++) {
		if (actualTitle == null || actualTitle.equalsIgnoreCase("")) {

		    ThreadSleep(5000);
		    if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
		            && !this.property.getBrowserName().contains("native")) {
			driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
		    }
		    actualTitle = driver.getTitle();
		} else {
		    break;
		}
	    }

	    return verifyStatus(data, actualTitle);

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyURL Description: verify current URL of page Input
     * variables: TestObject, DataContentFirst AND property Content of variable:
     * Testobject name of GUI item Item number of data that need the be selected
     * Output: NA
     *********************************************************************************************/

    public boolean verifyURL(String data) throws Exception {
	try {

	    ThreadSleep(3000);
	    ThreadSleep(5000);
	    if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")
	            && !this.property.getBrowserName().contains("native")) {
		driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
	    }
	    String actualURL = driver.getCurrentUrl();
	    return verifyStatus(data, actualURL);

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyURL Description: Verify url variables: - NA Content
     * of variable: Output: NA.
     *********************************************************************************************/

    public boolean verifyTitlePresenceRedirectedPage(String data) throws Exception {
	try {
	    if (!this.property.getBrowserName().contains("native")) {

		ThreadSleep(8000);
		if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")) {
		    driver.manage().timeouts().pageLoadTimeout(property.maxTimeOut, TimeUnit.SECONDS);
		}
		boolean status = false;
		if (!this.property.getBrowserName().equalsIgnoreCase("safari") && !this.property.getBrowserName().equalsIgnoreCase("ie")) {
		    driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		}
		Set<String> windowHandles = getWindowHandles();
		if (windowHandles.size() > 1) {
		    for (String window : windowHandles) {
			driver.switchTo().window(window);
		    }
		    status = verifyTitle(data);
		    driver.close();
		    switchToMostRecentWindow();
		} else {
		    property.setRemarks("Click did not open a new window.");

		}
		return status;
	    } else {
		return true;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: waitAndGetElement Description: Wait until WebElement is
     * shown on screen and get WebElement Input variables: TestObject Content of
     * variable: Properties of Webelement Output: testObject
     *********************************************************************************************/

    public WebElement waitAndGetElement() throws Exception {
	try {
	    wait.until(new ExpectedCondition<WebElement>() {
		@Override
		public WebElement apply(WebDriver d) {
		    try {
			testObject = driver.findElement(byLocator(attributeType, attribute));
			return testObject;
		    } catch (Exception e) {
			property.setStepDescription("Exception occurred: " + e.getMessage());
			objLog.writeError("Exception occurred: " + e.getMessage());
			property.setRemarks("Exception occurred. Please see the logs for more details.");
			try {
			    property.setScreenshotName(takeScreenShot());
			} catch (WebDriverException | IOException e1) {
			    e1.printStackTrace();
			}
			return null;
		    }

		}
	    });
	    return testObject;

	} catch (TimeoutException exception) {
	    property.setScreenshotName(takeScreenShot());
	    property.setStepDescription("Unable to locate element: " + exception);
	    property.setRemarks("Unable to locate element. screenshot");
	    throw new NoSuchElementException("Unable to locate element. screenshot");

	}
    }

    /*********************************************************************************************
     * Function name: windowResize Description: Resize window Input variables:
     * NA Content of variable: Output: NA
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean windowResize(int width, int height) throws Exception {
	try {

	    if (this.property.getBrowserName().equalsIgnoreCase("chrome") || this.property.getBrowserName().equalsIgnoreCase("safari")
	            || this.property.getBrowserName().equalsIgnoreCase("firefox") || this.property.getBrowserName().equalsIgnoreCase("IE")) {

		driver.manage().window().setPosition(new Point(0, 0));

		if (width == 0 && height == 0) {
		    driver.manage().window().maximize();
		} else {
		    org.openqa.selenium.Dimension d = new org.openqa.selenium.Dimension(width, height);
		    driver.manage().window().setSize(d);
		}

	    }

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    property.setStepDescription("Error in Resizing the Window.Exception occurred: " + e);
	    property.setRemarks("Error in Resizing the Window. Exception occurred: " + e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyStatus Description: verify Status Input variables:
     * NA Content of variable: Output: NA
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean verifyStatus(String expected, String actual) throws Exception {
	try {
	    if (actual.contains(expected)) {
		return true;
	    } else {
		property.setRemarks("Expected is: " + expected + " but Actual is :" + actual + " screenshot");
		return false;
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyStatus Description: verify Status Input variables:
     * NA Content of variable: Output: NA
     * 
     * @throws Exception
     *********************************************************************************************/

    public boolean verifyStatus(int expected, int actual) throws Exception {
	try {
	    if (expected == actual) {
		return true;
	    } else {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Expected is: " + expected + " but Actual is :" + actual + " screenshot");
		return false;
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: exceptionMessage Description: Common Exception message
     * Input variables: NA Content of variable: Output: NA
     * 
     * @throws Exception
     *********************************************************************************************/

    public void exceptionMessage(Exception e) throws Exception {
	String err = " screenshot";
	property.setScreenshotName(takeScreenShot());
	property.setStepDescription("Exception occurred: " + e);
	objLog.writeError("Exception occurred: " + e);
	if (testObject == null) {
	    property.setRemarks("Unable to locate element." + err + " screenshot");
	} else if (e instanceof NoSuchElementException) {
	    property.setRemarks("Unable to locate element." + err + " screenshot");
	} else if (e instanceof ElementNotVisibleException) {
	    property.setRemarks("Element was not visible." + err + " screenshot");
	} else if (e instanceof MoveTargetOutOfBoundsException) {
	    property.setRemarks("Targeted element was outside of the size of the window." + err + " screenshot");
	} else if (e instanceof TimeoutException) {
	    property.setRemarks("Timeout exception occurred." + err + " screenshot");
	} else if (e instanceof StaleElementReferenceException) {
	    property.setRemarks("Slate element reference exception occurred." + err + " screenshot");
	} else if (e instanceof NoSuchFrameException) {
	    property.setRemarks("Frame was not present." + err + " screenshot");
	} else if (e instanceof ScreenshotException) {
	    property.setRemarks("Unable to take screenshot." + err + " screenshot");
	} else if (e instanceof NoAlertPresentException) {
	    property.setRemarks("Alert was not present." + err + " screenshot");
	} else if (e instanceof UnhandledAlertException) {
	    property.setRemarks("Unexpected alert found." + err + " screenshot");
	} else if (e instanceof NoSuchWindowException) {
	    property.setRemarks("Browser window was not found, it died." + err + " screenshot");
	    driver = initDriver();
	} else if (e instanceof UnreachableBrowserException) {
	    property.setRemarks("Browser window was not found, it died." + err + " screenshot");
	    driver = initDriver();
	} else if (e instanceof SessionNotCreatedException) {
	    property.setRemarks("Session not created." + err + " screenshot");
	    driver = initDriver();
	} else if (e instanceof SessionNotFoundException) {
	    property.setRemarks("Session not found." + err + " screenshot");
	    driver = initDriver();
	} else if (e instanceof UnsupportedCommandException) {
	    property.setRemarks("Selenium does not support this command." + err + " screenshot");
	} else if (e instanceof UnexpectedTagNameException) {
	    property.setRemarks("Seems locator is illegal." + err + " screenshot");
	} else if (e.toString().contains("Error communicating with the remote browser")) {
	    property.setRemarks("Browser window was not found, it died." + err + " screenshot");
	    driver = null;
	} else if (e.toString().contains("Element is not clickable")) {
	    property.setRemarks("Element was not clickable, Other element was received the click." + err + " screenshot");
	} else {
	    property.setRemarks("Unexpected error occurred:" + e.getMessage() + err + " screenshot");
	}
    }

    @SuppressWarnings("deprecation")
    HttpClient client = new HttpClient();
    static HttpResponse httpResponse = null;
    static JSONObject httpResponseJSON = null;
    String responseString = null;
    static String mimeType = null;
    static int statusCode = 0;
    static String photoID = null;

    public boolean getRequest(String url, String bearer, String auth) throws ClientProtocolException, IOException {
	try {

	    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
	    HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig);
	    HttpUriRequest request = new HttpGet(url);
	    request.setHeader("Authorization", bearer + ' ' + auth);
	    request.setHeader("Accept", "application/json");
	    request.setHeader("Accept-Encoding", "gzip, deflate");

	    httpResponse = builder.build().execute(request);
	    statusCode = httpResponse.getStatusLine().getStatusCode();
	    mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();

	    // System.out.println(">> " + httpResponse);
	    // System.out.println(">> " + statusCode);
	    // System.out.println(">> " + mimeType);

	    return true;
	} catch (Exception e) {
	    property.setRemarks("GET Request thew an exception");
	    return false;
	}

    }

    public boolean getRequestAfterAction(String url, String bearer, String auth) throws ClientProtocolException, IOException {
	try {

	    String updatedURL = url + photoID + ".json";
	    verification = getRequest(updatedURL, bearer, auth);

	    return true;
	} catch (Exception e) {
	    property.setRemarks("GET Request thew an exception");
	    return false;
	}

    }

    public boolean verifyStatusCodeREST(String code) throws Exception {
	try {
	    if (code.trim().isEmpty()) {
		property.setRemarks("Test Data is null");
		return false;
	    } else {
		if (statusCode == Integer.parseInt(code)) {
		    return true;
		} else {
		    property.setRemarks("Response Status code(" + statusCode + ") does not match with expetcetd: " + code);
		    return false;
		}
	    }
	} catch (Exception e) {
	    property.setRemarks("Response Status code(" + statusCode + ") does not match with expetcetd: " + code);
	    return false;
	}
    }

    public boolean verifyResponseTypeREST(String type) {
	try {
	    if (type.trim().isEmpty()) {
		property.setRemarks("Test Data is null");
		return false;
	    } else {
		if (mimeType.contains(type)) {
		    return true;
		} else {
		    property.setRemarks("Response type(" + mimeType + ") does not match with: " + type);
		    return false;
		}
	    }
	} catch (Exception e) {
	    property.setRemarks("Response type(" + mimeType + ") does not match with: " + type);
	    return false;
	}
    }

    public boolean verifyResponseData(String responseData) throws Exception {

	try {
	    if (responseData.trim().isEmpty()) {
		property.setRemarks("Test Data is null");
		return false;
	    } else {
		HttpEntity entity = httpResponse.getEntity();
		responseString = EntityUtils.toString(entity, "UTF-8");

		if (mimeType.contains("json")) {
		    JSONObject res = new JSONObject(responseString);

		    Iterator<String> keys = res.keys();

		    while (keys.hasNext()) {
			String k = keys.next();
			// System.out.println("Key: " + k + "\nvalue: "
			// + res.get(k));
		    }
		}

		if (responseString.contains(responseData)) {
		    return true;
		} else {
		    property.setRemarks("Response does not contain '" + responseData + "'");
		    return false;
		}
	    }
	} catch (Exception e) {
	    property.setRemarks("Response does not contain '" + responseData + "'");
	    return false;
	}

    }

    public boolean postRequest(String url, String namespace, String bearer, String auth, String fileName) throws ClientProtocolException, IOException {

	PostMethod post = new PostMethod(url);
	try {
	    setRequestHeaders(post, bearer, auth);

	    StringPart namespacePart = new StringPart("photo[namespace]", namespace);
	    StringPart slugPart = new StringPart("photo[slug]", randomNameGenerator());
	    // next two lines annoying work around for this bug:
	    // https://github.com/rack/rack/issues/186
	    namespacePart.setContentType(null);
	    slugPart.setContentType(null);

	    File f = new File(System.getProperty("user.dir"), "src" + property.FileSeperator.get() + "test" + property.FileSeperator.get() + "resources"
	            + property.FileSeperator.get() + "images" + property.FileSeperator.get() + fileName);
	    Part[] parts = { namespacePart, slugPart, new FilePart("photo[file]", f, "image/png", FilePart.DEFAULT_CHARSET) };
	    post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));

	    statusCode = client.executeMethod(post);
	    String httpResponseString = post.getResponseBodyAsString();

	    // System.out.println("Status code is : " + statusCode);
	    // System.out.println("Photo Service response: " +
	    // httpResponseString);

	    httpResponseJSON = new JSONObject(httpResponseString);
	    // System.out.println(">>: " + httpResponseJSON);
	    String photoIdJSONValue = httpResponseJSON.getString("id");
	    String[] photoIDArray = photoIdJSONValue.split("/");
	    photoID = photoIDArray[1];
	    // System.out.println("id: " + photoID);
	    return true;
	} catch (Exception e) {
	    property.setRemarks("POST Request thew an exception");
	    return false;
	} finally {
	    // Release the connection.
	    post.releaseConnection();
	}
    }

    /**
     * @param namespace
     * @param slug
     * @return
     * 
     */
    public boolean deleteRequest(String url, String bearer, String auth) {

	DeleteMethod delete = new DeleteMethod(url);

	try {
	    setRequestHeaders(delete, bearer, auth);

	    statusCode = client.executeMethod(delete);
	    String httpResponseString = delete.getResponseBodyAsString();

	    // System.out.println("Status code is : " + statusCode);
	    // System.out.println("Photo Service response: " +
	    // httpResponseString);
	} catch (Exception e) {
	    property.setRemarks("delete Request thew an exception");
	    return false;

	} finally {
	    // Release the connection.
	    delete.releaseConnection();
	}

	return false;
    }

    public boolean deleteRequestAfterAction(String url, String bearer, String auth) {
	try {

	    String updatedURL = url + photoID + ".json";
	    verification = deleteRequest(updatedURL, bearer, auth);

	    return true;
	} catch (Exception e) {
	    property.setRemarks("delete Request thew an exception");
	    return false;
	}

    }

    public boolean updateRequest(String url, String namespace, String bearer, String auth, String fileName) throws Exception {

	PutMethod put = new PutMethod(url);

	try {
	    // System.out.println(">>: " + url);
	    setRequestHeaders(put, bearer, auth);
	    StringPart namespacePart = new StringPart("photo[namespace]", namespace);
	    StringPart slugPart = new StringPart("photo[slug]", randomNameGenerator());
	    // next two lines annoying work around for this bug:
	    // https://github.com/rack/rack/issues/186
	    namespacePart.setContentType(null);
	    slugPart.setContentType(null);

	    File f = new File(System.getProperty("user.dir"), "src" + property.FileSeperator.get() + "test" + property.FileSeperator.get() + "resources"
	            + property.FileSeperator.get() + "images" + property.FileSeperator.get() + fileName);
	    Part[] parts = { namespacePart, slugPart, new FilePart("photo[file]", f, "image/png", FilePart.DEFAULT_CHARSET) };
	    put.setRequestEntity(new MultipartRequestEntity(parts, put.getParams()));

	    statusCode = client.executeMethod(put);
	    String httpResponseString = put.getResponseBodyAsString();

	    // System.out.println("Status code is : " + statusCode);
	    // System.out.println("Photo Service response: " +
	    // httpResponseString);

	    return true;

	} catch (Exception e) {
	    property.setRemarks("update Request thew an exception");
	    return false;
	} finally {
	    // Release the connection.
	    put.releaseConnection();
	}
    }

    public boolean updateRequestAfterAction(String url, String namespace, String bearer, String auth, String fileName) throws Exception {

	try {

	    String updatedURL = url + photoID + ".json";
	    verification = updateRequest(updatedURL, namespace, bearer, auth, fileName);

	    return true;
	} catch (Exception e) {
	    property.setRemarks("update Request thew an exception");
	    return false;
	}
    }

    private static void setRequestHeaders(HttpMethod method, String bearer, String auth) {

	method.addRequestHeader("Authorization", bearer + ' ' + auth);
	method.addRequestHeader("Accept", "application/json");
	method.addRequestHeader("Accept-Encoding", "gzip, deflate");
    }

    /**
     * @param data
     * @throws Exception
     * 
     */
    public boolean verifylEmentCount(String data) throws Exception {
	List<WebElement> findElements = new ArrayList<>();
	try {
	    explicitWait("elementisvisible", null);

	    findElements = driver.findElements(byLocator(attributeType, attribute));
	} catch (Exception e) {
	    objLog.writeInfo(e.getMessage());
	}
	try {
	    verification = verifyStatus(findElements.size(), Integer.parseInt(data));
	} catch (Exception e) {
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	}
	return verification;
    }

    /**
     * @throws Exception
     * @throws @throws
     *             NumberFormatException
     * 
     */
    public boolean verifyHeight(String data) throws Exception {

	try {
	    explicitWait("elementisvisible", null);
	    org.openqa.selenium.Dimension size = testObject.getSize();
	    verification = verifyStatus(Integer.parseInt(data), size.getHeight());
	} catch (Exception e) {
	    exceptionMessage(e);
	    objLog.writeInfo(e.getMessage());
	    verification = false;
	}
	return verification;

    }

    /**
     * @throws Exception
     * 
     */
    public WebElement getElement() throws Exception {

	try {
	    if (this.property.getBrowserName().contains("native") && this.property.getBrowserName().contains("ios")) {
		if (allAttributes.length > 1) {
		    testObject = driver.findElement(byLocator(attributeType, allAttributes[0])).findElement(byLocator(attributeType, allAttributes[1]));
		} else {
		    testObject = driver.findElement(byLocator(attributeType, attribute));
		}
	    } else {
		findElements = driver.findElements(byLocator(attributeType, attribute));
		testObject = findElements.get(nthElement);
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	    objLog.writeInfo(e.getMessage());
	}
	return testObject;
    }

    public boolean scrollToElement(String scrollDirection, int scrollHeight, int scrollWidth) throws Exception {
	try {
	    WebDriverWait localWait = new WebDriverWait(driver, 20);
	    localWait.until(ExpectedConditions.presenceOfElementLocated(byLocator(attributeType, attribute)));
	    // System.out.println("displayed without i");
	    return true;
	} catch (NoSuchElementException | TimeoutException e) {
	    for (int i = 1; i <= 50; i++) {
		try {
		    if (driver.findElement(byLocator(attributeType, attribute)).isDisplayed()) {
			// System.out.println("displayed: "+i);
			break;
		    }
		} catch (NoSuchElementException | TimeoutException ee) {
		    // System.out.println("not displayed: "+i);
		    swipeOnScreen = true;
		    if (scrollHeight == 0) {
			scrollHeight = 75;
		    }
		    if (scrollWidth == 0) {
			scrollWidth = 50;
		    }
		    if (scrollDirection.equalsIgnoreCase("bottomtotop")) {
			swipeBottomToTop(scrollHeight, scrollWidth);
		    } else if (scrollDirection.equalsIgnoreCase("toptoBottom")) {
			swipeTopToBottom(scrollHeight, scrollWidth);
		    } else if (scrollDirection.equalsIgnoreCase("righttoleft")) {
			swipeRightToLeft(scrollHeight, scrollWidth);
		    } else if (scrollDirection.equalsIgnoreCase("lefttoRight")) {
			swipeLeftToRight(scrollHeight, scrollWidth);
		    }
		}
		if (i == 50) {
		    // System.out.println("Loop Maxout");
		    exceptionMessage(e);
		    return false;
		}
	    }
	}
	System.out.println("out of loop");
	return true;
    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean swipeRightToLeft() throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY;
	    if (attribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = (int) (size.width * 0.90);
		endX = (int) (size.width * 0.10);
		startY = size.height / 2;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = (int) (size.width * 0.90) + location.getX();
		endX = (int) (size.width * 0.10) + location.getX();
		startY = size.height / 2 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public boolean swipeRightToLeft(int scrollHeight) throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY;
	    if (attribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = (int) (size.width * 0.90);
		endX = (int) (size.width * 0.10);
		startY = size.height * scrollHeight / 100;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = (int) (size.width * 0.90) + location.getX();
		endX = (int) (size.width * 0.10) + location.getX();
		startY = size.height * scrollHeight / 100 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean swipeRightToLeft(int scrollHeight, int scrollWidth) throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY;
	    if (attribute.equalsIgnoreCase("NA") || swipeOnScreen) {
		size = driver.manage().window().getSize();
		startX = size.width * scrollWidth / 100;
		endX = (int) (size.width * 0.10);
		startY = size.height * scrollHeight / 100;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width * scrollWidth / 100 + location.getX();
		endX = (int) (size.width * 0.10) + location.getX();
		startY = size.height * scrollHeight / 100 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean swipeLeftToRight() throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY;
	    if (attribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = (int) (size.width * 0.10);
		endX = (int) (size.width * 0.90);
		startY = size.height / 2;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = (int) (size.width * 0.10) + location.getX();
		endX = (int) (size.width * 0.90) + location.getX();
		startY = size.height / 2 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public boolean swipeLeftToRight(int scrollHeight) throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY;
	    if (attribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = (int) (size.width * 0.10);
		endX = (int) (size.width * 0.90);
		startY = size.height * scrollHeight / 100;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = (int) (size.width * 0.10) + location.getX();
		endX = (int) (size.width * 0.90) + location.getX();
		startY = size.height * scrollHeight / 100 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public boolean swipeLeftToRight(int scrollHeight, int scrollWidth) throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY;
	    if (attribute.equalsIgnoreCase("NA") || swipeOnScreen) {
		size = driver.manage().window().getSize();
		startX = (int) (size.width * 0.10);
		endX = size.width * scrollWidth / 100;
		startY = size.height * scrollHeight / 100;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = (int) (size.width * 0.10) + location.getX();
		endX = size.width * scrollWidth / 100 + location.getX();
		startY = size.height * scrollHeight / 100 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public boolean swipeTopToBottom() throws Exception {
	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");

	    if (attribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = size.width / 2;
		startY = (int) (size.height * 0.25);
		endY = (int) (size.height * 0.95);
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width / 2 + location.getX();
		startY = (int) (size.height * 0.25) + location.getY();
		endY = (int) (size.height * 0.95) + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    // Scroll Height and Scroll Width should be greater than 1 and less than 100
    private boolean swipeTopToBottom(int scrollHeight, int ScrollWidth) throws Exception {

	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");

	    if (attribute.equalsIgnoreCase("NA") || swipeOnScreen) {
		size = driver.manage().window().getSize();
		startX = size.width * ScrollWidth / 100;
		startY = size.height * scrollHeight / 100;
		endY = size.height * 95 / 100;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width * ScrollWidth + location.getX();
		startY = size.height * scrollHeight / 100 + location.getY();
		endY = size.height * 95 / 100 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean swipeBottomToTop() throws Exception {
	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");

	    if (attribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = size.width / 2;
		startY = (int) (size.height * 0.95);
		endY = (int) (size.height * 0.25);
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width / 2 + location.getX();
		startY = (int) (size.height * 0.95) + location.getY();
		endY = (int) (size.height * 0.25) + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @param string
     * @return
     * @throws Exception
     */
    private boolean swipeBottomToTop(String sample) throws Exception {

	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");
	    String localAttribute;
	    if (sample != null) {
		localAttribute = sample;
	    } else {
		localAttribute = attribute;
	    }
	    if (localAttribute.equalsIgnoreCase("NA")) {
		size = driver.manage().window().getSize();
		startX = size.width / 2;
		startY = (int) (size.height * 0.95);
		endY = (int) (size.height * 0.25);
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width / 2 + location.getX();
		startY = (int) (size.height * 0.95) + location.getY();
		endY = (int) (size.height * 0.25) + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
	    ThreadSleepNative(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    // Scroll Height and Scroll Width should be greater than 1 and less than 100
    private boolean swipeBottomToTop(int scrollHeight, int ScrollWidth) throws Exception {

	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");

	    if (attribute.equalsIgnoreCase("NA") || swipeOnScreen) {
		size = driver.manage().window().getSize();
		startX = size.width * ScrollWidth / 100;
		startY = size.height * scrollHeight / 100;
		endY = size.height * 25 / 100;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width * ScrollWidth / 100 + location.getX();
		startY = size.height * scrollHeight / 100 + location.getY();
		endY = size.height * 25 / 100 + location.getY();
	    }
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
	    ThreadSleepNative(500);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean switchOffWifi() throws Exception {
	try {
	    Process pr = Runtime.getRuntime().exec("adb shell svc wifi disable");
	    pr.waitFor();

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean switchOnWifi() throws Exception {
	try {
	    Process pr = Runtime.getRuntime().exec("adb shell svc wifi enable");
	    pr.waitFor();

	    return true;
	} catch (Exception e) {
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean changeOrientation(String orientation) throws Exception {
	try {
	    if (orientation.equalsIgnoreCase("")) {
		orientation = "portrait";
		ScreenOrientation currentOrientation = ((AppiumDriver) driver).getOrientation();
		if (currentOrientation.toString().equalsIgnoreCase("portrait")) {
		    orientation = "landscape";
		}
	    }
	    if (orientation.equalsIgnoreCase("landscape")) {
		((AppiumDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
	    } else if (orientation.equalsIgnoreCase("portrait")) {
		((AppiumDriver) driver).rotate(ScreenOrientation.PORTRAIT);
	    }

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @return
     * @throws Exception
     * 
     */
    public boolean scroll(String direction) throws Exception {
	try {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    HashMap<String, String> scrollObject = new HashMap<String, String>();
	    scrollObject.put("direction", direction);
	    // js.executeScript("mobile: scroll", scrollObject);
	    ((AppiumDriver) driver).scrollTo("XYZ");
	    // ((AppiumDriver) driver).swipe(400, 600, 400, 100, 1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    /**
     * @throws InterruptedException
     * 
     */
    private void ThreadSleep(long i) throws InterruptedException {
	if (!this.property.getBrowserName().contains("native")) {
	    Thread.sleep(i);
	}

    }

    private void ThreadSleepNative(long i) throws InterruptedException {
	Thread.sleep(i);
    }

    public boolean switchToAndroidWebView(String viewName) throws Exception {
	try {
	    Set contextNames = ((AppiumDriver) driver).getContextHandles();
	    Iterator iter = contextNames.iterator();
	    while (iter.hasNext()) {
		String cn = iter.next().toString();

		objLog.writeInfo(cn);

		// if context contains viewName than Switch to that context
		if (cn.contains(viewName)) {
		    // System.out.println(viewName);
		    Thread.sleep(10000);
		    System.out.println("switching the context");
		    ((AppiumDriver) driver).context(viewName);
		    System.out.println(driver.getCurrentUrl());
		    break;
		}
	    }
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Switch to Android Webview threw an exception");
	    objLog.writeInfo("Switch to Android Webview threw an exception");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean switchToiOSWebView(String viewName, String urlName) throws Exception {
	try {
	    Set contextNames = ((AppiumDriver) driver).getContextHandles();
	    Iterator iter = contextNames.iterator();
	    while (iter.hasNext()) {
		String cn = iter.next().toString();
		System.out.println(cn);
		if (!iter.hasNext()) {
		    if (cn.contains("WEBVIEW")) {
			((AppiumDriver) driver).context(cn);
			System.out.println("context switched");
			Thread.sleep(5000);
			wait.until(ExpectedConditions.urlContains(urlName));
			System.out.println(driver.getCurrentUrl());
		    }
		}

	    }
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Switch to iOS Webview threw an exception");
	    objLog.writeInfo("Switch to iOS Webview threw an exception");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean switchToNativeView() throws Exception {
	try {
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Switch to Native threw an exception");
	    objLog.writeInfo("Switch to Native threw an exception");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean verifyArticleDatePattern() throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String valueToBeMatched = testObject.getText().toString();
	    if (this.property.getBrowserName().contains("native") && this.property.getBrowserName().contains("ios")) {

		String[] parts = valueToBeMatched.split("\\|");
		valueToBeMatched = parts[1];
		valueToBeMatched = valueToBeMatched.trim();
	    }
	    if (Pattern.matches("\\w* [0-9][0-9]?, [0-9][0-9][0-9][0-9], [0-9]?[0-9]:[0-9]?[0-9] [AP]M.*?", valueToBeMatched)) {
		return true;
	    } else {
		property.setRemarks("value to be matched: " + valueToBeMatched + "does not match with regular expression: "
		        + "\\w* [0-9][0-9]?, [0-9][0-9][0-9][0-9], [0-9]?[0-9]:[0-9]?[0-9] [AP]M.*?" + " screenshot");
		return false;

	    }
	} catch (Exception e) {
	    property.setRemarks("Element not present");
	    objLog.writeInfo("Element not present");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean verifyElementLength(String expectedLengthInString) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    int expectedLength = Integer.parseInt(expectedLengthInString);

	    String actualValue = testObject.getText();
	    String[] topicNamewithoutPlus = actualValue.split("\\+");

	    if (topicNamewithoutPlus[1].length() < expectedLength) {
		return true;
	    } else {
		property.setRemarks("Follow Topic Length, expected: " + expectedLength + " actual is: " + topicNamewithoutPlus[1].length());
		return false;
	    }
	} catch (Exception e) {
	    property.setRemarks("Exception in FollowTopicLengthiOS");
	    objLog.writeInfo("Exception in FollowTopicLengthiOS");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean verifyTopicNameisDifferent() throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    String actualValueNew = testObject.getText();
	    String[] topicNamewithoutPlusNew = actualValueNew.split("\\+");

	    // New Topic and previous topic should not be same
	    if (!(topicNamewithoutPlusNew[1].equalsIgnoreCase(var2ShareAmongTestSteps.get((int) Thread.currentThread().getId()).toString()))) {
		return true;
	    } else {
		property.setRemarks("Fail: TopicName is same as previous Topic Name");
		objLog.writeInfo("Fail: TopicName is same as previous Topic Name");
		return false;
	    }

	} catch (Exception e) {
	    property.setRemarks("Exception: TopicName is same as previous Topic Name");
	    objLog.writeInfo("Exception:TopicName is same as previous Topic Name");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean findArticleWithTopiciOS(String option) throws Exception {
	try {
	    WebDriverWait localWait = new WebDriverWait(driver, 90);
	    for (int i = 1; i <= 4; i++) {

		By iosUIAutomationElement = MobileBy.IosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[" + i + "]");

		// wait for first Article to load on Sections Page
		wait.until(ExpectedConditions.elementToBeClickable(iosUIAutomationElement));
		// click on first article
		driver.findElement(iosUIAutomationElement).click();
		// check if Follow Button is present
		try {
		    By iosTopicName = MobileBy.IosUIAutomation(".collectionViews()[0].cells()[" + (i - 1) + "].buttons()[0]");
		    localWait.until(ExpectedConditions.presenceOfElementLocated(iosTopicName));
		    // localWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//UIACollectionView[1]//UIAButton[1]")));
		    String topicName = driver.findElement(iosTopicName).getText();
		    String[] topicNamewithoutPlus = topicName.split("\\+");
		    if (option.equalsIgnoreCase("SaveTopicValue")) {
			var2ShareAmongTestSteps.put((int) Thread.currentThread().getId(), topicNamewithoutPlus[1]);
		    }
		    break;
		} catch (NoSuchElementException | TimeoutException e) {
		    localWait.until(ExpectedConditions.presenceOfElementLocated(By.name("header back")));
		    driver.findElement(By.name("header back")).click();
		    if (i == 4) {
			property.setRemarks("Could not find article with Topic in first four articles");
			return false;
		    }
		} catch (WebDriverException e) {
		    property.setRemarks("App Crashed because Appium Failed on TableView");
		    return false;
		}
	    }
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Exception in findArticleWithTopiciOS");
	    objLog.writeInfo(e.getMessage());
	    objLog.writeInfo("Exception in findArticleWithTopiciOS");
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean findAndClickOnArticleWithPhotoGalleryiOS() throws Exception {
	boolean status = false;
	try {
	    WebDriverWait localWait = new WebDriverWait((driver), 60);
	    for (int i = 1; i <= 40; i++) {
		try {
		    Thread.sleep(5000);

		    By iosPhotoGalleryx = MobileBy
		            .IosUIAutomation(
		                    ".collectionViews()[0].cells()[5].tableViews()[0].cells().firstWithPredicate(\"ANY staticTexts.name BEGINSWITH 'Photos:'\")");
		    localWait.until(ExpectedConditions.visibilityOfElementLocated(iosPhotoGalleryx));
		    driver.findElement(iosPhotoGalleryx).click();
		    status = true;
		    break;
		} catch (NoSuchElementException | TimeoutException e) {
		    swipeBottomToTop();
		    Thread.sleep(2000);
		}
	    }
	    Thread.sleep(1000);
	    if (status == false) {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Could not find article with photo gallery. screenshot");
		objLog.writeInfo("Could not find article with photo gallery");
	    }
	    return status;
	} catch (Exception e) {
	    property.setScreenshotName(takeScreenShot());
	    property.setRemarks("Could not find article with photo gallery. screenshot");
	    objLog.writeInfo("Could not find article with photo gallery");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean findAndClickOnArticleWithVideoiOS() throws Exception {
	boolean status = false;
	try {
	    WebDriverWait localWait = new WebDriverWait((driver), 60);
	    for (int i = 1; i <= 40; i++) {
		try {
		    Thread.sleep(5000);
		    By iosVideoArticle = MobileBy
		            .IosUIAutomation(
		                    ".collectionViews()[0].cells()[12].tableViews()[0].cells().firstWithPredicate(\"ANY staticTexts.name BEGINSWITH 'Video:'\")");
		    localWait.until(ExpectedConditions.visibilityOfElementLocated(iosVideoArticle));
		    driver.findElement(iosVideoArticle).click();
		    status = true;
		    break;
		} catch (NoSuchElementException | TimeoutException e) {
		    swipeBottomToTop();
		    Thread.sleep(2000);
		}
	    }
	    Thread.sleep(1000);
	    if (status == false) {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Could not find article with video gallery. screenshot");
		objLog.writeInfo("Could not find article with video gallery");
	    }
	    return status;
	} catch (Exception e) {
	    property.setScreenshotName(takeScreenShot());
	    property.setRemarks("Could not find article with video gallery. screenshot");
	    objLog.writeInfo("Could not find article with video gallery");
	    return false;
	}
    }

    public boolean clickVideoGalleryDoneButtoniOS() throws Exception {
	boolean status = false;
	try {
	    for (int i = 1; i <= 10; i++) {
		try {
		    Thread.sleep(1000);
		    ((IOSDriver) driver).findElement(MobileBy.xpath("//UIAApplication[1]")).click();
		    ((IOSDriver) driver).findElement(MobileBy.IosUIAutomation(".buttons()[0]")).click();
		    status = true;
		    break;
		} catch (NoSuchElementException | TimeoutException e) {
		    Thread.sleep(1000);
		}
	    }
	    Thread.sleep(1000);
	    if (status == false) {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Could not find done button. screenshot");
		objLog.writeInfo("Could not find done button");
	    }
	    return status;
	} catch (Exception e) {
	    property.setScreenshotName(takeScreenShot());
	    property.setRemarks("Could not find done button. screenshot");
	    objLog.writeInfo("Could not find done button");
	    return false;
	}
    }

    public boolean waitTillVideoTotalTimeiOS() throws Exception {
	boolean status = false;
	try {
	    WebDriverWait localWait = new WebDriverWait((driver), 90);
	    for (int i = 1; i <= 10; i++) {
		try {
		    Thread.sleep(1000);
		    localWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, attribute)));
		    String totalTime = ((IOSDriver) driver).findElement(byLocator(attributeType, attribute)).getAttribute("label");
		    String[] tokens = totalTime.split(":");
		    int minutes = Integer.parseInt(tokens[0]);
		    int seconds = Integer.parseInt(tokens[1]);
		    int duration = 60 * minutes + seconds;
		    Thread.sleep(duration * 1000);
		    status = true;
		    break;
		} catch (NoSuchElementException | TimeoutException e) {
		    Thread.sleep(1000);
		}
	    }
	    Thread.sleep(1000);
	    if (status == false) {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Could not find video. screenshot");
		objLog.writeInfo("Could not find video");
	    }
	    return status;
	} catch (Exception e) {
	    property.setScreenshotName(takeScreenShot());
	    property.setRemarks("Could not find video. screenshot");
	    objLog.writeInfo("Could not find video");
	    return false;
	}
    }

    public WebElement createElementfromDataContent(String attributeType, String attribute1, String attribute2) throws Exception {
	try {
	    testObject = driver.findElement(byLocator(attributeType, attribute1 + loopCounter + attribute2));
	} catch (Exception e) {
	    e.printStackTrace();
	}

	System.out.println(attribute1 + loopCounter + attribute2);
	System.out.println(testObject.getText());
	return testObject;
    }

    // This keyword is not appllicable now.
    public boolean clickPremiumArticleAndriod() throws Exception {
	try {
	    for (int i = 0; i < 10; i++) {
		List<WebElement> l = driver.findElements(By.id("com.apptivateme.next.ct:id/digital_plus_icon"));
		if (l.size() > 0) {
		    System.out.println("element found");
		    // click on Premium Article
		    l.get(0).click();
		    Thread.sleep(2500);
		    break;
		} else {
		    System.out.println("premium article not found in iteration: " + i);
		    swipeBottomToTop();
		}
	    }
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Could not find premium article");
	    objLog.writeInfo("Could not find premium article");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean clickFollowAnArticleAndriod() throws Exception {
	try {
	    WebDriverWait localWait = new WebDriverWait(driver, 15);
	    for (int i = 0; i < 10; i++) {
		try {
		    localWait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.apptivateme.next.ct:id/cell_topic")));
		    WebElement articleWithFollow = driver.findElement(By.id("com.apptivateme.next.ct:id/cell_topic"));
		    // click on Topic
		    articleWithFollow.click();
		    Thread.sleep(2500);
		    // verify the property
		    // System.out.println(articleWithFollow.getAttribute("selected"));

		    var2ShareAmongTestSteps.put((int) Thread.currentThread().getId(), articleWithFollow.getText());
		    i = 10;
		    break;
		} catch (TimeoutException | NoSuchElementException e) {
		    System.out.println("article with Topic not found in iteration: " + i);
		    swipeBottomToTop(70, 25);
		    if (i == 9) {
			property.setRemarks("article with Topic not found");
			objLog.writeInfo("article with Topic not found");
			objLog.writeInfo(e.getMessage());
			return false;
		    }
		}
	    }
	    return true;
	} catch (Exception e) {
	    property.setRemarks("article with Topic not found");
	    objLog.writeInfo("article with Topic not found");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    // this fucntion is not in use now
    public boolean clickNonPremiumArticleAndroid() throws Exception {
	try {
	    for (int i = 1; i <= 10; i++) {
		List<WebElement> l = driver.findElements(By.id("com.apptivateme.next.ct:id/card_view"));
		int noOfCards = l.size();

		for (int j = 0; j < noOfCards; j++) {
		    try {
			if (driver.findElement(
			        By.xpath("//*[@resource-id='com.apptivateme.next.ct:id/card_view'][" + (j + 1)
			                + "]//android.widget.ImageView[@resource-id='com.apptivateme.next.ct:id/digital_plus_icon']"))
			        .isDisplayed()) {
			    System.out.println("premium article: " + (j + 1));
			}
			if (j == noOfCards - 1) {
			    System.out.println("Non Premium not found in iteration: " + i);
			    swipeBottomToTop();
			}
		    } catch (NoSuchElementException e) {
			System.out.println("clicking Non premium article");
			driver.findElement(By.xpath("//*[@resource-id='com.apptivateme.next.ct:id/card_view'][" + (j + 1) + "]")).click();
			i = 11;
			Thread.sleep(2500);
			break;
		    }
		}
	    }
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Non Premium not found");
	    objLog.writeInfo("Non Premium not found");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * @return
     * @return
     * @throws Exception
     * 
     */
    public boolean clickPhotoGalleryAndroid() throws Exception {
	WebElement photoGallery = null;
	try {
	    for (int i = 0; i < 10; i++) {
		Thread.sleep(3000);
		WebDriverWait localWait = new WebDriverWait(driver, 20);
		// wait for the round photo gallery element on the round image
		// on Article on Section page itself
		localWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator("xpath",
		        "//android.widget.ImageView[@resource-id='com.apptivateme.next.ct:id/cell_media_round_image']")));
		try {
		    photoGallery = driver.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.apptivateme.next.ct:id/cell_media_round_image']"));
		    break;
		} catch (Exception e) {
		    e.printStackTrace();
		    swipeBottomToTop();
		}
	    }
	    photoGallery.click();
	    Thread.sleep(3000);
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Exception occurred while clicking on the photoGallery article");
	    objLog.writeInfo("Exception occurred while clicking on the photoGallery article");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * 
     * @param dataContentFirst
     * @return
     * @throws Exception
     */
    public boolean verifySectionTitlePresent(String dataContentFirst) throws Exception {
	WebElement sectionTitle = null;
	WebDriverWait localWait = new WebDriverWait(driver, 15);
	try {
	    if (dataContentFirst.equalsIgnoreCase("")) {
		dataContentFirst = var2ShareAmongTestSteps.get((int) Thread.currentThread().getId()).toString();
	    }

	    for (int i = 0; i < 2; i++) {
		Thread.sleep(3000);

		String currentTitleLocator = attribute + "//android.widget.TextView[@text='" + dataContentFirst + "']";
		// System.out.println(currentTitleLocator);

		try {
		    localWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, currentTitleLocator)));
		    WebElement currentTitle = driver.findElement(byLocator(attributeType, currentTitleLocator));
		    i = 2;
		    return true;
		} catch (TimeoutException | NoSuchElementException e) {
		    // System.out.println(i);
		    if (i == 1) {
			return false;
		    }
		}
	    }
	    return false;
	} catch (Exception e) {
	    property.setRemarks("Exception ocurred while checking for the Section title");
	    objLog.writeInfo("Exception ocurred while checking for the Section title");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * 
     * @param dataContentFirst
     * @return
     * @throws Exception
     */
    public boolean clickOnSectionTitle() throws Exception {

	try {
	    String TitleTobeClicked = var2ShareAmongTestSteps.get((int) Thread.currentThread().getId()).toString();
	    String currentTitleLocator = attribute + "//android.widget.TextView[@text='" + TitleTobeClicked + "']";
	    wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(attributeType, currentTitleLocator)));
	    driver.findElement(byLocator(attributeType, currentTitleLocator)).click();
	    return true;
	} catch (Exception e) {
	    property.setRemarks("Exception ocurred while clicking on Section title");
	    objLog.writeInfo("Exception ocurred while clicking on the Section title");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    return false;
	}
    }

    /*********************************************************************************************
     * Function name: verifyObjectPresent Description: Verify if Test Object is
     * present on browser screen Input variables: TestObject Content of
     * variable: Testobject name of GUI item Output: status
     *********************************************************************************************/

    public boolean verifyObjectPresentAndroid() throws Exception {
	try {
	    WebDriverWait localWait = new WebDriverWait(driver, 20);
	    int i = 0;
	    boolean status = false;
	    do {
		ThreadSleep(3000);
		ThreadSleep(5000);
		localWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator(attributeType, attribute)));
		ThreadSleep(5000);
		try {
		    testObject = getElement();
		    status = testObject.isDisplayed();
		    break;
		} catch (Exception e) {
		    objLog.writeInfo("Element not displayed in current view port. So swiping it to top.");
		    swipeBottomToTop("NA");
		}

		i++;
	    } while (i < 20);

	    if (status) {
		return true;
	    } else {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Element is not present on the page. screenshot");
		return false;
	    }

	} catch (NoSuchElementException e) {
	    exceptionMessage(e);
	    return false;
	}

	catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean SaveObjectTextValueinMap() throws Exception {
	try {
	    explicitWait("elementsarevisible", null);
	    String valueTobeSaved = testObject.getText().toString().trim();
	    var2ShareAmongTestSteps.put((int) Thread.currentThread().getId(), valueTobeSaved);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public boolean VerifyObjectTextValueinMap() throws Exception {
	try {
	    explicitWait("elementsarevisible", null);
	    String actualValue = testObject.getText().toString().trim();
	    String expectedValue = var2ShareAmongTestSteps.get((int) Thread.currentThread().getId()).toString().trim();
	    if (expectedValue.equalsIgnoreCase(actualValue)) {
		return true;
	    } else {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Expected value: " + expectedValue + " does not match with actual value: " + actualValue + " screenshot");
		return false;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean VerifyObjectTextValueDontMatchinMap() throws Exception {
	try {
	    explicitWait("elementsarevisible", null);
	    String actualValue = testObject.getText().toString();
	    String expectedValue = var2ShareAmongTestSteps.get((int) Thread.currentThread().getId()).toString();
	    // System.out.println(actualValue);
	    // System.out.println(expectedValue);
	    if (expectedValue.equalsIgnoreCase(actualValue)) {
		property.setScreenshotName(takeScreenShot());
		property.setRemarks("Expected value: " + expectedValue + " match with actual value: " + actualValue + " screenshot");
		return false;
	    } else {
		return true;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * @param string
     * @return
     */
    private boolean setSettingsIOS() {

	try {

	    if (this.property.IsRemoteExecution.equalsIgnoreCase("true")) {

		objLog.writeInfo("Remote execution is true");

		String remoteURL = this.property.RemoteURL + "/wd/hub";
		URL uri = new URL(remoteURL);

		DesiredCapabilities createCapabilities = createCapabilities("settings", "noReset");
		driver = new IOSDriver(uri, createCapabilities);
		((AppiumDriver) driver).context("NATIVE_APP");
		ThreadSleep(2500);
	    } else {
		objLog.writeInfo("Remote execution is false");

		DesiredCapabilities createCapabilities = createCapabilities("settings", "noReset");

		driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), createCapabilities);
		((AppiumDriver) driver).context("NATIVE_APP");
		ThreadSleep(2500);

	    }

	    try {
		WebElement faceBook = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[9]"));
		faceBook.click();

		Thread.sleep(5000);
		if (this.property.getBrowserName().equalsIgnoreCase("IOSPhonePortraitNative")
		        || this.property.getBrowserName().equalsIgnoreCase("IOSPhoneLandscapeNative")) {
		    WebElement userName = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[1].textFields()[0]"));
		    userName.sendKeys("xebiatribune@gmail.com");
		    Thread.sleep(5000);
		    WebElement passWord = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[2].secureTextFields()[0]"));
		    passWord.sendKeys("Mar@2014@");
		    Thread.sleep(5000);
		    WebElement signIn = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[3]"));
		    signIn.click();
		    Thread.sleep(5000);
		    WebElement signIn2 = driver.findElement(MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[2]"));
		    signIn2.click();
		    Thread.sleep(10000);
		} else if (this.property.getBrowserName().equalsIgnoreCase("IOSTabletPortraitNative")
		        || this.property.getBrowserName().equalsIgnoreCase("IOSTabletLandscapeNative")) {
		    WebElement userName = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()[1].textFields()[0]"));
		    userName.sendKeys("xebiatribune@gmail.com");
		    Thread.sleep(5000);
		    WebElement passWord = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()[2].secureTextFields()[0]"));
		    passWord.sendKeys("Mar@2014@");
		    Thread.sleep(5000);
		    WebElement signIn = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()[3]"));
		    signIn.click();
		    Thread.sleep(5000);
		    WebElement signIn2 = driver.findElement(MobileBy.IosUIAutomation(".navigationBars()[2].buttons()[2]"));
		    signIn2.click();
		    Thread.sleep(10000);
		}
	    } catch (NoSuchElementException e) {
		System.out.println("Facebook already logged in");
	    }
	    try {
		WebElement back2Settings = driver.findElement(MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[0]"));
		back2Settings.click();
	    } catch (Exception e1) {
		System.out.println("Back button not displayed");
	    }

	    try {
		WebElement twitter = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[8]"));
		twitter.click();
		Thread.sleep(5000);
		if (this.property.getBrowserName().equalsIgnoreCase("IOSPhonePortraitNative")
		        || this.property.getBrowserName().equalsIgnoreCase("IOSPhoneLandscapeNative")) {

		    WebElement userName = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[1].textFields()[0]"));
		    userName.sendKeys("xebiatribune@gmail.com");
		    Thread.sleep(5000);
		    WebElement passWord = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[2].secureTextFields()[0]"));
		    passWord.sendKeys("Mar@2014@");
		    Thread.sleep(5000);
		    WebElement signIn = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[0].cells()[3]"));
		    signIn.click();
		    Thread.sleep(10000);
		} else if (this.property.getBrowserName().equalsIgnoreCase("IOSTabletPortraitNative")
		        || this.property.getBrowserName().equalsIgnoreCase("IOSTabletLandscapeNative")) {

		    WebElement userName = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()[1].textFields()[0]"));
		    userName.sendKeys("xebiatribune@gmail.com");
		    Thread.sleep(5000);
		    WebElement passWord = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()[2].secureTextFields()[0]"));
		    passWord.sendKeys("Mar@2014@");
		    Thread.sleep(5000);
		    WebElement signIn = driver.findElement(MobileBy.IosUIAutomation(".tableViews()[1].cells()[3]"));
		    signIn.click();
		    Thread.sleep(10000);
		}

	    } catch (NoSuchElementException e) {
		System.out.println("Twitter already logged in");
	    }

	    try {
		WebElement back2Settings = driver.findElement(MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[0]"));
		back2Settings.click();
	    } catch (Exception e1) {
		System.out.println("Back button not displayed");
	    }

	    driver.quit();
	    return true;

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    objLog.writeInfo(e.getMessage());
	    return false;
	}

    }

    /*********************************************************************************************
     * Function name: initDriver Description: Initialize the driver for the
     * current (remote) Browser Input variables: None Content of variable: NA
     * Output: driver
     * 
     * @return
     * @throws IOException
     *********************************************************************************************/

    /**
     * @param uri
     * @throws InterruptedException
     */
    public DesiredCapabilities createCapabilities(String appPath, String data) throws InterruptedException {
	DesiredCapabilities capabilities = new DesiredCapabilities();
	if (data.equalsIgnoreCase("noReset")) {
	    capabilities.setCapability("noReset", "true");
	}
	if (this.property.getBrowserName().equalsIgnoreCase("IOSPhonePortraitNative")) {

	    capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platformVersion", "9.0");
	    capabilities.setCapability("platformName", "iOS");
	    capabilities.setCapability("platform", "MAC");
	    capabilities.setCapability("deviceName", "iPhone 6");
	    // capabilities.setCapability("fullReset", "true");
	    capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
	    capabilities.setCapability("app", appPath);

	} else if (this.property.getBrowserName().equalsIgnoreCase("IOSPhoneLandscapeNative")) {

	    capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platformVersion", "9.0");
	    capabilities.setCapability("platformName", "iOS");
	    capabilities.setCapability("platform", "MAC");
	    capabilities.setCapability("deviceName", "iPhone 6");
	    capabilities.setCapability("orientation", "LANDSCAPE");
	    // capabilities.setCapability("fullReset", "true");
	    capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
	    capabilities.setCapability("app", appPath);

	} else if (this.property.getBrowserName().equalsIgnoreCase("IOSTabletPortraitNative")) {

	    capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platformVersion", "9.0");
	    capabilities.setCapability("platformName", "iOS");
	    capabilities.setCapability("platform", "MAC");
	    capabilities.setCapability("deviceName", "iPad Air");
	    // capabilities.setCapability("fullReset", "true");
	    capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
	    capabilities.setCapability("app", appPath);

	} else if (this.property.getBrowserName().equalsIgnoreCase("IOSTabletLandscapeNative")) {

	    capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platformVersion", "9.0");
	    capabilities.setCapability("platformName", "iOS");
	    capabilities.setCapability("platform", "MAC");
	    capabilities.setCapability("deviceName", "iPad Air");
	    capabilities.setCapability("orientation", "LANDSCAPE");
	    // capabilities.setCapability("fullReset", "true");
	    capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
	    capabilities.setCapability("app", appPath);
	}
	return capabilities;
    }

    public boolean wait4AlertAndAccept() throws Exception {
	try {
	    explicitWait("alertispresent", null);
	    driver.switchTo().alert().accept();

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean navigateToNextPremiumArticle() throws Exception {
	int maxArticles = 20;
	try {
	    WebDriverWait localWait = new WebDriverWait((driver), 60);
	    By articleContainer = MobileBy.IosUIAutomation(".collectionViews().firstWithPredicate(\"ANY cells.enabled == TRUE\")");
	    By iosArticleReadPremiumButton1 = MobileBy.name("Tap to read story");
	    By iosArticleReadPremiumButton = MobileBy
	            .IosUIAutomation(
	                    ".collectionViews()[0].cells().firstWithPredicate(\"visible==TRUE\").buttons().firstWithPredicate(\"name == 'Tap to read story'\")");

	    localWait.until(ExpectedConditions.visibilityOfElementLocated(articleContainer));
	    org.openqa.selenium.Dimension size = driver.manage().window().getSize();
	    for (int i = 0; i < maxArticles; i++) {

		// swipeRightToLeft();
		((AppiumDriver) driver).context("NATIVE_APP");

		((AppiumDriver) driver).swipe((int) (size.width * 0.90), size.height / 2, (int) (size.width * 0.10), size.height / 2, 1000);

		localWait.until(ExpectedConditions.visibilityOfElementLocated(articleContainer));

		// swipeBottomToTop();
		// ((AppiumDriver) driver).context("NATIVE_APP");
		((AppiumDriver) driver).swipe(size.width / 2, (int) (size.height * 0.95), size.width / 2, (int) (size.height * 0.25), 1000);

		try {

		    if (driver.findElement(byLocator(attributeType, attribute)).isDisplayed()) {
			return true;

		    }
		} catch (Exception e) {
		}

	    }
	    property.setRemarks("Premium article not found even after opening " + maxArticles + " articles");
	    objLog.writeInfo("Premium article not found even after opening " + maxArticles + " articles");
	    return false;
	} catch (Exception e) {
	    property.setRemarks("Exception occurred " + e);
	    objLog.writeInfo("Exception occurred " + e);
	    return false;
	}
    }

    public boolean readFirstPremiumArticleiOS() throws Exception {
	int maxArticles = 20;
	try {
	    WebDriverWait localWait = new WebDriverWait((driver), 60);
	    By iosArticle = MobileBy.IosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[1]");

	    By iosArticleReadPremiumButton = MobileBy.name("Tap to read story");
	    By articleContainer = MobileBy.IosUIAutomation(".collectionViews().firstWithPredicate(\"ANY cells.enabled == TRUE\")");
	    By backButton = MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[0]");
	    By yourAccount = MobileBy.IosUIAutomation(".staticTexts()[1]");
	    By yourAccountTablet = MobileBy.IosUIAutomation(".staticTexts()[3]");
	    By close = MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[1]");
	    By closeTablet = MobileBy.IosUIAutomation(".navigationBars()[1].buttons()[1]");
	    By storyCredits = MobileBy.IosUIAutomation(".tableViews()[0].cells()[2].staticTexts().firstWithPredicate(\"name BEGINSWITH 'Story Credits:'\")");

	    localWait.until(ExpectedConditions.visibilityOfElementLocated(iosArticle));
	    driver.findElement(iosArticle).click();
	    localWait.until(ExpectedConditions.visibilityOfElementLocated(articleContainer));
	    driver.findElement(backButton).click();
	    localWait.until(ExpectedConditions.visibilityOfElementLocated(backButton));
	    driver.findElement(backButton).click();

	    String creditsValue;
	    if (this.property.getBrowserName().equalsIgnoreCase("IOSPhonePortraitNative")
	            || this.property.getBrowserName().equalsIgnoreCase("IOSPhoneLandscapeNative")) {
		localWait.until(ExpectedConditions.visibilityOfElementLocated(yourAccount));
		driver.findElement(yourAccount).click();
		localWait.until(ExpectedConditions.visibilityOfElementLocated(storyCredits));
		creditsValue = driver.findElement(storyCredits).getAttribute("name");
		driver.findElement(close).click();
		localWait.until(ExpectedConditions.visibilityOfElementLocated(close));
		driver.findElement(close).click();
	    } else {
		localWait.until(ExpectedConditions.visibilityOfElementLocated(yourAccountTablet));
		driver.findElement(yourAccountTablet).click();
		localWait.until(ExpectedConditions.visibilityOfElementLocated(storyCredits));
		creditsValue = driver.findElement(storyCredits).getAttribute("name");
		driver.findElement(closeTablet).click();
		localWait.until(ExpectedConditions.visibilityOfElementLocated(closeTablet));
		driver.findElement(closeTablet).click();
	    }

	    // swipeRightToLeft();
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size = driver.manage().window().getSize();
	    ((AppiumDriver) driver).swipe((int) (size.width * 0.90), size.height / 2, (int) (size.width * 0.10), size.height / 2, 1000);

	    // swipeLeftToRight();
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    ((AppiumDriver) driver).swipe((int) (size.width * 0.10), size.height / 2, (int) (size.width * 0.90), size.height / 2, 1000);

	    localWait.until(ExpectedConditions.visibilityOfElementLocated(iosArticle));
	    driver.findElement(iosArticle).click();
	    localWait.until(ExpectedConditions.visibilityOfElementLocated(articleContainer));

	    if (creditsValue.equalsIgnoreCase("Story Credits: 20")) {
		for (int i = 0; i < maxArticles; i++) {

		    // swipeRightToLeft();
		    ((AppiumDriver) driver).context("NATIVE_APP");
		    ((AppiumDriver) driver).swipe((int) (size.width * 0.90), size.height / 2, (int) (size.width * 0.10), size.height / 2, 1000);

		    localWait.until(ExpectedConditions.visibilityOfElementLocated(articleContainer));

		    // swipeBottomToTop();
		    ((AppiumDriver) driver).context("NATIVE_APP");
		    ((AppiumDriver) driver).swipe(size.width / 2, (int) (size.height * 0.95), size.width / 2, (int) (size.height * 0.25), 1000);
		    WebElement current = null;
		    try {

			if ((current = driver.findElement(byLocator(attributeType, attribute))).isDisplayed()) {
			    Thread.sleep(1000);
			    ((MobileElement) current).tap(1, 500);
			    return true;
			}
		    } catch (Exception e) {
		    }
		}
	    } else if (creditsValue.equalsIgnoreCase("Story Credits: 19")) {
		return true;
	    }

	    return false;
	} catch (Exception e) {
	    property.setRemarks("First premium article not found even after opening " + maxArticles + " articles");
	    objLog.writeInfo("First premium article not found even after opening " + maxArticles + " articles");
	    return false;
	}
    }

    public boolean clickReadPremiumArticle() throws Exception {
	try {
	    WebDriverWait localWait = new WebDriverWait((driver), 60);
	    By articleContainer = MobileBy.IosUIAutomation(".collectionViews().firstWithPredicate(\"ANY cells.enabled == TRUE\")");
	    By iosArticleReadPremiumButton1 = MobileBy.name("Tap to read story");
	    By iosArticleReadPremiumButton = MobileBy
	            .IosUIAutomation(
	                    ".collectionViews()[0].cells().firstWithPredicate(\"visible==TRUE\").buttons().firstWithPredicate(\"name == 'Tap to read story'\")");

	    localWait.until(ExpectedConditions.visibilityOfElementLocated(articleContainer));

	    try {
		List<WebElement> allElements = driver.findElements(iosArticleReadPremiumButton);
		for (int i1 = 0; i1 < allElements.size(); i1++) {
		    if (allElements.get(i1).isDisplayed()) {
			Thread.sleep(1000);
			((IOSElement) allElements.get(i1)).tap(1, 500);
			Thread.sleep(2000);
			return true;
		    }
		    if (i1 == 30) {
			break;
		    }
		}
	    } catch (Exception e) {
	    }

	    property.setRemarks("Read premium article button not found.");
	    objLog.writeInfo("Read premium article button not found.");
	    return false;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean verifyTextInTitle(String text) throws Exception {
	try {
	    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("com.apptivateme.next.ct:id/search_results_listview")));
	    List<WebElement> l = driver.findElements(By.id("com.apptivateme.next.ct:id/search_results_listview"));
	    int noOfCards = l.size();
	    if (noOfCards > 0) {
		for (int j = 0; j < noOfCards; j++) {

		    if (!(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/search_title'][" + (j + 1) + "]"))
		            .getText()).toLowerCase().contains(text)) {
			throw new Exception();
		    }

		}
		return true;
	    } else {
		property.setRemarks("No Search Results");
		objLog.writeInfo("No Search Results");
		return false;
	    }
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean SwitchToAlert() throws Exception {
	try {
	    explicitWait("alertispresent", null);
	    driver.switchTo().alert();

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean longTap() throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    TouchAction action = new TouchAction((AppiumDriver) driver);
	    action.longPress(testObject).release().perform();
	    Thread.sleep(1000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public boolean longTap(int x, int y) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    TouchAction action = new TouchAction((AppiumDriver) driver);
	    action.longPress(testObject, x, y).release().perform();
	    System.out.println("here");
	    ThreadSleepNative(10000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean tapObject() throws Exception {
	ThreadSleepNative(2000);

	try {
	    explicitWait("elementtobeclickable", null);
	    ((MobileElement) testObject).tap(1, 500);

	    ThreadSleepNative(2000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean tapObject(int x, int y) throws Exception {
	ThreadSleepNative(2000);

	try {
	    explicitWait("elementtobeclickable", null);
	    TouchAction action = new TouchAction((AppiumDriver) driver);
	    Point location = testObject.getLocation();
	    action.tap(testObject, x, y).perform();

	    ThreadSleepNative(2000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public boolean tapAtElement(int x, int y) throws Exception {
	ThreadSleepNative(2000);

	try {
	    explicitWait("elementispresent", null);
	    Point location = testObject.getLocation();
	    TouchAction action = new TouchAction((AppiumDriver) driver);
	    action.tap(location.getX() + x, location.getY() + y).perform();

	    ThreadSleepNative(2000);
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    /**
     * @throws Exception
     * 
     */
    private boolean clickVideoGalleryArticleAndroid() throws Exception {
	WebDriverWait waitvar = new WebDriverWait(driver, 90);

	By firstArticleText = By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/cell_title']");
	By seeThisStoryButton = By.id("com.apptivateme.next.ct:id/subscription_meter_button");
	By articleHeading = By.id("com.apptivateme.next.ct:id/article_title");
	By videoPlayButton = By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tvVideoTime']");
	By articleBack = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");

	List ReadArticles = new ArrayList<String>();
	try {
	    // Total at max we do 50 swipes to check for the video article
	    for (int i = 1; i <= 50; i++) {
		// Getting all the article texts in the view port
		List<WebElement> l = driver.findElements(firstArticleText);
		int noOfCards = l.size();
		// rotating for each article text in the current view port
		for (int j = 1; j <= noOfCards; j++) {
		    String currentArticleText;
		    By articleText;
		    try {
			articleText = By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/cell_title'][" + j + "]");
			currentArticleText = driver.findElement(articleText).getText();
			System.out.println("current article is " + currentArticleText);

		    } catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException occurred while getting the text of the article at " + j
			        + ". So need to swipe to see new articles.");
			break;
		    }
		    // checking whether the article already read or not
		    if (!ReadArticles.contains(currentArticleText)) {
			try {
			    // opening the current article
			    driver.findElement(articleText).click();
			    try {
				// Click on See This Story button
				waitvar.until(ExpectedConditions.elementToBeClickable(seeThisStoryButton));
				driver.findElement(seeThisStoryButton).click();
			    } catch (Exception e1) {
				System.out.println("See This Story button not displayed for the article");
			    }
			    // Get the current reading article heading
			    new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(articleHeading));
			    String headingText = driver.findElement(articleHeading).getText();
			    System.out.println("just read article is " + headingText);
			    // Adding the article heading to ReadArticles list
			    ReadArticles.add(headingText);
			    try {
				// Check whether the article contains video
				if (driver.findElement(videoPlayButton).isDisplayed()) {
				    System.out.println("Current article is video article");
				    return true;
				}
			    } catch (Exception e) {
				System.out.println("Current articl is not a video article");
			    }

			    waitvar.until(ExpectedConditions.elementToBeClickable(articleBack));
			    driver.findElement(articleBack).click();
			    Thread.sleep(3000);

			} catch (Exception e) {
			    System.out.println("Exception occurred " + e);
			}
		    } else {
			System.out.println("Current article is already read.");
		    }

		}
		System.out.println("Non Premium/Video article not found in iteration: " + i);
		swipeBottomToTop(60, 50);
		Thread.sleep(4000);
	    }

	} catch (Exception e) {
	    objLog.writeInfo("Video article not found");
	    exceptionMessage(e);
	    return false;

	}
	System.out.println("Video article not found after looking for almost 30 view ports");
	return false;

    }

    public boolean clickVideoGalleryArticleAndroid1() throws Exception {
	try {
	    int count = 0;
	    boolean videoFound = false;
	    for (int i = 1; i <= 25; i++) {
		List<WebElement> l = driver.findElements(By.id("com.apptivateme.next.ct:id/card_view"));
		int noOfCards = l.size();
		if (videoFound) {
		    break;
		}

		for (int j = 0; j < noOfCards; j++) {
		    try {
			driver.findElement(
			        By.xpath("//*[@resource-id='com.apptivateme.next.ct:id/card_view'][" + (j + 1)
			                + "]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
			        .click();
		    } catch (NoSuchElementException e) {
			swipeBottomToTop(50, 50);
			Thread.sleep(4000);
			try {
			    driver.findElement(
			            By.xpath("//*[@resource-id='com.apptivateme.next.ct:id/card_view'][" + (j + 1)
			                    + "]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
			            .click();
			} catch (NoSuchElementException e1) {
			    swipeBottomToTop(50, 50);
			    Thread.sleep(4000);
			    try {
				driver.findElement(
				        By.xpath(
				                "//*[@resource-id='com.apptivateme.next.ct:id/card_view'][1]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
				        .click();
			    } catch (NoSuchElementException e2) {
				driver.findElement(
				        By.xpath(
				                "//*[@resource-id='com.apptivateme.next.ct:id/card_view'][2]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
				        .click();
			    }
			}

		    }
		    /*
		     * // Premium article handling try { if
		     * (driver.findElement(By.xpath(
		     * "//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/subscription_meter_button']"
		     * )) .isDisplayed()) {
		     * System.out.println("premium article: " + (j + 1));
		     * driver.findElement(By.xpath(
		     * "//android.widget.ImageButton[@content-desc='Navigate up']"
		     * )).click(); Thread.sleep(3000); if (count == 1) {
		     * swipeBottomToTop(); Thread.sleep(3000); } } }
		     */
		    try {
			// Non Premium Video article handling
			driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/subscription_meter_button']")).click();
			if (driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tvVideoTime']")).isDisplayed()) {
			    // driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tvVideoTime']")).click();
			    videoFound = true;
			    break;
			}
		    }

		    // Rest of the Non Premium article handling
		    catch (NoSuchElementException exp1) {
			driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			Thread.sleep(3000);
			if (count == 1) {
			    swipeBottomToTop();
			    Thread.sleep(4000);
			}
		    }

		    if (j == noOfCards - 1) {
			System.out.println("Non Premium/Video article not found in iteration: " + i);
			swipeBottomToTop();
			smallSwipeBottomToTop();
			Thread.sleep(4000);
			count = 1;
		    }

		}
	    }
	    return true;
	} catch (Exception e) {
	    objLog.writeInfo("Video article not found");
	    objLog.writeInfo(e.getMessage());
	    exceptionMessage(e);
	    skip = true;
	    return false;
	}
    }

    public boolean clickVideoGalleryArticleLandscapeAndroid() throws Exception {
	try {
	    int count = 0;
	    boolean videoFound = false;
	    for (int i = 1; i <= 15; i++) {
		List<WebElement> l = driver.findElements(By.id("com.apptivateme.next.ct:id/card_view"));
		int noOfCards = l.size();
		if (videoFound) {
		    break;
		}

		for (int j = 0; j < noOfCards; j++) {
		    try {
			driver.findElement(
			        By.xpath("//*[@resource-id='com.apptivateme.next.ct:id/card_view'][" + (j + 1)
			                + "]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
			        .click();
		    } catch (NoSuchElementException e) {
			smallSwipeBottomToTop();
			Thread.sleep(4000);
			try {
			    driver.findElement(
			            By.xpath("//*[@resource-id='com.apptivateme.next.ct:id/card_view'][" + (j + 1)
			                    + "]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
			            .click();
			} catch (NoSuchElementException e1) {
			    smallSwipeBottomToTop();
			    Thread.sleep(4000);
			    if (j == 4 || j == 3) {
				driver.findElement(
				        By.xpath(
				                "//*[@resource-id='com.apptivateme.next.ct:id/card_view'][1]/android.widget.RelativeLayout/*[@resource-id='com.apptivateme.next.ct:id/cell_main_area']"))
				        .click();
			    }
			}

		    }
		    /*
		     * // Premium article handling try { if
		     * (driver.findElement(By.xpath(
		     * "//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/subscription_meter_button']"
		     * )) .isDisplayed()) {
		     * System.out.println("premium article: " + (j + 1));
		     * driver.findElement(By.xpath(
		     * "//android.widget.ImageButton[@content-desc='Navigate up']"
		     * )).click(); Thread.sleep(3000); if (count == 1) {
		     * swipeBottomToTop(); Thread.sleep(3000); } } }
		     */

		    try {
			// Non Premium Video article handling
			driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/subscription_meter_button']")).click();
			if (driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tvVideoTime']")).isDisplayed()) {
			    // driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tvVideoTime']")).click();
			    videoFound = true;
			    break;
			}
		    }

		    // Rest of the Non Premium article handling
		    catch (NoSuchElementException exp1) {
			driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			Thread.sleep(3000);
			/*
			 * if (count == 1) { swipeBottomToTop();
			 * Thread.sleep(4000); }
			 */
		    }

		    if (j == noOfCards - 1) {
			System.out.println("Non Premium/Video article not found in iteration: " + i);
			swipeBottomToTop();
			// smallSwipeBottomToTop();
			Thread.sleep(4000);
			count = 1;
		    }

		}
	    }
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    property.setRemarks("Video article not found");
	    objLog.writeInfo("Video article not found");
	    return false;
	}
    }

    public boolean smallSwipeBottomToTop() throws Exception {
	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");

	    if ((attribute.equalsIgnoreCase("common") || attribute.equalsIgnoreCase("NA"))) {
		size = driver.manage().window().getSize();
		startX = size.width / 2;
		startY = (int) (size.height * 0.95);
		endY = (int) (size.height * 0.75);
		((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
		return true;
	    } else {
		explicitWait("elementisvisible", null);
		Point location = testObject.getLocation();
		size = testObject.getSize();
		startX = size.width / 2 + location.getX();
		startY = (int) (size.width * 0.95) + location.getY();
		endY = (int) (size.height * 0.25) + location.getY();
		return true;
	    }

	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}

    }

    public Boolean apendRandomTextFieldValue(String Text) throws Exception {
	try {
	    explicitWait("elementisvisible", null);
	    ((AppiumDriver) driver).rotate(ScreenOrientation.PORTRAIT);
	    int randomNum = 10 + (int) (Math.random() * ((100000 - 10) + 1));
	    testObject.sendKeys(Text + randomNum);
	    if (this.property.getBrowserName().contains("landscape")) {
		((AppiumDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
	    }

	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }

    public Boolean clickFacebookPOST() throws Exception {
	try {
	    WebDriverWait localWait = new WebDriverWait(driver, 15);
	    try {
		localWait.until(ExpectedConditions.presenceOfElementLocated(By
		        .xpath("//android.widget.TextView[@resource-id='com.facebook.katana:id/primary_named_button']")));
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.facebook.katana:id/primary_named_button']")).click();
	    } catch (Exception e) {
		localWait.until(ExpectedConditions.presenceOfElementLocated(By
		        .xpath("//android.widget.TextView[@resource-id='com.facebook.katana:id/button_share']")));
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.facebook.katana:id/button_share']")).click();
	    }
	    return true;
	} catch (Exception e) {
	    exceptionMessage(e);
	    return false;
	}
    }
}
