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
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';
import { Part4Request } from '@domain/Part4Request.domain';

@Component({
    selector: 'v1-part4-request',
    templateUrl: './v1-part4-request-render.component.html',
    styleUrls: ['./v1-part4-request-render.component.scss']
})
export class V1Part4RequestRenderComponent extends NodeContainerRenderComponent {
    public getNode(): Part4Request {
        return this.node as Part4Request
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public getMissing(): number {
        return this.getNode().getMissed();
    }
    public getRequired(): number {
        return this.getNode().getRequired()
    }
    public getLabel(): string {
        const part = this.node as Part;
        return part.label;
    }
    public getMaterial(): string {
        const part = this.node as Part;
        return part.material;
    }
    public getColor(): string {
        const part = this.node as Part;
        return part.color;
    }
    public removePart(): void {
        const newRequestPanelAsAny = this.container as any;
        const newRequestPanel = newRequestPanelAsAny as V1NewRequestPanelComponent
        newRequestPanel.removePart(this.getNode())
    }
}
