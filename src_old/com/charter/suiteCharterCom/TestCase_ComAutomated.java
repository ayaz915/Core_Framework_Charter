package com.charter.suiteCharterCom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.charter.util.TestUtil;

public class TestCase_ComAutomated extends TestSuiteBase {

	String runmodes[]=null;
	static int count=-1;

	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(suiteCharterComxls,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suiteCharterComxls, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void nonAutomatedBuyFlow(
			            String browserType,
						String streetAddress,
						String zipCode,
						String packageID,
						String monthlyCost,
						String Url,
						String TV,
						String Internet,
						String Phone
							) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\ComAutomated_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
	
		   Log.info("================================ Executing TestCase_ComNonAutomated for Test Data: "+streetAddress +"  "+zipCode+" "+browserType+"======================================================");
		   Log.info("==================================================================================================================================================");
		
		  
		
//======================================WebDriver Code ================================================================

		
	    openBrowser(browserType);
		//setUp();
		navigate(Url);
		waitForTextToBeDisplayed("btn_continue_id", "CONTINUE");
		getScreen(document, fos, "com_Localization");
		input("txtbx_streetAddress_id", streetAddress);
		input("txtbx_zipCode_id", zipCode);
		click("btn_continue_id");
		wait1();
		wait1();
		
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
		
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("AddressNotMatch_com"))){
			waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
			verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
			//captureScreenshot("com_AddressNotMatchPage");
			getScreen(document, fos, "com_AddressNotMatchPage");
			Log.info("AddressNotMatch Page is present, now handling it");
			click("ckbx_yes_xpath");
			click("btn_conti_xpath");
			wait1();
			wait1();
		}
		else{
			Log.info("Address is Matched ");
		}
	
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
		
		Log.info("checking for Multiple Address Page");
		
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("MultipleAddress_com"))){
			
			Log.info("Multiple Address Page is present");
			waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
			verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
			//captureScreenshot("com_MultipleAddressPage");
			getScreen(document, fos, "com_MultipleAddressPage");
			  Log.info("Choosing first address");
			 click("ckbx_selectAdd0_xpath");
			 wait1();
			 wait1();
		}
		else{
			Log.info("Multiple Address Page is not present");
		}
		
		Log.info("checking for address clarification Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("Addressclarification_com"))){
			Log.info("address clarification Page is present");
			waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
			verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
			//captureScreenshot("com_AddressclarificationPage");
			getScreen(document, fos, "com_AddressclarificationPage");
			  Log.info("Clicking on view offers");
			  click("btn_prospect_view_offers_xpath");
			 wait1();
			 wait1();
		}
		else{
			Log.info("address clarification window is not present");
		} 
		
	
		Log.info("checking for Retrieve Cart window");
		
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("RetrieveCart_com"))){
			Log.info("Retrieve Cart window is present");
			waitForTextToBeDisplayed("txt_number_xpath","1-877-684-9760");
			verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
			//captureScreenshot("com_RetrieveCartPage");
			getScreen(document, fos, "com_RetrieveCartPage");
			  Log.info("Clicking on Proceed with new order");
			  click("lnk_startNewOrde_id");
			 wait1();
			 wait1();
		}
		else{
			
			Log.info("Retrieve Cart window is not present");
		}	
		
		
		waitForTextToBeDisplayed("txt_number_xpath", "1-855-839-4246");
		verifyPhoneNumber("txt_number_xpath", "1-855-839-4246");
		getScreen(document, fos, "com_StoreFrontPage");
		fluentWait("btn_choosePlan_xpath",60,2);
		choosePackage(packageID);
		wait1();
		wait1();
			//waitForTextToBeDisplayed("txt_number_xpath", "1-855-839-4341");CHARTER.COMAYAZ.HASAN@CHARTER.COM
			//verifyPhoneNumber("txt_number_xpath", "1-855-839-4341");
			getScreen(document, fos, "com_CustomizeYourOrderPage");
			handleCustomizationResi(TV, Internet, Phone);
			waitForPageLoad(5000);
	     	click("btn_continue3_xpath");
	     	wait1();
	     	
			waitForTextToBeDisplayed("txt_number_xpath", "1-855-839-4368");
			verifyPhoneNumber("txt_number_xpath", "1-855-839-4368");
			waitForTextToBeDisplayed("btn_registration_submit_id", "SUBMIT ORDER");
			//captureScreenshot("com_EnterYourInfoPage");
			getScreen(document, fos, "com_EnterYourInfoPage");
			input("txtbx_firstname_id","FakeAyaz");
			input("txtbx_lastname_id","TestCH0107");
			click("ckbx_phoneType_id");  
			input("txtbx_phoneNumber_id","1234567890");
			input("txtbx_email_id","ayaz.hasan@charter.com");
			input("txtbx_confirmEmail_id","ayaz.hasan@charter.com");
			click("ckbx_over_a_year_over_1_year_id");
			click("ckbx_termsAgree_id");
			//verifyMonthlyCost("txt_monthlyCost_xpath",monthlyCost);
			click("btn_registration_submit_id");
			wait1();
			wait1();
			wait1();
			
			Log.info("Checking for TermsAndConds");
			if(driver.getCurrentUrl().contains(CONFIG.getProperty("TermsAndConds_com"))){
				Log.info("TermsAndConds is present");
				fluentWait("ckbx_iAgree3_name",60, 2);
				getScreen(document, fos, "com_TermsConditionsPage");
				click("ckbx_iAgree3_name");
				click("ckbx_iAgree4_name");
				click("ckbx_iAgree5_name");
				click("btn_registration_submit_id");
				wait1();
			}
			else{
				Log.info("TermsAndConds is not present");
			}
			waitForTextToBeDisplayed("txt_number_xpath", "1-877-581-3485");
			verifyPhoneNumber("txt_number_xpath", "1-877-581-3485");
			getScreen(document, fos, "com_NonScheduleOrderHistoryPage");
			verifyMonthlyCost("txt_monthlyCost_xpath",monthlyCost);
		    closeBrowser();
			
//===============================================================================================================================================		
	
	}
	
	@AfterMethod
	public void reportDataSetResult() throws IOException{

		if(skip)
			TestUtil.reportDataSetResult(suiteCharterComxls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteCharterComxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteCharterComxls, this.getClass().getSimpleName(), count+2, "PASS");
		if(!skip)
		{
		document.write(fos);
	    fos.flush();
		fos.close();
		}
		skip=false;
		fail=false;	
	}
	@AfterTest
	public void reportTestResult(){
		if(isTestPass)
			TestUtil.reportDataSetResult(suiteCharterComxls, "Test Cases", TestUtil.getRowNum(suiteCharterComxls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteCharterComxls, "Test Cases", TestUtil.getRowNum(suiteCharterComxls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteCharterComxls, this.getClass().getSimpleName()) ;
	}
	
	
	
}
