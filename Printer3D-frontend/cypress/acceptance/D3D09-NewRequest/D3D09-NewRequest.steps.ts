// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps"
import { When } from "cypress-cucumber-preprocessor/steps"
import { Then } from "cypress-cucumber-preprocessor/steps"
// - SERVICES
import { SupportService } from '../../support/SupportService.support'

const supportService = new SupportService()

// - N E W E S T
Given('the target has a panel labeled {string}', function (fieldName: string) {
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="' + fieldName + '"]').as('target')
    })
})
Then('the active page is set to Dasboard', function () {
    cy.visit('/')
})
Then('the Request is persisted at the backend', function () {
    cy.log('The Request is being persisted at the backend.')
})

// - M A C R O   S T E P S
When('the part {string} is dropped into drop {string}', function (targetPartId: string, dropDestinaton: string) {
    // Given the target is the panel of type "available-request-elements"
    const symbolicName = "available-request-elements"
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[tag replacement]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // And the drag source the "part" with id "d8addef6-0da0-4d65-9583-7a9276a42012"
    const tagPart = supportService.translateTag("part") // Do name replacement
    cy.get('@target').find(tagPart).find('[id="' + targetPartId + '"]').as('drag-source')
        .should('have.prop', 'draggable')
        .should('exist')
    // Given the target is the panel of type "new-request"
    const tagRequest = supportService.translateTag("new-request") // Do name replacement
    cy.get('@target-page').find(tagRequest)
        .as('target-panel').as('target')
    // And the target has a drop place named "dropContents"
    cy.get('@target').find('[cy-name="' + "dropContents" + '"]').should('exist')
    // # - Drag a part to the drop contents
    // When the drag source is dragged to the drop destination "dropContents"
    cy.get('@drag-source').scrollIntoView().trigger('dragstart')
    cy.get('@target').find('[cy-name="' + "dropContents" + '"]').trigger('drop')
})

// - T A R G E T   C O N T E N T S
// - I N P U T   F I E L D S
Given('{string} is set on form field {string}', function (fieldValue: string, fieldName: string) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        switch (type) {
            case 'input':
                cy.get('@target-field').find('input').clear().type(fieldValue)
                break
            case 'textarea':
                cy.get('@target-field').find('textarea').clear().type(fieldValue)
                break
            case 'select':
                cy.log('select')
                cy.get('@target-field').find('select').select(fieldValue)
                break
        }
    })
})

// - C O N T E N T S
Then('the target panel has a panel labeled {string} named {string}',
    function (fieldLabel: string, fieldName: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('content-panel')
        cy.get('@target-panel').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
    })
Then('the target panel input field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string)
        let stateClass = 'ng-valid'
        if (state == 'invalid') stateClass = 'ng-invalid'
        if (state == 'valid') stateClass = 'ng-valid'
        if (state == 'indiferent') stateClass = 'dsf-input'
        switch (type) {
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
    })
})
When('{string} is set on the target form field {string}', function (value: string, fieldName: string) {
    cy.get('@target-form').find('[name="' + fieldName + '"]').clear().type(value)
})
