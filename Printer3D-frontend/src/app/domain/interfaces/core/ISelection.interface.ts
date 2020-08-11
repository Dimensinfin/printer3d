// - DOMAIN
import { ICollaboration } from './ICollaboration.interface';
import { ISelectable } from './ISelectable.interface';

export interface ISelection {
    getFirstSelected(): ISelectable
    getSelection(): ISelectable[]
    clearSelection(): void
    addSelection(node: ISelectable): void
    subtractSelection(node: ISelectable): boolean
}
