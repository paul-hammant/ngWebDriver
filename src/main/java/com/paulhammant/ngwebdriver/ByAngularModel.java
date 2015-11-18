package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularModel extends ByAngular.BaseBy {

    public ByAngularModel(JavascriptExecutor jse, String model) {
        super(jse);
        this.model = model;
    }

    private String model;

    @Override
    public WebElement findElement(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var model = '" + model + "';\n" +
                        "\n" +
                        "var rows = [];\n" +
                        "var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-'];\n" +
                        "for (var p = 0; p < prefixes.length; ++p) {\n" +
                        "  var attr = prefixes[p] + 'model';\n" +
                        "  var modelElems = using.querySelectorAll('[' + attr + ']');\n" +
                        "  attr = attr.replace(/\\\\/g, '');\n" +
                        "  for (var i = 0; i < modelElems.length; ++i) {\n" +
                        "    if (modelElems[i].getAttribute(attr).indexOf(model) != -1) {\n" +
                        "      rows.push(modelElems[i]);\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n" +
                        "return rows[0];", context);
        errorIfNull(o);
        return (WebElement) o;
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        if (searchContext instanceof WebDriver) {
            searchContext = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var model = '" + model + "';\n" +
                        "\n" +
                        "var rows = [];\n" +
                        "var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-'];\n" +
                        "for (var p = 0; p < prefixes.length; ++p) {\n" +
                        "  var attr = prefixes[p] + 'model';\n" +
                        "  var modelElems = using.querySelectorAll('[' + attr + ']');\n" +
                        "  attr = attr.replace(/\\\\/g, '');\n" +
                        "  for (var i = 0; i < modelElems.length; ++i) {\n" +
                        "    if (modelElems[i].getAttribute(attr).indexOf(model) != -1) {\n" +
                        "      rows.push(modelElems[i]);\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n" +
                        "return rows;", searchContext);
        errorIfNull(o);
        return (List<WebElement>) o;
    }

    @Override
    public String toString() {
        return "model(" + model + ')';
    }
}
