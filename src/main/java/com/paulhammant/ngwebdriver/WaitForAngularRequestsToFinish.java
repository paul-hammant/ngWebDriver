package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;

public class WaitForAngularRequestsToFinish {

    public static void waitForAngularRequestsToFinish(JavascriptExecutor driver) {
        driver.executeAsyncScript("var callback = arguments[arguments.length - 1];" +
                "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
    }
}
