package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterRow extends ByAngular.BaseBy {

    private final String repeater;
    private boolean exact;
    private final int row;

    public ByAngularRepeaterRow(String repeater, boolean exact, int row) {
        super();
        this.repeater = repeater;
        this.exact = exact;
        this.row = row;
    }

    public ByAngularRepeaterCell column(String column) {
        return new ByAngularRepeaterCell(repeater, exact, row, column);
    }

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = 'body';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var index = " + row + ";\n" +
                            "var exact = " + exact+ ";\n" +
                            "\n" +
                            ByAngular.functions.get("findRepeaterRows")
                    , context);
    }

    // meaningless
    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        throw new UnsupportedOperationException("This locator zooms in on a single row, findElements() is meaningless");
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ").row(" + row + ")";
    }

}
