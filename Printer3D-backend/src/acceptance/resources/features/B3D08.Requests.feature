@B3D08 @Requests
Feature: [FEATURE] Manage the interactions with the Request entity. Requests have a complex life cycle that can be managed through the endpoints.

    This is an entity used by the customers to collect the Parts that they want to buy. The **Request** contains the list of
    specific Part models along with the material and color that are required by the customer. Requests are ordered by order of arrival. The system
    will follow the first come/first served pattern. Then Part build jobs generated by Requests have a higher priority than other jobs generated by
    inventory leveling.
    [STORY] There is and endpoint to deliver the Customer Request to the customer and at this point remove the delivered parts from the stock.
    [STORY] There is an endpoint to charge a Customer Request and account its amount to the balance.

    Background:
        Given a clean Coils repository
        And the following Coils in my service
            | id                                   | material | tradeMark   | color  | label | weight | active |
            | e7a42126-6732-41f0-902b-98a8ebe79eb5 | PLA      | FILLAMENTUM | BLANCO | -     | 500    | true   |
            | 151e6dd4-ab88-4289-9a5d-a68a25ff0b65 | PLA      | FILLAMENTUM | VERDE  | -     | 500    | true   |
            | fccd2177-e6ee-499a-8a53-1e7aa4e101a7 | FLEX     | EASUN       | NEGRO  | -     | 500    | true   |
            | d29b31c9-7223-4935-8d32-4f5b60ecd492 | PLA      | FILLAMENTUM | NEGRO  | -     | 498    | true   |
        Given a clean Parts repository
        And the following Parts in my service
            | id                                   | label                                   | material | color  | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath         | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 5      | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL        | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 5      | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL        | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 5      | 30        | 1.0  | 5.00  | 2          | 2              | https://ibb.co/3dGbsRh | pieza3.STL        | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 5      | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL        | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 5      | 15        | 0.1  | 1.00  | 10         | 4              |                        |                   | true   | Union para las piezas de laplataforma slot                                                                    |
            | 93eb3718-d012-4787-9996-96d498f2ed97 | BASTIDORES ''URBAN DIGIT''              | PLA      | NEGRO  | 10     | 1440      | 1.0  | 5.0   | 3          | 2              |                        | BASTIDORES UD 1.0 | true   | Bastidor de la caja que es parte del set ''URBAN DIGIT''.                                                     |
            | d698fd38-61b0-49f5-86cf-a8b25b3bec06 | CARCASA INFERIOR ''URBAN DIGIT''        | PLA      | NEGRO  | 50     | 1440      | 5.0  | 10.0  | 2          | 2              |                        | CARCASA INF 1.0   | true   | Parte de abajo del set ''URBAN DIGIT''.                                                                       |
            | 0e679328-f68b-44f5-a3cd-dea13f46ec4c | CARCASA SUPERIOR ''URBAN DIGIT''        | PLA      | NEGRO  | 50     | 1440      | 5.0  | 10.0  | 2          | 2              |                        | CARCASA SUP 1.0   | true   | Parte de arriba del set ''URBAN DIGIT''.                                                                      |
        Given a clean Models repository
        And the following Models in my service
            | id                                   | label               | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
            | 9b8a20ba-259c-4ee9-9478-19354e75dcd1 | URBAN DIGIT         | 93eb3718-d012-4787-9996-96d498f2ed97,d698fd38-61b0-49f5-86cf-a8b25b3bec06,0e679328-f68b-44f5-a3cd-dea13f46ec4c                                                                           | 30.0  | 1          |                        | true   |
        Given a clean Jobs repository
        Given a clean RequestsV2 repository

      # - H A P P Y   P A T H
    @B3D08.H1 @B3D08.01
    Scenario: [B3D08.01] Validate the creation of a new Request with the minimal data.
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                             | requestDate              | state | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Primera parte pedido URBAN DIGIT. | 2021-03-03T16:00:17.353Z | OPEN  | 15.73 |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Read back the list of Open requests to check the persisted data
        When the Get Open Requests request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                             | customer | requestDate              | deliveredDate | paymentDate | state | paid  | amount | iva  | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Primera parte pedido URBAN DIGIT. |          | 2021-03-03T16:00:17.353Z |               |             | OPEN  | false | 13.0   | 2.73 | 15.73 |
        And the Request V2 with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" has the next list of contents
            | itemId                               | type  | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        | 0       |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        | 1       |

    @B3D08.H1 @B3D08.02
    Scenario: [B3D08.02] Validate the creation of a new Request with Customer data.
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                             | customer           | requestDate              | state | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Primera parte pedido URBAN DIGIT. | Test Customer Name | 2021-03-03T16:00:17.353Z | OPEN  | 15.73 |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        When the Get Open Requests request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                             | customer           | requestDate              | deliveredDate | paymentDate | state | paid  | amount | iva  | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Primera parte pedido URBAN DIGIT. | Test Customer Name | 2021-03-03T16:00:17.353Z |               |             | OPEN  | false | 13.0   | 2.73 | 15.73 |
        And the Request V2 with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" has the next list of contents
            | itemId                               | type  | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        | 0       |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        | 1       |

    @B3D08.H1 @B3D08.03
    Scenario: [B3D08.03] Validate the creation of a new Request pre-paid.
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                             | customer           | requestDate              | state | total | paid |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Primera parte pedido URBAN DIGIT. | Test Customer Name | 2021-03-03T16:00:17.353Z | OPEN  | 15.73 | true |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        When the Get Open Requests request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                             | customer           | requestDate              | deliveredDate | paymentDate | state | paid | amount | iva  | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Primera parte pedido URBAN DIGIT. | Test Customer Name | 2021-03-03T16:00:17.353Z |               | <now>       | OPEN  | true | 13.0   | 2.73 | 15.73 |
        And the Request V2 with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" has the next list of contents
            | itemId                               | type  | quantity | missing |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        | 0       |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        | 1       |

    @B3D08.H1 @B3D08.04
    Scenario: [B3D08.04] When a new Request is created the return state is always Open. This state changes to Completed if there are enough Parts at
    the Inventory stock and only when the Requests is processed during the frontend requesting the lists of Requests.
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | 18.0  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        When the Get Open Requests request is processed
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
        When the Get Open Requests request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
            | id                                   | label                          | requestDate                 | state     |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED |

    @B3D08.H1 @B3D08.05
    Scenario: [B3D08.05] When a new request cannot be completed because there are not enough Parts at the Inventory the jobs generated to complete the
    Request have priority 1.
        And the next Request Contents List
            | itemId                               | type | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 4        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | total |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | 18.0  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"

        When the Get Jobs request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of jobs has "30" records
        And there are "2" records of priority "1"
        And there are "28" records of priority "2"

    @B3D08.H1 @B3D08.06
    Scenario: [B3D08.06] An open request is verified to be in COMPLETED and then their contents delivered that means that they are removed from the stocks.
            # - Create the request
        Given the next Request Contents List
            | itemId                               | type  | quantity |
            | 9b8a20ba-259c-4ee9-9478-19354e75dcd1 | MODEL | 1        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                             | requestDate              | total | state |
            | 9d656f6e-d6c7-4783-91d8-984543900a53 | Primera parte pedido URBAN DIGIT. | 2021-05-12T10:00:00.000Z | 35    | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
            # - Check the request is on the COMPLETED state
        When the Get Open Requests request is processed
        Then there is a valid response with return code of "200 OK"
        And the resulting list of Requests has a request with id "9d656f6e-d6c7-4783-91d8-984543900a53" with the next data
            | id                                   | label                             | requestDate              | state     |
            | 9d656f6e-d6c7-4783-91d8-984543900a53 | Primera parte pedido URBAN DIGIT. | 2021-05-12T10:00:00.000Z | COMPLETED |
            # - Check the current stock levels
        When the Get Parts V2 request is processed
        Then we have Part with id "93eb3718-d012-4787-9996-96d498f2ed97" with the next values
            | id                                   | label                      | material | color | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath | modelPath         | active | description                                               |
            | 93eb3718-d012-4787-9996-96d498f2ed97 | BASTIDORES ''URBAN DIGIT'' | PLA      | NEGRO | 10     | 1440      | 1.0  | 5.0   | 3          | 2              |           | BASTIDORES UD 1.0 | true   | Bastidor de la caja que es parte del set ''URBAN DIGIT''. |
        Then we have Part with id "d698fd38-61b0-49f5-86cf-a8b25b3bec06" with the next values
            | id                                   | label                            | material | color | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath | modelPath       | active | description                             |
            | d698fd38-61b0-49f5-86cf-a8b25b3bec06 | CARCASA INFERIOR ''URBAN DIGIT'' | PLA      | NEGRO | 50     | 1440      | 5.0  | 10.0  | 2          | 2              |           | CARCASA INF 1.0 | true   | Parte de abajo del set ''URBAN DIGIT''. |
        Then we have Part with id "0e679328-f68b-44f5-a3cd-dea13f46ec4c" with the next values
            | id                                   | label                            | material | color | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath | modelPath       | active | description                              |
            | 0e679328-f68b-44f5-a3cd-dea13f46ec4c | CARCASA SUPERIOR ''URBAN DIGIT'' | PLA      | NEGRO | 50     | 1440      | 5.0  | 10.0  | 2          | 2              |           | CARCASA SUP 1.0 | true   | Parte de arriba del set ''URBAN DIGIT''. |
            # - Deliver the request
        When the Deliver Request endpoint is called for Request "9d656f6e-d6c7-4783-91d8-984543900a53"
        When the Get Closed Requests request is processed
        Then there is a valid response with return code of "200 OK"
        And the number of Requests found is 1
        And the resulting list of Requests has a request with id "9d656f6e-d6c7-4783-91d8-984543900a53" with the next data
            | id                                   | label                             | requestDate              | deliveredDate | state     |
            | 9d656f6e-d6c7-4783-91d8-984543900a53 | Primera parte pedido URBAN DIGIT. | 2021-05-12T10:00:00.000Z | <defined>     | DELIVERED |
        Then we have Part with id "93eb3718-d012-4787-9996-96d498f2ed97" with the next values
            | id                                   | label                      | material | color | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath | modelPath         | active | description                                               |
            | 93eb3718-d012-4787-9996-96d498f2ed97 | BASTIDORES ''URBAN DIGIT'' | PLA      | NEGRO | 10     | 1440      | 1.0  | 5.0   | 3          | 1              |           | BASTIDORES UD 1.0 | true   | Bastidor de la caja que es parte del set ''URBAN DIGIT''. |
        Then we have Part with id "d698fd38-61b0-49f5-86cf-a8b25b3bec06" with the next values
            | id                                   | label                            | material | color | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath | modelPath       | active | description                             |
            | d698fd38-61b0-49f5-86cf-a8b25b3bec06 | CARCASA INFERIOR ''URBAN DIGIT'' | PLA      | NEGRO | 50     | 1440      | 5.0  | 10.0  | 2          | 1              |           | CARCASA INF 1.0 | true   | Parte de abajo del set ''URBAN DIGIT''. |
        Then we have Part with id "0e679328-f68b-44f5-a3cd-dea13f46ec4c" with the next values
            | id                                   | label                            | material | color | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath | modelPath       | active | description                              |
            | 0e679328-f68b-44f5-a3cd-dea13f46ec4c | CARCASA SUPERIOR ''URBAN DIGIT'' | PLA      | NEGRO | 50     | 1440      | 5.0  | 10.0  | 2          | 1              |           | CARCASA SUP 1.0 | true   | Parte de arriba del set ''URBAN DIGIT''. |

