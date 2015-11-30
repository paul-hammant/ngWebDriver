package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;


public class ByAngularCssContainingText extends ByAngular.BaseBy {

    public ByAngularCssContainingText(String cssSelector,String searchText) {
        super();
        this.cssSelector = cssSelector;
        this.searchText = searchText;
    }

    private String cssSelector,searchText;

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                        "var cssSelector = '" + cssSelector + "';\n" +
                        "var searchText = '" + searchText + "';\n" +
                        "var using = arguments[0] || document;\n" +
                        "\n" +
                        ByAngular.functions.get("findByCssContainingText")
                , context);
    }

    @Override
    public String toString() {
        return "cssContainingText(" +cssSelector + searchText + ')';
    }
}
