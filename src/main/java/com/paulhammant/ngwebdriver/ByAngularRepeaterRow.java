package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterRow extends ByAngular.BaseBy {

    private final String repeater;
    private final int row;

    public ByAngularRepeaterRow(JavascriptExecutor jse, String repeater, int row) {
        super(jse);
        this.repeater = repeater;
        this.row = row;
    }

    public ByAngularRepeaterCell column(String column) {
        return new ByAngularRepeaterCell(jse, repeater, row, column);
    }


    @Override
    public WebElement findElement(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = 'body';\n" +
                        "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                        "var index = " + row + ";\n" +
                        "var exact = false;\n" +
                        "\n" +
                        ByAngular.functions.get("findRepeaterRows")
                , context);
        errorIfNull(o);
        return ((List<WebElement>) o).get(0);
    }

    // meaningless
    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        throw new UnsupportedOperationException("This locator zooms in on a single row, findElements() is meaningless");
    }

    @Override
    public String toString() {
        return "repeater(" + repeater + ").row(" + row + ")";
    }

}
