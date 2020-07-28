// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - H O V E R I N G
Given('a hover on the target', function () {
    cy.get('@target').trigger('mouseenter')
});
// - T A R G E T   M A R K S
Then('the target item has a mark {string}', function (markname: string) {
    cy.get('@target').parent().find('[cy-field-mark="REQUEST"]').get('.' + markname).should('exist')
});

// - T A R G E T   S E L E C T I O N
Then('on the target panel there is one {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length', 1)
});
Then('on the target panel there are one {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length', 1)
});
Then('on the target panel there are one or more {string}', function (renderName: string) {
    const tag = supportService.translateTag(renderName) // Do name replacement
    cy.get('@target-panel').find(tag).should('have.length.greaterThan', 0)
});
