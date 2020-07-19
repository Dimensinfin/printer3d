// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
// - DOMAIN
import { CoilListResponse } from '@domain/dto/CoilListResponse.dto';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
import { V1CoilsPanelComponent } from '../../panels/v1-coils-panel/v1-coils-panel.component';

@Component({
    selector: 'v2-coil-list-page',
    templateUrl: './v2-coil-list-page.component.html',
    styleUrls: ['./v2-coil-list-page.component.scss']
})
export class V2CoilListPageComponent implements Refreshable{  
    @ViewChild(V1CoilsPanelComponent) private coilsPanel: V1CoilsPanelComponent;
      // - R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        if (null != this.coilsPanel) this.coilsPanel.refresh();
    }
 }
