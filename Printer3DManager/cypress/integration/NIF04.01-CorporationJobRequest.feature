@NIF04 @CorporationJobRequest
Feature: [NIF04]-Cnofigure the Corproation Ship Manufacture Request by maniplating the elements to be bought or manufacture.

    This page shows a title for the function covered that is the Fittings display. There is an active tab that allows to select between the pilot or the corporation fittings to be displayed. Fittings are then classified by the type of ship that they apply to, then by the ship class and finally the fitting node.

    @NIF04 @NIF04.01
    Scenario: [NIF04.01]-Verify that the required panels are visible and that the page is ready.
        When the CorporationShipRequestPage is activated with the request id "FITTING-BY-ID" and the fitting id "49342522" and quantity "5"
        Given one instance of SelectedFittingData
        Then there is a "v1-selected-fitting-data" with the next fields
            | neocom-name  | production-quantity |
            | ODST Odin B2 | 5                   |
        Given one instance of ShipYardLocation
        Given one instance of V1FittingBuyList
        Given one instance of V1ManufactureSchedule
        Given one instance of V1Logistics
# Then there is a viewer-panel with "4" instances of "v1-category"
        # Then there is a viewer-panel with "12" instances of "v1-fitting-item-render"
        Given one instance of V1JobProductionAccounting
        Then there is a panel title with the next text "JOBS & COSTS"
        Then there is a section title with the next text "INDUSTRY JOBS"
        Then there is a section title with the next text "ACCOUNTING"
# Then there is a viewer-panel with "4" instances of "v1-category"
