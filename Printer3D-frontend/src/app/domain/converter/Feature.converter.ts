import { EInteraction } from '../interfaces/EPack.enumerated';

export class FeatureConverter {
    public convertInstance(values: any): object {
        const result: object = {};
        for (const key in values) {
            if (values.hasOwnProperty(key)) {
                const element = this.convert(key, values[key]);
                result[key] = element;
            }
        }
        console.log('>[FeatureConverter.convertInstance]> Result: ' + JSON.stringify(result));
        return result;
    }
    private convert(fieldName: string, data: any): any {
        switch (fieldName) {
            case 'interaction':
                const typedInteractionString = data as keyof typeof EInteraction;
                return EInteraction[typedInteractionString];
            default:
                return data;
        }
    }
}
