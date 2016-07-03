package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularModel extends ByAngular.BaseBy {

    public ByAngularModel(String model) {
        super();
        this.model = model;
    }

    private String model;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = '"+NgWebDriver.rootSelector+"';\n" +
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
