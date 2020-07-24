// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();
let store: any = {};

// - N E W   B E S T   P R A C T I C E S
Then('the loading panels shows {string}', function (loadingMessage: string) {
    cy.get('@target-page').find('.index-loading')
        .contains(loadingMessage)
});
When('the loading panel completes', function () {
    cy.wait(1000)
});
Then('the target item has a list named {string} with {int} {string}', function (panelName: string, itemCount: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target-item').find('[cy-name="' + panelName + '"]').as('target-list')
        .find(tag).should('have.length', itemCount)
});
Given('the target item is expandable', function () {
    cy.get('@target').parents().closest('node-container').first()
        .find('[cy-name="expand-button"]')
        .should('exist')
});
When('the target item is expanded', function () {
    cy.get('@target').click()
    cy.get('@target').parents().closest('node-container').first()
        .find('.arrow-box').find('.arrow-expanded').should('exist')
});
Then('active {string} shows a green corner', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    const fieldName = 'active'
    const fieldValue = 'ACTIVA'
    cy.get('@target-panel').find(tag).find('[cy-field-value="' + fieldName + '"]')
        .contains(fieldValue, { matchCase: false }).first().as('target-item').parent().parent().parent().within(($item) => {
            cy.get('.corner-mark').find('[fill="greenyellow"]').should('exist')
        })
});
Then('inactive {string} shows an orange corner', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    const fieldName = 'active'
    const fieldValue = 'FUERA PROD.'
    cy.get('@target-panel').find(tag).find('[cy-field-value="' + fieldName + '"]')
        .contains(fieldValue, { matchCase: false }).first().as('target-item').parent().parent().parent().within(($item) => {
            cy.get('.corner-mark').find('[fill="orangered"]').should('exist')
        })
});
Then('the target item has a named {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .should('exist')
});
Given('the target item named button {string} is clicked', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .click()
});
Given('the target item field named {string} is editable', function (fieldName: string) {
    cy.get('@target-item').find('[cy-name="' + fieldName + '"]')
        .find('input').should('exist')
});







// ---------------------------------------------------

Then('the field {string} stores the current value into {string}', function (fieldName: string, storeName: string) {
    cy.get('@target-part')
        .find('.field').within(($panel) => {
            cy.get('[name="' + fieldName + '"]').then(($field) => {
                cy.log('[the field {string} stores the current value into {string}]> Field text: ' + $field.text())
                store[storeName] = $field.text().replace('€', '').trim()
            })
        });
});

Then('the field {string} is editable and the content equals the stored value {string}', function (fieldName: string, storeName: string) {
    cy.get('@target-part').within(($panel) => {
        cy.log('[the field {string} is editable and the content equals the stored value {string}]> Store content: ' + store[storeName])
        cy.get('[name="' + fieldName + '"]')
            .should('have.value', store[storeName])
    });
});

Then('the target Part shows an Edit button at the right', function () {
    cy.get('@target-part').within(($part) => {
        cy.get('.edit-attributes').should('exist')
    });
});


Then('the target Part shows a Save button at the right', function () {
    cy.get('@target-part').within(($part) => {
        cy.get('.save-modifications').should('exist')
    });
});

When('the target Part Edit button is clicked', function () {
    cy.get('@target-part').within(($part) => {
        cy.get('.edit-attributes').click()
    });
});

When('the target Part Save button is clicked', function () {
    cy.get('@target-part').within(($part) => {
        cy.get('.save-modifications').click()
    });
});


Then('the target Part field {string} stores the current value into {string}', function (fieldName: string, storeName: string) {
    cy.get('@target-part')
        .find('.field').within(($panel) => {
            cy.get('[name="' + fieldName + '"]').then(($field) => {
                cy.log('[the field {string} stores the current value into {string}]> Field text: ' + $field.text())
                store[storeName] = $field.text().replace('€', '').trim()
            })
        });
});

Then('the target Part field {string} equals the stored value {string}', function (fieldName: string, storeName: string) {
    cy.get('@target-part').within(($panel) => {
        cy.log('[the field {string} is editable and the content equals the stored value {string}]> Store content: ' + store[storeName])
        cy.get('[name="' + fieldName + '"]')
            .should('have.value', store[storeName])
    });
});

Then('the target Part field {string} is changed to {string}', function (fieldName: string, newValue: string) {
    cy.get('@target-part')
        .find('.field').within(($panel) => {
            cy.get('[name="' + fieldName + '"]').clear().type(newValue)
        });
});
When('the right arrow of the target Part Container is clicked', function () {
    cy.get('@target-part').parent().parent().within(($panel) => {
        cy.get('.arrow-box').click('center')
    })
});



Then('the target item is expandable', function () {
    cy.get('@target-item').parent().parent().parent().parent().parent()
        .find('[cy-name="expand-button"]')
        .should('exist')
});
When('the target item expand-collapse button is clicked', function () {
    cy.get('@target-item').parent().parent().parent().parent().parent()
        .find('[cy-name="expand-button"]')
        .click()
});
Then('the target item has {string} childs of type {string}', function (childCount: string, renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-item').children().find(tag).should('have.length', childCount)
});
Then('on the target panel there are none {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('not.exist')
});
Then('on the target panel there are one or more {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length.greaterThan', 0)
});
Then('on the target panel there are {string} {string}', function (itemCount: number, renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length', itemCount)
});
When('the target Part Duplicate button is clicked', function () {
    cy.get('@target-item').find('[cy-name="duplicate-button"]')
        .click()
});
// Then('the {string} dialog opens and blocks the display', function (pageName: string) {
//     const tag = supportService.translateTag(pageName) // Do name replacement
//     cy.get('app-root').get('mat-dialog-container').as('target-page').get(tag).should('exist')
// });
// Given('the target panel is the panel named {string}', function (elementName: string) {
//     cy.get('[cy-name="' + elementName + '"]').as('target-panel')
//         .should('exist')
// });
Then('the target panel has a input field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').find('[name="' + fieldName + '"]').parent().find('label')
            .contains(fieldLabel)
        cy.get('@target-panel').find('[name="' + fieldName + '"]')
            .should('have.value', fieldValue)
    });


// - E X P E R I M E N T 
Then('the target panel has a input field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('input')
            .should('have.value', fieldValue)
    });
Then('the target panel has a textarea field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldLabel + '"]')
            .contains(fieldLabel, { matchCase: false })
        cy.get('@target-field').find('textarea')
            .should('have.value', fieldValue)
    });
Then('the target panel has a select field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').find('[name="' + fieldName + '"]').parent().find('label')
            .contains(fieldLabel)
        cy.get('@target-panel').find('[name="' + fieldName + '"]')
            .should('have.value', fieldValue)
    });
