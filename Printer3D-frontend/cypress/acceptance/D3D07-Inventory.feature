@D3D07
Feature: [D3D07]-Validate the Inventory version 2 features and page contents.

    The inventory is an information section that groups elements that are in stock. Availability means that they can be used
    to build things. Stock control may be used to decide if more things should be obtained.
    Inventory actions should allow to create, edit and see the differents elements that can be stocked.
    The Part list should be changed to a master-detail concept. When  the list is displayed only is displayed the main part
    information. If the Part is clicked then the Part expands and the detailed stock data is shown.

    Background: Application landing page
        Given the application Printer3DManager

    # @D3D07 @D3D07.01
    # Scenario: [D3D07.01]-Validate the structure of the Inventory Part List Page version 2.
    #     Given there is a click on Feature "/INVENTARIO"
    #     Then the V2InventoryPartListPage is activated
    #     And one instance of ViewerPanel
    #     And one or more instances of NodeContainer

    # @D3D07 @D3D07.02
    # Scenario: [D3D07.02]-Validate the contents of a Part render.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then the first NodeContainer contains a Part Container Render
    #     And on the v1-part-container component there is a field named "ETIQUETA" with class "partcontainer-label"
    #     And on the v1-part-container component there is a field named "DESCRIPCION" with class "partcontainer-description"
    #     And on the v1-part-container component there is a field named "TIEMPO" with class "partcontainer-buildTime"

    # @D3D07 @D3D07.04
    # Scenario: [D3D07.04]-The Part render is an expandable element so the node container shows a right arrow.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then on the v1-part-container component there is a right arrow

    # @D3D07 @D3D07.05
    # Scenario: [D3D07.05]-If the arrow is clicked then the Part Container expands and shows the parts that match this same label.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then on the v1-part-container component there is a right arrow
    #     When the right arrow is clicked
    #     Then the container expands and there are one or more v1-part nodes
    #     And on the v1-part component there is a field named "MATERIAL" with class "part-material"
    #     And on the v1-part component there is a field named "COLOR" with class "part-color"
    #     And on the v1-part component there is a field named "STOCK" with class "part-stock"
    #     And on the v1-part component there is a field named "DISPONIBLE" with class "part-stockAvailable"
    #     And on the v1-part component there is a field named "COSTE" with class "part-cost"
    #     And on the v1-part component there is a field named "PRECIO" with class "part-price"
    #     And on the v1-part component there is a field named "ACTIVA" with class "part-active"

    # @D3D07 @D3D07.06
    # Scenario: [D3D07.06]-Active Parts show a green corner while inactive show it orange.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Given the target panel is the panel of type "viewer-panel"
    #     Then on the v1-part-container component there is a right arrow
    #     And when the target Part Container is the second
    #     When the right arrow of the target Part Container is clicked
    #     Then the target panel has one or more "part"
    #     And active Part shows a green corner
    #     And inactive Part shows an orange corner

    # @D3D07 @D3D07.07
    # Scenario: [D3D07.07]-Active and inactive parts have a editor activation button at the left.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then on the v1-part-container component there is a right arrow
    #     When the right arrow is clicked
    #     Then the container expands and there are one or more v1-part nodes
    #     When the first v1-part is selected as the target
    #     Then the target Part shows an Edit button at the right

    # @D3D07 @D3D07.08
    # Scenario: [D3D07.08]-When the Edit Attributes button is clicked the Part display changes and some fields are now editable.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then on the v1-part-container component there is a right arrow
    #     When the right arrow is clicked
    #     Then the container expands and there are one or more v1-part nodes
    #     When the first v1-part is selected as the target
    #     Then the target Part shows an Edit button at the right
    #     When the target Part Edit button is clicked
    #     Then the field "STOCK" is editable
    #     And the field "DISPONIBLE" is editable
    #     And the field "COSTE" is editable
    #     And the field "PRECIO" is editable
    #     And the field "ACTIVA" is editable

    # @D3D07 @D3D07.09
    # Scenario: [D3D07.09]-When the part is put in editable mode the editable field contents are the same of the original part.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     Then on the v1-part-container component there is a right arrow
    #     When the right arrow is clicked
    #     Then the container expands and there are one or more v1-part nodes
    #     When the first v1-part is selected as the target
    #     Then the field "stock" stores the current value into "STOCK-STORE"
    #     Then the field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
    #     Then the field "cost" stores the current value into "COSTE-STORE"
    #     Then the field "price" stores the current value into "PRECIO-STORE"
    #     Then the field "active" stores the current value into "ACTIVA-STORE"
    #     When the first v1-part is selected as the target
    #     Then the target Part shows an Edit button at the right
    #     When the target Part Edit button is clicked
    #     Then the field "stock" is editable and the content equals the stored value "STOCK-STORE"
    #     And the field "stockAvailable" is editable and the content equals the stored value "DISPONIBLE-STORE"
    #     And the field "cost" is editable and the content equals the stored value "COSTE-STORE"
    #     And the field "price" is editable and the content equals the stored value "PRECIO-STORE"
    # # And the field "active" is editable and the content equals the stored value "ACTIVA-STORE"

    # @D3D07 @D3D07.10
    # Scenario: [D3D07.10]-When the part is put in editable mode there is a check mark button to save the Part changes to the repository.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     When the right arrow is clicked
    #     When the first v1-part is selected as the target
    #     Then the target Part shows an Edit button at the right
    #     When the target Part Edit button is clicked
    #     Then the target Part shows a Save button at the right

    # @D3D07 @D3D07.11
    # Scenario: [D3D07.11]-If this check mark button is clicked then the new values are persisted at the repository, a notification is shown and the edit state is exited.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     When the right arrow is clicked
    #     When the first v1-part is selected as the target
    #     When the target Part Edit button is clicked
    #     When the target Part Save button is clicked
    #     Then there is a Notification panel
    #     Then the new part contents are persisted to the backend
    #     And the edit state is exited

    # @D3D07 @D3D07.12
    # Scenario: [D3D07.12]-The the Part values edited and persisted match the current Part values.
    #     Given there is a click on Feature "/INVENTARIO"
    #     When the V2InventoryPartListPage is activated
    #     When the right arrow is clicked
    #     When the first v1-part is selected as the target
    #     When the target Part Edit button is clicked
    #     Then the target Part field "stock" is changed to "8"
    #     Then the target Part field "stockAvailable" is changed to "8"
    #     Then the target Part field "cost" is changed to "8"
    #     Then the target Part field "stock" stores the current value into "STOCK-STORE"
    #     Then the target Part field "stockAvailable" stores the current value into "DISPONIBLE-STORE"
    #     Then the target Part field "cost" stores the current value into "COSTE-STORE"
    #     Then the target Part field "price" stores the current value into "PRECIO-STORE"
    #     Then the target Part field "active" stores the current value into "ACTIVA-STORE"
    #     When the target Part Save button is clicked
    #     Then the target Part field "stock" equals the stored value "STOCK-STORE"
    #     Then the target Part field "stockAvailable" equals the stored value "DISPONIBLE-STORE"
    #     Then the target Part field "cost" equals the stored value "COSTE-STORE"
    #     Then the target Part field "price" equals the stored value "PRECIO-STORE"

    @D3D07 @D3D07.13
    Scenario: [D3D07.13]-When the duplicate button of any Part is clicked then there is a new Duplicate Part dialog.
        Given there is a click on Feature "/INVENTARIO"
        Then the page "PartInventory" is activated
        Given the target panel is the panel named "part-list"
        Given the target item the "part-container" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
        Then on the target panel there are none "part"
        Then the target item is expandable
        When the target item expand-collapse button is clicked
        Then on the target panel there are "2" "part"
        Given the target item the "part" with id "0078cd61-63bb-4a35-9d66-c4c630b017c3"
        When the target Part Duplicate button is clicked
        Then the "New Part" dialog opens and blocks the display
        Given the target panel is the panel named "new-part-dialog"
        Then the target panel has a input field named "label" with label "ETIQUETA" and value "Boquilla Ganesha - Embocadura"
        Then the target panel has a input field named "description" with label "Descripción" and value "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables."
        Then the target panel has a select field named "material" with label "MATERIAL" and value "TPU"
        # Then the target panel has a select field named "color" with label "COLOR" and value "BLANCO"
        Then the target panel has a input field named "cost" with label "COSTE FAB." and value "0.45"
        Then the target panel has a input field named "price" with label "PRECIO" and value "1"
        Then the target panel has a input field named "buildTime" with label "TIEMPO" and value "20"
        Then the target panel has a input field named "stockLevel" with label "STOCK DESEADO" and value "15"
        Then the target panel has a input field named "stockAvailable" with label "STOCK ACTUAL" and value "10"

