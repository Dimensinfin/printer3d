export class PartRecordConverter {
    private convert(fieldName: string, data: any): any {
        // console.log('>[PartRecordConverter.convert]> FieldName: ' + fieldName);
        switch (fieldName) {
            case 'cost':
                // console.log('>[PartRecordConverter.convert]> Value: ' + data + '€');
                return data + '€';
            default:
                return data;
        }
    }
    public convertInstance(values: any): object {
        const result: object = {};
        for (const key in values) {
            if (values.hasOwnProperty(key)) {
                const element = this.convert(key, values[key]);
                result[key] = element;
            }
        }
        console.log('>[PartRecordConverter.convertInstance]> Result: ' + JSON.stringify(result));
        return result;
    }
}
