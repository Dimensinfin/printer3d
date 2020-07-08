// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Part } from '@domain/Part.domain';
import { EVariant } from '@domain/interfaces/EPack.enumerated';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { Coil } from '@domain/Coil.domain';

@Component({
    selector: 'v1-coil',
    templateUrl: './v1-coil-render.component.html',
    styleUrls: ['./v1-coil-render.component.scss']
})
export class V1CoilRenderComponent extends NodeContainerRenderComponent {
    public getNode(): Coil {
        return this.node as Coil
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getMaterial(): string {
        return this.getNode().material;
    }
    public getColor(): string {
        return this.getNode().color;
    }
    public getWeight(): string {
        return this.getNode().weight + ' gr.';
    }
}
