// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
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
import { RollConstructor } from '@domain/constructor/Roll.constructor';

@Component({
    selector: 'new-roll-dialog',
    templateUrl: './new-roll-dialog.component.html',
    styleUrls: ['./new-roll-dialog.component.scss']
})
export class NewRollDialogComponent implements OnInit {
    public roll: Coil = new Coil();
    public colors: string[] = [];
    private backendConnections: Subscription[] = [];

    constructor(
        public dialogRef: MatDialogRef<NewRollDialogComponent>,
        private isolationService: IsolationService,
        private backendService: BackendService
    ) { }
    /**
     * Retrieves the list of current rolls to populate the list of materials and colors. Selects should be editable to be able to add to this list.
     */
    public ngOnInit(): void {
        console.log('><[NewRollDialogComponent.ngOnInit]')
        this.roll.id = uuidv4();
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
    public saveRoll(): void {
        console.log('><[NewRollDialogComponent.saveRoll]')
        const newRoll: Coil = new RollConstructor().construct(this.roll);
        this.backendConnections.push(
            this.backendService.apiNewRoll_v1(newRoll, new ResponseTransformer().setDescription('Do HTTP transformation to "Roll".')
                .setTransformation((entrydata: any): Coil => {
                    this.isolationService.infoNotification('Rollo [' + entrydata.id + '] almacenada correctamente.', '/INVENTARIO/NUEVO ROLL/OK')
                    return new Coil(entrydata);
                }))
                .subscribe((persistedRoll: Coil) => {
                    this.closeModal();
                })
        )
    }
    public saveRollAndRepeat() {
        console.log('><[NewRollDialogComponent.saveRoll]')
        const newRoll: Coil = new RollConstructor().construct(this.roll);
        this.backendConnections.push(
            this.backendService.apiNewRoll_v1(newRoll, new ResponseTransformer().setDescription('Do HTTP transformation to "Roll".')
                .setTransformation((entrydata: any): Coil => {
                    this.isolationService.infoNotification('Rollo [' + entrydata.id + '] almacenada correctamente.', '/INVENTARIO/NUEVO ROLL/OK')
                    return new Coil(entrydata);
                }))
                .subscribe((persistedRoll: Coil) => {
                    this.roll.createNewId();
                    this.roll.material = 'PLA';
                    this.roll.color = '';
                    this.roll.weight = undefined;
                })
        );
    }
    public closeModal(): void {
        console.log('>[NewRollDialogComponent.closeModal]')
        this.dialogRef.close();
    }
}
