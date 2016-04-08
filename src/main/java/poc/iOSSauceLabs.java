package poc;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.Assert;

public class iOSSauceLabs {

    public static void main(String args[]) throws MalformedURLException, InterruptedException {
//	final File classpathRoot = new File(System.getProperty("user.dir"));
//	final File appDir = new File(classpathRoot, "src/main/resources/apps/android");
//	final File app = new File(appDir, "apps-ct-release.apk");

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

	// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
