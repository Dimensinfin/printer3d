import { INode } from './interfaces/INode.interface';

export class PartRecord implements INode{
    public label:string;
    public description:string;
    
    constructor(values: Object = {}) {
        // super();
        Object.assign(this, values);
        // this.jsonClass = "Feature";
    }
   getJsonClass(): string {
        throw new Error("Method not implemented.");
    }
}
