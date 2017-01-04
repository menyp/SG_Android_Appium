package Native;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.ios.AndroidDriver;















import com.applitools.eyes.Eyes;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;

import io.appium.java_client.android.AndroidKeyCode;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import com.google.common.base.Function;




public class AndroidMethods {
	
	//QA Variables
	final String QAAuthorizationURL = "https://sgwin2012r2.skygiraffe.com/SkyGiraffeAuthorizationServer/oauth2/token";
	final String QADistributionURL = "https://sgwin2012r2.skygiraffe.com/Publisher/api/V1";
	final String QAclient_ID = "099153c2625149bc8ecb3e85e03f0022";
	final String QAclient_Secret = "IxrAjDoa2FqElO7IhrSrUJELhUckePEPVpaePlS_Xaw";

	
	//Staging Variables
	final String StagingAuthorizationURL = "https://skygiraffeauthorizationserver-staging.azurewebsites.net/oauth2/token";
	final String StagingDistributionURL = "https://skygiraffepublisher-staging.azurewebsites.net/api/v1";
	final String Stagingclient_ID = "099153c2625149bc8ecb3e85e03f0022";
	final String Stagingclient_Secret = "IxrAjDoa2FqElO7IhrSrUJELhUckePEPVpaePlS_Xaw";
		
	AndroidDriver<MobileElement> driver;
	AndroidElements DroidData;
	Eyes eyes = new Eyes();
	Boolean useEye = false;
	Boolean skipfailure = true;
	Boolean qaENV = true;
	
	
	

	public void cleanLoginDroid(AndroidMethods genMeth, EnvironmentMode mode) throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		
		
		switch (mode) {

		case QA:
			
			genMeth.configureENV(genMeth, QAAuthorizationURL, QADistributionURL, QAclient_ID, QAclient_Secret);

			break;


		case Staging:
			genMeth.configureENV(genMeth, StagingAuthorizationURL, StagingDistributionURL, Stagingclient_ID, Stagingclient_Secret);


			break;

		case Prod:

			break;
		}
		

		
		//Login to the client
		genMeth.sendId(genMeth,"com.skygiraffe.operationaldata:id/login_screen_email_field",DroidData.userQA);
		genMeth.sendId(genMeth,"com.skygiraffe.operationaldata:id/login_screen_password_field",DroidData.passwordQA);
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/login_screen_authentication_btn");

