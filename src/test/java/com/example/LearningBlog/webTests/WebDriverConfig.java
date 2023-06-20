package com.example.LearningBlog.webTests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverConfig {
    public final static String CHROME = "CHROME_DRIVER";
    public static WebDriver getDriver(final String driver) {
        System.setProperty("webdriver.chrome.driver", "c:\\Selenium-drivers\\Chrome\\chromedriver.exe");
        if (driver.equals(CHROME)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            return new ChromeDriver(options);
        } else {
            return null;
        }
    }
}


