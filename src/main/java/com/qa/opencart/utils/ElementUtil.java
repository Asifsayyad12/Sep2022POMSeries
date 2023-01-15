package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
private WebDriver driver;  
private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {  //parameterized constructor
		this.driver=driver;
		jsUtil=new JavaScriptUtil(driver);
	}

	public  WebElement getElement(By locator) {
		WebElement ele=driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
		return ele;
	}
	
	public void dosendKey(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}
	
	public  void doClick(By locator) {
		getElement(locator).click();
	}
	public String doGetElementTextt(By locator) {
		return getElement(locator).getText();
		
	}
	public  String doGetAttributeValue(By locator,String name) {
		return getElement(locator).getAttribute(name);
	}
	
	public void doClickOnElement(By locator ,String linkText) {
		List<WebElement> linklist=driver.findElements(locator);
		System.out.println("Total link :"+linklist.size());
		
		for(WebElement e:linklist) {
		String text=e.getText();
		System.out.println(text);
		if(text.contains(linkText)) {
			e.click();
			break;
		}
	}

}
	//*****IsDisplayed**********
	
	public  boolean doISDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public  List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public boolean isSingleElementExit(By locator) {
		int actCount = getElements(locator).size();
		System.out.println("actual count of element==" + actCount);
		if (actCount == 1) {
			return true;
		} else {
			return false;
	}

}

	public boolean isTwoElementExit(By locator) { // if two logo or search field are there
		int actCount = getElements(locator).size();
		System.out.println("actual count of element==" + actCount);
		if (actCount == 2) {
			return true;
		} else {
			return false;
	}

}
	public boolean isMultipleElementExit(By locator) { // method overloading  //dont know the count
		int actCount = getElements(locator).size();
		System.out.println("actual count of element==" + actCount);
		if (actCount > 1) {
			return true;
		} else {
			return false;
	}

}

	public boolean isMultipleElementExit(By locator, int excElementCount) { // if Multiple logo or search field
																					// are there  //count know
		int actCount = getElements(locator).size();
		System.out.println("actual count of element==" + actCount);
		if (actCount == excElementCount) {
			return true;
		} else {
			return false;
		}

	}
	
	public int totalElementCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElementTextList(By locator) { // all element captured & give me list
		List<WebElement> elelist = getElements(locator);
		List<String> eleTextList = new ArrayList<String>(); // to store

		for (WebElement e : elelist) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}
	
	
	//*********************DropDown Utills-- Select based drop down*******************//
	
	public  void doSelectDropDownByValue(By locator,String value) {
		Select select= new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public  void doSelectDropDownByVisibleText(By locator, String text) {
		Select select=new Select(getElement(locator));
		select.selectByVisibleText(text);
	}
	
	public  void doSelectDropDownByIndex(By locator, int index) {
		Select select=new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	public void doSelectDropDownValue(By locator, String value) {
		Select select = new Select(getElement(locator));

		List<WebElement> OptionList = select.getOptions();

		for (WebElement e : OptionList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}

		}

	}
	//********************Action Class*********************//
public  void selectSubMenu(String htmltag,String parentMenu, String childMenu) throws InterruptedException {
		
		By parentMenuLocator=By.xpath("//"+htmltag+"[text()='"+parentMenu+"']");
		By childMenuLocator=By.xpath("//"+htmltag+"[text()='"+childMenu+"']");
		
		WebElement parentMenuElement=driver.findElement(parentMenuLocator);
		
		Actions act=new Actions(driver);   
		act.moveToElement(parentMenuElement).build().perform();
		
		Thread.sleep(2000);
		
		doClick(childMenuLocator);
		
	}
	
	public void doActionSendKey(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}
	
	//***************Wait Utils***************************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 *  This does notnecessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public  WebElement  waitForElementPresence(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * 	An expection for checking that an element is present on the DOM the page and Visible.
	 *	Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public  WebElement  waitForElementVisible(By locator,int timeOut) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));   
	}
	
	
	public String waitForTitleContains(String titlrFractionValue,int timeout) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		if(wait.until(ExpectedConditions.titleContains(titlrFractionValue))){
			return driver.getTitle();
		}
		else {
			System.out.println("Expected tite is not visible");
			return null;
		}
	}
	
	public String waitForTitleIs(String titleVal,int timeout) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		if(wait.until(ExpectedConditions.titleContains(titleVal))){
			return driver.getTitle();
		}
		else {
			System.out.println("Expected tite is not visible");
			return null;
		}
	}
	
	//URL
		public  String waitForUrlContains(String UrlFractionValue,int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			if(wait.until(ExpectedConditions.urlContains(UrlFractionValue))){
				return driver.getCurrentUrl();
			}
			else {
				System.out.println("Expected URL is not visible");
				return null;
			}
		}
		
		public String waitForUrl(String UrlValue,int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			if(wait.until(ExpectedConditions.urlToBe(UrlValue))){
				return driver.getCurrentUrl();
			}
			else {
				System.out.println("Expected URL is not visible");
				return null;
			}
		}

		//Alert
		
		//Fluent
		public  Alert waitForAlertPresentAndSwitchFluentWait(int timeout,int intervalTime) {
			Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofSeconds(intervalTime))
					.ignoring(NoAlertPresentException.class)
					.withMessage("Alert not found on the page");
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		//WD
		public  Alert waitForAlertPresentAndSwitch(int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public  String getAlertText(int timeout) {
			return waitForAlertPresentAndSwitch(timeout).getText();
		}
		
		public  void acceptAlert(int timeout) {
			waitForAlertPresentAndSwitch(timeout).accept();
		}

		public  void dismissAlert(int timeout) {
			waitForAlertPresentAndSwitch(timeout).dismiss();
		}
		public  void alertSendKeys(int timeout ,String value) {
			waitForAlertPresentAndSwitch(timeout).sendKeys(value);
		}
		
		//Frame
		
		public  WebDriver waitForFramePresentAndSwitch(int frameIndex,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}
		
		public WebDriver waitForFramePresentAndSwitch(By frameLocator,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		public  WebDriver waitForFramePresentAndSwitch(WebElement frameElement,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}
		
		public  WebDriver waitForFramePresentAndSwitch(String nameOrID ,int timeOut) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
		}

		/**
		 * An expectation for checking that all elements present on the web page that match the locatorare visible.
		 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0..
		 * @param locator
		 * @param timeout
		 * default interval time ==500ms
		 * @return 
		 * @return
		 */
		
		public List<WebElement> waitForElementsVisible(By locator,int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locatorare visible.
		 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeout
		 * @param intervalTime
		 * @return
		 */
		
		public List<WebElement> waitForElementsVisible(By locator,int timeout,long intervalTime) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofSeconds(intervalTime));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		
		public List<WebElement> waitForElementsPresence(By locator,int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeout
		 */
		public void clickElementWhenReady(By locator,int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		
		/**
		 * An expectation for checking an element is visible and enabled such that you can click it.
		 * @param locator
		 * @param timeout
		 */
		public void clickElementWhenReady(By locator,int timeout, int intervalTime) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofSeconds(intervalTime));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		
		
		public WebElement waitForElementToBeVisibleWithFluentWait(By locator,int timeout,int intervalTime) {
			
			Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
									.withTimeout(Duration.ofSeconds(timeout))
									.pollingEvery(Duration.ofSeconds(intervalTime))
									.ignoring(ElementNotInteractableException.class)
									.ignoring(NoSuchElementException.class)
									.ignoring(StaleElementReferenceException.class)
									.withMessage("Element not found on the page");
			
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
		}
		
		
		public WebElement waitForElementToBeVisible(By locator,int timeout,int intervalTime) {
			
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofSeconds(intervalTime));
			wait.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class)
				.withMessage("Element not found on the page");

			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
		}
		
		public  void wiatForPageLoad(int timeOut) {
			long endTime=System.currentTimeMillis()+timeOut;
			
			while(System.currentTimeMillis()<endTime) {
				JavascriptExecutor js= (JavascriptExecutor)driver;
				String pageState=js.executeScript("return document.readyState").toString();
					if(pageState.equals("complete")) {
						System.out.println("Page is fully loaded now");
						break;
					}
				
				
			}
			
		}
	
}


