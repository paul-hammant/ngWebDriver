package com.paulhammant.ngwebdriver;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.openqa.selenium.By.xpath;

@RunWith(OleasterRunner.class)
public class OleasterIntroductionTest {
    private static FirefoxDriver driver;
    private static Server webServer;
    private static NgWebDriver ngWebDriver;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // Launch Protractor's own test app on http://localhost:8080
        ((StdErrLog) Log.getRootLogger()).setLevel(StdErrLog.LEVEL_OFF);
        webServer = new Server(new QueuedThreadPool(5));
        ServerConnector connector = new ServerConnector(webServer, new HttpConnectionFactory());
        connector.setPort(8080);
        webServer.addConnector(connector);
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase("src/test/webapp");
        HandlerList handlers = new HandlerList();
        MovedContextHandler effective_symlink = new MovedContextHandler(webServer, "/lib/angular", "/lib/angular_v1.2.9");
        handlers.setHandlers(new Handler[] { effective_symlink, resource_handler, new DefaultHandler() });
        webServer.setHandler(handlers);
        webServer.start();

        driver = new FirefoxDriver();
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        ngWebDriver = new NgWebDriver(driver);
    }


    {
    describe("Some specs", () -> {

        beforeEach(() -> {
            driver.get("about:blank");
        });

        it("should find by angular model", () -> {
            driver.get("http://localhost:8080/");
            ngWebDriver.waitForAngularRequestsToFinish();
            WebElement firstname = driver.findElement(ByAngular.model("username"));
            firstname.clear();
            firstname.sendKeys("Mary");
            expect(driver.findElement(xpath("//input")).getAttribute("value")).toEqual("Mary");
        });

        it("should find all for angular options", () -> {
            driver.get("http://localhost:8080/#/form");
            ngWebDriver.waitForAngularRequestsToFinish();

            List<WebElement> weColors = driver.findElements(ByAngular.options("fruit for fruit in fruits"));
            expect(weColors.get(0).getText()).toContain("apple");
            expect(weColors.get(3).getText()).toContain("banana");
        });

    });

    }
    @AfterClass
    public static void afterClass() throws Exception {
        driver.quit();
        webServer.stop();
    }

}