// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - S T E P   M A C R O S
Given('activate model {string} for editing', function (recordId: string) {
    // Given the target is the panel of type "catalog"
    let tag = supportService.translateTag('catalog') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
    // Given the target the "model" with id "0f789845-cdc6-48ce-a0ce-cbaf63cffab5"
    tag = supportService.translateTag('model') // Do name replacement
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
    // And target has an actionable image named "edit-button"
    const buttonName = 'edit-button'
    cy.get('@target').find('[cy-name="' + buttonName + '"]').should('exist')
    // And actionable image named "edit-button" is "enabled"
    cy.get('@target').find('[cy-name="' + buttonName + '"]')
        .should('have.class', 'button-enabled')
    // When target actionable image "edit-button" is clicked
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .scrollIntoView().click()
    // Given the target is the panel of type "model-detail"
    tag = supportService.translateTag('model-detail') // Do name replacement
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
});
