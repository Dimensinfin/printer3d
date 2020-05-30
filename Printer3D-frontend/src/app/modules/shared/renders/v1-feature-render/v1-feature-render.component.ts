// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { Feature } from '@domain/Feature.domain';
import { V1DockComponent } from '../../panels/v1-dock/v1-dock.component';
import { DialogFactoryService } from '@app/services/dialog-factory.service';
import { IsolationService } from '@app/platform/isolation.service';
import { platformconstants } from '@app/platform/platform-constants';

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
                    // case 'ACTION-TO-DEFINE':
                    //     this.isolationService.removeFromStorage(platformconstants.DOCK_CURRENT_CONFIGURATION_KEY)
                    //     if (null != this.dock) this.dock.clean();
                    //     if (null != this.dock) this.dock.activateFeature(this.node);
                    //     break;
                }
        }
    }
}
