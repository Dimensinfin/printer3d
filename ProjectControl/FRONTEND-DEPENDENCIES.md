# FRONTEND DEPENDENCIES
## Angular dependencies
There is a list of dependencies that are directly related to the Angular framework. They define the compilation, build and test scenarios. Many other dependencies are related to an specific Angular cli version.
```
"dependencies": {
    "@angular/animations": "~11.1.1",
    "@angular/common": "~11.1.1",
    "@angular/compiler": "~11.1.1",
    "@angular/core": "~11.1.1",
    "@angular/forms": "~11.1.1",
    "@angular/platform-browser": "~11.1.1",
    "@angular/platform-browser-dynamic": "~11.1.1",
    "@angular/router": "~11.1.1",
    "rxjs": "~6.6.0",
    "tslib": "^2.0.0",
    "zone.js": "~0.11.3"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~0.1101.2",
    "@angular/cli": "~11.1.2",
    "@angular/compiler-cli": "~11.1.1",
    "@types/jasmine": "~3.6.0",
    "@types/node": "^12.11.1",
    "codelyzer": "^6.0.0",
    "jasmine-core": "~3.6.0",
    "jasmine-spec-reporter": "~5.0.0",
    "karma": "~5.2.0",
    "karma-chrome-launcher": "~3.1.0",
    "karma-coverage": "~2.0.3",
    "karma-jasmine": "~4.0.0",
    "karma-jasmine-html-reporter": "^1.5.0",
    "protractor": "~7.0.0",
    "ts-node": "~8.3.0",
    "tslint": "~6.1.0",
    "typescript": "~4.1.2"
  }
}
```
Typescript version ususlly goes with the Angular version and there is a set of versions suitable for every Angular version.

## LINT
````
"dependencies": {
  },
  "devDependencies": {
  }
}
````

## Deprecated dependencies
* **tslint** is deprecated and should be replaced with **ESLint**
* **protractor** is deprecated and seems to not have a replacement. This is the engine to run the tests on a virtual navigator. In this project it can be removed since e2e testing is done with Cypress.

## CYPRESS
Cypress is the selected e2e testing tool selected for the projects. It will require an application running on a stable set of data usually mocked to do any UI and visual testing on the run.

There are some additions surrounding cypress to add typescript support to the feature steps.

````
  "dependencies": {
    "@bahmutov/add-typescript-to-cypress": "2.1.2",
  },
  "devDependencies": {
    "@cypress/code-coverage": "3.7.2",
    "@cypress/webpack-preprocessor": "5.2.1",
    "@types/chai": "4.2.11",
    "@types/cucumber": "6.0.1",
    "@types/cypress-cucumber-preprocessor": "1.14.1",
    "chai": "4.2.0",
    "cucumber": "6.0.5",
    "cypress": "6.8.0",
    "cypress-cucumber-preprocessor": "2.3.1",
  }
````
## DIMENSINFIN
This is the set of dependencies related to the application itself. The items from **bit** are deprecated and should be replaced by standard and easy to maintain npm libraries.

This set of libraries should be migrated to Adapters to isolate their dependency from specific Angular versions and simplify upgrading tasks.

**ng-drag-drop** is deprecated since some years. Should be replaced by **ngx-drag-drop** or encapsulated to isolate from Angular dependencies.

````
  "dependencies": {
    "@bit/innovative.innovative-core.interfaces": "^0.1.2",
    "@bit/innovative.innovative-core.isolation-service": "^0.1.1",
    "@bit/innovative.innovative-core.testing-support": "^0.1.1",
    "@swimlane/ngx-charts": "16.0.0",
    "@types/uuid": "10.0.0",
    "jwt-decode": "2.2.0",
    "ng-drag-drop": "5.0.0",
    "ngx-toastr": "^13.2.1",
    "ngx-webstorage-service": "4.1.0",
    "uuid": "10.0.0",
  },
````
## NODE SERVER
This are the dependencies used on the Nose express server to route requests.
````
  "dependencies": {
    "compression": "1.7.4",
    "config": "3.3.1",
    "dotenv-cli": "^7.4.2",
    "env-cmd": "^10.1.0",
    "express": "4.17.1",
    "express-http-proxy": "1.6.0",
    "npm": "6.14.5",
    "path": "0.12.7",
    "request": "2.88.2",
    "wait-on": "5.0.0",
  },
````
## Other dependencies
````
  "dependencies": {
    "npm": "6.14.5",
    "wait-on": "5.0.0",
  },
  "devDependencies": {
    "chai": "4.2.0",
    "chromedriver": "81.0.0",
    "cucumber": "6.0.5",
    "cypress": "6.8.0",
    "cypress-cucumber-preprocessor": "2.3.1",
    "karma": "6.4.4",
    "karma-chrome-launcher": "3.1.0",
    "karma-coverage-istanbul-reporter": "3.0.2",
    "karma-jasmine": "4.0.0",
    "karma-jasmine-html-reporter": "1.5.0",
    "nyc": "15.0.1",
  }

````
