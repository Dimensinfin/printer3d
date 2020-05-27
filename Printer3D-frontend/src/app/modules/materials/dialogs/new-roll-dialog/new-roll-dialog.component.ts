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
import { Roll } from '@domain/Roll.domain';

@Component({
    selector: 'new-roll-dialog',
    templateUrl: './new-roll-dialog.component.html',
    styleUrls: ['./new-roll-dialog.component.scss']
})
export class NewRollDialogComponent implements OnInit {
    public roll: Roll = new Roll();
    public colors: string[] = [];
    constructor(
        public dialogRef: MatDialogRef<NewRollDialogComponent>,
        private isolationService: IsolationService,
        private backendService: BackendService
    ) {}
    /**
     * Retrieves the list of current rolls to populate the list of materials and colors. Selects should be editable to be able to add to this list.
     */
    public ngOnInit(): void {
        console.log('><[NewRollDialogComponent.ngOnInit]')
        this.roll.id = uuidv4();
    }
    // - I N T E R A C T I O N S
    public savePart(): void {
    }
    public savePartAndRepeat() {
    }
    public closeModal(): void {
        console.log('>[NewRollDialogComponent.closeModal]')
        this.dialogRef.close();
    }
}
