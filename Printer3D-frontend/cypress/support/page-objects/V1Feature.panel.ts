// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const LABEL: string = 'label';

export class V1Feature extends IsolationService {
    public validatePanel(row: any) {
        console.log('[V1Feature.validatePanel]> row:' + JSON.stringify(row));
        let label = this.decodeDataTableRow(row, LABEL);
        cy.log('[V1Feature.validatePanel]> LABEL=' + label);

        cy.get('v1-feature-render').within(($panel) => {
            cy.get('.label').contains(label);
        });
    }
}
