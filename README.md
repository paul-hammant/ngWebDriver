A small library of WebDriver locators for AngularJS, as well as a utility to interoperate with $scope variables directly.

It is only in Java for now, but I'm sure ports will make it available for .Net, Python, Ruby etc.

# Status

We have taken JavaScript from Angular's [Protractor](https://github.com/angular/protractor) project. While ngWebDriver perfectly compliments the Java version of WebDriver, it has to pass JavaScript up to the browser to inteoprate with Angular, and the Protractor project has done the hard work (including testing) to make that solid, and ngWebDriver benefits from that work.

You can use ngWebDriver today with the regular Java Selenium2/WebDriver libraries. You can also use it with [FluentSelenium](https://github.com/SeleniumHQ/fluent-selenium).

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

## Angular model interop

As Protractor, you can change items in an Angular model, or retrieve them reagrdless of whether they aappear in the UI or not.

```java
AngularModelAccessor ngModel = new AngularModelAccessor(driver);
// change something via the model defined in $scope 
ngModel.mutate(wholeForm, "person.name", "'Wilma'");
// Note Wilma wrapped in single-quotes as it has to be a valid JavaScript 
// string literal when it arrives browser-side for execution 
```

See also retrieveJson, retrieve, retrieveAsString and retrieveAsLong for getting Angular data back from the browser.

## Code Examples

All our usage examples are in [a single test class](https://github.com/paul-hammant/ngWebDriver/blob/master/src/test/java/com/paulhammant/ngwebdriver/AngularAndWebDriverTest.java): 

In there are examples of:

1. how to wait for Angular to stop being busy.
1. how to find 'rows', 'columns' or 'cells' via Angular's ng-repeat directive.
1. how to find elements via Angular's ng-bind directive, also used like so: {{bindingVar}}.
1. how to find read $scope variables
1. how to find write $scope variables

## Pitfalls with retrieveJson

If you're trying to retrieve a date object, there's a [selenium bug](http://code.google.com/p/selenium/issues/detail?id=6267) stopping that for now. Instead bring it back as JSON and post-process it:

```java
DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser()
                            .withChronology(ISOChronology.getInstanceUTC());
DateTime actualWhen = parser.parseDateTime(ngModel.retrieveJson(anElem, "myDateField").replace("\"", ""));
```

# Releases

### 0.9.5 - Nov 29, 2015

* Updated dependence on Protractor's clientsidescripts.js source.
* FluentSelenium dependency changed to 1.16.
* New locators (from Protractor) exactRepeater, buttonText, exactBinding (courtesy of Manoj Kumar)

[Comparison/diffs between tags](https://github.com/paul-hammant/ngWebDriver/compare/ngwebdriver-0.9.3...ngwebdriver-0.9.4)

### 0.9.3 - Nov 21, 2015

* FluentSelenium dependency changed to 1.15.
* New locators (from Protractor) model, options (courtesy of Manoj Kumar)

[Comparison/diffs between tags](https://github.com/paul-hammant/ngWebDriver/compare/ngwebdriver-0.9.2...ngwebdriver-0.9.3)

### 0.9.2 - Sep 23, 2013

* bugfix in retrieveAsLong() operation

[Comparison/diffs between tags](https://github.com/paul-hammant/ngWebDriver/compare/ngwebdriver-0.9.1...ngwebdriver-0.9.2)

### 0.9.1 - Sep 16, 2013

* bugfix in retrieveJson() operation

[Comparison/diffs between tags](https://github.com/paul-hammant/ngWebDriver/compare/ngwebdriver-0.9...ngwebdriver-0.9.1)

### 0.9 - Sep 15, 2013

* Initial release

# Including it in your project

## Maven

```xml
<dependency>
  <groupId>com.paulhammant</groupId>
  <artifactId>ngwebdriver</artifactId>
  <version>0.9.4</version>
  <scope>test</scope>
</dependency>

<!-- you still need to have a dependency for preferred version of 
  Selenium/WebDriver. That should be 2.48.2 or above -->
```

## Non-Maven

[Download from here](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22ngwebdriver%22)
