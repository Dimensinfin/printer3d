@D3D06
Feature: [D3D06]-New Feature to display the list of jobs then are required to stabilize the stocks.

    After some Parts are created on the Inventory and the stock levels are set then we should execute tasks
    to see if the current stock levels suits was it is desired. If not we should create a list of **Pending**
    jobs that are the work expected to be built on the machines over the next hours.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager

    @D3D06 @D3D06.01
    Scenario: [D3D06.01]-Check that there is a new Feature on the Dock.
        Given one instance of Dock
        Then there is a Feature with label "/TRABAJOS PND."

    @D3D06 @D3D06.02
    Scenario: [D3D06.02]-Validate the content of the Jobs page once the Jobs feature is clicked.
        Given there is a click on Feature "/TRABAJOS PND."
        When the page "ProductionJobListPage" is activated
        Given the target panel is the panel named "jobs-list"
        Then the target panel has one or more "job"

    @D3D06 @D3D06.03
    Scenario: [D3D06.03]-The pending Jobs page has two main panels inside two columns. Check that both panels are visible.
        Given there is a click on Feature "/TRABAJOS PND."
        When the page "ProductionJobListPage" is activated
        Then the page "ProductionJobListPage" has 2 panels
        Given the target panel is the panel named "jobs-list"
        Then the target panel has a title "/TRABAJOS/PENDIENTES"
        Given the target panel is the panel named "machines"
        Then the target panel has a title "/MAQUINAS"

    @D3D06 @D3D06.05
    Scenario: [D3D06.05]-The pending job list at the left panel shows the job records and they have the next list of required fields.
        Given there is a click on Feature "/TRABAJOS PND."
        When the page "ProductionJobListPage" is activated
        Given the target panel is the panel named "jobs-list"
        Given the target item the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Then the target item has a field labeled "ETIQUETA" with value "Covid-19 Key"
        Then the target item has a field labeled "MATERIAL" with value "PLA"
        Then the target item has a field labeled "COLOR" with value "NARANJA-T"
        Then the target item has a field labeled "TIEMPO" with value "30"

    @D3D06 @D3D06.06
    Scenario: [D3D06.06]-The pending job elements can be dragged to another place with the constraint 'JOB'.
        Given there is a click on Feature "/TRABAJOS PND."
        When the page "ProductionJobListPage" is activated
        Given the target panel is the panel named "jobs-list"
        Given the target item the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Then the target item is draggable and with the contraint "JOB"

    @D3D06 @D3D06.07
    Scenario: [D3D06.07]-The machine panel has a drop slot where the user can drop PendingJobs with the constraint 'JOB'.
        Given there is a click on Feature "/TRABAJOS PND."
        When the page "ProductionJobListPage" is activated
        Given the target panel is the panel named "machines"
        Given the target item the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then the target item is droppable and with the contraint "JOB"
