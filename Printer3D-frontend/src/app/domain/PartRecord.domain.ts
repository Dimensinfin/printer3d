export class PartRecord {
    public etiqueta: string;
    public descripcion: string;
    public material: string;
    public color: string;
    public coste: string;
    public precio: string;
    public tiempo:number;
    public stockRequerido: number;
    public stockDisponible: number
    public active: string = 'ACTIVA';

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
