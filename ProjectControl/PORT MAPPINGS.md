# PRINTER3D Application Port Mapping - Project Code 51
## Port Nomenclature
The project will provide all ports on the range 5100 to 5199. Digits for port assignment follow next rules.
- The first two digits are always **5** and **1**.
- The third digit is for the functionality group. Like server, mocks and other services. Goes from 0 (main service) to 9. Services may depend on the environment like the mocks thare are not present on staging or production.
  * **0** - the Angular frontend server.
  * **1** - backend server or backend mock.
- The fourth and last digit is for the environment.
  * **0** is for ***PRODUCTION*** environment
  * **1** is for ***LOCAL TESTING*** and quiality testing before pre-production deployment for final quality testing. 

## APPLICATION CODES
Each application should have a global Application code to be used on the Kubernetes and other services provisioning. Also there is a module code and name that matches the project source architecture.

* **APP_CODE** - PRINTER3D
* **MODULE_CODE** - There are two modules on this application. Frontend coded *P3DF* and backend coded *P3DB*
