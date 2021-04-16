// - DOMAIN
import { Node } from '../Node.domain'

export class MachineV2 extends Node {
    private id: string
    public label: string
    public model: string
    public characteristics: string
    public buildRecord: any

    constructor(values: Object = {}) {
        super()
        Object.assign(this, values)
    }

    public getId(): string {
        return this.id
    }
    public isRunning(): boolean {
        if (this.buildRecord)
            if (this.buildRecord.state === 'RUNNING') return true
        return false
    }
    public getRunTime(): number {
        if (this.isRunning()) {
            return this.buildRecord.remainingTime
        }
        return 0
    }
}
