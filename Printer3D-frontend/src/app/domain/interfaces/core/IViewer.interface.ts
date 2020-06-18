// - DOMAIN
import { ICollaboration } from './ICollaboration.interface';

export interface IViewer {
    enterSelected(node: ICollaboration): void;
    fireSelectionChanged(): void;
    notifyDataChanged(): void;
    redirectPage(route: any): void;
}
