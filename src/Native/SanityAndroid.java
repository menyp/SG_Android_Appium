package Native;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.xml.sax.SAXException;

import sun.print.resources.serviceui;

import com.applitools.eyes.Eyes;



//MobileElement e2; //test will wait for this diring 20 seconds

  public class SanityAndroid {
	  
	@WithTimeout(time = 30, unit = TimeUnit.SECONDS)
	@iOSFindBy (id = "relevant id need to be added here")

	
	String currentDateFolder;
	String webElementXmlLang;
	String webElementXmlPath;
	String StartServerPath;
	String StopServerPath;
	String appIdentifier;
	Boolean skipfailure = true;
	boolean qaENV = true;
	Boolean useEye = true;


	AndroidDriver<MobileElement> driver;
	AndroidMethods genMeth = new AndroidMethods();
	Eyes eyes = new Eyes();
	AndroidElements DroidData;
	//AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
	AppiumDriverLocalService service;
	
	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) throws ParserConfigurationException, SAXException, IOException, InterruptedException, jdk.internal.org.xml.sax.SAXException {
		
        // This is your api key, make sure you use it in all your tests.
		
	
		//Set the tests configuration
		StartServerPath = genMeth.getValueFromPropFile("StartServerPath");
		StopServerPath = genMeth.getValueFromPropFile("StopServerPath");
		webElementXmlPath = genMeth.getValueFromPropFile("webElementXmlPath");
		webElementXmlLang = genMeth.getValueFromPropFile("webElementXmlLang");
		appIdentifier = genMeth.getValueFromPropFile("appIdentifier");
		
		//DroidData= new IosElements(webElementXmlLang, webElementXmlPath);
		DroidData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
		service = genMeth.startAppiumService();
		
		driver = genMeth.setCapabilitiesAndroid(genMeth, service);
		
		genMeth.cleanLoginDroid(genMeth, DroidData.userQA, DroidData.passwordQA, qaENV); 
	}

	@BeforeMethod (alwaysRun = true)
	public void checkHomeScreen() throws InterruptedException, IOException, ParserConfigurationException, SAXException, jdk.internal.org.xml.sax.SAXException{


		// Check if the client still logged in & in StartUp screen before each test
		if (driver == null) {
			try {
//				driver.removeApp(genMeth.getValueFromPropFile("appPackage"));
				driver.quit();
			} catch (Exception e) {
				// swallow if fails
			}
			service.stop();
			driver = genMeth.setCapabilitiesAndroid(genMeth, service);
			DroidData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
			genMeth.cleanLoginDroid( genMeth, DroidData.userQA , DroidData.passwordQA, qaENV);
		}

		else {
			
			String Startup_Screen = "//android.widget.TextView[@text='All Tabs']";
			//String Startup_Screen = "//android.widget.LinearLayout[@text='SQL Golden App']";
			
			genMeth.swipeUpLong(1000);
			genMeth.swipeUpLong(1000);
			genMeth.swipeUpLong(1000);
			genMeth.swipeUpLong(1000);


			boolean StartUpScreenDisplay = genMeth.checkIsElementVisible( By.xpath(Startup_Screen));

			if (StartUpScreenDisplay != true) {

				try {
					driver.resetApp();
					driver.removeApp(appIdentifier);
					driver.quit();
				} catch (Exception e) {
					// swallow if fails
				}
				
				driver = genMeth.setCapabilitiesAndroid(genMeth, service);
				DroidData = genMeth.setElements(webElementXmlPath, webElementXmlLang);
				genMeth.cleanLoginDroid( genMeth, DroidData.userQA, DroidData.passwordQA, qaENV);


			}

		}
	}
	
	
	@Test(enabled = true, testName = "URL Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity Android" })

	public void Tabs_URL() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to URL Constant

		genMeth.clickXpthName_TextView(genMeth, "URL / News");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Tabs(Droid)- URL Data Item", useEye, genMeth, skipfailure);

		//go to URL data Item
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "URL Constant");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Tabs (Droid) - URL Constant", useEye, genMeth, skipfailure);
		
		//Go Back to Startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);

	}
		
	
	@Test(enabled = true, testName = "News Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity Android" })

	public void Tabs_News() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to News 

		genMeth.clickXpthName_TextView(genMeth, "URL / News");

		//go to URL data Item
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		Thread.sleep(2000);
		genMeth.clickXpthName_CheckedTextView(genMeth, "News");
		genMeth.eyesCheckWindow("Tabs (Droid) - News", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "www.milliondollarhomepage.com");

		Thread.sleep(10000);

		genMeth.eyesCheckWindow("All Tabs (Droid)- The milliion $ home page", useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, DroidData.IconHome);
		
//		genMeth.rotateLandscape();   --> The rotate cause the oriantation to stuck & all tests are skipped
		//genMeth.eyesCheckWindow("All Tabs- News Landscape", useEye, genMeth, skipfailure);
		//genMeth.rotatePortrait();

		//Go Back to Startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
	}

	
	
	@Test(enabled = true, testName = "Dashboard  Tab", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity Android" })

	public void Tabs_Dashboard() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		
		//Open Dashboard  Tab
		Thread.sleep(8000);
		genMeth.clickXpthName_TextView(genMeth, "DashB/Cards/Employee");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Dashboard Default Layout", useEye, genMeth, skipfailure);

		
		//Navigate to Employee directory tab
		genMeth.clickXpthName_TextView(genMeth, "Service Call ID1");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Dashboard Default Layout- Navigate to Employee Directory", useEye, genMeth, skipfailure);

		//Navigate back to Dashboard
		genMeth.backDroidButton();
		genMeth.clickXpthName_TextView(genMeth, "DashB/Cards/Employee");

		
		genMeth.clickId(genMeth, DroidData.BTNSlicer);
		genMeth.clickXpthName_TextView(genMeth, "Service Call ID1");
		genMeth.clickXpthName_TextView(genMeth, "1");
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/filter_detail_navigation_left_btn");		
		genMeth.clickXpthName_TextView(genMeth, "Done");
		
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);

		genMeth.eyesCheckWindow("All Tabs (Droid)- Dashboard Advanced columns (Scroll down)", useEye, genMeth, skipfailure);

		//Gauge
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Dash with Gauge");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Dashboard- Gauge Half", useEye, genMeth, skipfailure);
		
		//Navigate
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/dashboard_item_bottom_label_container_view");
		
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Dashboard- Navigate to Map By GPS", useEye, genMeth, skipfailure);
		genMeth.backDroidButton();
		
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);

		genMeth.eyesCheckWindow("All Tabs (Droid)- Dashboard- Gauge Full/Solid", useEye, genMeth, skipfailure);

		//Back to Startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
				
	}
	
	
	@Test(enabled = true, testName = "Map,Dashboard, Charts Tabs", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity Android" })

	public void Tabs_Map() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		//Open Map By Address Tab
		genMeth.clickXpthName_TextView(genMeth, "Map");
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Map By GPS", useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Map By Address");

		                    
		Thread.sleep(3000);
		//genMeth.eyesCheckWindow(eyes, "All Tabs- Map By Address", useEye, skipfailure);
