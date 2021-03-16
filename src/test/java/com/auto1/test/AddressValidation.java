package com.auto1.test;

import java.awt.AWTException;
import java.text.ParseException;

import org.testng.Reporter;

import org.testng.annotations.Test;
import com.project.auto1.pages.HomePage;
import com.project.auto1.pages.MyAddessPage;
import com.project.base.TestBase;
import com.framework.util.*;

public class AddressValidation extends TestBase {

	HomePage homePage = new HomePage();
	MyAddessPage addressPage = new MyAddessPage();

	@Test(description = "To verify user can able to add valid address and display of newly added address")
	public void TestCase_001() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description : : To verify user can able to add valid address and display of newly added address",
				true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToAddressSettingPage();
		addressPage.addAdress(data.getTestData("Name"), data.getTestData("Company"), data.getTestData("AdditionalInfo"),
				data.getTestData("StreetAndNumber"), data.getTestData("ZipCode"), data.getTestData("City"),
				data.getTestData("County"), data.getTestData("Phone"));
		addressPage.assertAddressAdded();
		homePage.logOut();
	}

	@Test(description = "To verify user can able edit the address")
	public void TestCase_002() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description : : To verify user can able edit the address", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToAddressSettingPage();
		addressPage.clickOnEditAddress();
		addressPage.updateAddress(data.getTestData("UpdatedName"));
		addressPage.assertAddressUpdated();
		homePage.logOut();

	}

	@Test(description = "To verify user can able delete the address added")
	public void TestCase_003() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description : :To verify user can able delete the address added", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToAddressSettingPage();
		addressPage.clickOnDeleteAddress();
		addressPage.AssertAddressDeleted();
		homePage.logOut();

	}

	@Test(description = "To verify user can able add addredd with out street field")
	public void TestCase_004() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description : :To verify user can able add addredd with out street field", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToAddressSettingPage();
		addressPage.addAdress(data.getTestData("Name"), data.getTestData("Company"), data.getTestData("Invalid_Street"),
				data.getTestData("AdditionalInfo"), data.getTestData("ZipCode"), data.getTestData("City"),
				data.getTestData("County"), data.getTestData("Phone"));
		addressPage.assertInvalidDataError(data.getTestData("InvalidAddress_message"));
		homePage.logOut();

	}

	@Test(description = "To verify user can able see 20 addresses per page")
	public void TestCase_005() {
		Reporter.log("description : :To verify user can able see 20 addresses per page", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username2"), data.getTestData("Password2"));
		homePage.navigateToAddressSettingPage();
		for (int i = 1; i <= 30; i++) {
			addressPage.addAdress(com.fakerAddress("name"), com.fakerAddress("company"), com.fakerAddress("Street"),
					com.fakerAddress("addAddress"), com.fakerAddress("zipCode"), com.fakerAddress("City"),
					com.fakerAddress("Country"), com.fakerAddress("phone"));
		}
		addressPage.assertAddresslist();
		homePage.logOut();
	}

	@Test(description = "To Verify the system behaviour when merchants try to add the address with invalid Postal code")
	public void TestCase_006() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description : :To Verify the system behaviour when merchants try to add the address with invalid Postal code", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToAddressSettingPage();
		addressPage.addAdress(data.getTestData("Name"), data.getTestData("Company"),
				data.getTestData("StreetAndNumber"), data.getTestData("AdditionalInfo"),
				data.getTestData("Invalid_ZipCode"), data.getTestData("City"), data.getTestData("County"),
				data.getTestData("Phone"));
		addressPage.assertAddressNotAdded();
		homePage.logOut();

	}

	@Test(description = "To Verify the system behaviour when merchants try to add the address with invalid Phone number")
	public void TestCase_007() throws InterruptedException, AWTException, ParseException {
		Reporter.log("description : :To Verify the system behaviour when merchants try to add the address with invalid Phone number", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(data.getTestData("Username1"), data.getTestData("Password1"));
		homePage.navigateToAddressSettingPage();
		addressPage.addAdress(data.getTestData("Name"), data.getTestData("Company"),
				data.getTestData("Street_For_Invalid_mobile"), data.getTestData("AdditionalInfo"),
				data.getTestData("ZipCode"), data.getTestData("City"), data.getTestData("County"),
				data.getTestData("Invalid_Mobile"));
		addressPage.assertAddressNotAdded();

	}	
	@Test(description = "To delete the all addresses added in previous testcases",dataProvider = "LoginDetails", dataProviderClass = DataProviderClass.class)
	public void TestCase_008(String email, String password) {
		Reporter.log("description : :To delete the all addresses added in previous testcase", true);
		gen.launch(utils.getProperty("auto1_url"));
		homePage.login(email,password);
		homePage.navigateToAddressSettingPage();
		addressPage.deleteAllAdresses();
		homePage.logOut();
	}

}
