package com.project.auto1.pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import com.project.base.TestBase;

public class MyAddessPage extends TestBase {

	private final By add_new_address = By.xpath("//div[@class='btn-container']/button[contains(text(),'ADD NEW ADDRESS')]");
	private final By name = By.xpath("//div[@class=\"form-control input-control\"]/input[@name=\"name\"]");
	private final By company = By.name("company");
	private final By street = By.name("street");
	private final By address2 = By.name("address2");
	private final By zipcode = By.name("zipcode");
	private final By city = By.name("city");
	private final By country = By.name("country");
	private final By phone = By.name("phone");
	private final By save = By.xpath("//button[@class='btn-primary' and contains(text(),'SAVE')]");
	private final By confirm_delete = By.xpath("//button[@class=\"btn-secondary\"]");
	private final By address_list = By.xpath("//div[@class=\"address-list__item\"]");
	private final By delete_button = By.xpath("//a[@class=\"btn-delete\"]");
	private final By address_error = By.xpath("//div[contains(@class,'form-group has-error')]");

	public void addAdress(String Name, String Company, String Street, String Address1, String Zipcode, String City,
			String Country, String Phone) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement addaddress = driver.findElement(add_new_address);
		js.executeScript("arguments[0].scrollIntoView();", addaddress);
		gen.threadWait("1");
		gen.waitAndClickByXpath(add_new_address);
		gen.threadWait("1");
		WebElement nameField = driver.findElement(name);
		js.executeScript("arguments[0].scrollIntoView();", nameField);
		gen.enterValueinFieldByXpath(name, Name);
		com.setVariable("addedName", Name);
		gen.enterValueinFieldByXpath(company, Company);
		gen.enterValueinFieldByXpath(street, Street);
		gen.enterValueinFieldByXpath(address2, Address1);
		com.setVariable("AddressTitle", Street);
		gen.enterValueinFieldByXpath(zipcode, Zipcode);
		gen.enterValueinFieldByXpath(city, City);
		gen.selectOptionByVisibleText(country, Country);
		gen.enterValueinFieldByXpath(phone, Phone);
		gen.waitAndClickByXpath(save);

	}

	public void assertAddressAdded() {
		Reporter.log("Description :: Assert the adderss added", true);
		String xpathValue = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]";
		Assert.assertTrue(gen.assertDynamicElementPresentByXpath(xpathValue));

	}
	
	public void assertAddressNotAdded() {
		Reporter.log("Description :: Assert the adderss is not added", true);
		String xpathValue = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]";
		Assert.assertFalse(gen.assertDynamicElementPresentByXpath(xpathValue));

	}

	public void assertInvalidDataError(String value) {
		Reporter.log("Description :: Address with no Street name", true);
		Assert.assertTrue(gen.assertElementPresentByXpath(address_error));
	}

	public void clickOnEditAddress() {
		Reporter.log("Description :: clicking on Edit address button", true);
		String xpathValue = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]/../div/div/button[@class=\"btn btn-primary\"]";
		gen.waitAndClickByXpath(xpathValue);

	}

	public void updateAddress(String nameUpdate) {
		Reporter.log("Description :: updating the address", true);
		gen.enterValueinFieldByXpath(name, nameUpdate);
		com.setVariable("updatedName", nameUpdate);
		gen.waitAndClickByXpath(save);

	}

	public void assertAddressUpdated() {
		Reporter.log("Description :: Assert the adderss updated", true);
		String xpathValue = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]/../ul/li[contains(text(),'" + com.getVariable("updatedName")
				+ "')]";
		Assert.assertTrue(gen.assertDynamicElementPresentByXpath(xpathValue));

	}

	public void clickOnDeleteAddress() {
		Reporter.log("Description :: clicking on Delete address button", true);
		String AddressTitle = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]";
		String deleteButton = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]/../div/div/a[@class=\"btn-delete\"]";
		gen.threadWait("1");
		gen.waitAndClickByXpath(deleteButton);
		gen.waitAndClickByXpath(confirm_delete);
		Assert.assertFalse(gen.assertDynamicElementPresentByXpath(AddressTitle));
	}

	public void AssertAddressDeleted() {
		Reporter.log("Description :: Assert the adderss deleted", true);
		String AddressTitle = "//div[contains(@class,'existing-address')]/h2[contains(text(),'"
				+ com.getVariable("AddressTitle") + "')]";
		Assert.assertFalse(gen.assertDynamicElementPresentByXpath(AddressTitle));
	}

	public void assertAddresslist() {
		gen.refreshPage();
		List<WebElement> elementName = driver.findElements(address_list);
		if (elementName.size() == 20) {
			Reporter.log("Total addresses displaying on the page :" + elementName.size(), true);
			Assert.assertTrue(true);
		} else {
			Reporter.log("In this page  " + elementName.size() + " addresses are  displaying but expected is 20 per page ", true);
			Assert.assertTrue(false);
		}
	}

	public void deleteAllAdresses() {
		int deleteButtons = 1;
		int deleteButtonsNow =driver.findElements(delete_button).size();
		if (deleteButtonsNow>0) {
		for (int i = 1; i <= deleteButtons; deleteButtons = deleteButtonsNow) {
			String Xpath = "(//a[@class=\"btn-delete\"])[" + 1 + "]";
			gen.threadWait("1");
			gen.waitAndClickByXpath(Xpath);
			gen.threadWait("1");
			gen.waitAndClickByXpath(confirm_delete);
			deleteButtonsNow = driver.findElements(delete_button).size();
			gen.threadWait("1");

		}
		}else {
			Reporter.log("No addresses are found for deletion",true);
			Assert.assertTrue(true);
		
		}

	}

}