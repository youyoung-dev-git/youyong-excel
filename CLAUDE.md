# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

YouYoung Excel is a Java library for Excel processing in Spring Boot applications, built on top of Alibaba's EasyExcel library.

- **Language**: Java 21
- **Build**: Gradle 9.2.1 with Kotlin DSL
- **Framework**: Spring Boot 4.0.1 (optional dependency)
- **Core Dependency**: EasyExcel 4.0.3

## Build Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "fully.qualified.TestClassName"

# Run a single test method
./gradlew test --tests "fully.qualified.TestClassName.testMethodName"

# Clean build
./gradlew clean build

# Generate sources and javadoc JARs
./gradlew jar sourcesJar javadocJar

# Publish to local Maven repository
./gradlew publishToMavenLocal
```

## Architecture Notes

This is a library project (not a Spring Boot application):
- Uses `java-library` plugin for API/implementation dependency separation
- Spring Boot dependencies are `compileOnly` (provided by consuming projects)
- Designed to be included as a dependency in Spring Boot applications

## Package Structure

```
src/main/java/net/youyoung/excel/
├── annotation/           # Annotation definitions
│   ├── ExcelSheet        # Sheet configuration
│   ├── ExcelColumn       # Column configuration
│   ├── ExcelStyle        # Data cell style
│   ├── ExcelHeaderStyle  # Header row style
│   ├── ExcelDateFormat   # Date format
│   └── ExcelNumberFormat # Number format
├── style/                # Style enums and strategies
│   ├── ExcelColor        # Color enum
│   ├── ExcelAlign        # Alignment enum
│   └── DefaultStyleStrategy
├── core/                 # Core functionality
│   ├── ExcelReader       # Read Excel → List<T>
│   ├── ExcelWriter       # Write List<T> → Excel
│   ├── ExcelMetadataParser
│   ├── ColumnMetadata
│   ├── SheetMetadata
│   └── ExcelFile
├── handler/              # EasyExcel handlers
│   ├── AnnotationWriteHandler
│   └── EmptyColumnHideHandler
├── support/              # Utilities
│   ├── ExcelFileNameEncoder
│   └── ExcelResponseHelper
└── spring/               # Spring integration (optional)
    ├── ExcelHttpResponse
    └── ExcelMultipartFile
```

## Key APIs

- `ExcelReader.read(inputStream, Class)` - Read Excel to Java objects
- `ExcelWriter.write(list, Class)` - Write Java objects to Excel bytes
- `ExcelHttpResponse.write(response, list, Class, filename)` - Spring HTTP response
- `ExcelMultipartFile.read(file, Class)` - Spring MultipartFile upload
