// - CORE
import { environment } from '../../src/environments/environment';

export class SupportService {
    private translationTable: any = {}

    constructor() {
        // - PAGES
        this.translationTable['New Part'] = 'new-part-dialog'
        this.translationTable['New Coil'] = 'new-coil-dialog'
        this.translationTable['ProductionJobListPage'] = 'production-job-list-page'
        this.translationTable['Production Jobs Page'] = 'production-job-list-page'
        this.translationTable['NewRequestPage'] = 'v1-new-request-page'
        this.translationTable['OpenRequestsPage'] = 'v1-open-requests-page'
        this.translationTable['Open Requests Page'] = 'v1-open-requests-page'
        this.translationTable['NewModelPage'] = 'v1-new-model-page'
        this.translationTable['InventoryPage'] = 'v3-inventory-page'
        this.translationTable['CoilsPage'] = 'v2-coil-list-page'
        this.translationTable['Delete Confirmation'] = 'delete-confirmation-dialog'
        // - PANELS
        this.translationTable['app-component'] = 'app-root'
        this.translationTable['dock'] = 'v1-dock'
        this.translationTable['work-load'] = 'v1-work-load-panel'
        this.translationTable['coils'] = 'v1-coils-panel'
        this.translationTable['machines'] = 'v2-machines-panel'
        this.translationTable['available-parts'] = 'v1-available-parts-panel'
        this.translationTable['new-request'] = 'v1-new-request-panel'
        this.translationTable['new-model'] = 'v1-new-model-panel'
        this.translationTable['drop-part-location'] = 'v1-drop-part-panel'
        this.translationTable['available-request-elements'] = 'v1-requestable-elements-panel'
        this.translationTable['catalog'] = 'v1-catalog-panel'
        this.translationTable['model-detail'] = 'v1-new-model-panel'
        this.translationTable['open-requests'] = 'v1-open-requests-panel'
        this.translationTable['request-detail'] = 'v1-request-detail-panel'
        this.translationTable['jobs-list'] = 'v1-pending-jobs-panel'
        this.translationTable['billing-chart'] = 'v1-billing-chart-panel'
        // - RENDERS
        this.translationTable['feature'] = 'v2-feature'
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
        this.translationTable['request-item'] = 'v1-request-item'
        this.translationTable['request-content'] = 'v1-request-item'
        this.translationTable['job-timer'] = 'v1-build-countdown-timer'
        // - TAGS
        this.translationTable['buttons'] = 'button'   
        // - BACKEND REQUESTS
        this.translationTable['Get Open Requests'] = 'apiProductionGetOpenRequests_v2'           
        this.translationTable['Start Build Job'] = 'apiMachinesStartBuild_v2'           
    }
    /**
     * Replaces symbolic names by the application names so if there are version changes the acceptance scritps should not change.
     * For example the tags for machine can change from version 1 v1-machine to version 3 v3-machine without changes on the test code.
     * @param tag the HTML tag name to serch for the applciation tag replacement.
     */
    public translateTag(name: string): string {
        return this.translationTable[name];
    }
    // - R A N D O M
    public generateRandomNum(min: number, max: number) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    };
    public generateRandomString(length: number): string {
        var string = '';
        var letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' //Include numbers if you want
        for (let i = 0; i < length; i++) {
            string += letters.charAt(Math.floor(Math.random() * letters.length));
        }
        return string;
    }
    // - T E M P L A T E   R E P L A C E M E N T
    /**
    * This function replaces values found on Gherkin files by configuration values if they fit the <name> syntax.
    * There are 3 sets of templated values. Environmental that will replace the value by an 'environtment' value,
    * configuration that will do the same with an application configured constant and system that will replace the
    * value by the result of a system function.
    * @param value the value to check for templated.
    */
    public replaceValueTemplated(value: string): string {
        let regex = /^<(.+)\.(.+)>$/g
        if (regex.test(value)) {
            const domain = RegExp.$1;
            const name = RegExp.$2;
            console.log('-[replaceValueTemplated]>domain=' + domain);
            console.log('-[replaceValueTemplated]>name=' + name);
            if (null != domain) {
                switch (domain) {
                    case 'environment':
                        return this.replaceEnvironmentTemplate(name);
                        break;
                }
            }
        }
        return value;
    }
    public replaceEnvironmentTemplate(templateName: string): string {
        switch (templateName) {
            case 'app-name':
                return environment.appName;
            case 'app-title':
                return environment.appTitle;
            case 'app-version':
                return environment.appVersion;
            case 'backend-version':
                return environment.backendVersion;
            case 'copyright':
                return environment.copyright;
        }
        return '-undefined-';
    }
}
