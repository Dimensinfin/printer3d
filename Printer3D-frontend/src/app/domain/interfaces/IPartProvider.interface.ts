// - DOMAIN
import { Part } from '@domain/inventory/Part.domain';
import { RequestContentType } from './EPack.enumerated';
import { IContent } from './IContent.interface';

export interface IPartProvider {
    findById(id: string, type: RequestContentType): IContent;
}
