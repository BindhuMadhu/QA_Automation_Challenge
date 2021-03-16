 package com.project.autoHero.pages;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import com.project.base.TestBase;

public class SearchPage extends TestBase {

	WebElement element;
	List<Integer> yearsList = new LinkedList<Integer>();
	List<String> regYear = new LinkedList<>();
	List<String> pricesDes = new LinkedList<>();

	private final By Erstzulassung_filter = By.xpath("//span[text()='Erstzulassung']");
	private final By Erstzulassung_ab = By.xpath("//div[@aria-labelledby='yearFilter']/div/div[@class='rangeContainer___1mgKq']/div/select[@id='rangeStart']");
	private final By priceDecending = By.xpath("//select[contains(@class,\"sortBySelec\")]");
	private final By logo = By.xpath("//img[@title=\"Autohero-Logo\"]");

	public void clickOnLogo() {
		gen.waitAndClickByXpath(logo);
	}

	public void regFilter(String yearStart) {
		gen.waitAndClickByXpath(Erstzulassung_filter);
		gen.selectOptionByVisibleText(Erstzulassung_ab, yearStart);

	}

	public void priceSort() {
		gen.selectOptionByIndex(priceDecending, "2");
	}

	public boolean searchCars(String yearInput) {
		int year = Integer.parseInt(yearInput);
		for (int i = year; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
			yearsList.add(i);
		}
		Reporter.log("Asserting the cars are filted by year selected", true);
		List<WebElement> yearFound = new LinkedList<>();
		List<WebElement> price = new LinkedList<>();
		boolean value = true;
		pricesDes.clear();
		try {
			long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

			while (true) {
				yearFound = driver.findElements(By.xpath("//li[@class='specItem___2gMHn'][1]"));
				price = driver.findElements(By.xpath("//div[@class='title___uRijL price___1A8DG']"));
				for (int j = 0; j < yearFound.size(); j++) {
					String carRegYear = yearFound.get(j).getText();
					String priceOfCar = price.get(j).getText();
					pricesDes.add(priceOfCar);
					if (!(yearsList.contains(Integer.parseInt(carRegYear)))) {
						System.out.println("the yrea is " + carRegYear);
						value = false;
					}
				}
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(2000);

				long newHeight = (long) ((JavascriptExecutor) driver)
						.executeScript("return document.body.scrollHeight");
				if (newHeight == lastHeight) {
					break;
				}
				lastHeight = newHeight;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return value;
	}

	public boolean priceDescending() throws ParseException {
		Reporter.log("Asserting the prices are filtered is correct", true);
		List<BigDecimal> convertedPrices = new ArrayList<>();

		for (int i = 0; i < pricesDes.size(); i++) {
			final NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY);
			if (format instanceof DecimalFormat) {
				((DecimalFormat) format).setParseBigDecimal(true);
			}
			convertedPrices.add((BigDecimal) format.parse(pricesDes.get(i).replaceAll("[^\\d.,]", "")));
		}
		List<BigDecimal> priceListCopy = new ArrayList<BigDecimal>(convertedPrices);
		Collections.sort(priceListCopy, Collections.reverseOrder());

		return priceListCopy.equals(convertedPrices);

	}
}