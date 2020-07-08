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
import { EVariant, RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';
import { Part4Request } from '@domain/Part4Request.domain';
import { RequestItem } from '@domain/RequestItem.domain';

@Component({
    selector: 'v1-request-item',
    templateUrl: './v1-request-item-render.component.html',
    styleUrls: ['./v1-request-item-render.component.scss']
})
export class V1RequestItemRenderComponent extends NodeContainerRenderComponent {
    public getNode(): RequestItem {
        return this.node as RequestItem
    }
    public getUniqueId(): string {
        return this.getNode().getId()
    }
    public hasMissing(): boolean {
        return this.getNode().getMissing() > 0
    }
    public getMissing(): string {
        if (this.hasMissing()) return this.getNode().getMissing() + '';
        else return '-'
    }
    public getRequired(): number {
        return this.getNode().getQuantity()
    }
    public getLabel(): string {
        return this.getNode().getContent().getLabel()
    }
    public getPrice(): string {
        return this.getNode().getPrice() * this.getNode().getQuantity() + " €"
    }
    public getMaterial(): string {
        if (this.getNode().getType() == RequestContentType.PART) {
            const part: Part = this.getNode().getContent() as any
            return part.getMaterial()
        } else return "-"
    }
    public getColor(): string {
        if (this.getNode().getType() == RequestContentType.PART) {
            const part: Part = this.getNode().getContent() as any
            return part.getColor()
        } else return "-"
    }
    // - I N T E R A C T I O N S
    public removeContent(): void {
        const newRequestPanelAsAny = this.container as any;
        const newRequestPanel = newRequestPanelAsAny as V1NewRequestPanelComponent
        newRequestPanel.removeContent(this.getNode())
    }
}
