# README #

## What is this repository for? ###

Project to help manage the works to complete on a 3D printer or set of equal 3D printers.
* App 1. The Back end. Developed with SpringBoot 2+ has the API interface to the persistence repository where the application stores the part models and the completed jobs. There is a RDMBS database with the application records and the needed entities required to model the application domain.
* App 2. The Front end. Developed with Angular 9. Codes the UI to be used by the operators to enter the model and job information into the persistence repository. Shows listings of Customer Requests and pending jobs along with the current Part Catalog.
* App 3. The Proof Of Concept repository where development can test new experimental features before entering them on the project main repository.

The App 1 and App 2 are glued by a Node server that will create a unique virtual domain and that will detect requests that should be processed by the backend.

The project has Continuous Integration on the CircleCI platform and the production application instances will be located on Heroku servers.

**Latest version is PRODUCTION 0.13.0.**

## Application Feature Summary
* The UI allows to control the Rolls of plastic that can be used as source materials on the 3D printers.
* The UI allows the definition of Parts and inside them of part variations (by the color axis by example) that can be used to compose Models.
* Customer Requests can contain Part and/or Models.
* The UI shows the catalog of printers available and the current work status as commaned by the Operator. There is no direct connection with the 3D printer so there is no direct access to the real printer status.
* Operators are authenticated by user/password so all operator jobs and changes can be tracked.
* Pending 3D printer jobs is generated on the flow from the Stock levels set by operators and from the current open Customer Requests.
* Application can take control of the source material expected usage so it can warn about shortages or reject jobs that will exceed the expected resource usage.
* Application will provide a raw information about the expected time left to complete all Pending Jobs.
* Application will provice a simple billing report for each work week from the Customer Requests fulfilled and billed.

## Application Port Mapping
#### 5100 - [DEVELOPMENT] Frontend Node server<br>5110 - [DEVELOPMENT] Frontend ApiSimulator backed server<br>5120 - [DEVELOPMENT] Backend SB application server<br>5432 - [DEVELOPMENT] Backend Postgres DB (Global Database)

#### 5121 - [ACCEPTANCE] Backend SB application server<br>

<br>5140 - Backend application acceptance instance<br>5150 - Development portal server
#### 5101 - Integration frontend server<br>5121 - Local backend application springboot instance<br>5131 - Postgres database docker container for the integration environment
#### 5190 - Experimental GUI fontend server. Uses the same mock server.

#
# Printer3D-Frontend
## Steps to change to next version
* Change the field *'~/package.json[version]'*
* Change the version on the **~/src/index.html** file.
* Run the npm script **app:banner**

## Steps to upgrade/update the framework
* Current frameworks are registered on file *'dimensinfin3d-angular9-commands.txt'*.
* Node version is set to lts/erbium with nvm command **nvm use lts/erbium**
* Angular version is set to '**ng916**' by alias configuration


# Printer3D-Backend
## Database File Name Pattern
* ddl/dml - Data Description Language / Data Management Language for inserts and data manipulation.
* -
* v.w.x-00 - Version.Minor Version.Change-Order - the version code and the order for the file on a version set.
* -
* -/alter - Creation or alteration of a repository entity.
* -
* entity - repository entity name.
* -
* schema/table/index - entity type.
* .yaml

## Liquibase Id Name Pattern
* version
* _
* minor version
* _
* change
* _
* 0 - order file number
* 099 - change set sequential number
* _
* schema
* _
* action - one of the set actions that usually match the liqiubase action verb.
* _
* entity
* _
* entity type

### Colors
* azul electrico claro
**ddl-v0.0.1-printer3d-schema** - Data Manipulation for version 0, subversion 0, first change. Schema name and this is the creation of the schema.


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
SELECT id FROM parts WHERE active=false;
```
* **DELETE BAD COLORS**
```sql
DELETE FROM parts WHERE color='INDEFINIDO';
```

* **CLOSE REQUEST**
```sql
UPDATE printer3d.requestsv2 SET state='CLOSE' WHERE id='d6510c35-2b78-46bd-b2c2-f1343b7d4bae';
```

# UTILITIES

# GOURCE
gource --fullscreen --start-date "2020-00-15 21:00:00" --output-ppm-stream /dev/null --output-framerate 25 --title "3DPrinterManagement" --hide filenames



### Database configuration
### Deployment instructions

## Who do I talk to?

* Repo owner and admin (adamantinoo.git@gmail.com)
