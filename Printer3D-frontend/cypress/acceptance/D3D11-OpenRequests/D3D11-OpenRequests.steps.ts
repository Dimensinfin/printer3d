// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Then('the V1OpenRequestsPage is activated', function () {
    cy.get('app-root').find('v1-open-requests-page').as('target-page')
        .should('exist')
});
Then('the V1OpenRequestsPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-open-requests-page').find('.row').first()
        .children()
        .should('have.length', panelCount)
});
Then('the target item has a mark {string}', function (markname: string) {
    cy.get('@target-item').parent().find('[cy-field-mark="REQUEST"]').get('.' + markname).should('exist')
});
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
