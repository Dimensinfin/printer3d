// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';
// - DOMAIN
import { V1PagePath } from '../../support/page-objects/V1PagePath.panel';
import { V1Dock } from '../../support/page-objects/V1Dock.panel';
import { V1Feature } from '../../support/page-objects/V1Feature.panel';
import { GridRow } from '../../support/page-objects/GridRow.panel';

const supportService = new SupportService();

// - N E W E S T   I M P L E M E N T A T I O N









// - A C C E P T A N C E
// Then('on the target panel there is one {string}', function (panelType: string) {
//     cy.get('@target-panel').find(panelType).should('have.length', 1)
// });
// Then('on the target panel there are one {string}', function (panelType: string) {
//     cy.get('@target-panel').find(panelType).should('have.length', 1)
// });
// Then('on the target panel there are one or more {string}', function (panelType: string) {
//     cy.get('@target-panel').find(panelType).should('have.length.greaterThan', 0)
// });
Then('the target Machine has no instances of {string}', function (panelName: string) {
    cy.get('@target-machine').find(panelName).should('not.exist')
});
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



// - R E V I E W



// - C O M M O N   T O   S O M E   F E A T U R E S
Given('the target panel is the panel with variant {string}', function (variant: string) {
    cy.get('@target-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .as('target-panel')
});
Then('there is a Notification panel', function () {
    cy.get('#toast-container').should('exist')
});

// - O B S O L E T E
Given('one instance of PagePath', function () {
    console.log('[GIVEN] one instance of PageTitle');
    const pageTitle: V1PagePath = new V1PagePath();
    expect(pageTitle).to.not.be.null;
    cy.get('app-root').find('v1-page-path-panel').should('have.length', 1)
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




// - PAGE ACTIVATION & CONTENTS
Then('the target page has one panel of type {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).should('exist')
});
Then('the target page has one panel of type {string} with variant {string}', function (symbolicName: string, variant: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-page').find(tag).get('[ng-reflect-variant="' + variant + '"]').first().should('exist')
});





// - ITEMS

// - DRAG & DROP
When('the mouse enter the target item', function () {
    cy.get('@target-item').trigger('mouseenter')
});
// Given('the drag source the {string} with id {string}', function (renderName: string, recordId: string) {
//     const tag = supportService.translateTag(renderName) // Do name replacement
//     cy.log('>[translation]> ' + renderName + ' -> ' + tag)
//     cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('drag-source')
//         .should('have.prop', 'draggable')
//         .should('exist')
// });
