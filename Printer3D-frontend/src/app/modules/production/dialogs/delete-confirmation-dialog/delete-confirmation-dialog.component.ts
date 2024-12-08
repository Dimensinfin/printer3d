// - CORE
import { Component } from '@angular/core';
import { Inject } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA } from '@angular/material/legacy-dialog';
// - SERVICES
import { ProductionService } from '../../service/production.service';
// - DOMAIN
import { IsolationService } from '@app/platform/isolation.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto';

@Component({
    selector: 'delete-confirmation-dialog',
    templateUrl: './delete-confirmation-dialog.component.html',
    styleUrls: ['./delete-confirmation-dialog.component.scss']
})
export class DeleteConfirmationDialogComponent extends BackgroundEnabledComponent {
    private request: CustomerRequestResponse

    constructor(
        public dialogRef: MatDialogRef<DeleteConfirmationDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        protected isolationService: IsolationService,
        protected productionService: ProductionService
    ) {
        super()
        console.log('> data: ' + JSON.stringify(data))
        this.request = data.request;
    }

    public requestName(): string {
        return this.request.getLabel()
    }
    public deleteRequest(): void {
        this.backendConnections.push(
            this.productionService.apiv3_ProductionDeleteRequest(this.request.getId())
                .subscribe((request: any) => {
                    this.isolationService.infoNotification(
                        'Pedido [' + this.request.getLabel() + '] borrado satisfactoriamente.',
                        '/PRODUCCION/PEDIDO/OK')
                    this.dialogRef.close('DELETED');
                })
        )

    }
    public closeModal(): void {
        console.log('>[NewCoilDialogComponent.closeModal]')
        this.dialogRef.close('CANCELED');
    }
}
