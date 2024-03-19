# Tooler Tools Coding Style Guide

This document serves as the coding conventions for Java source code in the tooler-tools project.
Some parts of this document only apply to Java source code, while others apply to all source code in the project.

## Table of Contents

1. [Source Formatting](#source-formatting)
    1. [Indentation](#indentation)
    2. [Import Statements](#import-statements)
        1. [Wildcard Imports](#wildcard-imports)
        2. [Ordering](#ordering)
2. [Naming Conventions](#naming-conventions)
    1. [Field Naming](#field-naming)
    2. [Method Naming](#method-naming)
3. [Use of `final` Keyword](#use-of-final-keyword)
4. [Maximum Line Length](#maximum-line-length)
5. [Testing Conventions](#testing-conventions)
    1. [Test Class Naming](#test-class-naming)
    2. [Failing Tests](#failing-tests)

## Source Formatting

### Code Style

- If using IntelliJ Scheme please use GoogleStyle which will automatically follow the majority of code styles applied to
  this project.

### Indentation

- **Rule**: Use spaces instead of tabs.
- **Amount**: 4 spaces per indentation level.

### Import Statements

#### Wildcard Imports

- **Usage**: Wildcards are permissible.
- **Threshold**: Employ wildcards for 5 or more imports from the same package.
- **Exceptions**: For frequently used packages like `java.util`, `java.io`, wildcards may be used for fewer imports.

#### Ordering

Import statements should be grouped and ordered in the following manner:

1. **JDK Imports**: Begin with standard Java (JDK) imports.
2. **General 3rd Party Imports**: Follow with imports from third-party libraries (e.g., JUnit).

Static import statements should be grouped after non-static ones, using same ordering.

So, for example we might have:

```java
import java.io.*;           // JDK imports
import java.util.*;

import org.junit.*;         // General 3rd party imports

import com.fasterxml.jackson.annotation.*;  // Jackson core types: annotations

import com.fasterxml.jackson.core.*;        // Jackson core types: core
import com.fasterxml.jackson.databind.*;    // Jackson core types: databind

import com.fasterxml.jackson.other.modules.*; // and some Component-specific imports (if any)

// same mechanism for static imports

...static import statements...
```

## Naming Conventions

### Field Naming

Standard Java Language naming recommendation (camel-case) is followed with following modifications:

### Method Naming

- **Public Methods**: Adhere to standard Java naming conventions.
- **Underscore**: Do not use underscores to define private fields or methods.

## Use of `this` keyword

- Use it always where applicable.

## Use of `final` keyword

- **Fields**: Strongly encourage the use of `final` for fields, assigning them in the constructor to promote
  immutability.
- **Method Parameters**: Allowed but not necessarily encouraged.
- **Local Variables**: Encouraged where applicable if not effectively final.

## Maximum Line Length

- **Recommendation**: Lines should not exceed 120 characters.
- **Javadocs**: Strict adherence to a maximum of 120 characters per line is recommended.

## Testing Conventions

### Test Class Naming

- **ClassNameTest**: Mandatory naming convention is "ClassNameTest".
- **JUnit and Maven Compatibility**: It is important to note that for `JUnit` integration with `Maven`, test classes
  must either start or end with `"Test"` to be automatically included in test runs.

### Tests

- **Test Resources**: Make the most of the provided test platform and resources in the project. New resources may only
  be added if they aim to test a use-case where existing resources can't be used. Existing resources may be adapted if
  they don't break existing tests.
