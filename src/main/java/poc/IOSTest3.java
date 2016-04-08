package poc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.Assert;

public class IOSTest3 {

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
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	WebDriverWait waitvar = new WebDriverWait(driver, 30);
	//MobileBy.ByIosUIAutomation mobileWait = new ByIosUIAutomation(uiautomationText)

	Thread.sleep(5000);

	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();
	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();
	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();
	waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".buttons()[0]")));
	driver.findElementByIosUIAutomation(".buttons()[0]").click();

	final Date date1 = new Date();
        System.out.println("findElementByIosUIAutomation" );
       
        //UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[2]/UIATableView[1]/UIATableCell[2]
         waitvar.until(ExpectedConditions.elementToBeClickable(MobileBy.IosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[1].withPredicate(\"ALL staticTexts.isVisible == TRUE\")")));
        //waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[1].withPredicate(\"ALL staticTexts.isVisible == TRUE\")")));
        WebElement firstArtile = driver.findElementByIosUIAutomation(".collectionViews()[0].cells()[1].tableViews()[0].cells()[1].withPredicate(\"ALL staticTexts.isVisible == TRUE\")");
        firstArtile.click();
        System.out.println("clicked element1");

        final Date date2 = new Date();

        //UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]
        waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByIosUIAutomation(".navigationBars()[0].buttons()[0]")));
	driver.findElementByIosUIAutomation(".navigationBars()[0].buttons()[0]").click();
        final Date date3 = new Date();
/*
        System.out.println("\n\n\n\nxpath\n\n\n\n");

        waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[2]/UIATableView[1]/UIATableCell[2]")));
        driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIACollectionView[1]/UIACollectionCell[2]/UIATableView[1]/UIATableCell[2]")).click();

        final Date date4 = new Date();

        waitvar.until(ExpectedConditions.elementToBeClickable(driver.findElementByName("header back")));

        driver.findElement(By.name("header back")).click();
        */
        final Date date5 = new Date();
        System.out.println("IOS Automator :" + (date2.getTime() - date1.getTime()));
        System.out.println("Back :" + (date3.getTime() - date2.getTime()));

        //System.out.println("XPath :" + (date4.getTime() - date3.getTime()));
        //System.out.println("Back :" + (date5.getTime() - date4.getTime()));
	Thread.sleep(5000);
	driver.quit();
	System.out.println("done");

    }
}
