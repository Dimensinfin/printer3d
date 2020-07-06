// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

When('the target item is expanded', function () {
    cy.get('@target-item').click()
    cy.get('@target-item').parents().closest('node-container').first()
        .find('.arrow-box').find('.arrow-expanded').should('exist')
});
Then('the target item has a {string} tag', function (tagColor: string) {
    const colorTag = '.' + tagColor + '-mark'
    cy.get('@target-item').find(colorTag).should('exist')
});
