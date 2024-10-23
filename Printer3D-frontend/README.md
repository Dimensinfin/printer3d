# PRINTER3D - FRONTEND
## Project Structure
* **config** - Node config json files to configure the application properties by environment. DEPRECATED.
* **cypres** - Cypress acceptance test sources and features.
* **src** - Application frontend source code.
* **support** - Mocks and other test support code. DEPRECATED
* **.deploy** - Where to place shell scripts for builds and deployment. NEW

## Project Structure Proposal
To make the SpringBoot and Angular projects similar on structure I propose to put all code under the **src** directory and then differentiate there between the acceptance code (*cypress*), the test code (*support*) and the main code (*src*).

## New Deployment Configuration
The old Node configuration will be dephased and replaced by a shell environment selectable configuration that will allow more flexibility.
The general action will be to generate environment configuration from the processing of template data with the specific environment values so making the configuration accesible for Node and for Shell.

The source for environment configuration is an .env.<environment> file that defines the Shell environment. Node will use that environment to replace placeholders on the js configuration files from the environment contents using a script to do the replacement.
