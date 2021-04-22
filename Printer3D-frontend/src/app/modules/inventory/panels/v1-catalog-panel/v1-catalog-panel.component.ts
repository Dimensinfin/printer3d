// - CORE
import { Component } from '@angular/core'
import { OnInit } from '@angular/core'
import { Input } from '@angular/core'
// - SERVICES
import { BackendService } from '@app/services/backend.service'
// - DOMAIN
import { ResponseTransformer } from '@app/services/support/ResponseTransformer'
import { Refreshable } from '@domain/interfaces/Refreshable.interface'
import { AppPanelComponent } from '@app/modules/shared/core/app-panel/app-panel.component'
import { PartListResponse } from '@domain/dto/PartListResponse.dto'
import { Part } from '@domain/inventory/Part.domain'
import { Model } from '@domain/inventory/Model.domain'
import { ICollaboration } from '@domain/interfaces/core/ICollaboration.interface'
import { PartContainer } from '@domain/inventory/PartContainer.domain'
import { V3InventoryPageComponent } from '../../pages/v3-inventory-page/v3-inventory-page.component'
import { IContentProvider } from '@domain/interfaces/IContentProvider.interface'
import { Project } from '@domain/inventory/Project.domain'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'

@Component({
    selector: 'v1-catalog-panel',
    templateUrl: './v1-catalog-panel.component.html',
    styleUrls: ['./v1-catalog-panel.component.scss']
})
export class V1CatalogPanelComponent extends AppPanelComponent implements OnInit, Refreshable, IContentProvider {
    @Input() page: V3InventoryPageComponent  // Pointer to the page that contains this panel. Used as a way to connect to siblings
    public filterInactive: boolean = true
    private parts: Part[] = []
    private models: Model[] = []
    private projects: Map<string, Project> = new Map<string, Project>()
    private partContainers: Map<string, PartContainer> = new Map<string, PartContainer>()
    private items: ICollaboration[] = []

    constructor(protected backendService: BackendService) {
        super()
    }

    /**
     * There are two flows when updating the render list.
     * A - Get the backend data always and remove any expand/collapse state.
     * B - Keep data and only update the render list.
     * 
     * Using the B requires that any sub component change be reflected on this main component to fire the recalculation of all the modified items. That is not being possible on this release so returning back to the A solution.
     */
    public ngOnInit(): void {
        console.log(">[V3InventoryPageComponent.ngOnInit]")
        // - A solution
        // this.clean()
        // this.downloadParts()
        // - A solution      
        super.ngOnInit()
        console.log("<[V3InventoryPageComponent.ngOnInit]")
    }
    // - I N T E R A C T I O N
    public toggleFilter(): void {
        console.log("-[V1CatalogPanelComponent.changeFilter]")
        this.refresh()
    }

    // - I C O N T E N T P R O V I D E R
    public findById(id: string, type: string): Part {
        for (let part of this.parts)
            if (part.getId() == id) return part
        return undefined
    }

    // - I V I E W E R
    /**
     * This method is called whenever the selection changes. Used to spread the event or perform other actions.
     */
    public fireSelectionChanged(): void {
        if (null != this.page)
            if (this.selection.getFirstSelected() instanceof Model)
                this.page.setSelected(this.selection.getFirstSelected() as Model)
            else this.page.closeEditor()
    }

    // - R E F R E S H A B L E
    public clean(): void {
        this.selection.clearSelection() // Clear the selection
        this.parts = []
        this.models = []
        this.partContainers = new Map<string, PartContainer>()
        this.items = []
    }
    public refresh(): void {
        // - A solution
        // this.startDownloading()
        // this.packProjects()
        this.clean()
        this.downloadParts()
        // this.completeDowload(this.items)
        // - A solution
    }

