// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Node } from '../Node.domain';
import { Part } from '@domain/Part.domain';
import { PartStack } from '@domain/PartStack.domain';

export class Model extends Node {
    private id: string
    private label: string
    private price: number
    private stockLevel: number
    private active: boolean
    private imagePath: string
    private partList: PartStack[] = []

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'Model';
    }

    public getId(): string {
        return this.id
    }
    public getLabel(): string {
        return this.label
    }
    public getPrice(): number {
        return this.price
    }
    public addPart(newPart: Part): void {
        for (let part of this.partList) {
            if (newPart.getId() == part.getId()) { // If the part is on the list increment the stack
                part.incrementStack()
                return
            }
        }
        this.partList.push(new PartStack(newPart)) // Add the new part to the list
    }
}
