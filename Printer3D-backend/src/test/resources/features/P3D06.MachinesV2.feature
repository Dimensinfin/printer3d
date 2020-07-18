@P3D06 @Machines
Feature: Manage the information about the production 3D printer Machines.

    The Machines can build Parts on demand. There is one API to check the current Machine state and to request build actions.
    The build jobs expire after the completion time updating the stock inventories automatically.
    The User can also cancel the job or even stop from the result being updated at the inventory.
    The Machines api is already on version 2 so there are two sets of tests and of implementations.

    # - H A P P Y   P A T H
    @P3D06.H @P3D06.01
    Scenario: [P3D06.01] When we receive a new MachineV2 list request we go to the repository and search for all the records.
        When the Get Machines V2 request is processed
        Then there is a valid response with return code of "200 OK"

    @P3D06.H @P3D06.02
    Scenario: [P3D06.02] Check that all the machines are IDLE and check their configurations.
        Given a clean Parts repository
        Given the following Parts in my service
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId | jobInstallmentDate | currentPartInstances |
            |                  |                    | 1                    |
        And the next setup for Machine "Ender 3 Pro - B"
            | currentJobPartId | jobInstallmentDate | currentPartInstances |
            |                  |                    | 1                    |
        When the Get Machines V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the machine "Ender 3 Pro - A" has the next build information
            | state | partId | partLabel | partCopies | jobInstallmentDate | remainingTime |
            | IDLE  |        |           | 1          |                    | 0             |
        And the machine "Ender 3 Pro - B" has the next build information
            | state | partId | partLabel | partCopies | jobInstallmentDate | remainingTime |
            | IDLE  |        |           | 1          |                    | 0             |

    @P3D06.H @P3D06.03
    Scenario: [P3D06.03] When a build start request is processed the Machine record gets updated.
        Given a clean Parts repository
        Given the following Parts in my service
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId | jobInstallmentDate | currentPartInstances |
            |                  |                    | 1                    |
        And the next setup for Machine "Ender 3 Pro - B"
            | currentJobPartId | jobInstallmentDate | currentPartInstances |
            |                  |                    | 1                    |
        And the next Job Request request
            | jobId                                | partId                               | copies |
            | ed36cdfb-e5ae-4275-a163-63b4be4d952c | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | 1      |
        When the Start Build V2 for Machine "e18aa442-19cd-4b08-8ed0-9f1917821fac" request is processed
        Then there is a valid response with return code of "200 OK"
        When the Get Machines V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the machine "Ender 3 Pro - A" has the next build information
            | state   | partId                               | partLabel    | partCopies | remainingTime |
            | RUNNING | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | 1          | 3600          |
        And the machine "Ender 3 Pro - B" has the next build information
            | state | partId | partLabel | partCopies | jobInstallmentDate | remainingTime |
            | IDLE  |        |           | 1          |                    | 0             |

    @P3D06.H @P3D06.04
    Scenario: [P3D06.04] If one machine had a build request and the build time has elapsed then the job is left completed at the Machine and the
    machine slot continues to be in use.
        Given a clean Parts repository
        Given the following Parts in my service
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId                     | jobInstallmentDate          | currentPartInstances |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | 2020-06-18T10:00:00.226181Z | 1                    |
        And the next setup for Machine "Ender 3 Pro - B"
            | currentJobPartId                     | jobInstallmentDate          | currentPartInstances |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | 2020-06-18T10:00:00.226181Z | 1                    |
        When the Get Machines V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the machine "Ender 3 Pro - A" has the next build information
            | state   | partId                               | partLabel    | jobInstallmentDate          | partCopies | remainingTime |
            | RUNNING | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | 2020-06-18T10:00:00.226181Z | 1          | 0             |
        And the machine "Ender 3 Pro - B" has the next build information
            | state   | partId                               | partLabel    | jobInstallmentDate          | partCopies | remainingTime |
            | RUNNING | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | 2020-06-18T10:00:00.226181Z | 1          | 0             |

    @P3D06.H @P3D06.05
    Scenario: [P3D06.05] If the Machine receives a Complete Job request then the machine job is completed, the Part stock updated, persisted on the
    Job repository and the Machine is ready to accept new Jobs.
        Given a clean Parts repository
        Given the following Parts in my service
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId                     | jobInstallmentDate          | currentPartInstances |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | 2020-06-18T10:00:00.226181Z | 1                    |
        When the Complete Job request for Machine "e18aa442-19cd-4b08-8ed0-9f1917821fac" is processed
        Then there is a valid response with return code of "200 OK"
        When the Get Machines V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the machine "Ender 3 Pro - A" has the next build information
            | state | partId | partLabel | jobInstallmentDate | partCopies | remainingTime |
            | IDLE  |        |           |                    | 1          | 0             |
        When the Get Parts request is processed
        Then there is a valid response with return code of "200 OK"
        And the list of Parts has "2" items
        And the item "4e7001ee-6bf5-40b4-9c15-61802e4c59ea" of the list of Parts has the next fields
            | id                                   | label        | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 1              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

    @P3D06.H @P3D06.06
    Scenario: [P3D06.06] The Machine now accepts jobs for more than a single Part production. The number of copies comes from the front end in a
    new endpoint V2. Validate that the correct information is persisted on the Machine.
        Given a clean Parts repository
        Given the following Parts in my service
            | id                                   | label            | material | color  | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key     | PLA      | BLANCO | 60        | 0.65 | 2.00  | 3          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Boquilla Ganesha | PLA      | VERDE  | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId | jobInstallmentDate | currentPartInstances | state |
            |                  |                    | 1                    | IDLE  |
        And the next Job Request request
            | jobId                                | partId                               | copies |
            | ed36cdfb-e5ae-4275-a163-63b4be4d952c | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | 3      |
        When the Start Build V2 for Machine "e18aa442-19cd-4b08-8ed0-9f1917821fac" request is processed
        Then there is a valid response with return code of "200 OK"
        # - Validate the machine state
        When the Get Machines V2 request is processed
        Then there is a valid response with return code of "200 OK"
        And the machine "Ender 3 Pro - A" has the next build information
            | state   | partId                               | partLabel    | partCopies | remainingTime |
            | RUNNING | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | 3          | 10800         |

    @P3D06.H @P3D06.07
    Scenario: [P3D06.07]
