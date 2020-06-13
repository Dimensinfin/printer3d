// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

const INVENTORY_PART_LIST_PAGE_NAME = '/Inventory/Part List';

Then('there are {int} Features enabled', function (int) {
    cy.get('v1-dock').find('v2-feature-render').within(($panel) => {
        cy.get('.feature-clip').should('not.have.class', 'disabled')
    });
});
Then('there is a Feature with label {string}', function (label: string) {
    cy.get('v1-dock').find('v2-feature-render').find('.feature-label').contains(label, { matchCase: false })
});
Then('the Feature with label {string} opens a Dialog', function (label: string) {
    cy.get('v1-dock').find('v2-feature-render')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').should('exist')
});
Then('the Feature with label {string} opens a Page', function (label: string) {
    cy.get('v1-dock').find('v2-feature-render')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').should('not.exist')
});
Then('there are no Features active', function () {
    cy.get('v1-dock')
        .find('v2-feature-render').within(($panel) => {
            cy.get('.corner-mark').should('have.length', 0)
        });
});
Then('the target Feature {string} changes to state {string}', function (featureLabel:string, state:string) {
    if ( state=='active')
    cy.get('@target-feature').within(($panel) => {
        cy.get('.corner-mark').should('exist')
    });
    if ( state=='inactive')
    cy.get('@target-feature').within(($panel) => {
        cy.get('.corner-mark').should('not.exist')
    });
    // switch (state) {
    //     case 'active':
    //         cy.get('v1-dock')
    //             .find('v1-feature-render')
    //             .find('.feature-block')
    //             .contains(featureLabel, { matchCase: false })
    //             .closest('.feature-block')
    //             .find('.clip')
    //             .find('div')
    //             .eq(0)
    //             .should('have.class', 'container').and('have.class', 'active');
    //         break;
    //     case 'inactive':
    //         cy.get('v1-dock')
    //             .find('v1-feature-render')
    //             .find('.feature-block')
    //             .contains(featureLabel, { matchCase: false })
    //             .closest('.feature-block')
    //             .find('.clip')
    //             .find('div')
    //             .eq(0)
    //             .should('have.class', 'container').and('not.have.class', 'active');
    //         break;
    // }
});



// Then('there are {int} Features active', function (int) {
//     cy.get('v1-dock').find('v1-feature-render')
// });


Then('the target page is InventoryPartListPage', function () {
    cy.get('app-root').find('inventory-part-list-page').should('have.length', 1);
});

Then('the target page is V2InventoryPartListPage', function () {
    cy.get('app-root').find('v2-inventory-part-list-page').should('have.length', 1);
});

Then('there are {int} Features', function (featureCount) {
    console.log('[THEN] there are {int} Features');
    cy.get('app-root').find('v2-feature-render').should('have.length', featureCount)
});

Then('there is a v1-feature-render with label {string}', function (label) {
    console.log('[THEN] there is a v1-feature-render with label {string}');
    cy.get('app-root').find('v1-dock').find('v1-feature-render').contains(label)
});

Then('the target Feature {string} changes to state {string}', function (featureLabel, state) {
    switch (state) {
        case 'active':
            cy.get('v1-dock')
                .find('v1-feature-render')
                .find('.feature-block')
                .contains(featureLabel, { matchCase: false })
                .closest('.feature-block')
                .find('.clip')
                .find('div')
                .eq(0)
                .should('have.class', 'container').and('have.class', 'active');
            break;
        case 'inactive':
            cy.get('v1-dock')
                .find('v1-feature-render')
                .find('.feature-block')
                .contains(featureLabel, { matchCase: false })
                .closest('.feature-block')
                .find('.clip')
                .find('div')
                .eq(0)
                .should('have.class', 'container').and('not.have.class', 'active');
            break;
    }
});

When('there is a click on the {string} button', function (buttonName) {
    console.log('[WHEN] there is a click on the {string} button');
    switch (buttonName) {
        case 'CLOSE':
            cy.get('new-part-dialog').find('button').get('#cancel-button').should('not.be.disabled')
            cy.get('new-part-dialog').find('button').get('#cancel-button').click();
            break;
    }
});
