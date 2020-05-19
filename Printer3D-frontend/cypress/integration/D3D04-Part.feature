
@D3D04
Feature: [D3D04]-Define the requirements for the Part inrteractions liek creation and update.

    If the user interacts with Inventory features that affect the Parts there should be requirements to control the
    presentation, contents and interaction flow. Parts cannot be eliminated but some of their fields are editable
    once the part is constructed.

    Background: Dock Default Configuration setup
    #     Given the Default Dock Configuration
        Given the application Printer3DManager

    # - H A P P Y   P A T H
    @D3D04 @D3D04.01
    Scenario: [D3D04.01]-If the Feature New Part received a click then we should show the New Part Dialog.
        Given one instance of Dock
        When there is a click on Feature "/New Part"
        Then the dialog New Part opens and blocks the display

#     @D3D04 @D3D04.02
#     Scenario: [D3D04.02]-A new part dialog should have the unique identifier not editable. The fields should be empty.
#     @D3D04 @D3D04.03
#     Scenario: [D3D04.03]-A new part dialog should have the fields empty.
#     @D3D04 @D3D04.04
#     Scenario: [D3D04.04]-A new part dialog should have two buttons. One to save the new part and another to cancel the operation.
#     @D3D04 @D3D04.05
#     Scenario: [D3D04.05]-If the New Part Save button is clicked then the part is stored at the repository and a notification is thrown.
#     @D3D04 @D3D04.06
#     Scenario: [D3D04.06]-If the New Part Cancel button is clicked then there is a confirmation dialog.
#     @D3D04 @D3D04.07
#     Scenario: [D3D04.07]-If Confirm button is clicked on the New Part Cancel button then the dialog is closed and the part
#     is not stored but the fields values are saved for later reuse.
#     @D3D04 @D3D04.08
#     Scenario: [D3D04.08]-If Cancel button is clicked on the New Part Cancel button then the dialog to cancel is closed and
#     the interaction returns to the part edition.

#     # - E X C E P T I O N S
#     @D3D04 @D3D04.E01
#     Scenario: [D3D04.E01]-If there is an exception saving the Part data at the repository then there is a notification and
# the part dialog is not exited.
