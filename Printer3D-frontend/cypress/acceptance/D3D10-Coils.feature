@D3D10
Feature: [D3D10]-[STORY] Have a list of all the Coils persisted on the backend.

    Coil list comes from the Coils repository at the backend. This initial concept will only store some of the information
    and it should be used to complete the list of Finishings.
    The Coils list should show a selected list of the Coil fields.
    The list is ordered alphabetically by Material and then by Color.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/ROLLOS"
        Then the page "CoilsPage" is activated
        Given the target is the panel of type "coils"

    @D3D10.01
    Scenario: [D3D10.01]-The coils page has a single panel and the variant is set to '-COIL-LIST-'
        And the page "CoilsPage" has 1 panels
        And the target has variant "-COIL-LIST-"

    @D3D10.02
    Scenario: [D3D10.02]-Check the title and the contents for the coils panel.
        Then the target has the title "/ROLLOS DISPONIBLES"
        Then the target has 15 "coil"

    @D3D10.03
    Scenario: [D3D10.03]-Validate the contents of a Coil.
        # - Check the coil contents visible on the list
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then field named "material" with label "MATERIAL" and value "PLA"
        And field named "color" with label "COLOR" and value "MORADO TRANSPARENTE"
        And field named "weight" with label "PESO" and value "600 gr."
        And target has an actionable image named "edit-button"
        And target has an actionable image named "save-button"
        And actionable image named "edit-button" is "enabled"
        And actionable image named "save-button" is "disabled"

    @D3D10.04
    Scenario: [D3D10.04]-The Coil has the weight field editable to do adjusts on the plastic consumed. If the edit is clicked then the edit in place and the save buttons are activated.
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        When target actionable image "edit-button" is clicked
        # - Reselect the target because the page has changed
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then form field named "weight" with label "PESO" and contents "600"
        And form field named "weight" is "valid"
        And actionable image named "edit-button" is "enabled"
        And actionable image named "save-button" is "enabled"

    @D3D10.05
    Scenario: [D3D10.05]-If the weight field is invalidated then the Save button is disabled.
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        When target actionable image "edit-button" is clicked
        # - Reselect the target because the page has changed
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then form field named "weight" with label "PESO" and contents "600"
        And form field named "weight" is "valid"
        # - Clear the field to invalidate it
        And empty is set on form field "weight"
        And form field named "weight" is "invalid"
        And actionable image named "save-button" is "disabled"

    @D3D10.06
    Scenario: [D3D10.06]-If the Save button is clicked then the Coil is updated and a notification panel is shown.
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        When target actionable image "edit-button" is clicked
        # - Reselect the target because the page has changed
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        And 700 is set on form field "weight"
        And actionable image named "save-button" is "enabled"
        When target actionable image "save-button" is clicked
        And there is a Notification panel
        # - Go back to the display state and close editing. Use the value returned by the api simulator
        Given the target the "coil" with id "4e7001ee-6bf5-40b4-9c15-61802e4c59ea"
        And field named "weight" with label "PESO" and value "750 gr."
        And actionable image named "edit-button" is "enabled"
        And actionable image named "save-button" is "disabled"
