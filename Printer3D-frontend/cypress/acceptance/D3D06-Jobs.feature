@D3D06
Feature: [D3D06]-New Feature to display the list of jobs then are required to stabilize the stocks.

    After some Parts are created on the Inventory and the stock levels are set then we should execute tasks
    to see if the current stock levels suits was it is desired. If not we should create a list of **Pending**
    jobs that are the work expected to be built on the machines over the next hours.
    [STORY] The Pending Jobs panel has a counter of the number of jobs in the queue.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager
        Given one instance of Dock
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        When the loading panel completes
        When the loading panel completes

    @D3D06 @D3D06.02
    Scenario: [D3D06.02]-Validate the content of the Jobs page once the Jobs feature is clicked.
        Given the target is the panel of type "jobs-list"
        Then the target has 43 "job"
        Given the target is the panel of type "machines"
        Then the target has 4 "machine"

    @D3D06 @D3D06.03
    Scenario: [D3D06.03]-The pending Jobs page has two main panels inside two columns. Check that both panels are visible.
        Then the page "ProductionJobListPage" has 2 panels
        Given the target is the panel of type "jobs-list"
        Then the target has the title "/TRABAJOS/PENDIENTES"
        Given the target is the panel of type "machines"
        Then the target has the title "/MAQUINAS"

    @D3D06 @D3D06.05
    Scenario: [D3D06.05]-The pending job list at the left panel shows the job records and they have the next list of required fields.
        Given the target is the panel of type "jobs-list"
        Then the target has 43 "job"
        Given the target the "job" with id "a31c1e8c-8f09-4b44-aa25-e1e6706ff036"
        Then field named "quantity" with label "CANTIDAD" has contents "x 15"
        And field named "label" with label "ETIQUETA" has contents "PLETINA SUPERIOR 1.32"
        And field named "material" with label "MATERIAL/COLOR" has contents "PLA/BLUE"
        And field named "buildTime" with label "TIEMPO" has contents "60 min."

    @D3D06 @D3D06.06
    Scenario: [D3D06.06]-The pending job elements can be dragged to another place with the constraint 'JOB'.
        Given the target is the panel of type "jobs-list"
        Given the target the "job" with id "a31c1e8c-8f09-4b44-aa25-e1e6706ff036"
        Then the target item is draggable and with the contraint "JOB"

    @D3D06 @D3D06.07
    Scenario: [D3D06.07]-The machine panel has a drop slot where the user can drop PendingJobs with the constraint 'JOB'.
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then the target item is droppable and with the contraint "JOB"

    @D3D06 @D3D06.08
    Scenario: [D3D06.08]-Jobs can have different priorities. Higher priority jobs have a different background tint.
        Given the target is the panel of type "jobs-list"
        Given the target the "job" with id "a9fc453a-856e-48b7-8553-ba0ed4994036"
        Then hidden field named "PRIORIDAD" has contents "1"
        Given the target the "job" with id "a8274fe2-aeba-4eda-a71b-0a8c7250e738"
        Then hidden field named "PRIORIDAD" has contents "2"

    @D3D06.09
    Scenario: [D3D06.09]-The Pending Jobs list page has a counter with the number of pending jobs shown.
        Given the target is the panel of type "jobs-list"
        Then the target has 43 "job"
        And field named "jobCounter" has contents "43"
