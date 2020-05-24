// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { platformconstants } from '@app/platform/platform-constants';
import { v4 as uuidv4 } from 'uuid';
// - MATERIAL
import { MatDialogConfig } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';

@Component({
    selector: 'new-part-dialog',
    templateUrl: './new-part-dialog.component.html',
    styleUrls: ['./new-part-dialog.component.scss']
})
export class NewPartDialogComponent implements OnInit {
    public downloading: boolean = true;
    public development: boolean = false;
    public part: PartRecord = new PartRecord();
    public label;
    public cost;
    public price;
    public stockLevel;
    public imagePath;
    public modelPath;

    constructor(
        public dialogRef: MatDialogRef<NewPartDialogComponent>,
        private isolationService: IsolationService) { }

    public ngOnInit(): void {
        console.log('><[NewPartDialogComponent.ngOnInit]')
        // If there is no previous pending part then initialize a new one with default values but new ID.
        const pendingPart = this.isolationService.getFromStorage(platformconstants.PARTIAL_PART_KEY);
        if (null == pendingPart) this.part.id = uuidv4();
        else this.part = JSON.parse(pendingPart);
    }

    public actionFunction() { }
    public closeModal(): void {
        console.log('>[NewPartDialogComponent.closeModal]')
        this.dialogRef.close();
    }
    public onIdentificadorBlur() { }
    public onSubmit(): void {
        console.log(">> [NewServicePanelComponent.onSubmit]");
        // if (this.allFieldsValid()) {
        //   // Filter the data. In the case of Services be sure that the specility and the apellidos are clear.
        //   if (this.isService()) {
        //     this.service.apellidos = '';
        //     this.service.especialidad = 'Radiología';  // Speciality can never be empty.
        //   }
        //   // To create a new medico we should get access to the current Centro.
        //   let centro = this.appStoreService.accessCredential().getCentro();
        //   this.backendService.backendCreateConsultaMedico(centro, this.service)
        //     .subscribe((savedService) => {
        //       // Clear the cache of services so they should progress to a backend call.
        //       this.appStoreService.clearDoctors();
        //       this.finished.emit(this);
        //     }), (error) => {
        //       console.log("-- [NewServicePanelComponent.onSubmit]> Error: " + error.message);
        //     };
        // }
        console.log("<< [NewServicePanelComponent.onSubmit]");
    }
    get diagnostic() { return JSON.stringify(this.part); }
}
