package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularOptions extends ByAngular.BaseBy {

    public ByAngularOptions(String options) {
        super();
        this.options = options;
    }

    private String options;

    protected Object getObject(SearchContext context) {
        JavascriptExecutor jse = getJavascriptExecutor(context);
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var optionsDescriptor = '" + options + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findByOptions")
                , context);
        errorIfNull(o);
        return o;
    }

    @Override
    public String toString() {
        return "options(" + options + ')';
    }
}