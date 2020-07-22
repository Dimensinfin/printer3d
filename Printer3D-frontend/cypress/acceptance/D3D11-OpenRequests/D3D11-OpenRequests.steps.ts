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
/** Add some time to the page activation */
// Then('the page {string} is activated', function (symbolicName: string) {
//     const tag = supportService.translateTag(symbolicName) // Do name replacement
//     cy.log('>[the {string} is activated]> Translation: ' + tag)
//     cy.wait(1000)
//     cy.get('app-root').find(tag).as('target-page')
//         .should('exist')
//     cy.get('app-root').find(tag).as('target')
//         .should('exist')
// });


// Then('the V1OpenRequestsPage is activated', function () {
//     cy.get('app-root').find('v1-open-requests-page').as('target-page')
//         .should('exist')
// });
// Then('the V1OpenRequestsPage has {int} panels', function (panelCount: number) {
//     cy.get('app-root').find('v1-open-requests-page').find('.row').first()
//         .children()
//         .should('have.length', panelCount)
// });
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
