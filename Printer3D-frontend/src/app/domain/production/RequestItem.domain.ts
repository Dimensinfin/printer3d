// - DOMAIN
import { Node } from '../Node.domain';
import { RequestContentType } from '../interfaces/EPack.enumerated';
import { IContent } from '../interfaces/IContent.interface';

/**
 * Request items can be of different classes so we need a container that can export the common element interface and make manipulation of different items easy and feasible.
 * Iems can be created from requests stubs that come from the backend or from full fledged items on the loca services. This is something like Optionals that are evaluated and obtained at a later time by accessing the source.
 * @since 0.8.0
 */
export class RequestItem extends Node implements IContent {
    // STUB Fields
    private itemId: string
    private type: RequestContentType
    private quantity: number = 0
    private missing: number = 0
    // OPERATIVE Fields used inside the report
    private required: number = 0 // Number of required parts to detect missing parts on Open requests
    private content: IContent // Instance of the stub. can be empty until the Request content provider gets the instance

    constructor(values: Object = {}) {
        super()
        Object.assign(this, values);
        this.jsonClass = 'RequestItem'
    }

    public getQuantity(): number {
        return this.quantity
    }
    public getMissing(): number {
        return this.missing
    }
    public setMissing(missing: number): RequestItem {
        if (missing > 0) this.missing = missing
        return this
    }
    public addContent(content: IContent): RequestItem {
        this.content = content
        this.quantity++
        return this
    }
    public getContent(): IContent {
        return this.content
    }
    public setContent(content: IContent): RequestItem {
        this.content = content
        return this
    }
    public getRequired(): number {
        return this.required
    }
    public setRequired(required: number): RequestItem {
        this.required = required
        return this
    }
    public incrementCount(): number {
        this.quantity++
        return this.quantity
    }
    public decrementCount(): number {
        this.quantity--
        return this.quantity
    }

    // - I C O N T E N T
    public getId(): string {
        return this.itemId
    }
    public getLabel(): string {
        return this.content.getLabel()
    }
    public getType(): RequestContentType {
        return this.type
    }
    public getPrice(): number {
        return this.content.getPrice()
    }
    public getStock(): number {
        return this.content.getStock()
    }
}
