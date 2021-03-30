@D3D08
Feature: [D3D08]-[STORY] There should be a Feature to create Models. Models have some data fields and an area where the user can deploy from a list of parts to define the Parts that compose the model.

    To create Models we are going to use a Page and not a Dialog. We need to allow to drag and drop Parts into the Model for its construction.
    A Model is a set of Parts that can have a different price and packaging than the parts alone.
    Models should not be edited since a change on a Model should really be the creation of a new Model reference.
    A same model can be created using same parts but of different color. This should be a different Model and have a different Label.
    Labels can be repeated and are not editable.
    Price should be editable because things can change in value depending on demand.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/NUEVO MODELO"
        Then the page "NewModelPage" is activated
        And the loading panel shows "Clasificando Piezas..."
        When the loading panel completes

    # - H A P P Y   P A T H
    @D3D08 @D3D08.03
    Scenario: [D3D08.03]-The Parts panel has a simplified, one level list of Parts. The listing contains Parts with any number of copies on stock but that are active.
        Given the target is the panel of type "available-parts"
        Then the target has the title "/PIEZAS/DISPONIBLES"
        And the target has 13 "part"
        # - Validate the Part contents
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then field named "ETIQUETA" with label "ETIQUETA" has contents "Boquilla Ganesha - Figura"
        Then field named "MATERIAL" with label "MATERIAL" has contents "PLA/ROSA"
        Then field named "DISPONIBLE" with label "DISPONIBLE" has contents "0"
        Then field named "active" has contents "ACTIVA"

    @D3D08 @D3D08.04
    Scenario: [D3D08.04]-The New Model panel has some input fields and a place to drop the Parts that are to be used on the Model.
        Given the target is the panel of type "new-model"
        Then the target has the title "/NUEVO MODELO/DEFINICION"
        Then form field named "label" with label "ETIQUETA" is empty
        Then form field named "price" with label "PRECIO" is empty
        Then form field named "stock" with label "NIVEL STOCK DESEADO" is empty
        And form field named "label" is "invalid"
        And form field named "price" is "invalid"
        And form field named "stock" is "invalid"
        And the target panel has a drop place named "drop-part-location"
        And the button with name "save-button" has a label "Guardar" and is "disabled"

    @D3D08 @D3D08.05
    Scenario: [D3D08.05]-The New Model panel has buttons to save the Model. The Save button is only active when all the constraints are validated.
        Given the target is the panel of type "new-model"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"
        When "Modelo de prueba 1" is set on the target panel input field named "label"
        Then form field named "label" is "valid"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"
        And "2" is set on the target panel input field named "price"
        Then form field named "price" is "valid"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"
        And "8" is set on the target panel input field named "stock"
        Then form field named "stock" is "valid"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"

        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Then the button with name "save-button" has a label "Guardar" and is "enabled"

    @D3D08 @D3D08.06
    Scenario: [D3D08.06]-When one or more Parts are dropped on the Model the dropped Part information has some required fields.
        # Given there is a click on Feature "/NUEVO MODELO"
        # Then the page "NewModelPage" is activated
        Given the target is the panel of type "new-model"
        When "Modelo de prueba 1" is set on the target panel input field named "label"
        And "2" is set on the target panel input field named "price"
        And "8" is set on the target panel input field named "stock"

        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"

        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "068368c3-ab2c-4140-b2b1-039365a409b7"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "068368c3-ab2c-4140-b2b1-039365a409b7"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "068368c3-ab2c-4140-b2b1-039365a409b7"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"

        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "cb3f7075-e364-4c41-8d4b-a31a8f2039fe"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"

        Given the target the "part-stack" with id "068368c3-ab2c-4140-b2b1-039365a409b7"
        Then column named "quantity" has contents "3"
        And column named "label" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "material" has contents "PLA/ROJO"
        And target has an actionable image named "remove-button"

    # DRAGGING IS NOT WORKING
    # @D3D08 @D3D08.07
    # Scenario: [D3D08.07]-The Part on the Model has a button that allows to remove parts from the Model. If the counter reaches 0 the Part is removed.
    #     Given there is a click on Feature "/NUEVO MODELO"
    #     Then the page "NewModelPage" is activated

    #     Given the target is the panel of type "available-parts"
    #     And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
    #     Given the target is the panel of type "new-model"
    #     When the drag source is dragged to the drop destination "dropParts"
    #     Given the target is the panel of type "available-parts"
    #     And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
    #     Given the target is the panel of type "new-model"
    #     When the drag source is dragged to the drop destination "dropParts"
    #     Given the target is the panel of type "available-parts"
    #     And the drag source the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
    #     Given the target is the panel of type "new-model"
    #     When the drag source is dragged to the drop destination "dropParts"

    #     Given the target the "part-stack" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
    #     And the target panel has 1 "part-stack"
    #     Then column named "quantity" has contents "3"
    #     And column named "label" has contents "Boquilla Ganesha - Embocadura"
    #     And column named "material" has contents "TPU/AZUL"
    #     And target has an actionable image named "remove-button"

    #     When target actionable image "remove-button" is clicked
    #     Then column named "quantity" has contents "2"
    #     When target actionable image "remove-button" is clicked
    #     When target actionable image "remove-button" is clicked
    #     And the target panel has no "part-stack"

    @D3D08 @D3D08.08
    Scenario: [D3D08.08]-If the Save button is active and it is clicked then the Model is persisted at the repository, there is a notification panel and the page is closed returning to the Dashboard.
        # Given there is a click on Feature "/NUEVO MODELO"
        # Then the page "NewModelPage" is activated
        Given the target is the panel of type "new-model"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"
        When "Modelo de prueba 1" is set on the target panel input field named "label"
        Then form field named "label" is "valid"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"
        And "2" is set on the target panel input field named "price"
        Then form field named "price" is "valid"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"
        And "8" is set on the target panel input field named "stock"
        Then form field named "stock" is "valid"
        Then the button with name "save-button" has a label "Guardar" and is "disabled"

        Given the target is the panel of type "available-parts"
        And the drag source the "part" with id "754e6cc5-1a8a-435b-8c17-956b2a8391a7"
        Given the target is the panel of type "new-model"
        When the drag source is dragged to the drop destination "dropParts"
        Then the button with name "save-button" has a label "Guardar" and is "enabled"

        When the button with name "save-button" is clicked
        Then the Model is persisted at the backend
        And there is a Notification panel
        And the active page is set to Dashboard
        And there are no Features active

    @D3D08 @D3D08.09
    Scenario: [D3D08.09]-On The list of available parts there is none with the state inactive.
        Given the target is the panel of type "available-parts"
        Then the target has the title "/PIEZAS/DISPONIBLES"
        And the target has 13 "part"
        Then number of "part" with field "active" set to "FUERA PROD." is 0
