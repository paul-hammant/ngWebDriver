package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterCell extends By {

    private final String repeater;
    private final int row;
    private final String column;

    public ByAngularRepeaterCell(String repeater, int row, String column) {
        this.repeater = repeater;
        this.row = row;
        this.column = column;
    }

    private By makeJsBy() {
        return By.js(
                "var matches = [];\n" +
                "var using = arguments[0] || document;\n" +
                "var repeater = '" + repeater + "';\n" +
                "var index = " + row + ";\n" +
                "var binding = '" + column + "';\n" +
                "var rows = [];\n" +
                "var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\\\:'];\n" +
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
                "var row = rows[index - 1];\n" +
                "var bindings = [];\n" +
                "if (row.className.indexOf('ng-binding') != -1) {\n" +
                "  bindings.push(row);\n" +
                "}\n" +
                "var childBindings = row.getElementsByClassName('ng-binding');\n" +
                "for (var i = 0; i < childBindings.length; ++i) {\n" +
                "  bindings.push(childBindings[i]);\n" +
                "}\n" +
                "for (var i = 0; i < bindings.length; ++i) {\n" +
                "  var bindingName = angular.element(bindings[i]).data().$binding[0].exp ||\n" +
                "      angular.element(bindings[i]).data().$binding;\n" +
                "  if (bindingName.indexOf(binding) != -1) {\n" +
                "    matches.push(bindings[i]);\n" +
                "  }\n" +
                "}\n" +
                "// We can only return one with webdriver.findElement.\n" +
                "return matches[0];"
        );
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return makeJsBy().findElement(context);
    }

    // meaningless
    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        throw new UnsupportedOperationException("This locator zooms in on a single row, findElements() is meaningless");
    }

}
