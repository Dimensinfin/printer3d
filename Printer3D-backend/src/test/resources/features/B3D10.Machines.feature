@B3D10 @Machines
Feature: [STORY] Manage the endpoints and the relations that have effect to the machines.

    The Machines can build Parts on demand. There is one API to check the current Machine state.
    A build job can be sent to a Machine. Build jobs are for a single part or a set of identical parts.
    An started job consumes plastic from the coil that whose material matches the Part's finishing.
    Jobs have a duration that depends on the Part and on the number of copies requested.
    Once a job is completed the user can decide if to complete it. Completion will update the list of part with the new items.
    If the user decides not to accept the job, it is cleared and the Machine is available gain.
    During the build process the user can cancel the production at any time. Cancellation requires a confirmation dialog.
    If a Job is completed and accepted then a copy is persisted on the Job history repository for analysis.

    Background:
        Given a clean Parts repository
        Given a clean Coils repository

    # - H A P P Y   P A T H
#    @P3D06.H @P3D06.01
#    Scenario: [P3D06.01] When we receive a new MachineV2 list request we go to the repository and search for all the records.
#        When the Get Machines V2 request is processed
#        Then there is a valid response with return code of "200 OK"
#
#    @P3D06.H @P3D06.02
#    Scenario: [P3D06.02]

        # - E X C E P T I O N S
    @B3D10.E @B3D10.E.01
    Scenario: [B3D10.E.01] When a build start request is processed and during the process to subtract the material required to complete the job the
    request founds that there is no more available material or this material is exhausted.
        Given the following Parts in my service
            | id                                   | label        | material | color  | weight | buildTime | cost | price | stockLevel | stockAvailable | imagePath              | modelPath  | active | description                                                                                                   |
            | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | Covid-19 Key | PLA      | BLANCO | 5      | 60        | 0.65 | 2.00  | 3          | 0              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
            | 63fff2bc-a93f-4ee5-b753-185d83a13151 | Covid-19 Key | PLA      | VERDE  | 5      | 60        | 0.65 | 2.00  | 3          | 2              | https://ibb.co/3dGbsRh | pieza3.STL | true   | This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons. |
        And the following Coils in my service
            | id                                   | material | color  | weight |
            | 3bcb9a1f-fa2e-42a9-8a66-d05a7453a61e | TPU      | ROJO   | 500    |
            | 6aee8cff-6d33-43d9-99eb-4b86800fa0dd | TPU      | BLANCO | 500    |
            | 49f72da1-051c-437c-b9d1-b81e298b156d | TPU      | NEGRO  | 500    |
            | 55ad0b77-dd63-4ea3-804d-2f384074def9 | PLA      | GRIS   | 500    |
            | 2e5dd268-0940-454a-918b-58b4dfd1a308 | PLA      | BLANCO | 5      |
        And the next setup for Machine "Ender 3 Pro - A"
            | currentJobPartId | jobInstallmentDate | currentPartInstances |
            |                  |                    | 1                    |
        And the next setup for Machine "Ender 3 Pro - B"
            | currentJobPartId | jobInstallmentDate | currentPartInstances |
            |                  |                    | 1                    |
        And the next Job Request request
            | jobId                                | partId                               | copies |
            | ed36cdfb-e5ae-4275-a163-63b4be4d952c | 4e7001ee-6bf5-40b4-9c15-61802e4c59ea | 2      |
        # - Process the request
        When the Start Build V2 for Machine labeled "Ender 3 Pro - A" request is processed
        Then there is a exception response with return code of "412 PRECONDITION_FAILED"
        And the exception response name is "MISSING_MATERIAL_TO_COMPLETE_JOB"
        And the exception response has the message "Not enough Material or no coil available to start the job."
        And the exception response has the cause "No enough material or no coil while performing the material use before starting a job."
