// - CORE
import { environment } from '../../src/environments/environment';

export class SupportService {
    private translationTable: any = {}

    constructor() {
        this.translationTable['ProductionJobListPage'] = 'production-job-list-page'
        this.translationTable['job'] = 'v1-pending-job'
        this.translationTable['machine'] = 'v3-machine'
        this.translationTable['part'] = 'v1-part'
        this.translationTable['request'] = 'v1-request'
        this.translationTable['coil'] = 'v1-coil'
        this.translationTable['build-countdown-timer'] = 'v1-build-countdown-timer'
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
