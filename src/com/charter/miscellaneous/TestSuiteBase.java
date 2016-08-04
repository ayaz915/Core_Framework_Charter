package com.charter.miscellaneous;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import com.charter.base.TestBase;
import com.charter.util.TestUtil;

public class TestSuiteBase extends TestBase{

	// check if the suite ex has to be skiped
		@BeforeSuite
		public void checkSuiteSkip() throws Exception{
			initialize();
			Log.info("Checking Runmode of Miscellaneous suite");
			if(!TestUtil.isSuiteRunnable(suiteXls, "Miscellaneous suite")){
				Log.info("Skipped Miscellaneous suite as the runmode was set to NO");
				throw new SkipException("Runmode of Miscellaneous suite set to no. So Skipping all tests in Miscellaneous suite");
			}
			
		}
}