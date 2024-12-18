// - DOMAIN
import { Node } from '../Node.domain';
import { Part } from '@domain/inventory/Part.domain';
import { PartStack } from '@domain/PartStack.domain';
import { IContent } from '@domain/interfaces/IContent.interface';
import { RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform';

export class Model extends Node implements IContent {
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

    public getParts(): PartStack[] {
        return this.partList
    }
    public isActive(): boolean {
        return this.active
    }
    public getProject(): string {
        if (this.isEmpty(this.partList)) return Printer3DConstants.DEFAULT_PROJECT_NAME
        else return this.partList[0].getProject()
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

    // - I C O N T E N T
    public getId(): string {
        return this.id
    }
    public getLabel(): string {
        return this.label
    }
    public getType(): RequestContentType {
        return RequestContentType.MODEL
    }
    public getPrice(): number {
        return this.price
    }
    public getStock(): number {
        return this.stockLevel
    }
}
