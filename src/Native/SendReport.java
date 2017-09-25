package Native;

import org.testng.annotations.Test;

public class SendReport {

	@Test(alwaysRun = true, groups = "send mail")
	public void sendMail() throws Exception {

		SendResults sr1 = new SendResults("skygiraffeqa@gmail.com","skygiraffeqa@gmail.com", "TestNG results", "Test Results");
		sr1.sendRegularEmail();
		//SendResults sr4 = new SendResults("elicherni444@gmail.com","lior@cloudengines.com", "TestNG results", "Test Results");
		//sr4.sendRegularEmail();

	}
	
}
