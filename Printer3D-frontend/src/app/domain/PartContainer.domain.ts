// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { Node } from './Node.domain';
import { Part } from './Part.domain';

export class PartContainer extends Node {
    public id: string;
    public label: string;
    public description: string;
    public buildTime: number;
    public imagePath: string;
    public modelPath: string;
    public contents: Part[] = [];

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        this.jsonClass = 'PartContainer';
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
    // - I C O L L A B O R A T I O N
    public collaborate2View(): ICollaboration[] {
        let collaboration: ICollaboration[] = [];
        if (this.isExpanded()) {
            collaboration.push(this);
            this.contents.forEach(element => {
                collaboration.push(element);
            });
            return collaboration;
        }
        return [this];
    }
}
