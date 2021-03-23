// - DOMAIN
export class UpdateCoilRequest {
    public id: string
    public weight: number
    public color:string
    public active : boolean

    constructor(values: Object = {}) {
        Object.assign(this, values)
    }
}
