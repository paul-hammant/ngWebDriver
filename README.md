This is about WebDriver locators for AngularJS, as well as a utility to interoperate with $scope variables directly.

It is only in Java for now, but I'm sure ports will make it available for .Net, Python, Ruby etc.

# Status

This is experimental for now. Though [Protractor](https://github.com/angular/protractor) (from where the JavaScript has been copied) is stable, the enhancements to WebDriver to allow the Angular locators within the a chain of findElement()s (that this uses) has not been released yet. 

## Code Examples

All our usage examples are in [a single test class](https://github.com/paul-hammant/ngWebDriver/blob/master/src/test/java/com/paulhammant/ngwebdriver/AngularAndWebDriverTest.java): 

# Including it in your project

## Maven

```xml
<dependency>
  <groupId>com.paulhammant</groupId>
  <artifactId>ngwebdriver</artifactId>
  <version>1.0-SNAPSHOT</version>
  <scope>test</scope>
</dependency>

<!-- you still need to declare you preferred version of Selenium/WebDriver 2.36 or above -->
```

