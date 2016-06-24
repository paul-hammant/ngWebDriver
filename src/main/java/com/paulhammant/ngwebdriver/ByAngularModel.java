package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularModel extends ByAngular.BaseBy {

    public ByAngularModel(String model) {
        super();
        this.model = model;
        this.rootSelector = "body";
    }

    public ByAngularModel(String model, String rootSelector) {
        super();
        this.model = model;
        this.rootSelector = rootSelector;
    }

    private String model;
    private String rootSelector;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = '" + rootSelector + "';\n" +
                        "var model = '" + model + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findByModel")
                , context);
    }

    @Override
    public String toString() {
        return "model(" + model + ')';
    }
}
