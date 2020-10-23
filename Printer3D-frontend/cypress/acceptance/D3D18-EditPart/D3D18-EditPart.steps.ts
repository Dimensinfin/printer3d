// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();
let store: any = {};

// - E D I T I N G   D E T E C T I O N
When('field named {string} is editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('span').invoke('attr', 'cy-input-type')
        .should('exist')
});
When('field named {string} is not editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('span').invoke('attr', 'cy-input-type')
        .should('not.exist')
});
Then('field {string} stores the current value into {string}', function (fieldName: string, storeName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('[cy-field-value="' + fieldName + '"]').then(($field) => {
        cy.log('[the field {string} stores the current value into {string}]> Field text: ' + $field.text().replace('€', '').trim())
        store[storeName] = $field.text().replace('€', '').trim()
    })
})
Then('field {string} is editable and the content equals the stored value {string}', function (fieldName: string, storeName: string) {
    cy.log('[the field {string} is editable and the content equals the stored value {string}]> Store content: ' + store[storeName])
    cy.get('@target').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('[cy-field-label="' + fieldName + '"]').invoke('attr', 'cy-input-type').then(type => {
        cy.log(type as string);
        switch (type) {
            case 'input':
                cy.log('input')
                cy.get('@target-field').find('input')
                    .invoke('val').should('equal', store[storeName])
                break
            case 'select':
                cy.log('select')
                cy.get('@target-field').find('select')
                    .invoke('val').should('equal', store[storeName])
                break
            case 'textarea':
                cy.log('textarea')
                cy.get('@target-field').find('textarea')
                    .invoke('val').should('equal', store[storeName])
                break
            case 'checkbox':
                cy.log('chekbox')
                cy.get('@target-field').find('input')
                    .invoke('val').should('equal', store[storeName])
                break
        }
    })

})
Then('form field {string} is cleared', function (fieldName: number) {
    cy.get('@target-panel').find('[cy-name="' + fieldName + '"]').as('target-field')
    cy.get('@target-field').find('input').clear()
})

// - D I A L O G
Then('the dialog not closes', function () {
    console.log('[THEN] the dialog closes');
    cy.get('new-part-dialog').should('exist');
});

// - S T E P   M A C R O S
Given('an editable Part Container with id {string}', function (recordId: string) {
    // Given the target is the panel of type "catalog"
    let tag = supportService.translateTag('catalog') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // Given the target item the "part-container" with id "<parameter>"
    tag = supportService.translateTag('part-container') // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
    // Then target has an actionable image named "edit-button"
    const buttonName = 'edit-button'
    cy.get('@target').find('[cy-name="' + buttonName + '"]').should('exist')
    // And actionable image named "edit-button" is "enabled"
    cy.get('@target').find('[cy-name="' + buttonName + '"]')
        .should('have.class', 'button-enabled')
    // When target actionable image "edit-button" is clicked
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
    // After a click that changes the dom we need to reselect the target
    tag = supportService.translateTag('part-container') // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
});
Given('a duplicated New Part from Part id {string}', function (recordId: string) {
    // Given the target is the panel of type "catalog"
    let tag = supportService.translateTag('catalog') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // Given the target the "part-container" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
    tag = supportService.translateTag('part-container') // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
    // When target actionable image "expand-button" is clicked
    let buttonName = 'expand-button'
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
    // Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
    tag = supportService.translateTag('part') // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
    // When target actionable image "duplicate-button" is clicked
    buttonName = 'duplicate-button'
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
    // Then the "New Part" dialog opens and blocks the display
    tag = supportService.translateTag('New Part') // Do name replacement
    cy.get('app-root').get('mat-dialog-container').get(tag).as('target-panel').as('target')
        .should('exist')
})
Given('a Part with id {string}', function (recordId: string) {
    // Given the target is the panel of type "catalog"
    let tag = supportService.translateTag('catalog') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // Given the target item the "part-container" with id "<parameter>"
    tag = supportService.translateTag('part-container') // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
    // When target actionable image "expand-button" is clicked
    let buttonName = 'expand-button'
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
    // Given the target the "part" with id "5caaf805-f3dd-4dfe-9545-eaa3e6300da3"
    tag = supportService.translateTag('part') // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
})
