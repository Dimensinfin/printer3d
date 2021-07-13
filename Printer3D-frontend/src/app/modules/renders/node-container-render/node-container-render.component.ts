// - CORE
import { Component, Input } from '@angular/core'
// - DOMAIN
import { IViewer } from '@app/domain/interfaces/core/IViewer.interface'
import { Node } from '@domain/Node.domain'
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component'
import { ISelectable } from '@domain/interfaces/core/ISelectable.interface'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'

@Component({
    selector: 'node-container',
    templateUrl: './node-container-render.component.html',
    styleUrls: ['./node-container-render.component.scss']
})
export class NodeContainerRenderComponent extends BackgroundEnabledComponent {
    @Input() container: IViewer
    @Input() node: Node
    @Input() variant: string = Printer3DConstants.DEFAULT_VARIANT
    @Input() index: number = 1
    @Input() selectOnHover: boolean = false

    // - G E T T E R S
    public getNode(): Node {
        return this.node
    }
    public getVariant(): string {
        return this.variant
    }
    /**
     * Pass the container panel the node that is being entered so if there is additional data it can be exported to another panel.
     * @param target target node that is being entered by the cursor.
     */
    public mouseEnter(target: any) {
        if (this.selectOnHover) this.container.addSelection(target as ISelectable)
    }
    public toggleExpanded(): void {
        if (null != this.node) {
            if (this.node.isExpandable()) {
                this.node.toggleExpanded()
                this.container.notifyDataChanged()
            }
        }
    }
    public select(): void {
        this.node.select()
        this.container.addSelection(this.node)
    }
    public unselect(): void {
        this.node.unselect()
        this.container.subtractSelection(this.node)
    }
    public isExpanded(): boolean {
        if (null != this.node)
            return this.node.isExpanded()
    }
    public isActive(): boolean {
        if (null != this.node) return this.node.isActive()
        else return true
    }
    public isEmpty(target?: any): boolean {
        if (null == target) return true
        if (Object.keys(target).length == 0) return true
        return false
    }
}
