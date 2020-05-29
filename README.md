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

# PROCEDURES
## Development Procedures
1. Start writing the STORY card to describe what is expected from the user.
2. From the story create the **features** and the **scenarios**.
3. For each of the scenarios identified do steps **4** to **N**.
4. Register the scenario as a Trello card.
5. Copy the scenario identifier to the *cucumber* script.
6. Generate the scenario code executing the command '*npm run cucumber*'.
7. Create the steps on the ***&lt;feature&gt;* step.ts**.
8. Check the scenario on cypress. All scenarios are run at the same time so comment out correct solutions until the target scenario is completed.
9. When the scenario is completed commit the changes with the scenario title.


### Database configuration
### How to run tests
### Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin (adamantinoo.git@gmail.com)
