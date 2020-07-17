export class WeekData{
    public name: string
    public value: number

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
