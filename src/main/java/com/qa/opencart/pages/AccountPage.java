package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By search=By.xpath("//input[@name='search']");
	private By searchIcon=By.cssSelector("div#search button");
	private By logoutLink=By.linkText("Logout");
	private By accSecHeaders=By.cssSelector("div#content h2");
	
	
	public AccountPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		//return driver.getTitle();
		return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE,TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public String getAccPageURL() {
		//return driver.getCurrentUrl();
		return eleUtil.waitForUrlContains(AppConstants.ACC_PAGE_FRACTION_URL,TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public boolean isSearchExist() {
		//return driver.findElement(search).isDisplayed();
		return eleUtil.waitForElementVisible(search, TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}

	public boolean isLogoutExist() {
		//return driver.findElement(logoutLink).isDisplayed();
		return eleUtil.waitForElementVisible(logoutLink, TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountPagesectionHeaders() {
	//	List<WebElement> searchHeaderList = driver.findElements(accSecHeaders);
		List<WebElement> searchHeaderList =eleUtil.waitForElementsVisible(accSecHeaders, TimeUtil.DEFAULT_TIME_OUT);
		
		List<String> searchHeaderValueList =new ArrayList<String>();

		for (WebElement e : searchHeaderList) {
			String text = e.getText();
			searchHeaderValueList.add(text);
		}
		return searchHeaderValueList;

	}

	public ResultPage performSearch(String productName) {
		System.out.println("product search for :"+ productName);
		
		if(isSearchExist()) {
			eleUtil.dosendKey(search, productName);
			eleUtil.doClick(searchIcon);
			return new ResultPage(driver);    //TDD
		}
		return null;
		
	}
}
