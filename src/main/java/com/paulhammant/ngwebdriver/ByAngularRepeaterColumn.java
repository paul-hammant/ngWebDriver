package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeaterColumn extends By {

    private final String repeater;
    private final String column;

    public ByAngularRepeaterColumn(String repeater, String column) {
        this.repeater = repeater;
        this.column = column;
    }

    private By makeJsBy() {
        return By.js(
                "var matches = [];\n" +
                "var using = arguments[0] || document;\n" +
                "var repeater = '" + repeater + "';\n" +
                "var binding = '" + column + "';\n" +
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
                "for (var i = 0; i < rows.length; ++i) {\n" +
                "  var bindings = [];\n" +
                "  if (rows[i].className.indexOf('ng-binding') != -1) {\n" +
                "    bindings.push(rows[i]);\n" +
                "  }\n" +
                "  var childBindings = rows[i].getElementsByClassName('ng-binding');\n" +
                "  for (var k = 0; k < childBindings.length; ++k) {\n" +
                "    bindings.push(childBindings[k]);\n" +
                "  }\n" +
                "  for (var j = 0; j < bindings.length; ++j) {\n" +
                "    var bindingName = angular.element(bindings[j]).data().$binding[0].exp ||\n" +
                "        angular.element(bindings[j]).data().$binding;\n" +
                "    if (bindingName.indexOf(binding) != -1) {\n" +
                "      matches.push(bindings[j]);\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "return matches;"
        );
    }

    // meaningless
    @Override
    public WebElement findElement(SearchContext context) {
        throw new UnsupportedOperationException("This locator zooms in on a multiple cells, findElement() is meaningless");
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        return makeJsBy().findElements(searchContext);

    }

}
