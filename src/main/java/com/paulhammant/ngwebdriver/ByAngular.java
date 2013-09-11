package com.paulhammant.ngwebdriver;

import org.openqa.selenium.JavascriptExecutor;

public class ByAngular {

    private JavascriptExecutor jse;

    public ByAngular(JavascriptExecutor jse) {
        this.jse = jse;
    }

    public ByAngularRepeater repeater(String repeater) {
        return new ByAngularRepeater(jse, repeater);
    }

    public ByAngularBinding binding(String binding) {
        return new ByAngularBinding(jse, binding);
    }



}
