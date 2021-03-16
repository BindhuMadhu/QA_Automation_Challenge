package com.autohero.test;

import java.awt.AWTException;
import java.text.ParseException;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import com.framework.util.ExcelUtils;
import com.project.autoHero.pages.*;
import com.project.base.TestBase;

public class SearchTest extends TestBase {
	HomePage homePage = new HomePage();
	SearchPage search = new SearchPage();

	String test_data_sheet = utils.getProperty("test_data");
	protected ExcelUtils data = new ExcelUtils(test_data_sheet);
	 String url = utils.getProperty("url");

	@Test(priority = 1, description = "To Verify the search functinality as anonymous user")
	public void TestCase_001() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description: : To Verify the search functinality as anonymous user", true);
		gen.launch(utils.getProperty("auto_hero_url"));
		homePage.navigateToSearchPage();
		search.regFilter(data.getTestData("year_start"));
		search.priceSort();
		boolean result = search.searchCars(data.getTestData("year_start"));
		boolean isSorted = search.priceDescending();
		
		SoftAssert softs = new SoftAssert();
		softs.assertEquals(result, true, "cars are not filtered by the year provided");
		softs.assertEquals(isSorted, true, "cars are not sorted by price descending");
		
		softs.assertAll();
		
		
	}
	
	@Test(priority = 1, description = "To Verify the search functinality as Loggedin  user.")
	public void TestCase_002() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description: : To Verify the search functinality as Loggedin  user.", true);
		gen.launch(utils.getProperty("auto_hero_url"));
		homePage.Login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToSearchPage();
		search.regFilter(data.getTestData("year_start"));
		search.priceSort();
		boolean result = search.searchCars(data.getTestData("year_start"));
		boolean isSorted = search.priceDescending();
		
		SoftAssert softs = new SoftAssert();
		softs.assertEquals(result, true, "cars are not filtered by the year provided");
		softs.assertEquals(isSorted, true, "cars are not sorted by price descending");
		
		softs.assertAll();
		
		
	}
	
	
   
}