    // - B A C K E N D
    protected downloadParts(): void {
        // console.log("-[V1CatalogPanelComponent.downloadParts]>Filter: " + this.filterInactive)
        this.backendConnections.push(
            this.backendService.apiv2_InventoryGetParts()
                .subscribe((response: Part[]) => {
                    console.log('downloadParts.part count: ' + response.length)
                    this.parts = response
                    // Sort the Parts before storing them inside the containers.
                    const partList = this.sortPartsByActive(response)
                    this.downloadModels()
                })
        )
    }
    protected downloadModels(): void {
        this.backendConnections.push(
            this.backendService.apiInventoryGetModels_v1(this)
                .subscribe((response: Model[]) => {
                    this.models = response
                    this.packProjects()
                    // - A solution
                    this.completeDowload(this.items)
                    // - A solution
                })
        )
    }
    /**
     * Compose the list of projects to be rendered.
     * Projects contain Models and Parts.
     * There is an special project that contains the Parts and Models not associates to any other project.
     * Expanded items do not add to the list of rendered nodes but they render their contents themselves.
     * The <DEFAULT> project is not rendered as a container but their contents added to the list after the MOdels.
     */
    protected packProjects(): void {
        // Set up data structures
        this.buildPartContainers()
        this.items = []
        this.projects = new Map<string, Project>()
        // Add the Special <DEFAULT> Project for Parts with no project.
        const defaultProject = new Project({ name: Printer3DConstants.DEFAULT_PROJECT_NAME })
        this.projects.set(defaultProject.getName(), defaultProject)

        // Add the Models to the beginning of the Project. Do filtering on the process
        for (const model of this.sortModelsByLabel(this.models)) {
            if (this.filterInactive) { // Filter out inactive items
                if (model.isActive()) this.addItemToProject(model)
            } else this.addItemToProject(model)
        }

        // Classify parts into projects
        const containers = this.partContainers.values()
        const sortedContainers: PartContainer[] = []
        for (const container of containers)
            sortedContainers.push(container)
        for (const container of this.sortPartContainersByLabel(sortedContainers)) {
            this.addItemToProject(container)
        }

        // Add project to the list of contents
        for (const project of this.projects.values()) {
            if (project.getName() != Printer3DConstants.DEFAULT_PROJECT_NAME) this.items.push(project)
        }

        // Add models or parts not associated to any project
        for (let container of defaultProject.getContents())
            this.items.push(container)
        this.completeDowload(this.items)
    }
    private buildPartContainers(): void {
        // Classify Parts on part containers.
        this.parts.forEach(element => {
            let hit = this.partContainers.get(element.label)
            if (null == hit) {
                hit = new PartContainer(element)
                this.partContainers.set(element.label, hit)
            }
            if (this.filterInactive) {  // Filter out inactive items
                if (element.isActive()) hit.addPart(element)
            } else hit.addPart(element)
        })
        this.partContainers = this.removeEmptyContainers()
    }
    private addItemToProject(item: Model | PartContainer): void {
        const project = this.projects.get(item.getProject())
        if (project) project.addContainer(item)
        else {
            const project = new Project({ name: item.getProject() })
            this.projects.set(project.getName(), project)
            project.addContainer(item)
        }
    }
    private removeEmptyContainers(): Map<string, PartContainer> {
        const filteredContainers: Map<string, PartContainer> = new Map<string, PartContainer>()
        this.partContainers.forEach((value, key) => {
            if (!this.isEmpty(value.contents))
                filteredContainers.set(key, value)
        })
        return filteredContainers
    }
    private sortPartsByActive(parts: Part[]): Part[] {
        return parts.sort((part1, part2) => {
            if (part1.active && !part2.active) return -1
            if (!part1.active && part2.active) return 1
            return 0
        })
    }
    private sortPartContainersByLabel(containers: PartContainer[]): PartContainer[] {
        return containers.sort((container1, container2) =>
            0 - (container2.label > container1.label ? 1 : -1)
        )
    }
    private sortModelsByLabel(containers: Model[]): Model[] {
        return containers.sort((container1, container2) =>
            0 - (container2.getLabel() > container1.getLabel() ? 1 : -1)
        )
    }
    private sortProjectsByName(projects: Project[]): Project[] {
        return projects.sort((container1, container2) =>
            0 - (container2.getName() > container1.getName() ? 1 : -1)
        )
    }
}
