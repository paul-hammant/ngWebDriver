This is about WebDriver locators for AngularJS, as well as a utility to interoperate with $scope variables directly.

It is only in Java for now, but I'm sure ports will make it available for .Net, Python, Ruby etc.

# Status

This is experimental for now. Though [Protractor](https://github.com/angular/protractor) (from where the JavaScript has been copied) is stable, but this Java library isn't quite released as yet. 

## Code Examples

All our usage examples are in [a single test class](https://github.com/paul-hammant/ngWebDriver/blob/master/src/test/java/com/paulhammant/ngwebdriver/AngularAndWebDriverTest.java): 

In there are examples of:

1. how to wait for Angular to stop being busy.
1. how to find 'rows', 'columns' or 'cells' via Angular's ng-repeat directive.
1. how to find elements via Angular's ng-bind directive, also used like so: {{bindingVar}}.
1. how to find read $scope variables
1. how to find write $scope variables

## Pitfalls

If you're trying to revrieve a data object, there's a [selenium bug](http://code.google.com/p/selenium/issues/detail?id=6267) stopping that for now.  Instead bring it back as JSON and post-process it.

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

