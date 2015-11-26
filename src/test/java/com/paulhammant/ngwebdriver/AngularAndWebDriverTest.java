package com.paulhammant.ngwebdriver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentBy;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.openqa.selenium.support.ui.Select;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.paulhammant.ngwebdriver.WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.By.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.fail;

public class AngularAndWebDriverTest {

    private FirefoxDriver driver;
    private ByAngular byNg;
    private Server webServer;

    @BeforeTest
    public void setup() throws Exception {

        // Launch Protractor's own test app on http://localhost:8080
        webServer = new Server(new QueuedThreadPool(5));
        ServerConnector connector = new ServerConnector(webServer, new HttpConnectionFactory());
        connector.setPort(8080);
        webServer.addConnector(connector);
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase("src/test/webapp");
        HandlerList handlers = new HandlerList();
        MovedContextHandler effective_symlink = new MovedContextHandler();
        effective_symlink.setNewContextURL("http://localhost:8080/lib/angular_v1.2.9");
        effective_symlink.setContextPath("/lib/angular");
        effective_symlink.setPermanent(false);
        effective_symlink.setDiscardPathInfo(false);
        effective_symlink.setDiscardQuery(false);
        handlers.setHandlers(new Handler[] { effective_symlink, resource_handler, new DefaultHandler() });
        webServer.setHandler(handlers);
        webServer.start();
        webServer.dumpStdErr();

        driver = new FirefoxDriver();
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        byNg = new ByAngular(driver);
    }

    @AfterTest
    public void tear_down() throws Exception {
        driver.quit();
        webServer.stop();
    }

    @Test
    public void find_by_angular_model() {

        //driver.get("http://www.angularjshub.com/code/examples/basics/02_TwoWayDataBinding_HTML/index.demo.php");
        driver.get("http://localhost:8080/");
        waitForAngularRequestsToFinish(driver);

        WebElement firstname = driver.findElement(byNg.model("username"));
        firstname.clear();
        firstname.sendKeys("Mary");
        assertEquals(driver.findElement(xpath("//input")).getAttribute("value"), "Mary");

    }

    @Test
    public void find_all_for_an_angular_options() {

        driver.get("http://localhost:8080/#/form");
        waitForAngularRequestsToFinish(driver);

        List<WebElement> weColors = driver.findElements(byNg.options("fruit for fruit in fruits"));
        assertThat(weColors.get(0).getText(), containsString("apple"));
        assertThat(weColors.get(3).getText(), containsString("banana"));

    }

