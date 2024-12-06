export class Part{
  private name:string=''

  constructor(values: Object = {}) {
    // super();
    Object.assign(this, values);
    // this.jsonClass = 'Coil';
}

  public getName(): string{
    console.log('Getting the part name')
    return this.name
  }
}
