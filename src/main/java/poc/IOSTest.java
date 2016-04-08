package poc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.Assert;

public class IOSTest {

    public static void main(String args[]) throws InterruptedException, IOException {
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability("app", "/Users/sgarg/Documents/xebia/tribune/code/xebiacode/Tribune/src/main/resources/apps/ios/ChicagoTribune.app");
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

	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")));
	driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")).click();
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")));
	driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")).click();
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")));
	driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")).click();
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")));
	driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")).click();

	int loopCounter = 1;
	for (int i = 2; i <= 4; i++) {
	    System.out.println("article: " + "//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[2]/UIATableView[1]/UIATableCell[" + i
		    + "]");
	    waitvar.until(ExpectedConditions.elementToBeClickable(By
		    .xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[2]/UIATableView[1]/UIATableCell[" + i + "]")));
	    driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[2]/UIATableView[1]/UIATableCell[" + i + "]"))
		    .click();
	    try {
		System.out.println("Button: " + "//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[" + (i - 1) + "]/UIAButton[1]");
		waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell["
		        + (i - 1) + "]/UIAButton[1]")));

		// if (driver
		// .findElement(
		// By.xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell["
		// + (i - 1) + "]/UIAButton[1]"))
		// .isDisplayed()) {
		loopCounter = (i - 1);
		System.out.println("found. i= " + loopCounter);
		// driver.findElement(
		// By.xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell["
		// + i + "]/UIAButton[1]")).click();
		break;

	    } catch (SessionNotFoundException e) {
		System.out.println("Application and Appium Crashed");
	    } catch (NoSuchElementException e) {
		System.out.println("inside catch. i= " + i);
		driver.findElement(By.name("header back")).click();
		if (i == 4) {
		    i = 2;
		    org.openqa.selenium.Dimension size;
		    int startX, endX, startY, endY;
		    size = driver.manage().window().getSize();
		    startX = size.width / 2;
		    startY = (int) (size.height * 0.90);
		    endY = (int) (size.height * 0.10);
		    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
		    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 1000);
		}
	    }

	}
	System.out.println("lp: " + loopCounter);
	Thread.sleep(5000);
	driver.quit();
	System.out.println("done");

    }
}
