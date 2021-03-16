package com.project.autoHero.pages;

import org.openqa.selenium.By;
import org.testng.Reporter;
import com.project.base.TestBase;

public class HomePage extends TestBase {

	
	private final By mein_konto = By.xpath("//div[@class=\"rightItems___1QD6D\"]/div/button[@class=\"button___3tyEI default___1FRAY\" and @type = \"button\"]");
	private final By anmelden = By.xpath("//div[@class=\"itemsList___3fp9v\"]/a[ contains(text(),'Registrieren')]");
	private final By email= By.xpath("//input[@name=\"email\" and @name=\"email\"]");
	private final By email_weiter = By.xpath("//button[@class=\"btn btn-primary\" and @type=\"submit\"]");
	private final By password = By.xpath("//input[@id=\"password\"]");
	private final By login = By.xpath("//button[@class=\"btn btn-primary\" and @type=\"submit\"]");
	private final By search_button = By.xpath("//a[contains(@class,'linkItem___24trh color-inherit___SyKXO decoration-none___1IENu') and contains(text(),'Auto finden')]");

	public void Login(String Username, String Password) {
		Reporter.log("Login to search cars",true);
//		if(gen.assertElementPresentByXpath(accept_cookies)) {
//			gen.waitAndClickByXpath(accept);
//		}
		gen.waitAndClickByXpath(mein_konto);
		gen.waitAndClickByXpath(anmelden);
		gen.enterValueinFieldByXpath(email, Username);
		gen.waitAndClickByXpath(email_weiter);
		gen.enterValueinFieldByXpath(password, Password);
		gen.waitAndClickByXpath(login);
	}

	public void navigateToSearchPage() {
		Reporter.log("navigating to search page",true);
		gen.waitAndClickByXpath(search_button);

	}
}
