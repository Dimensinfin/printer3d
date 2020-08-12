// - DOMAIN
import { RequestForm } from './RequestForm.domain';
import { Part } from '@domain/inventory/Part.domain';
import { RequestItem } from '@domain/production/RequestItem.domain';

describe('CLASS RequestForm [Module: DTO]', () => {
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('constructor.empty: validate initial state without constructor', () => {
            const instance = new RequestForm()
            expect(instance).toBeDefined();
            expect(instance.id).toBeDefined();
            expect(instance.label).toBeUndefined();
            expect(instance.requestDate).toBeDefined();
            expect(instance.contents).toBeDefined();
            expect(instance.contents.length).toBe(0);
        });
        it('constructor.object: validate initial state with object data', () => {
            const instance = new RequestForm({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Pedido de 100 Covid-19 Key",
                "requestDate": "Sun Jun 14 2020 17:00:03 GMT+0200 (Central European Summer Time)",
                "contents": [new RequestItem({ itemId: "ef3babed-36bf-4acb-a1f9-1b4da6085cf8" })]
            });
            expect(instance).toBeDefined();
            expect(instance.id).toBe("4e7001ee-6bf5-40b4-9c15-61802e4c59ea");
            expect(instance.label).toBe("Pedido de 100 Covid-19 Key");
            expect(instance.requestDate).toBeDefined();
            expect(instance.contents).toBeDefined();
            expect(instance.contents.length).toBe(1);
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Methods]', () => {
        it('addContent: add a part to the Request', () => {
            const instance = new RequestForm({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Pedido de 100 Covid-19 Key",
                "requestDate": "Sun Jun 14 2020 17:00:03 GMT+0200 (Central European Summer Time)",
                "contents": [new RequestItem({ itemId: "ef3babed-36bf-4acb-a1f9-1b4da6085cf8" })]
            });
            expect(instance.contents).toBeDefined();
            expect(instance.contents.length).toBe(1);
            instance.addContent(new Part({ id: "3d96b02d-641c-449c-8945-b487ae2a6c22" }));
            expect(instance.contents.length).toBe(2);
        });
        it('addContent: add an existing part to the Request', () => {
            const instance = new RequestForm({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Pedido de 100 Covid-19 Key",
                "requestDate": "Sun Jun 14 2020 17:00:03 GMT+0200 (Central European Summer Time)",
                "contents": [new RequestItem({ itemId: "ef3babed-36bf-4acb-a1f9-1b4da6085cf8" })]
            });
            expect(instance.contents).toBeDefined();
            expect(instance.contents.length).toBe(1);
            instance.addContent(new Part({ id: "3d96b02d-641c-449c-8945-b487ae2a6c22" }));
            expect(instance.contents.length).toBe(2);
            instance.addContent(new Part({ id: "3d96b02d-641c-449c-8945-b487ae2a6c22" }));
            expect(instance.contents.length).toBe(2);
        });
        it('getRequestContents: get current Request contents', async () => {
            const instance = new RequestForm({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Pedido de 100 Covid-19 Key",
                "requestDate": "Sun Jun 14 2020 17:00:03 GMT+0200 (Central European Summer Time)",
                "contents": [new RequestItem({ itemId: "ef3babed-36bf-4acb-a1f9-1b4da6085cf8" })]
            });
            expect(instance.contents).toBeDefined();
            expect(instance.contents.length).toBe(1, 'Number of Parts at creation');
            expect(instance.getRequestContents().length).toBe(1);
        });
        it('removeContent: remove a Request content', async () => {
            const instance = new RequestForm({
                "id": "4e7001ee-6bf5-40b4-9c15-61802e4c59ea",
                "label": "Pedido de 100 Covid-19 Key",
                "requestDate": "Sun Jun 14 2020 17:00:03 GMT+0200 (Central European Summer Time)",
                "contents": [new RequestItem({ itemId: "ef3babed-36bf-4acb-a1f9-1b4da6085cf8" })]
            });
            expect(instance.contents).toBeDefined();
            expect(instance.contents.length).toBe(1, 'Number of Parts at creation');
            const part1: Part = new Part({ id: "3d96b02d-641c-449c-8945-b487ae2a6c22", label: 'Part1' })
            const part2: Part = new Part({ id: "6801b340-a572-4043-85d4-a9e10634e916", label: 'Part2' })
            instance.addContent(part1);
            instance.addContent(part1);
            instance.addContent(part2);
            expect(instance.getRequestContents().length).toBe(3, 'Number of parts after aditions');
            instance.removeContent(new RequestItem({ itemId: "3d96b02d-641c-449c-8945-b487ae2a6c22", content: part1 }))
            expect(instance.getRequestContents().length).toBe(3, 'Number of parts after removal');
            instance.removeContent(new RequestItem({ itemId: "6801b340-a572-4043-85d4-a9e10634e916", content: part2 }))
            expect(instance.getRequestContents().length).toBe(2, 'Number of parts after removal');
        });
    });
});
