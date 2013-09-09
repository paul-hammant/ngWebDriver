package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class AngularModelAccessor {


    private JavascriptExecutor driver;

    public AngularModelAccessor(JavascriptExecutor driver) {
        this.driver = driver;
    }

    public void mutate(WebElement element, final String variable, final String value) {

        driver.executeScript("angular.element(arguments[0]).scope()." + variable + " = " + value + ";" +
                "angular.element(document.body).injector().get('$rootScope').$apply();", element);


    }

    public String retrieveJson(WebElement element, final String variable) {

        return (String) driver.executeScript("return angular.toJson(angular.element(arguments[0]).scope()." + variable + ");", element);

    }

    public Object retrieve(WebElement element, final String variable) {
        return driver.executeScript("return angular.element(arguments[0]).scope()." + variable + ";", element);
    }

    public String retrieveAsString(WebElement element, final String variable) {
        return driver.executeScript("return angular.element(arguments[0]).scope()." + variable + ";", element).toString();
    }

    public Long retrieveAsLong(WebElement element, final String variable) {
        return (Long) driver.executeScript("return angular.element(arguments[0]).scope()." + variable + ";", element);
    }


}
