# README #

### What is this repository for? ###

* This repository contains the projects for an application to manage a set of 3D printer work queues. It will allow to define a set of Parts to be stocked for sell and when new requests from customers arrive the application should generate the worklist queue to fulfill them.
* Current version is DEVELOPMENT-0.3.0

## How do I get set up? ###

### Summary of set up
### Configuration
#### Application Ports

5100 - Angular frontend server
5101 - ApiSimulator port for the backend mock
5110 - SpringBoot backend development server
5130 - SpringBoot backend acceptance server

5190 - Experimental GUI Fronte end

# PROCEDURES
## Development Procedures
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


### Database configuration
### How to run tests
### Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin (adamantinoo.git@gmail.com)