    @Test
    public void find_multiple_hits_for_ng_repeat_in_page() {

        driver.get("http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        List<WebElement> wes = driver.findElement(tagName("table")).findElements(byNg.repeater("person in people"));

        assertThat(wes.size(), is(4));
        assertThat(wes.get(0).findElement(tagName("td")).getText(), containsString("John"));
        assertThat(wes.get(1).findElement(tagName("td")).getText(), containsString("Bob"));
        assertThat(wes.get(2).findElement(tagName("td")).getText(), containsString("Jack"));
        assertThat(wes.get(3).findElement(tagName("td")).getText(), containsString("Michael"));

    }

    @Test
    public void find_multiple_hits_for_ng_repeat_and_subset_to_first_matching_predicate_for_fluent_selenium_example() {

        // As much as anything, this is a test of FluentSelenium

        driver.get("http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        new FluentWebDriver(driver).lis(byNg.repeater("(propName, propValue) in person"))
                .first(new TextContainsTerm("name ="))
                .getText().shouldContain("John");

    }

    public static class TextContainsTerm implements FluentMatcher {

        private String term;

        public TextContainsTerm(String term) {
            this.term = term;
        }

        public boolean matches(WebElement webElement) {
            return webElement.getText().indexOf(term) > -1;
        }

        @Override
        public String toString() {
            return "TextContainsTerm{term='" + term + "'}";
        }
    }

    @Test
    public void find_second_row_in_ng_repeat() {

        driver.get("http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        // find the second person
        // index starts with 1 (Javascript)

        assertThat(driver.findElement(byNg.repeater("person in people").row(2)).getText(), is("Bob Smith"));

    }

    @Test(enabled = false)
    public void find_specific_cell_in_ng_repeat() {

        driver.get("http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        driver.findElement(byNg.repeater("person in selectablePeople").row(2).column("person.isSelected")).click();

        assertThat(driver.findElement(xpath("//tr[@byNg-repeat='person in selectablePeople']")).getText(), is("x y z"));
    }

    @Test(enabled = false)
    public void find_specific_cell_in_ng_repeat_the_other_way() {

        driver.get("http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        // find the second address' city
        driver.findElement(byNg.repeater("person in selectablePeople").column("person.isSelected").row(2)).click();

        assertThat(driver.findElement(xpath("//tr[@byNg-repeat='person in selectablePeople']")).getText(), is("x y z"));
    }

    @Test(enabled = false)
    public void find_all_of_a_column_in_an_ng_repeat() {

        driver.get("http://www.angularjshub.com/code/examples/collections/01_Repeater/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        List<WebElement> we = driver.findElements(byNg.repeater("person in selectablePeople").column("person.isSelected"));

        assertThat(we.get(0).getText(), is("unselcted"));
        we.get(1).click();
        assertThat(we.get(1).getText(), is("selected"));
        assertThat(we.get(2).getText(), is("unselected"));
    }

    @Test
    public void find_by_angular_binding() {

        driver.get("http://localhost:8080/#/form");
        waitForAngularRequestsToFinish(driver);

        List<WebElement> wes = driver.findElements(byNg.binding("username"));
        assertThat(wes.get(0).getText(), is("Anon"));
    }

    @Test
    public void find_all_for_an_angular_binding() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        List<WebElement> wes = driver.findElements(byNg.binding("peopleArrayValue4"));

        assertThat(wes.get(0).getTagName(), is("textarea"));

        // really need an example that return more than one.

    }

    // Model interaction

    @Test
    public void model_mutation_and_query_is_possible() {

        driver.get("http://www.angularjshub.com/code/examples/forms/08_FormSubmission/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        WebElement fn = driver.findElement(id("firstNameEdit1"));
        fn.sendKeys("Fred");
        WebElement ln = driver.findElement(id("lastNameEdit1"));
        ln.sendKeys("Flintstone");

        WebElement wholeForm = driver.findElement(FluentBy.attribute("name", "personForm1"));

        AngularModelAccessor ngModel = new AngularModelAccessor(driver);

        // change something via the $scope model
        ngModel.mutate(wholeForm, "person1.firstName", "'Wilma'");
        // assert the change happened via regular WebDriver.
        assertThat(fn.getAttribute("value"), containsString("Wilma"));

        // retrieve the JSON for the location via the $scope model
        String tv = ngModel.retrieveJson(wholeForm, "person1");
        assertThat(tv, is("{\"firstName\":\"Wilma\",\"lastName\":\"Flintstone\"}"));

        // retrieve a single field as JSON
        String v = ngModel.retrieveJson(wholeForm, "person1.firstName");

        // assert that it comes back indicating its type (presence of quotes)
        assertThat(v, is("\"Wilma\""));

        // retrieve it again, but directly as String
        v = ngModel.retrieveAsString(wholeForm, "person1.firstName");

        // assert it is still what we expect
        assertThat(v, is("Wilma"));


        // WebDriver naturally hands back as a Map if it is not one
        // variable..
        Object rv = ngModel.retrieve(wholeForm, "person1");
        assertThat(((Map) rv).get("firstName").toString(), is("Wilma"));

        // If something is numeric, WebDriver hands that back
        // naturally as a long.
//        long id = ngModel.retrieveAsLong(wholeForm, "location.Id");
//        assertThat(id, is(1675L));

        // Can't process scoped variables that don't exist
        try {
            ngModel.retrieve(wholeForm, "person1.Cityyyyyyy");
            fail("should have barfed");
        } catch (WebDriverException e) {
            assertThat(e.getMessage(), startsWith("$scope variable 'person1.Cityyyyyyy' not found in same scope as the element passed in."));
        }
        // Can't process scoped variables that don't exist
        try {
            ngModel.retrieveJson(wholeForm, "locationnnnnnnnn");
        } catch (WebDriverException e) {
            assertThat(e.getMessage(), startsWith("$scope variable 'locationnnnnnnnn' not found in same scope as the element passed in."));
        }
        // Can't process scoped variables that don't exist
        try {
            ngModel.retrieveAsLong(wholeForm, "person1.Iddddddd");
        } catch (WebDriverException e) {
            assertThat(e.getMessage(), startsWith("$scope variable 'person1.Iddddddd' not found in same scope as the element passed in."));
        }

        // You can set whole parts of the tree within the scope..
        ngModel.mutate(wholeForm, "person1",
                "{" +
                        "  firstName: 'Barney'," +
                        "  lastName: 'Rubble'" +
                        "}");

        assertEquals(fn.getAttribute("value"), "Barney");
        assertEquals(ln.getAttribute("value"), "Rubble");

        // Keys can be in quotes (single or double) or not have quotes at all.
        // Values can be in quotes (single or double) or not have quotes if
        // they are not of type string.

    }


    //  All the failure tests

    @Test
    public void findElement_should_barf_with_message_for_bad_repeater() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);


        try {
            driver.findElement(byNg.repeater("location in Locationssss"));
            fail("should have barfed");
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), startsWith("repeater(location in Locationssss) didn't have any matching elements at this place in the DOM"));
        }

    }

    @Test
    public void findElement_should_barf_with_message_for_bad_repeater_and_row() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);


        try {
            driver.findElement(byNg.repeater("location in Locationssss").row(99999));
            fail("should have barfed");
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), startsWith("repeater(location in Locationssss).row(99999) didn't have any matching elements at this place in the DOM"));
        }

    }

    @Test
    public void findElements_should_barf_with_message_for_any_repeater_and_row2() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);


        try {
            driver.findElements(byNg.repeater("location in Locationssss").row(99999));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), startsWith("This locator zooms in on a single row, findElements() is meaningless"));
        }

    }

    @Test
    public void findElement_should_barf_with_message_for_bad_repeater_and_row_and_column() {

        try {
            driver.findElement(byNg.repeater("location in Locationssss").row(99999).column("blort"));
            fail("should have barfed");
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), startsWith("repeater(location in Locationssss).row(99999).column(blort) didn't have any matching elements at this place in the DOM"));
        }
    }

    @Test
    public void findElements_should_barf_with_message_for_any_repeater_and_row_and_column() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);


        try {
            driver.findElements(byNg.repeater("location in Locationssss").row(99999).column("blort"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), startsWith("This locator zooms in on a single row, findElements() is meaningless"));
        }
    }

    @Test
    public void findElement_should_barf_with_message_for_any_repeater_and_column() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);


        try {
            driver.findElement(byNg.repeater("location in Locationssss").column("blort"));
            fail("should have barfed");
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), startsWith("This locator zooms in on a multiple cells, findElement() is meaningless"));
        }
    }

    @Test
    public void findElements_should_barf_with_message_for_bad_repeater_and_column() {

        driver.get("http://www.angularjshub.com/code/examples/forms/04_Select/index.demo.php");
        waitForAngularRequestsToFinish(driver);

        try {
            driver.findElements(byNg.repeater("location in Locationssss").column("blort"));
            fail("should have barfed");
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), startsWith("repeater(location in Locationssss).column(blort) didn't have any matching elements at this place in the DOM"));
        }
    }

    /*
      Ported from protractor/stress/spec.js
     */
    @Test
    public void stress_test() {
        FluentWebDriver fwd = new FluentWebDriver(driver);
        for (int i = 0; i < 20; ++i) {
            driver.get("http://localhost:8080/");

            FluentWebElement usernameInput = fwd.input(byNg.model("username"));
            FluentWebElement name = fwd.span(byNg.binding("username"));

            driver.get("http://localhost:8080/index.html#/form");
            waitForAngularRequestsToFinish(driver);

            name.getText().shouldBe("Anon");
            usernameInput.clearField().sendKeys("B");
            name.getText().shouldBe("B");
        }
    }

    /*
      Ported from protractor/spec/altRoot/findelements_spec.js
     */
    @Test
    public void find_elements() {
        FluentWebDriver fwd = new FluentWebDriver(driver);
        driver.get("http://localhost:8080/alt_root_index.html#/form");

        fwd.span(byNg.binding("{{greeting}}")).getText().shouldBe("Hiya");

        fwd.div(id("outside-ng")).getText().shouldBe("{{1 + 2}}");
        fwd.div(id("inside-ng")).getText().shouldBe("3");
    }



}
