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
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Part } from '@domain/Part.domain';
import { PartConstructor } from '@domain/constructor/Part.constructor';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';

@Component({
    selector: 'new-part-dialog',
    templateUrl: './new-part-dialog.component.html',
    styleUrls: ['./new-part-dialog.component.scss']
})
export class NewPartDialogComponent implements OnInit {
    public downloading: boolean = true;
    public development: boolean = false;
    public part: Part = new Part();
    public label;
    public cost;
    public price;
    public stockLevel;
    public imagePath;
    public modelPath;

    constructor(
        public dialogRef: MatDialogRef<NewPartDialogComponent>,
        private isolationService: IsolationService,
        private backendService: BackendService
    ) { }

    public ngOnInit(): void {
        console.log('><[NewPartDialogComponent.ngOnInit]')
        // If there is no previous pending part then initialize a new one with default values but new ID.
        const pendingPart = this.isolationService.getFromStorage(platformconstants.PARTIAL_PART_KEY);
        if (null == pendingPart) this.part.id = uuidv4();
        else this.part = JSON.parse(pendingPart);
    }
    // - I N T E R A C T I O N S
    public savePart(): void {
        // Get the form data.
        const newPart: Part = new PartConstructor().construct(this.part);
        this.backendService.apiNewPart_v1(newPart, new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): void => {
                const persistedPart = new Part(entrydata);
                this.closeModal();
            })
        )
    }
    public savePartAndRepeat() {
        // Get the form data.
        const newPart: Part = new PartConstructor().construct(this.part);
        this.backendService.apiNewPart_v1(newPart, new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): void => {
                const persistedPart = new Part(entrydata);
                this.part.colorCode = 'UNDEFINED';
            })
        )
    }
    public closeModal(): void {
        console.log('>[NewPartDialogComponent.closeModal]')
        this.dialogRef.close();
    }
}
