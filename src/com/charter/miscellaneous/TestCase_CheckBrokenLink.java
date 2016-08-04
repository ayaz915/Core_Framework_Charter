package com.charter.miscellaneous;


import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.charter.util.TestUtil;

public class TestCase_CheckBrokenLink extends TestSuiteBase{

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
	public void CheckBrokenLink(
						String Url,
						String browserType
							) throws Exception{
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")){
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		   Log.info("================================ Executing TestCase_CheckBrokenLink for Test Data: "+Url +"  "+browserType+" ======================================================");
		   Log.info("==================================================================================================================================================");
		
		
		
//======================================WebDriver Code ================================================================

		    openBrowser(browserType);
			navigate(Url);
			checkForBrokenLinks();
			closeBrowser();
		
		
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

