package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularButtonText extends ByAngular.BaseBy {

    public ByAngularButtonText(JavascriptExecutor jse, String buttonText) {
        super(jse);
        this.searchText = buttonText;
    }

    private String searchText;

    @Override
    public WebElement findElement(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = getObject(context);
        return ((List<WebElement>) o).get(0);
    }

    private Object getObject(SearchContext context) {
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
        if (searchContext instanceof WebDriver) {
            searchContext = null;
        }
        Object o = getObject(searchContext);
        errorIfNull(o);
        return (List<WebElement>) o;
    }

    @Override
    public String toString() {
        return "searchText(" + searchText + ')';
    }
}