package com.charter.Affiliates;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.charter.util.TestUtil;
import com.charter.util.Xls_Reader;

public class Residential extends TestSuiteBase{
	/*XWPFDocument document=null;
	FileOutputStream fos= null;*/
	
	String runmodes[]=null;
	static int count=-1;

	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(suiteAffiliatesxls,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case "+ this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suiteAffiliatesxls, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void nonAutomatedBuyFlow(
			String Url,
			String streetAddress,
			String zipCode,
			String browserType,
			String packageID,
			String monthlyCost,
	        String phoneNumber,
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
		
		String[] output = Url.split("=");
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\Residential Affiliate_"+output[1]+"_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
	
		
		   Log.info("================================ Executing Residential Affiliate for Test Data: "+streetAddress +"  "+zipCode+"_"+browserType+" ======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================

		//setUp();
		openBrowser(browserType);
		//navigate(CONFIG.getProperty("Localization_lfo"));
		navigate(Url);
		//captureScreenshot("lfo_Localization");
		getScreen(document, fos, "lfo_Localization");
		input("txtbx_streetAddress_id", streetAddress);
		input("txtbx_zipCode_id", zipCode);
		click("btn_continue_id");
		wait1();
		
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
		
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("AddressNotMatch_com"))){
			//waitForTextToBeDisplayed("txt_number_xpath", "1-877-312-2691");
  
	//		877-581-3485
		    
			//verifyPhoneNumber("txt_number_xpath", "1-877-312-2691");
			//captureScreenshot("lfo_AddressNotMatch");
			getScreen(document, fos, "lfo_AddressNotMatch");
			Log.info("AddressNotMatch Page is present, now handling it");	
			click("ckbx_yes_xpath");
			click("btn_conti_xpath");
			wait1();		
		}
		else{
			Log.info("Address is Matched ");
		}
		
		checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
		
		Log.info("checking for Multiple Address Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("MultipleAddress_com"))){
			
			Log.info("Multiple Address Page is present");
			//waitForTextToBeDisplayed("txt_number_xpath", "1-877-312-2691");
			//verifyPhoneNumber("txt_number_xpath", "1-877-312-2691");
			//captureScreenshot("lfo_MultipleAddress");
			getScreen(document, fos, "lfo_MultipleAddress");
			
			  Log.info("Choosing first address");
			 click("ckbx_selectAdd0_xpath");
			 wait1();
		}
		else{
			Log.info("Multiple Address Page is not present");
		}
		
		Log.info("checking for address clarification Page");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("Addressclarification_com"))){
			Log.info("address clarification Page is present");
			//waitForTextToBeDisplayed("txt_number_xpath", "1-877-312-2691");
			//verifyPhoneNumber("txt_number_xpath", "1-877-312-2691");
			//captureScreenshot("lfo_Addressclarification");
			getScreen(document, fos, "lfo_Addressclarification");
			  Log.info("Clicking on view offers");
			  click("btn_prospect_view_offers_xpath");
			  wait1();
		}
		else{
			Log.info("address clarification window is not present");
		} 
		
		
		Log.info("checking for Retrieve Cart window");
		if(driver.getCurrentUrl().contains(CONFIG.getProperty("RetrieveCart_com"))){
			Log.info("Retrieve Cart window is present");
			//waitForNumberToBeDisplayed("txt_number_xpath", "1-877-684-9760");
			//verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
			//captureScreenshot("lfo_RetrieveCart");
			getScreen(document, fos, "lfo_RetrieveCart");
			  Log.info("Clicking on Proceed with new order");
			  click("lnk_startNewOrde_id");
			  wait1();
		}
		else{
			Log.info("Retrieve Cart window is not present");
		}	
		fluentWait("btn_choosePlan_xpath",60,2);
		//captureScreenshot("lfo_StoreFront");
		getScreen(document, fos, "lfo_StoreFront");
		 // waitForNumberToBeDisplayed("txt_number_xpath", "1-855-839-4246");
		//verifyPhoneNumber("txt_number_xpath", "1-855-839-4246");
		
		choosePackage(packageID);
		wait1();
		
		    waitForTextToBeDisplayed("ckbx_keepExisting_id", "Keep Existing");
			//captureScreenshot("lfo_CustomizeYourOrder");
			getScreen(document, fos, "lfo_CustomizeYourOrder");
			handleCustomizationResi(TV, Internet, Phone);
			waitForPageLoad(5000);
	     	click("btn_continue3_xpath");
	     	wait1();
	     	
	     	waitForTextToBeDisplayed("btn_registration_submit_id", "SUBMIT ORDER");
			//captureScreenshot("lfo_EnterYourInfo");
			getScreen(document, fos, "lfo_EnterYourInfo");
			input("txtbx_firstname_id","FakeAyaz");
			input("txtbx_lastname_id","TestCH0107");
			//selectDropDown("txtbx_phoneType_xpath", "Home");
			click("ckbx_phoneType_id");  
			input("txtbx_phoneNumber_id","1234567890");
			input("txtbx_email_id","ayaz.hasan@charter.com");
			input("txtbx_confirmEmail_id","ayaz.hasan@charter.com");
			click("ckbx_addressChangedYes_id");
			//click("lfo_ContractBuyout_id");
			//click("ckbx_iAgree2_css");
			//verifyPhoneNumber("txt_number_xpath", "1-855-839-4368");
			//verifyMonthlyCost("txt_monthlyCost_xpath",monthlyCost);
			click("btn_registration_submit_id");
			wait1();
			
			if(driver.getCurrentUrl().contains(CONFIG.getProperty("TermsAndConds_com"))){
				fluentWait("ckbx_iAgree3_name",60, 2);
				getScreen(document, fos, "lfo_TermsConditionsPage");
				click("ckbx_iAgree3_name");
				click("ckbx_iAgree4_name");
				click("ckbx_iAgree5_name");
				click("btn_registration_submit_id");
				wait1();	
			}
			
			fluentWait("txt_monthlyCost_xpath",60, 2);
			//waitForTextToBeDisplayed("txt_number_xpath", "1-877-312-2691");
			//verifyPhoneNumber("txt_number_xpath", "1-877-312-2691");
			
			/*Xls_Reader datatable=new Xls_Reader(System.getProperty("user.dir")+"\\Confirmation Number\\Confirmation Number.xlsx");
			//click("lfo_Close_css");
			String s =getText("lfo_ConfirmationNumber_xpath");
			System.out.println(s);
			int rowCount =datatable.getRowCount("ResiAffiliates");
			
			datatable.setCellData("ResiAffiliates", "Url", rowCount+1, Url);
			datatable.setCellData("ResiAffiliates", "Confirmation Number", rowCount+1, s);*/
			
			//verifyMonthlyCost("txt_monthlyCost_xpath",monthlyCost);
			//captureScreenshot("lfo_NonScheduleOrderHistory");
			getScreen(document, fos, "lfo_NonScheduleOrderHistory");
		    closeBrowser();
		
		
//===============================================================================================================================================		
	
	}
	

	@AfterMethod
	public void reportDataSetResult() throws Exception{
	
		
		if(skip)
			TestUtil.reportDataSetResult(suiteAffiliatesxls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteAffiliatesxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteAffiliatesxls, this.getClass().getSimpleName(), count+2, "PASS");
		
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
			TestUtil.reportDataSetResult(suiteAffiliatesxls, "Test Cases", TestUtil.getRowNum(suiteAffiliatesxls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteAffiliatesxls, "Test Cases", TestUtil.getRowNum(suiteAffiliatesxls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteAffiliatesxls, this.getClass().getSimpleName()) ;
	}
	
	
	
}
