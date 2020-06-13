// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Then('the New Request dialog opens and blocks the display', function () {
    cy.get('app-root').get('mat-dialog-container').get('v1-new-request-dialog').should('exist')
});
Then('the V1NewRequestPage is activated', function () {
    cy.get('app-root').find('v1-new-request-page')
        .should('exist')
});
Then('the V1NewRequestPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .children()
        .should('have.length', 2)
});
Then('the left panel on page V1NewRequestPage is a {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(0)
        .find(panelType)
        .should('exist')
});
Then('the left panel on page V1NewRequestPage has variant {string}', function (variant: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .should('exist')
});

Then('the right panel on page V1NewRequestPage is a {string}', function (panelType: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(1)
        .find(panelType)
        .should('exist')
});
Given('the target panel is the panel with variant {string}', function (variant: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .as('target-panel')
});
Then('the target panel has a {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType)
        .should('exist')
});
Then('the target panel has one or more {string}', function (panelType: string) {
    cy.get('@target-panel').find(panelType)
        .should('have.length.greaterThan', 0)
});
Given('the target Part is one labeled {string}', function (labelContent: string) {
    cy.get('app-root').get('v1-available-parts-panel').find('v1-part').find('[name="label"]')
        .contains(labelContent, { matchCase: false }).first().parent().parent()
        .as('target-part')
});
Given('on the target Part there is a field named {string} with field name {string}', function (fieldLabel: string, fieldName: string) {
    cy.get('@target-part').find('.label').contains(fieldLabel, { matchCase: false })
        .parent()
        .find('[name="' + fieldName + '"]').should('exist')
});

// ?And on the target Part there is a field named "MATERIAL" with class "part-material"
// Undefined.Implement with the following snippet:

// Given('on the target Part there is a field named {string} with class {string}', function (string, string2) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });

// ?And on the target Part there is a field named "COLOR" with class "part-color"
// Undefined.Implement with the following snippet:

// Given('on the target Part there is a field named {string} with class {string}', function (string, string2) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });

// ?And on the target Part there is a field named "DISPONIBLE" with class "part-stockAvailable"
// Undefined.Implement with the following snippet:

// Given('on the target Part there is a field named {string} with class {string}', function (string, string2) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });
