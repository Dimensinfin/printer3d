@D3D16
Feature: [D3D16]-[STORY] Models should be editable. Some of the fields and the list of composing Parts should be able to be changed.

    The Model at the Catalog page has an edit button. If activated then there is a new panel at the left with the Model fields which some of them are editable.
    The list of Parts at the right can be moved to the left Model part container.
    Also the elements at the Model can be removed by clicking on the remove button.
    There is a save button that is always enabled. If clicked the modifieed data is saved to the backend persistence repository.

    Background: Application landing page
        Given the application Printer3DManager

    @D3D16.01
    Scenario: [D3D16.01]-There is an edit button on the Model render. If clicked then there is a new panel with the Model fields.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "InventoryPage" is activated
        And the page "InventoryPage" has 2 panels
        Given the target panel is the panel of type "model-detail"
        Given the target panel is the panel of type "catalog"

        Given the target item the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        Then the target item has a named "edit-button" button

        Given the target item named button "edit-button" is clicked
        Given the target panel is the panel of type "model-detail"
        Then the target panel has an input field named "label" with label "ETIQUETA" and contents "PLATAFORMA SLOT 1/32 - Verde"
        Then the target panel has an input field named "price" with label "PRECIO" and contents "15"
        Then the target panel has an input field named "stock" with label "NIVEL STOCK DESEADO" and contents "3"

        Given the target panel is the panel of type "drop-part-location"
        Then the target panel has 3 "part-stack"

        Given the target item the "part-stack" with id "8128a07b-b270-4097-99f6-7a6960122f6c"
        Then the target item has a column named "REQUERIDAS" with value "1"
        Then the target item has a column named "ETIQUETA" with value "PLATAFORMA SLOT 1/32 - Base"
        Then the target item has a column named "MATERIAL" with value "PLA"
        Then the target item has a column named "COLOR" with value "VERDE TRANSPARENTE"
        Then the target item has a actionable image named "remove-button"
