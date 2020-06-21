// - DOMAIN
import { Node } from './Node.domain';
import { RequestState } from '@domain/interfaces/EPack.enumerated';
import { PartRequest } from './dto/PartRequest.dto';
import { IPartProvider } from './interfaces/IPartProvider.interface';
import { Part } from './Part.domain';
import { Part4Request } from './Part4Request.domain';

export class Request extends Node {
    private id: string;
    private label: string;
    private requestDate: string;
    private state: RequestState = RequestState.OPEN;
    private partList: PartRequest[] = [];

    private partProvider: IPartProvider

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.transformInput();
        this.jsonClass = 'Request'
    }

    public getId(): string {
        return this.id;
    }
    public getLabel(): string {
        return this.label;
    }
    public getPartList(): PartRequest[] {
        return this.partList;
    }
    public getRequestDate(): Date {
        return new Date(this.requestDate);
    }
    public getPartCount(): number {
        let count: number = 0
        for (let part of this.partList)
            count += part.getQuantity()
        return count
    }
    public getAmount(): string {
        let amount: number = 0.0
        for (let partContainer of this.partList) {
            if (null != this.partProvider) {
                const part = this.partProvider.findById(partContainer.getPartId())
                if (null != part)
                    amount += part.getPrice() * partContainer.getQuantity();
            }
        }
        return amount + ' €';
    }
    public getState(): RequestState {
        return this.state;
    }
    public setPartProvider(newPartProvider: IPartProvider): Request {
        this.partProvider = newPartProvider;
        return this;
    }
    public getParts(): Part4Request[] {
        const parts: Part4Request[] = []
        for (let partPointer of this.partList) {
            const part = this.partProvider.findById(partPointer.getPartId())
            const part4r = new Part4Request(part)
            part4r.setRequired(partPointer.getQuantity())
            parts.push(part4r)
        }
        return parts;
    }
    private transformInput(): void {
        if (null != this.partList) {
            const newPartList: PartRequest[] = [];
            for (let entry of this.partList)
                newPartList.push(new PartRequest(entry))
            this.partList = newPartList;
        }
    }
}
