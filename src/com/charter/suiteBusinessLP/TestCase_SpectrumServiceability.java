package com.charter.suiteBusinessLP;

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

public class TestCase_SpectrumServiceability extends TestSuiteBase{
/*	XWPFDocument document=null;
	FileOutputStream fos= null;*/
	
	String runmodes[]=null;
	static int count=-1;

	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(suitebusinessLP,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suitebusinessLP, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void Serviceability(
						String businessName,
						String zipCode,
						String browserType,
						String IndividualName,
						String email,
						String Phone,
						String Url
							) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\lpBussiness_SpectrumServiceability "+IndividualName+" ,"+zipCode+"_"+browserType+".docx"));
	
		   Log.info("================================ Executing TestCase_SpectrumServiceability for Test Data: "+IndividualName+" ,"+zipCode+"_"+browserType+" ======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================

	
		   openBrowser(browserType);
			navigate(Url);
			waitForTextToBeDisplayed("lpbusiness_txt_homePageContact_css", "855.251.0817");
			getScreen(document, fos, "lpBussiness_SpectrumServiceability_Home");
			input("lpbusiness_txtbx_BusinessName_id", businessName);
			input("lpbusiness_txtbx_IndividualName_id", IndividualName);
			input("lpbusiness_txtbx_zipCode_id", zipCode);
			input("lpbusiness_txtbx_Emailid_id", email);
			input("lpbusiness_txtbx_PhoneNo_id", Phone);
			click("lpbusiness_btn_GetStarted_id");
			waitForTextToBeDisplayed("lpbusiness_txt_thankyouPageContact_css", "888.692.8635");
			getScreen(document, fos, "lpBussiness_SpectrumServiceability_thankYouPage");
			verifyURL(CONFIG.getProperty("LPBussiness_SpectrumThankYouPage"));
			closeBrowser();

		
//===============================================================================================================================================		
	
	}
	

	@AfterMethod
	public void reportDataSetResult() throws Exception{
		if(skip)
			TestUtil.reportDataSetResult(suitebusinessLP, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suitebusinessLP, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suitebusinessLP, this.getClass().getSimpleName(), count+2, "PASS");
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
			TestUtil.reportDataSetResult(suitebusinessLP, "Test Cases", TestUtil.getRowNum(suitebusinessLP,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suitebusinessLP, "Test Cases", TestUtil.getRowNum(suitebusinessLP,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suitebusinessLP, this.getClass().getSimpleName()) ;
	}
	
	
	
}

