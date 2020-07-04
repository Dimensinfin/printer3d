// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';
import { PartContainer } from '@domain/PartContainer.domain';

export class PartContainerConstructor implements Constructor<PartContainer> {
    private container: PartContainer

    constructor(container: PartContainer) {
        this.container = container
    }

    public construct(input: any): PartContainer {
        return new PartContainer({
            description: input['description'],
            buildTime: input['buildTime'],
            weight: input['weight'],
            modelPath: input['modelPath'],
        })
    }
}
