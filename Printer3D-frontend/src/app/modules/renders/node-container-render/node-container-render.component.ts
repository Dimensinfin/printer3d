// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { ICollaboration } from '@app/domain/interfaces/core/ICollaboration.interface';
import { EVariant } from '@app/domain/interfaces/EPack.enumerated';
import { IViewer } from '@app/domain/interfaces/core/IViewer.interface';
import { Node } from '@domain/Node.domain';
import { IColorTheme } from '@domain/interfaces/core/IColorTheme.interface';
import { BackgroundEnabledComponent } from '@app/modules/shared/core/background-enabled/background-enabled.component';

@Component({
    selector: 'node-container',
    templateUrl: './node-container-render.component.html',
    styleUrls: ['./node-container-render.component.scss']
})
export class NodeContainerRenderComponent extends BackgroundEnabledComponent {
    @Input() container: IViewer;
    @Input() node: Node;
    @Input() variant: EVariant = EVariant.DEFAULT;
    @Input() colorScheme: string = 'panel-white';  // The name of the panel style to be rendered.
    @Input() index: number = 1;
    @Input() autoselect: boolean = false

    public getNode(): Node {
        return this.node;
    }
    public getVariant(): EVariant {
        return this.variant;
    }
    /**
     * Pass the container panel the node that is being entered so if there is additional data it can be exported to another panel.
     * @param target target node that is being entered by the cursor.
     */
    public mouseEnter(target: ICollaboration) {
        if (this.autoselect) this.container.enterSelected(target);
    }
    public toggleExpanded(): void {
        if (null != this.node) {
            if (this.node.isExpandable()) {
                this.node.toggleExpanded()
                this.container.notifyDataChanged();
            }
        }
    }
    public isExpanded(): boolean {
        if (null != this.node)
            return this.node.isExpanded();
    }
    public isActive(): boolean {
        if (null != this.node) return this.node.isActive();
        else return true;
    }
    public isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length == 0) return true;
        return false;
    }
}
