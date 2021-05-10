// - DOMAIN
import { Node } from "@domain/Node.domain"
import { EInteraction } from "./EInteraction.domain"

export class Feature extends Node {
    public label: string
    public active: boolean
    public enabled: boolean
    private hasMenu: boolean
    public route: string = '/'
    public interaction: EInteraction = EInteraction.PAGEROUTE
    public modifier: string
    public dialog: string
    private features: Feature[]

    constructor(values: Object = {}) {
        super(values)
        // Object.assign(this, values)
        this.jsonClass = 'Feature'
    }
    public decode(): void {
        this.interaction = EInteraction[this.interaction as keyof typeof EInteraction]
        if (this.hasMenu) { // If the feature has hierachy then transform the feature data
            console.log('-[V3FeatureRenderComponent.hasMenu]>has menu: ' + this.hasMenu)
            const childFeatures: Feature[] = []
            for (let entry of this.features)
                childFeatures.push(new Feature(entry))
            this.features = childFeatures
        }
    }

    // - G E T T E R S
    public getLabel(): string {
        if (this.label) return this.label
        else return '/'
    }
    public isActive(): boolean {
        if (this.active) return this.active
        else {
            this.active = false
            return this.active
        }
    }
    public isEnabled(): boolean {
        if (this.enabled) return this.enabled
        else {
            this.enabled = false
            return this.enabled
        }
    }
    public getRoute(): string {
        return this.route
    }
    public equals(target: Feature): boolean {
        if (this.label != target.label) return false
        if (this.active != target.active) return false
        if (this.route != target.route) return false
        return true
    }
    public activate(): boolean {
        const result = this.active
        this.active = true
        return result
    }
    public deactivate(): boolean {
        const result = this.active
        this.active = false
        return result
    }
    public ifHasMenu(): boolean {
        if (this.hasMenu) return this.hasMenu
        else return false
    }
    public getChildFeatures(): Feature[] {
        if (this.features) return this.features
        else return []
    }
}