		//Check if default app is open
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden Ap",useEye, genMeth, skipfailure);

	}

	
	public void configureENV(AndroidMethods genMeth, String AuthURL, String DistURL,String Client_ID, String Client_Secret) throws InterruptedException, IOException{
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_settings_image_btn");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/gserver_config_activity_add_new");
		genMeth.clickXpthName_TextView(genMeth, "Create new");
		
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/create_environment_name_ed", "QA ENV");
		genMeth.clickXpthName_TextView(genMeth, "CREATE");
		
		//Add Authentication server
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/environment_server_config_server_url", AuthURL);

		
		//Add Client ID
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/environment_server_config_server_add_new");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/additional_field_key", "client_id");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/additional_field_value", Client_ID);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/additional_field_save");
		
		
		//Add Client secret
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/environment_server_config_server_add_new");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/additional_field_key", "client_secret");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/additional_field_value", Client_Secret);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/additional_field_save");
		
		genMeth.clickXpthName_TextView(genMeth, "DIST SERVER");
		genMeth.sendXpthName_EditText(genMeth, "Server URL", DistURL);		

		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/environment_preview_activity_save");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/sgserver_config_activity_close_btn");
		
	}
	
	
	
	public void eyesCheckWindow(String testName, Boolean useEye, AndroidMethods genMeth, boolean  skipfailure)

			throws InterruptedException, IOException {
			
		
		if (useEye) {
			Thread.sleep(2000);
			eyes.setMatchTimeout(20);
			eyes.setApiKey("Hbh6716cKDCgn8a9bMAREPM105nbW109PQe0993So5GwFpNM110");
			 //Switch between the versions to generate test failure.
	        String version = "0.2";
	        
	        // Define the OS and hosting application to identify the baseline
	        eyes.setHostOS("Mac");
			eyes.setHostApp("My maxthon browser");
			
			BufferedImage img;

			eyes.setMatchLevel(MatchLevel.STRICT);
			
			//eyes.open("SG_Android", testName, new RectangleSize(785, 1087));  compatible with the old Samsung
			eyes.open("SG_Android", testName, new RectangleSize(785, 1087));  
			// Load page image and validate
			File scrFile = (driver.getScreenshotAs(OutputType.FILE));
			img = ImageIO.read(scrFile);

			// Visual validation point #1
			//Rectangle rect = new Rectangle(0, 0, 1080, 1940);
			Rectangle rect = new Rectangle(0, 0, 1080, 1810);
			//eyes.setSaveNewTests(true);
			eyes.setSaveFailedTests(false);

			img = genMeth.cropImage(img, rect);
			eyes.checkImage(img, "Sample");
	            

				if (skipfailure) {
					// Use the below code instead of eyes.close(); --> It will allow to continue the test even if the UI testing will fail
					com.applitools.eyes.TestResults testResult = eyes.close(false);
					

				}

				else {
					
					eyes.close();

			}

		}
	}


	public void signOutFromStartup(AndroidMethods genMeth)

			throws InterruptedException, IOException {
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/application_logout_button_icon");
	}

		
	@SuppressWarnings("unchecked")
	public AndroidDriver<MobileElement> setCapabilitiesAndroid(AndroidMethods genMeth, AppiumDriverLocalService service)
			throws IOException {
		
		// Login with an existing account
 
		DesiredCapabilities capabilities =  DesiredCapabilities.android();
	   // DesiredCapabilities capabilities = new DesiredCapabilities();

		//capabilities.setCapability("appium-version", genMeth.getValueFromPropFile("appiumVersion"));
	    //capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, genMeth.getValueFromPropFile("appiumVersion"));
	    
		//capabilities.setCapability("platformName", genMeth.getValueFromPropFile("platformName"));
	 //   capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, genMeth.getValueFromPropFile("platformName"));

		//capabilities.setCapability("platformVersion", genMeth.getValueFromPropFile("platformVersion"));
	   // capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, genMeth.getValueFromPropFile("platformVersion"));

		//capabilities.setCapability("deviceName", genMeth.getValueFromPropFile("deviceName"));
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, genMeth.getValueFromPropFile("deviceName"));
		
		//capabilities.setCapability("app",genMeth.getValueFromPropFile("appPath"));
	    capabilities.setCapability(MobileCapabilityType.APP, genMeth.getValueFromPropFile("appPath"));

		capabilities.setCapability("automationName",genMeth.getValueFromPropFile("uiautomator2"));

	    //capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, genMeth.getValueFromPropFile("automationName"));

	    
		//capabilities.setCapability("newCommandTimeout", 1200);
	    capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);


		//capabilities.setCapability("appPackage", genMeth.getValueFromPropFile("appPackage"));
		//capabilities.setCapability("appActivity", genMeth.getValueFromPropFile("appLauncherActivity"));
		//capabilities.setCapability(AndroidMobileCapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);


		

		try {

			driver = new AndroidDriver<MobileElement>(service.getUrl(),capabilities);

		}
		
		catch (AppiumServerHasNotBeenStartedLocallyException e) {

			genMeth.takeScreenShot(driver, genMeth,"Faliled to open Appium driver");
			org.testng.Assert.fail("WebElement"+ " Faliled to open Appium driver");
		}

		return driver;
	}
	
	

	public String getValueFromPropFile(String key) {
		Properties properties = new Properties();
		String value = "";
		try {

			properties.load(getClass().getResourceAsStream(
					"/resources/config.properties"));
			// properties.load(getClass().getResourceAsStream("/resources/webui.properties"));
			{
				value = properties.getProperty(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}

	public void takeScreenShot(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, String imageName) throws IOException {

		File scrFile = (driver.getScreenshotAs(OutputType.FILE));
		String currentTime = genMeth.currentTime();
		String currentDate = genMeth.currentDate();

		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		String imagePath = genMeth.getValueFromPropFile("screenshotPath")
				+ currentDate + "/" + currentTime + "_" + imageName + ".JPG";
		FileUtils.copyFile(scrFile, new File(imagePath));

	}

	public void takeScreenShotPositive(AndroidMethods genMeth, String imageName)
			throws IOException {
		String currentTime = genMeth.currentTime();
		File scrFile = (driver.getScreenshotAs(OutputType.FILE));
		String currentDate = genMeth.currentDate();

		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		String imagePath = genMeth
				.getValueFromPropFile("screenshotPathPositive")
				+ currentDate
				+ "/" + currentTime + "_" + imageName + ".JPG";
		FileUtils.copyFile(scrFile, new File(imagePath));

	}

	/*
	 * ***************************************************
	 * Web Element Handling *
	 * ***************************************************
	 */

	// ==================== RETURN ELEMENT

	public WebElement returnCss(AndroidDriver<MobileElement> driver,
			String cssSelector) throws InterruptedException {

		AndroidMethods genMeth = new AndroidMethods();
		try {

			genMeth.fluentwait(driver, By.cssSelector(cssSelector));

		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement 'by css' can't be located");

		}

		WebElement myElement = genMeth.fluentwait(driver,
				By.cssSelector(cssSelector));
		return myElement;
	}

	public MobileElement returnId(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, String id) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.id(id));

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + " didn't display");

		}

		MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
		return myElement;

	}

	public WebElement returnClassName(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, String className) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.className(className));
		}

		catch (Exception e) {

			org.testng.Assert.fail(className + " didn't display");

		}

		WebElement myElement = genMeth.fluentwait(driver,
				By.className(className));
		return myElement;
	}

	public MobileElement returnXpth(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, String xpth) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.xpath(xpth));

		}

		catch (Exception e) {

			org.testng.Assert.fail(xpth + " didn't display");
		}

		MobileElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
		return myElement;

	}

	public MobileElement returnName(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, String name) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, By.name(name));

		}

		catch (Exception e) {

			org.testng.Assert.fail(name + " didn't display");

		}

		MobileElement myElement = genMeth.fluentwait(driver, By.name(name));
		return myElement;

	}

	public WebElement returnBy(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, By by) throws InterruptedException {

		try {

			genMeth.fluentwait(driver, by);

		}

		catch (Exception e) {

			org.testng.Assert.fail(by + " didn't display");

		}

		WebElement myElement = genMeth.fluentwait(driver, by);
		return myElement;

	}

	// ========= CLICK an ELEMENT
	// =========================================================================

	public void clickBy(AndroidDriver<MobileElement> driver, AndroidMethods genMeth,
			By by) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();
		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement can't be located");

		}

	}

	public void tapBy(AndroidDriver<MobileElement> driver, AndroidMethods genMeth, By by)
			throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			driver.tap(1, myElement, 1000);
		}

		catch (Exception e) {

			org.testng.Assert.fail("WebElement can't be located");

		}

	}



	public void clickId(AndroidMethods genMeth, String id)
			throws InterruptedException, IOException {

		try {
			MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.click();

			// driver.findElementById(id).click();
			

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, id);
			org.testng.Assert.fail(id + " didn't display");

		}
	}

	public void tapId(AndroidMethods genMeth, String id)
			throws InterruptedException {

		try {

			MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
			driver.tap(1, myElement, 1000);

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + " didn't display");

		}
	}


	public void clickXpth(AndroidMethods genMeth, String xpth)
			throws InterruptedException, IOException {
		
		int i= 0;
		By by = By.xpath(xpth);
		
		
		while (i < 2){
			
			try {

				MobileElement myElement = genMeth.fluentwait(driver, by);
				myElement.click();
				i=3;
				// driver.findElementByXPath(xpth).click();

			}

			catch (Exception e) {
				// genMeth.takeScreenShot(driver, genMeth, xpth);
				// org.testng.Assert.fail(xpth + " didn't display");
				i++;
			}

		}
		
		if (i==2){
			
			org.testng.Assert.fail(xpth + " didn't display");

		}

	}

	
	public void clickXpthName_TextView(AndroidMethods genMeth, String xpthName)
			throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.TextView[@text='" + xpthName + "']");
		
		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	public void clickXpthName_Button(AndroidMethods genMeth, String xpthName)
			throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.Button[@text='" + xpthName + "']");

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	
	public void clickXpthName_CheckedTextView(AndroidMethods genMeth,
			String xpthName) throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.CheckedTextView[@text='" + xpthName + "']");

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	public void clickXpthName_ImageView(AndroidMethods genMeth,
			String xpthName) throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.ImageView[@text='" + xpthName + "']");


		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	
	public void clickXpthName_EditText(AndroidMethods genMeth,
			String xpthName) throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.EditText[@text='" + xpthName + "']");


		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	public void clickXpthName_LinearLayout(AndroidMethods genMeth,
			String xpthName) throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.LinearLayout[@index='" + xpthName + "']");


		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.click();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	


	
	public void tapXpth(AndroidMethods genMeth, String xpth)
			throws InterruptedException, IOException {

		By by = By.xpath(xpth);

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			driver.tap(1, myElement, 1000);
		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpth);
			org.testng.Assert.fail(xpth + " didn't display");

		}

	}

	public void clickName(AndroidMethods genMeth, String name)
			throws InterruptedException {

		try {

			MobileElement myElement = genMeth.fluentwait(driver, By.name(name));
			driver.tap(1, myElement, 1000);

		}

		catch (Exception e) {

			org.testng.Assert.fail(name + " didn't display");

		}
	}



	// ======================== SEND ELEMENT====

	public void sendBy(AndroidDriver<MobileElement> driver, AndroidMethods genMeth,
			By by, String send) throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, by);
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, send);
			org.testng.Assert.fail("WebElement'send by' can't be located");

		}

	}


	public void sendId(AndroidMethods genMeth, String id, String send)
			throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, send);
			org.testng.Assert.fail(id + "didn't displayed");

		}

	}


	public void sendXpth(AndroidMethods genMeth, String xpth, String send)
			throws InterruptedException, IOException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
			myElement.sendKeys(send);

		}

		catch (Exception e) {

			genMeth.takeScreenShot(driver, genMeth, send);
			org.testng.Assert.fail(xpth + "didn't displayed");

		}

	}

	public void sendXpthName_EditText(AndroidMethods genMeth, String xpthName, String send)
			throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.EditText[@text='" + xpthName + "']");

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.sendKeys(send);

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}
	
	
	
	/*
	 * public void sendXpth(AndroidDriver<MobileElement> driver, IosMethods genMeth,
	 * String xpth, String send) throws IOException {
	 * 
	 * try {
	 * 
	 * WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpth));
	 * myElement.sendKeys(send);
	 * 
	 * }
	 * 
	 * catch (Exception e) {
	 * 
	 * genMeth.takeScreenShot(driver, genMeth, xpth);
	 * org.testng.Assert.fail(xpth + "didn't displayed");
	 * 
	 * }
	 * 
	 * }
	 */

	// =========================Clear======

	public void clearXpth(AndroidDriver<MobileElement> driver, AndroidMethods genMeth,
			String xpath) throws InterruptedException {

		try {

			WebElement myElement = genMeth.fluentwait(driver, By.xpath(xpath));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(xpath + "didn't displayed");

		}

	}

	
	public void clearXpthName_TextView(AndroidMethods genMeth, String xpthName)
			throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.TextView[@text='" + xpthName + "']");

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.clear();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}

	public void clearXpthName_EditText(AndroidMethods genMeth, String xpthName)
			throws InterruptedException, IOException {

		By by = By.xpath("//android.widget.EditText[@text='" + xpthName + "']");

		try {

			MobileElement myElement = genMeth.fluentwait(driver, by);
			myElement.clear();

		}

		catch (Exception e) {
			genMeth.takeScreenShot(driver, genMeth, xpthName);
			org.testng.Assert.fail(xpthName + " didn't display");

		}

	}

	
	public void clearId(AndroidMethods genMeth, String id)
			throws InterruptedException {

		try {

			MobileElement myElement = genMeth.fluentwait(driver, By.id(id));
			myElement.clear();

		}

		catch (Exception e) {

			org.testng.Assert.fail(id + "didn't displayed");

		}

	}

