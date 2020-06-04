// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
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
import { Coil } from '@domain/Coil.domain';
import { CoilConstructor } from '@domain/constructor/Coil.constructor';

const dataToCoilTransformer: ResponseTransformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Roll".')
    .setTransformation((entrydata: any): Coil => {
        const targetCoil: Coil = new Coil(entrydata);
        this.isolationService.successNotification('Rollo [' + targetCoil.getCoildIdentifier() + '] almacenado correctamente.', '/INVENTARIO/NUEVO ROLL/OK');
        return targetCoil;
    });
    
@Component({
    selector: 'new-coil-dialog',
    templateUrl: './new-coil-dialog.component.html',
    styleUrls: ['./new-coil-dialog.component.scss']
})
export class NewCoilDialogComponent implements OnInit, OnDestroy {
    public coil: Coil = new Coil();
    public colors: string[] = [];
    private backendConnections: Subscription[] = [];

    constructor(
        public dialogRef: MatDialogRef<NewCoilDialogComponent>,
        private isolationService: IsolationService,
        private backendService: BackendService
    ) { }
    /**
     * Retrieves the list of current rolls to populate the list of materials and colors. Selects should be editable to be able to add to this list.
     */
    public ngOnInit(): void {
        console.log('><[NewCoilDialogComponent.ngOnInit]')
        this.coil.id = uuidv4();
    }
    /**
     * Unsubscribe from any open subscription made to the backend.
     */
    public ngOnDestroy(): void {
        this.backendConnections.forEach(element => {
            element.unsubscribe();
        });
    }
    // - I N T E R A C T I O N S
    public saveCoil(): void {
        console.log('><[NewCoilDialogComponent.saveCoil]')
        const newRoll: Coil = new CoilConstructor().construct(this.coil);
        this.backendConnections.push(
            this.backendService.apiNewCoil_v1(newRoll, dataToCoilTransformer)
                .subscribe((persistedRoll: Coil) => {
                    this.closeModal();
                })
        )
    }
    public saveCoilAndRepeat() {
        console.log('><[NewCoilDialogComponent.saveCoilAndRepeat]')
        const newRoll: Coil = new CoilConstructor().construct(this.coil);
        this.backendConnections.push(
            this.backendService.apiNewCoil_v1(newRoll, dataToCoilTransformer)
                .subscribe((persistedRoll: Coil) => {
                    console.log('><[NewCoilDialogComponent.saveCoilAndRepeat]> Restarting form')
                    this.coil.createNewId();
                    this.coil.material = 'PLA';
                    this.coil.color = '';
                    this.coil.weight = undefined;
                })
        );
    }
    public closeModal(): void {
        console.log('>[NewCoilDialogComponent.closeModal]')
        this.dialogRef.close();
    }
}
