package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterCell extends ByAngular.BaseBy {

    private final String repeater;
    private boolean exact;
    private final int row;
    private final String column;

    public ByAngularRepeaterCell(String repeater, boolean exact, int row, String column) {
        super();
        this.repeater = repeater;
        this.exact = exact;
        this.row = row;
        this.column = column;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        JavascriptExecutor jse = getJavascriptExecutor(context);

        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = 'body';\n" +
                        "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                        "var index = " + row + ";\n" +
                        "var binding = '" + column + "';\n" +
                        "var exact = " + exact + ";\n" +
                        "\n" +
                        ByAngular.functions.get("findRepeaterElement")
                , context);
        errorIfNull(o);
        return ((List<WebElement>) o).get(0);
    }

    // meaningless
    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        throw new UnsupportedOperationException("This locator zooms in on a single cell, findElements() is meaningless");
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ").row(" + row + ").column(" + column + ")";
    }

}
