# WEBSOCKETS
Websockets is a common way to add realtime technology to a web application. It will allow to send typed messages from server to client over an open connection that can define one or more subscriptions to topics.

Topics will represent datasources that are a table os set of related tables that need to issue events when the contents are changed. Changes to the datasources can be of three types, insertion of new elements, update of present elements and deletion of elements being this last one quite infrecuent.

The frontend will receive all that events over the different topics but it is expected that the application has a single page active and that only the subscriptions that apply to that page should be active. All other possible subscriptions should not be active so depending on the current application operation it will receive only a subset of the global events produced at the backend.

The project will use STOMP as the protocol on top on websockets to pack the event contents and allow different topics to distribute different contents related to the underlying dataset type contents.
## 1.0 REFERENCES
## 2.0 PROJECT ORGANIZATION
The project will have a dummy server and some clients that can send data to the server to alter the repository contents. The server should send updates to all subscribers and this way they should be able to update page contents when repository changes by actions on other client.

The structure then is a client developed with Angular that is going to be run from different terminals and a backend server developed with Springboot that will use a memory repository and a websockets service to accept subscriptions and send updates.

The UI interface will have buttons to create and update elements. Deletion is also possible but not necessary to implement on this POC. Once an element is created or updated it will fire the events from the backend that should update the frontend UI with the new data with no need to read again all records.

The backend should have then two services. The REST service for the standard CRUD management of data source contents and the REST service for the subcriptions and websockets connection.

### 2.1 Backend Server
```mermaid
graph LR
	Client --> REST
	Client --> WS
	DS
	UC
	subgraph Inbound
		REST --> APP
		WS --> Client
	end
	subgraph Outbound
		DS(DataSource) --> WS
	end
	subgraph APP
		UC(Use Case) --> DS
	end

````
### 2.2 Backend Implementation
The datasource will be a simple H2 database with a single type of records. Records are called Parts and have two attributes, a *nam* and a number that represents the *weight*.
The rest endpoints will have access to the *create* action of new Parts and the *update* of a current part. The update will help to identify actions when the item to be updated has changed before the update, something that can be identified also with the use of topics and subscriptions.

```mermaid
classDiagram
	class Part {
		+UUID: id
		-String: name
		-Integer: weight
	}
````
### 2.3 Frontend Client
The frontend UI will have two pages so the exit from the active page will do the subscription disconnection and help identify any problem with the connection/disconnection flow and identify memory leaks.

The Initial page is a non functional page that will only show the pod identification and the number of current elements on the datasource.
The action page will have a button to initiate the creation of a new Part and the list of current Parts on the repository that should be udpated whenever a repository change is commited.
### 2.4 Frontend Implementation
The list of Parts will have a button to initiate the edition of a Part contents. During edition we should also monitor the events so a change on that specific part will be intercepted and a warning message be issued indicating that the Parts contents have already changed on the backend. Data change colision is something specific to the application so this POC will not get deeper into a way to solve this on the UX.
The list of Parts has twp panels, one for the list and another for the Part being edited.
```mermaid
---
config:
  kanban:
    ticketBaseUrl: 'https://yourproject.atlassian.net/browse/#TICKET#'
---
kanban
	Pages
		ID01[Initial page with the POC description and the current count of parts. Has a connection to to /counter topic]@{ ticket: Main}
		ID02[Part List Page]@{ ticket: Parts}
	Panels
		ID04[POC Identification Panel]
	Controllers
	Adapters

````
## 3.0 DEPLOYMENT
To test the different WS scenarios and the possibilities we need a backend server available (possible under docker) and several clielts that will modify the same backend datasource repository interactively or simultaneously.
The different actions on one of those clients should be reflects on the other clients, Being the clients different instances of the same Angular application we can run that client from local installation with no need to make it a complex deployment to docker.
The *.deploy* directory contains the deployment and user test case preparation to start verifiing the POC functionalities.
 
