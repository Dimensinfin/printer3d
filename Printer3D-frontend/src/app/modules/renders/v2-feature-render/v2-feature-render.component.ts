// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { V1DockComponent } from '../../common/v1-dock/v1-dock.component';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { IsolationService } from '@app/platform/isolation.service';
import { platformconstants } from '@app/platform/platform-constants';

@Component({
    selector: 'v2-feature-render',
    templateUrl: './v2-feature-render.component.html',
    styleUrls: ['./v2-feature-render.component.scss']
})
export class V2FeatureRenderComponent {
    @Input() dock: V1DockComponent;
    @Input() node: Feature;

    constructor(private dialogFactory: DialogFactoryService) { }

    public isMarkVisible(): boolean {
        if (this.node.interaction == 'DIALOG') return true
        if (this.node.modifier == 'DROP') return true
        return false
    }
    public getLabel(): string {
        return this.node.label
    }
    /**
     * If the Feature is of the type PAGEROUTE then we should send a message to the Dock to report the change on the Feature active.
     * If the feature is of type DIALOG then we can process the click here by opening the dialog requested.
     */
    public onClick() {
        if (null != this.node) {
            console.log('><[V1FeatureRenderComponent,onClick]> Label: ' + this.node.label)
            if (this.node.enabled) // Only interact with enabled Features
                switch (this.node.interaction) {
                    case 'PAGEROUTE':
                        console.log('><[V1FeatureRenderComponent,onClick]> PAGEROUTE')
                        if (null != this.dock) this.dock.activateFeature(this.node);
                        break;
                    case 'DIALOG':
                        console.log('><[V1FeatureRenderComponent,onClick]> DIALOG')
                        this.node.activate();
                        const dialogRef = this.dialogFactory.processClick(this.node);
                        dialogRef.afterClosed()
                            .subscribe(result => {
                                console.log('[V1FeatureRenderComponent.onClick]> Close detected');
                                this.node.deactivate();
                            });
                        break;
                }
        }
    }
}
