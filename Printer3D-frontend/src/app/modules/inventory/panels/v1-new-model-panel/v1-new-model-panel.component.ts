// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { V1DropPartPanelComponent } from '@app/modules/common/v1-drop-part-panel/v1-drop-part-panel.component';
import { ModelForm } from '@domain/ModelForm.domain';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { RequestFormToRequestConverter } from '@domain/converter/RequestFormToRequest.converter';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { ModelFormToModelRequestConverter } from '@domain/converter/ModelFormToModelRequest.converter';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';
import { Model } from '@domain/inventory/Model.domain';

@Component({
    selector: 'v1-new-model-panel',
    templateUrl: './v1-new-model-panel.component.html',
    styleUrls: ['./v1-new-model-panel.component.scss']
})
export class V1NewModelPanelComponent extends BackgroundEnabledComponent {
    @Input() visible: boolean
    @ViewChild(V1DropPartPanelComponent) public partContainer: V1DropPartPanelComponent;
    public model: ModelForm = new ModelForm();
    public editing: boolean = false

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        super();
    }

    public startEditing(model2Edit: Model): void {
        console.log('>[V1NewModelPanelComponent.startEditing]')
        this.model = new ModelForm(model2Edit)
        this.visible = true
        this.editing = true
        if (null != this.partContainer) this.partContainer.startEditing(model2Edit.getParts())
    }
    public isFormValid(formState: boolean): boolean {
        if (null != this.partContainer)
            return formState && (this.partContainer.getDroppedParts().length > 0)
        else return false
    }
    public saveModel(): void {
        const model: ModelRequest = new ModelFormToModelRequestConverter(this.partContainer.getPartIdList()).convert(this.model)
        if (this.editing)
            this.backendConnections.push(
                this.backendService.apiInventoryUpdateModel_v1(model,
                    new ResponseTransformer().setDescription('Do HTTP transformation to "Request" dto instance from response.')
                        .setTransformation((entrydata: any): any => {
                            this.isolationService.successNotification('Modelo [' + this.model.label + '] registrado correctamente.', '/PRODUCCION/NUEVO MODELO/OK');
                            return new ModelForm(); // Discard the just persisted request and return an empty instance.
                        }))
                    .subscribe((newModel: ModelForm) => {
                        this.stopEditing()
                    })
            )
        else
            this.backendConnections.push(
                this.backendService.apiNewModel_v1(model,
                    new ResponseTransformer().setDescription('Do HTTP transformation to "Request" dto instance from response.')
                        .setTransformation((entrydata: any): any => {
                            this.isolationService.successNotification('Modelo [' + this.model.label + '] registrado correctamente.', '/PRODUCCION/NUEVO MODELO/OK');
                            return new ModelForm(); // Discard the just persisted request and return an empty instance.
                        }))
                    .subscribe((newModel: ModelForm) => {
                        console.log('>[V1NewModelPanelComponent.saveModel]> Clear the page')
                        this.router.navigate(['/']);
                    })
            )
    }
    protected stopEditing(): void {
        this.editing = false
        this.visible = false
    }
}
