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
        Given the target panel is the panel of type "coils"

    @D3D10 @D3D10.01
    Scenario: [D3D10.01]-The coils page has a single panel and the variant is set to '-COIL-LIST-'
        And the target page has "1" panels
        And the target panel has variant "-COIL-LIST-"

    @D3D10 @D3D10.02
    Scenario: [D3D10.02]-Check the title and the contents for the coils panel.
        Then the target panel has a title "/ROLLOS DISPONIBLES"
        Then the target panel has 15 "coil"

    @D3D10 @D3D10.03
    Scenario: [D3D10.03]-Validate the contents of a Coil.
        # - Check the model contents visible on the catalog
        Given the target item the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then the target item has a field named "material" with label "MATERIAL" and value "PLA"
        And the target item has a field named "color" with label "COLOR" and value "MORADO TRANSPARENTE"
        And the target item has a field named "weight" with label "PESO" and value "600 gr."
