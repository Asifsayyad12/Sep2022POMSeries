package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1.private By locators
	
	private By emailId=By.id("input-email");
	private By password=By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPWLink= By.linkText("Forgotten Password");
	
	private By registerLink=By.linkText("Register");//Register

	//2.page constructor:
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	//3.Page Actions
	@Step("getting login page title")
	public String getLoginPageTitle() {
		//return driver.getTitle();
		return eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE,TimeUtil.DEFAULT_TIME_OUT);
		
	}
	
	
	@Step("getting login page Url")
	public String getLoginPageUrl() {
		//return driver.getCurrentUrl();
		return eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL,TimeUtil.DEFAULT_TIME_OUT);
	}
	
	@Step("checking forgot pwd link exist")
	public boolean isforgotPWLinkExist() {
		//return driver.findElement(forgotPWLink).isDisplayed();
		return eleUtil.doISDisplayed(forgotPWLink);
	}
	
	
	@Step("login with userName:{0} and password:{1}" )
	public AccountPage doLogin(String un, String Pwd) {
		System.out.println("Cred are "+ un+"   " + Pwd);
//		driver.findElement(emailId).sendKeys(un);
//		driver.findElement(password).sendKeys(Pwd);
//		driver.findElement(loginBtn).click();
		
		//return driver.findElement(By.linkText("Logout")).isDisplayed();
		
		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_TIME_OUT).sendKeys(un);
		eleUtil.dosendKey(password, Pwd);
		eleUtil.doClick(loginBtn);
		return new AccountPage(driver);
	}
	
	@Step("navigating to register page")
	public RegPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegPage(driver);
	}
	
	

}
