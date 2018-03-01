package com.paulhammant.ngwebdriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.testng.annotations.Test;

public class FindByNgWebDriverTest {

    @ByAngularBinding.FindBy(rootSelector = "butter", binding = "cheese")
    public WebElement findBy_binding;

    @ByAngularButtonText.FindBy(rootSelector = "butter", buttonText = "cheese")
    public WebElement findBy_buttonText;

    @ByAngularCssContainingText.FindBy(rootSelector = "butter", cssSelector = "cheese", searchText = "crackers")
    public WebElement findBy_cssContainingText;

    @ByAngularExactBinding.FindBy(rootSelector = "butter", exactBinding = "cheese")
    public WebElement findBy_exactBinding;

    @ByAngularModel.FindBy(rootSelector = "butter", model = "cheese")
    public WebElement findBy_model;

    @ByAngularOptions.FindBy(rootSelector = "butter", options = "cheese")
    public WebElement findBy_options;

    @ByAngularPartialButtonText.FindBy(rootSelector = "butter", partialButtonText = "cheese")
    public WebElement findBy_partialButtonText;

    @ByAngularRepeater.FindBy(rootSelector = "butter", repeater = "cheese", exact = true)
    public WebElement findBy_repeater;

    @ByAngularRepeaterCell.FindBy(rootSelector = "butter", repeater = "cheese", exact = true, row = 99, column = "cracker")
    public WebElement findBy_repeaterCell;

    @ByAngularRepeaterColumn.FindBy(rootSelector = "butter", repeater = "cheese", exact = true, column = "cracker")
    public WebElement findBy_repeaterColumn;

    @ByAngularRepeaterRow.FindBy(rootSelector = "butter", repeater = "cheese", exact = true, row = 99)
    public WebElement findBy_repeaterRow;

    @Test
    public void findByAngularBinding() throws Exception {
        ByAngularBinding built = (ByAngularBinding) new Annotations(getClass().getField("findBy_binding")).buildBy();
        ByAngularBinding expected = new ByAngularBinding("butter", "cheese");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByButtonText() throws Exception {
        ByAngularButtonText built = (ByAngularButtonText) new Annotations(getClass().getField("findBy_buttonText")).buildBy();
        ByAngularButtonText expected = new ByAngularButtonText("butter", "cheese");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByCssContainingText() throws Exception {
        ByAngularCssContainingText built = (ByAngularCssContainingText) new Annotations(getClass().getField("findBy_cssContainingText")).buildBy();
        ByAngularCssContainingText expected = new ByAngularCssContainingText("butter", "cheese", "crackers");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByExactBinding() throws Exception {
        ByAngularExactBinding built = (ByAngularExactBinding) new Annotations(getClass().getField("findBy_exactBinding")).buildBy();
        ByAngularExactBinding expected = new ByAngularExactBinding("butter", "cheese");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByModel() throws Exception {
        ByAngularModel built = (ByAngularModel) new Annotations(getClass().getField("findBy_model")).buildBy();
        ByAngularModel expected = new ByAngularModel("butter", "cheese");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByOptions() throws Exception {
        ByAngularOptions built = (ByAngularOptions) new Annotations(getClass().getField("findBy_options")).buildBy();
        ByAngularOptions expected = new ByAngularOptions("butter", "cheese");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByPartialButtonText() throws Exception {
        ByAngularPartialButtonText built = (ByAngularPartialButtonText) new Annotations(getClass().getField("findBy_partialButtonText")).buildBy();
        ByAngularPartialButtonText expected = new ByAngularPartialButtonText("butter", "cheese");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByRepeater() throws Exception {
        ByAngularRepeater built = (ByAngularRepeater) new Annotations(getClass().getField("findBy_repeater")).buildBy();
        ByAngularRepeater expected = new ByAngularRepeater("butter", "cheese", true);
        assertThat(built, equalTo(expected));
    }
    
    @Test
    public void findByRepeaterCell() throws Exception {
        ByAngularRepeaterCell built = (ByAngularRepeaterCell) new Annotations(getClass().getField("findBy_repeaterCell")).buildBy();
        ByAngularRepeaterCell expected = new ByAngularRepeaterCell("butter", "cheese", true,  99,  "cracker");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByRepeaterColumn() throws Exception {
        ByAngularRepeaterColumn built = (ByAngularRepeaterColumn) new Annotations(getClass().getField("findBy_repeaterColumn")).buildBy();
        ByAngularRepeaterColumn expected = new ByAngularRepeaterColumn("butter", "cheese", true,  "cracker");
        assertThat(built, equalTo(expected));
    }

    @Test
    public void findByRepeaterRow() throws Exception {
        ByAngularRepeaterRow built = (ByAngularRepeaterRow) new Annotations(getClass().getField("findBy_repeaterRow")).buildBy();
        ByAngularRepeaterRow expected = new ByAngularRepeaterRow("butter", "cheese", true,  99);
        assertThat(built, equalTo(expected));
    }

}

