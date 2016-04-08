/**
 * 
 */
package poc;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author hemasundar
 *
 */
public class AndroidSauceLabsNew {

    private static AndroidDriver driver;

    /**
     * @param args
     * @throws MalformedURLException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
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
//		URL sauceUrl = new URL("http://hpenugonda1988:0c96745f-0bc4-49d7-90f7-5f95d9fbe110@ondemand.saucelabs.com:80/wd/hub");

		driver = new AndroidDriver(sauceUrl, caps);
		
		
		
		
		
		
	    WebDriverWait waitvar = new WebDriverWait(driver,90);
	    Thread.sleep(2000);
//		Thread.sleep(20000);
//		System.out.println(driver.getPageSource());
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/launch_ad_close_btn")));
	    driver.findElement(By.id("com.apptivateme.next.ct:id/launch_ad_close_btn")).click();
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.id("com.apptivateme.next.ct:id/card_view")));
	    driver.findElement(By.id("com.apptivateme.next.ct:id/card_view")).click();
		
	    Thread.sleep(2000);
	    try {
		if (driver.findElement(By.id("com.apptivateme.next.ct:id/subscription_meter_button")).isDisplayed()) {
		    driver.findElement(By.id("com.apptivateme.next.ct:id/subscription_meter_button")).click();  
		    System.out.println("Opened the premium article");
		}
	    } catch (Exception e) {
		System.out.println("It's a free article");
	    }
	    waitvar.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.LinearLayout[@resource-id='com.apptivateme.next.ct:id/article_content']")));
	    
//	    Swipe right to left
	    Thread.sleep(2000);
	    ((AppiumDriver) driver).context("NATIVE_APP");
	    org.openqa.selenium.Dimension size;
	    int startX, endX, startY, endY;
		    
	    size = driver.manage().window().getSize();
	    startX = (int) (size.width * 0.90);
	    endX = (int) (size.width * 0.10);
	    startY = (int)(size.height * 0.70);
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);

//	    Swipe left to right
	    Thread.sleep(2000);
	    startX = (int) (size.width * 0.10);
	    endX = (int) (size.width * 0.90);
	    startY = size.height / 2;
	    ((AppiumDriver) driver).swipe(startX, startY, endX, startY, 500);

//	    Swipe bottom to top
	    Thread.sleep(2000);
	    startX = size.width / 2;
	    startY = (int) (size.height * 0.95);
	    endY = (int) (size.height * 0.25);
	    ((AppiumDriver) driver).swipe(startX, startY, startX, endY, 500);
	    
	    ((AppiumDriver) driver).rotate(ScreenOrientation.LANDSCAPE);
	    
	    Thread.sleep(2000);
	    
	    driver.quit();
	    System.out.println("Execution completed successfully.");
    }

}
