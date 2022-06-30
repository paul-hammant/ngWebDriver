package com.paulhammant.ngwebdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.paulhammant.ngwebdriver.NgWebDriver.DEFAULT_ROOT_SELECTOR;

public class ByAngular {

    protected static final Map<String, String> functions = new HashMap<String, String>();

    static {
        // in a Jar
        InputStream resourceAsStream = ByAngular.class.getResourceAsStream("/js/clientsidescripts.js");
        if (resourceAsStream == null) {
            try {
                // In ngWebDriver's own build (fallback)
                resourceAsStream = new FileInputStream(new File("src/main/resources/js/clientsidescripts.js"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Could not find clientsidescripts.js in file system or in jar", e);
            }
        }
        String src = new Scanner(resourceAsStream, "UTF-8").useDelimiter("\\A").next();
        iterateOverJsFunctionsInSource(src);
        inlineUtilityFunctionsIfNeeded();

    }

    private static void iterateOverJsFunctionsInSource(String src) {
        Pattern ps = Pattern.compile("^function.* \\{$", Pattern.MULTILINE);
        Pattern pe = Pattern.compile("^\\}", Pattern.MULTILINE);
        Matcher m = ps.matcher(src);
        boolean more = true;
        while (more && m.find()) {
            src = src.substring(m.start());
            Matcher m2 = pe.matcher(src);
            if (m2.find()) {
                String body = src.substring(0, m2.start());
                storeJavaScriptFunction(body);
                src = src.substring(body.length());
                m = ps.matcher(src);
            } else {
                more = false;
            }
        }
    }

    private static void inlineUtilityFunctionsIfNeeded() {
        for (String functionName : functions.keySet()) {
            String functionBody = functions.get(functionName);
            if (!functionName.equals("repeaterMatch") && functionBody.indexOf("repeaterMatch") > 0) {
                functionBody = "var repeaterMatch = function(ngRepeat, repeater, exact) {" + functions.get("repeaterMatch") + "}\n" + functionBody;
            }
            if (!functionName.equals("getNg1Hooks") && functionBody.indexOf("getNg1Hooks") > 0) {
                functionBody = "var getNg1Hooks = function(selector, injectorPlease) {" + functions.get("getNg1Hooks") + "}\n" + functionBody;
            }
            functions.put(functionName, functionBody);
        }
    }

    private static void storeJavaScriptFunction(String body) {
        Pattern regFn = Pattern.compile("^function ([a-zA-Z0-9]+)\\(", Pattern.MULTILINE);
        Matcher m = regFn.matcher(body);
        String fnName;
        if (m.find()) {
            fnName = m.group(1);
        } else {
            Pattern fnPro = Pattern.compile("^functions\\.([a-zA-Z0-9]+) ", Pattern.MULTILINE);
            Matcher m2 = fnPro.matcher(body);
            if (m2.find()) {
                fnName = m2.group(1);
            } else {
                return;
            }
        }
        functions.put(fnName, body.substring(body.indexOf("{")+1));
    }

    private ByAngular() {
    }

    /**
     * Use the ByAngular selectors withRootSelector a different rootSelector
     * @param rootSelector like "[ng-app]" (which is the default)
     * @return
     */
    public static Factory withRootSelector(String rootSelector) {
        return new Factory(rootSelector);
    }

    public static ByAngularRepeater repeater(String repeater) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).repeater(repeater);
    }

    public static ByAngularRepeater exactRepeater(String repeater) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).exactRepeater(repeater);
    }

    public static ByAngularBinding binding(String binding) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).binding(binding);
    }

    public static ByAngularExactBinding exactBinding(String exactBinding) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).exactBinding(exactBinding);
    }

    public static ByAngularModel model(String model) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).model(model);
    }

    public static ByAngularOptions options(String options) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).options(options);
    }

    public static ByAngularButtonText buttonText(String buttonText) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).buttonText(buttonText);
    }

    public static ByAngularPartialButtonText partialButtonText(String partialButtonText) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).partialButtonText(partialButtonText);
    }
    public static ByAngularCssContainingText cssContainingText(String cssSelector, String searchText) {
        return withRootSelector(DEFAULT_ROOT_SELECTOR).cssContainingText(cssSelector,searchText);
    }

    public abstract static class BaseBy extends By {

        protected final String rootSelector;

        protected BaseBy(String rootSelector) {
            this.rootSelector = rootSelector;
        }

        protected final JavascriptExecutor getJavascriptExecutor(SearchContext context) {
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

    public static class Factory {

        private String rootSelector;

        Factory(String rootSelector) {
            this.rootSelector = rootSelector;
        }

        public ByAngularRepeater repeater(String repeater) {
            return new ByAngularRepeater(rootSelector, repeater, false);
        }

        public ByAngularRepeater exactRepeater(String repeater) {
            return new ByAngularRepeater(rootSelector, repeater, true);
        }

        public ByAngularBinding binding(String binding) {
            return new ByAngularBinding(rootSelector, binding);
        }

        public ByAngularExactBinding exactBinding(String exactBinding) {
            return new ByAngularExactBinding(rootSelector, exactBinding);
        }

        public ByAngularModel model(String model) {
            return new ByAngularModel(rootSelector, model);
        }

        public ByAngularOptions options(String options) {
            return new ByAngularOptions(rootSelector, options);
        }

        public ByAngularButtonText buttonText(String buttonText) {
            return new ByAngularButtonText(rootSelector, buttonText);
        }
        public ByAngularPartialButtonText partialButtonText(String partialButtonText) {
            return new ByAngularPartialButtonText(rootSelector, partialButtonText);
        }
        public ByAngularCssContainingText cssContainingText(String cssSelector, String searchText) {
            return new ByAngularCssContainingText(rootSelector, cssSelector,searchText);
        }


    }

}
