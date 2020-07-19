@D3D14
Feature: [D3D14]-[STORY] Once the user registers a job for build it should have to wait for job completion to interact with the Job.

    Register a job on a machione and wait for job completion.
    Once the Job completes check the flow when the job is accepted and when the job is rejected.

    Background: Application landing page
        Given a timed application Printer3DManager

    @D3D14 @D3D14.01
    Scenario: [D3D14.01]-Register a job on both the machines and await for job completion using test fast time forward.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        Given the target is the panel of type "jobs-list"
        Given the target job the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Given the target is the panel of type "machines"
        Given the target machine the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Given the target item the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the target Job is dragged and dropped on the target Machine
        When there is a click on the target item "START-BUILD" button
        Then advance time "5" minutes
        Then advance time "15" minutes
        Then there is a click on the target item "CANCEL" button

        Given the target machine the "machine" with id "d55a5ca6-b1f5-423c-9a47-007439534744"
        Given the target item the "machine" with id "d55a5ca6-b1f5-423c-9a47-007439534744"
        And the target Machine has "2" instances of "button"
        And the target item button with name "COMPLETE" has a label "Completar" and is "enabled"
        And the target item button with name "CANCEL" has a label "Cancelar" and is "enabled"
        When there is a click on the target item "COMPLETE" button

    # @D3D13.07
    #     Scenario: [D3D13.07]-When the job completes the number of copies continues not being editable.

    @D3D13.09
    Scenario: [D3D13.09]-When the job completes the number of copies continues not being editable.
        # - Use a visit initialization with time configured
        Given a timed application Printer3DManager
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        Then the target has 1 "job-timer"
        # - Change the number of copies to 2
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        And 2 is set on form field "quantity"
        # - Click the Start and advance the time to complete the job
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the button with name "start-button" is clicked
        Then advance time "1" minutes
        Then advance time "5" minutes
        Then advance time "30" minutes
        Then advance time "30" minutes
        # - Validate the new state for the Machine
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then the field named "timer" contains "0H00M"
        And the target has 2 "buttons"
        And the button with name "complete-button" has a label "Completar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
        # - Then validate that the quantity field is not editable
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        And field named "quantity-data" with label "CANTIDAD" and value "2"
