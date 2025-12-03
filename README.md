# 📱 SwagLabs Mobile Automation Tests

This project contains an **Appium-based mobile automation framework** for testing the **SauceLabs demo application** on both **Android** and **iOS** platforms.
The framework is implemented in **Java** using **TestNG**, and follows a highly modular, scalable structure to support cross-platform automation, maintainability, and CI/CD integration.

## 📌 Project Overview
The objective of this project is to automate major end-to-end functionalities of the SauceLabs mobile app — covering login, product selection, cart interactions, and checkout flows.
The architecture is designed using the **Page Object Model (POM)** and adheres to clean separation of concerns, ensuring:
- Easy maintenance
- High reusability
- Platform-independent abstraction
- Smooth scalability for new app modules and test cases

## 📌 Architecture & Design
The codebase follows a layered structure as shown below:

src/
├── main/java/com/saucedemo/app
│ ├── base/ → Base page classes, test setup classes
│ ├── controller/ → Driver & Appium service management
│ ├── objects/ → Enums, utilities, constants, user objects
│ ├── utils/ → Gesture helpers, capability utilities, JSON helpers
│ └── ...
│
├── test/java/com/saucedemo/app
│ ├── pages/
│ │ ├── android/ → Android-specific Page Object classes
│ │ ├── ios/ → iOS-specific Page Object classes
│ │ └── interfaces/ → Shared page interfaces for cross-platform behavior
│ ├── tests/ → Test cases and execution flows
│ └── ...
│
├── test/resources/
│ ├── device-profiles/ → Capability configuration for Android/iOS
│ └── app binaries → .apk and .app files


### 🔹 Key Components

#### `base/`
Contains core reusable classes:
- `BasePage` — UI interactions, gestures, element handling
- `BaseTest` — test lifecycle, hooks, driver setup

#### `controller/`
Handles driver initialization and Appium server lifecycle:
- `AppiumDriverManager`
- `AppiumServiceManager`

#### `objects/`
Contains shared model and utility assets:
- Platform enum (`PlatformType`)
- User objects
- JSON utilities
- Constants
- Environment properties

#### `pages/`
Implements the **Page Object Model**:
- `android/` — Android UI elements and flows
- `ios/` — iOS-specific locators and actions
- `interfaces/` — Shared method contracts → platform implementations adhere to the same API

#### `tests/`
Contains end-to-end scenarios and business workflows:
- Login
- Product selection
- Cart
- Checkout

#### `resources/device-profiles/`
Defines device capabilities (perfecto, simulator/emulator, screen size, OS version, etc.)
Includes application binaries (`.apk`, `.app`)

## 📌 Tools & Technologies

| Technology | Purpose |
|-----------|--------|
| **Appium Java Client (10+)** | Mobile automation |
| **Java 17** | Core programming language |
| **TestNG** | Test framework, grouping, parallel execution |
| **Maven** | Dependency & lifecycle management |
| **Log4j** | Structured logging |
| **ExtentReports** | HTML visual reporting |
| **Jackson Databind** | JSON parsing and data mapping |

## 📌 Execution Platform — Perfecto Mobile Cloud
This project is integrated with **Perfecto**, enabling:
- Test execution on **real cloud devices**
- Device capability management
- Multi-platform parallel execution (Android + iOS)
- Stable reporting & device video logs

Execution is triggered using Perfecto’s driver capabilities, pointing to cloud-hosted devices.

## 🚀 CI/CD Integration (Jenkins)
The project supports headless execution through Jenkins:
- Tests triggered via Maven (`mvn clean test`)
- Cloud device execution using Perfecto
- HTML reports & logs archived post-build
- Used in Sanity & Regression pipelines
