package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class RegisterPage {
	
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	private	By firstName=By.id("input-firstname");
	private	By lastName=By.id("input-lastname");
	private	By email=	By.id("input-email");
	private	By telephone=By.id("input-telephone");
	private	By password=By.id("input-password");
	private	By confirmpassword=By.id("input-confirm");
	
	
	private	By agreeCheckBox=By.xpath("//input[@name='agree']");
	private By continueButton=By.xpath("//input[@value='Continue' ]");
	private By subscribeYes=By.xpath("//label[@class='radio-inline']/input[@value='1']");
	private By subscribeNo=By.xpath("//label[@class='radio-inline']/input[@value='0']");
	
	private By registerSucessMesg=By.cssSelector("div#content h1");
	private By logoutLink=By.linkText("Logout");
	private By registerLink=By.linkText("Register");

	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	
	public boolean registerUser(String firstName,String lastName,String email,String telephone,String password,String subscribe) {
		eleUtil.waitForElementVisible(this.firstName,TimeUtil.DEFAULT_TIME_OUT).sendKeys(firstName);
		eleUtil.dosendKey(this.lastName, lastName);
		eleUtil.dosendKey(this.email,email);
		eleUtil.dosendKey(this.telephone, telephone);
		eleUtil.dosendKey(this.password, password);
		eleUtil.dosendKey(this.confirmpassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String successMesg=eleUtil.waitForElementVisible(registerSucessMesg, TimeUtil.DEFAULT_TIME_OUT).getText();
		System.out.println(successMesg);
		
		if(successMesg.contains(AppConstants.ACCOUNT_REGISTER_SUCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		
		else {
			eleUtil.doClick(registerLink);
		}
		return false;
		
		
		
		
	}
	

}
