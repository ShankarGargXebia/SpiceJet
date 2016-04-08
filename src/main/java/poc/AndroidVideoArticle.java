/**
 * 
 */
package poc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author hemasundar
 *
 */
public class AndroidVideoArticle {
    static Properties prop;
    private static AndroidDriver driver;
    private static WebDriverWait waitvar;
    private static Properties locators;

    /**
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
	locators = loadProp();

	launchApp();

	waitvar = new WebDriverWait(driver, 90);
	Thread.sleep(5000);

	// click("LaunchAdv");
	// wait for the first article image to be clickable
	By firstArticleImage = getLocator("FirstArticleImage");
	waitvar.until(ExpectedConditions.elementToBeClickable(firstArticleImage));

	// Moving to Nation/World section
	org.openqa.selenium.Dimension size;
	int startX, endX, startY, endY;
	size = driver.manage().window().getSize();
	startX = (int) (size.width * 0.90);
	endX = (int) (size.width * 0.10);
	startY = (int) (size.height * 0.70);
	((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);
	((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);
	((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);

	findAndClickOnVideoArticle();

	driver.quit();
    }

    /**
     * 
     */
    public static boolean findAndClickOnVideoArticle() {
	List ReadArticles = new ArrayList<String>();
	try {
	    // Total at max we do 50 swipes to check for the video article
	    for (int i = 1; i <= 50; i++) {
		// Getting all the article texts in the view port
		List<WebElement> l = driver.findElements(getLocator("FirstArticleText"));
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
			System.out.println(
			        "NoSuchElementException occurred while getting the text of the article at " + j + ". So need to swipe to see new articles.");
			break;
		    }
		    // checking whether the article already read or not
		    if (!ReadArticles.contains(currentArticleText)) {
			try {
			    // opening the current article
			    driver.findElement(articleText).click();
			    try {
				// Click on See This Story button
				click("SeeThisStoryButton");
			    } catch (Exception e1) {
				System.out.println("See This Story button not displayed for the article");
			    }
			    // Get the current reading article heading
			    By articleHeading = getLocator("ArticleHeading");
			    new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(articleHeading));
			    String headingText = driver.findElement(articleHeading).getText();
			    System.out.println("just read article is " + headingText);
			    // Adding the article heading to ReadArticles list
			    ReadArticles.add(headingText);
			    try {
				// Check whether the article contains video
				if (driver.findElement(getLocator("VideoPlayButton")).isDisplayed()) {
				    System.out.println("Current article is video article");
				    return true;
				}
			    } catch (Exception e) {
				System.out.println("Current articl is not a video article");
			    }

			    click("ArticleBack");
			    Thread.sleep(3000);

			} catch (Exception e) {
			    System.out.println("Exception occurred " + e);
			}
		    } else {
			System.out.println("Current article is already read.");
		    }

		}
		System.out.println("Non Premium/Video article not found in iteration: " + i);
		swipeBottomToTop(0.95, 0.45);
		Thread.sleep(4000);
	    }

	} catch (Exception e) {
	    System.out.println("Video article not found" + e);

	}
	System.out.println("Video article not found after looking for almost 30 view ports");
	return false;
    }

    /**
     * @throws InterruptedException
     * 
     */
    private static void swipeBottomToTop(double startY1, double endY1) throws InterruptedException {
	org.openqa.selenium.Dimension size;
	int startX;

	size = driver.manage().window().getSize();

	Thread.sleep(2000);
	startX = size.width / 2;
	int startY = (int) (size.height * startY1);
	int endY = (int) (size.height * endY1);

	((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
    }

    /**
     * 
     */
    public static void sendKeys(String objectName, String textToEnter) {
	By object = getLocator(objectName);
	waitvar.until(ExpectedConditions.visibilityOfElementLocated(object));
	driver.findElement(object).clear();
	driver.findElement(object).sendKeys(textToEnter);
    }

    /**
     * 
     */
    public static void click(String objectName) {
	By object = getLocator(objectName);
	waitvar.until(ExpectedConditions.elementToBeClickable(object));
	driver.findElement(object).click();
    }

    /**
     * @throws InterruptedException
     */
    public static void switchToWebView(String webViewName) throws InterruptedException {
	Set contextNames = ((AppiumDriver) driver).getContextHandles();
	Iterator iter = contextNames.iterator();
	while (iter.hasNext()) {
	    String cn = iter.next().toString();

	    System.out.println(cn);

	    // if context contains viewName than Switch to that context
	    if (cn.contains(webViewName)) {
		// System.out.println(viewName);
		Thread.sleep(10000);
		System.out.println("switching the context");
		((AppiumDriver) driver).context(webViewName);
		System.out.println(driver.getCurrentUrl());
		break;
	    }
	}
    }

    /**
     * @throws MalformedURLException
     */
    public static void launchApp() throws MalformedURLException {
	/*
	 * DesiredCapabilities caps = new DesiredCapabilities();
	 * caps.setCapability("browserName", ""); caps.setCapability("platform",
	 * "ANDROID"); caps.setCapability("deviceName", "Android 5.0 Emulator");
	 * caps.setCapability("platformVersion", "5.0");
	 * //caps.setCapability("autoWebview", "true");
	 * caps.setCapability("appPackage", "com.apptivateme.next.ct");
	 * caps.setCapability("appActivity",
	 * "com.tribune.universalnews.MainActivity");
	 * 
	 * caps.setCapability("appiumVersion", "1.4.14");
	 * caps.setCapability("app", "sauce-storage:apps-ct-release.apk.zip");
	 * 
	 * URL sauceUrl = new URL(
	 * "http://shnakeygarg:66c91399-8cfb-46ca-a2da-f09c2ce5f170@ondemand.saucelabs.com:80/wd/hub"
	 * ); // URL sauceUrl = new URL(
	 * "http://hpenugonda1988:0c96745f-0bc4-49d7-90f7-5f95d9fbe110@ondemand.saucelabs.com:80/wd/hub"
	 * );
	 * 
	 * driver = new AndroidDriver(sauceUrl, caps);
	 */

	final File classpathRoot = new File(System.getProperty("user.dir"));
	final File appDir = new File(classpathRoot, "src/main/resources/apps/android");
	final File app = new File(appDir, "apps-ct-release.apk");

	DesiredCapabilities caps = new DesiredCapabilities();
	caps.setCapability("browserName", "");
	caps.setCapability("platform", "ANDROID");
	caps.setCapability("deviceName", "AndroidPhone");
	caps.setCapability("platformVersion", "5.0");
	// caps.setCapability("udid", "192.168.56.101:5555");
	// caps.setCapability("avd", "AndroidPhone");
	// capabilities.setCapability("fullReset", "true");
	caps.setCapability("appPackage", "com.apptivateme.next.ct");
	caps.setCapability("appActivity", "com.tribune.universalnews.MainActivity");
	caps.setCapability("app", app.getAbsolutePath());

	driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    /**
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static Properties loadProp() throws IOException, FileNotFoundException {
	String fileSeparator = System.getProperty("file.separator");
	String propertyFileLocation = "src" + fileSeparator + "main" + fileSeparator + "java" + fileSeparator + "testDriver" + fileSeparator
	        + "locators.properties";

	prop = new Properties();
	prop.load(new FileInputStream(new File(propertyFileLocation)));

	prop.list(System.out);
	return prop;
    }

    /**
     * @return
     * 
     */
    private static By getLocator(String name) {
	Set<String> stringPropertyNames = prop.stringPropertyNames();
	for (String indKey : stringPropertyNames) {
	    String[] split = indKey.split("\\.");
	    if (split[2].equalsIgnoreCase(name)) {
		String[] sample = { split[3], prop.getProperty(indKey) };
		switch (split[3].toLowerCase()) {
		case "id":
		    return By.id(prop.getProperty(indKey));

		case "xpath":
		    return By.xpath(prop.getProperty(indKey));

		case "class":
		    return By.className(prop.getProperty(indKey));

		case "name":
		    return By.name(prop.getProperty(indKey));

		default:
		    break;
		}

	    }
	}
	return null;
    }
}
