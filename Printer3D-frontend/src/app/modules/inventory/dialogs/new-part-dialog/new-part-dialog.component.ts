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
import { Part } from '@domain/inventory/Part.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { FinishingResponse } from '@domain/dto/FinishingResponse.dto';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { environment } from '@env/environment';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'new-part-dialog',
    templateUrl: './new-part-dialog.component.html',
    styleUrls: ['./new-part-dialog.component.scss']
})
export class NewPartDialogComponent extends BackgroundEnabledComponent implements OnInit, OnDestroy {
    public part: Part = new Part();
    public finishings: Map<string, string[]> = new Map<string, string[]>();
    public materials: string[] = [];
    public colors: string[] = [];
    private dataToPartTransformer: ResponseTransformer;

    constructor(
        public dialogRef: MatDialogRef<NewPartDialogComponent>,
        private isolationService: IsolationService,
        private backendService: BackendService
    ) {
        super()
        this.dataToPartTransformer = new ResponseTransformer().setDescription('Do HTTP transformation to "Part".')
            .setTransformation((entrydata: any): Part => {
                const targetPart: Part = new Part(entrydata);
                this.isolationService.successNotification('Pieza [' + targetPart.composePartIdentifier() + '] registrada correctamente.', '/INVENTARIO/NUEVA PIEZA/OK');
                return targetPart;
            });
    }

    /**
     * To duplicate Parts we are going to use the prepared storage for partially created parts when the backend fails.
     * To duplicate a part we create a new one from the selected one as a template and store on the lcoal storage. When the dialog is open ot will detect the part and edit it instead creating a new one.
     */
    public ngOnInit(): void {
        console.log('>[NewPartDialogComponent.ngOnInit]')
        this.readFinishings();
    }
    // - I N T E R A C T I O N S
    public savePart(): void {
        this.backendConnections.push(
            this.backendService.apiNewPart_v1(this.part, this.dataToPartTransformer)
                .subscribe((persistedPart: Part) => {
                    this.isolationService.removeFromStorage(platformconstants.PARTIAL_PART_KEY) // Clear the part copy
                    this.closeModal();
                }, (error) => {
                    console.log('-[NewPartDialogComponent.savePart.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        );
    }
    public savePartAndRepeat() {
        console.log('>[NewPartDialogComponent.savePartAndRepeat]')
        this.backendConnections.push(
            this.backendService.apiNewPart_v1(this.part, this.dataToPartTransformer)
                .subscribe((persistedPart: Part) => {
                    console.log('>[NewPartDialogComponent.savePartAndRepeat]> Reinitialize the form')
                    this.part.createNewId();
                    this.part.color = undefined;
                }, (error) => {
                    console.log('-[NewPartDialogComponent.savePart.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse)
                            this.isolationService.processException(error)
                })
        );
    }
    public closeModal(): void {
        console.log('>[NewPartDialogComponent.closeModal]')
        this.isolationService.removeFromStorage(platformconstants.PARTIAL_PART_KEY); // Clean the part on edition
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
                    console.log('-[NewPartDialogComponent.readFinishings]> finishings: ' + JSON.stringify(finishings))
                    const data = finishings.getMaterials()
                    for (let index = 0; index < data.length; index++) {
                        const element = data[index];
                        const colors = element['colors'];
                        console.log('-[NewPartDialogComponent.readFinishings]> Adding Finishing: ' + element['material'] + ':' + element['colors'])
                        this.finishings.set(element['material'], colors);
                        this.materials.push(element['material']);
                    }
                    this.loadPartForm()
                })
        );
    }
    protected loadPartForm(): void {
        // If there is no previous pending part then initialize a new one with default values but new ID.
        const pendingPart = this.isolationService.getFromStorage(platformconstants.PARTIAL_PART_KEY);
        console.log('-[NewPartDialogComponent.ngOnInit]> Previous Part: ' + pendingPart)
        if (null == pendingPart) {
            console.log('-[NewPartDialogComponent.ngOnInit]> Initializing Part')
            this.part.id = uuidv4()
            this.part.material = 'PLA'
            this.part.color = undefined
            this.part.stockAvailable = 0
            this.materialSelectorChanged(this.part.material)
        }
        else {
            console.log('-[NewPartDialogComponent.ngOnInit]> Setting Previous Part: ' + pendingPart)
            this.part = new Part(JSON.parse(pendingPart))
            this.part.createNewId() // Create a new id for any new part under creation
            this.part.color = undefined
            this.part.stockAvailable = 0
            this.materialSelectorChanged(this.part.material)
        }
    }
}
