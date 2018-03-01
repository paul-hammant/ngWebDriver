package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;

import static com.paulhammant.ngwebdriver.NgWebDriver.DEFAULT_ROOT_SELECTOR;

public class ByAngularRepeaterCell extends ByAngular.BaseBy {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(NgFindByBuilder.class)
    public @interface FindBy {
        String rootSelector() default DEFAULT_ROOT_SELECTOR;
        String repeater();
        boolean exact();
        int row();
        String column();
    }

    public static class NgFindByBuilder extends AbstractFindByBuilder {
        @Override
        public By buildIt(final Object annotation, Field field) {
            final FindBy findBy = (FindBy) annotation;
            return new ByAngularRepeaterCell(findBy.rootSelector(), findBy.repeater(), findBy.exact(), findBy.row(), findBy.column());
        }
    }

    private final String repeater;
    private boolean exact;
    private final int row;
    private final String column;

    public ByAngularRepeaterCell(String rootSelector, String repeater, boolean exact, int row, String column) {
        super(rootSelector);
        this.repeater = repeater;
        this.exact = exact;
        this.row = row;
        this.column = column;
    }

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = '" + rootSelector + "';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var index = " + row + ";\n" +
                            "var binding = '" + column + "';\n" +
                            "var exact = " + exact + ";\n" +
                            "\n" +
                            ByAngular.functions.get("findRepeaterElement")
                    , context);
    }

    // meaningless
    @Override
    public List<WebElement> findElements(SearchContext searchContext) {
        throw new UnsupportedOperationException("This locator zooms in on a single cell, findElements() is meaningless");
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ").row(" + row + ").column(" + column + ")";
    }

}
