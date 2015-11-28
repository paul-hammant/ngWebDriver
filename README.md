A small library of WebDriver locators for AngularJS, as well as a utility to interoperate with $scope variables directly.

It is only in Java for now, but I'm sure ports will make it available for .Net, Python, Ruby etc.

# Status

[Protractor](https://github.com/angular/protractor) (from where some of the JavaScript has been copied) is stable, but this Java library isn't quite released as yet despite high test coverage, hence the sub-1.0 version number. APIs could change! 

## Locators

### repeater() and exactRepeater()

[As Protractor's repeater](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.repeater) and [Protractor's exactRepeater](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.exactRepeater) 

```java
byAngular.exactRepeater("foo in foos")
byAngular.exactRepeater("foo in foos").row(17)
byAngular.exactRepeater("foo in foos").row(17).column("foo.name")
byAngular.exactRepeater("foo in foos").column("foo.name")
byAngular.repeater("foo in f")
byAngular.repeater("foo in f").row(17)
byAngular.repeater("foo in f").row(17).column("foo.name")
byAngular.repeater("foo in f").column("foo.name")
```

### binding()

[As Protractor's binding](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.binding)

```java
by.binding('person.name')
by.binding('{{person.name}}')
```

### model()

[As Protractor's binding](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.model)

```java
by.model('person.name')
```

### options()

[As Protractor's options](https://angular.github.io/protractor/#/api?view=ProtractorBy.prototype.options)

```java
by.model('c for c in colors')
```

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
  <version>0.9.1</version>
  <scope>test</scope>
</dependency>

<!-- you still need to have a dependency for preferred version of Selenium/WebDriver 2.35 or above -->
```

## Non-Maven

[Download from here](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22ngwebdriver%22)