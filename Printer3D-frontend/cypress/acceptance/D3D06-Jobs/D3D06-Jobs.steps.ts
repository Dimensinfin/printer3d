// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { V1PendingJob } from '../../support/page-objects/V1PendingJob.panel';
import { MachinePanel } from '../../support/page-objects/MachinePanel.panel';
import { SupportService } from '../../support/SupportService.support';

const supportService = new SupportService();

Then('the target item is draggable and with the contraint {string}', function (constraintName: string) {
    const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
    cy.get('@target-item').get('[draggable="true"]').first()
        .should('exist')
    cy.get('@target-item').get('[draggable="true"]').first()
        .get(constraint).should('exist')
});
Then('the target item is droppable and with the contraint {string}', function (constraintName: string) {
    const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
    cy.get('@target-item').get('[droppable]').first()
        .should('exist')
    cy.get('@target-item').get('[droppable]').first()
        .get(constraint).should('exist')
});



// Then('the v3-machine-render is droppable', function () {
//     cy.get('app-root').find('production-job-list-page')
//         .find('v2-machines-panel')
//         .find('v3-machine-render').find('.machine-job')
//         .get('[droppable]').first().should('exist')
// });
// Then('there is a constraint named {string}', function (constraintName: string) {
//     const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
//     cy.get('app-root').find('production-job-list-page')
//         .find('v2-machines-panel')
//         .find('v3-machine-render').find('.machine-job')
//         .get('[droppable]').first()
//         .get(constraint).should('exist')
// });

//   Then('the v1-pending-job-render is dragabble', function () {
//     cy.get('app-root').find('production-job-list-page')
//         .find('v1-pending-jobs-panel')
//         .find('div').find('.panel-left')
//         .get('[draggable="true"]').first().should('exist')
// });

// Then('there are a Feature with label {string}', function (label: string) {
//     cy.get('app-root').find('v1-dock').find('v1-feature-render').contains(label, { matchCase: false })
// });

Then('one instance of v1-pending-jobs-panel', function () {
    // const dock: V1PendingJob = new V1PendingJob();
    // expect(dock).to.not.be.null;
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel').should('have.length', 1)
});

Then('one instance of v2-machines-panel', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel').should('have.length', 1)
});

Then('one or more instances of v1-pending-job-render', function () {
    // const dock: V1PendingJob = new V1PendingJob();
    // expect(dock).to.not.be.null;
    cy.get('app-root').find('production-job-list-page')
        // .find('v1-pending-jobs-panel')
        .find('v1-pending-job-render').should('have.length.gt', 1)
});

Then('one or more instances of v3-machine-render', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render').should('exist')
});

Then('on the v3-machine-render component there is a field named {string}', function (fieldName: string) {
    const columnIdentifer = '[name="' + fieldName + '"]'
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render')
        .find(columnIdentifer).should('exist')
});

Then('on the v1-pending-job-render component there is a field named {string}', function (fieldName: string) {
    const columnIdentifer = '[name="' + fieldName + '"]'
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel')
        .find('v1-pending-job-render')
        .find(columnIdentifer).should('have.length.greaterThan', 1)
});
// Then('there is a constraint named {string}', function (constraintName: string) {
//     const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
//     cy.get('app-root').find('production-job-list-page')
//         .find('v1-pending-jobs-panel')
//         .find('div').find('.panel-left')
//         .get('[draggable="true"]').first()
//         .get(constraint).should('exist')
// });


Then('the button {string} has the next properties', function (buttonName: string, dataTable: any) {
    console.log('[THEN] the button {string} has the next properties');
    const form = new MachinePanel();
    expect(form).to.not.be.null;
    for (let index = 0; index < dataTable.hashes().length; index++) {
        const row = dataTable.hashes()[index];
        form.validateButton(buttonName, row)
    }
})

When('the Job is dragged and dropped on the Machine', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel')
        .find('div').find('.panel-left')
        .get('[draggable="true"]').first()
        .trigger('dragstart')
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render').find('.machine-job')
        .get('[droppable]').first()
        .trigger('drop')
});

Then('the machine has a Job', function () {
    // Check the job is on place
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render')
        .find('v1-pending-job-render').should('exist')
});

When('there is a click on the {string} button', function (buttonName: string) {
    console.log('[WHEN] there is a click on the {string} button');
    switch (buttonName) {
        case 'COMENZAR':
            cy.get('v3-machine-render').find('button').get('#start-button').should('not.be.disabled')
            cy.get('app-root').find('production-job-list-page')
                .find('v2-machines-panel')
                .find('v3-machine-render')
                .find('button').get('#start-button').click();
            break;
        case 'CLEAR':
            cy.get('v3-machine-render').find('button').get('#clear-button').should('not.be.disabled')
            cy.get('v3-machine-render').find('button').get('#clear-button').click();
            break;
    }
});

Then('the job is atached to the Machine persisted at the backend', function () {
    cy.log('need to verify that backed call is completed')
});

Then('one instance of v1-build-countdown-timer', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v1-build-countdown-timer').should('exist')
});

When('the target Machine has a Part on build', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render').as('target-machine')
        .find('v1-pending-job-render').should('exist')
});

Then('the target Machine button {string} is visible', function (buttonName: string) {
    switch (buttonName) {
        case 'COMENZAR':
            cy.get('@target-machine')
                .find('button').get('#start-button').should('be.visible')
            break;
        case 'CLEAR':
            cy.get('@target-machine')
                .find('button').get('#clear-button').should('be.visible')
            break;
    }
});

Then('the target Machine button {string} is not visible', function (buttonName: string) {
    const buttonReference = '#' + buttonName;
    cy.get('@target-machine')
        .find('button').get(buttonReference).should('not.exist')
});

Then('the target Machine button {string} has the next properties', function (buttonName: string, dataTable) {
    const form = new MachinePanel(cy.get('@target-machine'));
    expect(form).to.not.be.null;
    for (let index = 0; index < dataTable.hashes().length; index++) {
        const row = dataTable.hashes()[index];
        form.validateButton(buttonName, row)
    }
});

Then('the target Machine has one instance of {string}', function (panelName: string) {
    cy.get('@target-machine').find(panelName).should('exist')
});


Then('the button {string} is not visible', function (buttonName: string) {
    let buttonReference: string = '#start-button';
    switch (buttonName) {
        case 'COMENZAR':
            buttonReference = '#start-button';
            break;
        case 'CLER':
            buttonReference = '#clear-button';
            break;
    }
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render')
        .find('button').get(buttonReference).should('not.exist')
});

Then('the button {string} is visible', function (buttonName: string) {
    let buttonReference: string = '#start-button';
    switch (buttonName) {
        case 'COMENZAR':
            buttonReference = '#start-button';
            break;
        case 'CLER':
            buttonReference = '#clear-button';
            break;
    }
    cy.get('app-root').find('production-job-list-page')
        .find('v2-machines-panel')
        .find('v3-machine-render')
        .find('button').get(buttonReference).should('exist')
});


// - B E S T   P R A C T I C E S
Then('the target item has a field named {string} with value {string}', function (fieldName: string, fieldValue: string) {
    cy.get('@target-panel').within(($item) => {
        cy.get('[cy-name="' + fieldName + '"]').contains(fieldValue, { matchCase: false })
    })
});
// Then('the target panel has {int} {string}', function (count: number, renderName: string) {
//     const tag = supportService.translateTag(renderName) // Do name replacement
//     cy.log('>[translation]> ' + renderName + ' -> ' + tag)
//     cy.get('@target-panel').find(tag).should('have.length', count)
// });
