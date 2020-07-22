// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - L A T E S T   I M P L E M E N T A T I O N
Given('a hover on the target', function () {
    cy.get('@target').trigger('mouseenter')
});
Given('the target is the panel of name {string}', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]')
        .as('target-panel').as('target')
});
Given('the target has no {string} button', function (buttonName: string) {
    cy.get('@target').get('[cy-name="' + buttonName + '"]')
        .should('not.exist')
});

// - T A R G E T   C O N T E N T S
Then('target has a panel labeled {string} named {string}',
    function (fieldLabel: string, fieldName: string) {
        cy.get('@target').get('[cy-name="' + fieldName + '"]').as('target-panel')
        cy.get('@target').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
    });
// - D I A L O G
Then('the dialog closes', function () {
    cy.get('@target-dialog').should('not.exist');
});
// -  D O C K
Then('the target page refreshes', function () {
});


// --- FROM C13
// - T A R G E T   S E L E C T I O N
Given('the target the {string} with id {string}', function (symbolicName: string, recordId: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
});

// - T A R G E T   C O N T E N T S
Then('the target has the title {string}', function (title: string) {
    cy.get('@target').find('.panel-title').contains(title, { matchCase: false })
});
Then('the target has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').find(tag).should('have.length', count)
});
Then('field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target').within(($item) => {
            cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
                .find('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    });
Then('the target has a drop place named {string}', function (dropName: string) {
    cy.get('@target').find('[cy-name="' + dropName + '"]').should('exist')
});
Then('the panel {string} has no {string}', function (targetName: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="' + targetName + '"]').find(tag).should('not.exist')
    })
});
// Then('the target has no {string}', function (symbolicName: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.get('@target').within(($item) => {
//         cy.get('button').should('not.exist')
//     })
// });
// Then('the target has {int} {string}', function (count: number, symbolicName: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
//     cy.get('@target').within(($item) => {
//         cy.get(tag).should('have.length', count)
//     })
// });
// Then('the button with name {string} has a label {string} and is {string}', function (
//     buttonName: string, buttonLabel: string, buttonState: string) {
//     if (buttonState == 'disabled')
//         cy.get('@target').get('[disabled]')
//             .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
//     else
//         cy.get('@target').get('[cy-name="' + buttonName + '"]')
//             .contains(buttonLabel, { matchCase: false })
// });

// // - D R A G   &   D R O P
// Given('the drag source the {string} with id {string}', function (symbolicName: string, recordId: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
//     cy.get('@target').find(tag).find('[id="' + recordId + '"]').as('drag-source')
//         .should('have.prop', 'draggable')
//         .should('exist')
// });
// When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
//     cy.get('@drag-source').trigger('dragstart')
//     cy.get('@target').find('[cy-name="' + dropDestination + '"]').trigger('drop')
// });



// -----------------------
Given('in the target item there is a {string} button', function (buttonName: string) {
    cy.get('@target-item').get('[cy-name="' + buttonName + '"]')
        .should('exist')
});
When('there is a click on the target panel {string} button', function (buttonName: string) {
    cy.get('@target-panel').get('[cy-name="' + buttonName + '"]')
        .click('center')
});
Then('the Request is updated on the backend', function () {
    cy.log('>[the Request is updated on the backend]')
});
Then('the active page is set to Dasboard', function () {
    cy.visit('/')
});
Then('on the target panel there is one {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length', 1)
});
Then('the target item has a field named {string} with value {string}', function (fieldName: string, fieldValue: string) {
    cy.get('@target-item').within(($item) => {
        cy.get('[cy-name="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
    })
});
