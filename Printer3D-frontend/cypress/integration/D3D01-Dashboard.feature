@D3D01
Feature: [D3D01]-When the application starts show the dashboard page with the preconfigured feature dock and the list of pieces available at the repository.

    At the application starts the inital page should be the Dashboard. On the dashboard there are different zones that should show the available features to be used on the User Interface. Check that the title, dock and their contents match the requirements and that the display zone shows the expected list of pieces available on the service.

    @D3D01 @D3D01.01
    Scenario: [D3D01.01]-Check that the landing page after application start is the Dashboard.
        Given the application Printer3DManager
        Then the application starts the default route is the "Dashboard"

    @D3D01 @D3D01.02
    Scenario: [D3D01.02]-The landing page Dashboard shows the title panel.
        Given the DashboardPage is activated
        Given one instance of PagePath
        Then there is a "v1-page-path-panel" with the next fields
            | page-path  |
            | /Dashboard |

#     Given one instance of SelectedFittingData
#     Then there is a "v1-selected-fitting-data" with the next fields
#         | neocom-name  | production-quantity |
#         | ODST Odin B2 | 5                   |
#     Given one instance of ShipYardLocation
#     Given one instance of V1FittingBuyList
#     Given one instance of V1ManufactureSchedule
#     Given one instance of V1Logistics
#     # Then there is a viewer-panel with "4" instances of "v1-category"
#     # Then there is a viewer-panel with "12" instances of "v1-fitting-item-render"
#     Given one instance of V1JobProductionAccounting
#     Then there is a panel title with the next text "JOBS & COSTS"
#     Then there is a section title with the next text "INDUSTRY JOBS"
#     Then there is a section title with the next text "ACCOUNTING"
# # Then there is a viewer-panel with "4" instances of "v1-category"
# @D3D01 @D3D01.03
# Scenario: [D3D01.03]-On the Dashboard page there is a docker zone that stores the feature buttons.
# @D3D01 @D3D01.04
# Scenario: [D3D01.04]-The predefined feature group selected is the Inventory and then the display area shows the inventory list of pieces available on the repository.
