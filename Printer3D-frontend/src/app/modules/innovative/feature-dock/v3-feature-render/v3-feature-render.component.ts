// - CORE
import { Component } from '@angular/core'
import { Input } from '@angular/core'
import { DialogFactoryService } from '@app/services/dialog-factory.service'
import { DockService } from '@app/services/dock.service'
import { Feature } from '../domain/Feature.domain'
// - INNOVATIVE
// import { DialogFactoryService } from '@bit/innovative.mvc-components.dock-service/dialog-factory.service'
// import { DockService } from '@bit/innovative.mvc-components.dock-service/dock.service'
// import { Feature } from '@domain/Feature.domain'
// - DOMAIN
// import { Feature } from '../domain/Feature.domain'

@Component({
    selector: 'v2-feature',
    templateUrl: './v3-feature-render.component.html',
    styleUrls: ['./v3-feature-render.component.scss']
})
export class V3FeatureRenderComponent {
    @Input() node: Feature
    @Input() variant: string
    private isMenuActive: boolean = false

    constructor(
        private dialogFactory: DialogFactoryService,
        private dockService: DockService) { }

    public getVariant(): string {
        return this.variant
    }
    public getUniqueId(): string {
        if (this.node) return this.getNode().getUniqueId()
        else return '-'
    }
    public getNode(): Feature {
        if (this.node) return this.node
        else throw new Error('The component still has not the node.')
    }
    public isMarkVisible(): boolean {
        if (this.getNode().interaction == 'DIALOG') return true
        if (this.getNode().modifier == 'DROP') return true
        return false
    }
    public hasMenu(): boolean {
        return this.getNode().ifHasMenu()
    }
    public getChildFeatures(): Feature[] {
        if (this.hasMenu()) return this.getNode().getChildFeatures()
        else return []
    }
    public getLabel(): string {
        return this.getNode().getLabel()
    }
    /**
     * If the Feature is of the type PAGEROUTE then we should send a message to the Dock to report the change on the Feature active.
     * If the feature is of type DIALOG then we can process the click here by opening the dialog requested.
     */
    public onClick() {
        if (this.node) {
            console.log('>[V3FeatureRenderComponent.onClick]> Label: ' + this.getNode().getLabel())
            if (this.getNode().isEnabled()) // Only interact with enabled Features
                switch (this.getNode().interaction) {
                    case 'PAGEROUTE':
                        console.log('-[V3FeatureRenderComponent.onClick]> PAGEROUTE')
                        this.dockService.activateFeature(this.getNode())
                        break
                    case 'DIALOG':
                        console.log('-[V3FeatureRenderComponent.onClick]> DIALOG')
                        this.getNode().activate()
                        const dialogRef = this.dialogFactory.processClick(this.getNode())
                        dialogRef?.afterClosed()
                            .subscribe(result => {
                                console.log('-[V3FeatureRenderComponent.onClick]> Close detected')
                                this.getNode().deactivate()
                            })
                        break
                }
        }
    }
    public activateMenu() {
        this.isMenuActive = true
    }
    public deactivateMenu() {
        this.isMenuActive = false
    }
    public isActive(): boolean {
        // console.log('menu state: ' + this.isMenuActive)
        return this.isMenuActive
    }
}
