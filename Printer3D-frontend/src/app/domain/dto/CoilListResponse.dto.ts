// - DOMAIN
import { Coil } from '@domain/inventory/Coil.domain';

export class CoilListResponse {
    public count: number = 0;
    public coils: Coil[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }

    public getCoils(): any[] {
        return this.coils;
    }

    private transformInput(): void {
        if (null != this.coils) {
            const recordList: Coil[] = [];
            for (let entry of this.coils)
                recordList.push(new Coil(entry));
            this.coils = recordList;
        }
    }
}
