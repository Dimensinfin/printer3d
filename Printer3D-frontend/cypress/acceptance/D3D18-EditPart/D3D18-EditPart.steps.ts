// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - E D I T I N G   D E T E C T I O N
When('field named {string} is editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('span').invoke('attr', 'cy-input-type')
        .should('exist')
});
When('field named {string} is not editable', function (fieldName: string) {
    cy.get('@target').find('[cy-name="' + fieldName + '"]').find('span').invoke('attr', 'cy-input-type')
        .should('not.exist')
});

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
    // Given the target item the "part-container" with id "9fd4337d-6a4d-47b3-a7ac-a61bd51fad39"
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
});
