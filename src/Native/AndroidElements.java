package Native;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import jdk.internal.org.xml.sax.SAXException;

public class AndroidElements {
	
	
	//Buttons
	String BTNloginID;
	String BTNforgotPasswordID;
	String BTNsettingsLoginScreenID;
	String BTNsampleAccountID;
	String BTNweeklyOperationsID;
	String BTNokName;
	String BTNcancelForgotPasswordID;
	String BTNrecoverPasswordID;
	String BTNresetPasswordID;
	String BTNdoneName;
	String BTNsettingsIconXpth;
	String BTNlogoutXpth;
	String BTNBackName;
	String BTNClearName;
	String BTNSlicer;
	String BTNmapphoneiconID;
	String BTNCancelName;
	String BTNdirection;
	String BTNpriority_Name;
	String BTNnewServiceCallId;
	String BTNplaceNewOrder_Xpth;
	String BTNaddContact_Name;
	String BTNdeleteDraft_Name;
	String BTNclearText_Name;
	String BTNdelete_Name;
	String BTNrefresh_Name;
	String BTNsend_Name;
	String BTNseeAll_ID;
	String BTNkeyboardDelete;
	String BtnkeyboardMoreNumbers;
	String BTNsave;
	String BTNsubmit;
	String BTNrightMenuButton_ID;
	String BTNlogoutID;
	String BTNsampleAccount;
	
	
	
	//TextFields	
	String TEXTFIELDUserNameLoginScreen;
	String TEXTFIELDPasswordLoginScreen;
	String TEXTFIELDrecoveryEmailID;
	String TEXTFIELDqr_Xpth;
	
	
	
	//STRINGS
	String scrollDown;
	String scrollUp;
	String DashboardName;
	String SalesName;
	String ReturnsName;
	String NetSalesName;
	
	String DailySalesBarID;
	String DailysalesPieID;
	String DestinyUSAID;
	String Last12hoursID;
	String BranchID;
	String DailySalesID;
	String ServiceCallsID;
	String ServiceCallsMapID;
	String MallofAmericaOnMapXpath;
	String MallOfAmerica_Id;
	
	String CameraPemissions_ID;
	String ContactsPermissionsName;
	String OrderLookup_ID;
	
	String ActionsReport;
	
	
	//Checkbox
	
	
	//Icons
	String IconBackToApplicationList_xpth;
	String Iconaction_icon_green_Name;
	String Icon_AllApps_Name;
	String IconBack_Name;
	String IconBack_Nav_Name;
	String IconApplicationInfo_Name;
	String IconHome;
	String IconRefresh;
	String IconLoginSettings;
	String IconNavigationChart;
	String IconNavigationDashboard;
	
	
	
	//General Info
	String ConnectionAIRPLANE_MODE;
	String ConnectionWIFI;
	String CoonectionDATA;
	String ConnectionALL;
	String TabBarTitle_Name;
	String IconSearch;
	String EnvironmentQA;
	

	// Credentials
	String UserProd;
	String passwordProd;
	String userQA;
	String passwordQA;
	String badPassword;
	String StartupApplication;
	
