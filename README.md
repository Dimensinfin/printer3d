# README #

### What is this repository for? ###

* This repository contains the projects for an application to manage a set of 3D printer work queues. It will allow to define a set of Parts to be stocked for sell and when new requests from customers arrive the application should generate the worklist queue to fulfill them.
* This repository holds the entire project. This is divided into different executables and services but all being together on the same storage.
* There is a frontend with the user interface over a web platform developed in Angular.
* Also there is s backend where the data is persisted and searched implemented with SpringBoot and using database relational repositories to store the data and relationships.
* And a portal that unifies the frontend and the backend developed in Node that initializes the application and keeps track of the authentication and firewalls. This posrtal is still pending development once the authentication is put in place.
* Current version is DEVELOPMENT-0.7.0

# Application Port Mapping
#### 5100 - Development frontend server<br>5110 - Development backend mock<br>5120 - Local backend application sringboot instance<br>5130 - Postgres database instance for acceptance tests<br>5140 - Backend application acceptance instance<br>5150 - Development portal server
#### 5101 - Integration frontend server<br>5121 - Local backend application springboot instance<br>5131 - Postgres database docker container for the integration environment
#### 5190 - Experimental GUI fontend server. Uses the same mock server.

# Printer3D-Backend

## Filtering tests

./gradlew test -Dcucumber.optis="--tags @P3D08" --tests **/backend/core/exception/*Test

# Printer3D-Frontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 9.1.6.

* This project is located on the port range 51xx. There are two environment on the local computer. One for development and another for integration tests.

## Development server
* Previous to start the development frontend server we should start the backedn mockup. This is a docker container with an ApiSimulator instance with HTTP pre recorded responses. The command for the mock is **`npm run docker:start`**. This opens the mock server on port **5101**.
* The development frontend server is started with the script **`npm run start:dev`**. This opens the development frontend server on port **5100**.
* Unit testing is run with the command **`npm run test:dev`**
* Acceptance tests are developed under *Cypress*. To run tests interactively open the cypress console with **`cypress open`**. To run all the set of acceptance tests the command is **`cypress run`**. Acceptance tests require that the development frontend server is started.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build
* There are a set of commands to build the distribution code for development, staging or production. Also there are scripts for production testing and the scrip coordination on CI platforms.
* To build the distribution run the command **`npm run build:<environment>`** where there are three supported environments, **dev**, **stage** y **prod**.
* For a complete test run the command **`npm run check:deploy`**

## Unit Tests pending
* NeoCom.domain
* V1PartRender
* ProductionJobListPage
* V2MachinesPanel

## How do I get set up? ###

## CYPRESS Models Common
* **Given there is a click on Feature ">FEATURE LABEL<"**
```Javascript

```
* **Then the page ">SymbolicName<" is activated**
 ```Javascript

```
* **Then the target panel has a title "/NUEVO MODELO/DEFINICION"
* **Given the target panel is the panel of type "new-model"**
```Javascript
Given('the target panel is the panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel')
});
```


```Javascript
Then('the target page has one panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
Then('the target page has one panel of type {string} with variant {string}', function (symbolicName: string, variant: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});
```

        Then the target panel has a input field named "label" with label "ETIQUETA" and empty


# PROCEDURES
## Development Procedures Frontend
1. Start writing the STORY card to describe what is expected from the user.
2. From the story create the **features** and the **scenarios**.
3. For each of the scenarios identified do steps **4** to **11**.
4. Register the scenario as a Trello card.
5. Copy the scenario identifier to the *cucumber* script.
6. Generate the scenario code executing the command '*npm run cucumber*'.
7. Create the steps on the ***&lt;feature&gt;* step.ts**.
8. Check the scenario on cypress. All scenarios are run at the same time so comment out correct solutions until the target scenario is completed.
9. When the scenario is completed commit the changes with the scenario title.
10. When there are additional actions that may require heavy development record them into new Trello cards.
11. Once a card is completed do a commit with the card contents.
12. Once all the scenarios are completed start the testing with the command '*npm run test:dev*'
13. Change to the **test** git repository path
13. Fix all tests and complete the code coverage to a level green on all sections.
14. Validate the application running the command '*npm run build:prod*'

# DATABASE
## PSQL Commands

* **CHANGE SCHEMA** - *SET search_path TO printer3d;*
* **LIST TABLES** - \dt
* **EXIT PSQL** - \q

## SQL Scripts
* **SELECT ROWS** 
```sql
SELECT id FROM parts WHERE color='INDEFINIDO';
```
* **DELETE BAD COLORS**
```sql
DELETE FROM parts WHERE color='INDEFINIDO';
```
# UTILITIES

# GOURCE
gource --fullscreen --start-date "2020-00-15 21:00:00" --output-ppm-stream /dev/null --output-framerate 25 --title "3DPrinterManagement" --hide filenames



### Database configuration
### Deployment instructions

## Who do I talk to?

* Repo owner and admin (adamantinoo.git@gmail.com)
