@P3D05 @Jobs
Feature: The list of jobs to build to have a preselected stock can be ordered and grouped

    The stock requirements will require to optimize the build order so all requests are fulfilled on time. From the simplest unordered list to the
    special time prioritized lists.

    # - H A P P Y   P A T H
    @P3D05.H @P3D05.01
    Scenario: [P3D05.01] When the pending jobs request is received a new list of jobs is calculated.
        Given a clean Inventory repository
        And the following Parts in my service
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        When the Get Jobs request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of jobs has "2" records

    @P3D05.H @P3D05.02
    Scenario: [P3D05.02] When a job is completed by the user the Machine Job record is expanded with the Part information and persisted on the
    historic Jobs repository.
        Given a clean Jobs repository
        Given a clean Parts repository
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
