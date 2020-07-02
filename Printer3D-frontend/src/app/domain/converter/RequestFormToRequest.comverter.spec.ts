// - DOMAIN
import { RequestFormToRequestConverter } from './RequestFormToRequest.converter';
import { RequestForm } from '@domain/RequestForm.domain';
import { RequestState, RequestContentType } from '@domain/interfaces/EPack.enumerated';
import { RequestRequest } from '@domain/dto/RequestRequest.dto';
import { RequestItem } from '@domain/RequestItem.domain';

describe('CLASS RequestFormToRequestConverter [Module: CONVERTER]', () => {
    // - C O V E R A G E   P H A S E
    describe('Coverage Phase [Methods]', () => {
        it('convert.: convert a RequestForm into a RequestRequest for background connection', () => {
            // Given
            const contents : RequestItem[]=[]
            contents.push(new RequestItem({
                itemId: "fed05e3a-fc43-41a2-95b3-2002e15a98ba",
                type: RequestContentType.PART,
                quantity: 5,
                missing: 0
            }))
            contents.push(new RequestItem({
                itemId: "66fda6e3-cfad-45b0-95de-12c6d70149b9",
                type: RequestContentType.MODEL,
                quantity: 10,
                missing: 0
            }))
            const form : RequestForm = new RequestForm({
                id: "f37a66b0-0b80-47b6-a0e8-42ca5a68eb91",
                label: "-TEST-REQUEST-FORM-LABEL-",
                requestDate : new Date(),
                contents: contents
            })
            const converter = new RequestFormToRequestConverter();
            const obtained: RequestRequest = converter.convert(form)
            const obtainedAsAny = obtained as any;
            console.log('>[]> RequestRequest: ' + JSON.stringify(obtained))
            expect(obtained).toBeDefined();
            expect(obtainedAsAny.id).toBe("f37a66b0-0b80-47b6-a0e8-42ca5a68eb91")
            expect(obtainedAsAny.label).toBe("-TEST-REQUEST-FORM-LABEL-")
            expect(obtainedAsAny.requestDate).toBeDefined()
            expect(obtainedAsAny.state).toBe('OPEN')
            expect(obtainedAsAny.contents).toBeDefined()
            expect(obtainedAsAny.contents.length).toBe(2)
            expect(obtainedAsAny.contents[0].itemId).toBe("fed05e3a-fc43-41a2-95b3-2002e15a98ba")
            expect(obtainedAsAny.contents[0].type).toBe(RequestContentType.PART)
            expect(obtainedAsAny.contents[0].quantity).toBe(5)
            expect(obtainedAsAny.contents[1].itemId).toBe("66fda6e3-cfad-45b0-95de-12c6d70149b9")
            expect(obtainedAsAny.contents[1].type).toBe(RequestContentType.MODEL)
            expect(obtainedAsAny.contents[1].quantity).toBe(10)
        });
    });
});
