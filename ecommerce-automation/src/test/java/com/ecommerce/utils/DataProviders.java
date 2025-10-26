package com.ecommerce.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "signupData")
    public static Object[][] signupData() {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx";
        return ExcelUtils.readSheet(path, "signup");
    }
}