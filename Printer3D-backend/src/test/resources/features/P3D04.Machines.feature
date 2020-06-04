@P3D04 @Machines
Feature: Process the jobs scheduled on the 3D printers

  The Machines can build Parts on demand. There is one API to check the current Machine state and to request build actions.

    # - H A P P Y   P A T H
  @P3D04.H @P3D04.01
  Scenario: [P3D04.01] When we receive a new Machine list request we go to the repository and search for all the records.
    When the Get Machines request is processed
    Then there is a valid response with return code of "200 OK"

  @P3D04.H @P3D04.02
  Scenario: [P3D04.02] If one machine had a build request and the build time has elapsed then the job is completed and the number of Parts on stock
  is increased.
    Given a clean Inventory repository
    Given the following Parts in my service
      | id                                   | label        | material | colorCode | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO    | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE     | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next setup for Machine "Ender 3 Pro - B"
      | currentJobPartId                     | jobInstallmentDate | currentPartInstances |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | <yesterday>        | 1                    |
    When the Get Machines request is processed
    Then there is a valid response with return code of "200 OK"
    And the machine "Ender 3 Pro - B" has the next state
      | currentJobPartId | jobInstallmentDate | currentPartInstances |
      |                  |                    | 1                    |
    When the Get Parts request is processed
    Then there is a valid response with return code of "200 OK"
    And the item "4e7001ee-6bf5-40b4-9c15-61802e4c59ea" of the list of Parts has the next fields
      | id                                   | label        | material | colorCode | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO    | 60        | 0.65 | 2.00  | 3          | 3              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |

  @P3D04.H @P3D04.03
  Scenario: [P3D04.03] When a build start request is processed the Machine record gets updated.
    Given a clean Inventory repository
    Given the following Parts in my service
      | id                                   | label        | material | colorCode | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO    | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE     | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next setup for Machine "Ender 3 Pro - A"
      | currentJobPartId | jobInstallmentDate | currentPartInstances |
      |                  |                    | 1                    |
    When the Start Build for Part "63fff2bc-a93f-4ee5-b753-185d83a13151" for Machine "e18aa442-19cd-4b08-8ed0-9f1917821fac" request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Machines request is processed
    Then there is a valid response with return code of "200 OK"
    And the machine "Ender 3 Pro - A" has the next state
      | currentJobPartId                     | currentPartInstances |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | 1                    |

  @P3D04.H @P3D04.04
  Scenario: [P3D04.04] When a build cancel request is processed the Machine record gets updated.
    Given a clean Inventory repository
    Given the following Parts in my service
      | id                                   | label        | material | colorCode | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO    | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE     | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    And the next setup for Machine "Ender 3 Pro - A"
      | currentJobPartId                     | jobInstallmentDate | currentPartInstances |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | <today>            | 1                    |
    When the Get Machines request is processed
    Then there is a valid response with return code of "200 OK"
    And the machine "Ender 3 Pro - A" has the next state
      | currentJobPartId                     | currentPartInstances |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | 1                    |
    When the Cancel Build for Machine "e18aa442-19cd-4b08-8ed0-9f1917821fac" request is processed
    Then there is a valid response with return code of "200 OK"
    When the Get Machines request is processed
    Then there is a valid response with return code of "200 OK"
    And the machine "Ender 3 Pro - A" has the next state
      | currentJobPartId | jobInstallmentDate | currentPartInstances |
      |                  |                    | 1                    |
