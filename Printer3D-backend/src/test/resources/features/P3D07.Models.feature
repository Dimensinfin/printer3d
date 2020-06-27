@P3D07 @Models
Feature: [STORY] There is a new backend entity to store Models that have a list of references to the unique id of Parts that are used to compose the model.

    The creation of a new Model has two sets of data. The fields that belong to the Model and the list of Parts that should be used  to  complete
    the Model.
    Models can have a different price than the sum of the prices of the Parts that compose the Model.
    Models should be editable on some of the Model fields and also on the composition of the Model part list.
    Stock calculation ofr Models is done on real time from the stocks of each of the parts that compose it. So it is possible that Models that
    share parts will have stock values that are not real values. Obce requests are processed this values should match.

    # - H A P P Y   P A T H
    @P3D07.H @P3D07.01
    Scenario: [P3D07.01] Validate the creation of a new Model from the data received.
        Given a clean Models repository
        Given a clean Parts repository
        And the following Parts in my service
            | id                                   | label                         | material | color  | buildTime | cost | price | stockLevel | stockAvailable |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                  | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                  | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              |
            | 8d2853ca-ebf6-443e-b705-89f7a5149d37 | Boquilla Ganesha - Embocadura | PLA      | BLANCO | 30        | 0.65 | 2.00  | 3          | 2              |
            | eb80222b-efeb-4d90-9be7-1a0850338e5e | Boquilla Ganesha - Figura     | PLA      | GRIS   | 30        | 0.65 | 2.00  | 3          | 2              |
            | c8505631-938d-4648-a466-7dad581641fe | Boquilla Ganesha - Base       | FLEX     | NEGRO  | 30        | 0.65 | 2.00  | 3          | 2              |
        And the next New Model request
            | id                                   | label            | partIdList                                                                                                     | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Boquilla Ganesha | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea,eb80222b-efeb-4d90-9be7-1a0850338e5e,eb80222b-efeb-4d90-9be7-1a0850338e5e | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        When the New Model request is processed
        Then there is a valid response with return code of "201 CREATED"
        And the response for Model requests has the next fields
            | id                                   | label            | partIdList                                                                                                     | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Boquilla Ganesha | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea,eb80222b-efeb-4d90-9be7-1a0850338e5e,eb80222b-efeb-4d90-9be7-1a0850338e5e | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |

    @P3D07.H @P3D07.02
    Scenario: [P3D07.02] Update an existing Model with data from the frontend. All data is editable so the flow is like the Model creation.
        Given a clean Models repository
        Given a clean Parts repository
        And the following Parts in my service
            | id                                   | label                         | material | color  | buildTime | cost | price | stockLevel | stockAvailable |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                  | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                  | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              |
            | 8d2853ca-ebf6-443e-b705-89f7a5149d37 | Boquilla Ganesha - Embocadura | PLA      | BLANCO | 30        | 0.65 | 2.00  | 3          | 2              |
            | eb80222b-efeb-4d90-9be7-1a0850338e5e | Boquilla Ganesha - Figura     | PLA      | GRIS   | 30        | 0.65 | 2.00  | 3          | 2              |
            | c8505631-938d-4648-a466-7dad581641fe | Boquilla Ganesha - Base       | FLEX     | NEGRO  | 30        | 0.65 | 2.00  | 3          | 2              |
        Given a clean Models repository
        And the following Models in my service
            | id                                   | label            | partIdList                                                                                                       | price | stockLevel | stockAvailable | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Boquilla Ganesha | eb80222b-efeb-4d90-9be7-1a0850338e5e, 8d2853ca-ebf6-443e-b705-89f7a5149d37, 8d2853ca-ebf6-443e-b705-89f7a5149d37 | 4.00  | 3          | 1              | https://ibb.co/3dGbsRh | true   |
        And the next Update Model request
            | id                                   | label        | partIdList                                                                                                     | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Slot CR 1/32 | c8505631-938d-4648-a466-7dad581641fe,eb80222b-efeb-4d90-9be7-1a0850338e5e,eb80222b-efeb-4d90-9be7-1a0850338e5e | 5.00  | 5          | https://ibb.co/3dGbsRh | true   |
        When the Update Model request is processed
        Then there is a valid response with return code of "200 OK"
        And the response for Model requests has the next fields
            | id                                   | label        | partIdList                                                                                                     | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Slot CR 1/32 | c8505631-938d-4648-a466-7dad581641fe,eb80222b-efeb-4d90-9be7-1a0850338e5e,eb80222b-efeb-4d90-9be7-1a0850338e5e | 5.00  | 5          | https://ibb.co/3dGbsRh | true   |
        When the Get Models request is processed
        Then there is a valid response with return code of "200 OK"
        And the number of Models is "1"
        And the model with id "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" has the next fields
            | id                                   | label            | partIdList                                                                                                     | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Boquilla Ganesha | c8505631-938d-4648-a466-7dad581641fe,eb80222b-efeb-4d90-9be7-1a0850338e5e,eb80222b-efeb-4d90-9be7-1a0850338e5e | 5.00  | 5          | https://ibb.co/3dGbsRh | true   |

    @P3D07.H @P3D07.06
    Scenario: [P3D07.06] Retrieve the list of Models and their composition parts from the repository.
        Given a clean Parts repository
        And the following Parts in my service
            | id                                   | label                         | material | color  | buildTime | cost | price | stockLevel | stockAvailable |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key                  | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key                  | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              |
            | 8d2853ca-ebf6-443e-b705-89f7a5149d37 | Boquilla Ganesha - Embocadura | PLA      | BLANCO | 30        | 0.65 | 2.00  | 3          | 2              |
            | eb80222b-efeb-4d90-9be7-1a0850338e5e | Boquilla Ganesha - Figura     | PLA      | GRIS   | 30        | 0.65 | 2.00  | 3          | 2              |
            | c8505631-938d-4648-a466-7dad581641fe | Boquilla Ganesha - Base       | FLEX     | NEGRO  | 30        | 0.65 | 2.00  | 3          | 2              |
        Given a clean Models repository
        And the following Models in my service
            | id                                   | label            | partIdList                                                                                                       | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Boquilla Ganesha | eb80222b-efeb-4d90-9be7-1a0850338e5e, 8d2853ca-ebf6-443e-b705-89f7a5149d37, 8d2853ca-ebf6-443e-b705-89f7a5149d37 | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
            | 08a1c652-37f7-456d-9762-8dab873b40e3 | Boquilla Dragon  | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea, 63fff2bc-a93f-4ee5-b753-185d83a13151                                       | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        When the Get Models request is processed
        Then there is a valid response with return code of "200 OK"
        And the number of Models is "2"
        And the model with id "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" has the next fields
            | id                                   | label            | partIdList                                                                                                       | price | stockLevel | imagePath              | active |
            | 85403a7a-4bf8-4e99-bbc1-8283ea91f99b | Boquilla Ganesha | eb80222b-efeb-4d90-9be7-1a0850338e5e, 8d2853ca-ebf6-443e-b705-89f7a5149d37, 8d2853ca-ebf6-443e-b705-89f7a5149d37 | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
        And the model with id "08a1c652-37f7-456d-9762-8dab873b40e3" has the next fields
            | id                                   | label           | partIdList                                                                 | price | stockLevel | imagePath              | active |
            | 08a1c652-37f7-456d-9762-8dab873b40e3 | Boquilla Dragon | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea, 63fff2bc-a93f-4ee5-b753-185d83a13151 | 4.00  | 3          | https://ibb.co/3dGbsRh | true   |
