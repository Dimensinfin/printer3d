// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

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
// // - I M A G E   B U T T O N S
// Then('the target has a actionable image named {string}', function (buttonName: string) {
//     cy.get('@target').find('[cy-name="' + buttonName + '"]').should('exist')
// });
// When('the target item actionable image {string} is clicked', function (buttonName: string) {
//     cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
//         .click()
// });
// Then('the actionable image named {string} is {string}', function (buttonName: string, state: string) {
//     cy.log('actionable')
//     if (state == 'enabled')
//         cy.get('@target').find('[cy-name="' + buttonName + '"]')
//             .should('have.class', 'button-enabled')
//     if (state == 'disabled')
//         cy.get('@target').find('[cy-name="' + buttonName + '"]')
//             .should('have.class', 'button-disabled')
// });

// - F O R M S
Then('form field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]')            .find('[cy-field-label="' + fieldName + '"]')
                .contains(fieldLabel, { matchCase: false })
            cy.get('[cy-name="' + fieldName + '"]').find('[cy-field-label="' + fieldName + '"]')
                .invoke('attr', 'cy-input-type').then(type => {
                    cy.log(type as string)
                    cy.get('@target').get('[cy-name="' + fieldName + '"]').as('target-field')
                    switch (type) {
                        case 'input':
                            cy.get('@target-field').find('input')
                                .invoke('val').should('equal', fieldValue)
                            break
                        case 'select':
                            cy.get('@target-field').find('select')
                                .invoke('val').should('equal', fieldValue)
                            break
                        case 'textarea':
                            cy.get('@target-field').find('textarea')
                                .invoke('val').should('equal', fieldValue)
                            break
                    }
                })
        })
    });
Then('form field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    let inputType: string = ''
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string);
        inputType = type as string
    })
    let stateClass = 'ng-valid'
    if (state == 'invalid') stateClass = 'ng-invalid'
    if (state == 'valid') stateClass = 'ng-valid'
    if (state == 'indiferent') stateClass = 'dsf-input'
    if (inputType != '') {
        switch (inputType) {
            case 'input':
                cy.log('input')
                cy.get('@target-field').find('input')
                    .should('have.class', stateClass)
                break
            case 'select':
                cy.log('select')
                cy.get('@target-field').find('select')
                    .should('have.class', stateClass)
                break
            case 'textarea':
                cy.log('textarea')
                cy.get('@target-field').find('textarea')
                    .should('have.class', stateClass)
                break
        }
    }
});
Given('{int} is set on form field {string}', function (fieldValue: number, fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(fieldValue + '')
});
Given('empty is set on form field {string}', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear()
});
