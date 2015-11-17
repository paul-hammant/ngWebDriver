package com.paulhammant.ngwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

public class ByAngular {

    protected final JavascriptExecutor jse;

    public ByAngular(JavascriptExecutor jse) {
        this.jse = jse;
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
