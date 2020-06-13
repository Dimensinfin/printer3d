// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

Then('the New Request dialog opens and blocks the display', function () {
    cy.get('app-root').get('mat-dialog-container').get('v1-new-request-dialog').should('exist')
});
Then('the V1NewRequestPage is activated', function () {
    cy.get('app-root').find('v1-new-request-page')
        .should('exist')
});
Then('the V1NewRequestPage has {int} panels', function (panelCount: number) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .children()
        .should('have.length', 2)
});
Then('the left panel on page V1NewRequestPage is a {string}', function (panelType:string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(0)
        .find(panelType)
        .should('exist')
});
Then('the left panel on page V1NewRequestPage has variant {string}', function (variant: string) {
    cy.get('app-root').find('v1-new-request-page').find('.row')
        .find('[ng-reflect-variant="' + variant + '"]')
        .should('exist')
});

Then('the right panel on page V1NewRequestPage is a {string}', function (panelType:string) {
    cy.get('app-root').find('v1-new-request-page').find('.row').children().eq(1)
        .find(panelType)
        .should('exist')
});
