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

public class ByAngularRepeaterColumn extends ByAngular.BaseBy {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(NgFindByBuilder.class)
    public @interface FindBy {
        String rootSelector() default DEFAULT_ROOT_SELECTOR;
        String repeater();
        boolean exact();
        String column();
    }

    public static class NgFindByBuilder extends AbstractFindByBuilder {
        @Override
        public By buildIt(final Object annotation, Field field) {
            final FindBy findBy = (FindBy) annotation;
            return new ByAngularRepeaterColumn(findBy.rootSelector(), findBy.repeater(), findBy.exact(), findBy.column());
        }
    }

    private final String repeater;
    private boolean exact;
    private final String column;

    public ByAngularRepeaterColumn(String rootSelector, String repeater, boolean exact, String column) {
        super(rootSelector);
        this.repeater = repeater;
        this.exact = exact;
        this.column = column;
    }

    public ByAngularRepeaterCell row(int row) {
        return new ByAngularRepeaterCell(rootSelector, repeater, exact, row, column);
    }

    protected Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor) {
        return javascriptExecutor.executeScript(
                    "var using = arguments[0] || document;\n" +
                            "var rootSelector = '" + rootSelector + "';\n" +
                            "var repeater = '" + repeater.replace("'", "\\'") + "';\n" +
                            "var binding = '" + column + "';\n" +
                            "var exact = " + exact + ";\n" +
                            "\n" +
                            ByAngular.functions.get("findRepeaterColumn")
                    , context);
    }

    @Override
    public String toString() {
        return (exact? "exactR":"r") + "epeater(" + repeater + ").column(" + column + ")";
    }


}
