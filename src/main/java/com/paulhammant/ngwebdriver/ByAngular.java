package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByAngular {

    protected final JavascriptExecutor jse;
    protected static final Map<String, String> functions = new HashMap<String, String>();

    public ByAngular(JavascriptExecutor jse) {
        this.jse = jse;
    }

    static {
        // in a Jar
        InputStream resourceAsStream = ByAngular.class.getResourceAsStream("js/clientsidescripts.js");
        if (resourceAsStream == null) {
            try {
                // In ngWebDriver's own build (fallback)
                resourceAsStream = new FileInputStream(new File("src/main/resources/js/clientsidescripts.js"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        String src = new Scanner(resourceAsStream, "UTF-8").useDelimiter("\\A").next();
        iterateOverJsFunctionsInSource(src);

        inlineUtilityFunctions();

        // Debugging
        //System.out.println(new XStream().toXML(functions));

    }

    private static void inlineUtilityFunctions() {
        Set<String> keys = functions.keySet();
        for (String key : keys) {
            String val = functions.get(key);
            if (!key.equals("repeaterMatch") && val.indexOf("repeaterMatch") > 0) {
                val = "var repeaterMatch = function(ngRepeat, repeater, exact) {" + functions.get("repeaterMatch") + "}\n" + val;
                functions.put(key, val);
            }
        }
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

    private static void storeJavaScriptFunction(String body) {
        Pattern regFn = Pattern.compile("^function ([a-zA-Z]+)\\(", Pattern.MULTILINE);
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
                throw new UnsupportedOperationException(body.substring(0, 40));
            }
        }
        functions.put(fnName, body.substring(body.indexOf("{")+1));
    }

    public ByAngularRepeater repeater(String repeater) {
        return new ByAngularRepeater(jse, repeater);
    }

    public ByAngularBinding binding(String binding) {
        return new ByAngularBinding(jse, binding);
    }
     public ByAngularModel model(String model) {
        return new ByAngularModel(jse, model);
    }

    public ByAngularOptions options(String options) {
        return new ByAngularOptions(jse,options);
    }

    public abstract static class BaseBy extends By {

        protected final JavascriptExecutor jse;

        public BaseBy(JavascriptExecutor jse) {
            this.jse = jse;
        }

        protected final void errorIfNull(Object o) {
            if (o == null || o instanceof List && ((List) o).size() == 0) {
                throw new NoSuchElementException(this + " didn't have any matching elements at this place in the DOM");
            }
        }

    }
}
