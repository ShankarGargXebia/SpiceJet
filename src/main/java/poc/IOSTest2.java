package poc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.Assert;

public class IOSTest2 {

    public static void main(String args[]) throws InterruptedException, IOException {
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability("app", "/Users/abhishek/Documents/workspace/Tribune/src/main/resources/apps/ios/ChicagoTribune.app");
	capabilities.setCapability("platformVersion", "9.0");
	capabilities.setCapability("platformName", "iOS");
	capabilities.setCapability("deviceName", "iPhone 6");

	// capabilities.setCapability("fullReset", "true");
	capabilities.setCapability("waitForAppScript", "$.delay(3000); $.acceptAlert();");
	// capabilities.setCapability("autoAcceptAlerts", "true");

	IOSDriver driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
	((AppiumDriver) driver).context("NATIVE_APP");
	// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	WebDriverWait waitvar = new WebDriverWait(driver, 90);

	Thread.sleep(5000);
	try {
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;

	    ((AppiumDriver) driver).context("NATIVE_APP");
	    size = driver.manage().window().getSize();
	    startX = size.width / 2;
	    startY = (int) (size.height * 0.95);
	    endY = (int) (size.height * 0.25);
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	// waitvar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'Photos:')]")));
	// driver.findElement(By.xpath("//*[contains(@name,'Photos:')]")).click();

	Thread.sleep(5000);
	driver.quit();
	System.out.println("done");

    }
}
