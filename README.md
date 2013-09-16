A small library of WebDriver locators for AngularJS, as well as a utility to interoperate with $scope variables directly.

It is only in Java for now, but I'm sure ports will make it available for .Net, Python, Ruby etc.

# Status

[Protractor](https://github.com/angular/protractor) (from where some of the JavaScript has been copied) is stable, but this Java library isn't quite released as yet despite 100% test coverage, hence the sub-1.0 version number. APIs could change! 

## Code Examples

All our usage examples are in [a single test class](https://github.com/paul-hammant/ngWebDriver/blob/master/src/test/java/com/paulhammant/ngwebdriver/AngularAndWebDriverTest.java): 

In there are examples of:

1. how to wait for Angular to stop being busy.
1. how to find 'rows', 'columns' or 'cells' via Angular's ng-repeat directive.
1. how to find elements via Angular's ng-bind directive, also used like so: {{bindingVar}}.
1. how to find read $scope variables
1. how to find write $scope variables

## Pitfalls

If you're trying to retrieve a date object, there's a [selenium bug](http://code.google.com/p/selenium/issues/detail?id=6267) stopping that for now. Instead bring it back as JSON and post-process it:

```java
DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser()
                            .withChronology(ISOChronology.getInstanceUTC());
DateTime actualWhen = parser.parseDateTime(ngModel.retrieveJson(anElem, "myDateField").replace("\"", ""));
```

# Including it in your project

## Maven

```xml
<dependency>
  <groupId>com.paulhammant</groupId>
  <artifactId>ngwebdriver</artifactId>
  <version>0.9</version>
  <scope>test</scope>
</dependency>

<!-- you still need to have a dependency for preferred version of Selenium/WebDriver 2.35 or above -->
```

## Non-Maven

[Download from here](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22ngwebdriver%22)