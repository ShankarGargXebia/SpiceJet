/**
 * 
 */
package poc;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

/**
 * @author hemasundar
 *
 */
public class IOSSauceLabsNew {

    /**
     * @param args
     * @throws InterruptedException 
     * @throws MalformedURLException 
     */
    public static void main(String[] args) throws InterruptedException, MalformedURLException {

	DesiredCapabilities caps = new DesiredCapabilities();
	//caps.setCapability("browserName", "");
	caps.setCapability("platform", "iOS");
	caps.setCapability("deviceName", "iPhone 6");
	caps.setCapability("platformVersion", "9.0");
	caps.setCapability("waitForAppScript", "$.delay(10000); $.acceptAlert();");
	//caps.setCapability("autoWebview", "true");
	//caps.setCapability("appPackage", "com.apptivateme.next.ct");
	//caps.setCapability("appActivity", "com.tribune.universalnews.MainActivity");
	caps.setCapability("appiumVersion", "1.4.14");
	caps.setCapability("app", "sauce-storage:CT_iOS.zip");
	
	URL sauceUrl = new URL("http://shnakeygarg:66c91399-8cfb-46ca-a2da-f09c2ce5f170@ondemand.saucelabs.com:80/wd/hub");

	IOSDriver driver = new IOSDriver(sauceUrl, caps);

	WebDriverWait waitvar = new WebDriverWait(driver,90);

	Thread.sleep(5000);

	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();
	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();
	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();
	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();

        System.out.println("findElementByIosUIAutomation" );
       

        waitvar.until(ExpectedConditions.elementToBeClickable(MobileBy.IosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[1].withPredicate(\"ALL staticTexts.isVisible == TRUE\")")));
        WebElement firstArtile = driver.findElementByIosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[1].withPredicate(\"ALL staticTexts.isVisible == TRUE\")");
        firstArtile.click();
        System.out.println("clicked element1");

//	    Swipe right to left
	    Thread.sleep(5000);
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;
		    
	    size = driver.manage().window().getSize();
	    startX = (int) (size.width * 0.90);
	    endX = (int) (size.width * 0.10);
	    startY = (int)(size.height * 0.70);
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);

//	    Swipe left to right
	    Thread.sleep(5000);
	    startX = (int) (size.width * 0.10);
	    endX = (int) (size.width * 0.90);
	    startY = size.height / 2;
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);

//	    Swipe bottom to top
	    Thread.sleep(5000);
	    startX = size.width / 2;
	    startY = (int) (size.height * 0.95);
	    endY = (int) (size.height * 0.25);
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
	    
	    driver.quit();
	    System.out.println("Execution completed successfully.");

    }

}
