package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static com.paulhammant.ngwebdriver.NgWebDriver.DEFAULT_ROOT_SELECTOR;


public class ByAngularCssContainingText extends ByAngular.BaseBy {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(NgFindByBuilder.class)
    public @interface FindBy {
        String rootSelector() default DEFAULT_ROOT_SELECTOR;
        String cssSelector();
        String searchText();
    }

    public static class NgFindByBuilder extends AbstractFindByBuilder {
        @Override
        public By buildIt(final Object annotation, Field field) {
            final FindBy findBy = (FindBy) annotation;
            return new ByAngularCssContainingText(findBy.rootSelector(), findBy.cssSelector(), findBy.searchText());
        }
    }

    public ByAngularCssContainingText(String rootSelector, String cssSelector, String searchText) {
        super(rootSelector);
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
