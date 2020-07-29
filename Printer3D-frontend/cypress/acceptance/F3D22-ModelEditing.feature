@F3D22
Feature: [F3D22]-[STORY] From the Inventory the Models can be edited.

    The Model entry on the Inventory list has a button to start an editing session on the Model.
    The editing session can be terminated by clicking again on the edit button or just abandoning the edition.
    If the editing is abandoned then the changes should be reverted to the original Model property values.
    If the Save button is clicked then the new Model contents and properties are persisted at the backend and the display reflects that changes.
    The Model can be deactivated/reactivated on the Model editing page and this should change the model use behavior.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/INVENTARIO"
        Then the page "Inventory Page" is activated
        Given the target is the panel of type "catalog"

    # - H A P P Y   P A T H
    @F3D22.01
    Scenario: [F3D22.01]-There is an edit button on the Model render. If clicked then there is a new panel with the Model fields.
        # - Select a Model for editing
        Given activate model "0f789845-cdc6-48ce-a0ce-cbaf63cffab5" for editing
        # - Validate the contents on the Model details
        Then form field named "label" with label "ETIQUETA" has contents "PLATAFORMA SLOT 1/32 - Verde"
        Then form field named "price" with label "PRECIO" has contents "15"
        Then form field named "stock" with label "NIVEL STOCK DESEADO" has contents "3"
        Then form field named "active" with label "ACTIVO" has contents "on"
        Given the target is the panel of type "drop-part-location"
        Then the target has 3 "part-stack"
        # - Validate the Model part selected
        Given the target the "part-stack" with id "8128a07b-b270-4097-99f6-7a6960122f6c"
        Then column named "quantity" has contents "1"
        Then column named "label" has contents "PLATAFORMA SLOT 1/32 - Base"
        Then column named "material" has contents "PLA/VERDE TRANSPARENTE"
        Then target has an actionable image named "remove-button"

    @F3D22.02
    Scenario: [F3D22.02]-If after editing a Model the uses closes the editing session instead of saving changes, when the model is edited again it should have the original values.
        # - Select a Model for editing
        Given activate model "0f789845-cdc6-48ce-a0ce-cbaf63cffab5" for editing
        # - Edit the model and then cancel the editing.
        And "MODELO DE PRUEBA" is set on form field "label"
        Given the target is the panel of type "catalog"
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        When target actionable image "edit-button" is clicked
        Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
        When target actionable image "edit-button" is clicked
        Then column named "label" has contents "PLATAFORMA SLOT 1/32 - Base"

    @F3D22.03
    Scenario: [F3D22.03]-Validate the input fields limits and constraints
        # - Select a Model for editing
        Given activate model "0f789845-cdc6-48ce-a0ce-cbaf63cffab5" for editing
        # - Validate editable field constraints
        Then field named "label" is tested for size constraints 3 and 50
        And field named "price" is tested for numeric constraints 0.01
        And field named "stock" is tested for value constraints 1 to 100

    @F3D22.04
    Scenario: [F3D22.04]-Validate the title for the Model editing panel when active.
        # - Select a Model for editing
        Given activate model "0f789845-cdc6-48ce-a0ce-cbaf63cffab5" for editing
        Then the target has the title "/CAMBIO MODELO/EDICION"
