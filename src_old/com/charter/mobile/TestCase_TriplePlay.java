package com.charter.mobile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.testng.ITest;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.charter.util.TestUtil;

import test.TestParameters;

public class TestCase_TriplePlay extends TestSuiteBase {
	
/*	XWPFDocument document=null;
	FileOutputStream fos= null;*/

	String runmodes[]=null;
	static int count=-1;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(suiteMobilexls,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suiteMobilexls, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void TriplePlay(
			String deviceName,
			String streetAddress,
			String zipCode,
			String browserType,
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
		fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\Mobile LP TriplePlay_"+streetAddress+" ,"+zipCode+"_"+deviceName+".docx"));
	
		   Log.info("================================ Executing TestCase_Mobile LP TriplePlay for Test Data: "+streetAddress+" ,"+zipCode+"_"+deviceName+" ======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================

		
		    openMobile(deviceName);
		    navigate(Url);
		    waitForTextToBeDisplayed("lp_TP100Mb&TriplePlay_btn_go_id", "GO");
			getScreen(document, fos, "mobile lp_TriplePlay_home");
			input("lp_TP100Mb&TriplePlay_txtbx_streetAddress_id", streetAddress);
			input("lp_TP100Mb&TriplePlay_txtbx_zipCode_id", zipCode);
			click("lp_TP100Mb&TriplePlay_btn_go_id");
			wait1();
			checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
			closeBrowser();
		
		
//===============================================================================================================================================		
	
	}
	
	
	
	@AfterMethod
	public void reportDataSetResult() throws Exception{
		
		if(skip)
			TestUtil.reportDataSetResult(suiteMobilexls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteMobilexls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteMobilexls, this.getClass().getSimpleName(), count+2, "PASS");
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
			TestUtil.reportDataSetResult(suiteMobilexls, "Test Cases", TestUtil.getRowNum(suiteMobilexls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteMobilexls, "Test Cases", TestUtil.getRowNum(suiteMobilexls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteMobilexls, this.getClass().getSimpleName()) ;
	}

}
