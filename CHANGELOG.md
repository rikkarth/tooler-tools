# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.5] - Dev

### Added

- Unit tests in `pt.codeforge.toolertools.xml.XmlHandlerTest`
    - Tested `#getOptionalDomFromFile`
- JavaDoc to `pt.codeforge.toolertools.xml.XmlHandler`

### Changed

- README.md
- pom.xml for integration with *Maven Central*
- Unit tests signatures in `XmlHandlerTest` for improved readability.
- Separated `#getNodeFromXPath` unit test assertions for better readability.

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
