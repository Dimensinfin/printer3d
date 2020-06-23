export class JobRequest {
    private id: string
    private partId: string
    private partCount: number = 1

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.transformInput();
    }
    
    private transformInput(): void {
        this.partId = this['part'].id
    }
}
