package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class ByAngularExactBinding extends ByAngular.BaseBy {

    public ByAngularExactBinding(String exactBinding) {
        super();
        this.binding = exactBinding;
    }

    private String binding;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var rootSelector = 'body';\n" +
                        "var exactMatch = true;\n" +
                        "var binding = '" + binding + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findBindings")
                , context);
    }

    @Override
    public String toString() {
        return "exactBinding(" + binding + ')';
    }
}