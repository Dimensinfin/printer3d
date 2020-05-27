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
    public colors: string[] = [];
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
        if (null == pendingPart) {
            this.part.id = uuidv4();
            this.part.material = 'PLA';
            this.part.colorCode = 'INDEFINIDO';
            this.materialSelectorChanged('PLA');
        }
        else this.part = JSON.parse(pendingPart);
    }
    // - I N T E R A C T I O N S
    public savePart(): void {
        // Get the form data.
        const newPart: Part = new PartConstructor().construct(this.part);
        this.backendService.apiNewPart_v1(newPart, new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): Part => {
                this.isolationService.infoNotification('Pieza [' + entrydata.id + '] almacenada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK')
                return new Part(entrydata);
            }))
            .subscribe((persistedPart: Part) => {
                this.closeModal();
            });
    }
    public savePartAndRepeat() {
        // Get the form data.
        const newPart: Part = new PartConstructor().construct(this.part);
        this.backendService.apiNewPart_v1(newPart, new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): Part => {
                this.isolationService.infoNotification('Pieza [' +
                    entrydata.composePartIdentifier() +
                    '] almacenada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK')
                return new Part(entrydata);
            }))
            .subscribe((persistedPart: Part) => {
                this.part.createNewId();
                this.part.colorCode = 'GREEN';
            });
    }
    public closeModal(): void {
        console.log('>[NewPartDialogComponent.closeModal]')
        this.dialogRef.close();
    }
    public materialSelectorChanged(event: any): void {
        console.log('>[NewPartDialogComponent.materialSelectorChanged]> Selection: ' + event);
        if (event == 'PLA') {
            this.colors = ['INDEFINIDO', 'BLANCO', 'VERDE', 'ROSA-T', 'GRIS'];
        }
        if (event == 'TPU') {
            this.colors = ['INDEFINIDO', 'ROJO'];
        }
    }
}
