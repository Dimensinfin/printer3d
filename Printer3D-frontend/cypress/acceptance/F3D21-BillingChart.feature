@F3D21
Feature: [F3D21]-[STORY] Add a panel to display a bar graph with the aggregated amounts of sold items along each of the weeks.

    There is a panel on the page heading section that contains some chart data from the accounting endpoints.
    The graph shows a vertical bar chart with up to 6 bars of data.
    If the char is clicked then the chart data refreshes with data from the backend.

    Background: Application landing page
        Given the application Printer3DManager
        When the application completes loading

    # - H A P P Y   P A T H
    @F3D21.01
    Scenario: [F3D21.01]-The bar chart shows when the dashboard page is loaded. While there is no data downloaded the char shows the NO DATA sign.
        Then the target has 1 "billing-chart"
        And target has a "blueviolet-mark" mark
        And there is a sign saying "NO HAY DATOS"

    @F3D21.02
    Scenario: [F3D21.02]-After some time the bar chart shows some accounting data. The bar char has 4 columns.
        Then the target has 1 "billing-chart"
        And target has a "blueviolet-mark" mark
        When waiting for 5 second
        And graph chart has 4 columns
