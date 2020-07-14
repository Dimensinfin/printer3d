// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { IsolationService } from '@app/platform/isolation.service';
import { platformconstants } from '@app/platform/platform-constants';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { MachineListResponse } from '@domain/dto/MachineListResponse.dto';
import { Machine } from '@domain/Machine.domain';
import { PendingJobListResponse } from '@domain/dto/PendingJobListResponse.dto';
import { Job } from '@domain/Job.domain';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { environment } from '@env/environment';
import { Part } from '@domain/Part.domain';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { Request } from '@domain/Request.domain';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'delete-confirmation-dialog',
    templateUrl: './delete-confirmation-dialog.component.html',
    styleUrls: ['./delete-confirmation-dialog.component.scss']
})
export class DeleteConfirmationDialogComponent extends BackgroundEnabledComponent {
    private request: Request

    constructor(
        public dialogRef: MatDialogRef<DeleteConfirmationDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        protected isolationService: IsolationService,
        protected backendService: BackendService
    ) {
        super()
        this.request = data.request;
    }

    public requestName(): string {
        return this.request.getLabel()
    }
    public deleteRequest(): void {
        document.cookie = 'apiProductionGetOpenRequests_v2' + "=" + '404-REQUEST_NOT_FOUND'
        this.backendConnections.push(
            this.backendService.apiProductionDeleteRequest_v1(this.request.getId(),
                new ResponseTransformer().setDescription('Do HTTP transformation to "Request".')
                    .setTransformation((entrydata: any): any => {
                        this.isolationService.infoNotification(
                            'Pedido [' + this.request.getLabel() + '] borrado satisfactoriamente.',
                            '/PRODUCCION/PEDIDO/OK');
                        return entrydata;
                    }))
                .subscribe((request: any) => {
                    this.dialogRef.close();
                }, (error) => {
                    console.log('-[V1RequestRenderComponent.deleteRequest.exception]> Error message: ' + JSON.stringify(error.error))
                    if (environment.showexceptions)
                        if (error instanceof HttpErrorResponse) {
                            if (error.error.status == 404) {
                                this.isolationService.errorNotification('Endpoint [' + error.error.path + '] not found on server.', '404 NOT FOUND')
                            } else {
                                const errorInfo: string = error.error.errorInfo
                                const httpStatus: string = error.error.httpStatus
                                const message: string = this.isolationService.exceptionMessageMap(error.error)
                                this.isolationService.errorNotification(message, errorInfo)
                            }
                        }
                })
        )

    }
    public closeModal(): void {
        console.log('>[NewCoilDialogComponent.closeModal]')
        this.dialogRef.close();
    }
}
