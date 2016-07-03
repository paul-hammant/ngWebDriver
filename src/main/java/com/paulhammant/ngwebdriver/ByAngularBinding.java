package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;

public class ByAngularBinding extends ByAngular.BaseBy {

    public ByAngularBinding(String rootSelector, String binding) {
        super(rootSelector);
        this.binding = binding;
    }

    private String binding;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = '" + rootSelector + "';\n" +
                        "var exactMatch = false;\n" +
                        "var binding = '" + binding + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findBindings")
                , context);
    }

    @Override
    public String toString() {
        return "bindings(" + binding + ')';
    }
}