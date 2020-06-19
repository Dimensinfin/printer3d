// - DOMAIN
import { PartToPartRecordConverter } from './PartToPartRecord.converter';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';
import { CoilToCoilRecordConverter } from './CoilToCoilRecord.converter';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';
import { RequestFormToRequestConverter } from './RequestFormToRequest.converter';
import { RequestForm } from '@domain/RequestForm.domain';
import { Request } from '@domain/Request.domain';
import { PartRequest } from '@domain/dto/PartRequest.dto';
import { RequestState } from '@domain/interfaces/EPack.enumerated';

describe('CLASS RequestFormToRequestConverter [Module: CONVERTER]', () => {
    const testPartList: Part[] = [
        new Part({
            "id": "a047ef17-fa0b-4df8-9f5e-c98de82dc4a2",
            "label": "Pieza de Plata",
            "description": "Pieza de Plata",
            "material": "PLA",
            "color": "PLATA",
            "buildTime": 15,
            "cost": 0.9,
            "price": 2.0,
            "stockLevel": 10,
            "stockAvailable": 8,
            "imagePath": null,
            "modelPath": null,
            "active": true
        }),
        new Part({
            "id": "5caaf805-f3dd-4dfe-9545-eaa3e6300da3",
            "label": "Boquilla Ganesha - Embocadura",
            "description": "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables.",
            "material": "TPU",
            "color": "BLANCO",
            "buildTime": 20,
            "cost": 0.45,
            "price": 1.0,
            "stockLevel": 15,
            "stockAvailable": 10,
            "imagePath": null,
            "modelPath": "",
            "active": true
        }),
        new Part({
            "id": "5caaf805-f3dd-4dfe-9545-eaa3e6300da3",
            "label": "Boquilla Ganesha - Embocadura",
            "description": "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables.",
            "material": "TPU",
            "color": "AZUL",
            "buildTime": 20,
            "cost": 0.45,
            "price": 1.0,
            "stockLevel": 15,
            "stockAvailable": 10,
            "imagePath": null,
            "modelPath": "",
            "active": false
        }),
        new Part({
            "id": "5caaf805-f3dd-4dfe-9545-eaa3e6300da3",
            "label": "Boquilla Ganesha - Embocadura",
            "description": "Boquilla para fomar en narguile. Compuesta de 3 piezas desmontables.",
            "material": "TPU",
            "color": "AZUL",
            "buildTime": 20,
            "cost": 0.45,
            "price": 1.0,
            "stockLevel": 15,
            "stockAvailable": 10,
            "imagePath": null,
            "modelPath": "",
            "active": false
        })
    ]
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('convert.simple: convert a RequestForm into a Request and packing the parts by identifier', () => {
            const converter = new RequestFormToRequestConverter();
            const obtained: Request = converter.convert(new RequestForm({
                id: "da0bbeaf-2711-4e6f-9134-c9e15919ca57",
                requestDate: new Date(),
                label: '-REQUEST-FORM-LABEL-',
                partList: testPartList
            }))
            const obtainedAsAny = obtained as any;
            console.log('>[]> Request: ' + JSON.stringify(obtained))
            console.log('>[]> Single PartRequest: ' + JSON.stringify(obtainedAsAny.partList[0]))
            expect(obtained).toBeDefined();
            expect(obtainedAsAny.id).toBe("da0bbeaf-2711-4e6f-9134-c9e15919ca57")
            expect(obtainedAsAny.partList).toBeDefined()
            expect(obtainedAsAny.partList.length).toBe(2)
            expect(obtainedAsAny.partList[0] instanceof PartRequest).toBeTrue()
            expect(obtainedAsAny.partList[1].quantity).toBe(3)
        });
    });
});
