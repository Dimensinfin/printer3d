// - DOMAIN
import { ISelectable } from './ISelectable.interface';

export interface IViewer {
    addSelection(node: ISelectable): void;
    subtractSelection(node: ISelectable): void;
    fireSelectionChanged(): void;
    notifyDataChanged(): void;
    redirectPage(route: any): void;
}