//		genMeth.clickId(genMeth,"19501 Biscayne Blvd, Aventura, FL 33180. 19501 Biscayne Boulevard,Aventura, FL 33180.");
		//genMeth.clickXpthName_TextView(genMeth, "19501 Biscayne Blvd, Aventura, FL 33180. 19501 Biscayne Boulevard,Aventura, FL 33180.");
		

		By by = By.xpath("//android.view.View[@content-desc='19501 Biscayne Blvd, Aventura, FL 33180. 19501 Biscayne Boulevard,Aventura, FL 33180.']");
		driver.findElement(by).click();
		
		
		genMeth.eyesCheckWindow("All Tabs (Droid)- Map By Address- Aventura", useEye, genMeth, skipfailure);
		
		//Driving Directions
		genMeth.clickId(genMeth, DroidData.BTNdirection);
		
	//	genMeth.eyesCheckWindow("All Tabs (Droid)- Map By Address- Driving directions", useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		//Phone
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/map_add_info_adress_container");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/map_add_info_item_phone");
//		genMeth.eyesCheckWindow("All Tabs (Droid)- Map By Address- Phone", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);

		//Navigation to URL tab
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/map_add_info_item_jump_to");
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("Tabs (Droid)- URL Data Item", useEye, genMeth, skipfailure);

		
		//Navigation Back
		genMeth.backDroidButton();
		
		
		//Open Map By GPS
		//Thread.sleep(15000);
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Map By GPS");

		by = By.xpath("//android.view.View[@content-desc='40.918116,-74.076363. 1 Garden State Plaza Boulevard,Paramus, NJ 07652.']");
		genMeth.clickBy(driver, genMeth, by);
		
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Map By GPS- Press pin map", useEye, genMeth, skipfailure);

		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/map_add_info_adress_container");
		//All addresses 
		genMeth.eyesCheckWindow("All Tabs (Droid)- Map By GPS- All Addresses", useEye, genMeth, skipfailure);
		
		//Back to Startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);

	}
	
	@Test(enabled = true, testName = "Map Charts Tabs", retryAnalyzer = Retry.class, description = "Check the URL tab",
			groups = { "Sanity Android" })

	public void Tabs_Chart() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		//Open Bar Chart
		genMeth.clickXpthName_TextView(genMeth, "Chart/CoverF/ActionC");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart", useEye, genMeth, skipfailure);
		
		//Filter data
		genMeth.clickXpthName_TextView(genMeth, "Sales");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart- Returns & Net Sales", useEye, genMeth, skipfailure);
		
		genMeth.clickXpthName_TextView(genMeth, "Returns");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart- Net Sales", useEye, genMeth, skipfailure);
		
		genMeth.clickXpthName_TextView(genMeth, "Sales");
		genMeth.clickXpthName_TextView(genMeth, "Returns");
		//genMeth.clickId(genMeth, "Net Sales");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart", useEye, genMeth, skipfailure);


		//Navigation to pie chart
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/column_chart_selected_title_nav_icon");
		Thread.sleep(15000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart- Navigate to Dashboard", useEye, genMeth, skipfailure);
		
		//Navigate back to the Bar chart
		genMeth.backDroidButton();
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart", useEye, genMeth, skipfailure);
		
		//Pie Chart
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Pie Chart");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Pie Chart", useEye, genMeth, skipfailure);
		
		//Filter data Returen	
		genMeth.clickXpthName_TextView(genMeth, "Returns");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Pie Chart- Returns", useEye, genMeth, skipfailure);
		
		//Filter data Net Sales	
		genMeth.clickXpthName_TextView(genMeth, "Net Sales");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Pie Chart- Net Sales", useEye, genMeth, skipfailure);
		
		//Navigation to Bar chart
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/pie_chart_slicer_name");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Bar Chart", useEye, genMeth, skipfailure);
		
		//Go Back to Startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		
	}
	
	
	@Test(enabled = true, testName = "Cover Flow", retryAnalyzer = Retry.class, description = "Check the Cover Flow tab",
			groups = { "Sanity Android" })

	public void Tabs_CoverFlow() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to CoverFlow
		genMeth.clickXpthName_TextView(genMeth, "Chart/CoverF/ActionC");
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Cover Flow");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow", useEye, genMeth, skipfailure);
		genMeth.swipeRightShort(1000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- swipe John Grant", useEye, genMeth, skipfailure);
				
		//Address
		genMeth.clickXpthName_TextView(genMeth, "Address");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Address", useEye, genMeth, skipfailure);
		//genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);

		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Scroll Down", useEye, genMeth, skipfailure);

		//Address mini map
		genMeth.clickXpthName_TextView(genMeth, "Hermon 13 Kfar Vradim");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Address Mini Map", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);

		//Phone
		genMeth.clickXpthName_TextView(genMeth, "Phone");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Phone", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Phone");
		
		//Email
		genMeth.clickXpthName_TextView(genMeth, "Email");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Email", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		//URL
		genMeth.clickXpthName_TextView(genMeth, "URL");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNsubmit);
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Go to URL", useEye, genMeth, skipfailure);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		
		// Landline
		genMeth.clickXpthName_TextView(genMeth, "Landline");
		genMeth.eyesCheckWindow("All Tabs (Droid)- Cover Flow- Landline", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Landline");
		
		//Go to Startup screen
		genMeth.backDroidButton();
		
	}

	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity Android" })

	public void Tabs_List_AdvancedColumns() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to List
		genMeth.clickXpthName_TextView(genMeth, "List / Grid");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List", useEye, genMeth, skipfailure);

		//Phone
		genMeth.clickXpthName_TextView(genMeth, "Call");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List Phone", useEye, genMeth, skipfailure);
		//genMeth.eyesCheckWindow(eyes, "All Tabs- List Phone", useEye, skipfailure);
		
		//Email
		genMeth.clickXpthName_TextView(genMeth, "Email");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List Email", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		Thread.sleep(2000);
		
		//URL
		genMeth.clickXpthName_TextView(genMeth, "URL");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
	//	Thread.sleep(3000);
		
		// Landline
		genMeth.clickXpthName_TextView(genMeth, "Landline");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List Landline", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Landline");

		
		//Address
		genMeth.clickXpthName_TextView(genMeth, "Address");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List Address", useEye, genMeth, skipfailure);
		//genMeth.eyesCheckWindow(eyes, "All Tabs- List Address", useEye, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Address");

		
		//Mini Map
		genMeth.swipeDownShort(1000);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/template_view_item_map_layout");
		genMeth.clickId(genMeth, DroidData.BTNsubmit);
		Thread.sleep(3000);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		genMeth.swipeDownShort(1000);
		genMeth.swipeDownShort(1000);

		genMeth.clickId(genMeth, DroidData.BTNseeAll_ID);
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List See All", useEye, genMeth, skipfailure);		
		
		//Folder
		genMeth.clickXpthName_TextView(genMeth, "Folder");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List Folder", useEye, genMeth, skipfailure);

		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);

		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Tabs_List_AdvancedColumns (Droid)- List See All scroll down", useEye, genMeth, skipfailure);
		//genMeth.eyesCheckWindow(eyes, "All Tabs- List See All scroll down", useEye, skipfailure);
		
		
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, DroidData.IconHome);
		Thread.sleep(2000);

		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);

	}
	
	
	@Test(enabled = true, testName = "Grid two layer Advanced", retryAnalyzer = Retry.class, description = "Check the Grid two layer tab",
			groups = { "Sanity Android" })

	public void Tabs_Grid_Two_Layers() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to Grid
		genMeth.clickXpthName_TextView(genMeth, "List / Grid");
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - Two Layers");		
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- List Address", useEye, genMeth, skipfailure);

		//Open the second layer
		genMeth.clickXpthName_TextView(genMeth, "$200");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- Second layer", useEye, genMeth, skipfailure);
		genMeth.swipeDownShorter(1000);
	
		/*
		try {
			genMeth.setLandscapeMode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- Second layer - Landscape", useEye, genMeth, skipfailure);
		//genMeth.setPortraitMode();
		
	
		//Phone
		genMeth.clickXpthName_TextView(genMeth, "Phone");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- Phone options open", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Phone");

		
		// Landline
		genMeth.clickXpthName_TextView(genMeth, "Landline");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- Landline", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Landline");
	
		//URL
		genMeth.clickXpthName_TextView(genMeth, "URL");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- List URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);

		//Email
		genMeth.clickXpthName_TextView(genMeth, "Email");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- List Email", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		Thread.sleep(2000);
		
		//Address
		genMeth.clickXpthName_TextView(genMeth, "Address");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- Address", useEye, genMeth, skipfailure);
		//genMeth.eyesCheckWindow(eyes, "All Tabs- List Address", useEye, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Address");

		
		//Mini Map
		genMeth.swipeDownShort(1000);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/template_view_item_map_layout");
		genMeth.eyesCheckWindow("All Tabs- Grid two layers (Droid)- Mini Map", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNsubmit);
		Thread.sleep(3000);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
				
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, DroidData.IconHome);
		
		//Verify Startup screen is open		
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
	
	}
	
	@Test(enabled = true, testName = "Grid one layer", retryAnalyzer = Retry.class, description = "Check the Grid one layer tab Advanced & navigation",
			groups = { "Sanity Android" })

	public void Tabs_Grid_One_Layer_Advance_Navigation() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		
		//Need to find a solution to the find element by xpath for the Address/Mobile Phone etc sonce it keeps failing (seems like Appium bug)
		
		// go to Grid
		genMeth.clickXpthName_TextView(genMeth, "List / Grid");
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - One Layer");		
		Thread.sleep(3000);
	
		
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) -  (Advanced - Part 1)", useEye, genMeth, skipfailure);
		
		// Address
		genMeth.clickXpthName_LinearLayout(genMeth, "3");
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Address", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		// Mobile Phone
		genMeth.clickXpthName_LinearLayout(genMeth, "4");
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Phone", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		genMeth.backDroidButton();
		
		
		genMeth.clickXpthName_TextView(genMeth, "List / Grid");
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - One Layer");		
		Thread.sleep(3000);
		
		genMeth.swipeRightLong(2000);
		genMeth.swipeRightLong(2000);
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Swipe to the right", useEye, genMeth, skipfailure);

		// MiniMap - Navigation to slicer report
		genMeth.clickXpth(genMeth, "//android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.view.ViewPager[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[2]/android.widget.ImageView[1]");
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Mini Map Navigation", useEye, genMeth, skipfailure);
		genMeth.backDroidButton();
		
		

		// Email
		genMeth.clickXpth(genMeth, "//android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.view.ViewPager[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[3]");
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Email", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		genMeth.backDroidButton();
		
		genMeth.clickXpthName_TextView(genMeth, "List / Grid");
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - One Layer");		
		Thread.sleep(3000);

		// URL
		//genMeth.clickXpth(genMeth, "//android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.support.v4.view.ViewPager[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[4]");
		genMeth.swipeRightLong(2000);
		genMeth.swipeRightLong(2000);
		
		genMeth.clickXpth(genMeth, "//android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.view.ViewPager[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[4]/android.widget.ImageView[1]");
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - URL", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNsubmit);
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Go To URL", useEye, genMeth, skipfailure);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		// Landline
		genMeth.clickXpthName_LinearLayout(genMeth, "4");
	//	genMeth.clickXpth(genMeth, "//android.view.View[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.view.ViewPager[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[5]/android.widget.ImageView[1]");
		genMeth.eyesCheckWindow("All Tabs- Grid one layer (Droid) - Landline", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		genMeth.clickId(genMeth, DroidData.IconHome);
				
		//Verify Startup screen is open
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
	
	
	}
	
	
	@Test(enabled = true, testName = "Employee Directory", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity Android" })

	public void Tabs_Employee_Directory() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to Employee Directory tab
		genMeth.clickXpthName_TextView(genMeth, "DashB/Cards/Employee");
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Employee Directory");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Main",useEye, genMeth, skipfailure);
								
		//Search an employee (Empty search)
		genMeth.clickId(genMeth, DroidData.IconSearch);
		genMeth.sendId(genMeth, DroidData.IconSearch , "no emplyees found");
		
		
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - empty search",useEye, genMeth, skipfailure);		
		genMeth.deleteKey(17);

		genMeth.clearId(genMeth, DroidData.IconSearch);
		
		//Search an employee
		genMeth.clickId(genMeth, DroidData.IconSearch);
		genMeth.sendId(genMeth, DroidData.IconSearch , "Lane");
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - search Specific employee",useEye, genMeth, skipfailure);		
		genMeth.backDroidButton();	
		
		//second layer
		genMeth.clickXpthName_TextView(genMeth, "Lane R. Barlow");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Second layer",useEye, genMeth, skipfailure);				

		// Phone
		Thread.sleep(1000);
		genMeth.clickXpthName_TextView(genMeth, "Phone");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Phone",useEye, genMeth, skipfailure);				
	
		// Email
		genMeth.clickXpthName_TextView(genMeth, "Email");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Email",useEye, genMeth, skipfailure);				
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		//Map
		genMeth.swipeDownShorter(1000);
		genMeth.clickXpthName_TextView(genMeth, "Address First");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Address First",useEye, genMeth, skipfailure);				
		
		// Mini Map
		genMeth.swipeDownShorter(1000);
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/template_view_item_map_layout");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Address second",useEye, genMeth, skipfailure);				
		genMeth.clickId(genMeth, DroidData.BTNCancelName);

		// URL
		genMeth.swipeDownLong(1000);

		genMeth.clickXpthName_TextView(genMeth, "http://google.com");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - URL",useEye, genMeth, skipfailure);	
		genMeth.clickId(genMeth, DroidData.BTNsubmit);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.BTNCancelName);
		
		//Social Networks - Facebook
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/employee_directory_detail_person_social_net_facebook");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Facebook",useEye, genMeth, skipfailure);				
		genMeth.backDroidButton();

		//Twitter
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/employee_directory_detail_person_social_net_twitter");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Twitter",useEye, genMeth, skipfailure);				
		genMeth.backDroidButton();

		//LinkedIn
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/employee_directory_detail_person_social_net_linkidin");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - LinkedIn",useEye, genMeth, skipfailure);				
		genMeth.backDroidButton();

		//Google+
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/employee_directory_detail_person_social_net_google_plus");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Google+",useEye, genMeth, skipfailure);				
		genMeth.backDroidButton();
		
		//Navigation
		genMeth.clickXpthName_TextView(genMeth, "First_Name");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Navigation to Param report ed",useEye, genMeth, skipfailure);				
		genMeth.backDroidButton();
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Back from navigation",useEye, genMeth, skipfailure);				
		
		//No Social Networks available
		genMeth.clickId(genMeth, "android:id/search_close_btn");
		genMeth.sendId(genMeth, DroidData.IconSearch , "Callum R. Aguirre");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - No Social Networks",useEye, genMeth, skipfailure);				
		genMeth.backDroidButton();
		
		//No Google+ 
		genMeth.clickId(genMeth, "android:id/search_close_btn");
		genMeth.sendId(genMeth, DroidData.IconSearch , "Caldwell Alexander");
		driver.hideKeyboard();
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - No Google+",useEye, genMeth, skipfailure);				
		
		//Back to Startup screen
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.IconHome);

		
		//Press info for the app
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/left_menu_child_info_icon");
			
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("All Tabs- Employee Directory (Droid) - Golden App info screen",useEye, genMeth, skipfailure);	
		genMeth.clickXpthName_TextView(genMeth, "SQL Golden App");
		genMeth.clickXpthName_TextView(genMeth, "SQL Golden App");
				
		//Verify Startup screen is open
		Thread.sleep(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);

	}

	
	@Test(enabled = true, testName = "Parameterized report Grid", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity Android" })

	public void Param_Report_Grid() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to parameterized report - Grid tab
		genMeth.swipeDownShort(1000);
		
		// go to Employee Directory tab
		genMeth.clickXpthName_TextView(genMeth, "Param Report Grid");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("Param Report Grid (Droid)- add Parameters",useEye, genMeth, skipfailure);
		
				
		//Attempt to submit while mandatory is missing --> Doesn't work with the Nexus 5X (After dismissing a Popup test is failing)
	//	genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
	//	genMeth.eyesCheckWindow("Param Report Grid (Droid)- Mandatory field is missing",useEye, genMeth, skipfailure);
		

		//Insert parameters
		//genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/app_message_dialog_cancel_button");
		genMeth.clickXpthName_TextView(genMeth, "SL-Device Types");
		genMeth.eyesCheckWindow("Param Report Grid (Droid)- SL param",useEye, genMeth, skipfailure);
//		genMeth.clickXpthName_TextView(genMeth, "Laptop");
		genMeth.clickXpthName_TextView(genMeth, "Tablet");
		Thread.sleep(4000);


		genMeth.clickXpthName_TextView(genMeth, "PSL- Device Model");
		genMeth.eyesCheckWindow("Param Report Grid (Droid)- PSL param",useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "PSL- Device Model");
		genMeth.clickXpthName_TextView(genMeth, "iPad");		
		genMeth.eyesCheckWindow("Param Report Grid (Droid)- All params were filled",useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Report Grid (Droid)- Grid first layer",useEye, genMeth, skipfailure);		
		//Go To second layer
		genMeth.clickXpthName_TextView(genMeth, "Tablet");		
		genMeth.eyesCheckWindow("Param Report Grid (Droid)- Grid second layer",useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}

	@Test(enabled = true, testName = "Parameterized report With all variables", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity Android" })

	public void Param_Report_AllVariables() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		genMeth.swipeDownShort(1000);
		genMeth.clickXpthName_TextView(genMeth, "Param Variables only");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("Param Report with All Variables (Droid) - SQL Golden App",useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}

	
	@Test(enabled = true, testName = "Parameterized report List", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab",
			groups = { "Sanity Android" })

	public void Param_Report_List() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {


		// go to parameterized report - Grid tab
		genMeth.swipeDownShort(1000);

		genMeth.clickXpthName_TextView(genMeth, "Param Report List");
		genMeth.eyesCheckWindow("Param Report List (Droid)- add Parameters", useEye, genMeth, skipfailure);
		
		//Attempt to submit while mandatory is missing -->  (Android 7 bug- test fail after the popup is canceled)
		//genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");		
		//genMeth.eyesCheckWindow("Param Report List (Droid)- Mandatory field is missing", useEye, genMeth, skipfailure);
		//genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/app_message_dialog_cancel_button");

		//Insert parameters
		genMeth.clickXpthName_TextView(genMeth, "FreeText  (Priority)");
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");

		genMeth.clickXpthName_TextView(genMeth, "SL_ML (Priority)");
		genMeth.eyesCheckWindow("Param Report List (Droid)- SL ML  Priority", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "2");
		//genMeth.clickId(genMeth, "2");
		genMeth.eyesCheckWindow("Param Report List (Droid)- All params were filled", useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow("Param Report List (Droid)- FreeText Priority = 1", useEye, genMeth, skipfailure);

		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		
		genMeth.clickXpthName_CheckedTextView(genMeth, "SL_ML (Priority)");
		genMeth.eyesCheckWindow("Param Report List (Droid)- FreeText Priority = 2", useEye, genMeth, skipfailure);


		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "SC(Up_Date<=MobDate)");
		genMeth.eyesCheckWindow("Param Report List (Droid)- SC(Up_Date<=MobDate)", useEye, genMeth, skipfailure);
		
		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);

		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}

	@Test(enabled = true, groups = { "Sanity Android" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_DL_Dashboard()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report dashboard - DL- Device Info tab
		genMeth.swipeDownLong(1000);
		
		genMeth.clickXpthName_TextView(genMeth, "Param DL-Dashboard");
		Thread.sleep(4000);										 
		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- add Parameters",useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickXpthName_TextView(genMeth, "SL- Devices Type");
		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- SL param",useEye, genMeth, skipfailure);		
		genMeth.clickXpthName_TextView(genMeth, "Laptop");
		Thread.sleep(2000);
		
		genMeth.clickXpthName_TextView(genMeth, "DL- Device Model");
		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- DL param",useEye, genMeth, skipfailure);		
		genMeth.clickXpthName_TextView(genMeth, "Lenovo");
		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- All params were filled",useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- Dashboard tab",useEye, genMeth, skipfailure);		
		
		//Navigate to Dashboard tab
		genMeth.clickXpthName_TextView(genMeth, "Device Type Name (ParentName)");
		Thread.sleep(5000);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- Navigate to SL- Devices by Type tab",useEye, genMeth, skipfailure);		
		genMeth.backDroidButton();
		genMeth.backDroidButton();

		genMeth.eyesCheckWindow("Param Report Dashboard DL (Droid)- Dashboard tab",useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}
	
	
	@Test(enabled = true, groups = { "Sanity Android"}, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_CoverFlow()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report dashboard - DL- Device Info tab
		genMeth.swipeDownLong(1000);
		genMeth.clickXpthName_TextView(genMeth, "Param Rep Cover Flow");
		
		genMeth.eyesCheckWindow("Param Rep Cover Flow (Droid) - Parameters",useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickXpthName_TextView(genMeth, "Insert Gender (F or M)");
		genMeth.eyesCheckWindow("Param Rep Cover Flow (Droid) - QR",useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_M);
		driver.hideKeyboard();

		//genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/qr_scanner_view_finder", "m");
		//genMeth.clickId(genMeth, "m");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("Param Rep Cover Flow (Droid) - Males",useEye, genMeth, skipfailure);		
		
		//Go To cover flow tab by const (females)
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Const-Female Only");
		genMeth.eyesCheckWindow("Param Rep Cover Flow (Droid) - Female",useEye, genMeth, skipfailure);		
		
		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}
	
	@Test(enabled = true, groups = { "Sanity Android" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_Chart()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipeDownLong(1000);
		genMeth.clickXpthName_TextView(genMeth, "Param Report Chart");
		Thread.sleep(2000);
		
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - Parameters",useEye, genMeth, skipfailure);		

		//Insert parameters
		genMeth.clickXpthName_TextView(genMeth, "Choose Value");
		genMeth.clickXpthName_TextView(genMeth, "Mall of America");
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - SL",useEye, genMeth, skipfailure);		

		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - SL Mall of america Bar",useEye, genMeth, skipfailure);		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(3000);
		
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - SL Mall of america in bar chart",useEye, genMeth, skipfailure);		
		
		//Naviagte to param report
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/column_chart_selected_title_nav_icon");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - Param report map - parameters screen",useEye, genMeth, skipfailure);		
		genMeth.backDroidButton();
		//Go To Pie tab
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "SL-SalesbyBranch-Pie");
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - SL Mall of america Pie ",useEye, genMeth, skipfailure);		
		genMeth.clickXpthName_TextView(genMeth, "Returns");
		genMeth.eyesCheckWindow("Param Rep Chart (Droid) - SL Mall of america Pie (Returns) ",useEye, genMeth, skipfailure);		
		//genMeth.eyesCheckWindow(eyes, "Param Rep Chart - SL Mall of america Pie- Returnes", useEye, skipfailure);

		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}

	@Test(enabled = true, groups = { "Sanity Android" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_EmployeeDirectory()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);


		//genMeth.clickId(genMeth, "Param Report ED");
		genMeth.clickXpthName_TextView(genMeth, "Param Report ED");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Rep ED (Droid)- Parameters", useEye, genMeth, skipfailure);

		//Insert parameters
		genMeth.clickXpthName_TextView(genMeth, "Choose Value");
		genMeth.clickXpthName_TextView(genMeth, "Female");
		genMeth.eyesCheckWindow("Param Rep ED (Droid)- SL MB", useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Param Rep ED (Droid)- Female only", useEye, genMeth, skipfailure);
		
		//Go To Employee tab by Login variable
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "ED by Login");
		genMeth.eyesCheckWindow("Param Rep ED (Droid)- ED by Login", useEye, genMeth, skipfailure);

		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}

	@Test(enabled = true, groups = { "Sanity Android" }, testName = "Param_Report_DL_Dashboard", retryAnalyzer = Retry.class, description = "Check the Employee Directory tab")
	public void Param_Report_Map()

			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {


		// go to parameterized report-  Param report chart tab
		genMeth.swipeDownShort(1000);
		genMeth.swipeDownShort(1000);
		genMeth.clickXpthName_TextView(genMeth, "Param Report Map");
		Thread.sleep(5000);
		
		genMeth.eyesCheckWindow("Param Rep Map (Droid) - Parameters", useEye, genMeth, skipfailure);

		//Insert parameters
		genMeth.clickXpthName_TextView(genMeth, "Choose Value");
		genMeth.clickXpthName_TextView(genMeth, "King of Prussia Mall");

		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");
		genMeth.eyesCheckWindow("Param Rep Map (Droid) - King of Prussia Mall chosen", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(12000);
		genMeth.eyesCheckWindow("Param Rep Map (Droid) - King of Prussia Mall on map", useEye, genMeth, skipfailure);

		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);

		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}

	@Test(enabled = true, groups = { "Sanity Android" }, testName = "Param_Report_Cards", retryAnalyzer = Retry.class, description = "Check the Param_Report_Cards")
	public void Param_Report_Cards()
			throws ParserConfigurationException, SAXException, IOException,
			InterruptedException {

		// go to parameterized report-  Param report chart tab
		genMeth.swipeDownLong(1000);
		genMeth.clickXpthName_TextView(genMeth, "Param Report Cards");
		Thread.sleep(5000);
		
		genMeth.eyesCheckWindow("Param Rep Cards (Droid) - Parameters", useEye, genMeth, skipfailure);

		//Insert parameters
		genMeth.clearXpthName_TextView(genMeth, "Default");
		
		genMeth.sendXpthName_EditText(genMeth, "Input text here", "1");
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");

		genMeth.eyesCheckWindow("Param Rep Cards (Droid) - Priority = 1", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/parameterized_fragment_submit_button");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Param Rep Cards (Droid) - Priority = 1 service calls", useEye, genMeth, skipfailure);

		//Back to startup screen
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}


	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity Android" })

	public void Actions_List_Cell() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		
		
		// go to List
		genMeth.clickXpthName_TextView(genMeth, DroidData.ActionsReport);
		
		//Set slicer to one item
		genMeth.clickId(genMeth, DroidData.BTNSlicer);
		genMeth.clickXpthName_TextView(genMeth, "Service Call ID");
		genMeth.clickXpthName_TextView(genMeth, "1");
		genMeth.clickXpthName_TextView(genMeth, "Slicer");
		genMeth.clickXpthName_TextView(genMeth, "Done");

		genMeth.eyesCheckWindow("List Actions Cell (Droid) - List Actions", useEye, genMeth, skipfailure);
		
		//Execute action in the first layer
		//Free text description
		genMeth.clickXpthName_TextView(genMeth, "Description");
		boolean checkAction = genMeth.checkIsElementVisible(By.xpath("//android.widget.EditText[@text='" + "Descrip 1" + "']"));
		if (checkAction) {
			driver.pressKeyCode(AndroidKeyCode.DEL);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_2);

		} else {
			driver.pressKeyCode(AndroidKeyCode.DEL);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);

		}
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("List Actions Cell (Droid) - cell description", useEye, genMeth, skipfailure);

		//Priority (Simple List MB)
		genMeth.clickXpthName_TextView(genMeth, "Priority");
		genMeth.clickXpthName_TextView(genMeth, "91");
		checkAction = genMeth.checkIsElementVisible(By.id("Update Pirority (MB)"));

		if (checkAction) {
			genMeth.clickId(genMeth, "90");

		}
		
		genMeth.eyesCheckWindow("List Actions Cell (Droid) - cell Priority (Simple List MB)", useEye, genMeth, skipfailure);


		//BranchID - Parameterized SL mapped with source = Variable
		Thread.sleep(3000);
		genMeth.clickXpthName_TextView(genMeth, "Branch ID");
		genMeth.eyesCheckWindow("List Actions Cell (Droid) - Branch ID(PSL_VAR) = 1", useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "2");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("List Actions Cell (Droid) - Branch ID(PSL_VAR)- Success", useEye, genMeth, skipfailure);
		
		//Assign To (Dynamic List)
		genMeth.clickXpthName_TextView(genMeth, "Assigned To");
		genMeth.clickXpthName_TextView(genMeth, "John Grant");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("List Actions Cell (Droid) - cell Assign To (DL)", useEye, genMeth, skipfailure);
		
		//Action in second layer
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		
		//Action in second layer
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/one_lvls_item_seemore_btn");
		Thread.sleep(2000);
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);


		//QR code
		genMeth.clickXpth(genMeth, "//android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.support.v4.widget.DrawerLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[3]/android.widget.LinearLayout[1]/android.widget.ListView[1]/android.widget.RelativeLayout[8]/android.widget.TextView[1]");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
		Thread.sleep(2000);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		driver.hideKeyboard();
		
		//genMeth.sendXpthName_EditText(genMeth, "QR/Bar code", "1");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_dialog_submit");
		Thread.sleep(8000);
		genMeth.eyesCheckWindow("List Actions Cell (Droid) - cell QR second layer (QR)", useEye, genMeth, skipfailure);

		genMeth.backDroidButton();
		genMeth.clickId(genMeth, DroidData.IconHome);
		
		//Verify Startup screen is open
		genMeth.swipeUpShort(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);

	}
	
	@Test(enabled = true, testName = "List", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity Android" })
	public void Actions_List_Row_Popup() throws ParserConfigurationException, SAXException,
	IOException, InterruptedException {

		// go to List
		genMeth.clickXpthName_TextView(genMeth, DroidData.ActionsReport);
		Thread.sleep(12000);

		// Action in second layer
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		//Thread.sleep(2000);

		// Action in second layer
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/one_lvls_item_seemore_btn");
		
		// Row Action (Adding a row to the all parameters table)
		Thread.sleep(4000);
		genMeth.clickId(genMeth,DroidData.BTNrightMenuButton_ID);
		genMeth.clickXpthName_TextView(genMeth, "PopUp- AddRow");
		genMeth.clickXpthName_TextView(genMeth, "Free_Text");
		genMeth.sendXpthName_EditText(genMeth, "Input text here", "New Row");
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/action_submit_button");

		genMeth.clickXpthName_TextView(genMeth, "DeviceType_SL_ByName");
		genMeth.clickXpthName_TextView(genMeth, "Laptop");

		genMeth.clickXpthName_TextView(genMeth, "Device_Model_DL");
		genMeth.clickXpthName_TextView(genMeth, "Asus");

		genMeth.clickXpthName_TextView(genMeth, "Items_By_Category_PSL");
		genMeth.clickXpthName_TextView(genMeth, "Keyboard (Cat 1)");

		genMeth.clickXpthName_TextView(genMeth, "QR default");
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		driver.hideKeyboard();
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");

		genMeth.clickXpthName_TextView(genMeth, "SL_Manual_List");
		genMeth.clickXpthName_TextView(genMeth, "2");

		// PSL with Variable
		genMeth.clickXpthName_TextView(genMeth,"Items_SmallerThanMobileDate_PSL");
		genMeth.clickXpthName_TextView(genMeth, "3");

		// image
		genMeth.swipeDownLong(1000);
		genMeth.clickXpthName_TextView(genMeth, "Image");
		genMeth.eyesCheckWindow("List Actions Row (Droid) - No Image selected",useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/image_picker_fragment_open_camera_btn");
		Thread.sleep(2000);
		genMeth.backDroidButton();

		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/image_picker_fragment_open_gallery_btn");
		genMeth.eyesCheckWindow("List Actions Row (Droid) - Gallery opened",useEye, genMeth, skipfailure);
		genMeth.backDroidButton();
		Thread.sleep(2000);
		genMeth.backDroidButton();
		Thread.sleep(2000);

		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/action_fragment_submit_button");
		Thread.sleep(15000);

/*	

genMeth.clickId(genMeth, "com.sec.android.app.camera:id/okay");
genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/image_picker_fragment_save_btn");

genMeth.eyesCheckWindow("List Actions (Droid) - Image set", useEye, genMeth, skipfailure);
genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_fragment_submit_button");



//Signature
genMeth.swipedownIphone5Long(1000);
genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[8]");
TouchAction touchAction = new TouchAction(driver);
//  touchAction.press(250, 250).moveTo(250, 150).release().perform();

//MobileElement el1 = genMeth.returnName(driver, genMeth, "SkyGiraffe");
//MobileElement el2 = genMeth.returnId(driver, genMeth, "X");

//touchAction.longPress(el1, 2000).moveTo(el2).release().perform();



touchAction.longPress(200, 200, 3000).perform();

touchAction.longPress(200, 200, 3000).waitAction(1000).moveTo(200,201).release().perform();

touchAction.longPress(100, 100, 3000);
touchAction.moveTo(100, 50).waitAction(1000);
touchAction.release();
touchAction.perform();

genMeth.eyesCheckWindow(eyes, "List Actions- Cancel signature", useEye, skipfailure);

genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[8]");
touchAction.longPress(250, 250, 1000).moveTo(250, 150).release().perform();
genMeth.clickId(genMeth, DroidData.BTNdoneName);
genMeth.eyesCheckWindow(eyes, "List Actions- signature Set", useEye, skipfailure);
/
genMeth.clickId(genMeth, DroidData.BTNsubmit_ID);
*/

