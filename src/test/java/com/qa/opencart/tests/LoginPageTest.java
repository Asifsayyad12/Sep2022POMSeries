package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic -100: Design login page open cart shopping application")
@Story("Us-101: Create login page functinality for open cart login page")
public class LoginPageTest extends BaseTest {
	
	@Description("login page title test")
	@Severity(SeverityLevel.TRIVIAL)
	@Test
	public void loginPageTitleTest() {
	String title=loginpage.getLoginPageTitle();
	System.out.println("login Page Title"+ title);
	//Assert.assertEquals(title, "Account Login");
	Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE,AppError.NO_TITLE_MATCHED);
	}
	
	
	@Description("login page url test")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
	String url=	loginpage.getLoginPageUrl();
	System.out.println("login Page Url"+ url );
	//Assert.assertTrue(url.contains("route=account/login"));
	Assert.assertTrue(url.contains(AppConstants.LOGIN_PAGE_FRACTION_URL),AppError.NO_URL_MATCHED);
	}
	
	
	@Description(" forgot password link on login page test")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginpage.isforgotPWLinkExist());
	}
	
	
	@Description(" User is ableto login on login page")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		//Assert.assertTrue(loginpage.doLogin("aasifsayyad0404@gmail.com","Selenium@123" ),AppError.LOGIN_UNSUCCESSFUL);
		//accPage=loginpage.doLogin("aasifsayyad0404@gmail.com","Selenium@123");
		accPage=loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutExist(),AppError.LOGIN_UNSUCCESSFUL);
	}
	
	

}
