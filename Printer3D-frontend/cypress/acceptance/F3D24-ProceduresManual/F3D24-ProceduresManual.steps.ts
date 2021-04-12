// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - P R O C E D U R E S   S T E P S
Given('the intercepted application Printer3DManager', function () {
    cy.viewport(1400, 900)
    cy.visit('/')
    cy.get('app-root').as('target-page').as('target-panel').as('target')
})
When('the button with name {string} is clicked', function (buttonName: string) {
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="' + buttonName + '"]').get('a')
            .invoke('removeAttr', 'target')
        cy.get('a').as('manual-link').then(link => {
            cy.request(link.prop('href'))
                .its('status')
                .should('eq', 200);

        })
    })
    cy.wait(100)
})
Then('the Procedures Manual open on a new tab', function () {
    cy.window().then((win) => {
        cy.spy(win, 'open').as('redirect')
    })
    cy.get('@manual-link').click()
    cy.get('@redirect')
        .should('be.calledWith', '_blank', '/about')
})
