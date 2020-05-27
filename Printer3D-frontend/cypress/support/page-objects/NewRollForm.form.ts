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
}
