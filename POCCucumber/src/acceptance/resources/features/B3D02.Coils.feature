@B3D02 @Coils
Feature: Manage the Coils on the Inventory information section

    @B3D02.H @B3D02.04
    Scenario: [B3D02.04] Under version 2 there is a new endpoint to get all Coils persisted at the repository as a list. The Coil data has also changed
        Given a clean Coils repository
        When the Get Coils v2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of Coils has 1 items
