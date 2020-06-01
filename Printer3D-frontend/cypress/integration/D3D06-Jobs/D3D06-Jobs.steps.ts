// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { V1PendingJob } from '../../support/page-objects/V1PendingJob.panel';
import { MachinePanel } from '../../support/page-objects/MachinePanel.panel';

Then('there are a Feature with label {string}', function (label: string) {
    cy.get('app-root').find('v1-dock').find('v1-feature-render').contains(label, { matchCase: false })
});

Then('the ProductionJobListPage is activated', function () {
    cy.get('app-root').find('production-job-list-page').should('have.length', 1);
});

Then('one instance of v1-pending-jobs-panel', function () {
    // const dock: V1PendingJob = new V1PendingJob();
    // expect(dock).to.not.be.null;
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel').should('have.length', 1)
});

Then('one instance of v1-machines-panel', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v1-machines-panel').should('have.length', 1)
});

Then('one or more instances of v1-pending-job-render', function () {
    // const dock: V1PendingJob = new V1PendingJob();
    // expect(dock).to.not.be.null;
    cy.get('app-root').find('production-job-list-page')
        // .find('v1-pending-jobs-panel')
        .find('v1-pending-job-render').should('have.length.gt', 1)
});

Then('one or more instances of v1-machine-render', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v1-machines-panel')
        .find('v1-machine-render').should('exist')
});

Then('on the v1-machine-render component there is a field named {string}', function (fieldName: string) {
    const columnIdentifer = '[name="' + fieldName + '"]'
    cy.get('app-root').find('production-job-list-page')
        .find('v1-machines-panel')
        .find('v1-machine-render')
        .find(columnIdentifer).should('exist')
});

Then('on the v1-pending-job-render component there is a field named {string}', function (fieldName: string) {
    const columnIdentifer = '[name="' + fieldName + '"]'
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel')
        .find('v1-pending-job-render')
        .find(columnIdentifer).should('have.length.greaterThan', 1)
});

Then('the v1-pending-job-render is dragabble', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel')
        .find('div').find('.panel-left')
        .get('[draggable="true"]').first().should('exist')
});

Then('there is a constraint named {string}', function (constraintName: string) {
    const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
    cy.get('app-root').find('production-job-list-page')
        .find('v1-pending-jobs-panel')
        .find('div').find('.panel-left')
        .get('[draggable="true"]').first()
        .get(constraint).should('exist')
});

Then('the v1-machine-render is droppable', function () {
    cy.get('app-root').find('production-job-list-page')
        .find('v1-machines-panel')
        .find('v1-machine-render').find('.machine-job')
        .get('[droppable]').first().should('exist')
});

Then('there is a constraint named {string}', function (constraintName: string) {
    const constraint = '[ng-reflect-drag-scope="' + constraintName + '"]'
    cy.get('app-root').find('production-job-list-page')
        .find('v1-machines-panel')
        .find('v1-machine-render').find('.machine-job')
        .get('[droppable]').first()
        .get(constraint).should('exist')
});

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
        .find('v1-machines-panel')
        .find('v1-machine-render').find('.machine-job')
        .get('[droppable]').first()
        .trigger('drop')
});

Then('the machine has a Job', function () {
    // Check the job is on place
    cy.get('app-root').find('production-job-list-page')
        .find('v1-machines-panel')
        .find('v1-machine-render')
        .find('v1-pending-job-render').should('exist')
});
