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
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author hemasundar
 *
 */
public class AndroidLogin {
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
	
	waitvar = new WebDriverWait(driver,90);
	Thread.sleep(5000);

	click("LaunchAdv");
//	wait for the first article image to be clickable
	By firstArticleImage= getLocator("FirstArticleImage");
	waitvar.until(ExpectedConditions.elementToBeClickable(firstArticleImage));

	click("WidgetButton");

	click("Account");

	click("LogInLink");

	click("FaceBookLoginIcon");

	switchToWebView("WEBVIEW_com.apptivateme.next.ct");

	sendKeys("FBEmail", "xebiaTribune@yahoo.com");

	sendKeys("FBPassword", "Mar@2014@");
	
	click("FBLogInButton");
//	Switch to native view
	((AppiumDriver) driver).context("NATIVE_APP");
//	Verify text of object EmailText
	By emailText = getLocator("EmailText");
	waitvar.until(ExpectedConditions.visibilityOfElementLocated(emailText));
	String actualText = driver.findElement(emailText).getText();
	if (actualText.contains("xebiatribune@yahoo.com")) {
	    System.out.println("Verification successfull");
	}
	driver.quit();
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
	By object= getLocator(objectName);
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
	DesiredCapabilities caps = new DesiredCapabilities();
	caps.setCapability("browserName", "");
	caps.setCapability("platform", "ANDROID");
	caps.setCapability("deviceName", "Android 5.0 Emulator");
	caps.setCapability("platformVersion", "5.0");
	//caps.setCapability("autoWebview", "true");
	caps.setCapability("appPackage", "com.apptivateme.next.ct");
	caps.setCapability("appActivity", "com.tribune.universalnews.MainActivity");
	
	caps.setCapability("appiumVersion", "1.4.14");
	caps.setCapability("app", "sauce-storage:apps-ct-release.apk.zip");

	URL sauceUrl = new URL("http://shnakeygarg:66c91399-8cfb-46ca-a2da-f09c2ce5f170@ondemand.saucelabs.com:80/wd/hub");
//	URL sauceUrl = new URL("http://hpenugonda1988:0c96745f-0bc4-49d7-90f7-5f95d9fbe110@ondemand.saucelabs.com:80/wd/hub");

	driver = new AndroidDriver(sauceUrl, caps);
	
	/*	final File classpathRoot = new File(System.getProperty("user.dir"));
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

	    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);*/
    }
    /**
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static Properties loadProp() throws IOException, FileNotFoundException {
	String fileSeparator = System.getProperty("file.separator");
	String propertyFileLocation = "src" + fileSeparator + "main" + fileSeparator+ "java" + fileSeparator + "testDriver" + fileSeparator+ "locators.properties";

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
	String[] split= indKey.split("\\.");
	if (split[2].equalsIgnoreCase(name)) {
	    String[] sample = {split[3], prop.getProperty(indKey)};
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
