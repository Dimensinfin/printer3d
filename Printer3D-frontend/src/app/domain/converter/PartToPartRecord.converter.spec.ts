// - DOMAIN
import { PartToPartRecordConverter } from './PartToPartRecord.converter';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';

describe('CLASS PartToPartRecordConverter [Module: DOMAIN]', () => {
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('convert: convert a Part into a RecordPart transforming fields if required', () => {
            const part = new Part({
                "id": "64c26e80-6b5f-4ce5-a77b-6a0c58f853ae",
                "label": "Covid-19 Key",
                "description": "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                "material": "PLA",
                "colorCode": "NARANJA-T",
                "buildTime": 30,
                "cost": 0.85,
                "price": 3.0,
                "stockLevel": 5,
                "stockAvailable": 0,
                "imagePath": "https://ibb.co/3dGbsRh",
                "modelPath": "pieza3.sft",
                "active": true
            });
            const converter = new PartToPartRecordConverter();
            expect(converter).toBeDefined();
            const obtained: PartRecord = converter.convert(part);
            const expected: PartRecord = new PartRecord({
                etiqueta: "Covid-19 Key",
                descripcion: "This is a key to be used to isolate contact with surfaces and buttons. Use it to open doors and push buttons.",
                material: "PLA",
                color: "NARANJA-T",
                coste: "0.85 €",
                precio: "3 €",
                stockRequerido: 5,
                stockDisponible: 0,
                active: "ACTIVA"
            });
            expect(obtained).toEqual(expected);
        });
    });
});
