package com.qa.opencart.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.apache.xmlbeans.impl.store.Path;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.opencart.factory.DriverFactory;

public class ExtentReportListener implements ITestListener {
	
	private static final String OUTPUT_FOLDER="./report/";
	private static final String FILE_NAME="TestExecutionReport.html";
	
	private static ExtentReports extent=init();
	public static ThreadLocal<ExtentTest> test=new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;
	
	public static ExtentReports init() {
		
		java.nio.file.Path path= Paths.get(OUTPUT_FOLDER);
		//if directory exists?
		
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		extentReports=new ExtentReports();
		ExtentSparkReporter reporter=new ExtentSparkReporter(OUTPUT_FOLDER+FILE_NAME);
		reporter.config().setReportName("Oprn Cart Test Automation Results");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("System", "Windo 10");
		extentReports.setSystemInfo("Author", "Asif Sayyad");
		extentReports.setSystemInfo("Build#", "1.2");
		extentReports.setSystemInfo("Team", "OpenCart QA Team");
		extentReports.setSystemInfo("Customer Name", "NAL");
		
		return extentReports;
		
		
	}
	

	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		String qualifiedName=result.getMethod().getQualifiedName();
		int last=qualifiedName.lastIndexOf(".");
		int mid=qualifiedName.substring(0, last).lastIndexOf(".");
		String className=qualifiedName.substring(mid+1, last);
		
		System.out.println(methodName +"started! ");
		ExtentTest extentTest=extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+ "Passed!");
		test.get().pass("Test passed");
		//test.get().pass(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public  synchronized void onTestFailure(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+ "Failed!");
		String methodName=result.getMethod().getMethodName();
		
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(DriverFactory.getScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public  synchronized void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+ "Skipped!");
		String methodName=result.getMethod().getMethodName();
		
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(DriverFactory.getScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
		
	}

	@Override
	public  synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("OnTestFailedButWithinSuccessPercentage for"+ result.getMethod().getMethodName());
		
	}

	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite statred!");
		
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println("Test Suite ending!");
		extent.flush();
		test.remove();
		
	}
	
	private synchronized Date getTime(long millis) {
		Calendar calender=	Calendar.getInstance();
		calender.setTimeInMillis(millis);
		return calender.getTime();
	}
	
	

}
