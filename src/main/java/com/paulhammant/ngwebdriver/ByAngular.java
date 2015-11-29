package com.paulhammant.ngwebdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.*;

public class ByAngular extends AngularJavaScriptFunctions {
    
    private ByAngular() {
    }

    public static ByAngularRepeater repeater(String repeater) {
        return new ByAngularRepeater(repeater, false);
    }

    public static ByAngularRepeater exactRepeater(String repeater) {
        return new ByAngularRepeater(repeater, true);
    }

    public static ByAngularBinding binding(String binding) {
        return new ByAngularBinding(binding);
    }

    public static ByAngularExactBinding exactBinding(String exactBinding) {
        return new ByAngularExactBinding(exactBinding);
    }

    public static ByAngularModel model(String model) {
        return new ByAngularModel(model);
    }

    public static ByAngularOptions options(String options) {
        return new ByAngularOptions(options);
    }

    public static ByAngularButtonText buttonText(String buttonText) {
        return new ByAngularButtonText(buttonText);
    }

    protected abstract static class BaseBy extends By {

        private final JavascriptExecutor getJavascriptExecutor(SearchContext context) {
            JavascriptExecutor jse;
            if (context instanceof RemoteWebElement) {
                jse = (JavascriptExecutor) ((RemoteWebElement) context).getWrappedDriver();
            } else {
                jse = (JavascriptExecutor) context;
            }
            return jse;
        }

        protected final Object errorIfNull(Object o) {
            if (o == null || o instanceof List && ((List) o).size() == 0) {
                throw new NoSuchElementException(this + " didn't have any matching elements at this place in the DOM");
            }
            return o;
        }

        @Override
        public WebElement findElement(SearchContext context) {
            JavascriptExecutor javascriptExecutor = getJavascriptExecutor(context);
            if (context instanceof WebDriver) {
                context = null;
            }
            return ((List<WebElement>) errorIfNull(getObject(context, javascriptExecutor))).get(0);
        }

        protected abstract Object getObject(SearchContext context, JavascriptExecutor javascriptExecutor);

        @Override
        public List<WebElement> findElements(SearchContext context) {
            JavascriptExecutor javascriptExecutor = getJavascriptExecutor(context);
            if (context instanceof WebDriver) {
                context = null;
            }
            return (List<WebElement>) errorIfNull(getObject(context, javascriptExecutor));
        }
    }
}
