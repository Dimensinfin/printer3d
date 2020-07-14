// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { GridColumn } from '@domain/GridColumn.domain';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { PartListResponse } from '@domain/dto/PartListResponse.dto';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component';
import { PartContainer } from '@domain/PartContainer.domain';
import { environment } from '@env/environment';
import { Coil } from '@domain/Coil.domain';
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';

@Component({
    selector: 'v2-coil-list-page',
    templateUrl: './v2-coil-list-page.component.html',
    styleUrls: ['./v2-coil-list-page.component.scss']
})
export class V2CoilListPageComponent { }
