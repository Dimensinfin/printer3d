@P3D08 @Models
Feature: [STORY] Create a new Feature to see the list of Open Requests. A request is a need from a customer of a set of parts.
  If the request cannot be completed with stock parts then it is left Open.

  This is an entity used by the customers to collect the Parts that they want to buy. The **Request** contains the list of
  specific Part models along with the material and color that are required by the customer. Requests are ordered by order of arrival. The system
  will follow the first come/first served pattern. Then Part build jobs generated by Requests have a higher priority than other jobs generated by
  inventory leveling.

  # - H A P P Y   P A T H
  @P3D08.H @P3D08.01
  Scenario: [P3D08.01] Validate the creation of a new Request with the data received.
    Given a clean Parts repository
    And the following Parts in my service
      | id                                   | label        | material | colorCode | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
      | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO    | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE     | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
    Given a clean Requests repository
    And the next Part Request List
      | partId                               | quantity |
      | 63fff2bc-a93f-4ee5-b753-185d83a13151 | 2        |
    And the next New Request request with the current Part Request List
      | id                                   | label                    | requestDate                 | state |
      | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Tests Request to be Open | 2020-06-15T20:00:00.226181Z | OPEN  |
    When the New Request request is processed
    Then there is a valid response with return code of "201 CREATED"
    Then the repository list of Resources has the next contents
      | id                                   | label                    | requestDate                 | state | partRequestList                        |
      | d8e2cc31-4a5b-4f9a-a494-ca21956e8d2a | Tests Request to be Open | 2020-06-15T20:00:00.226181Z | OPEN  | 63fff2bc-a93f-4ee5-b753-185d83a13151/2 |

#  @P3D08.H @P3D08.02
#  Scenario: [P3D08.02] When a new Request is created the return state is always Open. This state changes to Completed if there are enough Part at
#  the Inventory stock and only when the Requests is processed when the frontend requests the lists of Requests.
#
#  @P3D08.H @P3D08.03
#  Scenario: [P3D08.03] If the backend receives an update for an Open Request the request fields are updated and the request reprocessed in the same
#  order location. Requests data cannot change when the request is updated.
#
#  @P3D08.H @P3D08.04
#  Scenario: [P3D08.04] Check that Requests are processed in the FirstIn/FirstOut order when returned to the client frontend.
#
#  @P3D08.H @P3D08.05
#  Scenario: [P3D08.05] When a new request cannot be completed because there are not enough Parts at the Inventory the jobs generated to complete the
#  Request have priority 1.
#
#  @P3D08.H @P3D08.06
#  Scenario: [P3D08.06] When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
