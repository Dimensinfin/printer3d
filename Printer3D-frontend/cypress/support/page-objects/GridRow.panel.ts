// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const LABEL: string = 'label';
const DESCRIPTION: string = 'description';
const BUILDTIME: string = 'buildTime';
const AFFINITY: string = 'affinity';
const STOCKLEVEL: string = 'stockLevel';

export class GridRow extends IsolationService {
    private rowIndex: number = 0;

    constructor(rowIndex: number) {
        super();
        this.rowIndex = rowIndex;
    }
    public validatePanel(row: any) {
        console.log('[GridRow.validatePanel]> row:' + JSON.stringify(row));
        const label = this.decodeDataTableRow(row, LABEL);
        cy.log('[GridRow.validatePanel]> LABEL=' + label);
        const description = this.decodeDataTableRow(row, DESCRIPTION);
        cy.log('[GridRow.validatePanel]> DESCRIPTION=' + description);

        cy.get('app-root')
            .find('inventory-part-list-page')
            .find('ag-grid-angular')
            .find('[role="row"]')
            .find('[col-id="label"]').each(($el, $index, $collection) => {
                if ($index == this.rowIndex) {
                    cy.wrap($el).find('span').contains(label);
                }
            });
    }
}
