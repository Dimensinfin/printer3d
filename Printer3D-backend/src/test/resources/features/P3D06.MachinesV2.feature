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

#  @P3D06.H @P3D06.02
#  Scenario: [P3D06.02] If one machine had a build request and the build time has elapsed then the job is completed and the number of Parts on stock
#  is increased.
#    Given a clean Inventory repository
#    Given the following Parts in my service
