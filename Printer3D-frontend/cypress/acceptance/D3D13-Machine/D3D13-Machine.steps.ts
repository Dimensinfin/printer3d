// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { SupportService } from '../../support/SupportService.support';
import { find } from 'cypress/types/lodash';

const supportService = new SupportService();

// // - T A R G E T   S E L E C T I O N
// Given('the target is the panel of type {string}', function (renderName: string) {
//     const tag = supportService.translateTag(renderName) // Do name replacement
//     cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
//     cy.get('@target-page').find(tag)
//         .as('target-panel').as('target')
// });
// Given('the target the {string} with id {string}', function (symbolicName: string, recordId: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.log('>[the {string} is activated]> Translation: ' + tag)
//     cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
//         .should('exist')
// });

// - T A R G E T   C O N T E N T S
// Then('the target has the title {string}', function (title: string) {
//     cy.get('@target').find('.panel-title').contains(title, { matchCase: false })
// });
// Then('the target has {int} {string}', function (count: number, symbolicName: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
//     cy.get('@target').find(tag).should('have.length', count)
// });
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
Then('the target has a panel labeled {string} named {string}',
    function (fieldLabel: string, fieldName: string) {
        cy.get('@target').get('[cy-name="' + fieldName + '"]').as('target-panel')
        cy.get('@target').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
    });
Then('the panel {string} has no {string}', function (targetName: string, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="' + targetName + '"]').find(tag).should('not.exist')
    })
});
Then('the target has no {string}', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.get('@target').within(($item) => {
        cy.get('button').should('not.exist')
    })
});
// Then('the target has {int} {string}', function (count: number, symbolicName: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
//     cy.get('@target').within(($item) => {
//         cy.get(tag).should('have.length', count)
//     })
// });
Then('the button with name {string} has a label {string} and is {string}', function (
    buttonName: string, buttonLabel: string, buttonState: string) {
    if (buttonState == 'disabled')
        cy.get('@target').get('[disabled]')
            .get('[cy-name="' + buttonName + '"]').contains(buttonLabel, { matchCase: false })
    else
        cy.get('@target').get('[cy-name="' + buttonName + '"]')
            .contains(buttonLabel, { matchCase: false })
});

// - D R A G   &   D R O P
Given('the drag source the {string} with id {string}', function (symbolicName: string, recordId: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').find(tag).find('[id="' + recordId + '"]').as('drag-source')
        .should('have.prop', 'draggable')
        .should('exist')
});
When('the drag source is dragged to the drop destination {string}', function (dropDestination: string) {
    cy.get('@drag-source').trigger('dragstart')
    cy.get('@target').find('[cy-name="' + dropDestination + '"]').trigger('drop')
});

// - B U T T O N S
When('the button with name {string} is clicked', function (buttonName: string) {
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="' + buttonName + '"]')
            .scrollIntoView().click()
    })
    cy.wait(500)
});

// - F O R M S
Then('form field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]')
            cy.get('[cy-field-label="' + fieldName + '"]')
                .contains(fieldLabel, { matchCase: false })
            cy.get('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
                cy.log(type as string);
                switch (type) {
                    case 'input':
                        cy.log('input')
                        cy.get('input')
                            .invoke('val').should('equal', fieldValue)
                        break
                    case 'select':
                        cy.log('select')
                        cy.get('select')
                            .invoke('val').should('equal', fieldValue)
                        break
                    case 'textarea':
                        cy.log('textarea')
                        cy.get('textarea')
                            .invoke('val').should('equal', fieldValue)
                        break
                }
            })
        })
    });
Then('form field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target').get('[cy-name="' + fieldName + '"]').as('target-field')
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

// - M O V E M E N T
When('the mouse exits the target', function () {
    cy.get('@target').find('[cy-focus="mouseleave"]').trigger('mouseleave')
});

// - T I M E   C O N T R O L
Given('a timed application Printer3DManager', function () {
    cy.viewport(1400, 900)
    cy.clock()
    cy.visit('/')
});
Then('advance time {string} minutes', function (minutes: number) {
    cy.tick(minutes * 60 * 1000)
    cy.wait(1000)
});



//--------------------------------------
Then('the target machine has no Job assigned', function () {
    cy.get('@target-item').get('v1-part')
        .should('not.exist')
});
Given('the target job the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target-item').as('target-job')
        .should('exist')
});
Given('the target machine the {string} with id {string}', function (renderName: string, recordId: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target-item').as('target-machine')
        .should('exist')
});
When('the target Job is dragged and dropped on the target Machine', function () {
    cy.get('@target-job').trigger('dragstart')
    cy.get('@target-machine').find('[cy-name="drop-job"]').trigger('drop')
});
Then('the target machine has one Job assigned with id {string}', function (recordId: string) {
    cy.get('@target-machine').find('v1-pending-job').get('[id="' + recordId + '"]')
        .should('exist')
});
When('there is a click on the target item {string} button', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
        .click('center')
});
Then('the Job is started and sent to the background', function () {
    cy.log('>[the Job is started and sent to the background]')
});
Then('the {string} item has a value of {string}', function (renderName: string, timerValue: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-item').find(tag).first().contains(timerValue, { matchCase: false })
});
// TODO - replace by a timed action so the new countdown value can be verified.
Then('the build-countdown-timer item has started countdown', function () {
    cy.wait(2000)
    cy.get('@target-item').find('v1-build-countdown-timer').should('exist')
});
Then('the target Machine has {string} instances of {string}', function (elementCount: string, elementType: string) {
    cy.get('@target-machine').find(elementType).should('have.length', elementCount)
});
