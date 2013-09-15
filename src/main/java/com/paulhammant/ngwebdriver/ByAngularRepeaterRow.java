package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterRow extends ByAngular.BaseBy {

    private final String repeater;
    private final int row;

    public ByAngularRepeaterRow(JavascriptExecutor jse, String repeater, int row) {
        super(jse);
        this.repeater = repeater;
        this.row = row;
    }

    public ByAngularRepeaterCell column(String column) {
        return new ByAngularRepeaterCell(jse, repeater, row, column);
    }


    @Override
    public WebElement findElement(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o = jse.executeScript(
                "var using = arguments[0] || document;\n" +
                        "var repeater = '" + repeater + "';\n" +
                        "var index = " + row + ";\n" +
                        "\n" +
                        "var rows = [];\n" +
                        "var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-'];\n" +
                        "for (var p = 0; p < prefixes.length; ++p) {\n" +
                        "  var attr = prefixes[p] + 'repeat';\n" +
                        "  var repeatElems = using.querySelectorAll('[' + attr + ']');\n" +
                        "  attr = attr.replace(/\\\\/g, '');\n" +
                        "  for (var i = 0; i < repeatElems.length; ++i) {\n" +
                        "    if (repeatElems[i].getAttribute(attr).indexOf(repeater) != -1) {\n" +
                        "      rows.push(repeatElems[i]);\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n" +
                        "return rows[index - 1];", context);
        errorIfNull(o);
        return (WebElement) o;
    }

    // meaningless
    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        throw new UnsupportedOperationException("This locator zooms in on a single row, findElements() is meaningless");
    }

    @Override
    public String toString() {
        return "repeater(" + repeater + ").row(" + row + ")";
    }

}
