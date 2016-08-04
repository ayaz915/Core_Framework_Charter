package com.charter.suiteLP;

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

public class TestCase_lfo7nLFO extends TestSuiteBase {
	
/*	XWPFDocument document=null;
	FileOutputStream fos= null;*/

	String runmodes[]=null;
	static int count=-1;

	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(suiteLPxls,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suiteLPxls, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void LFO7n(
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
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\LP lfo7n_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
	
		   Log.info("================================ Executing TestCase_lfo7nLFO for Test Data: "+streetAddress +"  "+zipCode+"_"+browserType+"======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================
		 		
		   openBrowser(browserType);
		   //setUp();
		   navigate(Url);
			waitForTextToBeDisplayed("lp_number_id", "1-877-244-0328");
			verifyPhoneNumber("lp_number_id", "1-877-244-0328");
			//checkForBrokenLinks();
			//captureScreenshot("lp_lfo7nLFO_home");
			getScreen(document, fos, "lp_lfo7nLFO_home");
			input("lp_lfo7nLFO_txtbx_streetAddress_id", streetAddress);
			input("lp_lfo7nLFO_txtbx_zipCode_id", zipCode);
			click("lp_lfo7nLFO_btn_go_id");
			wait1();
			checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
			
			if(driver.getCurrentUrl().contains(CONFIG.getProperty("AddressNotMatch_com"))){
				//waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
				//verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
				//captureScreenshot("com_AddressNotMatchPage");
				//getScreen(document, fos, "com_AddressNotMatchPage");
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
				//waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
				//verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
				//captureScreenshot("com_MultipleAddressPage");
				//getScreen(document, fos, "com_MultipleAddressPage");
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
				//waitForTextToBeDisplayed("txt_number_xpath", "1-877-684-9760");
				//verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
				//captureScreenshot("com_AddressclarificationPage");
				//getScreen(document, fos, "com_AddressclarificationPage");
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
				//waitForTextToBeDisplayed("txt_number_xpath","1-877-684-9760");
				//verifyPhoneNumber("txt_number_xpath", "1-877-684-9760");
				//captureScreenshot("com_RetrieveCartPage");
				//getScreen(document, fos, "com_RetrieveCartPage");
				  Log.info("Clicking on Proceed with new order");
				  click("lnk_startNewOrde_id");
				
				 wait1();
			}
			else{
				Log.info("Retrieve Cart window is not present");
			}	
			
			
			waitForTextToBeDisplayed("txt_number_xpath", "1-877-244-0328");
			verifyPhoneNumber("txt_number_xpath", "1-877-244-0328");
			
			getScreen(document, fos, "lp_lfo7nLFO_StoreFrontPage");
			closeBrowser();
		
		
//===============================================================================================================================================		
	
	}
	
	@AfterMethod
	public void reportDataSetResult() throws Exception{
		if(skip)
			TestUtil.reportDataSetResult(suiteLPxls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteLPxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteLPxls, this.getClass().getSimpleName(), count+2, "PASS");
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
			TestUtil.reportDataSetResult(suiteLPxls, "Test Cases", TestUtil.getRowNum(suiteLPxls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteLPxls, "Test Cases", TestUtil.getRowNum(suiteLPxls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteLPxls, this.getClass().getSimpleName()) ;
	}
	
	
	
}
