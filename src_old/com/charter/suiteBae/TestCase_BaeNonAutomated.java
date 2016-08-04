package com.charter.suiteBae;
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


public class TestCase_BaeNonAutomated extends TestSuiteBase{
	/*XWPFDocument document=null;
	FileOutputStream fos= null;*/
	
	String runmodes[]=null;
	static int count=-1;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
				
				if(!TestUtil.isTestCaseRunnable(suiteBaexls,this.getClass().getSimpleName())){
					Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
	runmodes=TestUtil.getDataSetRunmodes(suiteBaexls, this.getClass().getSimpleName());	
			}
	@Test(dataProvider="getTestData")
	public void nonAutomatedBuyFlow(
			
			String browserType,
			String streetAddress,
			String zipCode,
			String packageID,
			String monthlyCost,
			String Url
						  ) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\BaeNonAutomated_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
	
		Log.info("================================ Executing TestCase_BaeNonAutomated for Test Data: "+streetAddress +"  "+zipCode+"_"+browserType+"======================================================");
		Log.info("==================================================================================================================================================");
		
//======================================WebDriver Code ================================================================		
		openBrowser(browserType);
		//setUp();
		navigate(Url);
		
		if(driver.getTitle().equals("Charter Communications Login")){
			fluentWait("bae_txtbx_userName_id",60, 5);
			//	captureScreenshot("bae_EmployeeSingleSignOn");
				getScreen(document, fos, "bae_EmployeeSingleSignOn");
				input("bae_txtbx_userName_id", "ahasan");
				input("bae_txtbx_password_id", "Charter*12345");
				click("bae_btn_login_name");
				
		}
		
		fluentWait("bae_txtbx_streetAddress_id",60, 5);
	//	captureScreenshot("bae_Localization");
		getScreen(document, fos, "bae_Localization");
		input("bae_txtbx_streetAddress_id", streetAddress);
		input("bae_txtbx_zipCode_id", zipCode);
		input("bae_txtbx_salesid_id", "12345");
		click("bae_ckbx_customerpresentyes_id");
		click("bae_btn_continuegetonlineonlydeals_id");
		wait2();
		
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_sb"));
		
		Log.info("checking for Address Not Match Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("AddressNotMatch_sb"))){
		//	captureScreenshot("bae_AddressNotMatch");
			getScreen(document, fos, "bae_AddressNotMatch");
			Log.info("AddressNotMatch Page is present, now handling it");	
			click("bae_ckbx_yes_xpath");
			click("bae_btn_conti_xpath");
			wait2();	
		}
		else{
			Log.info("Address is Matched ");
		}
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_sb"));
		
		
		Log.info("checking for Multiple Address Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("MultipleAddress_sb"))){
			
			Log.info("Multiple Address Page is present");
			//captureScreenshot("bae_MultipleAddress");
			getScreen(document, fos, "bae_MultipleAddress");
			  Log.info("Choosing first address");
			 click("bae_btn_selectAdd_css");
			 wait2();
		}
		else{
			Log.info("Multiple Address Page is not present");
		}
		Log.info("checking for address clarification Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("Addressclarification_sb"))){
			Log.info("address clarification Page is present");
		//	captureScreenshot("bae_Addressclarification");
			getScreen(document, fos, "bae_Addressclarification");
			  Log.info("Clicking on view offers");
			  click("bae_rbtn_iAmMovingToThisAddress_xpath");
			  fluentWait("bae_btn_address-clarifyContinue_xpath",60, 2);
			  click("bae_btn_address-clarifyContinue_xpath");
			  wait2();
		}
		else{
			Log.info("address clarification window is not present");
		} 
		
		
		Log.info("checking for Retrieve Cart window");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("RetrieveCart_sb"))){
			Log.info("Retrieve Cart window is present");
		//	captureScreenshot("bae_RetrieveCart");
			getScreen(document, fos, "bae_RetrieveCart");
			  Log.info("Clicking on Proceed with new order");
			 click("bae_lnk_BeginaNewCart_id");
			 wait2();
		}
		else{
			Log.info("Retrieve Cart window is not present");
		}
		
		
		fluentWait("bae_btn_choosePlan_xpath",60,2);
	//	captureScreenshot("bae_Storefront");
		getScreen(document, fos, "bae_Storefront");
		choosePackage(packageID);
		wait3();
		
		waitForTextToBeDisplayed("bae_btn_Customizationcontinue_xpath", "CONTINUE");
	//	captureScreenshot("bae_Customization");
		getScreen(document, fos, "bae_Customization");
		click("bae_rbtn_keepExistingNumber_css");
		click("bae_rbtn_privateArea_xpath");		
		fluentWait("bae_lnk_moreOptions_xpath",60, 5);
		waitForPageLoad(10000);
		input("bae_txtbx_enterExistingPhoneNumber_id", "1234567897");
		selectDropDown("bae_drpdwn_phoneProvider_id", "AT&T");
		//verifyMonthlyCost("bae_txt_monthlyCost_customize_xpath",monthlyCost);
		waitForPageLoad(5000);
		click("bae_btn_Customizationcontinue_xpath");
		wait3();
		
		waitForTextToBeDisplayed("bae_btn_registration-submit_id", "CONTINUE");
	//	captureScreenshot("bae_EnterYourInfo");
		getScreen(document, fos, "bae_EnterYourInfo");
		input("bae_txtbx_businesstName_id","charter.com");
		input("bae_txtbx_firstName_id","FakeAyaz");
		input("bae_txtbx_lastName_id","TestCH0107");
		input("bae_txtbx_phoneNumber_id","123456789779");
		input("bae_txtbx_mobileNumber_id","123456789779");
		input("bae_txtbx_email_id","ayaz.hasan@charter.com");
		input("bae_txtbx_confirmEmail_id","ayaz.hasan@charter.com");
		input("bae_txtbx_agentEmail_id","ayaz.hasan@charter.com");
		input("bae_txtbx_confirmAgentEmail_id","ayaz.hasan@charter.com");
		selectDropDown("bae_select_leadReferral_id","None");
		click("bae_ckbx_IAgree_id");
		click("bae_btn_registration-submit_id");
		wait3();
		
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("TermsAndConds_sb"))){
	    	   
	    	click("sb_chkbx_chkSectionAgreed14_name");
	   		click("sb_chkbx_chkSectionAgreed15_name");
	   		click("sb_chkbx_chkSectionAgreed16_name");
	   		click("sb_chkbx_chkSectionAgreed24_name");
	   		click("sb_chkbx_chkSectionAgreed30_name");
	   		getScreen(document, fos, "TermsAndConds");
	   		click("sb_btn_registration-submit_id");
	   		wait3(); 
	       }
		fluentWait("bae_txt_monthlyCost_xpath",60,2);
	//	captureScreenshot("bae_NonScheduleOrderHistory");
		getScreen(document, fos, "bae_NonScheduleOrderHistory");
		verifyMonthlyCost("bae_txt_monthlyCost_xpath",monthlyCost);
		closeBrowser();
		
//===================================================================================================================================	
	}
	
	@AfterMethod
	public void reportDataSetResult() throws Exception{
		if(skip)
			TestUtil.reportDataSetResult(suiteBaexls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteBaexls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteBaexls, this.getClass().getSimpleName(), count+2, "PASS");
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
			TestUtil.reportDataSetResult(suiteBaexls, "Test Cases", TestUtil.getRowNum(suiteBaexls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteBaexls, "Test Cases", TestUtil.getRowNum(suiteBaexls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteBaexls, this.getClass().getSimpleName());
	}

}
