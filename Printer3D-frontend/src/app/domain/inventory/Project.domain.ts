// - DOMAIN
import { Node } from '../Node.domain';
import { Model } from './Model.domain';
import { PartContainer } from './PartContainer.domain';

export class Project extends Node {
    private name: string
    private contents: any[] = []

    constructor(values: Object = {}) {
        super(values)
        this.jsonClass = 'Project'
    }
    
    public isExpandable(): boolean {
        return true;
    }
    public addContainer(newPart: Model | PartContainer): void {
        if (null != newPart) this.contents.push(newPart);
    }
    public getName(): string {
        return this.name
    }
    public getContents(): any[] {
        return this.contents
    }
}