#    @B3D08.H1 @B3D08.06
#    Scenario: [B3D08.06] Requests on the COMPLETED state can be delivered and at this point the stocks are updated and the Parts that compose the request are subtracted from the Parts inventory.
#        And the next Request Contents List
#            | itemId                               | type | quantity |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 2        |
#        And creating the next Request V2 with previous Contents
#            | id                                   | label                          | requestDate                 | total |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | 18.0  |
#        When the New Request V2 request is processed
#        Then there is a valid response with return code of "201 CREATED"
#        When the Get Open Requests request is processed
#        Then there is a valid response with return code of "200 OK"
#        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
#            | id                                   | label                          | requestDate                 | state     |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED |

#    @B3D08.H2 @B3D08.06
#    Scenario: [B3D08.06] When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
#        And the next Request Contents List
#            | itemId                               | type  | quantity |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
#            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
#        And creating the next Request V2 with previous Contents
#            | id                                   | label                          | requestDate                 | state |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
#        When the New Request V2 request is processed
#        Then there is a valid response with return code of "201 CREATED"
#
#        When the Complete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
#        Then there is a valid response with return code of "200 OK"
#
#        When the Get Requests V2 request is processed
#        Then there is a valid response with return code of "200 OK"
#        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
#            | id                                   | label                          | requestDate                 | state     |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED |
#
#        When the Get Parts request is processed
#        Then there is a valid response with return code of "200 OK"
#        And the item "a12ec0be-52a4-424f-81e1-70446bc38372" of the list of Parts has the next fields
#            | id                                   | label                       | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                           |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars. |

