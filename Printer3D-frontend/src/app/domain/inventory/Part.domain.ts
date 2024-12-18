// - CORE
import { v4 as uuidv4 } from 'uuid'
// - DOMAIN
import { Node } from '../Node.domain'
import { IContent } from '@domain/interfaces/IContent.interface'
import { RequestContentType } from '@domain/interfaces/EPack.enumerated'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'

export class Part extends Node implements IContent {
    public id: string
    public label: string
    public project: string = Printer3DConstants.DEFAULT_PROJECT_NAME
    public description: string
    public material: string = 'PLA'
    public color: string
    public weight: number = 1
    public cost: number
    public price: number
    public buildTime: number
    public stockLevel: number = 1
    public stockAvailable: number = 0
    public imagePath: string
    public modelPath: string
    public active: boolean = true
    public unavailable: boolean = false

    constructor(values: Object = {}) {
        super(values)
        Object.assign(this, values)
        this.jsonClass = 'Part'
    }

    // - G E T T E R S   &   S E T T E R S
    public createNewId(): string {
        this.id = uuidv4()
        return this.id
    }
    public getMaterial(): string {
        return this.material
    }
    public getColor(): string {
        return this.color
    }
    public getAvailable(): number {
        return this.stockAvailable
    }
    public composePartIdentifier(): string {
        return this.label + ':' + this.color
    }
    public isExpandable(): boolean {
        return false
    }
    public isActive(): boolean {
        return this.active
    }
    public getProject(): string {
        return this.project
    }

    // - I C O N T E N T
    public getId(): string {
        return this.id
    }
    public getLabel(): string {
        return this.label
    }
    public getType(): RequestContentType {
        return RequestContentType.PART
    }
    public getPrice(): number {
        return this.price
    }
    /**
     * Return the number of instances that were found on the stock record.
     */
    public getStock(): number {
        return this.stockAvailable
    }
}
