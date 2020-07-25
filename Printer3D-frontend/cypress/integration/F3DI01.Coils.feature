@F3DI01
Feature: [F3DI01]-[STORY] Manage coils on integration

    The Coil llist has already some items.
    Create a new Coil and check that it now appears on the Coil list. Because the new coil has the same finishing than previous coils check the size increase.
    Set the weight for the new coil to 0 grames.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/ROLLOS"
        Then the page "CoilsPage" is activated
        Given the target is the panel of type "coils"

    @F3DI01.01
    Scenario: [F3DI01.01]-Count the coils already on the list and store the number of coils of material TEST.
        When store the "coil" count on target
        And store the test coil count on target
        # - Create a new coil
        Given one instance of Dock
        When there is a click on Feature "/NUEVO ROLLO"
        Then the "New Coil" dialog opens and blocks the display
        Given "TEST" is set on form field "material"
        And "AMARILLO" is set on form field "color"
        And 800 is set on form field "weight"
        When  the button with name "submit-button" is clicked
        # Then the coil is persisted at the backend
        # And the dialog closes
        # - Check that there is a new TEST coil
        Given the target is the panel of type "coils"
        Then the test coil count increases by 1
