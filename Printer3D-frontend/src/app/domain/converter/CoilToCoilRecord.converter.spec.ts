// - DOMAIN
import { PartToPartRecordConverter } from './PartToPartRecord.converter';
import { Part } from '@domain/Part.domain';
import { PartRecord } from '@domain/PartRecord.domain';
import { CoilToCoilRecordConverter } from './CoilToCoilRecord.converter';
import { Coil } from '@domain/Coil.domain';
import { CoilRecord } from '@domain/CoilRecord.domain';

describe('CLASS CoilToCoilRecordConverter [Module: DOMAIN]', () => {
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('convert: convert a Part into a RecordPart transforming fields if required', () => {
            const coil = new Coil({
                "id": "2365aee7-9eba-4ad8-9aa0-b3091147158f",
                "material": "TPU",
                "color": "BLANCO",
                "weight": 250
            });
            const converter = new CoilToCoilRecordConverter();
            expect(converter).toBeDefined();
            const obtained: CoilRecord = converter.convert(coil);
            const expected: CoilRecord = new CoilRecord({
                material: "TPU",
                color: "BLANCO",
                peso: "250 gr"
            });
            expect(obtained).toEqual(expected);
        });
    });
});