#    @B3D08.H2 @B3D08.07
#    Scenario: [B3D08.07] There is an endpoint that allows to delete a Request from the repository. The request should be on OPEN state.
#        And the next Request Contents List
#            | itemId                               | type | quantity |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 1        |
#        And creating the next Request V2 with previous Contents
#            | id                                   | label                          | requestDate                 | state |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
#        When the New Request V2 request is processed
#        Then there is a valid response with return code of "201 CREATED"
#
#        When the Delete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
#        Then there is a valid response with return code of "200 OK"
#        And the number of records processed is "1"

#    @B3D08.H2 @B3D08.08
#    Scenario: [B3D08.08] When a Request is closed the the request amount is calculated with the current price values for the parts contained on the
#    Request.
#        # - Create a request
#        And the next Request Contents List
#            | itemId                               | type  | quantity |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
#            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
#        And creating the next Request V2 with previous Contents
#            | id                                   | label                          | requestDate                 | state |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
#        When the New Request V2 request is processed
#        Then there is a valid response with return code of "201 CREATED"
#
#        # - Check the request data stored on the repository
#        When the Get Requests V2 request is processed
#        Then there is a valid response with return code of "200 OK"
#        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
#            | id                                   | label                          | requestDate                 | state     | amount |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED | 0.00   |
#
#        When the Complete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
#        Then there is a valid response with return code of "200 OK"
#        And the Request Response returned has the next data
#            | id                                   | label                          | requestDate                 | state | amount |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | CLOSE | 31.00  |
#
#    @B3D08.H2 @B3D08.09
#    Scenario: [B3D08.09] When a Request is closed the the request closed date is the current date and we can calculate the week for aggregation.
#        # - Create a request
#        And the next Request Contents List
#            | itemId                               | type  | quantity |
#            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
#            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
#        And creating the next Request V2 with previous Contents
#            | id                                   | label                          | requestDate                 | state |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
#        When the New Request V2 request is processed
#        Then there is a valid response with return code of "201 CREATED"
#
#        # - Check the request data stored on the repository
#        When the Get Requests V2 request is processed
#        Then there is a valid response with return code of "200 OK"
#        And the resulting list of Requests has a request with id "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" with the next data
#            | id                                   | label                          | requestDate                 | state     | amount |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | COMPLETED | 0.00   |
#
#        When the Complete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
#        Then there is a valid response with return code of "200 OK"
#        And the Request Response returned has the next data
#            | id                                   | label                          | dateClosed |
#            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | <today>    |

    @B3D08.H2 @B3D08.10
    Scenario: [B3D08.10] Requests should be processed ordered on the request date from older to newest.
        # - Create a request
        Given the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | <yesterday> | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Create a second request
        Given the next Request Contents List
            | itemId                               | type | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 1        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | PART | 5        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                     | requestDate | state |
            | 839b68e6-7c97-4575-9c9f-3812854f239d | Set of bases for slot car | <now>       | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"


    # - E X C E P T I O N S
    @B3D08.E @B3D08.E.01
    Scenario: [B3D08.E.01] Before delivering a Request we have to be sure there are enough Parts on stock. If not raise an exception.
            # - Create the request
        Given the next Request Contents List
            | itemId                               | type  | quantity |
            | 9b8a20ba-259c-4ee9-9478-19354e75dcd1 | MODEL | 3        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                             | requestDate              | total | state |
            | 9d656f6e-d6c7-4783-91d8-984543900a53 | Primera parte pedido URBAN DIGIT. | 2021-05-12T10:00:00.000Z | 35    | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
            # - Deliver the request
        When the Deliver Request endpoint is called for Request "9d656f6e-d6c7-4783-91d8-984543900a53"
        Then there is a exception response with return code of "409 CONFLICT"
        And the exception response name is "REQUEST_CANNOT_BE_FULFILLED"
        And the exception response has the message "Request record with id [9d656f6e-d6c7-4783-91d8-984543900a53] has not enough resources to be completed. Obsolete state."

    @B3D08.E @B3D08.E.02
    Scenario: [B3D08.E.02] If there are missing fields on the request of they do fail any of the validations then we report a reject exception.
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next incomplete Request V2 with previous Contents
            | id                                   | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a exception response with return code of "400 BAD_REQUEST"
        And the exception response contains the message "The request is not valid. [Field error in object"

    @B3D08.E @B3D08.E.03
    Scenario: [B3D08.E.03] If the Requests that should be deleted is on the state CLOSED then raise an exception.
        And the next Request Contents List
            | itemId                               | type | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 1        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | CLOSE |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"

        When the Delete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
        Then there is a exception response with return code of "409 CONFLICT"
        And the exception response name is "REQUEST_NOT_IN_CORRECT_STATE"
        And the exception response has the message "Request record with id [d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a] is not on the correct state to perform the requested operation."

    @B3D08.E @B3D08.E.04
    Scenario: [B3D08.E.04] When delivering a Request we can raise the NOT FOUND exception of the request is not on the repository (deleted on
    another session).
            # - Create the request
        Given the next Request Contents List
            | itemId                               | type  | quantity |
            | 9b8a20ba-259c-4ee9-9478-19354e75dcd1 | MODEL | 1        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                             | requestDate              | total | state |
            | 9d656f6e-d6c7-4783-91d8-984543900a53 | Primera parte pedido URBAN DIGIT. | 2021-05-12T10:00:00.000Z | 35    | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
            # - Deliver the request
        When the Deliver Request endpoint is called for Request "9d656f6e-d6c7-4783-91d8-984543900000"
        Then there is a exception response with return code of "404 NOT_FOUND"
        And the exception response name is "REQUEST_NOT_FOUND"
        And the exception response has the message "Request record with id [9d656f6e-d6c7-4783-91d8-984543900000] not found at the repository."
        And the exception response has the cause "No Request found while trying to deliver the Request."

    @B3D08.E @B3D08.E.05
    Scenario: [B3D08.E.05] When deleting a Request we can raise the NOT FOUND exception of the request is not on the repository (deleted on
    another session).
        When the Delete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8aaa" is processed
        Then there is a exception response with return code of "404 NOT_FOUND"
        And the exception response name is "REQUEST_NOT_FOUND"
        And the exception response has the message "Request record with id [d8e2cc31-4a5b-4f9a-a494-ca21956e8aaa] not found at the repository."
        And the exception response has the cause "No Request found while trying to delete a Request."

    @B3D08.E @B3D08.E.06
    Scenario: [B3D08.E.06] If when creating a request we found another Request with the same identifier then report that the action should use the
    update endpoint.
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"

        When the New Request V2 request is processed
        Then there is a exception response with return code of "409 CONFLICT"
        And the exception response name is "REQUEST_ALREADY_EXISTS"
        And the exception response has the message "Request with id [d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a] already exists at the repository. This should not be possible and means a development defect."
