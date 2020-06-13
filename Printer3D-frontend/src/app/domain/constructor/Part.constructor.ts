// - DOMAIN
import { PartRecord } from '@domain/PartRecord.domain';
import { Constructor } from '@domain/interfaces/Constructor.interface';
import { Part } from '@domain/Part.domain';

export class PartConstructor implements Constructor<Part> {
    public construct(input: any): Part {
        return new Part({
            id: input['id'],
            label: input['label'],
            description: input['description'],
            material: input['material'],
            colorCode: input['colorCode'],
            cost: input['cost'],
            price: input['price'],
            buildTime: input['buildTime'],
            stockLevel: input['stockLevel'],
            stockAvailable: input['stockAvailable'],
            imagePath: input['imagePath'],
            modelPath: input['modelPath'],
            active: input['active']
        })
    }
}
