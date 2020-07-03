// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

When('the target item actionable image {string} is clicked', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]').as('target-button')
        .click()
});
Then('the field named {string} of the target item is editable', function (fieldName: string) {
    cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
    .find('input').should('exist')
});







Then('the target item input field named {string} is editable', function (fieldName: string) {
    cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
        .find('input').should('exist')
});

// Then('the target item input field named {string} is editable', function (fieldName: string) {
//     cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
//         .find('input').should('exist')
// });
// Then('the target item text field named {string} is editable', function (fieldName: string) {
//     cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
//         .find('textarea').should('exist')
// });
Then('new the target item text field named {string} is editable', function (fieldName: string) {
    cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
        .find('textarea').should('exist')
});
