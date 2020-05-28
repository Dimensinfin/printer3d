import { Component } from '@angular/core';
import { environment } from '@env/environment';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    public appTitle: string = '3DPrinterManagement - UI';
    public appVersion: string = environment.appVersion;
    public self: AppComponent;
    private routedComponent: Refreshable;

    constructor() {
        this.self = this;
    }
    public getAppTitle(): string {
        return this.appTitle;
    }
    public getAppVersion(): string {
        return this.appVersion;
    }
    public setRoutedComponent(componentRef: Refreshable): void {
        console.log('[AppComponent.setRoutedComponent]')
        // if ( componentRef instanceof Refreshable )
        this.routedComponent = componentRef;
    }
    public refresh(): void {
        try {
            if (null != this.routedComponent) this.routedComponent.refresh();
        } catch (Exception) {
            console.log('[AppComponent.refresh]> Component is not refreshable.')
        }
    }
}
