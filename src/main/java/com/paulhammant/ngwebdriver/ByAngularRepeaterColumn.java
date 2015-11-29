package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class ByAngularRepeaterColumn extends ByAngular.BaseBy {

    private final String repeater;
    private boolean exact;
    private final String column;

    public ByAngularRepeaterColumn(String repeater, boolean exact, String column) {
        super();
        this.repeater = repeater;
        this.exact = exact;
        this.column = column;
    }

    public ByAngularRepeaterCell row(int row) {
        return new ByAngularRepeaterCell(repeater, exact, row, column);
    }

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = 'body';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var binding = '" + column + "';\n" +
                            "var exact = " + exact + ";\n" +
                            "\n" +
                            ByAngular.functions.get("findRepeaterColumn")
                    , context);
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ").column(" + column + ")";
    }


}
