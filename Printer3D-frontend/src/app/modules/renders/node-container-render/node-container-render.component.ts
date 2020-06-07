// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { ICollaboration } from '@app/domain/interfaces/core/ICollaboration.interface';
import { EVariant } from '@app/domain/interfaces/EPack.enumerated';
import { IViewer } from '@app/domain/interfaces/core/IViewer.interface';
import { NeoCom } from '@domain/NeoCom.domain';
import { IColorTheme } from '@domain/interfaces/core/IColorTheme.interface';

@Component({
    selector: 'node-container',
    templateUrl: './node-container-render.component.html',
    styleUrls: ['./node-container-render.component.scss']
})
export class NodeContainerRenderComponent implements IColorTheme {
    @Input() container: IViewer;
    @Input() node: NeoCom;
    @Input() variant: EVariant = EVariant.DEFAULT;
    @Input() colorScheme: string = 'panel-white';  // The name of the panel style to be rendered.

    public getNode(): NeoCom {
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
        this.container.enterSelected(target);
    }
    public toggleExpanded(): void {
        if (null != this.node) {
            console.log('><[Neocom.toggleExpanded]> expand: ' + this.node.toggleExpanded());
            this.container.notifyDataChanged();
        }
    }
    public isExpanded(): boolean {
        if (null != this.node)
            return this.node.isExpanded();
    }
    public isActive():boolean{
        if ( null != this.node)return this.node.isActive();
        else return true;
    }

    // - I C O L O R T H E M E
    /**
     * Return the list of styles that should be applied to the Panel dependin of the state of the associated node.
     * For panels the only color is the border color. More elaborated or actionable panels can have states and then we can return more detailed styles.
     * @returns {string} the list of styles to be applied.
     * @memberof AppPanelComponent
     */
    public getColorSchemePanelStyle(): string {
        // Detect the state configuration.
        if (this.node.isSelected())
            if (this.node.isExpanded())
                return this.getExpandedSelectedPanelStyle();
        if (this.node.isSelected())
            return this.getSelectedPanelStyle();
        if (this.node.isExpanded())
            return this.getExpandedPanelStyle();
        return this.getPanelStyle();
    }

    public getPanelStyle(): string {
        return this.decodeColor(this.node.getThemeColor());
    }
    public getExpandedPanelStyle(): string {
        return this.getPanelStyle() + ' ' + this.getPanelStyle() + '-expanded';
    }
    public getSelectedPanelStyle(): string {
        return this.getPanelStyle() + ' ' + this.getPanelStyle() + '-selected';
    }
    public getExpandedSelectedPanelStyle(): string {
        return this.getPanelStyle() + ' ' + this.getPanelStyle() + '-expanded' + ' ' + this.getPanelStyle() + '-selected';
    }
    private decodeColor(theme: string): string {
        switch (theme) {
            // case ESeparator.WHITE:
            //     return 'panel-white';
            // case ESeparator.GREEN:
            //     return 'panel-green';
            default:
                return 'panel-white';
        }
    }
}
