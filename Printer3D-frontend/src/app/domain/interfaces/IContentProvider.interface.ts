// - DOMAIN
import { IContent } from './IContent.interface';

export interface IContentProvider {
    findById(id: string, type:string): IContent;
}
