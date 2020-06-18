// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ElementRef } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
import { ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { NodeContainerRenderComponent } from '../node-container-render/node-container-render.component';
import { Request } from '@domain/Request.domain';
import { EVariant, RequestState } from '@domain/interfaces/EPack.enumerated';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { IsolationService } from '@app/platform/isolation.service';
import { V1NewRequestPanelComponent } from '@app/modules/production/panels/v1-new-request-panel/v1-new-request-panel.component';

@Component({
    selector: 'v1-request',
    templateUrl: './v1-request-render.component.html',
    styleUrls: ['./v1-request-render.component.scss']
})
export class V1RequestRenderComponent extends NodeContainerRenderComponent {
    // @ViewChild('requestInstance', { static: true }) requestInstance: ElementRef;

    public identifier: string = 'kjqwhkjlfhkqwjhckjqjkchkqj'

    // public ngOnInit() : void {
    //     this.requestInstance.nativeElement.setAttribute('cy-id', this.identifier);
    // }
    public getUniqueId(): string {
        // this.requestInstance.nativeElement.setAttribute('cy-id', this.identifier);
        const request = this.node as Request
        return request.getId();
    }
    public getRequestDate(): Date {
        const request = this.node as Request
        return request.getRequestDate();
    }
    public getLabel(): string {
        // this.requestInstance.nativeElement.setAttribute('cy-id', this.identifier);
        const request = this.node as Request
        return request.getLabel();
    }
    public getPartCount(): string {
        const request = this.node as Request
        return request.getPartCount() + '';
    }
    public getAmount(): string {
        const request = this.node as Request
        return request.getAmount();
    }
    public isOpen(): boolean {
        const request = this.node as Request
        return (request.getState() == RequestState.OPEN)
    }
    public completeRequest(): void {

    }
}
