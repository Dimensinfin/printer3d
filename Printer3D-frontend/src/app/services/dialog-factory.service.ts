// - CORE
import { Injectable } from '@angular/core';
// - MATERIAL
import { MatLegacyDialogConfig as MatDialogConfig } from '@angular/material/legacy-dialog';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
// - DOMAIN
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from '@app/modules/inventory/dialogs/new-coil-dialog/new-coil-dialog.component';
import { Feature } from '@app/modules/bit/Feature.domain';

@Injectable({
    providedIn: 'root'
})
export class DialogFactoryService {
    private modalDialog: MatDialogRef<any>;

    constructor(public matDialog: MatDialog) { }

    public processClick(target: Feature): MatDialogRef<any> {
        console.log('>[DialogFactoryComponent.processClick]> Feature: ' + JSON.stringify(target))
        let dialogConfig: MatDialogConfig;
        if (null != target)
            switch (target.route) {
                case 'NewPartDialog':
                    dialogConfig = new MatDialogConfig();
                    dialogConfig.disableClose = false;
                    dialogConfig.id = "newpart-component";
                    // dialogConfig.height = "86vh";
                    this.modalDialog = this.matDialog.open(NewPartDialogComponent, dialogConfig);
                    break;
                case 'NewCoilDialog':
                    dialogConfig = new MatDialogConfig();
                    dialogConfig.disableClose = false;
                    dialogConfig.id = "newcoil-component";
                    // dialogConfig.height = "86vh";
                    this.modalDialog = this.matDialog.open(NewCoilDialogComponent, dialogConfig);
                    break;
            }
        return this.modalDialog;
    }
}
