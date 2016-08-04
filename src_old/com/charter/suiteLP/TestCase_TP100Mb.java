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

public class TestCase_TP100Mb extends TestSuiteBase {
	
	/*XWPFDocument document=null;
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
	public void TP100Mb(
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
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\LP TP100Mb_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
	
		   Log.info("================================ Executing TestCase_TP100Mb for Test Data: "+streetAddress+" ,"+zipCode+"_"+browserType+" ======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================
		   openBrowser(browserType);
		   navigate(Url);
			//checkForBrokenLinks();
		    waitForTextToBeDisplayed("lp_TP100Mb&TriplePlay_btn_go_id", "GO");
			getScreen(document, fos, "lp_TP100Mb_home");
			input("lp_TP100Mb&TriplePlay_txtbx_streetAddress_id", streetAddress);
			input("lp_TP100Mb&TriplePlay_txtbx_zipCode_id", zipCode);
			click("lp_TP100Mb&TriplePlay_btn_go_id");
			wait1();
			checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
			/*navigate(CONFIG.getProperty("LP_TP100Mb"));
			wait1();
			click("lp_bnr_specialOffer_xpath");
			wait1();*/
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
