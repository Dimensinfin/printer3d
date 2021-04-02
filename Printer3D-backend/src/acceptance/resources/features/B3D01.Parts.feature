@B3D01 @Parts
Feature: [STORY] Manage the Parts on the Inventory repository

  The Parts inventory service should allow to create new parts.
  Some of the part fields can be edited and there should be an endpoint for that.
  Parts can not be deleted so there is no provisioning for that endpoint on production.
  There is duplicate detection where the system should reject Parts that have some key fields identical to an already stored Part.
  Some part fields are editable but for a set of Parts using an additional endpoint.
  [STORY] When a Coil is deleted there can be dangling Parts that no longer can be built. They should be removed from the part list.

  Background:
    Given a clean Parts repository

    # - H A P P Y   P A T H
  @B3D01.H @B3D01.01
  Scenario: [B3D01.01] Validate the creation of a new Part with the data received.
    And the next NewPart request
      | id                                   | label        | material | color  | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 5      | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    When the New Part request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the response for new Part has the next fields
      | id                                   | label        | material | color  | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 5      | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @B3D01.H @B3D01.02
  Scenario: [B3D01.02] Validate the update of the Part data and check the resulting records contents.
    And the following Parts in my service
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next Update Part request
      | id                                   | label                  | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key - UPDATED | PLA      | BLANCO | 60        | 2.65 | 6.00  | 10         | 4              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    When the Update Part request is processed
    Then there is a valid response with return code of "200 OK"
    And the response for Update Part has the next fields
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 2.65 | 6.00  | 10         | 4              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @B3D01.H @B3D01.03
  Scenario: [B3D01.03] Request the list of Parts available on Inventory repository.
    And the following Parts in my service
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    When the Get Parts request is processed
    Then there is a valid response with return code of "200 OK"
    And the list of Parts has "2" items
    And the item "4e7001ee-6bf5-40b4-9c15-61802e4c59ea" of the list of Parts has the next fields
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And we have the next list of Parts at the repository
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @B3D01.H @B3D01.04
  Scenario: [B3D01.04] When a group update is received validate that only the affected Parts are updated.
    And the following Parts in my service
      | id                                   | label          | material | color  | buildTime | weight | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key   | PLA      | BLANCO | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key   | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 8328e3ff-1cee-42f1-bd1d-275353debb6d | NOCovid-19 Key | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next Update Group Part request
      | label                  | buildTime | weight | imagePath              | modelPath    | description                    |
      | Covid-19 Key - UPDATED | 65        | 12     | https://ibb.co/changed | pieza101.STL | This is a changed description. |
    When the Update Group Parts with label "Covid-19 Key" request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Parts request is processed
    And we have the next list of Parts at the repository
      | id                                   | label          | material | color  | buildTime | weight | cost | price | stockLevel | stockAvailable | imagePath              | modelPath    | active | description                                                                                                   |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key   | PLA      | VERDE  | 65        | 12     | 0.65 | 2.00  | 3          | 2              | https://ibb.co/changed | pieza101.STL | true   | This is a changed description.                                                                                |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key   | PLA      | BLANCO | 65        | 12     | 0.65 | 2.00  | 3          | 2              | https://ibb.co/changed | pieza101.STL | true   | This is a changed description.                                                                                |
      | 8328e3ff-1cee-42f1-bd1d-275353debb6d | NOCovid-19 Key | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL   | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @B3D01.H @B3D01.06
  Scenario: [B3D01.06] When a new Part request is received check that missing optional fields get persisted on database with default values.
    And the next NewPart request
      | id                                   | label        | material | color  | buildTime | cost | price | imagePath              | modelPath  | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | https://ibb.co/3dGbsRh | pieza3.STL | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    When the New Part request is processed
    Then there is a valid response with return code of "201 CREATED"
    And the response for new Part has the next fields
      | id                                   | label        | material | color  | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 1      | 60        | 0.65 | 2.00  | 1          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @B3D01.H @B3D01.07
  Scenario: [B3D01.07] Validate the update of the Part Group data and check the resulting records contents.
    And the following Parts in my service
      | id                                   | label          | material | color  | buildTime | weight | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key   | PLA      | BLANCO | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key   | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 8328e3ff-1cee-42f1-bd1d-275353debb6d | NOCovid-19 Key | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next Update Group Part request
      | label                  | buildTime | weight | imagePath              | modelPath    | description                    |
      | Covid-19 Key - UPDATED | 30        | 12     | https://ibb.co/changed | pieza101.STL | This is a changed description. |
    When the Update Group Parts with label "Covid-19 Key" request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Parts request is processed
    And we have the next list of Parts at the repository
      | id                                   | label          | material | color  | buildTime | weight | cost | price | stockLevel | stockAvailable | imagePath              | modelPath    | active | description                                                                                                   |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key   | PLA      | VERDE  | 30        | 12     | 0.65 | 2.00  | 3          | 2              | https://ibb.co/changed | pieza101.STL | true   | This is a changed description.                                                                                |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key   | PLA      | BLANCO | 30        | 12     | 0.65 | 2.00  | 3          | 2              | https://ibb.co/changed | pieza101.STL | true   | This is a changed description.                                                                                |
      | 8328e3ff-1cee-42f1-bd1d-275353debb6d | NOCovid-19 Key | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL   | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @B3D01.H @B3D01.08
  Scenario: [B3D01.08] When requesting the list of Parts, remove those that can no longet be built because there are no such coils.
    Given a clean Coils repository
    And the following Parts in my service
      | id                                   | label          | material | color  | buildTime | weight | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key   | PLA      | BLANCO | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key   | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 8328e3ff-1cee-42f1-bd1d-275353debb6d | NOCovid-19 Key | PLA      | VERDE  | 60        | 3      | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the following Coils in my service
      | id                                   | material | tradeMark | color  | label    | weight | active |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ESUN      | ROJO   | ROJO     | 500    | true   |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | PLA      | ESUN      | BLANCO | BLANCO   | 500    | true   |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | ESUN      | NEGRO  | NEGRO    | 500    | true   |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | ESUN      | GRIS   | GRIS     | 500    | false  |
      | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | ESUN      | VERDE  | AMARILLO | 500    | true   |
    When the Get Parts V2 request is processed
    And the number of Parts is 3
    Given a clean Coils repository
    And the following Coils in my service
      | id                                   | material | tradeMark | color  | label  | weight | active |
      | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ESUN      | ROJO   | ROJO   | 500    | true   |
      | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | PLA      | ESUN      | BLANCO | BLANCO | 500    | true   |
      | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | ESUN      | NEGRO  | NEGRO  | 500    | true   |
      | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | ESUN      | GRIS   | GRIS   | 500    | false  |
    When the Get Parts V2 request is processed
    And the number of Parts is 1

    # - E X C E P T I O N S
  @B3D01.E @B3D01.E.01
  Scenario: [B3D01.E.01] If we receive a request with the same label/material/color of another already persisted Part we reject the new insertion
  with a clear message.
    And the following Parts in my service
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next NewPart request
      | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | c926da84-258c-47d2-8cc8-859058c6266e | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    When the New Part request is processed
    Then there is a exception response with return code of "409 CONFLICT"
    And the exception response contains the message "is rejected because constraint violation"

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
