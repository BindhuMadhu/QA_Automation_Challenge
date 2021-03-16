package com.project.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.framework.util.PropertiesUtils;
import com.github.javafaker.Faker;

public class Common {
	public static WebDriver driver;
	static PropertiesUtils utils;
	public static String variableStorePath = null;
	Random random = new Random();

	public Common(String path) {
		this.variableStorePath = path;

	}

	/*
	 * Getting Data from webelements
	 */

	public void setVariable(String key, String value) {
		InputStream varfile;
		OutputStream out;
		Properties prop = new Properties();
		try {
			varfile = new FileInputStream(variableStorePath);
			prop.load(varfile);
			varfile.close();
			out = new FileOutputStream(variableStorePath);
			prop.setProperty(key, value);
			prop.store(out, null);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getVariable(String key) {
		InputStream varfile = null;
		Properties prop = new Properties();
		try {
			varfile = new FileInputStream(variableStorePath);
			prop.load(varfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	

	public String randomsname() {
		StringBuffer sb = new StringBuffer("Auto1");
		sb.append("Test");
		sb.append(getRandomLong(0, 999999999, random));
		return sb.toString();
	}

	private long getRandomLong(long aStart, long aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		long randomNumber = (long) (fraction + aStart);
		return randomNumber;
	}

	
	public String getFieldTextbyXpath(String locator) {
		String text = driver.findElement(By.xpath(locator)).getText().trim();
		return text;

	}

	public String fakerAddress(String key) {

		Map<String, String> fakeAddress = new HashMap<String, String>();
		Faker faker = new Faker(new Locale("de-DE"));
		fakeAddress.put("name", faker.name().firstName());
		fakeAddress.put("company", faker.company().name());
		fakeAddress.put("Street", faker.address().streetName());
		fakeAddress.put("addAddress", faker.address().secondaryAddress());
		fakeAddress.put("zipCode", faker.address().zipCode());
		fakeAddress.put("City", faker.address().city());
		fakeAddress.put("Country", "Germany");
		fakeAddress.put("phone", faker.phoneNumber().cellPhone());

		return fakeAddress.get(key);

	}

}
