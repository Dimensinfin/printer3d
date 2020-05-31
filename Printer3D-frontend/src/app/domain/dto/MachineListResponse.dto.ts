// - DOMAIN
import { Machine } from '@domain/Machine.domain';

export class MachineListResponse {
    public count: number = 0;
    public machines: Machine[] = [];

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }

    public getMachines(): any[] {
        return this.machines;
    }
    private transformInput(): void {
        if (null != this.machines) {
            const recordList: Machine[] = [];
            for (let entry of this.machines)
                recordList.push(new Machine(entry));
            this.machines = recordList;
        }
    }
}
