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
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';

@Component({
    selector: 'new-part-dialog',
    templateUrl: './new-part-dialog.component.html',
    styleUrls: ['./new-part-dialog.component.scss']
})
export class NewPartDialogComponent implements OnInit, OnDestroy {
    public part: Part = new Part();
    public finishings: Map<string, string[]> = new Map<string, string[]>();
    public colors: string[] = [];
    private backendConnections: Subscription[] = [];

    constructor(
        public dialogRef: MatDialogRef<NewPartDialogComponent>,
        private isolationService: IsolationService,
        private backendService: BackendService
    ) { }

    public ngOnInit(): void {
        console.log('>[NewPartDialogComponent.ngOnInit]')
        // If there is no previous pending part then initialize a new one with default values but new ID.
        const pendingPart = this.isolationService.getFromStorage(platformconstants.PARTIAL_PART_KEY);
        console.log('-[NewPartDialogComponent.ngOnInit]> Previous Part: ' + pendingPart)
        if (null == pendingPart) {
            this.part.id = uuidv4();
            this.part.material = 'PLA';
            this.part.colorCode = 'INDEFINIDO';
            // this.materialSelectorChanged('PLA');
        }
        else {
            console.log('-[NewPartDialogComponent.ngOnInit]> Previous Part: ' + pendingPart)
            this.part = JSON.parse(pendingPart);
        }
        this.readFinishings();
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
    public savePart(): void {
        // Get the form data.
        const newPart: Part = new PartConstructor().construct(this.part);
        this.backendConnections.push(
            this.backendService.apiNewPart_v1(newPart, new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
                .setTransformation((entrydata: any): Part => {
                    this.isolationService.infoNotification('Pieza [' + entrydata.id + '] almacenada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK')
                    return new Part(entrydata);
                }))
                .subscribe((persistedPart: Part) => {
                    this.closeModal();
                })
        );
    }
    public savePartAndRepeat() {
        console.log('>[NewPartDialogComponent.savePartAndRepeat]')
        // Get the form data.
        const newPart: Part = new PartConstructor().construct(this.part);
        this.backendConnections.push(
            this.backendService.apiNewPart_v1(newPart, new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
                .setTransformation((entrydata: any): Part => {
                    const newPart = new Part(entrydata);
                    this.isolationService.infoNotification('Pieza [' +
                        newPart.composePartIdentifier() +
                        '] almacenada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK');
                    console.log('>[NewPartDialogComponent.savePartAndRepeat]> return the persisted Part');
                    return newPart;
                }))
                .subscribe((persistedPart: Part) => {
                    console.log('>[NewPartDialogComponent.savePartAndRepeat]> Reinitialize the form')
                    this.part.createNewId();
                    this.part.colorCode = 'INDEFINIDO';
                })
        );
    }
    public closeModal(): void {
        console.log('>[NewPartDialogComponent.closeModal]')
        this.dialogRef.close();
    }
    public materialSelectorChanged(event: any): void {
        console.log('>[NewPartDialogComponent.materialSelectorChanged]> Selection: ' + event);
        this.colors = this.finishings.get(event);
    }
    // - B A C K G R O U N D
    protected readFinishings(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetFinishings_v1(new ResponseTransformer().setDescription('Do HTTP transformation to "Finishing" list.')
                .setTransformation((entrydata: any): FinishingResponse => {
                    return new FinishingResponse(entrydata);
                }))
                .subscribe((finishings: FinishingResponse) => {
                    console.log('>[NewPartDialogComponent.readFinishings]> finishings: ' + JSON.stringify(finishings))
                    const data = finishings.getMaterials()
                    for (let index = 0; index < data.length; index++) {
                        const element = data[index];
                        const colors = element['colors'];
                        colors.push('INDEFINIDO')
                        this.finishings.set(element['material'], colors);
                    }
                    console.log('>[NewPartDialogComponent.readFinishings]> finishings: ' + JSON.stringify(this.finishings))
                    this.materialSelectorChanged(data[0]['material'])
                })
        );
    }
}
