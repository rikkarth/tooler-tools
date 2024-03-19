# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

## [0.1.0](https://github.com/rikkarth/tooler-tools/compare/0.0.8...0.1.0) (2024-03-19)


### Features

* **XmlHandler:** get element from xpath ([00fc2a0](https://github.com/rikkarth/tooler-tools/commits/00fc2a04f91fa448ea59e25441d477b7736e39fe))
* **ZipBuilder:** base implementation ([aa8cceb](https://github.com/rikkarth/tooler-tools/commits/aa8cceb6b599dfd7dee6492fabdbfeb0a5ef3f36))

### 0.0.8 (2023-12-14)


### Features

* add update:logs command to package.json ([bebe39a](https://github.com/mokkapps/changelog-generator-demo/commits/bebe39a192eaf4402215f97b62ee66f9e7b52623))
* automated semantic versioning for pom.xml and package.json ([2583086](https://github.com/mokkapps/changelog-generator-demo/commits/2583086932dc29e9ed3338faaf66826f3a5527ef))
* initial auto-generate CHANGELOG.md setup ([6653871](https://github.com/mokkapps/changelog-generator-demo/commits/665387170b9b0d5afe0c5cae50250bda95cf7d02))
* installed versions-maven-plugin ([db31f5a](https://github.com/mokkapps/changelog-generator-demo/commits/db31f5a4861149ab9c09ef84ff0df1cab272075b))
* update README.md ([f99c2b4](https://github.com/mokkapps/changelog-generator-demo/commits/f99c2b49f0d4e27c9c98f2581a6ad22d18c37374))


### Changed

* add new type to .versionrc ([c2fbd80](https://github.com/mokkapps/changelog-generator-demo/commits/c2fbd8043d8fbe4739e13b13aace4d1b5ab849c8))

### 0.0.7 - (08-12-2023)

### Added

- commons-beanutils:commons-beanutils installed.
- PropertiesLoader JavaDoc

### Changed

- README.md
- pom.xml
- PropertiesLoader Improved
- Configured `maven.yml` to create tag based on `pom.xml` version.
- Configured `maven.yml` to deploy release to mvn central and GitHub packages.

### 0.0.6 - (07-12-2023)

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

### 0.0.5 - (03-12-2023)

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

### 0.0.4 - (02-12-2023)

### Changed

- Package name change to reflect new org. From `org.tooler-tools` to `pt.codeforge`.
- Domain `pt.codeforge` now redirects to this repository - `https://github.com/rikkarth/tooler-tools`.

### 0.0.3 - (01-12-2023)

### Added

- CHANGELOG.md
- **Installed**: Maven Wrapper
- **Installed**: JUnit Params
- Ability to retrieve single Node from XPath with XmlHandler.

### Changed

- **XmlHandler** now handles XPathExpressions internally, getStringFromXPath and getNodeListFromXPath both now only
  require the expression to be provided as a String instead of an XPathExpression object.
