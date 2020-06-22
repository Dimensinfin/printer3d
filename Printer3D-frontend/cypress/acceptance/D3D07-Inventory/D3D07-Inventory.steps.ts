// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();
let store: any = {};

Then('the V2InventoryPartListPage is activated', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').should('exist');
});

Then('one instance of ViewerPanel', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel').should('exist');
});

Then('one or more instances of NodeContainer', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel').find('node-container').should('exist');
});

Then('the first NodeContainer contains a Part Container Render', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part-container').should('exist');
});

Then('on the v1-part-container component there is a field named {string} with class {string}', function (fieldName: string, fieldClass: string) {
    const fieldClassIdentifier = '.' + fieldClass
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').first().find('v1-part-container')
        .find('.field').as('target-partcontainer-field')
        .find(fieldClassIdentifier).should('exist')
    cy.get('@target-partcontainer-field').find('.label').contains(fieldName)
    cy.get('@target-partcontainer-field').find(fieldClassIdentifier).should('exist')
});

Then('there is a loading panel displaying {string}', function (downloadMessage: string) {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('.index-loading').contains(downloadMessage)
});

Then('on the v1-part-container component there is a right arrow', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('.arrow-box')
        .find('.right-arrow').should('exist')
});

When('the right arrow is clicked', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('.arrow-box')
        .find('.right-arrow').first().click()
});
Then('the container expands and there are one or more v1-part nodes', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part').should('exist');
});

Then('on the v1-part component there is a field named {string} with class {string}', function (fieldName: string, fieldClass: string) {
    const fieldClassIdentifier = '.' + fieldClass
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part').first()
        .find('.field').as('target-part-field')
        .find(fieldClassIdentifier).should('exist')
    cy.get('@target-part-field').find('.label').contains(fieldName)
    cy.get('@target-part-field').find(fieldClassIdentifier).should('exist')
});
Then('the field {string} is editable', function (fieldName: string) {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part').find('.field').as('target-part-field')
        .find('.label').contains(fieldName)
    cy.get('@target-part-field').find('input').should('exist')
});

When('the first v1-part is selected as the target', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container').find('v1-part')
        .first().as('target-part')
});

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

// When('any Part shows a Save button a the right', function () {
//     cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
//         .find('node-container')
//         .find('v1-part')
//         .find('.save-modifications').should('exist')
// });

// Then('the target Part shows a Edit button at the right', function () {
// });
// Then('any Part shows an Edit button at the right', function () {
//     cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
//         .find('node-container')
//         .find('v1-part').first().within(($part) => {
//         cy.get('.edit-attributes').should('exist')
//     });
// });

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

Then('the new part contents are persisted to the backend', function () {
    cy.log('the new part contents are persisted to the backend')
    cy.wait(1000)
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container')
        .find('v1-part').should('exist')
});


Then('the edit state is exited', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container')
        .find('v1-part').find('input').should('not.exist')
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
Then('when the target Part Container is the second', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
        .find('node-container')
        .find('v1-part-container').eq(1).as('target-part')
});
When('the right arrow of the target Part Container is clicked', function () {
    cy.get('@target-part').parent().parent().within(($panel) => {
        cy.get('.arrow-box').click('center')
    })
});
Then('active Part shows a green corner', function () {
    cy.get('v1-part').find('.field').find('[name="active"]').contains('ACTIVA')
        .parent().parent().parent().within(($panel) => {
            cy.get('.corner-mark').find('[fill="greenyellow"]').should('exist')
        })
});
Then('inactive Part shows an orange corner', function () {
    cy.get('v1-part').find('.field').find('[name="active"]').contains('FUERA PROD.')
        .parent().parent().parent().within(($panel) => {
            cy.get('.corner-mark').find('[fill="orangered"]').should('exist')
        })
});
Given('the target panel is the panel of type {string}', function (panelType: string) {
    cy.get('app-root').find(panelType)
        .as('target-panel')
});



// Then('active Parts show a green corner', function () {
//     cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
//         .find('node-container').as('target-node-active')
//         .find('v1-part')
//         .find('.field')
//         .find('.part-active').contains('ACTIVA')
//     cy.get('@target-node-active').find('.corner-bottom').should('have.class', 'active')
// });

// Then('inactive Part show an orange corner', function () {
//     cy.get('app-root').find('v2-inventory-part-list-page').find('viewer-panel')
//         .find('node-container').as('target-node-inactive')
//         .find('v1-part')
//         .find('.field')
//         .find('.part-active').contains('FUERA PROD.')
//         .parents('node-container').as('target-node-inactive')
//     cy.get('@target-node-inactive').find('.corner-bottom').should('not.have.class', 'active')
// });

// Given('the target panel is the panel named {string}', function (renderName: string) {
//     const tag = supportService.translateTag(renderName) // Do name replacement
//     cy.get('app-root').find('[cy-name="' + tag + '"]').as('target-panel')
//         .should('exist')
// });

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
Then('the {string} dialog opens and blocks the display', function (pageName: string) {
    const tag = supportService.translateTag(pageName) // Do name replacement
    cy.get('app-root').get('mat-dialog-container').get(tag).should('exist')
});
Given('the target panel is the panel named {string}', function (elementName: string) {
    cy.get('[cy-name="' + elementName + '"]').as('target-panel')
        .should('exist')
});
Then('the target panel has a input field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').find('[name="' + fieldName + '"]').parent().find('label')
            .contains(fieldLabel)
        cy.get('@target-panel').find('[name="' + fieldName + '"]')
            .should('have.value', fieldValue)
    });
Then('the target panel has a select field named {string} with label {string} and value {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').find('[name="' + fieldName + '"]').parent().find('label')
            .contains(fieldLabel)
        cy.get('@target-panel').find('[name="' + fieldName + '"]')
            .should('have.value', fieldValue)
    });
