package poc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.Assert;

public class AndroidTest {

    public static void main(String args[]) throws MalformedURLException, InterruptedException {
	final File classpathRoot = new File(System.getProperty("user.dir"));
	final File appDir = new File(classpathRoot, "src/main/resources/apps/android");
	final File app = new File(appDir, "apps-ct-release.apk");

	DesiredCapabilities caps = new DesiredCapabilities();
	caps.setCapability("browserName", "");
	caps.setCapability("platform", "ANDROID");
	caps.setCapability("deviceName", "AndroidPhone");
	caps.setCapability("platformVersion", "5.0");
	caps.setCapability("autoWebview", "true");
	caps.setCapability("appPackage", "com.apptivateme.next.ct");
	caps.setCapability("appActivity", "com.tribune.universalnews.MainActivity");
	caps.setCapability("app", app.getAbsolutePath());

	AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);

	// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	WebDriverWait waitvar = new WebDriverWait(driver,15);

	Thread.sleep(5000);
/*
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")));
	driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")).click();
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")));
	driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")).click();
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")));
	driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")).click();
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")));
	driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.apptivateme.next.ct:id/btn_special_next']")).click();
*/
	waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/card_view")));
	driver.findElement(By.id("com.apptivateme.next.ct:id/card_view")).click();
	
	waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/subscription_meter_button")));
	driver.findElement(By.id("com.apptivateme.next.ct:id/subscription_meter_button")).click();
	
	waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.LinearLayout[@resource-id='com.apptivateme.next.ct:id/article_content']")));
	//android.widget.ImageView[@resource-id='com.apptivateme.next.ct:id/share_facebook']
	
	//Bottom ad
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/bottom_cube_ad_container")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.id("com.apptivateme.next.ct:id/bottom_cube_ad_container")).isDisplayed()){
			     System.out.println("Bottom displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			org.openqa.selenium.Dimension size;
			int startX, endX, startY, endY;

			((AppiumDriver) driver).context("NATIVE_APP");
			size = driver.manage().window().getSize();
			startX = size.width * 50 / 100;
			startY = size.height * 75 / 100;
			endY = size.height * 25 / 100;
			System.out.println(startX+" "+startY+" "+startX+" "+endY);
			((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");
		
	
	//Top Article Author
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/article_author")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.id("com.apptivateme.next.ct:id/article_author")).isDisplayed()){
			     System.out.println("Author displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			org.openqa.selenium.Dimension size;
			int startX, endX, startY, endY;

			((AppiumDriver) driver).context("NATIVE_APP");
			size = driver.manage().window().getSize();
			startX = size.width * 50 / 100;
			startY = size.height * 25 / 100;
			endY = size.height * 75 / 100;
			System.out.println(startX+" "+startY+" "+startX+" "+endY);
			((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");
	
	//Top Ad
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/dfp_adtop_container_filler")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.id("com.apptivateme.next.ct:id/dfp_adtop_container_filler")).isDisplayed()){
			     System.out.println("TopAd displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			org.openqa.selenium.Dimension size;
			int startX, endX, startY, endY;

			((AppiumDriver) driver).context("NATIVE_APP");
			size = driver.manage().window().getSize();
			startX = size.width * 50 / 100;
			startY = size.height * 25 / 100;
			endY = size.height * 75 / 100;
			System.out.println(startX+" "+startY+" "+startX+" "+endY);
			((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");
		
	
	//Middle Ad
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/cube_ad_container")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.id("com.apptivateme.next.ct:id/cube_ad_container")).isDisplayed()){
			     System.out.println("middle displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			org.openqa.selenium.Dimension size;
			int startX, endX, startY, endY;

			((AppiumDriver) driver).context("NATIVE_APP");
			size = driver.manage().window().getSize();
			startX = size.width * 50 / 100;
			startY = size.height * 75 / 100;
			endY = size.height * 25 / 100;
			System.out.println(startX+" "+startY+" "+startX+" "+endY);
			((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");

	
	//Bottom Ad
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/bottom_cube_ad_container")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.id("com.apptivateme.next.ct:id/bottom_cube_ad_container")).isDisplayed()){
			     System.out.println("Bottom displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			org.openqa.selenium.Dimension size;
			int startX, endX, startY, endY;

			((AppiumDriver) driver).context("NATIVE_APP");
			size = driver.manage().window().getSize();
			startX = size.width * 50 / 100;
			startY = size.height * 75 / 100;
			endY = size.height * 25 / 100;
			System.out.println(startX+" "+startY+" "+startX+" "+endY);
			((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");
	
	driver.quit();
	System.out.println("done");
    }
    
 

}
