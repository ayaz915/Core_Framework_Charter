package com.charter.suiteSpectrumBusiness;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.charter.util.TestUtil;


public class TestCase_SbNonAutomated extends TestSuiteBase{
	
	/*XWPFDocument document=null;
	FileOutputStream fos= null;*/
	String runmodes[]=null;
	static int count=-1;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
				
				if(!TestUtil.isTestCaseRunnable(suiteSpectrumBusinessxls,this.getClass().getSimpleName())){
					Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
	runmodes=TestUtil.getDataSetRunmodes(suiteSpectrumBusinessxls, this.getClass().getSimpleName());	
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
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\SbNonAutomated_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
	
		Log.info("================================ Executing TestCase_SbNonAutomated for Test Data: "+streetAddress +"  "+zipCode+"_"+browserType+" ======================================================");
		Log.info("==================================================================================================================================================");
		
//======================================WebDriver Code ================================================================		
		
	
		//setUp();
		openBrowser(browserType);
		navigate(Url);
		waitForTextToBeDisplayed("sb_btn_continue_id", "CONTINUE");
		getScreen(document, fos, "sb_LocalizationPage");
		input("sb_txtbx_streetAddress_id",streetAddress);
		input("sb_txtbx_zipCode_id",zipCode);
		waitForTextToBeDisplayed("sb_phoneNumber_xpath", "877-581-3485");
		verifyPhoneNumber("sb_phoneNumber_xpath", "877-581-3485");
		scrollPage();
		click("sb_btn_continue_id");
		wait2();
		
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_sb"));
		
		Log.info("checking for Address Not Match Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("AddressNotMatch_sb"))){
			waitForTextToBeDisplayed("sb_phoneNumber_xpath", "855-690-3100");
			verifyPhoneNumber("sb_phoneNumber_xpath", "855-690-3100");
			//captureScreenshot("sb_AddressNotMatch");
			getScreen(document, fos, "sb_AddressNotMatch");
			Log.info("AddressNotMatch Page is present, now handling it");	
			click("sb_ckbx_yes_xpath");
			click("sb_btn_conti_xpath");
			wait2();
			
		}
		else{
			Log.info("Address is Matched ");
		}
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_sb"));
		
		
		Log.info("checking for Multiple Address Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("MultipleAddress_sb"))){
			
			Log.info("Multiple Address Page is present");
			waitForTextToBeDisplayed("sb_phoneNumber_xpath", "855-690-3100");
			verifyPhoneNumber("sb_phoneNumber_xpath", "855-690-3100");
			//captureScreenshot("sb_MultipleAddress");
			getScreen(document, fos, "sb_MultipleAddress");
			  Log.info("Choosing first address");
			 click("sb_btn_selectAdd_css");
			 wait2();
		}
		else{
			Log.info("Multiple Address Page is not present");
		}
		
		
		Log.info("checking for address clarification Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("Addressclarification_sb"))){
			Log.info("address clarification Page is present");
			//waitForTextToBeDisplayed("txt_number_xpath", "855.326.2399");
			//verifyPhoneNumber("txt_number_xpath", "855.326.2399");
			//captureScreenshot("sb_Addressclarification");
			getScreen(document, fos, "sb_Addressclarification");
			  click("sb_rbtn_iAmMovingToThisAddress_xpath");
			  fluentWait("sb_btn_address-clarifyContinue_xpath",60, 2);
			  click("sb_btn_address-clarifyContinue_xpath");
			  wait2();
		}
		else{
			Log.info("address clarification window is not present");
		} 
		
		
		Log.info("checking for Retrieve Cart window");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("RetrieveCart_sb"))){
			Log.info("Retrieve Cart window is present");
			//waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
			//verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
			//captureScreenshot("sb_RetrieveCart");
			getScreen(document, fos, "sb_RetrieveCart");
			  Log.info("Clicking on Proceed with new order");
			 click("sb_lnk_BeginaNewCart_id");
			 wait2();
		}
		else{
			Log.info("Retrieve Cart window is not present");
		}
		
		waitForTextToBeDisplayed("sb_btn_choosePlan_xpath", "CHOOSE");
	//	captureScreenshot("sb_Storefront");
		getScreen(document, fos, "sb_Storefront");
		waitForTextToBeDisplayed("sb_phoneNumber_xpath", "855-690-3100");
		verifyPhoneNumber("sb_phoneNumber_xpath", "855-690-3100");
		//click("sb_btn_internet_css");
		choosePackage(packageID);
		wait3();
		
		waitForTextToBeDisplayed("sb_btn_Customizationcontinue_xpath", "CONTINUE");
		//captureScreenshot("sb_Customization");
		getScreen(document, fos, "sb_Customization");
		waitForTextToBeDisplayed("sb_phoneNumber_xpath", "855-690-3100");
		verifyPhoneNumber("sb_phoneNumber_xpath", "855-690-3100");
		
		click("sb_rbtn_keepExistingNumber_css");
		click("sb_rbtn_privateArea_xpath");
		fluentWait("sb_lnk_moreOptions_xpath",60,2);
		waitForPageLoad(16000);
		input("sb_txtbx_enterExistingPhoneNumber_id", "1234567897");
		selectDropDown("sb_drpdwn_phoneProvider_id", "AT&T");
		
		waitForPageLoad(5000);
		click("sb_btn_Customizationcontinue_xpath");
		wait3();
		
		waitForTextToBeDisplayed("sb_btn_registration-submit_id", "CONTINUE");
		//captureScreenshot("sb_EnterYourInfo");
		getScreen(document, fos, "sb_EnterYourInfo");
		waitForTextToBeDisplayed("sb_phoneNumber_xpath", "877-581-3485");
		verifyPhoneNumber("sb_phoneNumber_xpath", "877-581-3485");
		input("sb_txtbx_businesstName_id","charter.com");
		input("sb_txtbx_firstName_id","FakeAyaz");
		input("sb_txtbx_lastName_id","TestCH0107");
		input("sb_txtbx_phoneNumber_id","123456789779");
		input("sb_txtbx_mobileNumber_id","123456789779");
		input("sb_txtbx_email_id","ayaz.hasan@charter.com");
		input("sb_txtbx_confirmEmail_id","ayaz.hasan@charter.com");
		//verifyMonthlyCost("sb_txt_monthlyCost_xpath",monthlyCost);
		click("sb_chkbx_TermsofServices_css");
		click("sb_btn_registration-submit_id");
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
		
		
		fluentWait("sb_txt_monthlyCost_xpath",60, 2);
		//captureScreenshot("sb_NonScheduleOrderHistory");
		getScreen(document, fos, "sb_NonScheduleOrderHistory");
		verifyMonthlyCost("sb_txt_monthlyCost_xpath",monthlyCost);
		closeBrowser();
		
	
//===================================================================================================================================	
	}
	
	@AfterMethod
	public void reportDataSetResult() throws Exception{

		if(skip)
			TestUtil.reportDataSetResult(suiteSpectrumBusinessxls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteSpectrumBusinessxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteSpectrumBusinessxls, this.getClass().getSimpleName(), count+2, "PASS");
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
			TestUtil.reportDataSetResult(suiteSpectrumBusinessxls, "Test Cases", TestUtil.getRowNum(suiteSpectrumBusinessxls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteSpectrumBusinessxls, "Test Cases", TestUtil.getRowNum(suiteSpectrumBusinessxls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteSpectrumBusinessxls, this.getClass().getSimpleName()) ;
	}

}
