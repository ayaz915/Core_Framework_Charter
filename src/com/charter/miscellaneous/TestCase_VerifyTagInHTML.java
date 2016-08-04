package com.charter.miscellaneous;
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

public class TestCase_VerifyTagInHTML extends TestSuiteBase{
	

	String runmodes[]=null;
	static int count=-1;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
		
		if(!TestUtil.isTestCaseRunnable(suiteMiscellaneousxls,this.getClass().getSimpleName())){
			Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		// load the runmodes off the tests
		runmodes=TestUtil.getDataSetRunmodes(suiteMiscellaneousxls, this.getClass().getSimpleName());
	}
	
	@Test(dataProvider="getTestData")
	public void mobileAntiSat(
						String Url,
						String browserType,
						String tagName
							) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		
	
		   Log.info("================================ Executing TestCase_VerifyTagInHTML for Test Data: "+Url +"  "+browserType+"  "+tagName+"======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================

		   Log.info("Running for "+Url);
		   verifyTagInHTML(Url, tagName);
		   
		    /*openBrowser(browserType);
			navigate(Url);
	
			if(!driver.getCurrentUrl().equals(Url)){
				Log.info(driver.getCurrentUrl());
				navigate(Url);
				Log.info(driver.getCurrentUrl());
				}
			
			verifyTagInHTML(tagName);
			closeBrowser();*/
		
		
//===============================================================================================================================================		
	
	}
	
	@AfterMethod
	public void reportDataSetResult(){
		if(skip)
			TestUtil.reportDataSetResult(suiteMiscellaneousxls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail){
			isTestPass=false;
			
			TestUtil.reportDataSetResult(suiteMiscellaneousxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteMiscellaneousxls, this.getClass().getSimpleName(), count+2, "PASS");
		
		skip=false;
		fail=false;	
	}
	@AfterTest
	public void reportTestResult(){
		if(isTestPass)
			TestUtil.reportDataSetResult(suiteMiscellaneousxls, "Test Cases", TestUtil.getRowNum(suiteMiscellaneousxls,this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResult(suiteMiscellaneousxls, "Test Cases", TestUtil.getRowNum(suiteMiscellaneousxls,this.getClass().getSimpleName()), "FAIL");
	}
	@DataProvider
	public Object[][] getTestData(){
		return TestUtil.getData(suiteMiscellaneousxls, this.getClass().getSimpleName()) ;
	}
	
	
	
}

