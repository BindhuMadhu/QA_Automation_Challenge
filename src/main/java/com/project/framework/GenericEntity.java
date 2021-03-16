package com.project.framework;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import com.framework.util.PropertiesUtils;

public class GenericEntity {

	public static WebDriver driver;
	PropertiesUtils utils;
	public static long PAGE_LOAD_TIMEOUT = 20;
	Random random = new Random();

	public GenericEntity(WebDriver driver) {
		this.driver = driver;
	}

	public void launch(String url) {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);

		Reporter.log(
				"============================================launching Url============================================",
				true);
		driver.get(url);
	}
	/*
	 * Clicking on WebElements
	 *
	 */

	public void waitForElementVisibleByXpath(By locator) {
		try {
			String timeout = utils.getProperty("Time");
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(timeout));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
		}
	}

	public void click(By locator) {
		WebElement element = driver.findElement(locator);
		element.click();
		Reporter.log("Clicked on Element having xpath : " + locator, true);
	}

	public void enterValueinFieldByXpath(By locator, String value) {
		waitForElementVisibleByXpath(locator);
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		element.clear();
		element.sendKeys(value);
		Reporter.log("Entered value :\"" + value + "\" in the element : " + locator, true);
	}

	public void waitAndClickByXpath(By locator) {
		waitForElementClickableByXpath(locator, "5");
		try {
			WebElement element = driver.findElement(locator);
			highlightElement(element);
			checkStaleElement(element, 10);
			element.click();
		} catch (StaleElementReferenceException e) {
		} catch (UnhandledAlertException e) {
			driver.switchTo().alert().dismiss();
		}
		Reporter.log("Clicked on Element having xpath : " + locator, true);
	}

	public void waitAndClickByXpath(String locator) {
		waitForElementClickableByXpath(locator, "5");
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			highlightElement(element);
			checkStaleElement(element, 10);
			element.click();
		} catch (StaleElementReferenceException e) {
		} catch (UnhandledAlertException e) {
			driver.switchTo().alert().dismiss();
		}
		Reporter.log("Clicked on Element having xpath : " + locator, true);
	}

	public void waitForElementClickableByXpath(String locator, String sec) {
		int seconds = Integer.parseInt(sec);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));

	}

	public void highlightElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid green'", element);
	}

	public void checkStaleElement(WebElement element, int timeout) {
		new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementClickableByXpath(By locator, String sec) {
		int seconds = Integer.parseInt(sec);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(locator));

	}

	/*
	 * Handling Select Tags
	 */

	public void selectOption(By locator, String visibleOption) {
		WebElement select = driver.findElement(locator);
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (visibleOption.equals(option.getText()))
				option.click();
			Reporter.log("Clicked on option :" + visibleOption, true);
		}
	}

	public void selectOptionByVisibleText(By locator, String visibleText) {
		waitForElementVisibleByXpath(locator);
		WebElement element = driver.findElement(locator);
		Select drop = new Select(element);
		drop.selectByVisibleText(visibleText);
		Reporter.log("Option Selected :" + visibleText, true);
	}

	public void selectOptionByIndex(By locator, String index) {
		waitForElementVisibleByXpath(locator);
		int idx = Integer.parseInt(index);
		WebElement element = driver.findElement(locator);
		Select drop = new Select(element);
		drop.selectByIndex(idx);
		Reporter.log("Option Selected :" + drop.getFirstSelectedOption().toString(), true);
	}

	/*
	 * Getting Data from webelements
	 */

	public String getFieldTextbyXpath(By locator) {
		String text = driver.findElement(locator).getText().trim();
		return text;
	}

	/*
	 * Assertions
	 */

	public boolean assertElementPresentByXpath(By locator) {
		boolean isPresent = false;
		try {
			WebElement element = driver.findElement(locator);
			waitForElementVisibleByXpath(locator);
			highlightElement(element);
			if (driver.findElements(locator).size() > 0) {
//			Assert.assertTrue(driver.findElements(By.xpath(locator)).size() > 0);
				Reporter.log("Expected Element is present :" + locator, true);
				isPresent = true;
			} else {
//			Log.error("Expected element is not present :"+locator,true);
			}
		} catch (Exception e) {
			Reporter.log("Expected element is not present :" + locator, true);
		}
		return isPresent;

	}

	public boolean assertDynamicElementPresentByXpath(String locator) {
		boolean isPresent = false;
		try {
			if (driver.findElements(By.xpath(locator)).size() > 0) {
				Reporter.log("Expected Element is present :" + locator, true);
				isPresent = true;
			} else {
//				Log.error("Expected element is not present :"+locator,true);
			}
		} catch (NoSuchElementException e) {
			Reporter.log("Expected element is not present :" + locator, true);

		}
		return isPresent;
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void threadWait(String sec) {
		int seconds = Integer.parseInt(sec);
		try {
			Thread.sleep(seconds * 1000);

		} catch (Exception e) {

		}
	}

}
