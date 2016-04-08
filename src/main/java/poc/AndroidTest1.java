package poc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.testng.Assert;

public class AndroidTest1 {

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
	//driver.findElement(By.id("com.apptivateme.next.ct:id/card_view")).click();
	
	//waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/subscription_meter_button")));
	//driver.findElement(By.id("com.apptivateme.next.ct:id/subscription_meter_button")).click();
	
	//waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.LinearLayout[@resource-id='com.apptivateme.next.ct:id/article_content']")));
	//android.widget.ImageView[@resource-id='com.apptivateme.next.ct:id/share_facebook']
	
	//RightMost Tab
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tabTitle' and @text='Southtown']")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/sliding_tabs_sections")));
	    WebElement testObject = driver.findElement(By.id("com.apptivateme.next.ct:id/sliding_tabs_sections"));
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tabTitle' and @text='Southtown']")).isDisplayed()){
			     System.out.println("Rightmost tab displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			((AppiumDriver) driver).context("NATIVE_APP");
			    org.openqa.selenium.Dimension size;
			    int startX, endX, startY;	    
				Point location = testObject.getLocation();
				size = testObject.getSize();
				startX = (int) (size.width * 0.80) + location.getX();
				endX = (int) (size.width * 0.20) + location.getX();
				startY = (size.height / 2) + location.getY();
			    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);
			    Thread.sleep(1000);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");
	
	//Left Tab
	try{
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tabTitle' and @text='Top Stories']")));
	    System.out.println("displayed without i");
	}
	catch (NoSuchElementException | TimeoutException  e){
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/sliding_tabs_sections")));
	    WebElement testObject = driver.findElement(By.id("com.apptivateme.next.ct:id/sliding_tabs_sections"));
	    for (int i=1;i<=50;i++){
		    try{
			 if(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tabTitle' and @text='Top Stories']")).isDisplayed()){
			     System.out.println("Rightmost tab displayed: "+i);
				break;
			 }
		    } catch (NoSuchElementException | TimeoutException ee){
			System.out.println("not displayed: "+i);
			((AppiumDriver) driver).context("NATIVE_APP");
			    org.openqa.selenium.Dimension size;
			    int startX, endX, startY;	    
				Point location = testObject.getLocation();
				size = testObject.getSize();
				startX = (int) (size.width * 0.20) + location.getX();
				endX = (int) (size.width * 0.80) + location.getX();
				startY = (size.height / 2) + location.getY();
			    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);
			    Thread.sleep(1000);
		    }
		    if(i==50){
			System.out.println("Loop Maxout");
		    }
		}
	}
	System.out.println("out of loop");		
		
		//Middile Tab
		try{
		    waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tabTitle' and @text='Columnists']")));
		    System.out.println("displayed without i");
		}
		catch (NoSuchElementException | TimeoutException  e){
		    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/sliding_tabs_sections")));
		    WebElement testObject = driver.findElement(By.id("com.apptivateme.next.ct:id/sliding_tabs_sections"));
		    for (int i=1;i<=50;i++){
			    try{
				 if(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.apptivateme.next.ct:id/tabTitle' and @text='Columnists']")).isDisplayed()){
				     System.out.println("columinst tab displayed: "+i);
					break;
				 }
			    } catch (NoSuchElementException | TimeoutException ee){
				System.out.println("not displayed: "+i);
				((AppiumDriver) driver).context("NATIVE_APP");
				    org.openqa.selenium.Dimension size;
				    int startX, endX, startY;	    
					Point location = testObject.getLocation();
					size = testObject.getSize();
					startX = (int) (size.width * 0.80) + location.getX();
					endX = (int) (size.width * 0.20) + location.getX();
					startY = (size.height / 2) + location.getY();
				    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);
				    Thread.sleep(1000);
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
