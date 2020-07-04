@D3D17
Feature: [D3D17]-[STORY] Now that there are Parts and Models and they could be used inside a Request the Api model for the Request changes to a more generic type of container.

    Add support for Models on the New Request feature. Now we can drop Parts and Models.
    Render the Request contents differently is they are a part or a model.
    Update the Request price amount calculations.

    Background: Application landing page
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D17.01
    Scenario: [D3D17.01]-The Parts at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        When the drag source is dragged to the drop destination "dropContents"

        Then the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents" and with "1" elements
        Given the target item the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then the target item has a column named "CANTIDAD" with value "1"
        Then the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Base"
        Then the target item has a column named "TERMINACION" with value "PLA/BLANCO"
        Then the target item has a named "remove-button" button

    @D3D17.02
    Scenario: [D3D17.02]-The Models at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        When the drag source is dragged to the drop destination "dropContents"

        Then the target panel has a panel labeled "CONTENIDO PEDIDO" named "requestContents" and with "1" elements
        Given the target item the "request-content" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a column named "CANTIDAD" with value "1"
        Then the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Verde"
        Then the target item has a column named "TERMINACION" with value "-/-"
        Then the target item has a named "remove-button" button

    @D3D17.03
    Scenario: [D3D17.03]-When composing the Request we can use Models and Parts. If we add a Model the data shown on the request should be this.
        # - Add a model to a new Request
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        Given the target panel is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the target panel is the panel of type "new-request"
        And the target panel has a drop place named "dropContents"
        When the drag source is dragged to the drop destination "dropContents"

        # - Validate the data to be rendered for a Model on a Request.
        Given the target item the "request-content" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a column named "CANTIDAD" with value "1"
        Then the target item has a column named "PRECIO" with value "15 €"
        Then the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Verde"
        Then the target item has a column named "TERMINACION" with value "-/-"
        Then the target item has a named "remove-button" button
