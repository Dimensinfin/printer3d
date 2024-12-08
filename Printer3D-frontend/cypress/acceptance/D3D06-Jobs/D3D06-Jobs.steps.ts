// - CUCUMBER-PREPROCESSOR
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { SupportService } from '../../support/SupportService.support';

// - F I E L D S
Then('hidden field named {string} has contents {string}',
    function (fieldName: string, fieldValue: string) {
        cy.get('@target').within(($item) => {
            cy.get('[cy-name="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
        })
    });
// - T A R G E T   C O N T E N T S
Then('the target item is draggable and with the contraint {string}', function (constraintName: string) {
    const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
    cy.get('@target').get('[draggable="true"]').first()
        .should('exist')
    cy.get('@target').get('[draggable="true"]').first()
        .get(constraint).should('exist')
});
Then('the target item is droppable and with the contraint {string}', function (constraintName: string) {
    const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
    cy.get('@target').get('[cdkDropList]').first()
        .should('exist')
    cy.get('@target').get('[cdkDropList]').first()
        .get(constraint).should('exist')
});
