# 🥒 Cucumber Selenium Framework Assignment

This is a sample project created to test the [Juice Shop](https://juice-shop.herokuapp.com/) using Cucumber Java and Selenium

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Execution](#execution)
- [Continuous Integration](#continuous-integration)


## Introduction

This Cucumber Selenium project is created with a simple and scalable framework architecture that provides flexibility to the user to accommodate new changes easily.

## Features

- 🌐 Support for multiple browsers. Currently supports Chrome and Firefox; more browsers can be added.
- ⚡ Support for Parallel Execution with TestNG.
- 📊 Extent Reports with Screenshot Facility.
- 📝 Log4j Support.
- 🔍 Strategic element locators technique to reduce object identification failures.
- 🏗️ Page Object Model Design Pattern.
- 💉 Dependency Injection for better maintainability and flexibility.
- 🔄 CI enabled to run test cases for every PR raised and push made to the main branch.

## Execution

### To execute in command prompt

```bash
# Example installation and execution steps
git clone https://github.com/Apzal/JuiceAppWebAutomation.git
cd your-repo
# Ensure maven is installed
mvn clean test
```
### To execute in IDE
- After cloning the project open it in your IDE
- Once the dependencies are downloaded
- Execute the file [RunCucumberTest](src/test/java/runner/WebRunCucumberTest.java)

## Continuous Integration

Workflow file [maven.yml](#.github/workflows/maven.yml) is configured with ability to run all test cases for every PR and push to main branch.
Also after every run the html report and logs are published under the [Git Action Run](https://github.com/Apzal/jdoodletest/actions/runs/7279475549)

Sample Report for reference attached [here](test-output/SparkReport/testReport.html)



