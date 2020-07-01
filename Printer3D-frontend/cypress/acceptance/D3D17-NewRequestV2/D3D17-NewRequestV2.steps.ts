// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - N E W E S T
Then('the target panel has a drop place named {string}', function (symbolicName: string) {
    cy.get('@target-panel').find('[cy-name="' + symbolicName + '"]').parent().within(($item) => {
        cy.get('[droppable]').should('exist')

    })
});
Then('the target panel has a panel labeled {string} named {string} and with {string} elements', function (
    fieldLabel: string, fieldName: string, elementCount: number) {
    cy.get('@target-panel').find('.field').find('.label').contains(fieldLabel, { matchCase: false })
        .parent().as('target-field')
    cy.get('@target-field').find('[cy-name="' + fieldName + '"]').should('exist')
        .find('v1-request-item').should('have.length', elementCount)
});
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').trigger('dragstart')
    cy.get('@target-panel').find('[cy-name="' + dropDestination + '"]').trigger('drop')
});
Then('the target item has a named {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .should('exist')
});
