// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - E X T R A C T I O N S   S T E P S
Then('the extraction link points to {string}', function (link: string) {
    cy.get('@target').get('a').last()
        .invoke('attr', 'href')
        .should('eq', link)
})
Then('the extraction has label {string}', function (label: string) {
    cy.get('@target').within(($item) => {
        cy.get('[cy-name="target-link"]').contains(label, { matchCase: false })
    })
})
