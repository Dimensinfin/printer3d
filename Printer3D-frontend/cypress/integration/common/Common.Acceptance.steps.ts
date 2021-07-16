// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// Then('the target Machine has no instances of {string}', function (panelName: string) {
//     cy.get('@target-machine').find(panelName).should('not.exist')
// });
Then('the target panel button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    if (buttonState == 'disabled')
        cy.get('@target-panel').get('[disabled]')
            .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
    else
        cy.get('@target-panel').get('[cy-name="' + buttonName + '"]')
            .contains(buttonLabel, { matchCase: false })
});
Then('the target item button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    if (buttonState == 'disabled')
        cy.get('@target-item').get('[disabled]')
            .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
    else
        cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
            .contains(buttonLabel, { matchCase: false })
});

// - C O M M O N   T O   S O M E   F E A T U R E S
Given('the target panel is the panel with variant {string}', function (variant: string) {
    cy.get('@target-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .as('target-panel')
});
Then('there is a Notification panel', function () {
    cy.get('#toast-container').should('exist')
});
Then('there is a dialog title saying {string}', function (dialogLabel) {
    console.log('[THEN] there is a dialog title saying {string}');
    cy.get('mat-dialog-container').find('header').find('div').contains(dialogLabel, { matchCase: false })
});

Then('there is one field called {string} with the label {string}', function (fieldId, label) {
    console.log('[THEN] there is one field called {string} with the label {string}');
    cy.get('mat-dialog-container').find('tr').find(fieldId).should('have.length', 1)
    cy.get('mat-dialog-container').find('tr').find('label').contains(label, { matchCase: false })
});

When('the target form is the New Part dialog', function () {
    cy.get('app-root').get('new-part-dialog').as('target-form')
});

Then('on target input form there is a field named {string} with name {string} and content up to {string} characters',
    function (fieldLabel: string, fieldName: string, maxLength: number) {
        cy.get('@target-form').find('.column').as('target-field')
        cy.get('@target-field').find('label').contains(fieldLabel)
        // Generate content
        const service = new IsolationService()
        const testContent = service.generateRandomString(maxLength + 1)
        cy.get('@target-field').find('[name="' + fieldName + '"]')
            .clear().type(testContent)
        // Check that field trimms input
        cy.get('@target-field').find('[name="' + fieldName + '"]').then(($field) => {
            const value = $field.text()
            cy.log('[on target input form there is a field named {string} with name {string} and content up to {string} characters]> Field text: ' + value)
            // expect(value.length).to.equal(maxLength)
        })
    });

Given('the InventoryPartListPage at route {string}', function (route: string) {
    cy.visit(route)
});
When('the mouse enter the target item', function () {
    cy.get('@target-item').trigger('mouseenter')
});
