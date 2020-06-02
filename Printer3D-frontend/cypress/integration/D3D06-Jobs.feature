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

    # @D3D06 @D3D06.03
    # Scenario: [D3D06.03]-The pending Jobs page has two main panels inside two columns. Check that both panels are visible.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     And one instance of v1-pending-jobs-panel
    #     And one instance of v1-pending-jobs-panel
    #     And one instance of v2-machines-panel

    # @D3D06 @D3D06.04
    # Scenario: [D3D06.04]-The machine list is at the right and displays at leash one Machine with some fields. Validate the fields.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     And one instance of v2-machines-panel
    #     And one or more instances of v2-machine-render
    #     And on the v2-machine-render component there is a field named "label"
    #     And on the v2-machine-render component there is a field named "model"
    #     And on the v2-machine-render component there is a field named "characteristics"
    #     And on the v2-machine-render component there is a field named "drop-job"

    # @D3D06 @D3D06.05
    # Scenario: [D3D06.05]-The pending job list at the left panel shows the job records and they have the next list of required fields.
    #    Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     And one instance of v1-pending-jobs-panel
    #     And one or more instances of v1-pending-job-render
    #     And on the v1-pending-job-render component there is a field named "label"
    #     And on the v1-pending-job-render component there is a field named "material"
    #     And on the v1-pending-job-render component there is a field named "color"
    #     And on the v1-pending-job-render component there is a field named "buildtime"

    # @D3D06 @D3D06.06
    # Scenario: [D3D06.06]-The pending job elements can be dragged to another place with the constraint 'JOB'.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     And one instance of v1-pending-jobs-panel
    #     And one or more instances of v1-pending-job-render
    #     Then the v1-pending-job-render is dragabble
    #     And there is a constraint named "JOB"

    # @D3D06 @D3D06.07
    # Scenario: [D3D06.07]-The machine panel has a drop slot where the user can drop PendingJobs.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     And one instance of v2-machines-panel
    #     And one or more instances of v2-machine-render
    #     Then the v2-machine-render is droppable
    #     And there is a constraint named "JOB"

    # @D3D06 @D3D06.08
    # Scenario: [D3D06.08]-If the user drops a Pending Job to a machine that has no job active then the COMENZAR button gets active and illuminates.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     When the Job is dragged and dropped on the Machine
    #     Then the machine has a Job
    #     Then the button "COMENZAR" is visible
    #     Then the button "COMENZAR" has the next properties
    #         | label    | state   |
    #         | Comenzar | enabled |
    #     Then the button "CLEAR" is visible
    #     And the button "CLEAR" has the next properties
    #         | label | state   |
    #         | Clear | enabled |
    #     And one instance of v1-build-countdown-timer-panel

    # @D3D06 @D3D06.09
    # Scenario: [D3D06.09]-If the Machine has a job atached the COMENZAR button is available. If the user clicks this button the build time count starts.
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the ProductionJobListPage is activated
    #     When the Job is dragged and dropped on the Machine
    #     Then the machine has a Job
    #     Then the button "COMENZAR" has the next properties
    #         | label    | state   |
    #         | Comenzar | enabled |
    # When there is a click on the "COMENZAR" button
    # Then the job is atached to the Machine persisted at the backend
    # Then the button "COMENZAR" has the next properties
    #     | label    | state   |
    #     | Comenzar | disabled |

    @D3D06 @D3D06.10
    Scenario: [D3D06.10]-If at initialization the Machine has already a running job the Part is shown on the part and the timer is activated.
        Given there is a click on Feature "/TRABAJOS PND."
        When the ProductionJobListPage is activated
        And the target Machine has a Part on build
        Then the target Machine button "COMENZAR" is not visible
        Then the target Machine button "CLEAR" is visible
        And the target Machine button "CLEAR" has the next properties
            | label | state   |
            | Clear | enabled |
        And the target Machine has one instance of "v1-pending-job-render"
        And the target Machine has one instance of "v1-build-countdown-timer-panel"

    @D3D06 @D3D06.11
    Scenario: [D3D06.11]-If the user clicks the CLEAR button it cancels the timer and clears the associated build Part.
        Given there is a click on Feature "/TRABAJOS PND."
        When the ProductionJobListPage is activated
        And the target Machine has a Part on build
        Then the target Machine button "COMENZAR" is not visible
        Then the target Machine button "CLEAR" is visible
        When there is a click on the "CLEAR" button
        Then the target Machine has no instances of "v1-build-countdown-timer-panel"
        Then the target Machine has no instances of "v1-pending-job-render"
