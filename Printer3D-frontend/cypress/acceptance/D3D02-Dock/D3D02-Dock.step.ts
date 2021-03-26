// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICE
import { IsolationService } from '../../support/IsolationService.support';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

// - F E A T U R E S
Then('there is a Feature with label {string}', function (label: string) {
    cy.get('v1-dock').find('v2-feature').find('.feature-label').contains(label, { matchCase: false })
});
Then('there are {int} Features enabled', function (count: number) {
    cy.get('v1-dock').find('v2-feature').within(($panel) => {
        cy.get('.feature').not('.disabled').should('have.length', count)
    });
});
Then('the Feature with label {string} opens a Dialog', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').parent().find('.blue-mark').should('exist')
});
Then('the Feature with label {string} opens a Page', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').should('not.exist')
});
Then('the Feature with label {string} opens a DropPage', function (label: string) {
    cy.get('v1-dock').find('v2-feature')
        .contains(label, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').find('.corner-top').parent().find('.blueviolet-mark').should('exist')
});

// - F E A T U R E   S E L E C T I O N
When('the Feature with label {string} is clicked the destination is the Dialog {string}', function (featureLabel: string, symbolicName: string) {
    const destination = supportService.translateTag(symbolicName) // Do name replacement
    cy.get(supportService.translateTag('dock'))
        .find(supportService.translateTag('feature')).find('[cy-name="feature"]')
        .contains(featureLabel, { matchCase: false }).parent().parent().as('target-feature')
    cy.get('@target-feature').scrollIntoView().click('center');
    cy.get('mat-dialog-container')
        .get(destination).should('exist').as('target-dialog')
});

// -  D I A L O G   B U T T O N S
When('there is a click on the {string} button of target dialog', function (buttonName: string) {
    cy.get('@target-dialog').find('[cy-name="' + buttonName + '"]').click('center')
});
// - D I A L O G   C O N T E N T S
Then('the dialog has the title {string}', function (title: string) {
    cy.get('@target-dialog').find('.panel-title').contains(title, { matchCase: false })
});


// When('there is a click on the {string} button', function (buttonName) {
//     console.log('[WHEN] there is a click on the {string} button');
//     switch (buttonName) {
//         case 'CLOSE':
//             cy.get('new-part-dialog').find('button').get('#cancel-button').should('not.be.disabled')
//             cy.get('new-part-dialog').find('button').get('#cancel-button').click();
//             break;
//     }
// });





// Then('there are {int} Features enabled', function (int) {
//     cy.get('v1-dock').find('v2-feature').within(($panel) => {
//         cy.get('.feature-clip').should('not.have.class', 'disabled')
//     });
// });
Then('the target Feature {string} changes to state {string}', function (featureLabel: string, state: string) {
    if (state == 'active')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('exist')
        });
    if (state == 'inactive')
        cy.get('@target-feature').within(($panel) => {
            cy.get('.corner-mark').should('not.exist')
        });
});
// Then('the target Feature {string} changes to state {string}', function (featureLabel: string, state: string) {
//     if (state == 'active')
//         cy.get('@target-feature').within(($panel) => {
//             cy.get('.corner-mark').should('exist')
//         });
//     if (state == 'inactive')
//         cy.get('@target-feature').within(($panel) => {
//             cy.get('.corner-mark').should('not.exist')
//         });
//     // switch (state) {
//     //     case 'active':
//     //         cy.get('v1-dock')
//     //             .find('v1-feature-render')
//     //             .find('.feature-block')
//     //             .contains(featureLabel, { matchCase: false })
//     //             .closest('.feature-block')
//     //             .find('.clip')
//     //             .find('div')
//     //             .eq(0)
//     //             .should('have.class', 'container').and('have.class', 'active');
//     //         break;
//     //     case 'inactive':
//     //         cy.get('v1-dock')
//     //             .find('v1-feature-render')
//     //             .find('.feature-block')
//     //             .contains(featureLabel, { matchCase: false })
//     //             .closest('.feature-block')
//     //             .find('.clip')
//     //             .find('div')
//     //             .eq(0)
//     //             .should('have.class', 'container').and('not.have.class', 'active');
//     //         break;
//     // }
// });




Then('the target page is InventoryPartListPage', function () {
    cy.get('app-root').find('inventory-part-list-page').should('have.length', 1);
});

Then('there are {int} Features', function (featureCount) {
    console.log('[THEN] there are {int} Features');
    cy.get('app-root').find('v2-feature').should('have.length', featureCount)
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
