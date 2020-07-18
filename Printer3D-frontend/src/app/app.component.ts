// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartContainer } from '@domain/PartContainer.domain';
import { environment } from '@env/environment';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { BackgroundEnabledComponent } from './modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { DockService } from './services/dock.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent extends BackgroundEnabledComponent implements OnInit {
    public appTitle: string = '3DPrinterManagement - UI';
    public appVersion: string = environment.appVersion;
    public backendVersion: string = 'backend';
    public self: AppComponent;

    constructor(
        protected backendService: BackendService,
        protected dockService : DockService) {
        super();
        this.self = this;
        this.appVersion = environment.appVersion + ' ' + process.env.NODE_ENV
    }
    /**
     * Access the backend to read the backend version
     */
    public ngOnInit(): void {
        this.backendConnections.push(
            this.backendService.apiActuatorInfo(new ResponseTransformer().setDescription('Transforms backend info data into json fields.')
                .setTransformation((entrydata: any): BackendInfoResponse => {
                    return new BackendInfoResponse(entrydata);
                }))
                .subscribe((backendInfo: BackendInfoResponse) => {
                    this.backendVersion = backendInfo.getVersion();
                })
        )
    }

    public getAppTitle(): string {
        return this.appTitle;
    }
    public getAppVersion(): string {
        return this.appVersion;
    }
    /**
     * This method is fired by any routing change. It is being used to report to the Dock service which page is being selected so the Dock can test if the user is navigating to the same route and then fire directly the refresh to the current active page detected through this method.
     * @param componentRef  
     */
    public setRoutedComponent(componentRef: Refreshable): void {
        console.log('-[AppComponent.setRoutedComponent]> Component: '+componentRef)
        this.dockService.activatePage( componentRef)
    }
    public refresh(): void {
        console.log('WARNING. This method should not be being called')
        this.dockService.refresh()
    }
}
