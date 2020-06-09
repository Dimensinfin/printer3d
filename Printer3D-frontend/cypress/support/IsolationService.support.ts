// - CORE
import { environment } from './environment';

export class IsolationService {
    public doLandingPage() {
        let urlRequest = '/';
        cy.log('-[IsolationService.doLandingPage]> urlRequest=' + urlRequest);
        cy.visit(urlRequest);
    }
    // - C U C U M B E R   D E C O D I N G
    public decodeDataTableRow(row: any, columnIdentifier: string): string {
        console.log('-[decodeDataTableRow]>row=' + JSON.stringify(row));
        console.log('-[decodeDataTableRow]>columnIdentifier=' + columnIdentifier);
        let foundValue = row[columnIdentifier];
        console.log('-[decodeDataTableRow]>foundValue=' + foundValue);
        if (null != foundValue) return this.replaceValueTemplated(foundValue)
        else return '-undefined-';
    }
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
                    case 'constant':
                        return this.replaceConfigurationTemplate(name);
                        break;
                    case 'system':
                        return this.replaceSystemTemplate(name);
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
            case 'copyright':
                return environment.copyright;
        }
        return '-undefined-';
    }
    public replaceConfigurationTemplate(templateName: string): string {
        return templateName;
    }
    public replaceSystemTemplate(templateName: string): string {
        return '-undefined-';
    }

    // - R A M D O M
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
}
