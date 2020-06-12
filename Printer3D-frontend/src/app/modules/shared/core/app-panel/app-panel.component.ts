// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { EVariant } from '@app/domain/interfaces/EPack.enumerated';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';
import { BackendService } from '@app/services/backend.service';
import { BackgroundEnabledComponent } from '../background-enabled/background-enabled.component';
@Component({
    selector: 'app-panel',
    templateUrl: './app-panel.component.html',
    styleUrls: ['./app-panel.component.scss']
})
export class AppPanelComponent extends BackgroundEnabledComponent implements IViewer {
    @Input() self: IViewer;
    @Input() variant: EVariant = EVariant.DEFAULT;
    protected downloading: boolean = true;
    protected dataModelRoot: ICollaboration[] = [];
    private renderNodeList: ICollaboration[] = [];
    private target: ICollaboration;

    constructor() {
        super()
        this.self = this;
    }

    // - GETTERS & SETTERS
    public getVariant(): EVariant {
        return this.variant;
    }
    public isDownloading(): boolean {
        return this.downloading;
    }
    public getNodes2Render(): ICollaboration[] {
        return this.renderNodeList;
    }

    public setVariant(variant: EVariant): void {
        this.variant = variant;
    }
    public startDownloading(): void {
        this.downloading = true;
    }
    public completeDowload(nodes: ICollaboration[]): void {
        this.dataModelRoot = nodes;
        this.notifyDataChanged();
        this.downloading = false;
    }
    // - I V I E W E R
    public enterSelected(node: ICollaboration): void {
        this.target = node;
    }
    /**
      Reconstructs the list of nodes to be rendered from the current DataRoot and their collaborations to the view.
      */
    public notifyDataChanged(): void {
        console.log(">[AppPanelComponent.notifyDataChanged]");
        let copyList = [];
        // Get the initial list by applying the policies defined at the page to the initial root node contents. Policies may be sorting or filtering actions.
        let initialList = this.applyPolicies(this.dataModelRoot);
        // Generate the contents by collaborating to the view all the nodes.
        for (let node of initialList) {
            let nodes = node.collaborate2View(null, this.variant);
            if (null != nodes) {
                console.log("-[AppPanelComponent.notifyDataChanged]> Collaborating " + nodes.length + " nodes.");
                // Add the collaborated nodes to the list of nodes to return.
                for (let childNode of nodes) {
                    copyList.push(childNode);
                }
            }
        }
        this.renderNodeList = copyList;
        console.log("<[AppPanelComponent.notifyDataChanged]");
    }
    public applyPolicies(entries: ICollaboration[]): ICollaboration[] {
        return entries;
    }
    public redirectPage(route: any): void { }
}
