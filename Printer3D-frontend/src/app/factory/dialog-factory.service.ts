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
    private modalDialog: MatDialogRef<NewPartDialogComponent>;

    public processClick(target: Feature): void {
        console.log('>[DialogFactoryComponent.processClick]> Feature: ' + JSON.stringify(target))
        switch (target.dialog) {
            case 'NewPartDialog':
                const dialogConfig = new MatDialogConfig();
                dialogConfig.disableClose = true;
                dialogConfig.id = "newpart-component";
                dialogConfig.height = "86vh";
                dialogConfig.width = "70vw";
                 this.modalDialog = this.matDialog.open(NewPartDialogComponent, dialogConfig);
                break;
        }
    }
    public a(){
        this.modalDialog.afterClosed().subscribe(result => {
            console.log(`Dialog result: ${result}`); // Pizza!
          });
          
          this.modalDialog.close('Pizza!');
    }
}
