// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { environment } from '@env/environment';
import { Part } from '@domain/Part.domain';
import { Request } from '@domain/Request.domain';
import { IPartProvider } from '@domain/interfaces/IPartProvider.interface';
import { INode } from '@domain/interfaces/INode.interface';
import { Model } from '@domain/inventory/Model.domain';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { forIn } from 'cypress/types/lodash';
import { PartContainer } from '@domain/PartContainer.domain';

@Component({
    selector: 'v3-inventory-page',
    templateUrl: './v3-inventory-page.component.html',
    styleUrls: ['./v3-inventory-page.component.scss']
})
export class V3InventoryPageComponent {
}
