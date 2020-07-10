@D3D13
Feature: [D3D13]-[STORY] Steps to define the interactions with a Machine.

    The list of Machines is not editable but by the system administrator. They are rendered on a fixed postion panel to avoid them from scrolling out of view.
    If the Machine is IDLE it does not have any Part on the build slot. Neither it has activation buttons not the timer counter.
    If a Part is dropped on the Machine the actuator buttons are shown and the counter initialized to the Part build time.
    If the Start button is clicked the Machine starts the Part build and the timer starts to count down.
    If the Clear button is clicked the Part is removed from the build slot.
    If the Machine is RUNNING then the build timer is shown. There is only visible the Cancel button to stop Part building.
    If the Machine is RUNNING and the build remaining time reaches zero then there is a new Button named Complete to complete the build and store the part on the inventory stock.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D13 @D3D13.01
    Scenario: [D3D13.01]-The list of Machines is a fixed list at the right panel and should show some fields.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        Given the target is the panel of type "machines"
        Then the target has the title "/MAQUINAS"
        Then the target has 2 "machine"

    @D3D13 @D3D13.02
    Scenario: [D3D13.02]-Any of the machines has a list of fields. If the Part is not assigned then there are no buttons.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        Given the target is the panel of type "machines"
        # - Validate the content for the Machine fields
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then field named "label" with label "ETIQUETA" and value "Ender 3 Pro - A"
        And field named "model" with label "MODEL" and value "Creality 3D Ender 3 Pro"
        And field named "characteristics" with label "CARACTERISTICAS" and value "Max size set to 200mm. Has adaptor for flexible plastic filament."
        # - There is a panel for dropping parts and empty
        And the target has a drop place named "dropJobs"
        And the target has a panel labeled "PIEZA EN PRODUCCION" named "buildingPart"
        And the panel "buildingPart" has no "job"
        # - There are no buttons
        And the target has no "buttons"

    @D3D13 @D3D13.03
    Scenario: [D3D13.03]-When one Part is dropped on the Part slot then the right panel activates with the time counter and the interaction buttons.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        # - Check that the buttons are shown and also the timer
        Then the target has 1 "job-timer"
        And the button with name "start-button" has a label "Comenzar" and is "enabled"
        And the button with name "clear-button" has a label "Clear" and is "enabled"

    @D3D13 @D3D13.04
    Scenario: [D3D13.04]-Once Part is dropped and the buttons are visible, if the user clicks the Clear button the job is cancelled and the Part is removed from the slot.
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
        # - Click the Cancel and clear the job
        When the button with name "clear-button" is clicked
        Then there is a Notification panel
        And the target has no "job-timer"
        And the target has no "job"
        And the target has no "buttons"

    @D3D13 @D3D13.05
    Scenario: [D3D13.05]-If the Start button is clicked then the Machine changes to RUNNING state, the job is submited for build to the backend and the timer starts to countdown.
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
        # - Click the Start and clear the job
        When the button with name "start-button" is clicked
        Then the Job is started and sent to the background
        And there is a Notification panel
        # REVIEW
        # And the build-countdown-timer item has started countdown
        And the target has 1 "job-timer"
        And the target has 1 "job"
        And the target has 1 "buttons"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D13.06
    Scenario: [D3D13.06]-If there is a Job running and the Cancel button is clicked the job is cancelled and the Machine returns to idle.
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
        # - Click the Start and clear the job
        When the button with name "start-button" is clicked
        Then the Job is started and sent to the background
        And there is a Notification panel
        # - Click the cancel to stop the build and clear the machine
        When the button with name "cancel-button" is clicked
        Then there is a Notification panel
        And the target has no "job-timer"
        And the target has no "job"
        And the target has no "buttons"

    @D3D13.07
    Scenario: [D3D13.07]-When a Part id dropped on the machine the Job block displays some job information and has the count of copies editable.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Validate the fields of the job displayed
        And form field named "quantity" with label "CANTIDAD" and contents "1"
        And form field named "quantity" is "valid"
        And field named "label" with label "ETIQUETA" and value "Covid-19 Key"
        And field named "material" with label "MATERIAL/COLOR" and value "PLA/NARANJA-T"
        And field named "buildTime" with label "TIEMPO" and value "30"

    @D3D13.08
    Scenario: [D3D13.08]-If the number of copies is invalidated then the Start button is disabled.
        Given there is a click on Feature "/TRABAJOS PND."
        Then the page "Production Jobs Page" is activated
        # - Select a Job for drag
        Given the target is the panel of type "jobs-list"
        Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        # - Drag a part to the drop contents
        Given the target is the panel of type "machines"
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        When the drag source is dragged to the drop destination "dropJobs"
        # - Invalidate the contents of the copies
        Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
        And empty is set on form field "quantity"
        When the mouse exits the target
        Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
        Then the target has no "buttons"

    # WARNING - Jasmine clock is not working as expected. Until fixed this type of tests are disabled.
    # @D3D13.09
    # Scenario: [D3D13.09]-When the job completes the number of copies continues not being editable.
    #     # - Use a visit initialization with time configured
    #     Given a timed application Printer3DManager
    #     Given there is a click on Feature "/TRABAJOS PND."
    #     Then the page "Production Jobs Page" is activated
    #     # - Select a Job for drag
    #     Given the target is the panel of type "jobs-list"
    #     Given the drag source the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
    #     # - Drag a part to the drop contents
    #     Given the target is the panel of type "machines"
    #     Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
    #     When the drag source is dragged to the drop destination "dropJobs"
    #     Then the target has 1 "job-timer"
    #     # - Change the number of copies to 2
    #     Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
    #     And 2 is set on form field "quantity"
    #     # - Click the Start and advance the time to complete the job
    #     Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
    #     When the button with name "start-button" is clicked
    #     Then advance time "1" minutes
    #     Then advance time "5" minutes
    #     Then advance time "30" minutes
    #     Then advance time "30" minutes
    #     # - Validate the new state for the Machine
    #     Given the target the "machine" with id "e18aa442-19cd-4b08-8ed0-9f1917821fac"
    #     Then the field named "timer" contains "0H00M"
    #     And the target has 2 "buttons"
    #     And the button with name "complete-button" has a label "Completar" and is "enabled"
    #     And the button with name "cancel-button" has a label "Cancelar" and is "enabled"
    #     # - Then validate that the quantity field is not editable
    #     Given the target the "job" with id "5d16edd1-6de3-4a74-a1bb-4f6cd476bf56"
    #     And field named "quantity-data" with label "CANTIDAD" and value "2"
