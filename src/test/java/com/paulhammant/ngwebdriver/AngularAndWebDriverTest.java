package com.paulhammant.ngwebdriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.paulhammant.ngwebdriver.ByAngularBinding.angularBinding;
import static com.paulhammant.ngwebdriver.ByAngularRepeater.angularRepeater;
import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.tagName;

public class AngularAndWebDriverTest {

    private FirefoxDriver driver;

    @BeforeTest
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.get("https://online.jimmyjohns.com/#/pickupresults/58");
        waitForAngularRequestsToFinish(driver);
    }

    @AfterTest
    public void tear_down() {
        driver.quit();
    }

    @Test
    public void find_ng_repeat_in_page() {

        // find the second address
        List<WebElement> wes = driver.findElements(angularRepeater("location in Locations"));

        assertThat(wes.size(), is(3));
        assertThat(wes.get(0).findElement(className("addressContent")).getText(), containsString("Chicago, IL"));
        assertThat(wes.get(1).findElement(className("addressContent")).getText(), containsString("Chicago, IL"));
        assertThat(wes.get(2).findElement(className("addressContent")).getText(), containsString("Chicago, IL"));


    }

    @Test
    public void find_second_row_in_ng_repeat() {

        // find the second address
        WebElement we = driver.findElement(angularRepeater("location in Locations").row(2))
                .findElement(className("addressContent"));

        assertThat(cleanup(we), is(
                "3328 N Clark St\n" +
                        "Chicago, IL\n" +
                        "773-244-9000\n" +
                        "min order $3.75"
        ));

    }

    private String cleanup(WebElement we) {
        return we.getText().replace("Today's hours: 10:30 am - 10 pm\n", "");
    }

    @Test
    public void find_third_row_in_ng_repeat_by_default_from_intermediate_node() {

        WebElement we = driver.findElement(tagName("body"))
                .findElement(angularRepeater("location in Locations").row(3))
                .findElement(className("addressContent"));

        assertThat(cleanup(we), is(
                "46 E Chicago Ave\n" +
                        "Chicago, IL\n" +
                        "312-787-0100\n" +
                        "min order $3.00"
        ));
    }

    @Test
    public void find_specific_cell_in_ng_repeat() {

        // find the second address
        WebElement we = driver.findElement(angularRepeater("location in Locations").row(2).column("location.City"));

        assertThat(we.getText(), is("Chicago, IL"));
    }

    @Test
    public void find_all_of_a_coumn_in_an_ng_repeat() {

        // find all the telephone numbers
        List<WebElement> we = driver.findElements(angularRepeater("location in Locations").column("location.Phone"));

        assertThat(we.get(0).getText(), is("312-733-8030"));
        assertThat(we.get(1).getText(), is("773-244-9000"));
        assertThat(we.get(2).getText(), is("312-787-0100"));
    }

    @Test
    public void find_by_angular_binding() {

        // find the first telephone number
        WebElement we = driver.findElement(angularBinding("location.Phone"));        
        // could have been {{location.Phone}} too, or even ion.Pho

        assertThat(we.getText(), is("312-733-8030"));
    }

    @Test
    public void find_all_for_an_angular_binding() {

        // find all the telephone numbers
        List<WebElement> wes = driver.findElements(angularBinding("location.Phone"));

        assertThat(wes.get(0).getText(), is("312-733-8030"));
        assertThat(wes.get(1).getText(), is("773-244-9000"));
        assertThat(wes.get(2).getText(), is("312-787-0100"));

    }

    @Test
    public void model_mutation_and_query_is_possible() {

        WebElement we = driver.findElement(className("addressContent"));

        assertThat(we.getText(), containsString("812 W Van Buren St\nChicago, IL"));

        AngularModelAccessor model = new AngularModelAccessor(driver);
        model.mutate(we, "location.City", "'Narnia'");

        assertThat(we.getText(), containsString("812 W Van Buren St\nNarnia, IL"));

        String locn = model.retrieveJson(we, "location");

        assertThat(locn.replace("\"","'"), containsString("{'Id':1675,'Name':'#0019 812 W Van Buren St','Abbreviation':'#0019'"));

        String city = model.retrieveJson(we, "location.City");

        assertThat(city, is("\"Narnia\""));

        city = model.retrieveAsString(we, "location.City");

        assertThat(city, is("Narnia"));

        Object rv = model.retrieve(we, "location.City");

        assertThat(rv.toString(), is("Narnia"));

        rv = model.retrieve(we, "location");

        assertThat(((Map) rv).get("City").toString(), is("Narnia"));

        Long id  = model.retrieveAsLong(we, "location.Id");

        assertThat(id, is(1675L));

    }


}
