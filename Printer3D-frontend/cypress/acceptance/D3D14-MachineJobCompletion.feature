@D3D14
Feature: [D3D14]-[STORY] Once the user registers a job for build it should have to wait for job completion to interact with the Job.

    Register a job on a machione and wait for job completion.
    Once the Job completes check the flow when the job is accepted and when the job is rejected.

    Background: Application landing page
        # - Use a visit initialization with time configured
        Given a timed application Printer3DManager
        Given one instance of Dock
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        When the loading panel completes

    @D3D14.01
    Scenario: [D3D14.01]-Register a job on one machine and advance the time to see that the timer is running.
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        Then the button with name "start-button" has a label "Comenzar" and is "enabled"
        # - Check th timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "0"
        Then column named "minutes" has contents "30"
        # - Click the button to start the timer
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the button with name "start-button" is clicked
        Then advance time "5" minutes
        Then advance time "15" minutes
        # - Check the timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "0"
        Then column named "minutes" has contents "10"

    @D3D14.02
    Scenario: [D3D14.02]-Register a job a machine and wait for time completion. When the job completes the buttons change.
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        Then the button with name "start-button" has a label "Comenzar" and is "enabled"
        # - Check th timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "0"
        Then column named "minutes" has contents "30"
        # - Click the button to start the timer
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the button with name "start-button" is clicked
        Then advance time "5" minutes
        Then advance time "15" minutes
        Then advance time "15" minutes
        # - Check th timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "0"
        Then column named "minutes" has contents "0"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then the target has 2 "buttons"
        Then the button with name "complete-button" has a label "Completar" and is "enabled"
        Then the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D14.03
    Scenario: [D3D14.03]-When the job completes the number of copies continues not being editable.
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        Then the button with name "start-button" has a label "Comenzar" and is "enabled"
        When 2 is set on form field "quantity"
        And the mouse exits the target
        # - Check the timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "1"
        Then column named "minutes" has contents "00"
        # - Click the button to start the timer
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the button with name "start-button" is clicked
        Then advance time "5" minutes
        Then advance time "15" minutes
        Then advance time "15" minutes
        Then advance time "30" minutes
        Then advance time "5" minutes
        # - Check th timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "0"
        Then column named "minutes" has contents "0"
        # - Validate the new state for the Machine
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        And the target has 2 "buttons"
        And the button with name "complete-button" has a label "Completar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        # - Then validate that the quantity field is not editable
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        And field named "quantity-data" with label "CANTIDAD" has contents "2"

    @D3D14.04
    Scenario: [D3D14.04]-When the build job enters the last minute then the timer color changes.
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        Then the button with name "start-button" has a label "Comenzar" and is "enabled"
        When 2 is set on form field "quantity"
        And the mouse exits the target
        # - Check the timer status
        Given the target has a component ot type "build-countdown-timer"
        Then column named "hours" has contents "1"
        Then column named "minutes" has contents "00"
        # - Click the button to start the timer
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the button with name "start-button" is clicked
        Then advance time "5" minutes
        Then advance time "50" minutes
        Then advance time "4" minutes
        Then field "hours" has color "rgb(154, 205, 50)"
        Then field "minutes" has color "rgb(154, 205, 50)"
        Then advance time "1" minutes
        Then field "hours" has color "rgb(255, 255, 0)"
        Then field "minutes" has color "rgb(255, 255, 0)"
