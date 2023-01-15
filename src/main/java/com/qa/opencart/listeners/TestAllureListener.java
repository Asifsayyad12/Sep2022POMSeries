package com.qa.opencart.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Attachment;

public class TestAllureListener implements ITestListener{
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	//Text attachments for Allure
	
	@Attachment(value="Page screenshot", type="image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
	
	
	//Text attachments for Allure
	@Attachment(value="{0}", type="text/plain")
	public static String saveTextLog(String message) {
		return message;
	}
	
	//HTML attachments for Allure
	@Attachment(value="{0}", type="text/html")
	public static String attach(String html) {
		return html;
	}
	
	
	
	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am im onTestStart method"+ getTestMethodName(iTestResult)+" start");
		
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am im onTestStart method"+ getTestMethodName(iTestResult)+"succeed");
		
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am im onTestStart method"+ getTestMethodName(iTestResult)+"failed");
		Object testClass=iTestResult.getInstance();
		//WebDriver driver=BasePage.getDriver();
		//Allure ScreenSchotRobot & SaveTestLog
		
		if(DriverFactory.getDriver() instanceof WebDriver) {
			System.out.println("Screenshot captured for test case :"+getTestMethodName(iTestResult));
			saveScreenshotPNG(DriverFactory.getDriver());
		}
		//save log on allure
		saveTextLog(getTestMethodName(iTestResult));
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am im onTestStart method"+ getTestMethodName(iTestResult)+"skipped");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
	}

}
