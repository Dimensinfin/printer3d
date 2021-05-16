// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - C A T A L O G
When('form checkbox named {string} is clicked', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]')
        .find('div').invoke('attr', 'cy-input-type')
        .should('exist')
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('input').click()
})

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
// Then('the target item has a {string} tag', function (tagColor: string) {
//     const colorTag = '.' + tagColor + '-mark'
//     cy.get('@target').find(colorTag).should('exist')
// });

// - E D I T I N G
Then('the target item has a form field named {string} with label {string} and contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target-panel').get('[cy-name="' + fieldName + '"]').as('target-field')
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]')
            .contains(fieldLabel, { matchCase: false })
        // Read the type of field from the cy-input-type
        cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
            cy.log(type as string);
            // inputType = type as string
            switch (type) {
                case 'input':
                    cy.log('input')
                    cy.get('@target-field').find('input')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'select':
                    cy.log('select')
                    cy.get('@target-field').find('select')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'textarea':
                    cy.log('textarea')
                    cy.get('@target-field').find('textarea')
                        .invoke('val').should('equal', fieldValue)
                    break
                case 'checkbox':
                    cy.log('checkbox')
                    cy.get('@target-field').find('[type="checkbox"]')
                        .invoke('val').should('equal', fieldValue)
                    break
            }
        })
    });

// - A C T I V E   P A R T   T A G G I N G
Then('active {string} shows a green corner', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    const fieldName = 'active'
    const fieldValue = 'ACTIVA'
    cy.get('@target-panel').find(tag).find('[cy-field-value="' + fieldName + '"]')
        .contains(fieldValue, { matchCase: false }).first().as('target-item').parents().closest('.row').within(($item) => {
            cy.get('.corner-mark').should('have.class', 'greenyellow-mark')
        })
});
Then('inactive {string} shows an orange corner', function (symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    const fieldName = 'active'
    const fieldValue = 'FUERA PROD.'
    cy.get('@target-panel').find(tag).find('[cy-field-value="' + fieldName + '"]')
        .contains(fieldValue, { matchCase: false }).first().as('target-item').parents().closest('.row').within(($item) => {
            cy.get('.corner-mark').should('have.class', 'orangered-mark')
        })
});
// - A C T I V E   M O D E L   T A G G I N G
Then('active Model shows a violet corner', function () {
    const tag = supportService.translateTag('model') // Do name replacement
    cy.get('@target-panel').find(tag).find('.corner-mark').should('have.class', 'blueviolet-mark')
});
Then('inactive Model shows a red corner', function () {
    const tag = supportService.translateTag('model') // Do name replacement
    cy.get('@target-panel').find(tag).find('.corner-mark').should('have.class', 'red-mark')
});
Given('{int} is set on form field {string}', function (fieldValue: number, fieldName: string) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(fieldValue + '')
});

// - S T E P   M A C R O S
/**
Given the target item the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
When the target item is expanded
Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
When the target item actionable image "edit-button" is clicked
 */
Given('editing state for Part {string} on Part Container {string}', function (partId: string, containerId: string) {
    // Given the target the "part-container" with id "0972b78a-8eb7-4d53-8ada-b5ae3bfda0f2"
    let tag = supportService.translateTag('part-container')
    cy.get('@target-panel').find(tag).find('[id="' + containerId + '"]').as('target')
        .should('exist')
    // When the target item is expanded
    cy.get('@target').click()
    cy.get('@target').parents().closest('node-container').first()
        .find('.arrow-box').find('.arrow-expanded').should('exist')
    // Given the target item the "part" with id "6939c6cc-297f-48ca-8f17-25fa18c3dbc7"
    tag = supportService.translateTag('part')
    cy.get('@target-panel').find(tag).find('[id="' + partId + '"]').as('target')
        .should('exist')
    // When the target item actionable image "edit-button" is clicked
    const buttonName = 'edit-button'
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
});

// - C O N S T R A I N T   V A L I D A T I O N S
Then('field named {string} is tested for numeric constraints {float}', function (fieldName, minValue) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().should('have.class', 'ng-invalid') // validate invalid before starting test
    cy.get('@target-field').find('input').clear().type(minValue+'').should('have.class', 'ng-valid')
    const numberValue: number = supportService.generateRandomNum(minValue, minValue + 100)
    cy.get('@target-field').find('input').clear().type(numberValue + '').should('have.class', 'ng-valid')
    if ((minValue - 1) > 0) cy.get('@target-field').find('input').clear().type((minValue - 1) + '').should('have.class', 'ng-invalid')
});
