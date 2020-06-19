// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';

Then('the target machine has no Job assigned', function () {
    cy.get('@target-item').get('v1-part')
        .should('not.exist')
});
Given('the target job the {string} with id {string}', function (itemType: string, recordId: string) {
    cy.get('@target-panel').find(itemType).find('[id="' + recordId + '"]').as('target-item').as('target-job')
        .should('exist')
});
Given('the target machine the {string} with id {string}', function (itemType: string, recordId: string) {
    cy.get('@target-panel').find(itemType).find('[id="' + recordId + '"]').as('target-item').as('target-machine')
        .should('exist')
});
When('the target Job is dragged and dropped on the target Machine', function () {
    cy.get('@target-job').trigger('dragstart')
    cy.get('@target-machine').find('[cy-name="drop-job"]').trigger('drop')
});
Then('the target machine has one Job assigned with id {string}', function (recordId: string) {
    cy.get('@target-machine').find('v1-pending-job').get('[id="' + recordId + '"]')
    .should('exist')
});
