/**
 * 
 */
package poc;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

/**
 * @author hema
 *
 */
public class AndroidChrome {

    private static AndroidDriver driver;

    /**
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {

	DesiredCapabilities caps = new DesiredCapabilities();
	caps.setCapability("browserName", "chrome");
	caps.setCapability("platform", "ANDROID");
	caps.setCapability("deviceName", "AndroidPhone");
	caps.setCapability("platformVersion", "5.0");
	caps.setCapability("appPackage", "com.apptivateme.next.ct");
	caps.setCapability("appActivity", "com.tribune.universalnews.MainActivity");

	driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);

	driver.get("http://nguxbeta:nguxtr!b@ngux.baltimoresun.stage.tribdev.com/qa/automation/barkers/02/15");

    }

}