	public AndroidElements(String langXml, String xmlPath ) throws ParserConfigurationException, SAXException, IOException, InterruptedException, org.xml.sax.SAXException{
		this.BTNloginID = XmlHandel.readAndroidXml("BTNloginID", langXml, xmlPath);
		this.BTNforgotPasswordID = XmlHandel.readAndroidXml("BTNforgotPasswordID", langXml, xmlPath);
		this.BTNsettingsLoginScreenID = XmlHandel.readAndroidXml("BTNsettingsLoginScreenID", langXml, xmlPath);
		this.BTNsampleAccountID = XmlHandel.readAndroidXml("BTNsampleAccountID", langXml, xmlPath);
		this.BTNcancelForgotPasswordID = XmlHandel.readAndroidXml("BTNcancelForgotPasswordID", langXml, xmlPath);
		this.BTNrecoverPasswordID = XmlHandel.readAndroidXml("BTNrecoverPasswordID", langXml, xmlPath);
		this.BTNresetPasswordID = XmlHandel.readAndroidXml("BTNresetPasswordID", langXml, xmlPath);
		this.BTNdoneName = XmlHandel.readAndroidXml("BTNdoneName", langXml, xmlPath);
		this.BTNsettingsIconXpth = XmlHandel.readAndroidXml("BTNsettingsIconXpth", langXml, xmlPath);
		this.BTNlogoutXpth = XmlHandel.readAndroidXml("BTNlogoutXpth", langXml, xmlPath);
		this.BTNBackName = XmlHandel.readAndroidXml("BTNBackName", langXml, xmlPath);
		this.BTNClearName = XmlHandel.readAndroidXml("BTNClearName", langXml, xmlPath);
		this.BTNSlicer = XmlHandel.readAndroidXml("BTNSlicer", langXml, xmlPath);
		this.BTNmapphoneiconID = XmlHandel.readAndroidXml("BTNmapphoneiconID", langXml, xmlPath);
		this.BTNCancelName = XmlHandel.readAndroidXml("BTNCancelName", langXml, xmlPath);
		this.BTNdirection = XmlHandel.readAndroidXml("BTNdirection", langXml, xmlPath);
		this.BTNokName = XmlHandel.readAndroidXml("BTNokName", langXml, xmlPath);
		this.BTNpriority_Name = XmlHandel.readAndroidXml("BTNpriority_Name", langXml, xmlPath);
		this.BTNnewServiceCallId = XmlHandel.readAndroidXml("BTNnewServiceCallId", langXml, xmlPath);
		this.BTNplaceNewOrder_Xpth = XmlHandel.readAndroidXml("BTNplaceNewOrder_Xpth", langXml, xmlPath);
		this.BTNaddContact_Name = XmlHandel.readAndroidXml("BTNaddContact_Name", langXml, xmlPath);
		this.BTNdeleteDraft_Name = XmlHandel.readAndroidXml("BTNdeleteDraft_Name", langXml, xmlPath);
		this.BTNclearText_Name = XmlHandel.readAndroidXml("BTNclearText_Name", langXml, xmlPath);
		this.BTNdelete_Name = XmlHandel.readAndroidXml("BTNdelete_Name", langXml, xmlPath);
		this.BTNrefresh_Name = XmlHandel.readAndroidXml("BTNrefresh_Name", langXml, xmlPath);
		this.BTNsend_Name = XmlHandel.readAndroidXml("BTNsend_Name", langXml, xmlPath);
		this.BTNseeAll_ID = XmlHandel.readAndroidXml("BTNseeAll_ID", langXml, xmlPath);
		this.BTNkeyboardDelete = XmlHandel.readAndroidXml("BTNkeyboardDelete", langXml, xmlPath);
		this.BtnkeyboardMoreNumbers = XmlHandel.readAndroidXml("BtnkeyboardMoreNumbers", langXml, xmlPath);
		this.BTNsave = XmlHandel.readAndroidXml("BTNsave", langXml, xmlPath);
		this.BTNsubmit = XmlHandel.readAndroidXml("BTNsubmit", langXml, xmlPath);
		this.BTNrightMenuButton_ID = XmlHandel.readAndroidXml("BTNrightMenuButton_ID", langXml, xmlPath);
		this.BTNlogoutID = XmlHandel.readAndroidXml("BTNlogoutID", langXml, xmlPath);
		this.BTNsampleAccount = XmlHandel.readAndroidXml("BTNsampleAccount", langXml, xmlPath);

		
		
		
		
		this.TEXTFIELDUserNameLoginScreen = XmlHandel.readAndroidXml("TEXTFIELDUserNameLoginScreen", langXml, xmlPath);
		this.TEXTFIELDPasswordLoginScreen = XmlHandel.readAndroidXml("TEXTFIELDPasswordLoginScreen", langXml, xmlPath);
		this.TEXTFIELDrecoveryEmailID = XmlHandel.readAndroidXml("TEXTFIELDrecoveryEmailID", langXml, xmlPath);
		this.TEXTFIELDqr_Xpth = XmlHandel.readAndroidXml("TEXTFIELDqr_Xpth", langXml, xmlPath);

		
		this.scrollDown = XmlHandel.readAndroidXml("scrollDown", langXml, xmlPath);
		this.scrollUp = XmlHandel.readAndroidXml("scrollUp", langXml, xmlPath);		
		this.ConnectionAIRPLANE_MODE = XmlHandel.readAndroidXml("ConnectionAIRPLANE_MODE", langXml, xmlPath);
		this.ConnectionWIFI = XmlHandel.readAndroidXml("ConnectionWIFI", langXml, xmlPath);
		this.CoonectionDATA = XmlHandel.readAndroidXml("CoonectionDATA", langXml, xmlPath);
		this.ConnectionALL = XmlHandel.readAndroidXml("ConnectionALL", langXml, xmlPath);
		this.UserProd = XmlHandel.readAndroidXml("UserProd", langXml, xmlPath);
		this.DashboardName = XmlHandel.readAndroidXml("DashboardName", langXml, xmlPath);
		this.SalesName = XmlHandel.readAndroidXml("SalesName", langXml, xmlPath);
		this.ReturnsName = XmlHandel.readAndroidXml("ReturnsName", langXml, xmlPath);
		this.NetSalesName = XmlHandel.readAndroidXml("NetSalesName", langXml, xmlPath);	
		this.DailySalesBarID = XmlHandel.readAndroidXml("DailySalesBarID", langXml, xmlPath);		
		this.DailysalesPieID = XmlHandel.readAndroidXml("DailysalesPieID", langXml, xmlPath);		
		this.DestinyUSAID = XmlHandel.readAndroidXml("DestinyUSAID", langXml, xmlPath);		
		this.Last12hoursID = XmlHandel.readAndroidXml("Last12hoursID", langXml, xmlPath);		
		this.BranchID = XmlHandel.readAndroidXml("BranchID", langXml, xmlPath);		
		this.DailySalesID = XmlHandel.readAndroidXml("DailySalesID", langXml, xmlPath);		
		this.ServiceCallsID = XmlHandel.readAndroidXml("ServiceCallsID", langXml, xmlPath);		
		this.ServiceCallsMapID = XmlHandel.readAndroidXml("ServiceCallsMapID", langXml, xmlPath);		
		this.MallofAmericaOnMapXpath = XmlHandel.readAndroidXml("MallofAmericaOnMapXpath", langXml, xmlPath);		
		this.CameraPemissions_ID = XmlHandel.readAndroidXml("CameraPemissions_ID", langXml, xmlPath);		
		this.ContactsPermissionsName = XmlHandel.readAndroidXml("ContactsPermissionsName", langXml, xmlPath);		
		this.MallOfAmerica_Id = XmlHandel.readAndroidXml("MallOfAmerica_Id", langXml, xmlPath);		
		this.OrderLookup_ID = XmlHandel.readAndroidXml("OrderLookup_ID", langXml, xmlPath);		
		this.ActionsReport = XmlHandel.readAndroidXml("ActionsReport", langXml, xmlPath);		

		
		
		

		this.IconBackToApplicationList_xpth = XmlHandel.readAndroidXml("IconBackToApplicationList_xpth", langXml, xmlPath);		
		this.Iconaction_icon_green_Name = XmlHandel.readAndroidXml("Iconaction_icon_green_Name", langXml, xmlPath);		
		this.Icon_AllApps_Name = XmlHandel.readAndroidXml("Icon_AllApps_Name", langXml, xmlPath);	
		this.IconBack_Name = XmlHandel.readAndroidXml("IconBack_Name", langXml, xmlPath);	
		this.IconBack_Nav_Name = XmlHandel.readAndroidXml("IconBack_Nav_Name", langXml, xmlPath);	
		this.IconApplicationInfo_Name = XmlHandel.readAndroidXml("IconApplicationInfo_Name", langXml, xmlPath);	
		this.IconRefresh = XmlHandel.readAndroidXml("IconRefresh", langXml, xmlPath);	
		this.IconHome = XmlHandel.readAndroidXml("IconHome", langXml, xmlPath);	
		this.IconSearch = XmlHandel.readAndroidXml("IconSearch", langXml, xmlPath);	
		this.IconLoginSettings = XmlHandel.readAndroidXml("IconLoginSettings", langXml, xmlPath);	
		this.IconNavigationChart = XmlHandel.readAndroidXml("IconNavigationChart", langXml, xmlPath);	
		this.IconNavigationDashboard = XmlHandel.readAndroidXml("IconNavigationDashboard", langXml, xmlPath);	

		
		
		

		this.passwordProd = XmlHandel.readAndroidXml("passwordProd", langXml, xmlPath);
		this.userQA = XmlHandel.readAndroidXml("userQA", langXml, xmlPath);
		this.passwordQA = XmlHandel.readAndroidXml("passwordQA", langXml, xmlPath);
		this.StartupApplication = XmlHandel.readAndroidXml("StartupApplication", langXml, xmlPath);
		this.TabBarTitle_Name = XmlHandel.readAndroidXml("TabBarTitle_Name", langXml, xmlPath);
		this.EnvironmentQA = XmlHandel.readAndroidXml("EnvironmentQA", langXml, xmlPath);
		//test

		
		
		
		
		

		
		
		

	}
	
}
