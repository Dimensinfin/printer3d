@P3D08 @Models
Feature: [STORY] Create a new Feature to see the list of Open Requests. A request is a need from a customer of a set of parts.
    If the request cannot be completed with stock parts then it is left Open.

    This is an entity used by the customers to collect the Parts that they want to buy. The **Request** contains the list of
    specific Part models along with the material and color that are required by the customer. Requests are ordered by order of arrival. The system
    will follow the first come/first served pattern. Then Part build jobs generated by Requests have a higher priority than other jobs generated by
    inventory leveling.

    Background:
        Given a clean Requests repository
        Given a clean RequestsV2 repository
        Given a clean Jobs repository
        Given a clean Models repository
        Given a clean Parts repository

      # - H A P P Y   P A T H
    @P3D08.H @P3D08.01
    Scenario: [P3D08.01] Validate the creation of a new Request with the data received.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 4              |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        And the following Models in my service
            | id                                   | label               | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        When the Get Requests V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        And the Request V2 with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" has the next list of contents
            | itemId                               | type  | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        | 0       |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        | 1       |

    @P3D08.H @P3D08.02
    Scenario: [P3D08.02] When a new Request is created the return state is always Open. This state changes to Completed if there are enough Parts at
    the Inventory stock and only when the Requests is processed during the frontend requesting the lists of Requests.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 4              |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        And the following Models in my service
            | id                                   | label               | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        When the Get Requests V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        And the Request V2 with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" has the next list of contents
            | itemId                               | type  | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        | 0       |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        | 1       |

        Given a clean Parts repository
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 20             |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        When the Get Requests V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                          | requestDate                 | state     |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED |

    @P3D08.H @P3D08.05
    Scenario: [P3D08.05] When a new request cannot be completed because there are not enough Parts at the Inventory the jobs generated to complete the
    Request have priority 1.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 4              |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        And the next Request Contents List
            | itemId                               | type | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"

        When the Get Jobs request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of jobs has "13" records
        And there are "2" records of priority "1"
        And there are "11" records of priority "2"

    @P3D08.H @P3D08.06
    Scenario: [P3D08.06] When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 4              |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        And the following Models in my service
            | id                                   | label               | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"

        When the Complete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
        Then there is a valid response with return code of "200 OK"

        When the Get Requests V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                          | requestDate                 | state     |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED |

        When the Get Parts request is processed
        Then there is a valid response with return code of "200 OK"
#        And the list of Parts has "2" items
        And the item "a12ec0be-52a4-424f-81e1-70446bc38372" of the list of Parts has the next fields
            | id                                   | label                       | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                           |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars. |

    @P3D08.H @P3D08.07
    Scenario: [P3D08.07] Get the Open Requests of version V1 that will only have Parts linked. There should be an Open and a Completed Request.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                 |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                       |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 1              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 3              |                        |            | true   | Union para las piezas de laplataforma slot                                                  |
#        And the next Part Request List
#            | partId                               | quantity |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | 2        |
#            | 2f780382-e539-4945-87ea-354bdd7879ce | 3        |
        And the next New Request request with the current Part Request List
            | id                                   | label                          | requestDate                 | state |
            | a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9 | Complete Slot Car Platform P01 | 2020-06-15T20:00:00.226181Z | OPEN  |
        When the New Request request is processed
        Then there is a valid response with return code of "201 CREATED"
        Given a clean RequestsV2 repository
        When the Get Requests V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9" with the next data
            | id                                   | label                          | requestDate                 | state |
            | a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9 | Complete Slot Car Platform P01 | 2020-06-15T20:00:00.226181Z | OPEN  |
        And the Request V2 with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9" the next list of contents
            | itemId                               | type | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 2        | 2       |
            | 2f780382-e539-4945-87ea-354bdd7879ce | PART | 3        | 0       |

    @P3D08.H @P3D08.08
    Scenario: [P3D08.08] Get the Open Requests of version V2 that will have Models linked.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                 |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                       |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 4              |                        |            | true   | Union para las piezas de laplataforma slot                                                  |
        And the following Models in my service
            | id                                   | label               | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9 | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the Get Requests V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9" with the next data
            | id                                   | label                          | requestDate                 | state |
            | a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9 | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        And the Request V2 with id "a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9" the next list of contents
            | itemId                               | type  | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        | 0       |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        | 1       |

#    @P3D08.H @P3D08.07
#    Scenario: [P3D08.07] Get the Open Requests with a mix of request V1 and Request V2 along with Models on the content list.
