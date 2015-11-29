package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class ByAngularBinding extends ByAngular.BaseBy {

    public ByAngularBinding(String binding) {
        super();
        this.binding = binding;
    }

    private String binding;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = 'body';\n" +
                        "var exactMatch = false;\n" +
                        "var binding = '" + binding + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findBindings")
                , context);
        errorIfNull(o);
        return o;
    }

    @Override
    public String toString() {
        return "bindings(" + binding + ')';
    }
}