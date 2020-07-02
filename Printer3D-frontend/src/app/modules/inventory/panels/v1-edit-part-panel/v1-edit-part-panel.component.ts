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
import { ModelForm } from '@domain/inventory/ModelForm.domain';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { RequestFormToRequestConverter } from '@domain/converter/RequestFormToRequest.converter';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { ModelFormToModelRequestConverter } from '@domain/converter/ModelFormToModelRequest.converter';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';
import { Model } from '@domain/inventory/Model.domain';

@Component({
    selector: 'v1-edit-part-panel',
    templateUrl: './v1-edit-part-panel.component.html',
    styleUrls: ['./v1-edit-part-panel.component.scss']
})
export class V1EditPartPanelComponent {
    @Input() visible: boolean
    @ViewChild(V1DropPartPanelComponent) public partContainer: V1DropPartPanelComponent;
    public model: ModelForm = new ModelForm().generateNewId();
    public editing: boolean = false

    public isFormValid(): boolean {
        return true
    }
    public savePart(): void { }
}
