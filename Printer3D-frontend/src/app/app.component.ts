import { Component } from '@angular/core';
import { environment } from '@env/environment';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    public appTitle: string = '3DPrinterManagement - UI';
    public appVersion : string = environment.appVersion;

    public getAppTitle() : string {
        return this.appTitle;
    }
    public getAppVersion() : string {
        return this.appVersion;
    }
}
