package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ByAngularBinding extends By {

    public static ByAngularBinding ngBinding(String binding) {
        return new ByAngularBinding(binding);
    }

    private String binding;

    public ByAngularBinding(String binding) {
        this.binding = binding;
    }

    private By makeJsBy(String oneOrAll) {
        return By.js(
                "var using = arguments[0] || document;\n" +
                "var binding = '" + binding + "';\n" +
                "var bindings = using.getElementsByClassName('ng-binding');\n" +
                "var matches = [];\n" +
                "for (var i = 0; i < bindings.length; ++i) {\n" +
                "  var bindingName = angular.element(bindings[i]).data().$binding[0].exp ||\n" +
                "      angular.element(bindings[i]).data().$binding;\n" +
                "  if (bindingName.indexOf(binding) != -1) {\n" +
                "    matches.push(bindings[i]);\n" +
                "  }\n" +
                "}\n" +
                "return matches" + oneOrAll + ";"
        );
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return makeJsBy("[0]").findElement(context);
    }

    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        return makeJsBy("").findElements(searchContext);
    }

}
