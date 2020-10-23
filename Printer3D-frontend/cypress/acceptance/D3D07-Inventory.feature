@D3D07
Feature: [D3D07]-Validate the Inventory version 2 features and page contents.

    The inventory is an information section that groups elements that are in stock. Availability means that they can be used
    to build things. Stock control may be used to decide if more things should be obtained.
    Inventory actions should allow to create, edit and see the differents elements that can be stocked.
    The Part list should be changed to a master-detail concept. When  the list is displayed only is displayed the main part
    information. If the Part is clicked then the Part expands and the detailed stock data is shown.
    With the addition of Models they should appear at the top of the inventory list.
    Models can show their contents when hovering over them.
    Parts are ordered by label, so they are the Models.

    Background: Application landing page
        Given the application Printer3DManager


# PENDING RECONSTRUCTION OF EDITABLES


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
