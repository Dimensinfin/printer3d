// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const PAGE_PATH: string = 'page-path';

export class V1Dock extends IsolationService {
    public validatePanel(row: any) {
        console.log('[V1Dock.validatePanel]> row:' + JSON.stringify(row));
        let pagePath = this.decodeDataTableRow(row, PAGE_PATH);
        cy.log('[V1Dock.validatePanel]> PAGE_PATH=' + pagePath);

        cy.get('v1-dock').within(($panel) => {
            cy.get('.page-path').contains(pagePath);
        });
    }
}
