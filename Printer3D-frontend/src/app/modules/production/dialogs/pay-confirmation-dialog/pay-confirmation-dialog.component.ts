// - CORE
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
// - SERVICES
import { ProductionService } from '../../service/production.service';
// - DOMAIN
import { IsolationService } from '@app/platform/isolation.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { MatDialogRef } from '@angular/material/dialog';
import { CustomerRequestResponse } from '../../domain/dto/CustomerRequestResponse.dto';

@Component({
    selector: 'pay-confirmation-dialog',
    templateUrl: './pay-confirmation-dialog.component.html',
    styleUrls: ['./pay-confirmation-dialog.component.scss']
})
export class PayConfirmationDialogComponent extends BackgroundEnabledComponent {
    private request: CustomerRequestResponse

    constructor(
        public dialogRef: MatDialogRef<PayConfirmationDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        protected isolationService: IsolationService,
        protected productionService: ProductionService
    ) {
        super()
        this.request = data.request;
    }

    public requestName(): string {
        return this.request.getLabel()
    }
    public paydRequest(): void {
        this.backendConnections.push(
            this.productionService.apiv3_ProductionDeleteRequest(this.request.getId())
                .subscribe((request: any) => {
                    this.isolationService.infoNotification(
                        'Pedido [' + this.request.getLabel() + '] pagado correctamente.',
                        '/PRODUCCION/PEDIDO/OK')
                    this.dialogRef.close('PAYD');
                })
        )

    }
    public closeModal(): void {
        console.log('>[PayConfirmationDialogComponent.closeModal]')
        this.dialogRef.close('CANCELED');
    }
}
