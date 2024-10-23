// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps"
import { When } from "cypress-cucumber-preprocessor/steps"
import { Then } from "cypress-cucumber-preprocessor/steps"
// - SERVICE
import { SupportService } from '../../support/SupportService.support'

const supportService = new SupportService()

// - T A R G E T   S E L E C T I O N
Then('the target has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').find(tag).should('have.length', count)
})

// - F O R M S
Then('form field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]').find('[cy-field-label="' + fieldName + '"]')
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
    })
Then('form field named {string} is {string}', function (fieldName: string, state: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    let inputType: string = ''
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string)
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
})
Given('{int} is set on form field {string}', function (fieldValue: number, fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(fieldValue + '')
})
Given('empty is set on form field {string}', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear()
})
When('form checkbox named {string} is clicked', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]')
        .find('span').invoke('attr', 'cy-input-type')
        .should('exist')
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('input').click()
})
