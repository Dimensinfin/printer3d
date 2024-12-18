// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { ModelForm } from '@domain/inventory/ModelForm.domain';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';

export class ModelFormToModelRequestConverter implements Converter<ModelForm, ModelRequest>{
    private idPartList: string[] = []

    constructor(ids: string[]) {
        this.idPartList = ids
    }
    convert(input: ModelForm): ModelRequest {
        return new ModelRequest({
            id: input.getId(),
            label: input.label,
            price: input.price,
            stockLevel: input.stockLevel,
            partIdList: this.idPartList,
            active: input.active
        })
    }
}
