// - SUPPORT
import { IsolationService } from "../IsolationService.support";

export class MachinePanel extends IsolationService {
    private target: any;

    constructor(target?: any) {
        super();
        this.target = target;
    }
    public validateButton(buttonName: string, row: any): void {
        let label = this.decodeDataTableRow(row, 'label');
        let state = this.decodeDataTableRow(row, 'state');
        switch (buttonName) {
            case 'COMENZAR':
                cy.log('[validateButton]> State: ' + state);
                if (null == this.target)
                    cy.get('v2-machines-panel').find('v2-machine-render').first()
                        .find('button').get('#start-button')
                        .should($el => expect($el.text().trim()).to.equal(label));
                else
                    cy.get('@target-machine')
                        .find('button').get('#start-button')
                        .should($el => expect($el.text().trim()).to.equal(label));
                if (null == this.target)
                    if (state === 'disabled')
                        cy.get('v2-machines-panel').find('v2-machine-render').first()
                            .find('button').get('#start-button')
                            .should('be.disabled')
                    else
                        cy.get('v2-machines-panel').find('v2-machine-render').first()
                            .find('button').get('#start-button')
                            .should('not.be.disabled')
                else
                    if (state === 'disabled')
                        cy.get('@target-machine')
                            .find('button').get('#start-button')
                            .should('be.disabled')
                    else
                        cy.get('@target-machine')
                            .find('button').get('#start-button')
                            .should('not.be.disabled')
                break;
            case 'CLEAR':
                const buttonName = '#clear-button'
                cy.log('[validateButton]> State: ' + state);
                if (null == this.target)
                    cy.get('v2-machines-panel').find('v2-machine-render').first()
                        .find('button').get('#clear-button')
                        .should($el => expect($el.text().trim()).to.equal(label));
                else
                    cy.get('@target-machine')
                        .find('button').get('#clear-button')
                        .should($el => expect($el.text().trim()).to.equal(label));
                if (null == this.target)
                    if (state === 'disabled')
                        cy.get('v2-machines-panel').find('v2-machine-render').first()
                            .find('button').get('#clear-button')
                            .should('be.disabled')
                    else
                        cy.get('v2-machines-panel').find('v2-machine-render').first()
                            .find('button').get('#clear-button')
                            .should('not.be.disabled')
                else
                    if (state === 'disabled')
                        cy.get('@target-machine')
                            .find('button').get('#clear-button')
                            .should('be.disabled')
                    else
                        cy.get('@target-machine')
                            .find('button').get('#clear-button')
                            .should('not.be.disabled')
                break;
        }
    }

}
