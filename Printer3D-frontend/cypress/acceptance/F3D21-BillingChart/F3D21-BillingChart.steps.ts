// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - T A R G E T   D I S P L A Y
Then('target has a {string} mark', function (tagName: string) {
    cy.get('@target').within(($target) => {
        cy.get('svg').get('.' + tagName).should('exist')
    })
});
// - B A R   C H A R T
Then('graph chart has {int} columns', function (columnCount: number) {
    cy.get('@target').find('[ngx-charts-series-vertical=""]').children().should('have.length', columnCount)
});
// - S I G N S   &   S P I N N E R S
Then('there is a sign saying {string}', function (signText: string) {
    cy.get('@target').find('[cy-name="sign"]').contains(signText, { matchCase: false })
});
// - W A I T S   &   U T I L I T I E S
When('waiting for {int} second', function (seconds: number) {
    cy.wait(seconds * 1000)
});
