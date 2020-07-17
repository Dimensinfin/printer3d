@B3D09 @Accounting
Feature: [STORY] Create a new endpoint to aggregate the amounts for closed Requests and generate a row for each week.

    The accounting has an endpoint that will read ll Requests and then aggregate their amounts by week/year.
    The endpoint has a parameter that allows to select the number of weeks ther should be returned.

    Background:
        Given a clean Requests repository
        Given a clean RequestsV2 repository
        Given a clean Jobs repository
        Given a clean Models repository
        Given a clean Parts repository

      # - H A P P Y   P A T H
    @P3D09.H @P3D09.01
    Scenario: [P3D09.01] If the request to get the amounts is received then the list of week data is generated.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces andbuttons. Use it to open doors and push buttons.  |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 10             |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        And the following Models in my service
            | id                                   | label                  | partIdList                                                                                                                                                                                                                    | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma 01 | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce                                      | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
            | d157d5dc-fa32-4054-bce8-0e72c02d49f0 | SLOT CAR Plataforma 02 | a12ec0be-52a4-424f-81e1-70446bc38372,a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        # - Create a new Request
        And the next Request Contents List
            | itemId                               | type | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 1        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P01 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Create a new Request
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | 8548027a-f969-43a4-bc29-188205e1b73d | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Create a new Request
        And the next Request Contents List
            | itemId                               | type | quantity |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | PART | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | c89ece07-d88a-4ed1-9382-d686237d220a | Complete Slot Car Platform P03 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Close one Request
        When the Complete Request request for request "c89ece07-d88a-4ed1-9382-d686237d220a" is processed
        Then there is a valid response with return code of "200 OK"

        When the Accounting Week Income request is processed
        Then there is a valid response with return code of "200 OK"
        And the week report has the next contents
            | year | week | amount |
            | 2020 | 29   | 4      |

    @P3D09.H @P3D09.02
    Scenario: [P3D09.02] Validate that where there are more than a week and the list is trimmed the results are on the correct order and that
    contain the most recent N weeks.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                            | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces andbuttons. Use it to open doors and push buttons.  |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                            | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                                         |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 10             | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot                   |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 10             |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        And the following Models in my service
            | id                                   | label                  | partIdList                                                                                                                                                                                                                    | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataforma 01 | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce                                      | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
            | d157d5dc-fa32-4054-bce8-0e72c02d49f0 | SLOT CAR Plataforma 02 | a12ec0be-52a4-424f-81e1-70446bc38372,a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        # - Create a new Request
        And the next Request Contents List
            | itemId                               | type | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART | 1        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Complete Slot Car Platform P01 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Create a new Request
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | 8548027a-f969-43a4-bc29-188205e1b73d | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Create a new Request
        And the next Request Contents List
            | itemId                               | type | quantity |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | PART | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | c89ece07-d88a-4ed1-9382-d686237d220a | Complete Slot Car Platform P03 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Close one Request
        When the Complete Request request for request "c89ece07-d88a-4ed1-9382-d686237d220a" is processed
        Then there is a valid response with return code of "200 OK"
        # - Change the close date to a previous week
        When moving back one week all closed dates
        # - Close another Request
        When the Complete Request request for request "d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a" is processed
        Then there is a valid response with return code of "200 OK"
