@D3D10
Feature: [D3D10]-[STORY] Have a list of all the Coils persisted on the backend.

    Coil list comes from the Coils repository at the backend. THis initial concept will only store some of the information
    and it should be used to complete the list of Finishings.
    The Coils list should show a selected list of the Coil fields.
    The list is ordered alphabetically by Material and then by Color.

    Background: Application landing page
        Given the application Printer3DManager

    @D3D10 @D3D10.01
    Scenario: [D3D10.01]-Check that the Feature points to the right page component.
        Given one instance of Dock
        When the Feature with label "/ROLLOS" is clicked the destination is the Page "v2-coil-list-page"

    @D3D10 @D3D10.02
    Scenario: [D3D10.02]-The Coils page has a single panel that contains the list of Coils.
        Given there is a click on Feature "/ROLLOS"
        Then the V2CoilListPage is activated
        And the V2CoilListPage has 1 panels
        And the panel on page V2CoilListPage has variant "-COIL-LIST-"
        Given the target panel is the panel with variant "-COIL-LIST-"
        And the target panel has one or more "v1-coil"

    @D3D10 @D3D10.03
    Scenario: [D3D10.03]-Check that the Inventory Coil List informs of the required fields.
        Given there is a click on Feature "/ROLLOS"
        Then the V2CoilListPage is activated
        Given the target panel is the panel with variant "-COIL-LIST-"
        Given the target Coil is one of color "MORADO TRANSPARENTE"
        And on the target Coil there is a field labeled "MATERIAL" with field name "material" and the value "PLA"
        And on the target Coil there is a field labeled "COLOR" with field name "color" and the value "MORADO TRANSPARENTE"
        And on the target Coil there is a field labeled "PESO" with field name "weight" and the value "600 gr."
