package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularRepeater extends By {

    public static ByAngularRepeater ngRepeater(String repeater) {
        return new ByAngularRepeater(repeater);
    }

    private String repeater;

    public ByAngularRepeater(String repeater) {
        this.repeater = repeater;
    }

    private By makeJsBy() {
        return By.js(
                "var using = arguments[0] || document;\n" +
                "var repeater = '" + repeater + "';\n" +
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
                "return rows;");
    }

    public ByAngularRepeaterRow row(int row) {
        return new ByAngularRepeaterRow(repeater, row);
    }

    public ByAngularRepeaterColumn column(String column) {
        return new ByAngularRepeaterColumn(repeater, column);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return makeJsBy().findElement(context);
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        return makeJsBy().findElements(searchContext);
    }

}
