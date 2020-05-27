// - DOMAIN
import { Node } from './Node';
import { EInteraction } from './interfaces/EPack.enumerated';
import { FeatureConverter } from './Feature.converter';

export class Feature extends Node {
    public label: string = '/';
    public active: boolean = false;
    public enabled: boolean = false;
    public route: string = '/';
    public interaction: EInteraction = EInteraction.PAGEROUTE;
    public dialog: string;

    constructor(values: Object = {}) {
        super();
        const convertedValues: object = new FeatureConverter().convertInstance(values);
        Object.assign(this, convertedValues);
        this.jsonClass = 'Feature';
    }
    public getRoute(): string {
        return this.route;
    }
    public equals(target: Feature): boolean {
        if (this.label != target.label) return false;
        if (this.active != target.active) return false;
        if (this.route != target.route) return false;
        return true;
    }
    public activate(): boolean {
        const result = this.active;
        this.active = true;
        return result;
    }
    public deactivate(): boolean {
        const result = this.active;
        this.active = false;
        return result;
    }
}
