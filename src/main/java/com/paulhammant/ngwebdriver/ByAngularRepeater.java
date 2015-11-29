package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

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

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = 'body';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var exact = " + exact + ";\n" +
                            "\n" +
                            ByAngular.functions.get("findAllRepeaterRows")
                    , context);
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ')';
    }
}
