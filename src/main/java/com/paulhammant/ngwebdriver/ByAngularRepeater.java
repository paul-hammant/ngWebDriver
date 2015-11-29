package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeater extends ByAngular.BaseBy {

    public ByAngularRepeater(String repeater, boolean exact) {
        super();
        this.repeater = repeater;
        this.exact = exact;
    }

    private String repeater;
    private boolean exact;

    public ByAngularRepeaterRow row(int row) {
        return new ByAngularRepeaterRow(repeater, exact, row);
    }

    public ByAngularRepeaterColumn column(String column) {
        return new ByAngularRepeaterColumn(repeater, exact, column);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        Object o = getObject(context);
        errorIfNull(o);
        return ((List<WebElement>) o).get(0);
    }

    private Object getObject(SearchContext context) {
        JavascriptExecutor jse = getJavascriptExecutor(context);
        if (context instanceof WebDriver) {
            context = null;
        }
        return jse.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = 'body';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var exact = " + exact + ";\n" +
                            "\n" +
                            ByAngular.functions.get("findAllRepeaterRows")

                    , context);
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        Object o = getObject(searchContext);
        errorIfNull(o);
        return (List<WebElement>) o;
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ')';
    }
}
