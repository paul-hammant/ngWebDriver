package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeater extends ByAngular.BaseBy {

    public ByAngularRepeater(JavascriptExecutor jse, String repeater) {
        super(jse);
        this.repeater = repeater;
    }

    private String repeater;

    public ByAngularRepeaterRow row(int row) {
        return new ByAngularRepeaterRow(jse, repeater, row);
    }

    public ByAngularRepeaterColumn column(String column) {
        return new ByAngularRepeaterColumn(jse, repeater, column);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = getObject(context);
        errorIfNull(o);
        return ((List<WebElement>) o).get(0);
    }

    private Object getObject(SearchContext context) {
        return jse.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = 'body';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var exact = false;\n" +
                            "\n" +
                            ByAngular.functions.get("findAllRepeaterRows")

                    , context);
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        if (searchContext instanceof WebDriver) {
            searchContext = null;
        }
        Object o = getObject(searchContext);
        errorIfNull(o);
        return (List<WebElement>) o;
    }

    @Override
    public String toString() {
        return "repeater(" + repeater + ')';
    }
}
