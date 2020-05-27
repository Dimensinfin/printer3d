@D3D01
Feature: [D3D01]-When the application starts show the dashboard page with the default feature dock configuration.

    At the application starts the inital page should be the Dashboard. The dashboard page is the core application page where
    there is no page selected and the Page Display are is clean. On the dashboard there are the application title and version
    along with the Dock tool bar with the default out of the box feature configuration.

    # Background: Dock Default Configuration setup
    #     Given the Inventory Active Dock Configuration

    @D3D01 @D3D01.01
    Scenario: [D3D01.01]-Check that the landing page after application start is the Dashboard.
        Given the application Printer3DManager
        When the application starts the default route is "/DASHBOARD"

    @D3D01 @D3D01.02
    Scenario: [D3D01.02]-The landing page Dashboard shows the title panel.
        Given the DashboardPage is activated
        Then there is a "app-title" with the next fields
            | app-title                |
            | <environment.app-name> |
        Then there is a "app-version" with the next fields
            | app-version               |
            | <environment.app-version> |
        Then there is a "page-path" with the next fields
            | page-name  |
            | /DASHBOARD |

    @D3D01 @D3D01.03
    Scenario: [D3D01.03]-On the dashboard page there is a docker with feature elements.
        When the DashboardPage is activated
        Given one instance of Dock
        Given one or more instances of Feature
        Then there is 0 v1-feature-render active
        Then there is a "v1-feature-render" at index "0" with the next fields
            | label      | active |
            | /Inventario | false  |
        Then there is a "v1-feature-render" at index "1" with the next fields
            | label     | active |
            | /Nueva Pieza | false  |
    # Then there is a v1-feature active with label "/Inventory"

    @D3D01 @D3D01.04
    Scenario: [D3D01.04]-On the dashboard page the page display area in empty because there is no feature selected.
        When the DashboardPage is activated
        Then the Page Display Area is empty
