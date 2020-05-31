// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';
import { NewPartForm } from '../../support/page-objects/NewPartForm.form';
import { NewCoilForm } from '../../support/page-objects/NewCoilForm.form';

Then('there are a Feature with label {string}', function (label: string) {
    cy.get('app-root').find('v1-dock').find('v1-feature-render').contains(label, {matchCase: false})
});
