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

    # @D3D10.01
    # Scenario: [D3D10.01]-The coils page has a single panel and the variant is set to '-COIL-LIST-'
    #     And the page "CoilsPage" has 1 panels
    #     And the target has variant "-COIL-LIST-"

    # @D3D10.02
    # Scenario: [D3D10.02]-Check the title and the contents for the coils panel.
    #     Then the target has the title "/ROLLOS DISPONIBLES"
    #     Then the target has 15 "coil"

    # @D3D10.03
    # Scenario: [D3D10.03]-Validate the contents of a Coil.
    #     # - Check the coil contents visible on the list
    #     Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
    #     Then field named "material" with label "MATERIAL" and value "PLA"
    #     And field named "color" with label "COLOR" and value "MORADO TRANSPARENTE"
    #     And field named "weight" with label "PESO" and value "600 gr."
    #     And the target has a actionable image named "edit-button"
    #     And the target has a actionable image named "save-button"
    #     And the actionable image named "edit-button" is "enabled"
    #     And the actionable image named "save-button" is "disabled"

    @D3D10.04
    Scenario: [D3D10.04]-The Coil has the weight field editable to do adjusts on the plastic consumed. If the edit is clicked then the edit in place and the save buttons are activated.
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        When the target item actionable image "edit-button" is clicked
        # - Reselect the target because the page has changed
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then form field named "weight" with label "PESO" and contents "600"
        And form field named "weight" is "valid"
        And the actionable image named "edit-button" is "enabled"
        And the actionable image named "save-button" is "enabled"
