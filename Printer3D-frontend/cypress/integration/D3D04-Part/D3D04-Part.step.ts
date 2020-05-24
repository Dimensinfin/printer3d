// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';

When('there is a click on Feature {string}', function (featureLabel: string) {
    console.log('[WHEN] there is a click on Feature {string}');
    cy.get('v1-dock')
        .find('v1-feature-render')
        .find('.feature-block')
        .contains(featureLabel, { matchCase: false })
        .closest('.feature-block').click('top');
});

Then('the New Part dialog opens and blocks the display', function () {
    console.log('[THEN] the New Part dialog opens and blocks the display');
    cy.get('app-root').get('mat-dialog-container').get('new-part-dialog').should('have.length', 1)
});

Then('there is one instance of form field {string} with the next contents', function (fieldId, dataTable) {
    console.log('[THEN] there is one instance of form field {string} with the next contents');
    const row = dataTable.hashes()[0];
    const form = new NewPartForm();
    expect(form).to.not.be.null;
    form.validatePanel(row);
});
Then('there is one instance of form field {string} with content', function (fieldName, dataTable) {
    console.log('[THEN] there is one instance of form field {string} with the next contents');
    const row = dataTable.hashes()[0];
    const form = new NewPartForm();
    expect(form).to.not.be.null;
    form.fieldHasContent(row, fieldName);
});
Then('the NewPart dialog input fields should be empty', function () {
    console.log('[THEN] the NewPart dialog input fields should be empty');
    const form = new NewPartForm();
    expect(form).to.not.be.null;
    form.fieldIsEmpty('label');
    form.fieldIsEmpty('description');
    form.fieldIsEmpty('cost');
    form.fieldIsEmpty('imagePath');
    form.fieldIsEmpty('modelPath');
});
