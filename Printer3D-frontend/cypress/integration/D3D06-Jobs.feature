@D3D06
Feature: [D3D06]-New Feature to display the list of jobs then are required to stabilize the stocks.

    After some Parts are created on the Inventory and the stock levels are set then we should execute tasks
    to see if the current stock levels suits was it is desired. If not we should create a list of **Pending**
    jobs that are the work expected to be built on the machines over the next hours.

    Background: Start the application and move to the initial page
        Given the application Printer3DManager

    # @D3D06 @D3D06.01
    # Scenario: [D3D06.01]-Check that there is a new Feature on the Dock.
    #     Given one instance of Dock
    #     Then there are a Feature with label "/TRABAJOS PND."

    # @D3D06 @D3D06.02
    # Scenario: [D3D06.02]-Validate the content of the Jobs page once the Jobs feature is clicked.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     # And one instance of v1-pending-jobs-panel
    #     And one or more instances of v1-pending-job-render

    @D3D06 @D3D06.03
    Scenario: [D3D06.03]-The pending Jobs page has two main panels inside two columns. Check that both panels are visible.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the ProductionJobListPage is activated
        And one instance of v1-pending-jobs-panel
        And one instance of v1-machines-panel

    @D3D06 @D3D06.04
    Scenario: [D3D06.03]-The pending Jobs page has two main panels inside two columns. Check that both panels are visible.
