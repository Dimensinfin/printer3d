@D3D15
Feature: [D3D15]-[STORY] For Requests the source of elements allowed to be added to the request is expanded to include the Models. The available list of elements to Request have changed to include Models.

        Validate that the render of Parts and Models follows the ordering requirements.
        Models go first then followed by Parts ordered by Label/Material/Color
        Models show a different set of fields than Parts and are expandable.
        When a Model is expanded it shows the list of Parts that are required for that Model instance.
    The elements that can be dragged to the drop destination are the Models and the real Parts. The parts that belong to the Model can also be dragged as a means of pointer references the same as the real Parts.¡

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated

    @D3D15.01
    Scenario: [D3D15.01]-When the New Request page open then we can see a panel with Models and Parts.
        Given the target is the panel of type "available-request-elements"
        Then the target panel has a title "/STOCKS DISPONIBLES"
        Then the target panel has 2 "model"
        Then the target panel has 16 "part"

    @D3D15.02
    Scenario: [D3D15.02]-Validate the fields shown by the Model and the data rendered by the Part.
        Given the target is the panel of type "available-request-elements"

        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Verde"
        Then field named "partCount" with label "NUMERO PIEZAS" has contents "6"
        Then field named "price" with label "PRECIO" has contents "15 €"

        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then field named "label" with label "ETIQUETA" has contents "Boquilla Ganesha - Figura"
        And field named "material" with label "MATERIAL" has contents "PLA/ROSA"
        And field named "partCount" with label "DISPONIBLE" has contents "0"

    @D3D15.03
    Scenario: [D3D15.03]-When the cursor clicks inside any of the models it expands to show the contents.
        Given the target is the panel of type "available-request-elements"

        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        When the target is clicked
        # - Check the model contents shown when expanded
        Then the target has a panel labeled "COMPOSICION" named "part-composition"
        Then the target has 3 "part-stack"
        # - Check one of the parts
        Given the target the "part-stack" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
        Then column named "quantity" has contents "x 1"
        And column named "label" has contents "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
        And column named "material" has contents "PLA/BLANCO"

    @D3D15.04
    Scenario: [D3D15.04]-The Model and the Part should be draggable items with constraint REQUEST-CONTENT.
        Given the target is the panel of type "available-request-elements"

        Given the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the drag source the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