//===============================================================
	
	
	
	/*
	 * ******************************************************************************
	 * Avoid the Element not found exception *
	 * **********************************
	 * *******************************************
	 */

	// Look for an element in a few tries (with counter)
	public void waitForElementToBeInvisible(AndroidDriver<MobileElement> driver,
			By byType, int numAttempts) throws IOException,
			ParserConfigurationException, SAXException {

		int count = 0;
		Boolean isInvisible = false;
		while (count < numAttempts) {

			try {
				isInvisible = new FluentWait<AndroidDriver<MobileElement>>(driver)
						.withTimeout(60, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class)
						.until(ExpectedConditions
								.invisibilityOfElementLocated(byType));

				if (isInvisible == true) {

					count = numAttempts;

				}

			}

			catch (Exception e) {
				count++;

			}

		}

		if (isInvisible == false) {
			AndroidMethods genMeth = new AndroidMethods();
			// str = new genData();
			String imageName = "Element isn't Invisible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " is not Invisible");
		}

	}

	public void waitForElementToBeVisible(AndroidDriver<MobileElement> driver,
			By By, int numAttempts) throws IOException,
			ParserConfigurationException, SAXException {

		AndroidMethods genMeth = new AndroidMethods();
		int count = 0;
		WebElement elementToBeVisible = null;
		while (count < numAttempts) {
			try {
				elementToBeVisible = new FluentWait<AndroidDriver<MobileElement>>(
						driver).withTimeout(60, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class)
						.until(ExpectedConditions.elementToBeClickable(By));

				if (elementToBeVisible != null) {

					count = numAttempts;

				}

			}

			catch (Exception e) {
				count++;
				// genMeth.takeScreenShot (driver, genMeth,
				// "Elelement not visible");
			}

		}

		if (elementToBeVisible == null) {
			String imageName = "Element isn't Visible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " is not Visible");
		}

	}

	@SuppressWarnings("rawtypes")
	public MobileElement fluentwait(AndroidDriver driver, final By byType) {
		Wait<AndroidDriver> wait = new FluentWait<AndroidDriver>(driver)

		.withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		MobileElement foo = (MobileElement) wait
				.until(new Function<AndroidDriver, MobileElement>() {
					public MobileElement apply(AndroidDriver driver) {
						return (MobileElement) driver.findElement(byType);
					}
				});

		wait.until(ExpectedConditions.elementToBeClickable(byType));

		return foo;
	}

	public void isElementInvisible(By By) throws ParserConfigurationException,
			SAXException, IOException {

		try {

			(new WebDriverWait(driver, 45)).until(ExpectedConditions
					.invisibilityOfElementLocated(By));

		}

		catch (Exception e) {

			AndroidMethods genMeth = new AndroidMethods();
			String imageName = " Element is visible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " still visible");

		}

	}

	public void isElementVisible(By By) throws ParserConfigurationException,
			SAXException, IOException {

		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			new FluentWait<AndroidDriver<MobileElement>>(driver)
					.withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By));

		}

		catch (Exception e) {
			AndroidMethods genMeth = new AndroidMethods();
			String imageName = "Element is invisible";
			genMeth.takeScreenShot(driver, genMeth, imageName);
			org.testng.Assert.fail("WebElement" + " is not visible");

		}

	}

	public boolean checkIsElementVisible(By By)
			throws ParserConfigurationException, SAXException, IOException {

		boolean isWebElementVisible = false;
		WebElement element = null;
		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			element = new FluentWait<AndroidDriver<MobileElement>>(driver)
					.withTimeout(5, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By));

		}

		catch (Exception e) {

			// GenericMethods genMeth = new GenericMethods();
			// genData str = new genData();
			// String imageName = str.screenShotPath + " Element is invisible "
			// + genMeth.currentTime() + ".png";
			// genMeth.takeScreenShotNative(driver, imageName );
			// org.testng.Assert.fail("WebElement" + " is not visible");

		}
		if (element != null) {
			return isWebElementVisible = true;
		}

		else {
			return isWebElementVisible;

		}

	}

	public boolean fastCheckIsElementVisible(By By)
			throws ParserConfigurationException, SAXException, IOException {

		boolean isWebElementVisible = false;
		WebElement element = null;
		try {

			// (new WebDriverWait(driver,
			// 20)).until(ExpectedConditions.visibilityOfElementLocated(by));
			element = new FluentWait<AndroidDriver<MobileElement>>(driver)
					.withTimeout(5, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By));

		}

		catch (Exception e) {

		}
		if (element != null) {
			return isWebElementVisible = true;
		}

		else {
			return isWebElementVisible;

		}

	}

	public void isElementInvisibleText(By By, String Text)
			throws ParserConfigurationException, SAXException, IOException {

		try {

			(new WebDriverWait(driver, 45)).until(ExpectedConditions
					.invisibilityOfElementWithText(By, Text));

		}

		catch (Exception e) {

			AndroidMethods genMeth = new AndroidMethods();
			// String imageName = genMeth.getValueFromPropFile(key) + text +
			// " still visible "
			// + genMeth.currentTime() + ".png";
			genMeth.takeScreenShot(driver, genMeth, Text);
			org.testng.Assert.fail(Text + " still visible");

		}

	}

	public final class SessionIdentifierGenerator {
		private SecureRandom random = new SecureRandom();

		public String nextSessionId() {

			return new BigInteger(130, random).toString(32);

		}

	};

	public int getRandomInt() {
		Random rand = new Random();
		int n = rand.nextInt(50) + 1;
		return n;
	}

	// Creates a Random string
	public String randomString() {

		String text;
		SessionIdentifierGenerator x = new SessionIdentifierGenerator();
		text = x.nextSessionId();
		return text;
	}

	// Create a string with current date
	public String currentDate() {

		String curDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		return curDate;
	}

	public String currentTime() {

		// String curDate = new
		// SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		return curDate;
	}

	public void backgroundToForeground(AndroidDriver<MobileElement> driver,
			int numOfTimes) {

		for (int count = 0; count < numOfTimes; count++) {

			driver.runAppInBackground(2);

		}

	}


	public void longPressElement(AndroidDriver<MobileElement> driver,
			AndroidMethods genMeth, By By) {
		TouchAction action;
		WebElement el;
		try {
			action = new TouchAction(driver);
			el = genMeth.returnBy(driver, genMeth, By);
			action.longPress(el).waitAction(2000).release().perform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setLandscapeMode() {

		driver.rotate(ScreenOrientation.LANDSCAPE);
	}

	public void setPortraitMode() {

		driver.rotate(ScreenOrientation.PORTRAIT);
	}

	public void setEnglishKeyboard(AndroidMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		boolean isENG = genMeth.checkIsElementVisible(By.id("@"));
		if (isENG != true) {
			// change to English
			genMeth.clickId(genMeth, "English (US)");
		}

	}
	
	public AndroidElements setElements(String webElementXmlPath, String webElementXmlLang) throws ParserConfigurationException, jdk.internal.org.xml.sax.SAXException, IOException, InterruptedException, SAXException{
		

	DroidData= new AndroidElements(webElementXmlLang, webElementXmlPath);
	
	
	return DroidData;
	}

/*	public void locationServicesHadle(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		boolean isLocationDisplay = genMeth.checkIsElementVisible(By
				.name("Allow"));
		if (isLocationDisplay) {

			genMeth.clickName(genMeth, "Allow");
		}

	}

	 public void accessToContactsHandle(IosMethods genMeth)
	 throws ParserConfigurationException, SAXException, IOException,
	 InterruptedException {
	 boolean isLocationDisplay =
	 genMeth.checkIsElementVisible(By.name(DroidData.CameraPemissions_ID));
	 boolean isLocationDisplay = genMeth
	 .checkIsElementVisible(By.name("OK"));
	
	 if (isLocationDisplay) {
	
	 genMeth.clickName(genMeth, "OK");
	 }
	
	 }
*/
	/*public void accessToCameraHandle(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		// boolean accessToCamera =
		// genMeth.checkIsElementVisible(By.name(DroidData.CameraPemissions_ID));
		boolean accessToCamera = genMeth.checkIsElementVisible(By
				.name("Don't Allow"));

		if (accessToCamera) {

			genMeth.clickName(genMeth, DroidData.BTNokName);
		}

	}

	public void sendNotificationHandle(IosMethods genMeth)
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {
		// boolean isLocationDisplay =
		// genMeth.checkIsElementVisible(By.name(DroidData.CameraPemissions_ID));
		boolean isLocationDisplay = genMeth
				.checkIsElementVisible(By.name("OK"));

		if (isLocationDisplay) {

			genMeth.clickName(genMeth, "OK");
		}

	}

*/	

	public void swipeRightLong(int miliseconds) throws InterruptedException {

		driver.swipe(1000, 1000, 100, 1000, miliseconds);
		Thread.sleep(2000);

	}
	
	public void swipeRightShort(int miliseconds) throws InterruptedException {

		driver.swipe(1000, 1000, 75, 800, miliseconds);
		Thread.sleep(2000);

	}
	
	public void swipeRightShortest(int miliseconds) throws InterruptedException {

		driver.swipe(1000, 300, 75, 900, miliseconds);
		Thread.sleep(2000);

	}


// Swipe & Scroll
	public void swipeDownLong(int miliseconds) throws InterruptedException {

		driver.swipe(500, 1600, 500, 500, miliseconds);
		Thread.sleep(2000);


	}
	
	public void swipeDownShort(int miliseconds) throws InterruptedException {

		driver.swipe(500, 1700, 500, 1200, miliseconds);
		Thread.sleep(2000);

	}
	
	public void swipeDownShorter(int miliseconds) throws InterruptedException {

		driver.swipe(500, 1700, 500, 1400, miliseconds);
		Thread.sleep(2000);

	}
	
	public void swipeDownShortest(int miliseconds) throws InterruptedException {

		driver.swipe(500, 1400, 500, 1100, miliseconds);
		Thread.sleep(2000);

	}

	public void swipeUpLong(int miliseconds) throws InterruptedException {

		driver.swipe(600, 400, 600, 1600, miliseconds);
		Thread.sleep(2000);

	}
	
	public void swipeUpShort(int miliseconds) throws InterruptedException {

		driver.swipe(600, 400, 600, 1000, miliseconds);
		Thread.sleep(2000);

	}
	
	public void openStratupScreen(AndroidMethods genMeth, AndroidElements DroidData, EnvironmentMode EnvMode) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		
		
	//	boolean isStartupScreenDisplay = genMeth.checkIsElementVisible(By.name(DroidData.Appium_Startup));

		boolean isStartupScreenDisplay = genMeth.checkIsElementVisible(By.name("Applications"));
		
		if (isStartupScreenDisplay != true ) {

			genMeth.signOutFromStartup(genMeth);
			genMeth.cleanLoginDroid(genMeth, EnvMode);
		}

	}
	
	//Rotate
	
	public void rotateLandscape(){

		driver.rotate(ScreenOrientation.LANDSCAPE);
	}
	
	public void rotatePortrait(){

		driver.rotate(ScreenOrientation.PORTRAIT);

	}
	
	
	public void backDroidButton() {
		 
	 driver.pressKeyCode(AndroidKeyCode.BACK);
	  
	  }
	 

	public void deleteKey(int nunOfDeleteTap) {

		while (nunOfDeleteTap > 0) {

			driver.pressKeyCode(AndroidKeyCode.DEL);
			nunOfDeleteTap--;
		}

	}
	 
	public AppiumDriverLocalService startAppiumService() {

		 AppiumDriverLocalService service =
		 AppiumDriverLocalService.buildDefaultService();
		 /*
		AppiumDriverLocalService service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.usingDriverExecutable(new File("/usr/local/bin/node"))
						.withAppiumJS(
								new File(
										"/usr/local/lib/node_modules/appium/build/lib/appium.js"))
						.withIPAddress("0.0.0.0").usingPort(4723));
						*/
		service.start();
		return service;

	}
	
	 

 	  private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
         BufferedImage dest = src.getSubimage(0, 75, rect.width, rect.height-100);
         return dest; 
      }
	
	// public void changeConnectionType(String mode) {
	//
	// NetworkConnection mobileDriver = (NetworkConnection) driver;
	// if (mode == "AIRPLANE_MODE") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.AIRPLANE_MODE);
	// }
	//
	// else if (mode == "WIFI") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.WIFI);
	//
	// }
	//
	// else if (mode == "DATA") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.DATA);
	//
	// }
	//
	// else if (mode == "ALL") {
	//
	// mobileDriver.setNetworkConnection(NetworkConnection.ConnectionType.ALL);
	//
	// }
	//
	// }
	//
	/*
	 * public void setAirplainMode() {
	 * 
	 * driver.setNetworkConnection(new NetworkConnectionSetting(true, false,
	 * false));
	 * 
	 * }
	 * 
	 * public void setWifiOn() {
	 * 
	 * driver.setNetworkConnection(new NetworkConnectionSetting(false, true,
	 * false));
	 * 
	 * }
	 * 
	 * public void pressHomeButton() { int Home = AndroidKeyCode.HOME;
	 * driver.sendKeyEvent(Home);
	 * 
	 * }
	 * 
	
	 * 
	 * public void pressEnter() { int Enter = AndroidKeyCode.ENTER;
	 * driver.sendKeyEvent(Enter);
	 * 
	 * }
	 * 
	 * 
	 * 
	public void scroll(AndroidDriver<MobileElement> driver, String direction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, String> scrollMap = new HashMap<String, String>();
		scrollMap.put("direction", direction);
		js.executeScript("mobile: scroll", scrollMap);
	}

	public void scrollUp(AndroidDriver<MobileElement> driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, String> scrollMap = new HashMap<String, String>();
		scrollMap.put("direction", "up");
		js.executeScript("mobile: scroll", scrollMap);
	}

	public void scrollDown(AndroidDriver<MobileElement> driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, String> scrollMap = new HashMap<String, String>();
		scrollMap.put("direction", "down");
		js.executeScript("mobile: scroll", scrollMap);
	}

	

	public void killAppAndroid(AndroidDriver<MobileElement> driver)
			throws InterruptedException, IOException {

		// driver.removeApp("com.pogoplug.android");
		driver.resetApp();
		// driver.removeApp(bundleId);

		try {
			driver.quit();
		} catch (Exception x) {
			// swallow exception
		}
		// driver = genMeth.setCapabilitiesIos();
	}

	 */
}
