package com.charter.suiteBusinessLP;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.charter.base.TestBase;
import com.charter.util.TestUtil;

public class TestSuiteBase extends TestBase {
    // check if the suite ex has to be skiped
	@BeforeSuite
	public void checkSuiteSkip() throws Exception{
		initialize();
		Log.info("Checking Runmode of Suite BusinessLP");
		if(!TestUtil.isSuiteRunnable(suiteXls, "BusinessLP suite")){
			Log.info("Skipped Suite BusinessLP as the runmode was set to NO");
			throw new SkipException("Runmode of Suite BusinessLP set to no. So Skipping all tests in Suite BusinessLP");
		}
		
	}
}
