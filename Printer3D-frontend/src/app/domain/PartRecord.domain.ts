export class PartRecord {
    public etiqueta: string;
    public descripcion: string;
    public material: string;
    public color: string;
    // public cost: number;
    // public price: number;
    // public buildTime: number;
    // public stockLevel: number = 1;
    // public stockAvailable: number = 0;
    // public imagePath: string;
    // public modelPath: string;
    // public active: boolean = true;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
