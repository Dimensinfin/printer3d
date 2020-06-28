// - DOMAIN
import { IContent } from './IContent.interface';
import { RequestItem } from '@domain/RequestItem.domain';

export interface IContentProvider {
    findById(id: string, type:string): IContent;
}
