// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - E D I T I N G   D E T E C T I O N
Then('field named {string} is editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').invoke('attr', 'cy-input-type')
        .should('exist')
});
Then('field named {string} is not editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').invoke('attr', 'cy-input-type')
        .should('not.exist')
});




// When('the target item actionable image {string} is clicked', function (buttonName: string) {
//     cy.get('@target-item').find('[cy-name="' + buttonName + '"]').as('target-button')
//         .click()
// });







// Then('the target item input field named {string} is editable', function (fieldName: string) {
//     cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
//         .find('input').should('exist')
// });

// Then('the target item input field named {string} is editable', function (fieldName: string) {
//     cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
//         .find('input').should('exist')
// });
// Then('the target item text field named {string} is editable', function (fieldName: string) {
//     cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
//         .find('textarea').should('exist')
// });
// Then('new the target item text field named {string} is editable', function (fieldName: string) {
//     cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
//         .find('textarea').should('exist')
// });
