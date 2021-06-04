// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - L A T E S T   I M P L E M E N T A T I O N
Then('the page {string} is open', function (pageLink: string) {
    cy.visit('/production/editrequest/uniqueid')
})
