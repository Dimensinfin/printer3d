// - CORE
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform';
// - DOMAIN
import { Node } from '../Node.domain';
import { Part } from './Part.domain';

export class PartContainer extends Node {
    public id: string
    public label: string
    private project: string = Printer3DConstants.DEFAULT_PROJECT_NAME
    public description: string
    public buildTime: number
    public weight: number
    public imagePath: string
    public modelPath: string
    public contents: Part[] = []

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        this.jsonClass = 'PartContainer';
    }

    public getId(): string {
        return this.id;
    }
    public isExpandable(): boolean {
        return true;
    }
    public isActive(): boolean {
        return true;
    }
    public addPart(newPart: Part): void {
        if (null != newPart) this.contents.push(newPart);
    }
    public getProject(): string {
        return this.project
    }
}
