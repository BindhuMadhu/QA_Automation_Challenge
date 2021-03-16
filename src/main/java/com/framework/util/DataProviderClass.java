package com.framework.util;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
	private final static PropertiesUtils utils = new PropertiesUtils("src/main/resources/test.properties");
	private final static String test_data_sheet = utils.getProperty("test_data");
	
	@DataProvider(name = "LoginDetails")
    public static Object[][] getDataArray() {
	    ExcelUtils data1 = new ExcelUtils(test_data_sheet);
        return data1.getDataArray("LoginData");
    }

}
