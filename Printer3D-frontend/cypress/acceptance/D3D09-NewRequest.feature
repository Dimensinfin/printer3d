@D3D09
Feature: [D3D09]-[STORY] There is a Feature to create user Requests. It can accept Models and Parts and does some price calculations.

    The new implementation allows to drop Parts and Models into a new Request.
    There are minor differences on the reports contents between models and parts.
    During the Request composition the calculation fields are updated automatically.
    Models and parts are added to the Request by dropping them into a landing area. If we need more items of a same model we drop it several times.
    During the Request construction the user can drop part to be added to the request or click on the remove button to subtract parts from the Request.
    If all the fields are filled and valid then the Request shows a button to persist it.
    There is always a Calcel button to close the New Request page.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/NUEVO PEDIDO"
        Then the page "NewRequestPage" is activated
        When the application completes loading

    # - H A P P Y   P A T H
    @D3D09.02
    Scenario: [D3D09.02]-The right panel is the Request definition panel. It should have the New Request fields and a place to drop Parts.
        Given the target is the panel of type "new-request"
        Then  the target has the title "/NUEVO PEDIDO/DEFINICION"
        # - Check the fields contents
        And field named "requestDate" with label "FECHA PEDIDO" is not empty
        And field named "quantity" with label "NRO. PIEZAS" has contents "0"
        And field named "price" with label "PRECIO" has contents "0 €"
        And form field named "label" with label "ETIQUETA" is empty
        And form field named "label" is "invalid"
        And the target has a drop place named "dropContents"

    @D3D09.03
    Scenario: [D3D09.03]-The Parts at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Given the target is the panel of type "new-request"
        And the target has a drop place named "dropContents"
        # - Drag a part to the drop contents
        When the drag source is dragged to the drop destination "dropContents"

    @D3D09.04
    Scenario: [D3D09.04]-The Models at the left panel can be dragged and deployed on the box at the right Panel to be added to the Request.
        Given the target is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Given the target is the panel of type "new-request"
        And the target has a drop place named "dropContents"
        # - Drag a model to the drop contents
        When the drag source is dragged to the drop destination "dropContents"

    @D3D09.05
    Scenario: [D3D09.05]-When a Part or a Model are dropped on the Request the data is displayed on the request content area.
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Select a Model for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        # - Drag a model to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"

        # - Check the contents dropped on the request
        Then field named "requestDate" with label "FECHA PEDIDO" is not empty
        And field named "quantity" with label "NRO. PIEZAS" has contents "2"
        And field named "price" with label "PRECIO" has contents "20 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 2 "request-content"
        # - Validate the contents columns
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "CANTIDAD" has contents "1"
        And column named "PRECIO" has contents "5 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "TERMINACION" has contents "PLA/PLATEADO"
        And target has an actionable image named "remove-button"
        # - Validate the contents columns
        Given the target the "request-content" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then column named "CANTIDAD" has contents "1"
        And column named "PRECIO" has contents "15 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Verde"
        And column named "TERMINACION" has contents "-/-"
        And target has an actionable image named "remove-button"

    @D3D09.06
    Scenario: [D3D09.06]-If we drag the same part multiple times the number of contents does not change but other fields reflect the new count.
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Then field named "quantity" with label "NRO. PIEZAS" has contents "1"
        And field named "price" with label "PRECIO" has contents "5 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 1 "request-content"
        # - Validate the contents columns
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "CANTIDAD" has contents "1"
        And column named "PRECIO" has contents "5 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "TERMINACION" has contents "PLA/PLATEADO"
        And target has an actionable image named "remove-button"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Then field named "quantity" with label "NRO. PIEZAS" has contents "2"
        And field named "price" with label "PRECIO" has contents "10 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 1 "request-content"
        # - Validate the contents columns
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "CANTIDAD" has contents "2"
        And column named "PRECIO" has contents "10 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "TERMINACION" has contents "PLA/PLATEADO"
        And target has an actionable image named "remove-button"
    # - END OF REVIEW

    @D3D09.07
    Scenario: [D3D09.07]-The New request shows two buttons.
        Given the target is the panel of type "new-request"
        # - Check the state of the buttons
        Then the button with name "save" has a label "Guardar" and is "disabled"
        And the button with name "cancel" has a label "Cancelar" and is "enabled"

    @D3D09.08
    Scenario: [D3D09.08]-If the label field is filled and there it at least one content then the Save button activates.
        Given the target is the panel of type "new-request"
        # - Validate the label field
        And the target panel input field named "label" is "invalid"
        When field named "label" is tested for size constraints 3 and 50
        And "Pedido de Prueba P01" is set on form field "label"
        And the target panel input field named "label" is "valid"
        # - Check the state of the buttons
        Then the target panel button with name "save" has a label "Guardar" and is "disabled"
        And the target panel button with name "cancel" has a label "Cancelar" and is "enabled"
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the state of the buttons
        Then the button with name "save" has a label "Guardar" and is "enabled"
        And the button with name "cancel" has a label "Cancelar" and is "enabled"

    @D3D09.09
    Scenario: [D3D09.09]-If the Save button is active and it is clicked then the Request is persisted on the backend, a notification shown and the page clears.
        Given the target is the panel of type "new-request"
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        And "Pedido de Prueba P01" is set on form field "label"
        # - Check the state of the buttons
        Then the button with name "save" has a label "Guardar" and is "enabled"
        And the button with name "cancel" has a label "Cancelar" and is "enabled"
        When the button with name "save" is clicked
        Then the Request is persisted at the backend
        And there is a Notification panel
        And the active page is set to Dasboard
        And there are no Features active

    @D3D09.10
    Scenario: [D3D09.10]-If the Cancel button is clicked then the page clears.
        Given the target is the panel of type "new-request"
        # - Check the state of the buttons
        Then the button with name "save" has a label "Guardar" and is "enabled"
        And the button with name "cancel" has a label "Cancelar" and is "enabled"
        When the button with name "cancel" is clicked
        And the active page is set to Dasboard
        And there are no Features active

    @D3D09.11
    Scenario: [D3D09.11]-If all the parts or models added to a Request are removed then the Save button deactivates.
        Given the target is the panel of type "new-request"
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        And "Pedido de Prueba P01" is set on form field "label"
        # - Check the state of the buttons
        Then the button with name "save" has a label "Guardar" and is "enabled"
        And the button with name "cancel" has a label "Cancelar" and is "enabled"
        # - Remove the request contents
        Given the target the "request-item" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        And target has an actionable image named "remove-button"
        When target actionable image "remove-button" is clicked
        # - Check that the button is now disabled
        Given the target is the panel of type "new-request"
        Then the button with name "save" has a label "Guardar" and is "disabled"
        And the button with name "cancel" has a label "Cancelar" and is "enabled"

    @D3D09.12
    Scenario: [D3D09.12]-If the user clicks on the Remove button of a Part assigned to a Request then the number of that type of Parts reduces in 1. The the count reaches 0 then the part is removed from the Report.
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Then field named "quantity" with label "NRO. PIEZAS" has contents "1"
        And field named "price" with label "PRECIO" has contents "5 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 1 "request-content"
        # - Validate the contents columns
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "CANTIDAD" has contents "1"
        And column named "PRECIO" has contents "5 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "TERMINACION" has contents "PLA/PLATEADO"
        And target has an actionable image named "remove-button"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Then field named "quantity" with label "NRO. PIEZAS" has contents "2"
        And field named "price" with label "PRECIO" has contents "10 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 1 "request-content"
        # - Validate the contents columns
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "CANTIDAD" has contents "2"
        And column named "PRECIO" has contents "10 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "TERMINACION" has contents "PLA/PLATEADO"
        And target has an actionable image named "remove-button"
        # - Remove one part and check the new values
        Given the target the "request-item" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        And target has an actionable image named "remove-button"
        When target actionable image "remove-button" is clicked
        # - Validate the contents columns
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        Then column named "CANTIDAD" has contents "1"
        And column named "PRECIO" has contents "5 €"
        And column named "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Base"
        And column named "TERMINACION" has contents "PLA/PLATEADO"
        And target has an actionable image named "remove-button"
        # - Remove one part and check the new values
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        And target has an actionable image named "remove-button"
        When target actionable image "remove-button" is clicked
        # - Validate the contents columns
        Given the target is the panel of type "new-request"
        Then the target has no "request-content"

    @D3D09.13
    Scenario: [D3D09.13]-Whe parts or models are added to the Request some accounting fields are updated. The same when items are removed from the Request.
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Given the target is the panel of type "new-request"
        Then field named "quantity" with label "NRO. PIEZAS" has contents "1"
        And field named "price" with label "PRECIO" has contents "5 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 1 "request-content"
        # - Select a Part for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "part" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        # - Drag a part to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Given the target is the panel of type "new-request"
        Then field named "quantity" with label "NRO. PIEZAS" has contents "2"
        And field named "price" with label "PRECIO" has contents "10 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 1 "request-content"
        # - Select a Model for drag
        Given the target is the panel of type "available-request-elements"
        And the drag source the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        # - Drag the model to the drop contents
        Given the target is the panel of type "new-request"
        When the drag source is dragged to the drop destination "dropContents"
        # - Check the contents dropped on the request
        Given the target is the panel of type "new-request"
        Then field named "quantity" with label "NRO. PIEZAS" has contents "3"
        And field named "price" with label "PRECIO" has contents "25 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 2 "request-content"
        # - Remove one part and check the new values
        Given the target the "request-content" with id "a12ec0be-52a4-424f-81e1-70446bc38372"
        And target has an actionable image named "remove-button"
        When target actionable image "remove-button" is clicked
        # - Check the contents dropped on the request
        Given the target is the panel of type "new-request"
        Then field named "quantity" with label "NRO. PIEZAS" has contents "2"
        And field named "price" with label "PRECIO" has contents "20 €"
        And the target has a panel labeled "CONTENIDO PEDIDO" named "requestContents"
        And the target has 2 "request-content"
