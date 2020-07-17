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

// - T A R G E T
Given('the target is the panel of type {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.log('>[tag replacement]> ' + renderName + ' -> ' + tag)
    cy.get('@target-page').find(tag)
        .as('target-panel').as('target')
});

// - T A R G E T   C O N T E N T S
Then('the target has {int} {string}', function (count: number, symbolicName: string) {
    const tag = supportService.translateTag(symbolicName) // Do name replacement
    cy.log('>[translation]> ' + symbolicName + ' -> ' + tag)
    cy.get('@target').within(($item) => {
        cy.get(tag).should('have.length', count)
    })
});
