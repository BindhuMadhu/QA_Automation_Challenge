package com.project.auto1.pages;


import org.openqa.selenium.By;
import org.testng.Reporter;
import com.project.base.TestBase;


public class HomePage extends TestBase{

	private final By login_Button = By.xpath("//button[contains(@class,'Header_loginButton')]");
    private final By email = By.xpath("//input[@type = \"text\" and @name =\"email\"]");
	private final By password = By.xpath("//input[@type = \"password\" and @name =\"password\"]");
	private final By login = By.xpath("//div[@class=\"LoginPopup_buttonWrapper__38JDN\"]/button[contains(@class,'Button_button') and contains(text(),'Log in')]");
	private final By profile_options = By.xpath("(//button[contains(@data-qa-id,'user_menu')]/span)[3]");
	private final By my_address = By.xpath("//li[@data-qa-id=\"user_menu_user-address\"]/a[ text()=\"Address settings\"]");
	private final By auto1_logo = By.xpath("(//div[@class=\"Auto1Logo\"])[1]");
	private final By logout_button = By.xpath("//li[@data-qa-id=\"user_menu_user-logOut\"]/a[contains(text(),'Log out')]");
	
	
	public void login(String Username, String Password) {
		gen.waitAndClickByXpath(login_Button);
		gen.enterValueinFieldByXpath(email, Username);
		gen.enterValueinFieldByXpath(password, Password);
		gen.waitAndClickByXpath(login);
		gen.threadWait("2");
		
	}
	
	public void navigateToAddressSettingPage() {
		gen.waitAndClickByXpath(profile_options);
		gen.waitAndClickByXpath(my_address);
	}
	
	public void logOut() {
		Reporter.log("Description :: Clicking on logout button", true);
		gen.waitAndClickByXpath(auto1_logo);
		gen.threadWait("2");
		gen.waitAndClickByXpath(profile_options);
		gen.waitAndClickByXpath(logout_button);
		
	}
		
	
	
	
	}
