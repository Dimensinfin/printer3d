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

@Injectable({
    providedIn: 'root'
})
export class DialogFactoryService {
    constructor(public matDialog: MatDialog) { }

    public processClick(target: Feature): void {
        console.log('>[DialogFactoryComponent.processClick]> Feature: ' + JSON.stringify(target))
        switch (target.dialog) {
            case 'NewPartDialog':
                this.openModal(NewPartDialogComponent);
                break;
        }
    }
    private openModal(dialogComponent: any) :void{
        const dialogConfig = new MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.id = "newpart-component";
        dialogConfig.height = "80vh";
        dialogConfig.width = "60vw";
        const modalDialog: MatDialogRef<NewPartDialogComponent> = this.matDialog.open(dialogComponent, dialogConfig);
    }
}
