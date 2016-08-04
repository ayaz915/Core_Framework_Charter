package com.charter.suiteDirectSales;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.charter.util.TestUtil;

public class TestCase_DirectSaleNonAutomated extends TestSuiteBase{
	/*XWPFDocument document=null;
	FileOutputStream fos= null;*/
	
	String runmodes[]=null;
	static int count=-1;
	// Runmode of test case in a suite
			@BeforeTest
			public void checkTestSkip(){
				
				if(!TestUtil.isTestCaseRunnable(suiteDirectSalesxls,this.getClass().getSimpleName())){
					Log.info("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//logs
					throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
				}
				runmodes=TestUtil.getDataSetRunmodes(suiteDirectSalesxls, this.getClass().getSimpleName());
			}
			
			@Test(dataProvider="getTestData")
			public void nonAutomatedBuyFlow(
					String browserType,
					String streetAddress,
					String zipCode,
					String packageID,
					String monthlyCost,
					String Url,
					String TV,
					String Internet,
					String Phone
								
									) throws Exception{
				// test the runmode of current dataset
				count++;
				if(!runmodes[count].equalsIgnoreCase("Y")){
					skip=true;
					throw new SkipException("Runmode for test set data set to no "+count);
				}
				
				document = new XWPFDocument();
				fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\Documented Screenshots\\DirectSalesNonAutomated_"+streetAddress+" ,"+zipCode+"_"+browserType+".docx"));
			
				   Log.info("Executing TestCase_DirectSalesNonAutomated for Test Data: "+streetAddress +"  "+zipCode+"_"+browserType+" ==================================");
				   Log.info("=====================================================================");
				
				
				
		//======================================WebDriver Code ================================================================
			
				   //setUp();
				  openBrowser(browserType);
				   navigate(Url);
				   	
				   if(driver.getTitle().equals("Charter Communications Login")){
					   fluentWait("ds_txtbx_userName_id",60, 5);
						//captureScreenshot("ds_EmployeeSingleSignOn");
						getScreen(document, fos, "ds_EmployeeSingleSignOn");
						input("ds_txtbx_userName_id", "ahasan");
						input("ds_txtbx_password_id", "Charter*12345");
						click("ds_btn_login_name"); 
				   }
					fluentWait("ds_txtbx_streetAddress_id",60, 5);
					//captureScreenshot("ds_Localization");
					getScreen(document, fos, "ds_Localization");
					input("ds_txtbx_streetAddress_id", streetAddress);
					input("ds_txtbx_zipCode_id", zipCode);
					input("ds_txtbx_salesid_id", "12345");
					click("ds_ckbx_customerpresentyes_id");
					verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
					scrollPage();
					click("ds_btn_continuegetonlineonlydeals_id");
					wait1();
					
					checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
					
					if(driver.getCurrentUrl().contains(CONFIG.getProperty("AddressNotMatch_com"))){
						waitForTextToBeDisplayed("ds_txt_number_xpath", "1-888-335-9908");
						verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
						//captureScreenshot("ds_AddressNotMatch");
						getScreen(document, fos, "ds_AddressNotMatch");
						Log.info("AddressNotMatch Page is present, now handling it");	
						click("ds_ckbx_yes_xpath");
						click("ds_btn_conti_xpath");
						wait1();
						
					}
					else{
						Log.info("Address is Matched ");
					}
					checkForLacalizationError(CONFIG.getProperty("LacalizationError_com"));
					Log.info("checking for Multiple Address Page");
					if(driver.getCurrentUrl().contains(CONFIG.getProperty("MultipleAddress_com"))){
						
						Log.info("Multiple Address Page is present");
						waitForTextToBeDisplayed("txt_number_xpath", "1-888-335-9908");
						verifyPhoneNumber("txt_number_xpath", "1-888-335-9908");
						//captureScreenshot("ds_MultipleAddressPage");
						getScreen(document, fos, "ds_MultipleAddressPage");
						  Log.info("Choosing first address");
						 click("ds_ckbx_selectAdd0_xpath");
						 wait1();
					}
					else{
						Log.info("Multiple Address Page is not present");
					}
					Log.info("checking for address clarification Page");
					if(driver.getCurrentUrl().contains(CONFIG.getProperty("Addressclarification_com"))){
						Log.info("address clarification Page is present");
						waitForTextToBeDisplayed("ds_txt_number_xpath", "1-888-335-9908");
						verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
						//captureScreenshot("ds_Addressclarification");
						getScreen(document, fos, "ds_Addressclarification");
						  Log.info("Clicking on view offers");
						  click("ds_btn_prospect_view_offers_xpath");
						  wait1();
					}
					else{
						Log.info("address clarification window is not present");
					} 
		
					Log.info("checking for Retrieve Cart window");
					if(driver.getCurrentUrl().contains(CONFIG.getProperty("RetrieveCart_com"))){
						Log.info("Retrieve Cart window is present");
						waitForTextToBeDisplayed("txt_number_xpath", "1-888-335-9908");
						verifyPhoneNumber("txt_number_xpath", "1-888-335-9908");
						//captureScreenshot("ds_RetrieveCartPage");
						getScreen(document, fos, "ds_RetrieveCartPage");
						  Log.info("Clicking on Proceed with new order");
						  click("lnk_startNewOrde_id");
						  wait1();
					}
					else{
						Log.info("Retrieve Cart window is not present");
					}	
					
					    
					   waitForTextToBeDisplayed("ds_txt_number_xpath", "1-888-335-9908");
					   verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
					    waitForTextToBeDisplayed("ds_btn_registration-submit_id", "CONTINUE");
					   // captureScreenshot("ds_credit-risk-assessment");
					    getScreen(document, fos, "ds_credit-risk-assessment");
						input("ds_txtbx_firstname_id","TestAyaz");
						input("ds_txtbx_lastname_id","TestCH0107");
						click("ds_ckbx_phoneType_id");
						input("ds_txtbx_phoneNumber_id","1234567890");
						input("ds_txtbx_email_id","ayaz.hasan@charter.com");
						input("ds_txtbx_confirmEmail_id","ayaz.hasan@charter.com");
						input("ds_txtbx_agentEmail_id","ayaz.hasan@charter.com");
						input("ds_txtbx_confirmagentEmail_id","ayaz.hasan@charter.com");
						click("ds_ckbx_address-changed-yes_id");
						click("ds_btn_registration-submit_id");
						wait1();
						
						fluentWait("btn_choosePlan_xpath",60,2);
						waitForTextToBeDisplayed("ds_txt_number_xpath", "1-888-335-9908");
						verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
						 verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
						// captureScreenshot("ds_StoreFront");
						 click("ds_link_triplePlayOffers_xpath");
						 click("ds_link_doublePlayOffers_xpath");
						 click("ds_link_singlePlayOffers_xpath");
						 getScreen(document, fos, "ds_StoreFront");
						 choosePackage(packageID);
						 //click("ds_btn_choosePlan_xpath");
						 wait1();
						 
						 waitForTextToBeDisplayed("ds_btn_continue3_xpath", "CONTINUE");
						 verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
						 getScreen(document, fos, "ds_CustomizeYourOrder");
						    handleCustomizationResi(TV, Internet, Phone);
							waitForPageLoad(5000);
					     	click("ds_btn_continue3_xpath");
					    	
					     	wait1();
					     	fluentWait("ds_btn_registration_submit_id",60,2);
					     	//captureScreenshot("ds_EnterYourInfo");
					     	 getScreen(document, fos, "ds_EnterYourInfo");
					     	 if(!TV.isEmpty()&&!Internet.isEmpty()&&!Phone.isEmpty()) {
					     		selectDropDown("ds_select_installationOption_id","Free Install"); 
					     		click("ds_ckbx_ContractBuyOut_id");
					     	 }
					     	
					     	click("ds_ckbx_termsAgree_id");
					     	verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
					     	//verifyMonthlyCost("ds_txt_monthlyCost_xpath",monthlyCost);
					     	click("ds_btn_registration_submit_id");
					     	wait1();
					     	wait1();
					     	
					     	Log.info("Checking for TermsAndConds");
					     	
							if(driver.getCurrentUrl().contains(CONFIG.getProperty("TermsAndConds_com"))){
							Log.info("TermsAndConds is present");
					     	fluentWait("ds_ckbx_iAgree3_css",60,2);
							//captureScreenshot("ds_TermsConditions");
							getScreen(document, fos, "ds_TermsConditions");
							click("ds_ckbx_iAgree3_css");
							click("ds_ckbx_iAgree4_css");
							click("ds_ckbx_iAgree5_css");
							verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
							click("ds_btn_registration-submit_id");
							}
							else{
								Log.info("TermsAndConds is not present");
							}
							
							wait1();
							fluentWait("ds_txt_monthlyCost_xpath",60,2);
							waitForTextToBeDisplayed("ds_txt_number_xpath", "1-888-335-9908");
							verifyPhoneNumber("ds_txt_number_xpath", "1-888-335-9908");
							verifyMonthlyCost("ds_txt_monthlyCost_xpath",monthlyCost);
							//captureScreenshot("ds_NonScheduleOrderHistory");
							
							getScreen(document, fos, "ds_NonScheduleOrderHistory");
						    closeBrowser();
					    
		//===============================================================================================================================================		
			
			}
			

			@AfterMethod
			public void reportDataSetResult() throws Exception{
				
				if(skip)
					TestUtil.reportDataSetResult(suiteDirectSalesxls, this.getClass().getSimpleName(), count+2, "SKIP");
				else if(fail){
					isTestPass=false;
					
					TestUtil.reportDataSetResult(suiteDirectSalesxls, this.getClass().getSimpleName(), count+2, "FAIL");
				}
				else
					TestUtil.reportDataSetResult(suiteDirectSalesxls, this.getClass().getSimpleName(), count+2, "PASS");
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
					TestUtil.reportDataSetResult(suiteDirectSalesxls, "Test Cases", TestUtil.getRowNum(suiteDirectSalesxls,this.getClass().getSimpleName()), "PASS");
				else
					TestUtil.reportDataSetResult(suiteDirectSalesxls, "Test Cases", TestUtil.getRowNum(suiteDirectSalesxls,this.getClass().getSimpleName()), "FAIL");
			}
			@DataProvider
			public Object[][] getTestData(){
				return TestUtil.getData(suiteDirectSalesxls, this.getClass().getSimpleName()) ;
			}
			
			
			
		}
