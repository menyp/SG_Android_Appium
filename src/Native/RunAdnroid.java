package Native;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;



public class RunAdnroid {
	
	public static void main(String[] args) throws Exception, Throwable {
		// TODO Auto-generated method stub
		TestListenerAdapter tla = new TestListenerAdapter();
		
		//GenericMethods genMeth = new GenericMethods();
		//Process proc = Runtime.getRuntime().exec("/Applications/Appium.app/Contents/MacOS/Appium");
	    
		//Start Appium server
		//String startServer = genMeth.getValueFromPropFile("StartServerPath");
		//String stopServer = genMeth.getValueFromPropFile("StopServerPath");

		//Process proc =  Runtime.getRuntime().exec(startServer);	
	    //Thread.sleep(4000);
		
		//Run the first suite
//		
		
		
		//StartServerPath = genMeth.getValueFromPropFile("StartServerPath");
		//StopServerPath = genMeth.getValueFromPropFile("StopServerPath");
	
		TestNG testng1 = new TestNG();
		testng1.setTestClasses(new Class[] { SanityAndroid.class });
		testng1.setGroups("Sanity Android3");
		testng1.addListener(tla);
		testng1.run();
		
//		//Second suite- send report by mail
		TestNG testng2 = new TestNG();
		testng2.setTestClasses(new Class[] { SendReport.class });
		testng2.setGroups("send mail");
		testng2.addListener(tla);
		testng2.run();

//		//Stop Appium server
//		//Runtime.getRuntime().exec(stopServer);
//		//Runtime.getRuntime().exec("/Users/pogoplug/stopAppium");


	}

}