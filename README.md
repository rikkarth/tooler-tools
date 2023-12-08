# Tooler-Tools

[![Maven Central](https://img.shields.io/maven-central/v/pt.codeforge/tooler-tools.svg)](https://mvnrepository.com/artifact/pt.codeforge/tooler-tools)
[![Java CI with Maven](https://github.com/rikkarth/tooler-tools/actions/workflows/maven.yml/badge.svg)](https://github.com/rikkarth/tooler-tools/actions/workflows/maven.yml)

Tooler-Tools: A versatile Java library for backend and CLI developers, offering a growing collection of tools to
simplify your development tasks.

## Installation

```
<dependency>
  <groupId>pt.codeforge</groupId>
  <artifactId>tooler-tools</artifactId>
  <version>${version}</version>
</dependency>
```

## Tools

**XmlHandler** retrieves values from a doc when provided with the target XPath.

**EnvPathParser** parses and expands env path variables into file paths.

**PropertiesLoader** loads properties from various sources, such as files and system properties,
with support for environment variable expansion in file paths.

### Example - Get String From XPath

*XML Example*

```xml
<?xml version="1.0" encoding="UTF-8"?>
<root xmlns="http://example.com/test">
    <test-element>Test Value</test-element>
    <empty-element></empty-element>
    <self-closing/>
    <element-group>
        <nested-element>Item 1</nested-element>
        <nested-element>Item 2</nested-element>
        <nested-element>Item 3</nested-element>
    </element-group>
</root>
```

*XmlHandler#getStringFromXPath use-case*

```java
import pt.codeforge.toolertools.xml.XmlHandler;

class TestClass {

    @Test
    void test() {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        String textElement = XmlHandler.getStringFromXPath("/root/test-element/text()", doc);

        System.out.println(textElement);
    }
}
```

*Expected Output*

```text
Test Value
```

*Other XPath Applications*

```java
String nesteElement = XmlHandler.getStringFromXPath("/root/element-group/nested-element/text()",doc) // Returns First Element;
String nesteElement1 = XmlHandler.getStringFromXPath("/root/element-group/nested-element[1]/text()",doc) // Returns First Element;
String nesteElement2 = XmlHandler.getStringFromXPath("/root/element-group/nested-element[2]/text()",doc) // Returns Second Element;
String nesteElement3 = XmlHandler.getStringFromXPath("/root/element-group/nested-element[3]/text()",doc) // Returns Third Element;
```

### Example - Get NodeList From XPath
Returns 0 nodes if nothing found, and 'n' nodes if any found.
```java
import pt.codeforge.toolertools.xml.XmlHandler;

class TestClass() {

    void test() {
        NodeList elements = XmlHandler.getNodeListFromXPath("/root/element-group", doc);

        System.out.println(elements.getLength());
    }
}
```

*Expected output*

```
3
```
