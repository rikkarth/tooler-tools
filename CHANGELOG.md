# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.6] - 07-12-2023

### Added

- `EnvPathParser` first implementation and JavaDoc
- `EnvPathParser` simple unit tests
- `ParametersProvider` in test package to facilitate testing with several, different parameters.

### Changed

- Reworked GitHub `maven.yml` to only test in non-production branches and compile, test, package and deploy on
  production branches.
- README.md Update

### Changed

- Moved `DocConstants` to `pt/codeforge/toolertools/xml` to make it effectively Package-Private

## [0.0.5] - 03-12-2023

### Added

- Unit tests in `pt.codeforge.toolertools.xml.XmlHandlerTest`
    - Tested `#getOptionalDomFromFile`
- JavaDoc to `pt.codeforge.toolertools.xml.XmlHandler`

### Added

- Unit tests in `pt.codeforge.toolertools.xml.XmlHandlerTest`
    - Tested `#getOptionalDomFromFile`
- JavaDoc to `pt.codeforge.toolertools.xml.XmlHandler`

### Changed

- README.md
- pom.xml for integration with *Maven Central*
- Unit tests signatures in `XmlHandlerTest` for improved readability.
- Separated `#getNodeFromXPath` unit test assertions for better readability.

### Removed

- Maven Wrapper for smaller binary. Unecessary since *"this is wibrary"*.

## [0.0.4] - 02-12-2023

### Changed

- Package name change to reflect new org. From `org.tooler-tools` to `pt.codeforge`.
- Domain `pt.codeforge` now redirects to this repository - `https://github.com/rikkarth/tooler-tools`.

## [0.0.3] - 01-12-2023

### Added

- CHANGELOG.md
- **Installed**: Maven Wrapper
- **Installed**: JUnit Params
- Ability to retrieve single Node from XPath with XmlHandler.

### Changed

- **XmlHandler** now handles XPathExpressions internally, getStringFromXPath and getNodeListFromXPath both now only
  require the expression to be provided as a String instead of an XPathExpression object.
