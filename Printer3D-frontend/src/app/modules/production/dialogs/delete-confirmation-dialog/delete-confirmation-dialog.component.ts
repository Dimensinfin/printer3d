// - CORE
import { Component } from '@angular/core';
import { Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { IsolationService } from '@app/platform/isolation.service';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { CustomerRequest } from '@domain/production/CustomerRequest.domain';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'delete-confirmation-dialog',
    templateUrl: './delete-confirmation-dialog.component.html',
    styleUrls: ['./delete-confirmation-dialog.component.scss']
})
export class DeleteConfirmationDialogComponent extends BackgroundEnabledComponent {
    private request: CustomerRequest

    constructor(
        public dialogRef: MatDialogRef<DeleteConfirmationDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        protected isolationService: IsolationService,
        protected backendService: BackendService
    ) {
        super()
        console.log('> data: '+JSON.stringify(data))
        this.request = data.request;
    }

    public requestName(): string {
        return this.request.getLabel()
    }
    public deleteRequest(): void {
        this.backendConnections.push(
            this.backendService.apiProductionDeleteRequest_v2(this.request.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
                    .setTransformation((entrydata: any): any => {
                        this.isolationService.infoNotification(
                            'Pedido [' + this.request.getLabel() + '] borrado satisfactoriamente.',
                            '/PRODUCCION/PEDIDO/OK');
                        return entrydata;
                    }))
                .subscribe((request: any) => {
                    this.dialogRef.close('DELETED');
                })
        )

    }
    public closeModal(): void {
        console.log('>[NewCoilDialogComponent.closeModal]')
        this.dialogRef.close('CANCELED');
    }
}
