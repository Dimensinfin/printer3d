// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

When('the target item actionable image {string} is clicked', function (buttonName: string) {
    cy.get('@target-item').find('[cy-name="' + buttonName + '"]').as('target-button')
        .click()
});
