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

public class TestCase_SpectrumSellSheetLead extends TestSuiteBase{
	/*XWPFDocument document=null;
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
	public void SellSheetLead(
			String fullName,
			String emailID,
			String zipCode,
			String phoneNumber,
			String companyName,
			String browserType,
			String Url
			
	    	) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
		document = new XWPFDocument();
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\lpBussiness_SpectrumSellSheetLead "+" "+fullName+" ,"+zipCode+"_"+browserType+".docx"));
	
		   Log.info("================================ Executing TestCase_SpectrumSellSheetLead for Test Data: "+fullName+" ,"+zipCode+"_"+browserType+" ======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================

	
		   openBrowser(browserType);
			navigate(Url);
			//waitForTextToBeDisplayed("lpbusiness_txt_thankyouPageContact_css", "888.692.8635");
			getScreen(document, fos, "lpBussiness_SpectrumSellSheetLead_Home ");
			input("lpbusiness_SpectrumSellSheetLead_txtbx_fullName_id", fullName);
			input("lpbusiness_SpectrumSellSheetLead_txtbx_Email_id", emailID);
			input("lpbusiness_SpectrumSellSheetLead_txtbx_zipCode_id", zipCode);
			input("lpbusiness_SpectrumSellSheetLead_txtbx_companyName_id", companyName);
			//input("lpbusiness_SpectrumSellSheetLead_txtbx_phoneNumber_id", phoneNumber);
			click("lpbusiness_SpectrumSellSheetLead_txtbx_Getpricingsheet_xpath");
			waitForTextToBeDisplayed("lpbusiness_SpectrumSellSheetLead_txt_contactNumber_css", "888.692.8635");
			getScreen(document, fos, "lpBussiness_SpectrumServiceability_thankYouPage ");
			//verifyURL(CONFIG.getProperty("LPBussiness_SpectrumThankYouPage"));
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

