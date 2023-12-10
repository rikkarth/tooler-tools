# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

### [0.0.8](https://github.com/mokkapps/changelog-generator-demo/compare/0.0.7...0.0.8) (2023-12-10)


### Features

* automated semantic versioning for pom.xml and package.json ([2583086](https://github.com/mokkapps/changelog-generator-demo/commits/2583086932dc29e9ed3338faaf66826f3a5527ef))
* initial auto-generate CHANGELOG.md setup ([6653871](https://github.com/mokkapps/changelog-generator-demo/commits/665387170b9b0d5afe0c5cae50250bda95cf7d02))
* installed versions-maven-plugin ([db31f5a](https://github.com/mokkapps/changelog-generator-demo/commits/db31f5a4861149ab9c09ef84ff0df1cab272075b))

## [0.0.7] - Dev

### Added

- commons-beanutils:commons-beanutils installed.
- PropertiesLoader JavaDoc

### Changed

- README.md
- pom.xml
- PropertiesLoader Improved
- Configured `maven.yml` to create tag based on `pom.xml` version.
- Configured `maven.yml` to deploy release to mvn central and GitHub packages.

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
