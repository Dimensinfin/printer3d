@D3D10
Feature: [D3D10]-[STORY] Have a list of all the Coils persisted on the backend.

    [STORY][FRONTEND] Coil list comes from the Coils repository at the backend. This initial concept will only store some of the information
    and it should be used to complete the list of Finishings.
    [STORY][FRONTEND] The Coils list should show a selected list of the Coil fields.
    [STORY][FRONTEND] Coils have an additional field that is the color code. This way even the color name for a coil can be changed then the set of
    equivalent coils should remain the same.
    [STORY][FRONTEND] The list is ordered alphabetically by Material and then by Color Group and inside a group by weight descending.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/ROLLOS"
        Then the page "CoilsPage" is activated
        When the loading panel completes
        Given the target is the panel of type "coils"

    @D3D10.01
    Scenario: [D3D10.01]-The coils page has a single panel and the variant is set to '-COIL-LIST-'
        And the page "CoilsPage" has 1 panels
        And the target has variant "-COIL-LIST-"

    @D3D10.02
    Scenario: [D3D10.02]-Check the title and the contents for the coils panel.
        Then the target has the title "/ROLLOS DISPONIBLES"
        Then the target has 13 "coil"

    @D3D10.03
    Scenario: [D3D10.03]-Validate the contents of a Coil.
        # - Check the coil contents visible on the list
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then field named "tradeMark" with label "MARCA" and value "EOLAS"
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
        Given the target the "coil" with id "e4d990d9-b8c4-438b-86da-987e17ee9116"
        Then field named "material" with label "MATERIAL" and value "PLA"
        And field named "tradeMark" with label "MARCA" and value "EOLO"
        And field named "color" with label "COLOR" and value "ROJO"
        And field named "label" with label "ETIQUETA" and value "001-ROJO-ALTA CALIDAD"
        And field named "weight" with label "PESO" and value "800 gr."
        And actionable image named "edit-button" is "enabled"
        And actionable image named "save-button" is "disabled"

    @D3D10.07
    Scenario: [D3D10.07]-Validate the contents of a Coil with the new added fields.
        # - Check the coil contents visible on the list
        Given the target the "coil" with id "be890c79-1d21-415e-8a38-ef20b0288b04"
        Then field named "material" with label "MATERIAL" and value "PLA"
        And field named "tradeMark" with label "MARCA" and value "ESUN"
        And field named "color" with label "COLOR" and value "AZUL BRILLANTE"
        And field named "label" with label "ETIQUETA" and value "AZUL BRILLANTE CLARO"
        And field named "weight" with label "PESO" and value "700 gr."
        And target has an actionable image named "edit-button"
        And target has an actionable image named "save-button"
        And actionable image named "edit-button" is "enabled"
        And actionable image named "save-button" is "disabled"

    @D3D10.08
    Scenario: [D3D10.08]-If the Save button is clicked and the color was changed then the coil is updated and the label changed.
        Given the target the "coil" with id "be890c79-1d21-415e-8a38-ef20b0288b04"
        When target actionable image "edit-button" is clicked
        # - Reselect the target because the page has changed
        Given the target the "coil" with id "be890c79-1d21-415e-8a38-ef20b0288b04"
        And 700 is set on form field "weight"
        And "AZUL OSCURO" is set on form field "color"
        And actionable image named "save-button" is "enabled"
        When target actionable image "save-button" is clicked
        And there is a Notification panel
        # - Go back to the display state and close editing. Use the value returned by the api simulator
        Given the target the "coil" with id "e4d990d9-b8c4-438b-86da-987e17ee9116"
        Then field named "material" with label "MATERIAL" and value "PLA"
        And field named "tradeMark" with label "MARCA" and value "EOLO"
        And field named "color" with label "COLOR" and value "ROJO"
        And field named "label" with label "ETIQUETA" and value "001-ROJO-ALTA CALIDAD"
        And field named "weight" with label "PESO" and value "800 gr."
        And actionable image named "edit-button" is "enabled"
        And actionable image named "save-button" is "disabled"

    @D3D10.09
    Scenario: [D3D10.09]-When the Coil is set in editable mode the Trade Mark is not editable.
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        When target actionable image "edit-button" is clicked
        # - Reselect the target because the page has changed
        Given the target the "coil" with id "653abddd-290b-47f9-9e1c-941ff9df90dd"
        Then field named "tradeMark" is not editable

    @D3D10.10
    Scenario: [D3D10.10]-There is a checkbox to change the filter state.
        Then form field named "inactiveFilter" with label "Filtrar elementos inactivos." has contents "on"

    @D3D10.11
    Scenario: [D3D10.11]-If the filter is deactivated then the inactive elements are visible.
        Then the target has 13 "coil"
        Then form field named "inactiveFilter" with label "Filtrar elementos inactivos." has contents "on"
        When form checkbox named "inactiveFilter" is clicked
        And the loading panel completes
        # - Count the new list of Coils
        Then the target has 23 "coil"

    @D3D10.12
    Scenario: [D3D10.12]-The Coils list page has a form field that will operate as a content match filter.
        Then form field named "filter" with label "FILTRO" is empty

    @D3D10.1
    Scenario: [D3D10.13]-The Coils filter is changed the number of coils also changes.
        Then form field named "filter" with label "FILTRO" is empty
        And the target has 13 "coil"
        When "tpu" is set on form field "filter"
        Then the target has 1 "coil"
