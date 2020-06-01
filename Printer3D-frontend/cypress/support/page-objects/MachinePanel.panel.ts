// - SUPPORT
import { IsolationService } from "../IsolationService.support";

export class MachinePanel extends IsolationService {
    public validateButton(buttonName: string, row: any): void {
        let label = this.decodeDataTableRow(row, 'label');
        let state = this.decodeDataTableRow(row, 'state');
        switch (buttonName) {
            case 'COMENZAR':
                cy.log('[validateButton]> State: ' + state);
                cy.get('v1-machines-panel').find('v1-machine-render').first()
                    .find('button').get('#start-button')
                    .should($el => expect($el.text().trim()).to.equal(label));
                if (state === 'disabled')
                    cy.get('v1-machines-panel').find('v1-machine-render').first()
                        .find('button').get('#start-button')
                        .should('be.disabled')
                else
                    cy.get('v1-machines-panel').find('v1-machine-render').first()
                        .find('button').get('#start-button')
                        .should('not.be.disabled')
                break;
            case 'CLEAR':
                cy.log('[validateButton]> State: ' + state);
                cy.get('v1-machines-panel').find('v1-machine-render').first()
                    .find('button').get('#clear-button')
                    .should($el => expect($el.text().trim()).to.equal(label));
                if (state === 'disabled')
                    cy.get('v1-machines-panel').find('v1-machine-render').first()
                        .find('button').get('#clear-button')
                        .should('be.disabled')
                else
                    cy.get('v1-machines-panel').find('v1-machine-render').first()
                        .find('button').get('#clear-button')
                        .should('not.be.disabled')
                break;
        }
    }

}
