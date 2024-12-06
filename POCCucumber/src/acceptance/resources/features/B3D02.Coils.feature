@B3D02 @Coils
Feature: Manage the Coils on the Inventory information section

    @B3D02.H @B3D02.01
    Scenario: [B3D02.01] Under version 2 there is a new endpoint to get all Coils persisted at the repository as a list. The Coil data has also changed
        Given a clean Coils repository
        When the Get Coils v2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of Coils has 0 items

    @B3D02.H @B3D02.02
    Scenario: [B3D02.02] Under version 2 there is a new endpoint to get all Coils persisted at the repository as a list. The Coil data has also changed
        Given a clean Coils repository
        When the Create Coils v2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        And the list of Coils has 1 items
