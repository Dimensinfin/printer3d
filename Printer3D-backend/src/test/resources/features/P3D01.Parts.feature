@P3D01 @Parts
Feature: Manage the Parts on the Inventory repository

  As the backend service
  The service should be able to persist new Part instances, to retrieve the list of already defined Parts including filtering and to expire and
  deactivate unused parts. Also updates should be supported and the complete control for duplicates.

  # - H A P P Y   P A T H
  @P3D01.H @P3D01.01
  Scenario: [P3D01.01] Validate the creation of a new Part with the data received.
    Given a clean Inventory repository
#    And the next NewPart request
#      | id                                   | label        | colorCode | cost | price |active|
#      | 03bd272e-207d-4461-80b6-1cf49483bdf8 | Covid-19 Key | LB        | 0.65 | 2.00  |true  |
#    When the New Part request is processed
#    Then there is a valid response with return code of "201 CREATED"
#    And the response for new Part has the next fields
#      | id                                   | label        | colorCode | cost | price |active|
#      | 03bd272e-207d-4461-80b6-1cf49483bdf8 | Covid-19 Key | LB        | 0.65 | 2.00  |true  |

#  @ID01.H @ID01.02
#  Scenario: [ID01.02] Validate the update of the Centro data and check the resulting records contents.
#    Given a clean Centros repository
#    And the following Centros in my service
#      | id     | nombre                  | logotipo               | direccion           | localidad   |
#      | 200001 | Centro Sanitorio Valles | Centro-Valles-Logo.gif | Calle Sin Nombre 17 | Guadalajara |
#    And the next Centro request
#      | id     | nombre                           | logotipo                        | direccion            | localidad |
#      | 200001 | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#    When the Update Center request is processed
#    Then there is a valid response with return code of "200 OK"
#    And the response for new Centro has the next fields
#      | jsonClass | id     | nombre                           | logotipo                        | direccion            | localidad |
#      | Centro    | 200001 | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#
#  @ID01.H @ID01.03
#  Scenario: [ID01.03] Request the list of Centros available on the system.
#    Given a clean Centros repository
#    And the following Centros in my service
#      | id     | nombre                  | logotipo               | direccion           | localidad   |
#      | 100001 | Centro Sanitorio Valles | Centro-Valles-Logo.gif | Calle Sin Nombre 17 | Guadalajara |
#    And the next Centro request
#      | id     | nombre                           | logotipo                        | direccion            | localidad |
#      | 200001 | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#    When the "New Center" request is processed
#    Then there is a valid response with return code of "201 CREATED"
#    When the "Get Centers" request is processed
#    Then there is a valid response with return code of "200 OK"
#    And the list of Centros has "2" items
#    And the item "1" of the list of Centros has the next fields
#      | jsonClass | nombre                  | logotipo               | direccion           | localidad   |
#      | Centro    | Centro Sanitorio Valles | Centro-Valles-Logo.gif | Calle Sin Nombre 17 | Guadalajara |
#    And the item "2" of the list of Centros has the next fields
#      | jsonClass | nombre                           | logotipo                        | direccion            | localidad |
#      | Centro    | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#
#  # - E X C E P T I O N S
#  @ID01.E @ID01.04
#  Scenario: [ID01.04] Get an exception if the Center creation endpoint does not get the right Center data.
#    Given and invalid Centro creation request
#    When the New Center request is processed
#    Then there is a exception response with return code of "400 BAD_REQUEST"
#    And the exception response has the message "The request is not valid. Centro record is mandatory."

#  @ID01.E @ID01.05
#  Scenario: [ID01.05] Reject the creation of a Center if there are missing data.
#    Given the next Centro request
#      | nombre                           | logotipo                        | direccion            | localidad |
#      | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#    When the New Center request is processed
#    Then there is a exception response with return code of "400 BAD_REQUEST"
#    And the exception response has the message "The request is not valid. Centro unique identifier id is mandatory."

#  @ID01.E @ID01.06
#  Scenario: [ID01.06] Reject the creation of a Center if there are missing data.
#    Given the next Centro request
#      | id     | logotipo                        | direccion            | localidad |
#      | 100001 | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#    When the New Center request is processed
#    Then there is a exception response with return code of "400 BAD_REQUEST"
#    And the exception response has the message "The request is not valid. Centro Nombre is mandatory."

#  @ID01.E @ID01.07
#  Scenario: [ID01.07] Reject the creation of a Center if there are missing data.
#    Given the next Centro request
#      | id     | nombre                           | logotipo                        | direccion            |
#      | 100001 | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 |
#    When the New Center request is processed
#    Then there is a exception response with return code of "400 BAD_REQUEST"
#    And the exception response has the message "The request is not valid. Centro Localidad is mandatory."

#  @ID01.E @ID01.08
#  Scenario: [ID01.08] Reject the create of the Centro record if the identifier is found and recommend to use the Update endpoint.
#    Given a clean Centros repository
#    And the following Centros in my service
#      | id     | nombre                  | logotipo               | direccion           | localidad   |
#      | 200001 | Centro Sanitorio Valles | Centro-Valles-Logo.gif | Calle Sin Nombre 17 | Guadalajara |
#    And the next Centro request
#      | id     | nombre                           | logotipo                        | direccion            | localidad |
#      | 200001 | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#    When the New Center request is processed
#    Then there is a exception response with return code of "409 CONFLICT"
#    And the exception response has the message "The Centro [200001] already exists. Use the Update endpoint."
#
#  @ID01.E @ID01.09
#  Scenario: [ID01.09] Reject the update of the Centro data if selected Centro is not found on the repository.
#    Given a clean Centros repository
#    And the next Centro request
#      | id     | nombre                           | logotipo                        | direccion            | localidad |
#      | 200001 | Centro Sanitorio Valle del Loira | Centro-Valle-del-Loira-Logo.gif | Calle Con Nombre 342 | Madrid    |
#    When the Update Center request is processed
#    Then there is a exception response with return code of "404 NOT_FOUND"
#    And the exception response has the message "The requested Centro with id [200001] is not found at the repository."

