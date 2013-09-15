package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
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
        Object o = driver.executeScript("return angular.element(arguments[0]).scope()." + variable + ";", element);
        if (o == null) {
            throw new WebDriverException("$scope variable '" + variable + "' not found in same scope as the element passed in.");
        }
        return o;
    }

    public String retrieveAsString(WebElement element, final String variable) {
        return retrieve(element, variable).toString();
    }

    public Long retrieveAsLong(WebElement element, final String variable) {
        return (Long) retrieve(element, variable);
    }


}
