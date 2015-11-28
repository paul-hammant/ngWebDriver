package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterColumn extends ByAngular.BaseBy {

    private final String repeater;
    private final String column;

    public ByAngularRepeaterColumn(JavascriptExecutor jse, String repeater, String column) {
        super(jse);
        this.repeater = repeater;
        this.column = column;
    }

    public ByAngularRepeaterCell row(int row) {
        return new ByAngularRepeaterCell(jse, repeater, row, column);
    }

    // meaningless
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
                            "var binding = '" + column + "';\n" +
                            "var exact = false;\n" +
                            "\n" +
                            ByAngular.functions.get("findRepeaterColumn")
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
        return "repeater(" + repeater + ").column(" + column + ")";
    }


}
