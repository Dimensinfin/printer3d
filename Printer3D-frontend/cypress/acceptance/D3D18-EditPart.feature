@D3D18
Feature: [D3D18]-[STORY] Parts have fields editable both at the generic definition level and at the instance definition level

    There is a button on the Inventory table to togle the 'Edit in Place' for a Part or Part Group. A Part group is just an artificial structure to
    define a set of Parts that have some set of common field values.
    There is another button to edit the properties of a single Part instance.

    Background: Application landing page
        Given the application Printer3DManager
        Given there is a click on Feature "/INVENTARIO"
        Then the page "Inventory Page" is activated
        When the application completes loading

    # - P A R T   C O N T A I N E R   E D I T I O N
    @D3D18.01
    Scenario: [D3D18.01]-The Part Container has some fields and two buttons to activate the Edit In Place feature.
        Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        And target has an actionable image named "save-button"
        And actionable image named "save-button" is "disabled"

    @D3D18.02
    Scenario: [D3D18.02]-The Part Container group now has an edit button that toggles the editing of some properties for a set of Parts. All the parts that share the label belong to the same Part Group and are edited at the same time.
        Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then target has an actionable image named "edit-button"
        And actionable image named "edit-button" is "enabled"
        # - Click to activate edit state
        When target actionable image "edit-button" is clicked
        Given the target the "part-container" with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field named "project" is editable
        And field named "description" is editable
        And field named "buildTime" is editable
        And field named "weight" is editable
        And field named "modelPath" is editable
        # - Click to deactivate edit state
        When target actionable image "edit-button" is clicked
        Given the target the "part-container" with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field named "description" is not editable
        And field named "buildTime" is not editable
        And field named "weight" is not editable
        And field named "modelPath" is not editable

    @D3D18.03
    Scenario: [D3D18.03]-Validate the editable Part Container fields for constraints.
        Given an editable Part Container with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field named "buildTime" is tested for value constraints 1
        And field named "weight" is tested for value constraints 1 to 100
        And field named "modelPath" is tested for max size of 300
        And field named "description" is tested for max size of 500
        And field named "project" is tested for max size of 50

    @D3D18.04.01
    Scenario: [D3D18.04.01]-Validate the contents of a Part Container not in edit mode. Now there are new fields like Project that should not be visible.
        Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field named "label" with label "ETIQUETA" has contents "BASE TETSUO 1.32"
        And field named "description" with label "DESCRIPCION" has contents "Una base más grande que la S 1.32, con cubículos incorporados para guardar herramientas o materiales necesarios para el ajuste del vehículo."
        And field named "buildTime" with label "TIEMPO" has contents "330 min."
        And field named "weight" with label "PLASTICO" has contents "54 gr."
        And field named "modelPath" with label "FICHERO IMPR." has contents "TETSUO 1.32.gcode"

    @D3D18.04.02
    Scenario: [D3D18.04.02]-Validate the contents of a Part Container in edit mode. Now there are new fields like Project that should be visible.
        Given an editable Part Container with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field named "label" with label "ETIQUETA" has contents "BASE TETSUO 1.32"
        # TODO - Textareas should be managed with another step
        # And field named "description" with label "DESCRIPCION" has contents "Una base más grande que la S 1.32, con cubículos incorporados para guardar herramientas o materiales necesarios para el ajuste del vehículo."
        # And field named "buildTime" with label "TIEMPO" has contents "330 min."
        # And field named "weight" with label "PLASTICO" has contents "54 gr."
        # And field named "modelPath" with label "FICHERO IMPR." has contents "TETSUO 1.32.gcode"
        # And field named "project" with label "PROJECT" has contents "<default>"

    # - P A R T   E D I T I O N
    @D3D18.05
    Scenario: [D3D18.05.1]-The Part has a buttons to activate the Edit In Place feature.
        # - Select a part
        Given a expanded Part Group with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Check the buttons available
        And target has an actionable image named "edit-button"
        And target has an actionable image named "duplicate-button"
        And target has an actionable image named "save-disabled"
        # - Check initial state
        Then field named "stock" is not editable
        And field named "stockAvailable" is not editable
        And field named "cost" is not editable
        And field named "price" is not editable
        And field named "active" is not editable
        When target actionable image "edit-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field named "stock" is editable
        And field named "stockAvailable" is editable
        And field named "cost" is editable
        And field named "price" is editable
        And field named "active" is editable

    @D3D18.05
    Scenario: [D3D18.05.2]-With a Part in editable mode is the fields are edited and the Part is saved then the new value are on the Part fields.
        # - Select a part and store the values
        Given a expanded Part Group with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Click the editable button and put the part in edit mode
        When target actionable image "edit-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Check original values
        Then form field named "stock" with label "STOCK" has contents "5"
        And form field named "stockAvailable" with label "DISPONIBLE" has contents "1"
        And form field named "cost" with label "COSTE" has contents "1.1"
        And form field named "price" with label "PRECIO" has contents "8"
        # - Set the new values
        And 8 is set on form field "stock"
        And 4 is set on form field "stockAvailable"
        And 1 is set on form field "cost"
        And 2 is set on form field "price"
        # - Save the edited changes
        When target actionable image "save-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Check original values
        Then form field named "stock" with label "STOCK" has contents "8"
        And form field named "stockAvailable" with label "DISPONIBLE" has contents "4"
        And form field named "cost" with label "COSTE" has contents "1"
        And form field named "price" with label "PRECIO" has contents "2"

    @D3D18.06
    Scenario: [D3D18.06.1]-When the part is put in editable mode the editable field contents are the same of the original part.
        # - Select a part and store the values
        Given a expanded Part Group with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field "stock" stores the current value into "STOCK-STORE"
        Then field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
        Then field "cost" stores the current value into "COSTE-STORE"
        Then field "price" stores the current value into "PRECIO-STORE"
        Then field "active" stores the current value into "ACTIVA-STORE"
        # - Click the editable button and put the part in edit mode
        When target actionable image "edit-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then field "stock" is editable and the content equals the stored value "STOCK-STORE"
        And field "stockAvailable" is editable and the content equals the stored value "DISPONIBLE-STORE"
        And field "cost" is editable and the content equals the stored value "COSTE-STORE"
        And field "price" is editable and the content equals the stored value "PRECIO-STORE"

    @D3D18.06
    Scenario: [D3D18.06.2]-Put a Part in editable mode and check the editable fields constraints.
        # - Select a part and store the values
        Given a expanded Part Group with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Click the editable button and put the part in edit mode
        When target actionable image "edit-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Check the fields constraints
        Then field named "stock" is tested for value constraints 0 to 999
        And field named "stockAvailable" is tested for value constraints 0
        And field named "cost" is tested for numeric constraints 0.01
        And field named "price" is tested for numeric constraints 0.01

    @D3D18.06
    Scenario: [D3D18.06.3]-When a part is deactivated then the stock count should be set to 0.
        # - Select a part and store the values
        Given a expanded Part Group with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        # - Click the editable button and put the part in edit mode
        When target actionable image "edit-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then form field named "stock" with label "STOCK" has contents "15"
        When form checkbox named "active" is clicked
        # - Save the edited changes
        When target actionable image "save-button" is clicked
        # - Reselect the Part as target that is disabled after a click
        # Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        Given the target the "part" with id "4e7001ee-6bf5-40b4-9c15-61802e4c59ea"
        Then form field named "stock" with label "STOCK" has contents "0"

    @D3D18.07
    Scenario: [D3D18.07]-When the duplicate button of any Part is clicked then there is a new Duplicate Part dialog.
        # - Expand the Part Container
        Given the target is the panel of type "catalog"
        Then the target has no "part"
        Given the target the "part-container" with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then target has an actionable image named "expand-button"
        # - Click the expand button
        When target actionable image "expand-button" is clicked
        Given the target is the panel of type "catalog"
        Then the target has 1 "part"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then target has an actionable image named "duplicate-button"
        # - Click the duplicate and validate the New Part Dialog
        When target actionable image "duplicate-button" is clicked
        Then the "New Part" dialog opens and blocks the display
        # - Validate the contents for the New Part dialog
        And form field named "label" with label "ETIQUETA" has contents "Boquilla Ganesha - Embocadura"
        And form field named "description" with label "DESCRIPCION" has contents "Boquilla para fumar en narguile. Compuesta de 3 piezas desmontables."
        And form field named "material" with label "MATERIAL" has contents "TPU"
        And form field named "color" with label "COLOR" is empty
        And form field named "weight" with label "PESO" has contents "8"
        And form field named "cost" with label "COSTE FAB." has contents "0.45"
        And form field named "price" with label "PRECIO" has contents "1"
        And form field named "buildTime" with label "TIEMPO" has contents "20"
        And form field named "stock" with label "STOCK DESEADO" has contents "15"
        And form field named "stockAvailable" with label "DISPONIBLES" has contents "0"
        And form field named "imagePath" with label "IMAGEN" is empty
        And form field named "modelPath" with label "FICHERO MODELO" is empty
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "disabled"
        And the button with name "submit-button" has a label "Guardar" and is "disabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D18.08
    Scenario: [D3D18.08]-When the duplicated New part dialog is open and the color field is filled then the save buttons activate.
        Given a duplicated New Part from Part id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        And "BLANCO" is set on form field "color"
        # - Check the state of the buttons
        And the button with name "repeat-button" has a label "Guardar y Repetir" and is "enabled"
        And the button with name "submit-button" has a label "Guardar" and is "enabled"
        And the button with name "cancel-button" has a label "Cancelar" and is "enabled"

    @D3D18.09
    Scenario: [D3D18.09]-If the New Part is saved with the same finishing because it is a duplicated then the save is cancelled and an error reported.
        Given a duplicated New Part from Part id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        And "BLANCO" is set on form field "color"
        # - Change the api simulator behavior
        Given response "409-PART_REPOSITORY_CONFLICT" for "Save New Part"
        # - When the New Part is saved
        When the button with name "submit-button" is clicked
        Then there is a "error" Notification panel
        And the dialog not closes

    @D3D18.10
    Scenario: [D3D18.10]-When a Part is duplicated the number of items on available stock should be initialized to 0
        Given a duplicated New Part from Part id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Then form field named "stockAvailable" with label "DISPONIBLES" has contents "0"

    @D3D18.11
    Scenario: [D3D18.11]-Validate the input fields that should be displayed when the Edit Part is activated.
        # - Select a part
        Given a expanded Part Group with id "part-container:953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        Given the target the "part" with id "953760b5-d5b7-459d-8ce6-fffdb85a6a6c"
        When target actionable image "edit-button" is clicked
        Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        # - Validate edit part form fields
        Then form field named "stock" with label "STOCK" has contents "15"
        And form field named "stockAvailable" with label "DISPONIBLE" has contents "10"
        And form field named "cost" with label "COSTE" has contents "0.45"
        And form field named "price" with label "PRECIO" has contents "1"
        And form field named "active" with label "ESTADO" has contents "on"
        And form field named "stock" is "valid"
        And form field named "stockAvailable" is "valid"
        And form field named "cost" is "valid"
        And form field named "price" is "valid"
        # - Check that the save button is enabled
        And actionable image named "save-button" is "enabled"

    @D3D18.12
    Scenario: [D3D18.12]-If any of the editable fields is invalidated then check that the save button is disabled.
        # - Select a part
        Given a expanded Part Group with id "part-container:5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        When target actionable image "edit-button" is clicked
        Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        # - Invalidated a field
        Then form field "stock" is cleared
        And form field named "stock" is "invalid"
        And actionable image named "save-button" is "disabled"

    @D3D18.13
    Scenario: [D3D18.13]-Inactive Parts should not have the duplicate action active.
        # - Deactivate the filter
        Then form field named "inactiveFilter" with label "Quitar elementos inactivos." has contents "on"
        When form checkbox named "inactiveFilter" is clicked
        And the loading panel completes
        # - Expand the Part Container
        Given the target is the panel of type "catalog"
        Given the target the "part-container" with id "part-container:0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
        Then target has an actionable image named "expand-button"
        # - Click the expand button
        When target actionable image "expand-button" is clicked
        Given the target the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
        Then target has an actionable image named "duplicate-button"
        Given the target the "part" with id "3097cf15-494e-43b7-b637-b91eb4f42630"
        Then target actionable image named "duplicate-button" is missing
