@P3D05 @Jobs
Feature: The list of jobs to build to have a preselected stock can be ordered and grouped

    The stock requirements will require to optimize the build order so all requests are fulfilled on time. From the simplest unordered list to the
    special time prioritized lists.

    Background:
        Given a clean Requests repository
        Given a clean RequestsV2 repository
        Given a clean Jobs repository
        Given a clean Models repository
        Given a clean Parts repository

    # - H A P P Y   P A T H
    @P3D05.H @P3D05.01
    Scenario: [P3D05.01] When the pending jobs request is received a new list of jobs is calculated.
        And the following Parts in my service
            | id                                   | label            | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key     | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key     | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 10             |                        |            | true   | Union para las piezas de laplataforma slot                                                                    |
        When the Get Jobs request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of jobs has "2" records

    @P3D05.H @P3D05.02
    Scenario: [P3D05.02] When a job is completed by the user the Machine Job record is expanded with the Part information and persisted on the
    historic Jobs repository.
        Given the following Parts in my service
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId                     | jobInstallmentDate          | currentPartInstances |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | 2020-06-18T10:00:00.226181Z | 2                    |
        When the Complete Job request for Machine "e18aa442-19cd-4b08-8ed0-9f1917821fac" is processed
        Then there is a valid response with return code of "200 OK"
        And on the Jobs repository there is a record for Part "4e7001ee-6bf5-40b4-9c15-61802e4c59ea" with the next data
            | buildTime | cost | price | partCopies | jobInstallmentDate          |
            | 120       | 0.65 | 2.00  | 2          | 2020-06-18T10:00:00.226181Z |

    @P3D05.H @P3D05.03
    Scenario: [P3D05.03] Jobs from Requests have a higher priority than stock level jobs.
        Given a clean Parts repository
        And the following Parts in my service
            | id                                   | label                                   | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                 |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 30        | 1.0  | 5.00  | 2          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                       |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 45        | 1.0  | 5.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 15        | 0.1  | 1.00  | 10         | 4              |                        |            | true   | Union para las piezas de laplataforma slot                                                  |
        And the following Models in my service
            | id                                   | label              | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataform | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        And the next Request Contents List
            | itemId                               | type  | quantity |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PART  | 1        |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | MODEL | 2        |
        And creating the next Request V2 with previous Contents
            | id                                   | label                          | requestDate                 | state |
            | a00f7e7a-56c4-4dc1-a630-2b2a62b54eb9 | Complete Slot Car Platform P02 | 2020-06-29T20:00:00.226181Z | OPEN  |
        When the New Request V2 request is processed
        Then there is a valid response with return code of "201 CREATED"
        # - Get the Job list after creating a Request.
        When the Get Jobs request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of jobs has "25" records
        And the jobs records contain the next information
            | part.id                              | part.label                              | priority |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 1        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 1        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 1        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |

    @P3D05.H @P3D05.04
    Scenario: [P3D05.04] When calculating the Model requirements from stock using the preferred stock level the list of jobs for leveling grows.
        And the following Parts in my service
            | id                                   | label                                   | material | color  | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                 |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | PLA      | BLANCO | 10     | 30        | 1.0  | 5.00  | 2          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Base para la plataforma de slot cars.                                                       |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | PLA      | BLANCO | 10     | 45        | 1.0  | 5.00  | 3          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | Panel para guardar tornillos y destornillador y adaptable para la base de la platforma Slot |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | FLEX     | NEGRO  | 10     | 15        | 0.1  | 1.00  | 10         | 2              |                        |            | true   | Union para las piezas de laplataforma slot                                                  |
        Given a clean Models repository
        And the following Models in my service
            | id                                   | label              | partIdList                                                                                                                                                                               | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | SLOT CAR Plataform | a12ec0be-52a4-424f-81e1-70446bc38372,9fd4337d-6a4d-47b3-a7ac-a61bd51fad39,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce,2f780382-e539-4945-87ea-354bdd7879ce | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        When the Get Jobs request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of jobs has "28" records
        And the jobs records contain the next information
            | part.id                              | part.label                              | priority |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | 2f780382-e539-4945-87ea-354bdd7879ce | UNION PLATAFORMA                        | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | a12ec0be-52a4-424f-81e1-70446bc38372 | PLATAFORMA SLOT 1/32 - Base             | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
            | 9fd4337d-6a4d-47b3-a7ac-a61bd51fad39 | PLATAFORMA SLOT 1/32 - Guarda Tornillos | 2        |
