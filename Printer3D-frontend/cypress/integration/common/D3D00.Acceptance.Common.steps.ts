// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const TITLE_VALIDATION = '3DPrinterManagement - UI';
const supportService = new SupportService();

// - A P P L I C A T I O N
Given('the application Printer3DManager', function () {
    cy.viewport(1400, 900)
    new IsolationService().doLandingPage(); // Load the landing page.
    cy.title().should('eq', TITLE_VALIDATION);
    cy.get('app-root').as('target-page').as('target')
});

// - T A R G E T   S E L E C T I O N
Given('the target is the panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
});
Given('the target the {string} with id {string}', function (symbolicName: string, recordId: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[the {string} is activated]> Translation: ' + tag)
    cy.get('@target-panel').find(tag).find('[id="' + recordId + '"]').as('target')
        .should('exist')
});

// - T A R G E T
When('the target is clicked', function () {
    cy.get('@target').click()
});

// - T A R G E T   C O N T E N T S
Then('the target has the title {string}', function (title: string) {
    cy.get('@target').find('.panel-title').contains(title, { matchCase: false })
});
Then('the target has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').within(($item) => {
        cy.get(tag).should('have.length', count)
    })
});

// - I M A G E   B U T T O N S
Then('the target has an actionable image named {string}', function (buttonName: string) {
    cy.get('@target').find('[cy-name="' + buttonName + '"]').should('exist')
});
When('the target item actionable image {string} is clicked', function (buttonName: string) {
    cy.get('@target').find('[cy-name="' + buttonName + '"]').as('target-button')
        .click()
});
Then('actionable image named {string} is {string}', function (buttonName: string, state: string) {
    cy.log('actionable')
    if (state == 'enabled')
        cy.get('@target').find('[cy-name="' + buttonName + '"]')
            .should('have.class', 'button-enabled')
    if (state == 'disabled')
        cy.get('@target').find('[cy-name="' + buttonName + '"]')
            .should('have.class', 'button-disabled')
});
// - A L T E R N A T E   B A C K E N D   R E S P O N S E S
Given('response {string} for {string}', function (responseCode: string, endpoint: string) {
    const tag = supportService.translateTag(endpoint) // Do name replacement
    cy.setCookie(tag, responseCode)
});
// - N O T I F I C A T I O N S
Then('there is a {string} Notification panel', function (string) {
    cy.get('#toast-container').should('exist')
});
