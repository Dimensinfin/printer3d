// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const PAGE_PATH: string = 'page-path';

export class V1PagePath extends IsolationService {
    public validatePanel(row: any) {
        console.log('[V1PagePath.validatePanel]> row:' + JSON.stringify(row));
        let pagePath = this.decodeDataTableRow(row, PAGE_PATH);
        cy.log('[V1PagePath.validatePanel]> PAGE_PATH=' + pagePath);

        cy.get('v1-page-path-panel').within(($panel) => {
            cy.get('.page-path').contains(pagePath);
        });
    }
}
