import { Node } from './Node.domain';
import { ICollaboration } from "./interfaces/core/ICollaboration.interface";
import { PatchNote } from "./PatchNote.domain";

export class PatchChange extends Node{
    public name: string
    public changes : string []=[]
 
    constructor(values: Object = {}) {
        super()
        Object.assign(this, values)
        this.jsonClass = 'PatchChange'
    }

    public collaborate2View(): ICollaboration[] {
        const notes : ICollaboration[] = [this]
        for (const note of this.changes) {
            notes.push( new PatchNote ({"note": note} ))
        }
        return notes
    }
    public getContent(): string{
        return this.name
    }
}
