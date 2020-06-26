// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { Converter } from '@domain/interfaces/Converter.interface';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { ModelForm } from '@domain/ModelForm.domain';
import { ModelRequest } from '@domain/dto/ModelRequest.dto';

export class ModelFormToModelRequestConverter implements Converter<ModelForm, ModelRequest>{
    private idPartList: string[] = []

    constructor(ids: string[]) {
        this.idPartList = ids
    }
    convert(input: ModelForm): ModelRequest {
        return new ModelRequest({
            id: uuidv4(),
            label: input.label,
            price: input.price,
            stockLevel: input.stockLevel,
            partIdList: this.idPartList
        })
    }
}
