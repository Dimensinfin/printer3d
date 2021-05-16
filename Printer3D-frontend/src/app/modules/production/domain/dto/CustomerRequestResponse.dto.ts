// - DOMAIN
import { Node } from '../../../../domain/Node.domain'
import { RequestState } from '@domain/interfaces/EPack.enumerated'
import { RequestItem } from '@domain/production/RequestItem.domain'
import { Printer3DConstants } from '@app/platform/Printer3DConstants.platform'

export class CustomerRequestResponse extends Node {
    private id: string
    private label: string
    private requestDate: string
    private state: RequestState = RequestState.OPEN
    private paid: boolean = false
    protected contents: RequestItem[] = []

    constructor(values: Object = {}) {
        super()
        Object.assign(this, values)
        this.jsonClass = 'CustomerRequestResponse'
    }

    public getId(): string {
        return this.id
    }
    public getLabel(): string {
        return this.label
    }
    public getRequestDate(): Date {
        return new Date(this.requestDate)
    }
    public getContents(): RequestItem[] {
        return this.contents
    }
    public getContentCount(): number {
        let count: number = 0
        for (let content of this.contents)
            count += content.getQuantity()
        return count
    }
    public getAmount(): number {
        let amount: number = 0.0
        for (let content of this.contents)
            amount += content.getPrice() * content.getQuantity()
        return amount
    }
    public getTotal(): number {
        return this.getAmount() * (1 + Printer3DConstants.IVA_TAX)
    }
    public getState(): RequestState {
        return this.state
    }
    public isPaid(): boolean {
        return this.paid
    }
}
