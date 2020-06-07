// - CORE
import { v4 as uuidv4 } from 'uuid';
import { ICollaboration } from './interfaces/core/ICollaboration.interface';
import { AppStoreService } from '@app/services/app-store.service';
import { NeoCom } from './NeoCom.domain';
import { PartDetail } from './PartDetail.domain';
// - DOMAIN

export class Part extends NeoCom {
    public id: string;
    public label: string;
    public description: string;
    public material: string = 'PLA'
    public colorCode: string;
    public cost: number;
    public price: number;
    public buildTime: number;
    public stockLevel: number = 1;
    public stockAvailable: number = 0;
    public imagePath: string;
    public modelPath: string;
    public active: boolean = true;

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        this.jsonClass = 'Part';
    }
    public createNewId(): string {
        this.id = uuidv4();
        return this.id;
    }
    public composePartIdentifier(): string {
        if (null != this.colorCode)
            return this.label + ':' + this.colorCode;
        else
            return this.label + ':' + 'INDEFINIDO';
    }
    public isExpandable(): boolean {
        return true;
    }
    public isActive(): boolean {
        return this.active;
    }
    public collaborate2View(): ICollaboration[] {
        if (this.isExpanded()) {
            const detail = new PartDetail(this) as any;
            detail.jsonClass = 'PartDetail';
            const collaboration = [];
            collaboration.push(this);
            collaboration.push(detail)
            collaboration.push(detail)
            collaboration.push(detail)
            return collaboration;
        }
        return [this];
    }

}
