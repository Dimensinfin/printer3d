// - SUPPORT
import { IsolationService } from "../IsolationService.support";

export class NewRollForm extends IsolationService {
    public validateButton(buttonName: string, row: any): void {
        let label = this.decodeDataTableRow(row, 'label').toLowerCase();
        let state = this.decodeDataTableRow(row, 'state');
        switch (buttonName) {
            case 'GUARDAR':
                cy.log('[validateButton]> State: ' + state);
                cy.get('new-roll-dialog').find('button').get('#submit-button').should($el => expect($el.text().toLowerCase().trim()).to.equal(label));
                if (state === 'disabled')
                    cy.get('new-roll-dialog').find('button').get('#submit-button').should('be.disabled')
                else
                    cy.get('new-roll-dialog').find('button').get('#submit-button').should('not.be.disabled')
                break;
            case 'CANCEL':
                cy.get('new-roll-dialog').find('button').get('#cancel-button').should($el => expect($el.text().toLowerCase().trim()).to.equal(label));
                if (state === 'disabled')
                    cy.get('new-roll-dialog').find('button').get('#cancel-button').should('be.disabled')
                else
                    cy.get('new-roll-dialog').find('button').get('#cancel-button').should('not.be.disabled')
                break;
            case 'GUARDARYCONTINUAR':
                cy.get('new-roll-dialog').find('button').get('#repeat-button').should($el => expect($el.text().toLowerCase().trim()).to.equal(label));
                if (state === 'disabled')
                    cy.get('new-roll-dialog').find('button').get('#repeat-button').should('be.disabled')
                else
                    cy.get('new-roll-dialog').find('button').get('#repeat-button').should('not.be.disabled')
                break;
        }
    }
    public formInput(row: any): void {
        console.log('[NewPartForm.formInput]> row:' + JSON.stringify(row));
        for (const key in row) {
            if (row.hasOwnProperty(key)) {
                const element = row[key];
                let value;
                switch (key) {
                    case 'material':
                        value = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> MATERIAL=' + value);
                        cy.get('new-roll-dialog').get('form').find('.roll-material').clear()
                        cy.get('new-roll-dialog').get('form').find('.roll-material').type(value)
                        break;
                    case 'color':
                        value = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> COLOR_CODE=' + value);
                        cy.get('new-roll-dialog').get('form').find('.roll-color').clear()
                        cy.get('new-roll-dialog').get('form').find('.roll-color').type(value)
                        break;
                    case 'peso':
                        value = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> BUILD_TIME=' + value);
                        cy.get('new-roll-dialog').get('form').find('.roll-weight').clear()
                        cy.get('new-roll-dialog').get('form').find('.roll-weight').type(value)
                        break;
                }
            }
        }
    }
    public validateFields(row: any): void {
        console.log('[NewPartForm.validateField]> row:' + JSON.stringify(row));
        for (const key in row) {
            if (row.hasOwnProperty(key)) {
                // const element = row[key];
                let value: string;
                switch (key) {
                    case 'material':
                        value = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> MATERIAL=' + value);
                        cy.get('new-roll-dialog').get('form')
                            .get('input[name="' + key + '"]')
                            .invoke('val').should('eq', value)
                        break;
                    case 'color':
                        value = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> COLOR_CODE=' + value);
                        cy.get('new-roll-dialog').get('form')
                            .get('input[name="' + key + '"]')
                            .invoke('val').should('eq', value)
                        break;
                    case 'peso':
                        value = this.decodeDataTableRow(row, key);
                        cy.log('[NewPartForm.validatePanel]> BUILD_TIME=' + value);
                        cy.get('new-roll-dialog').get('form')
                            .get('input[name="' + key + '"]')
                            .invoke('val').should('eq', value)
                        break;
                }
            }
        }
    }
}
