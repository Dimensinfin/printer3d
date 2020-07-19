// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - T A R G E T
Then('field named {string} with label {string} has contents {string}',
    function (fieldName: string, fieldLabel: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-field-label="' + fieldName + '"]').contains(fieldLabel, { matchCase: false })
        })
        cy.get('@target').within(($item) => {
            cy.get('.label').contains(fieldLabel, { matchCase: false }).parent()
                .find('[cy-field-value="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    });
// - C O L U M N S
Then('column named {string} has contents {string}', function (fieldName: string, fieldContents: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').contains(fieldContents, { matchCase: false })
});

// - C A T A L O G
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
Then('the target item has a {string} tag', function (tagColor: string) {
    const colorTag = '.' + tagColor + '-mark'
    cy.get('@target-item').find(colorTag).should('exist')
});

// - B U T T O N S
When('the target item actionable image {string} is clicked', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]').as('target-button')
        .click()
});
Then('the actionable image named {string} is {string}', function (buttonName: string, state: string) {
    cy.log('actionable')
    if (state == 'enabled')
        cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
            .should('have.class', 'save-button-enabled')
    if (state == 'disabled')
        cy.get('@target-item').find('[cy-name="' + buttonName + '"]')
            .should('have.class', 'save-button-disabled')
});
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



Then('form field {string} is cleared', function (fieldName: number) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear()
});


Given('{int} is set on form field {string}', function (fieldValue: number, fieldName: string) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear().type(fieldValue + '')
});
