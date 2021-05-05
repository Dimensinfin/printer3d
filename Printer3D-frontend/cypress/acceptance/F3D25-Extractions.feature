@F3D25
Feature: [F3D25]-Validate the contents and features on the Extractions module.

    [STORY] There is a extraction button that will generate a CVS file with all the Closed Customer Requests.

    Background: Application landing page
        Given the application Printer3DManager
        Given one instance of Dock
        Given there is a click on Feature "/EXTRACCIONES"
        Then the page "Extractions Page" is activated
        When the loading panel completes

    @F3D25.01
    Scenario: [F3D25.01]-The Customer Request Extraction button has some valid properties.
        When the page "Extractions Page" is activated
        Then  the target has the title "/EXTRACCIONES DE DATOS"
        Then the target has 1 "extraction"
        Given the target the "extraction" with id "001-closed-customer-requests"
        Then the extraction link points to "/api/v1/accounting/requests/data"
        Then the extraction has label "EXTRACCIÓN PEDIDOS CERRADOS"