# Then the target panel has a input field labeled "material" with value "TPU"
# Then the target panel has a input field labeled "color" with value "BLANCO"
# Then the target panel has a input field labeled "cost" with value "0.45"
# Then the target panel has a input field labeled "price" with value "1"

# <div _ngcontent-bbd-c89="" id="0078cd61-63bb-4a35-9d66-c4c630b017c3"><div _ngcontent-bbd-c89="" class="row part-panel"><div _ngcontent-bbd-c89="" class="edit-attributes"><img _ngcontent-bbd-c89="" src="/assets/media/edit_attributes-white-36dp.svg"></div><div _ngcontent-bbd-c89="" class="save-modifications"><span _ngcontent-bbd-c89="">&nbsp;</span></div><div _ngcontent-bbd-c89="" class="field material-width"><span _ngcontent-bbd-c89="" class="label">MATERIAL</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="material" class="field-height part-material">TPU</span></div><div _ngcontent-bbd-c89="" class="field color-width"><span _ngcontent-bbd-c89="" class="label">COLOR</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="color" class="field-height part-color">AZUL</span></div><div _ngcontent-bbd-c89="" class="field stock-width"><span _ngcontent-bbd-c89="" class="label">STOCK</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="stock" class="field-height part-stock">15</span></div><div _ngcontent-bbd-c89="" class="field stock-width"><span _ngcontent-bbd-c89="" class="label">DISPONIBLE</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="stockAvailable" class="field-height part-stockAvailable">10</span></div><div _ngcontent-bbd-c89="" class="field stock-width"><span _ngcontent-bbd-c89="" class="label">COSTE</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="cost" class="field-height part-cost">0.45 €</span></div><div _ngcontent-bbd-c89="" class="field stock-width"><span _ngcontent-bbd-c89="" class="label">PRECIO</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="price" class="field-height part-price">1 €</span></div><div _ngcontent-bbd-c89="" class="field active-width"><span _ngcontent-bbd-c89="" class="label">ACTIVA</span><br _ngcontent-bbd-c89=""><span _ngcontent-bbd-c89="" name="active" class="field-height part-active">FUERA PROD.</span></div><div _ngcontent-bbd-c89="" cy-name="duplicate-button" class="field duplicate-width"><svg _ngcontent-bbd-c89="" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" width="36px" height="36px" class="duplicate-icon"><path _ngcontent-bbd-c89="" d="M0 0h24v24H0z" fill="none"></path><path _ngcontent-bbd-c89="" d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"></path></svg></div></div><div _ngcontent-bbd-c89=""><svg _ngcontent-bbd-c89="" viewBox="0 0 100 100" class="corner-border"><path _ngcontent-bbd-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="white"></path></svg><svg _ngcontent-bbd-c89="" viewBox="0 0 100 100" class="corner-clear"><path _ngcontent-bbd-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="#180A16"></path></svg><!--bindings={
#   "ng-reflect-ng-if": "false"
# }--><svg _ngcontent-bbd-c89="" viewBox="0 0 100 100" class="corner-mark"><path _ngcontent-bbd-c89="" d="M100,100 L0,100 L100,0 L100,100" fill="orangered"></path></svg><!--bindings={
#   "ng-reflect-ng-if": "true"
# }--></div></div>
