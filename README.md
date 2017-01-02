# ngWebDriver

A small library of WebDriver locators and more for AngularJS, for Java.

# Status

We have taken JavaScript from Angular's [Protractor](https://github.com/angular/protractor) project. While ngWebDriver perfectly
compliments the Java version of WebDriver, it has to pass JavaScript up to the browser to inteoprate with Angular, and the
Protractor project has done the hard work (including testing) to make that solid, and ngWebDriver benefits from that work.

You can use ngWebDriver today with the regular Java Selenium2/WebDriver libraries. You can also use it with
[FluentSelenium](https://github.com/SeleniumHQ/fluent-selenium) for extra terseness.

## Waiting for Angular to finish async activity

```java
new NgWebDriver(driver).waitForAngularRequestsToFinish();
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

As with Protractor, you can change items in an Angular model, or retrieve them reagrdless of whether they appear in the UI or not.

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

Perhaps a different selector should be used to find Angular apps:

```
NgWebDriver ngwd = new NgWebDriver(javascriptExecutor).withRootSelector("something-custom");

ByAngular.Factory factory = ngwd.makeByAngularFactory()

factory.exactRepeater("day in days");

// locators
ByAngular.withRootSelector("something-custom").exactRepeater("day in days");

// or
ByAngular.Factory baf = ByAngular.withRootSelector("something-custom");
ByAngularRepeater foo = baf.exactRepeater("day in days");

```

### Alternate selectors

Referring to a handy StackOverflow questions - [No injector found for element argument to getTestability](http://stackoverflow.com/questions/28040078/no-injector-found-for-element-argument-to-gettestability), you can use any selector:

* `'[ng-app]'`- matching an element that has the arribute `ng-app` (this is the default)
* `'#my-app'` - matching an id `my-app`
* `'[fooBar]'` - matching an attribute `fooBar` on any element

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
  <version>0.9.6</version>
  <scope>test</scope>
</dependency>

<!-- you still need to have a dependency for preferred version of
  Selenium/WebDriver. That should be 2.48.2 or above -->
```

## Non-Maven

Download from [Mavens Central Repo](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22ngwebdriver%22)

## Releases

Last Release: 0.9.7 - July 22, 2016

Refer [CHANGELOG](./CHANGELOG.md)