//Row Action with  input type = Inline (Adding a row to the all parameters table)

		// Verify Startup screen is open
		genMeth.eyesCheckWindow("List Actions Row (Droid) - PN Success", useEye,genMeth, skipfailure);
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/push_notification_dialog_cancel_btn");
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpShort(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App",useEye, genMeth, skipfailure);

	}



	
	@Test(enabled = true, testName = "Inline row action", retryAnalyzer = Retry.class, description = "Check the List tab",
			groups = { "Sanity Android" })

	public void Actions_List_Inline_Row() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		
		genMeth.clickXpthName_TextView(genMeth, DroidData.ActionsReport);
		Thread.sleep(5000);
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "List (Inline)");

		// go to List
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/one_lvl_outgird_action_btn");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow("Actions_List_Inline (Droid)- Inline parameters default", useEye, genMeth, skipfailure);
		
		genMeth.sendXpthName_EditText(genMeth, "This is default value", "1");
		
		genMeth.clickXpthName_TextView(genMeth, "Device_Type_SL_DI");
		genMeth.clickXpthName_TextView(genMeth, "Mobile");
		
		genMeth.clickXpthName_TextView(genMeth, "Device_Model_DL");
		genMeth.clickXpthName_TextView(genMeth, "iPhone6");

		genMeth.swipeDownShort(1000);

		genMeth.clickXpthName_TextView(genMeth, "Items_By_Category_PSL");
		genMeth.clickXpthName_TextView(genMeth, "Keyboard (Cat 1)");
		
		//QR code
		genMeth.clickXpthName_TextView(genMeth, "This is QR");
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
		Thread.sleep(2000);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		driver.hideKeyboard();
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");
		
		genMeth.swipeDownShort(1000);
		genMeth.swipeDownShort(1000);

		genMeth.clickXpthName_TextView(genMeth, "SL_Manual_List");
		genMeth.clickXpthName_TextView(genMeth, "2");
		
		//PSL with Variable
		genMeth.clickXpthName_TextView(genMeth, "Items_SmallerThanMobileDate_PSL");
		genMeth.clickXpthName_TextView(genMeth, "3");
			
		// image 
		genMeth.swipeDownLong(1000);
		genMeth.clickXpthName_TextView(genMeth, "Image");
		genMeth.eyesCheckWindow("Actions_List_Inline (Droid) - No Image selected", useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/image_picker_fragment_open_camera_btn");
		Thread.sleep(1000);
		genMeth.backDroidButton();

		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/image_picker_fragment_open_gallery_btn");
		genMeth.eyesCheckWindow("Actions_List_Inline (Droid)- Gallery opened", useEye, genMeth, skipfailure);
		genMeth.backDroidButton();
		Thread.sleep(1000);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_fragment_submit_button");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("Actions_List_Inline (Droid)- PN success", useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/push_notification_dialog_cancel_btn");


		//Verify Startup screen is open
		genMeth.backDroidButton();
		genMeth.swipeUpShort(1000);
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);

	}
	
	
	@Test(enabled = true, testName = "Actions_Grid_One_Layer_Cell", retryAnalyzer = Retry.class, description = "Check the Grid one layer cell action",
			groups = { "Sanity Android" })

	public void Actions_Grid_One_Layer_Cell() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {
		
		// go to List
		genMeth.clickXpthName_TextView(genMeth, DroidData.ActionsReport);
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - One Layer");
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer main view", useEye, genMeth, skipfailure);
		
		//Set one row
		Thread.sleep(1000);
		genMeth.clickId(genMeth, "action_slicer");
		Thread.sleep(1000);
		genMeth.clickXpthName_TextView(genMeth, "Service Call ID");
		Thread.sleep(1000);
		genMeth.clickXpthName_TextView(genMeth, "10");
		genMeth.backDroidButton();
		genMeth.clickXpthName_TextView(genMeth, "Done");


		//USER INPUT = Free Text (Description)
		boolean isTextDisplayed = genMeth.checkIsElementVisible(By.xpath("//android.widget.TextView[@text='" + "Descrip 1" + "']"));
		if (isTextDisplayed) {
			genMeth.clickXpthName_TextView(genMeth, "Descrip 1");
			driver.pressKeyCode(AndroidKeyCode.DEL);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_2);
		} else {
			genMeth.clickXpthName_TextView(genMeth, "Descrip 2");
			driver.pressKeyCode(AndroidKeyCode.DEL);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		}
		driver.hideKeyboard();
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");
		
		Thread.sleep(10000);	
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer- description (free text)", useEye, genMeth, skipfailure);

		//USER INPUT = Simple List MB (Priority)
		isTextDisplayed = genMeth.checkIsElementVisible(By.xpath("//android.widget.TextView[@text='" + "90" + "']"));
		if (isTextDisplayed) {
			genMeth.clickXpthName_TextView(genMeth, "90");
			genMeth.clickXpthName_TextView(genMeth, "91");

		}
 else {
			genMeth.clickXpthName_TextView(genMeth, "91");
			genMeth.clickXpthName_TextView(genMeth, "90");

		}
		
		Thread.sleep(10000);	
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer- Priority (Simple List MB)", useEye, genMeth, skipfailure);

		

		//USER INPUT = PSL (ItemID)
		genMeth.clickXpthName_TextView(genMeth, "15");
		Thread.sleep(2000);
		genMeth.clickXpthName_TextView(genMeth, "Keyboard  (Cat 2)");
		
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer- ItemID (PSL)", useEye, genMeth, skipfailure);
		genMeth.swipeRightLong(1000);

		// USER INPUT = Simple List DI (Status)
		genMeth.clickXpthName_TextView(genMeth, "6");

		genMeth.eyesCheckWindow(
				"Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer- Status (Simple List DI)",
				useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "Not Clear");
		Thread.sleep(6000);
		genMeth.eyesCheckWindow(
				"Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer- Status success (Simple List DI)",
				useEye, genMeth, skipfailure);
		
		//USER INPUT = QR (KPI)
		isTextDisplayed = genMeth.checkIsElementVisible(By.xpath("//android.widget.TextView[@text='" + "01" + "']"));

		if (isTextDisplayed) {
			genMeth.clickXpthName_TextView(genMeth, "01");
			genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_2);
			driver.hideKeyboard();
			genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");
			genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_dialog_submit");

		}
		else{
			genMeth.clickXpthName_TextView(genMeth, "02");
			genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
			driver.hideKeyboard();			
			genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");
			genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_dialog_submit");
			
		}
		
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Cell (Droid) - Grid One Layer- KPI (QR)", useEye, genMeth, skipfailure);
		
		// Verify Startup screen is open
		genMeth.backDroidButton();
		genMeth.swipeUpShort(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	

	}
	
	
	@Test(enabled = true, testName = "Actions_Grid_One_Layer_Row", retryAnalyzer = Retry.class, description = "Check the Grid one layer Row action",
			groups = { "Sanity Android" })
	public void Actions_Grid_One_Layer_Row() throws ParserConfigurationException, SAXException,
	IOException, InterruptedException {

		// go to List
		genMeth.clickXpthName_TextView(genMeth, DroidData.ActionsReport);
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - One Layer");
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Row (Droid) - Grid One Layer main view",useEye, genMeth, skipfailure);

		// Set one row
		genMeth.clickId(genMeth, "action_slicer");
		genMeth.clickXpthName_TextView(genMeth, "Service Call ID");
		genMeth.clickXpthName_TextView(genMeth, "1");
		genMeth.backDroidButton();
		genMeth.clickXpthName_TextView(genMeth, "Done");


		//Row Action  
		genMeth.swipeRightLong(1000);
		genMeth.clickXpthName_LinearLayout(genMeth, "7");
		genMeth.clickXpthName_LinearLayout(genMeth, "7");
		Thread.sleep(4000);

		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Row (Droid) - Grid One Layer- Row parameters before insert",useEye, genMeth, skipfailure);

		// Free text
		genMeth.clickXpthName_TextView(genMeth, "Free_Text1");
		genMeth.sendXpthName_EditText(genMeth, "Input text here", "New Row");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");

		// QR
		genMeth.clickXpthName_TextView(genMeth, "QR_");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/barcode_activity_manual_input_button");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/qr_manual_input_edit_text", "New QR");
		driver.hideKeyboard();
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/qr_manual_input_submit_btn");

		// SL Manual List
		genMeth.clickXpthName_TextView(genMeth, "SL_Manual_List_");
		genMeth.clickXpthName_TextView(genMeth, "1");
		
		// SL DI
		genMeth.clickXpthName_TextView(genMeth, "Device_Type_SL_DI_");
		genMeth.clickXpthName_TextView(genMeth, "Laptop");

		// DL
		genMeth.clickXpthName_TextView(genMeth, "Device_Model_DL_");
		genMeth.clickXpthName_TextView(genMeth, "Lenovo");

		// PSL (Source = column)
		genMeth.clickXpthName_TextView(genMeth, "Items_By_Category_PSL");
		genMeth.clickXpthName_TextView(genMeth, "Power Supply (Cat 1)");

		// PSL (Source = Variable=MobileDate)
		genMeth.clickXpthName_TextView(genMeth, "Items_SmallerThanMobileDate_PSL");
		genMeth.clickXpthName_TextView(genMeth, "3");
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Row (Droid) - Grid One Layer- Row parameters after insert",useEye, genMeth, skipfailure);

		genMeth.swipeDownLong(1000);

		// image
		genMeth.clickXpthName_TextView(genMeth, "Image");
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Row (Droid) - Grid One Layer- Image set",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/image_picker_fragment_open_camera_btn");
		Thread.sleep(1000);
		genMeth.backDroidButton();

		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/image_picker_fragment_open_gallery_btn");
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Row (Droid) - Gallery opened", useEye,genMeth, skipfailure);
		genMeth.backDroidButton();
		Thread.sleep(1000);
		genMeth.backDroidButton();
		genMeth.clickId(genMeth,"com.skygiraffe.operationaldata:id/action_fragment_submit_button");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Actions_Grid_One_Layer_Row (Droid) - after action executed",useEye, genMeth, skipfailure);

		// Verify Startup screen is open
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpShort(2000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App",useEye, genMeth, skipfailure);

	}

	
	@Test(enabled = true, testName = "Actions_Grid_Two_Layer", retryAnalyzer = Retry.class, description = "Check the Grid two layer actions",
			groups = { "Sanity Android" })

	public void Actions_Grid_Two_Layer() throws ParserConfigurationException, SAXException,
			IOException, InterruptedException {

		// go to List
		genMeth.clickXpthName_TextView(genMeth, DroidData.ActionsReport );
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Grid - Two Layers");
		genMeth.eyesCheckWindow("Actions_Grid_Two_Layer (Droid) - Grid Two Layer main view",useEye, genMeth, skipfailure);

		genMeth.clickXpthName_TextView(genMeth, "3");
		
		//DL
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/template_view_item_icon");
		genMeth.clickXpthName_TextView(genMeth, "Keyboard (Cat 1)");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");
		Thread.sleep(10000);
		genMeth.eyesCheckWindow("Actions_Grid_Two_Layer- Grid Two Layers- ItemID SL",useEye, genMeth, skipfailure);
		
		//Row Action (UpdateWithTableParam)
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/right_menu");
		genMeth.clickXpthName_TextView(genMeth, "UpdateWithTableParam");
		
		//Cancel the action
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_fragment_cancel_button");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/right_menu");
		genMeth.clickXpthName_TextView(genMeth, "UpdateWithTableParam");
		
		//Press the back icon
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/right_menu");
		genMeth.clickXpthName_TextView(genMeth, "UpdateWithTableParam");
		
		
		genMeth.clickXpthName_TextView(genMeth, "DummyParam1");
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");

		genMeth.clickXpthName_TextView(genMeth, "TableParams");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/add_new_item_to_list");
		
		genMeth.clickXpthName_TextView(genMeth, "Priority");
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_1);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_submit_button");

		
		genMeth.clickXpthName_TextView(genMeth, "Status");
		genMeth.clickXpthName_TextView(genMeth, "Open");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/display_parameter_save");		
		genMeth.eyesCheckWindow("Actions_Grid_Two_Layer- Grid Two Layers- Table Parameter filled",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.eyesCheckWindow("Actions_Grid_Two_Layer- Grid Two Layers- All parameters are filled",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/action_fragment_submit_button");
		Thread.sleep(8000);
		
		//Check the push notification
		genMeth.eyesCheckWindow("Actions_Grid_Two_Layer- Grid Two Layers- Action Success",useEye, genMeth, skipfailure);
		
		// Verify Startup screen is open
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.clickId(genMeth, DroidData.IconHome);
		Thread.sleep(2000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
	}

	

	@Test(enabled = true, groups = {"Sanity Android"}, testName = "Sanity", description = "Slicer report")
	public void slicerReport() throws InterruptedException, IOException{
		
		// go to List
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		
		//Setting Report slicer (BranchID = 7) 
		genMeth.clickXpthName_TextView(genMeth, "Slicer report");
		genMeth.clickId(genMeth, DroidData.BTNSlicer);
		genMeth.clickXpthName_TextView(genMeth, "BranchID");
		genMeth.clickXpthName_TextView(genMeth, "7");
		genMeth.backDroidButton();
		genMeth.clickXpthName_TextView(genMeth, "Done");

		genMeth.eyesCheckWindow("Slicer Report (Droid)- branchID selected",useEye, genMeth, skipfailure);
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		genMeth.eyesCheckWindow("Slicer Report (Droid)- List (BranchID=7)",useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Grid");
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Grid (BranchID=7)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Cover Flow");
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Cover Flow (BranchID=7)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Dashboard");		
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Dashboard (BranchID=7)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Map");		
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Map (BranchID=7)",useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Cards");		
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Cards (BranchID=7)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer News");		
		genMeth.eyesCheckWindow("Slicer Report (Droid)- News (BranchID=7)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Bar Chart");		
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Bar chart empty slicing",useEye, genMeth, skipfailure);

		genMeth.clickId(genMeth, DroidData.BTNSlicer);
		genMeth.clickXpthName_TextView(genMeth, "BranchID");
		genMeth.clickXpthName_TextView(genMeth, "Aventura Mall");
		genMeth.backDroidButton();
		genMeth.clickXpthName_TextView(genMeth, "Done");
		genMeth.eyesCheckWindow("Slicer Report (Droid)- Bar chart Aventura Mall",useEye, genMeth, skipfailure);

		// Verify Startup screen is open
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}
	
	
	@Test(enabled = true, groups = {"Sanity Android"}, testName = "Sanity", description = "Slicer report")
	public void slicerReportWithSecurityFilter() throws InterruptedException, IOException{
		
		// go to List
		genMeth.swipeDownLong(1000);
		genMeth.swipeDownLong(1000);
		genMeth.clickXpthName_TextView(genMeth, "SlicerReport_Sfilter");
		genMeth.clickId(genMeth, DroidData.BTNSlicer);
		genMeth.clickXpthName_TextView(genMeth, "BranchID");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - branchID 1,3 only",useEye, genMeth, skipfailure);
				
		genMeth.clickXpthName_TextView(genMeth, "3");
		genMeth.backDroidButton();
		genMeth.clickXpthName_TextView(genMeth, "Done");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - List (BranchID=3)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer One Layer");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Grid One Layer (BranchID=3)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Cover Flow");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Cover Flow (BranchID=3)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Dashboard");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Dashboard (BranchID=3)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Map");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Map (BranchID=3)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Cards");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Cards (BranchID=3)",useEye, genMeth, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer Two Layer");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Slicer Two Layer (First layer) (BranchID=3)",useEye, genMeth, skipfailure);
		genMeth.clickXpthName_TextView(genMeth, "3");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - Slicer Two Layer (Second Layer layer) (BranchID=3)",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.IconHome);
		
		genMeth.clickId(genMeth, DroidData.TabBarTitle_Name);
		genMeth.clickXpthName_CheckedTextView(genMeth, "Slicer News");
		genMeth.eyesCheckWindow("Slicer Report with Security Filter (Droid) - News (BranchID=3)",useEye, genMeth, skipfailure);

		// Verify Startup screen is open
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.swipeUpLong(1000);
		genMeth.eyesCheckWindow("Default app is open (Droid) - SQL Golden App", useEye, genMeth, skipfailure);
		
	}
	
	

	
	
	@Test(enabled = true, groups = { "Sanity Android_Waiting for the android popup issue to be fixed" }, testName = "Sanity Tests", description = "login with bad/missing credentials", retryAnalyzer = Retry.class)
	public void badCredentials() throws Exception, Throwable {

		genMeth.signOutFromStartup(genMeth);
		
		// Login with invalid user name
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_email_field");
		driver.pressKeyCode(AndroidKeyCode.DEL);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_authentication_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) - incorrect UserName/Password",useEye, genMeth, skipfailure);
		genMeth.swipeUpLong(1000);
		//genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/app_message_dialog_cancel_button");
		/*
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_M);

		// Login with invalid password 
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_password_field");
		driver.pressKeyCode(AndroidKeyCode.DEL);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_authentication_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) - incorrect UserName/Password",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/app_message_dialog_cancel_button");


		
		// Login with empty UserName
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_email_field");
		genMeth.clearId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_email_field");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_authentication_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) - incorrect UserName/Password",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/app_message_dialog_cancel_button");
		
		
		// Login with empty Password
		genMeth.clearId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_password_field");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_authentication_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) - incorrect UserName/Password",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/app_message_dialog_cancel_button");
		
		*/
		
		// Forgot your password Negative (attempt to restore password without an email
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_forgor_password");
		genMeth.clearId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_edtext");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_submit_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) - Empty user name for password reset ",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_password_fragment_cancel_btn");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_forgor_password");

		// Forgot your password Negative (attempt to restore password without a non existing email
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_submit_btn");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_edtext", "bla@bla.com");
		genMeth.eyesCheckWindow("badCredentials (Droid) - invalid user name for password reset ",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_submit_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) - invalid user name for password reset ",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_password_fragment_cancel_btn");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_forgor_password");

		// Forgot your password Positive (attempt to restore password with an existing email)
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_forgor_password");

		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_submit_btn");
		genMeth.sendId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_edtext", DroidData.userQA);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/recovery_pass_step_one_submit_btn");
		genMeth.eyesCheckWindow("badCredentials (Droid) -password reset",useEye, genMeth, skipfailure);
	}
	
	
	@Test(enabled = true, groups = { "Sanity Android1" }, testName = "Sanity Tests", description = "login with bad/missing credentials", retryAnalyzer = Retry.class)
	public void SampleAccount() throws Exception, Throwable {

		genMeth.signOutFromStartup(genMeth);
		Thread.sleep(1000);
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_settings_image_btn");
		
		genMeth.clickXpthName_TextView(genMeth, "Default Environment");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/sgserver_config_activity_close_btn");
		genMeth.clickId(genMeth, "com.skygiraffe.operationaldata:id/login_screen_sample_account");
		Thread.sleep(4000);
		genMeth.eyesCheckWindow("SampleAccount (Droid) - Sample app main view",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.eyesCheckWindow("SampleAccount (Droid) - Sample app (LSM is open)",useEye, genMeth, skipfailure);
		genMeth.clickId(genMeth, DroidData.IconHome);
		genMeth.signOutFromStartup(genMeth);
		
		
	}

	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "Sanity Tests", description = "Switching from Foreground to Background and vice versa use cases",
			groups = { "Sanity IOS__" })
	public void foregroundBackgroundSwitch() throws Exception, Throwable {

		//Take the app to background & foreground x times
		
		
		//Take the app to sleep/lock  x times
	

	}
	
	@Test(enabled = false, retryAnalyzer = Retry.class, testName = "connection lost handling", description = "Checking how the app owrks while connection is lost & back again", dependsOnGroups = { "Sanity*" },
			groups = { "Sanity IOS__" })
	public void connectionLost() throws InterruptedException, IOException,
			ParserConfigurationException, SAXException {

	}
	
	
	
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		try {
			driver.removeApp(appIdentifier);
			driver.quit();
			service.stop();
			
			
			/*
			boolean isAppInstalled = driver.isAppInstalled(appIdentifier);
			if (isAppInstalled) {
				driver.removeApp(appIdentifier);
			}
			*/
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SendResults sr = new SendResults("elicherni444@gmail.com",
				"meny@skygiraffe.com", "TestNG results", "Test Results");
		//sr.sendTestNGResult();
		sr.sendRegularEmail();
		/*
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng2 = new TestNG();
		testng2.setTestClasses(new Class[] { SendReport.class });
		testng2.setGroups("send mail");
		testng2.addListener(tla);
		testng2.run();
*/
	}

	

	/*
	
	@Test (enabled = true ,testName = "Sample App Dashboard DailySales", retryAnalyzer = Retry.class, description = "Dashboard DailySales" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

	public void sampleAplicationDashboardDailySales() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		
//Logout from startup page
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, DroidData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  DroidData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 5.2");
		
		//useEye = true;
	
// Login to sample app & open Dashboard report

		genMeth.eyesCheckWindow(eyes, "SampleApp Main screen", useEye, skipfailure);
		genMeth.clickName(genMeth,  DroidData.DashboardName);
		genMeth.eyesCheckWindow(eyes, "Dashboard Tab", useEye, skipfailure);
//		genMeth.swipeRightIphone6Plus(1000);
		genMeth.swipeRightIphone5(500);
		genMeth.eyesCheckWindow(eyes, "World wide orders Tab", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		genMeth.clickName(genMeth,  DroidData.DashboardName);
		
// Open Sales Bar
		// Change eye back to true once oleg fix the decimal issue
		Thread.sleep(2000);
		genMeth.clickId(genMeth, DroidData.SalesName);
		//set Eye UI to false due to ordinal change
//		useEye = true;
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar- Show All", useEye, skipfailure);
		genMeth.clickId(genMeth, DroidData.ReturnsName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar- show Sales/Net Sales", useEye, skipfailure);
		genMeth.clickId(genMeth, DroidData.SalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp show Net Sales", useEye, skipfailure);
		genMeth.clickId(genMeth, DroidData.NetSalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales - show Empty", useEye, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.SalesName);
		genMeth.clickId(genMeth, DroidData.ReturnsName);
		genMeth.clickId(genMeth, DroidData.NetSalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar- Show All", useEye, skipfailure);
		
		
//Open Sales Pie
		genMeth.clickId(genMeth, DroidData.DailySalesBarID);
		genMeth.clickId(genMeth, DroidData.DailysalesPieID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Net Sales", useEye, skipfailure);
		//genMeth.clickId(genMeth, DroidData.DestinyUSAID);
		//genMeth.clickName(genMeth, DroidData.DestinyUSAID);
		
		try {
			driver.findElementById(DroidData.DestinyUSAID).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Net Sales - Destiny USA", useEye, skipfailure);

		genMeth.clickId(genMeth, DroidData.ReturnsName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Returns", useEye, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.SalesName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Pie- Sales", useEye, skipfailure);
		genMeth.clickId(genMeth, DroidData.DailysalesPieID);
		genMeth.clickId(genMeth, DroidData.Last12hoursID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye, skipfailure);
		
	
// Check slicer in Sparklines
		genMeth.clickName(genMeth, DroidData.BTNSlicer);
		genMeth.clickId(genMeth, DroidData.BranchID);
		genMeth.clickId(genMeth, DroidData.DestinyUSAID);
		genMeth.clickName(genMeth, DroidData.BTNBackName);
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines / Destiny USA", useEye, skipfailure);
		
//Clear the Slicer
		genMeth.clickName(genMeth, DroidData.BTNSlicer);
		genMeth.clickName(genMeth, DroidData.BTNClearName);
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Last 12 Months - Sparklines", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		
//Open Daily Sales from main screen
		genMeth.clickId(genMeth, DroidData.DailySalesID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Daily Sales Bar (no back icon)- Show All", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		
		genMeth.clickName(genMeth, "M");
		
	}
	
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

	public void sampleAplicationServiceCalls() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

	
//OPEN SERVICE CALLS
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, DroidData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  DroidData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
		
		//genMeth.clickName(genMeth,  DroidData.DashboardName);
		
		genMeth.clickId(genMeth, DroidData.ServiceCallsID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls", useEye, skipfailure);
		
// InGrid Action- First layer
		//genMeth.clickName(genMeth, DroidData.BTNpriority_Name);
	/*	genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAStaticText[7]");
		genMeth.clickName(genMeth, "1");
		Thread.sleep(3000);
		genMeth.swipedownIphone5(1000);
		genMeth.swipeUpIphone5(1000);
		genMeth.clickName(genMeth, DroidData.BTNpriority_Name);
		genMeth.clickName(genMeth, "3");
		Thread.sleep(5000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 3", useEye, skipfailure);	
		
		//Open the Slicer
		genMeth.clickName(genMeth, DroidData.BTNSlicer);
		genMeth.clickId(genMeth, DroidData.BranchID);
		genMeth.clickId(genMeth, DroidData.MallOfAmerica_Id);
		genMeth.clickId(genMeth, DroidData.BTNBackName);
		genMeth.clickId(genMeth, DroidData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Slicer Mall Of America", useEye, skipfailure);
/*
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[6]/UIAStaticText[1]");
		Thread.sleep(3000);
		genMeth.clickName(genMeth, DroidData.BTNpriority_Name);
		genMeth.clickName(genMeth, "1");
		Thread.sleep(10000);
		genMeth.clickName(genMeth, DroidData.BTNpriority_Name);
		genMeth.clickName(genMeth, "4");
		Thread.sleep(6000);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- priority = 4", useEye, skipfailure);
		
	
		//Open the second layer
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAButton[1]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Second layer", useEye, skipfailure);
		
		//Mobile & Email Contact Details/Person
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[16]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Mobile Contact Person -Cards", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[17]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Email Contact Person -Cards", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickName(genMeth, DroidData.BTNdeleteDraft_Name);
		
		genMeth.scrollDown(driver);
		genMeth.scrollDown(driver);

		//Mobile / Email / Map / URL - Address section
		//Phone
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[20]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Mobile (Address Section)", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);

		//Email
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[21]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Email (Address Section)", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickName(genMeth, DroidData.BTNdeleteDraft_Name);
		
		// URL
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[22]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- URL ((Address Section))", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		
		//Map
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[24]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Mobile Maps (Address Section)", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);

		// Mobile / Email (Address Section)
		//Mobile
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[28]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Phone (Assigned To Section)", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);

		// Email 
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAStaticText[29]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Email (Assigned To Section)", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickName(genMeth, DroidData.BTNdeleteDraft_Name);
		
		//Close Service Call Action
		genMeth.clickName(genMeth, "Close Service Call");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Close Service Calls - Action", useEye, skipfailure);
		genMeth.clickName(genMeth, "Comments");
		genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[1]", "Meny The Best");
		
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		genMeth.clickName(genMeth, "Parts");
		genMeth.clickName(genMeth, "Drawer");
		genMeth.clickName(genMeth, "SolutionType");
		genMeth.clickName(genMeth, "Replaced cash drawer");
		genMeth.clickName(genMeth, "Status");
		genMeth.clickName(genMeth, "Open");
		genMeth.clickId(genMeth, DroidData.BTNsubmit_ID);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls- Close Service Calls - After Action", useEye, skipfailure);
		Thread.sleep(2000);
		genMeth.clickName(genMeth, DroidData.BTNBackName);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
				
	}
	
	
	@Test (enabled = true ,testName = "Sample Application", retryAnalyzer = Retry.class, description = "" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

	public void sampleAplicationServiceCallsMapNewServicecall() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		
//OPEN SERVICE CALLS Map
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, DroidData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  DroidData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
		
		//Open service calls map
		genMeth.clickId(genMeth, DroidData.ServiceCallsMapID);
		Thread.sleep(1000);
		genMeth.clickXpth(genMeth, DroidData.MallofAmericaOnMapXpath);
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America", useEye, skipfailure);

		
		//Check is Location popup is displayed
		//genMeth.clickId(genMeth, DroidData.BTNmapphoneiconID);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[2]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America - Phone Icon Option", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		
	//	genMeth.clickName(genMeth, DroidData.BTNMapCarIconName);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[2]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls Maps- Mall of America - Car Direction", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		//go back to the map tab via the back navigation icon
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAButton[3]");
		genMeth.eyesCheckWindow(eyes, "SampleApp Service Calls:5", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);

//Create new service call
		genMeth.clickId(genMeth, DroidData.BTNnewServiceCallId);
		genMeth.eyesCheckWindow(eyes, "New Service Call", useEye, skipfailure);
		
		genMeth.clickId(genMeth, DroidData.BranchID);
		genMeth.eyesCheckWindow(eyes, "Branch simple list", useEye, skipfailure);
		genMeth.clickId(genMeth, DroidData.MallOfAmerica_Id);
		
		genMeth.clickId(genMeth, "Assigned To");
		genMeth.clickId(genMeth, "Jessica Blue");
		
		genMeth.clickId(genMeth, "Category");
		genMeth.clickId(genMeth, "Computer");
		
		genMeth.clickId(genMeth, "Item");
		genMeth.clickId(genMeth, "Memory card");
		
		genMeth.clickId(genMeth, "Description");
		genMeth.setEnglishKeyboard(genMeth);
		genMeth.sendXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextView[1]", "Meny The Best");
		genMeth.clickId(genMeth, DroidData.BTNdoneName);
		
		genMeth.clickId(genMeth, DroidData.BTNpriority_Name);
		genMeth.clickId(genMeth, "1");
		
		genMeth.eyesCheckWindow(eyes, "New service call with parameters", useEye, skipfailure);
		genMeth.clickId(genMeth, DroidData.BTNsubmit_ID);
		Thread.sleep(2000);

		//genMeth.eyesCheckWindow(eyes, "New Service Call", useEye, skipfailure);
		genMeth.eyesCheckWindow(eyes, "New service call Actions collections +", useEye, skipfailure);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAStaticText[1]");
		genMeth.eyesCheckWindow(eyes, "New Service Call", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);

	}
 

	@Test (enabled = true ,testName = "Sample App OrderLookup Operation", retryAnalyzer = Retry.class, description = "OrderLookup Operation" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

	public void sampleAplicationOrderLookupOperation() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {

//OPEN Order Lookup
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, DroidData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  DroidData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
			
//Order lookup	
		genMeth.clickId(genMeth, DroidData.OrderLookup_ID);
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Order Lookup parameters", useEye, skipfailure);
		genMeth.clickName(genMeth, "Start Date");
		MobileElement UIAPickerWheel = driver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAPicker[1]/UIAPickerWheel[1]");
		UIAPickerWheel.sendKeys("July");
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		genMeth.clickName(genMeth, DroidData.BTNsubmit_ID);
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "List of Orders", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		
		//Operations
		genMeth.clickXpth(genMeth, " //UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[7]");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Inventory", useEye, skipfailure);
		//Open grid second layer
		genMeth.clickName(genMeth, DroidData.MallOfAmerica_Id);
		genMeth.eyesCheckWindow(eyes, "Inventory second layer", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		
		genMeth.clickName(genMeth, "Inventory");
		genMeth.clickName(genMeth, "Orders");
		/*genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);
		genMeth.swipeRightIphone5(1000);

		genMeth.eyesCheckWindow(eyes, "Orders", useEye, skipfailure);
		
		genMeth.clickName(genMeth, "Orders");
		genMeth.clickName(genMeth, "Place New Order");

		genMeth.eyesCheckWindow(eyes, "Place New Order", useEye, skipfailure);

		//Open the place new order
		MobileElement El = driver.findElementByXPath(DroidData.BTNplaceNewOrder_Xpth);
		El.click();
		
		genMeth.eyesCheckWindow(eyes, "Place new order parameters", useEye, skipfailure);
//		genMeth.clickName(genMeth, DroidData.BTNsubmit_ID);		
//		genMeth.eyesCheckWindow(eyes, "Place new order parameters missing", useEye, skipfailure);
//		genMeth.clickName(genMeth, DroidData.BTNokName);
		
//Fill the parameters
		genMeth.clickId(genMeth, DroidData.BranchID);
		genMeth.clickName(genMeth, DroidData.MallOfAmerica_Id);
		genMeth.clickName(genMeth, "ProductID");
//		genMeth.accessToCameraHandle(genMeth);
		Thread.sleep(1000);
		genMeth.clickXpth(genMeth, "//UIAApplication[1]/UIAWindow[1]/UIATextField[1]");
		Thread.sleep(1000);
		genMeth.clickName(genMeth, "1");
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		Thread.sleep(2000);
		genMeth.clickName(genMeth, "Quantity");
		genMeth.clickName(genMeth, "1");
		genMeth.clickName(genMeth, DroidData.BTNdoneName);
		genMeth.eyesCheckWindow(eyes, "Place new order All parameters", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNsubmit_ID);
		genMeth.eyesCheckWindow(eyes, "Place New Order", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.IconBack_Nav_Name);
		genMeth.clickName(genMeth, DroidData.Icon_AllApps_Name);
		
	}
	
	@Test (enabled = true ,testName = "Sample App Technicians", retryAnalyzer = Retry.class, description = "Technicians" ,
			groups= {"Sanity IOSsample"}  /*dependsOnMethods={"testLogin"})	

	public void sampleAplicationTechnicians() throws ParserConfigurationException,
			SAXException, IOException, InterruptedException {
		
//OPEN Order Lookup
		genMeth.signOutFromStartup(genMeth);
		genMeth.clickId(genMeth, DroidData.BTNsampleAccountID);
		
		genMeth.clickName(genMeth,  DroidData.Icon_AllApps_Name);
		genMeth.clickName(genMeth,  "Operations 4.11");
					
// Technicians
		genMeth.clickName(genMeth, "Technicians");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians", useEye, skipfailure);
		
// 	Phone Icon
		genMeth.clickName(genMeth, "Phone");
		Thread.sleep(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians- Phone", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickName(genMeth, "Phone");
// Add to contacts
		genMeth.clickName(genMeth, DroidData.BTNaddContact_Name);
//		genMeth.accessToContactsHandle(genMeth);
		genMeth.eyesCheckWindow(eyes, "Technicians- Added by SkyGiraffe screen", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNBackName);
		
// Mail Icon
		genMeth.clickName(genMeth, "Email");
		Thread.sleep(3000);
		genMeth.eyesCheckWindow(eyes, "Technicians- New Message screen", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		genMeth.clickName(genMeth, DroidData.BTNdeleteDraft_Name);

// Map Icon
		genMeth.clickName(genMeth, "Address");
		genMeth.eyesCheckWindow(eyes, "Technicians- Address screen", useEye, skipfailure);
		genMeth.clickName(genMeth, DroidData.BTNCancelName);
		
// Swipe along the technicians Cover Flow
		genMeth.swipeRightIphone5(1000);
		genMeth.eyesCheckWindow(eyes, "Technicians- cover flow John Grant", useEye, skipfailure);
				
	}
 
	
	*/

	
}



