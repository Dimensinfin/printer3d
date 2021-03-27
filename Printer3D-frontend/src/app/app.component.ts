// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogConfig } from '@angular/material/dialog';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { environment } from '@env/environment';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { BackgroundEnabledComponent } from './modules/shared/core/background-enabled/background-enabled.component';
import { BackendInfoResponse } from '@domain/dto/BackendInfoResponse.dto';
import { DockService } from './services/dock.service';
import { PatchNotesDialogComponent } from './modules/common/patch-notes-dialog/patch-notes-dialog.component';

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
        protected dockService : DockService,
        protected matDialog: MatDialog) {
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

    // - I N T E R A C T I O N S
    public openPatchNotes () : void {
      const  dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = false;
        dialogConfig.id = "patch-notes";
        dialogConfig.height = "86vh";
        dialogConfig.width = "60vw";
        const modalDialog = this.matDialog.open(PatchNotesDialogComponent, dialogConfig);
    }
}
