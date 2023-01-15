package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tldriver=new ThreadLocal<WebDriver>();
	
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName=prop.getProperty("browser").trim();
		System.out.println(" Browser Name is"+browserName);
		
		
		highlight=prop.getProperty("highlight");
		optionsManager=new OptionsManager(prop);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			//driver=new ChromeDriver(optionsManager.getChromeOptions());
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			//driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			//driver=new EdgeDriver(optionsManager.getEdgeOptions());
			tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}
		else if(browserName.equalsIgnoreCase("safari")) {
			//driver=new SafariDriver();
			tldriver.set(new SafariDriver());
		}
		else {
			System.out.println("Pls Pass the right browser"+browserName);
		}
		
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.get(prop.getProperty("url"));
//		return driver;
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	
	
	//get the local thread copy of the driver
	public synchronized static WebDriver getDriver() {
		return tldriver.get();
	}
	
	
	
	
	
	
	
	
	public Properties intiProp() {
	prop=new Properties();
	try {
		FileInputStream ip=new 	FileInputStream("./src/test/resources/config/config.properties");
		prop.load(ip);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return prop;
		
	}
	
	
	//Screenshot
	
	public static String getScreenshot() {
		File srcFile=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+ "/screenshot/"+System.currentTimeMillis()+".png";
		File deatination=new File(path);
		try {
			FileHandler.copy(srcFile, deatination);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	
	
	
	
}
