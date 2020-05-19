// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { V1DockComponent } from '../../panels/v1-dock/v1-dock.component';
import { DialogFactoryService } from '@app/factory/dialog-factory.service';

@Component({
    selector: 'v1-feature-render',
    templateUrl: './v1-feature-render.component.html',
    styleUrls: ['./v1-feature-render.component.scss']
})
export class V1FeatureRenderComponent {
    @Input() dock: V1DockComponent;
    @Input() node: Feature;

    constructor(private dialogFactory: DialogFactoryService) { }

    /**
     * If the Feature is of the type PAGEROUTE then we should send a message to the Dock to report the change on the Feature active.
     * If the feature is of type DIALOG then we can process the click here by opening the dialog requested.
     */
    public onClick() {
        console.log('><[V1FeatureRenderComponent,onClick]> Label: ' + this.node.label)
        if (null != this.node) {
            switch (this.node.interaction) {
                case 'PAGEROUTE':
                    if (null != this.dock) this.dock.activateFeature(this.node);
                    break;
                case 'DIALOG':
                    this.dialogFactory.processClick(this.node);
                    break;
            }
        }
    }
}
