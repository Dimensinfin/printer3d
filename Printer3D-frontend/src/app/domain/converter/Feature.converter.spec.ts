// - DOMAIN
import { FeatureConverter } from './converter/Feature.converter';
import { Feature } from './Feature.domain';
import { EInteraction } from './interfaces/EPack.enumerated';

describe('CLASS FeatureConverter [Module: DOMAIN]', () => {

    beforeEach(() => {
    });

    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('convertInstance.interaction: used to convert json fields before getting into a Feature.', () => {
            const featureFields = new FeatureConverter().convertInstance({interaction: 'DIALOG'});
            expect(featureFields).toBeDefined();
            expect(featureFields['interaction']).toBe(EInteraction.DIALOG);
        });
        it('convertInstance.<other>: used to convert json fields before getting into a Feature.', () => {
            const featureFields = new FeatureConverter().convertInstance({anydata: 'DIALOG'});
            expect(featureFields).toBeDefined();
            expect(featureFields['anydata']).toBe('DIALOG');
        });
    });
});
