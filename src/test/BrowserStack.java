package test;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserStack {  

  private WebDriver driver;  

  @BeforeClass
  public void setUp() throws Exception {
    DesiredCapabilities capability = new DesiredCapabilities();
    capability.setPlatform(Platform.WINDOWS);
    capability.setCapability("build", "TestNG - Sample");
    
    driver = new RemoteWebDriver(
      new URL("http://pss12:1qLdNvZ47aQgJe8TfT4g@hub.browserstack.com/wd/hub"),
      capability
    );
  }  

  @Test
  public void testSimple() throws Exception {
    driver.get("http://www.google.com");
    System.out.println("Page title is: " + driver.getTitle());
    WebElement element = driver.findElement(By.name("q"));
    element.sendKeys("BrowserStack");
    element.submit();
    driver = new Augmenter().augment(driver);
    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(srcFile, new File("Screenshot.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @AfterClass  
  public void tearDown() throws Exception {  
    driver.quit();  
  } 

}    