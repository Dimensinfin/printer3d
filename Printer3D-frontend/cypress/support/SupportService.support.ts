// - CORE
import { environment } from '../../src/environments/environment';

export class SupportService {
    private translationTable: any = {}

    constructor() {
        // - PAGES
        this.translationTable['New Part'] = 'new-part-dialog'
        this.translationTable['ProductionJobListPage'] = 'production-job-list-page'
        this.translationTable['PartInventory'] = 'v2-inventory-part-list-page'
        this.translationTable['NewRequestPage'] = 'v1-new-request-page'
        this.translationTable['OpenRequestsPage'] = 'v1-open-requests-page'
        this.translationTable['NewModelPage'] = 'v1-new-model-page'
        this.translationTable['part-list'] = 'v2-inventory-part-list-page'
        this.translationTable['InventoryPage'] = 'v3-inventory-page'
        // - PANELS
        this.translationTable['available-parts'] = 'v1-available-parts-panel'
        this.translationTable['new-request'] = 'v1-new-request-panel'
        this.translationTable['new-model'] = 'v1-new-model-panel'
        this.translationTable['drop-part-location'] = 'v1-drop-part-panel'
        this.translationTable['available-request-elements'] = 'v1-requestable-elements-panel'
        this.translationTable['catalog'] = 'v1-catalog-panel'
        // - RENDERS
        this.translationTable['job'] = 'v1-pending-job'
        this.translationTable['machine'] = 'v3-machine'
        this.translationTable['part'] = 'v1-part'
        this.translationTable['request'] = 'v1-request'
        this.translationTable['coil'] = 'v1-coil'
        this.translationTable['build-countdown-timer'] = 'v1-build-countdown-timer'
        this.translationTable['part4-request'] = 'v1-part4-request'
        this.translationTable['part-container'] = 'v1-part-container'
        this.translationTable['part-stack'] = 'v1-part-stack'
        this.translationTable['model'] = 'v1-model'
    }
    /**
     * Replaces symbolic names by the application names so if there are version changes the acceptance scritps should not change.
     * For example the tags for machine can change from version 1 v1-machine to version 3 v3-machine without changes on the test code.
     * @param tag the HTML tag name to serch for the applciation tag replacement.
     */
    public translateTag(name: string): string {
        return this.translationTable[name];
    }
}
