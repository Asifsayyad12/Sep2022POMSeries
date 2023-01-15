package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppError;

public class AccountPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
	accPage=loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		String title=accPage.getAccPageTitle();
		System.out.println("Account page Title :"+ title);
		//Assert.assertEquals(title, "My Account");
		Assert.assertEquals(title,AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accPageURLTest() {
		String actURL=accPage.getAccPageURL();
		System.out.println("Account Page URL :"+actURL);
		//Assert.assertTrue(actURL.contains("route=account/account"));
		Assert.assertTrue(actURL.contains(AppConstants.ACC_PAGE_FRACTION_URL),AppError.NO_URL_MATCHED);
	}
	
	@Test
	public void searchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Test
	public void logoutExistTest() {
		Assert.assertTrue(accPage.isLogoutExist());
	}
	
	@Test
	public void accPageHeadersTest() {
	List<String> actHeaderList=	accPage.getAccountPagesectionHeaders();
	Assert.assertEquals(actHeaderList, AppConstants.EXPECTED_ACC_HEADER_LIST);
	}
	
	
	@DataProvider
	public Object[][] getProductName() {
	return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Samsung"},
		};
	}
	

	//TDD
	@Test(dataProvider = "getProductName")
	public void productSearchTest(String productName ) {
		//String productName="macbook";
		resultPage= accPage.performSearch(productName);
		String actTitle=resultPage.getSearchPageTitle(productName);
		System.out.println("Search Page Title :"+actTitle);
		//Assert.assertEquals(actTitle,"Search -");
		softassert.assertEquals(actTitle, AppConstants.SEARCH_PAGE_TITLE +" "+ productName);
		Assert.assertTrue(resultPage.getSearchProductsCount()>0);
		
		}
	
}
