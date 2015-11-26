package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;

public class WaitForAngularRequestsToFinish extends AngularJavaScriptFunctions{

    public static void waitForAngularRequestsToFinish(JavascriptExecutor driver) {
        driver.executeAsyncScript("var callback = arguments[arguments.length - 1];\n" +
                "var rootSelector = 'body';\n" +
                "\n" +
                ByAngular.functions.get("waitForAngular"));
    }
}
