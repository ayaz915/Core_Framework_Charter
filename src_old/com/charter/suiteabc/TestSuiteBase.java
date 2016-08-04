package com.charter.suiteabc;
import java.lang.reflect.Method;
import org.testng.ITest;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.charter.base.TestBase;
import com.charter.util.TestParameters;
import com.charter.util.TestUtil;

public abstract class TestSuiteBase extends TestBase implements ITest{

	private String mTestCaseName;
	// check if the suite ex has to be skiped
		@BeforeSuite
		public void checkSuiteSkip() throws Exception{
			initialize();
			Log.info("Checking Runmode of Suite Mobile");
			if(!TestUtil.isSuiteRunnable(suiteXls, "abc suite")){
				Log.info("Skipped abc suite as the runmode was set to NO");
				throw new SkipException("Runmode of Mobile suite set to no. So Skipping all tests in Mobile suite");
			}
			
		}
		
}