// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
// - DOMAIN
import { EVariant } from '@app/domain/interfaces/EPack.enumerated';
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface';
import { IViewer } from '@domain/interfaces/core/IViewer.interface';
import { BackgroundEnabledComponent } from '../background-enabled/background-enabled.component';
import { SingleSelection } from '../../domain/SingleSelection.domain';
import { ISelectable } from '@domain/interfaces/core/ISelectable.interface';
import { Refreshable } from '@domain/interfaces/Refreshable.interface';
@Component({
    selector: 'app-panel',
    templateUrl: './app-panel.component.html',
    styleUrls: ['./app-panel.component.scss']
})
export class AppPanelComponent extends BackgroundEnabledComponent implements IViewer, Refreshable {
    @Input() self: IViewer;
    @Input() variant: EVariant = EVariant.DEFAULT;
    private downloading: boolean = true;
    private dataModelRoot: ICollaboration[] = [];
    protected renderNodeList: ICollaboration[] = [];
    protected selection: SingleSelection = new SingleSelection();

    constructor() {
        super()
        this.self = this;
    }

    public ngOnInit(): void {
        console.log(">[AppPanelComponent.ngOnInit]");
        this.startDownloading();
        this.refresh();
        console.log("<[AppPanelComponent.ngOnInit]");
    }

    // - I R E F R E S H A B L E
    public clean(): void {
    }
    public refresh(): void {
        this.clean()
    }

    // - G E T T E R S
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
        console.log('>[AppPanelComponent.completeDowload]')
        this.dataModelRoot = nodes;
        this.notifyDataChanged();
        this.downloading = false;
        console.log('<[AppPanelComponent.completeDowload]> Nodes processed: ' + this.dataModelRoot.length)
    }
    // - I V I E W E R
    public addSelection(node: ISelectable): void {
        this.selection.addSelection(node)
        this.fireSelectionChanged();
    }
    public subtractSelection(node: ISelectable): void {
        if (this.selection.subtractSelection(node))
            this.fireSelectionChanged()
    }
    /**
     * This is an abstract methods that should be implemented by panels that require to support selections.
     */
    public fireSelectionChanged(): void { }
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
            let nodes = node.collaborate2View(this.variant);
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
    public nodeCounter(): number {
        return this.renderNodeList.length
    }
}
