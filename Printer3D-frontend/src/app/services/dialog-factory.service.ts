// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
// - MATERIAL
import { MatDialogConfig } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { NewPartDialogComponent } from '@app/modules/inventory/dialogs/new-part-dialog/new-part-dialog.component';
import { NewCoilDialogComponent } from '@app/modules/inventory/dialogs/new-coil-dialog/new-coil-dialog.component';
import { NewModelDialogComponent } from '@app/modules/inventory/dialogs/new-model-dialog/new-model-dialog.component';
// import { V1NewRequestDialogComponent } from '@app/modules/production/dialogs/v1-new-request-dialog/v1-new-request-dialog.component';

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
                    dialogConfig.height = "86vh";
                    this.modalDialog = this.matDialog.open(NewPartDialogComponent, dialogConfig);
                    break;
                case 'NewCoilDialog':
                    dialogConfig = new MatDialogConfig();
                    dialogConfig.disableClose = true;
                    dialogConfig.id = "newcoil-component";
                    dialogConfig.height = "86vh";
                    this.modalDialog = this.matDialog.open(NewCoilDialogComponent, dialogConfig);
                    break;
                // case 'NewRequestDialog':
                //     dialogConfig = new MatDialogConfig();
                //     dialogConfig.disableClose = false;
                //     dialogConfig.id = "newrequest-component";
                //     dialogConfig.height = "86vh";
                //     // dialogConfig.width = "60vw";
                //     this.modalDialog = this.matDialog.open(V1NewRequestDialogComponent, dialogConfig);
                //     break;
                // case 'NewModelDialog':
                //     dialogConfig = new MatDialogConfig();
                //     // dialogConfig.disableClose = true;
                //     dialogConfig.id = "newmodel-component";
                //     dialogConfig.height = "86vh";
                //     // dialogConfig.width = "60vw";
                //     this.modalDialog = this.matDialog.open(NewModelDialogComponent, dialogConfig);
                //     break;
            }
        return this.modalDialog;
    }
}
