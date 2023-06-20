package com.example.LearningBlog.webTests;

import com.example.LearningBlog.conteiners.Testcontainers;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class SeleniumTests extends Testcontainers  {

    public static final String LOGIN_PAGE = "//div[contains(@class,\"button-group\")]/a[@href='/login']/button";
    public static final String SUBMIT_LOGIN = "//div[contains(@class,\"ant-form-item-control-input-content\")]/div/button";

    @Test
    void invalidUser(){

        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        String backend = "http://" + backendContainer.getHost() + ":" + backendContainer.getMappedPort(8080) + "/";
        driver.get(backend);
        driver.findElement(By.xpath(LOGIN_PAGE)).click();
        WebElement username = driver.findElement(By.xpath("//*[@id=\"basic_username\"]"));
        username.sendKeys("Test");
        WebElement password = driver.findElement(By.xpath("//*[@id=\"basic_password\"]"));
        password.sendKeys("test");
        driver.findElement(By.xpath(SUBMIT_LOGIN)).click();


    }
}