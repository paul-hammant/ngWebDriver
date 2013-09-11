package com.paulhammant.ngwebdriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.tagName;

public class AngularAndWebDriverTest {

    private FirefoxDriver driver;
    private ByAngular ng;

    @BeforeTest
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.get("https://online.jimmyjohns.com/#/pickupresults/58");
        ng = new ByAngular(driver);
        waitForAngularRequestsToFinish(driver);
    }

    @AfterTest
    public void tear_down() {
        driver.quit();
    }

    @Test
    public void find_ng_repeat_in_page() {

        // find the second address
        List<WebElement> wes = driver.findElements(ng.repeater("location in Locations"));

        assertThat(wes.size(), is(3));
        assertThat(wes.get(0).findElement(className("addressContent")).getText(), containsString("Chicago, IL"));
        assertThat(wes.get(1).findElement(className("addressContent")).getText(), containsString("Chicago, IL"));
        assertThat(wes.get(2).findElement(className("addressContent")).getText(), containsString("Chicago, IL"));

    }

    @Test
    public void find_second_row_in_ng_repeat() {

        // find the second address
        WebElement we = driver.findElement(ng.repeater("location in Locations").row(2))
                .findElement(className("addressContent"));

        assertThat(getTextAndRemoveTimeOfDaySensitivePartOfAddress(we), is(
                "3328 N Clark St\n" +
                        "Chicago, IL\n" +
                        "773-244-9000\n" +
                        "min order $3.75"
        ));


    }

    private String getTextAndRemoveTimeOfDaySensitivePartOfAddress(WebElement we) {
        // if you run the tests before opening hours
        // the listing for the outlet in question, has the hours
        // that it opens as part of the address. This is a hack
        // to make that consistent for assertions :)
        return we.getText().replace("Today's hours: 10:30 am - 10 pm\n", "");
    }

    @Test
    public void find_third_row_in_ng_repeat_by_default_from_intermediate_node() {

        WebElement we = driver.findElement(tagName("body"))
                .findElement(ng.repeater("location in Locations").row(3))
                .findElement(className("addressContent"));

        assertThat(getTextAndRemoveTimeOfDaySensitivePartOfAddress(we), is(
                "46 E Chicago Ave\n" +
                        "Chicago, IL\n" +
                        "312-787-0100\n" +
                        "min order $3.00"
        ));
    }

    @Test
    public void find_specific_cell_in_ng_repeat() {

        // find the second address' city
        WebElement we = driver.findElement(ng.repeater("location in Locations").row(2).column("location.City"));

        assertThat(we.getText(), is("Chicago, IL"));
    }

    @Test
    public void find_specific_cell_in_ng_repeat_the_other_way() {

        // find the second address' city
        WebElement we = driver.findElement(ng.repeater("location in Locations").column("location.City").row(2));

        assertThat(we.getText(), is("Chicago, IL"));
    }

    @Test
    public void find_all_of_a_column_in_an_ng_repeat() {

        // find all the telephone numbers
        List<WebElement> we = driver.findElements(ng.repeater("location in Locations").column("location.Phone"));

        assertThat(we.get(0).getText(), is("312-733-8030"));
        assertThat(we.get(1).getText(), is("773-244-9000"));
        assertThat(we.get(2).getText(), is("312-787-0100"));
    }

    @Test
    public void find_by_angular_binding() {

        // find the first telephone number
        WebElement we = driver.findElement(ng.binding("location.Phone"));
        // could have been {{location.Phone}} too, or even ion.Pho

        assertThat(we.getText(), is("312-733-8030"));
    }

    @Test
    public void find_all_for_an_angular_binding() {

        // find all the telephone numbers
        List<WebElement> wes = driver.findElements(ng.binding("location.Phone"));

        assertThat(wes.get(0).getText(), is("312-733-8030"));
        assertThat(wes.get(1).getText(), is("773-244-9000"));
        assertThat(wes.get(2).getText(), is("312-787-0100"));

    }

    @Test
    public void model_mutation_and_query_is_possible() {

        WebElement we = driver.findElement(className("addressContent"));

        // assert the Starting position is true via regular WebDriver.
        assertThat(we.getText(), containsString("812 W Van Buren St\nChicago, IL"));

        AngularModelAccessor model = new AngularModelAccessor(driver);

        // change something via the $scope model
        model.mutate(we, "location.City", "'Narnia'");

        // assert the change happened via regular WebDriver.
        assertThat(we.getText(), containsString("812 W Van Buren St\nNarnia, IL"));

        // retrieve the JSON for the location via the $scope model
        String locn = model.retrieveJson(we, "location");

        // that is the JSON we expect.
        assertThat(locn.replace("\"","'"), containsString("{'Id':1675,'Name':'#0019 812 W Van Buren St','Abbreviation':'#0019'"));

        // retrieve a single field as JSON
        String city = model.retrieveJson(we, "location.City");

        // assert that it comes back indicating its type (presence of quotes)
        assertThat(city, is("\"Narnia\""));

        // retrieve it again, but directly as String
        city = model.retrieveAsString(we, "location.City");

        // assert it is still what we expect
        assertThat(city, is("Narnia"));

        // WebDriver can hand that back as a String
        Object rv = model.retrieve(we, "location.City");
        assertThat(rv.toString(), is("Narnia"));

        // WebDriver naturally hands back as a Map if it is not one
        // variable..
        rv = model.retrieve(we, "location");
        assertThat(((Map) rv).get("City").toString(), is("Narnia"));

        // If something is numeric, WebDriver hands that back
        // naturally as a long.
        long id  = model.retrieveAsLong(we, "location.Id");
        assertThat(id, is(1675L));

        // You can set whole parts of the tree within the scope..
        model.mutate(we, "location",
                "{" +
                "  AddressLine1: '1600 Pennsylvania Avenue NW'," +
                "  AddressLine2: ''," +
                "  City: 'Washington'," +
                "  State: 'DC'," +
                "  Zipcode: 20500" +
                "}");

        // keys can be in quotes (single or double) or not have quotes at all
        // values can be in quotes (single or double) or not habe quotes if
        // they are not of type string.

        WebElement addressContent = driver.findElement(className("addressContent"));
        assertThat(addressContent.getText(), containsString("1600 Pennsylvania Avenue NW\nWashington, DC\n"));

    }

}
