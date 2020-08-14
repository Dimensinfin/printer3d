@D3D06
Feature: [D3D06]-New Feature to display the list of jobs then are required to stabilize the stocks.

    After some Parts are created on the Inventory and the stock levels are set then we should execute tasks
    to see if the current stock levels suits was it is desired. If not we should create a list of **Pending**
    jobs that are the work expected to be built on the machines over the next hours.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager
        Given one instance of Dock
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        When the loading panel completes

    @D3D06 @D3D06.02
    Scenario: [D3D06.02]-Validate the content of the Jobs page once the Jobs feature is clicked.
        Given the target is the panel of type "jobs-list"
        Then the target panel has one or more "job"

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
        Then the target has 5 "job"
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Then field named "quantity" with label "CANTIDAD" has contents "x 2"
        And field named "label" with label "ETIQUETA" has contents "Covid-19 Key"
        And field named "material" with label "MATERIAL/COLOR" has contents "PLA/NARANJA-T"
        And field named "buildTime" with label "TIEMPO" has contents "30 min."

    @D3D06 @D3D06.06
    Scenario: [D3D06.06]-The pending job elements can be dragged to another place with the constraint 'JOB'.
        Given the target is the panel of type "jobs-list"
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Then the target item is draggable and with the contraint "JOB"

    @D3D06 @D3D06.07
    Scenario: [D3D06.07]-The machine panel has a drop slot where the user can drop PendingJobs with the constraint 'JOB'.
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then the target item is droppable and with the contraint "JOB"

    @D3D06 @D3D06.08
    Scenario: [D3D06.08]-Jobs can have different priorities. Higher priority jobs have a different background tint.
        Given the target is the panel of type "jobs-list"
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Then hidden field named "PRIORIDAD" has contents "1"
        Given the target the "job" with id "1682544c-364b-4e30-b097-fd181bcc50a5"
        Then hidden field named "PRIORIDAD" has contents "2"
