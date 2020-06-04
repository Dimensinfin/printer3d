@P3D05 @Jobs
Feature: The list of jobs to build to have a preselected stock can be ordered and grouped

  The stock requirements will require to optimize the build order so all requests are fulfilled on time. From the simplest unordered list to the
  special time prioritized lists.

    # - H A P P Y   P A T H
  @P3D05.H @P3D05.01
  Scenario: [P3D05.05] When the pending jobs request is received a new list of jobs is calculated.
    Given a clean Inventory repository
    And the following Parts in my service
      | id                                   | label        | material | colorCode | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO    | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE     | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    When the Get Jobs request is processed
    Then there is a valid response with return code of "200 OK"
    And the list of jobs has "2" records
