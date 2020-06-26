@D3D15
Feature: [D3D15]-[STORY] For Requests the source of elements allowed to be added to the request is expanded to include the Models. The available list of elements to Request have changed to include Models.

        Validate that the render of Parts and Models follows the ordering requirements.
        Models go first then followed by Parts ordered by Label/Material/Color
        Models show a different set of fields than Parts and are expandable.
        When a Model is expanded it shows the list of Parts that are required for that Model instance.
    The elements that can be dragged to the drop destination are the Models and the real Parts. The parts that belong to the Model can also be dragged as a means of pointer references the same as the real Parts.¡

    Background: Application landing page
        Given the application Printer3DManager

    # @D3D15.01
    # Scenario: [D3D15.01]-When the New Request page open then we can see a panel with Models and Parts.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "available-request-elements"
    #     Then the target panel has a title "/STOCKS DISPONIBLES"
    #     Then the target panel has 2 "model"
    #     Then the target panel has 16 "part"

    # @D3D15.02
    # Scenario: [D3D15.02]-Validate the fields shown by the Model and the data rendered by the Part.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "available-request-elements"

    #     Given the target item the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
    #     Then the target item has a field named "label" with label "ETIQUETA" and value "PLATAFORMA SLOT 1/32 - Verde"
    #     Then the target item has a field named "partCount" with label "NUMERO PIEZAS" and value "5"
    #     Then the target item has a field named "price" with label "PRECIO" and value "15 €"

    #     Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
    #     Then the target item has a field named "ETIQUETA" with label "ETIQUETA" and value "Boquilla Ganesha - Figura"
    #     And the target item has a field named "MATERIAL" with label "MATERIAL" and value "PLA"
    #     And the target item has a field named "COLOR" with label "COLOR" and value "ROSA"
    #     And the target item has a field named "DISPONIBLE" with label "DISPONIBLE" and value "0"

    # @D3D15.03
    # Scenario: [D3D15.03]-When the cursor enter any of the models it expands to show the contents.
    #     Given there is a click on Feature "/NUEVO PEDIDO"
    #     Then the page "NewRequestPage" is activated
    #     Given the target panel is the panel of type "available-request-elements"

    #     Given the target item the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
    #     When the mouse enter the target item
    #     Then the target item has a list named "part-composition" with 3 "part-stack"
    #     Given the target item the "part-stack" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
    #     Then the target item has a column named "REQUERIDAS" with value "x 1"
    #     And the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Guarda Tornillos"
    #     And the target item has a column named "MATERIAL" with value "PLA"
    #     And the target item has a column named "COLOR" with value "BLANCO"

    @D3D15.04
    Scenario: [D3D15.04]-The Model and the Part should be draggable items with constraint REQUEST-CONTENT.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"

        Given the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the drag source the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
