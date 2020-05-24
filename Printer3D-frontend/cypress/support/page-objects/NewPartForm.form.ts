// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
// const ID: string = 'id';

export class NewPartForm extends IsolationService {
    public validatePanel(row: any) {
        console.log('[NewPartForm.validatePanel]> row:' + JSON.stringify(row));
        for (const key in row) {
            if (row.hasOwnProperty(key)) {
                const element = row[key];
                switch (key) {
                    case 'id':
                        let idValue = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> PAGE_PATH=' + idValue);
                        cy.get('new-part-dialog').get('form').find('.part-id').contains(idValue)
                        break;
                }
            }
        }
    }
    public fieldHasContent(row: any, fieldName: string) {
        switch (fieldName) {
            case 'id':
                let idValue = this.decodeDataTableRow(row, fieldName);
                cy.log('[NewPartForm.validatePanel]> ID=' + idValue);
                cy.get('new-part-dialog').get('form').find('.part-id').should('to.not.be.null');
                break;
        }
    }
    public fieldIsEmpty(fieldName: string) {
        switch (fieldName) {
            case 'id':
                // let idValue = this.decodeDataTableRow(row, fieldName);
                // cy.log('[NewPartForm.validatePanel]> ID=' + idValue);
                cy.get('new-part-dialog').get('form').find('.part-id').should('to.be.empty');
                break;
            case 'label':
                // let labelValue = this.decodeDataTableRow(row, fieldName);
                // cy.log('[NewPartForm.validatePanel]> DESCRIPTION=' + labelValue);
                cy.get('new-part-dialog').get('form').find('.part-label').should('to.be.empty');
                break;
            case 'description':
                // let descriptionValue = this.decodeDataTableRow(row, fieldName);
                // cy.log('[NewPartForm.validatePanel]> DESCRIPTION=' + descriptionValue);
                cy.get('new-part-dialog').get('form').find('.part-description').should('to.be.empty');
                break;
        }
    }
}
