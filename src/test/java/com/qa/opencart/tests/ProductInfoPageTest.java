package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetup() {
		accPage=loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}

	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][]{
			{"Macbook","MacBook Pro"},
			{"Macbook", "MacBook Air"},
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Apple","Apple Cinema 30\""}
		};
	}
	
	//TDD
	@Test(dataProvider ="getProductTestData")
	public void productHeaderTest(String searchKey, String mainProductName) {
		resultPage= accPage.performSearch(searchKey);
		productInfoPage= resultPage.selectProduct(mainProductName);
		String actHeader=productInfoPage.getprodutHeader();
		Assert.assertEquals(actHeader, mainProductName);
	}
	
	
	@DataProvider
	public Object[][] getProductImageTestData() {
		return new Object[][]{
			{"Macbook","MacBook Pro",4},
			{"Macbook", "MacBook Air",4},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Apple","Apple Cinema 30\"",6}
		};
	}
	
	
	@Test(dataProvider = "getProductImageTestData")
	public void productImagesTest(String searchKey, String mainProductName,int imageCount ) {
		resultPage= accPage.performSearch(searchKey);
		productInfoPage= resultPage.selectProduct(mainProductName);
		int actImageCount=productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImageCount, imageCount);
	}
	

	
	
	
	@Test
	public void productMetaDataTest() {
		resultPage= accPage.performSearch("Macbook");
		productInfoPage= resultPage.selectProduct("MacBook Pro");
		Map<String,String> actProdInfoMap=productInfoPage.getProductInfotrmation();
		
		softassert.assertEquals(actProdInfoMap.get("Brand"), "Apple");
		softassert.assertEquals(actProdInfoMap.get("Availability"), "In Stock");
		softassert.assertEquals(actProdInfoMap.get("Actial Price"), "$2,000.00");
		softassert.assertEquals(actProdInfoMap.get("Reward Points"), "800");
		softassert.assertAll();
		
	}
	
}
