export class JobRequest {
    private id: string
    private partId: string
    private copies: number = 1

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
