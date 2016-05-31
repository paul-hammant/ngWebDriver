package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class NgWebDriver {

    private JavascriptExecutor driver;

    public NgWebDriver(JavascriptExecutor driver) {
        this.driver = driver;
    }

    public void mutate(WebElement element, final String variable, final String value) {
        driver.executeScript("angular.element(arguments[0]).scope()." + variable + " = " + value + ";" +
                "angular.element(document.body).injector().get('$rootScope').$apply();", element);
    }

    public String retrieveJson(WebElement element, final String variable) {
        return (String) check(variable, driver.executeScript(
                "return angular.toJson(angular.element(arguments[0]).scope()." + variable + ");", element));
    }

    private Object check(String variable, Object o) {
        if (o == null) {
            throw new VariableNotInScopeException("$scope variable '" + variable + "' not found in same scope as the element passed in.");
        }
        return o;
    }

    public Object retrieve(WebElement element, final String variable) {
        return check(variable, driver.executeScript(
                "return angular.element(arguments[0]).scope()." + variable + ";", element));
    }

    public String retrieveAsString(WebElement element, final String variable) {
        return retrieve(element, variable).toString();
    }

    public Long retrieveAsLong(WebElement element, final String variable) {
        Object rv = retrieve(element, variable);
        if (rv instanceof Double) {
            return ((Double) rv).longValue();
        }
        return (Long) rv;
    }

    public void waitForAngularRequestsToFinish() {
        driver.executeAsyncScript("var callback = arguments[arguments.length - 1];\n" +
                "var rootSelector = 'body';\n" +
                "\n" +
                ByAngular.functions.get("waitForAngular"));
    }

    public String getLocationAbsUrl() {
        return (String) driver.executeScript(
                "var selector = 'body';\n" +
                "\n" +
                ByAngular.functions.get("getLocationAbsUrl"));
    }
	
    public Object evaluateScript(WebElement element, String script) {
        script = script.replace("$scope", "angular.element(arguments[0]).scope()");
        return driver.executeScript(script, element);
    }
}
