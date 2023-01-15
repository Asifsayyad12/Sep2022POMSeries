package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegPage;
import com.qa.opencart.pages.ResultPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	protected LoginPage loginpage;
	protected AccountPage accPage;
	protected ResultPage resultPage;
	protected ProductInfoPage productInfoPage;
	
	protected SoftAssert softassert;
	
	protected Properties prop;
	
	protected RegPage regPage;
	
	@BeforeTest
	public void setup() {
		df=new DriverFactory();
		prop=df.intiProp();
		driver=df.initDriver(prop);
		loginpage=new LoginPage(driver);
		softassert=new SoftAssert();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
