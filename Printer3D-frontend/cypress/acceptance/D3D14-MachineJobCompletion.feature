@D3D14
Feature: [D3D14]-[STORY] Once the user registers a job for build it should have to wait for job completion to interact with the Job.

    Register a job on a machione and wait for job completion.
    Once the Job completes check the flow when the job is accepted and when the job is rejected.

    Background: Application landing page
        Given a timed application Printer3DManager

    @D3D14 @D3D14.01
    Scenario: [D3D14.01]-Register a job on both the machines and await for job completion using test fast time forward.
        Given there is a click on Feature "/TRABAJOS PND."
        Given the target panel is the panel named "jobs-list"
        Given the target job the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        Given the target panel is the panel named "machines"
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
