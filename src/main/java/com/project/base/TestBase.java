package com.project.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import com.framework.util.ExcelUtils;
import com.framework.util.PropertiesUtils;
import com.project.framework.Common;
import com.project.framework.Constants;
import com.project.framework.GenericEntity;

@Listeners(com.framework.report.TestNgReporter.class)
public class TestBase {

	public static ThreadLocal<WebDriver> delegate = new ThreadLocal<WebDriver>();
	public static WebDriver driver;
	public static GenericEntity gen;
	public static Common com;
	public static PropertiesUtils utils = new PropertiesUtils("src/main/resources/test.properties");
	String test_data_sheet = utils.getProperty("test_data");
	protected ExcelUtils data = new ExcelUtils(test_data_sheet);
	String variableStorePath = utils.getProperty(Constants.TEST_STORE_FILEPATH);

	@BeforeTest
	public void setup() {
		driver = initWebDriver();
		gen = new GenericEntity(driver);
		com = new Common(variableStorePath);

	}

	@AfterTest
	public void tearDown() {
		closeSession();
	}

	public static void initialization(String browserName) {

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", utils.getProperty(Constants.CHROME_DRIVER));
			driver = new ChromeDriver();
		} else if (browserName.equals("Firefox")) {
			System.setProperty("webdriver.chrome.driver", utils.getProperty(String.format(Constants.FIREFOX_DRIVER)));
			driver = new FirefoxDriver();
		} else if (browserName.equals("IE")) {
			System.setProperty("webdriver.chrome.driver", utils.getProperty(String.format(Constants.IE_DRIVER)));
			driver = new InternetExplorerDriver();
		}
		delegate.set(driver);
	}

	public WebDriver initWebDriver() {
		initialization(utils.getProperty(Constants.TEST_BROWSER));
		return delegate.get();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void closeSession() {
		delegate.get().quit();
	}

}
