package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularButtonText extends ByAngular.BaseBy {

    public ByAngularButtonText(String buttonText) {
        super();
        this.searchText = buttonText;
    }

    private String searchText;

    @Override
    public WebElement findElement(SearchContext context) {
        Object o = getObject(context);
        return ((List<WebElement>) o).get(0);
    }

    protected Object getObject(SearchContext context) {
        JavascriptExecutor jse = getJavascriptExecutor(context);
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var searchText = '" + searchText + "';\n" +
                        "\n" +
                        ByAngular.functions.get("findByButtonText")
                , context);
        errorIfNull(o);
        return o;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        Object o = getObject(searchContext);
        errorIfNull(o);
        return (List<WebElement>) o;
    }

    @Override
    public String toString() {
        return "searchText(" + searchText + ')';
    }
}