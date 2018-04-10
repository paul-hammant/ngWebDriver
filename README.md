# ngWebDriver

A small library of WebDriver locators and more for AngularJS (v1.x) and Angular (v2.x +), for Java.

# Status

We have taken JavaScript from Angular's [Protractor](https://github.com/angular/protractor) project. While ngWebDriver perfectly
compliments the Java version of WebDriver, it has to pass JavaScript up to the browser to interoperate with Angular, and the
Protractor project has done the hard work (including testing) to make that solid. This project benefits from that work.

You can use ngWebDriver today with the regular Java Selenium2/WebDriver libraries. You can also use it with
[FluentSelenium](https://github.com/SeleniumHQ/fluent-selenium) for extra terseness.


# Compatibility

Like Protractor, ngWebDriver works with Angular versions greater than 1.0.6/1.1.4, and is compatible with Angular 2 applications. 
Note that for Angular 2 apps, the `binding` and `model` locators are not supported. We recommend using `by.css`.

## Waiting for Angular to finish async activity

```java
ChromeDriver driver = new ChromeDriver();
NgWebDriver ngWebDriver = new NgWebDriver(driver);
ngWebDriver.waitForAngularRequestsToFinish();
```
Do this if WebDriver can possibly run ahead of Angular's ability finish it's MVC stuff in your application.

## Locators

### repeater()

[As Protractor's repeater locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.repeater)

```java
ByAngular.repeater("foo in f")
ByAngular.repeater("foo in f").row(17)
ByAngular.repeater("foo in f").row(17).column("foo.name")
ByAngular.repeater("foo in f").column("foo.name")
```

### exactRepeater()

[As Protractor's exactRepeater](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.exactRepeater)

```java
ByAngular.exactRepeater("foo in foos")
ByAngular.exactRepeater("foo in foos").row(17)
ByAngular.exactRepeater("foo in foos").row(17).column("foo.name")
ByAngular.exactRepeater("foo in foos").column("foo.name")
```


### binding()

[As Protractor's binding locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.binding)

```java
ByAngular.binding("person.name")
ByAngular.binding("{{person.name}}")
// You can also use a substring for a partial match
ByAngular.binding("person");

```

### exactBinding()

[As Protractor's exactBinding locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.exactBinding)

```java
ByAngular.exactBinding("person.name")
ByAngular.exactBinding("{{person.name}}")
```

### model()

[As Protractor's model locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.model)

```java
ByAngular.model('person.name')
```

### options()

[As Protractor's options locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.options)

```java
ByAngular.options("c for c in colors")
```
### buttonText()

[As Protractor's buttonText locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.buttonText)

```java
ByAngular.buttonText("cLiCk mE")
```

### partialButtonText()

[As Protractor's partialButtonText locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.partialButtonText)

```java
// If you have a button name "Click me to open", using just "click" would do if you partialButtonText
ByAngular.partialButtonText("cLiCk ")
```


### cssContainingText()

[As Protractor's cssContainingText locator](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.cssContainingText)

```java
ByAngular.cssContainingText("#animals ul .pet", "dog")
```

## Angular model interop

As with Protractor, you can change items in an Angular model, or retrieve them regardless of whether they appear in the UI or not.

### Changing model variables

```java
NgWebDriver ngWebDriver = new NgWebDriver(driver);
// change something via the model defined in $scope
ngWebDriver.mutate(formElement, "person.name", "'Wilma'");
// Note Wilma wrapped in single-quotes as it has to be a valid JavaScript
// string literal when it arrives browser-side for execution
```

### Getting model variables

*As a JSON string:*

```java
NgWebDriver ngWebDriver = new NgWebDriver(driver);
// Get something via the model defined in $scope
String personJson = ngWebDriver.retrieveJson(formElement, "person");
```

*As a string:*

```java
NgWebDriver ngWebDriver = new NgWebDriver(driver);
// Get something via the model defined in $scope
String personName = ngWebDriver.retrieveAsString(formElement, "person.name");
```

*As a number:*

```java
NgWebDriver ngWebDriver = new NgWebDriver(driver);
// Get something via the model defined in $scope
Long personAge = ngWebDriver.retrieveAsLong(formElement, "person.age");
```

*As Map/dict:*

```java
NgWebDriver ngWebDriver = new NgWebDriver(driver);
// Get something via the model defined in $scope
Map person = (Map) ngWebDriver.retrieve(formElement, "person");
// note - could be List instead of a Map - WebDriver makes a late decision on that
```

## Helping NgWebDriver find Angular apps in a page

**Getting "Cannot read property '$$testability' of undefined" ?**

If you're seeing `$$testability` referenced in a WebDriver error, then work out in you have 
correctly set selector for the "root element" on the Angular app. Specifically have you 
used `.withRootSelector(..)` in situations where you need to use it? Details about how to recover from that here...

To specify a different "root selector" to help ngWebDriver find the Angular app, take a look at what is defined in the `ng-app` attribute in the page in question. Examples:

```
NgWebDriver ngwd = new NgWebDriver(webDriver).withRootSelector("something-custom");
ByAngular.Factory factory = ngwd.makeByAngularFactory()
factory.exactRepeater("day in days");

// locators
ByAngular.withRootSelector("something-custom").exactRepeater("day in days");

// or
ByAngular.Factory baf = ByAngular.withRootSelector("something-custom");
ByAngularRepeater foo = baf.exactRepeater("day in days");
```

### Alternate selectors

Referring to a handy StackOverflow questions - [No injector found for element argument to getTestability](http://stackoverflow.com/questions/28040078/no-injector-found-for-element-argument-to-gettestability), you can use the applicable selector for your Angular app:

* `'[ng-app]'`- matching an element that has the arribute `ng-app` (this is the default)
* `'#my-app'` - matching an id `my-app`
* `'[fooBar]'` - matching an attribute `fooBar` on any element

There's a reference to css selectors you'll need to read - https://www.w3schools.com/cssref/css_selectors.asp - because that's the type of string it is going to require.

### Still needing help on $$testability ?

Read the five or so bug reports on $$testability and how (most likely) you have to learn a little about you application so that you can use `.withRootSelector("abc123")`. Those bug reports: https://github.com/paul-hammant/ngWebDriver/issues?issue+testability

## Other Functions

### getLocationAbsUrl()

Returns the URL of the page.

```java
String absoluteUrl = new NgWebDriver(driver).getLocationAbsUrl();
```

## Code Examples

All our usage examples are in [a single test class](https://github.com/paul-hammant/ngWebDriver/blob/master/src/test/java/com/paulhammant/ngwebdriver/AngularAndWebDriverTest.java):


# Including it in your project

## Maven

```xml
<dependency>
  <groupId>com.paulhammant</groupId>
  <artifactId>ngwebdriver</artifactId>
  <version>1.1.2</version>
  <scope>test</scope>
</dependency>

<!-- you still need to have a dependency for preferred version of
  Selenium/WebDriver. That should be 3.3.1 or above -->
```

## Non-Maven

Download from [Mavens Central Repo](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22ngwebdriver%22)

## Releases

Last Release: 1.1.2 - Mar 08, 2018

Refer [CHANGELOG](./CHANGELOG.md)
