package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularRepeater extends ByAngular.BaseBy {

    public ByAngularRepeater(String repeater, boolean exact) {
        super();
        this.repeater = repeater;
        this.exact = exact;
        this.rootSelector = "body";
    }

    public ByAngularRepeater(String repeater, boolean exact, String rootSelector) {
        super();
        this.repeater = repeater;
        this.exact = exact;
        this.rootSelector = rootSelector;
    }

    private String repeater;
    private boolean exact;
    private String rootSelector;

    public ByAngularRepeaterRow row(int row) {
        return new ByAngularRepeaterRow(repeater, exact, row);
    }

    public ByAngularRepeaterColumn column(String column) {
        return new ByAngularRepeaterColumn(repeater, exact, column);
    }

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = '" + rootSelector + "';\n" +
                        "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                        "var exact = " + exact + ";\n" +
                        "\n" +
                        ByAngular.functions.get("findAllRepeaterRows")
                , context);
    }

    @Override
    public String toString() {
        return (exact ? "exactR" : "r") + "epeater(" + repeater + ')';
    }
}
