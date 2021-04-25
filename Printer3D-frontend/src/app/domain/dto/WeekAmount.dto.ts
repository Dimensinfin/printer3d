export class WeekAmount {
    private year: number
    private week: number
    private amount: number

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

    // - G E T T E R S
    public getWeek(): number {
        return this.week
    }
    public getAmount(): number {
        return this.amount
    }
}
