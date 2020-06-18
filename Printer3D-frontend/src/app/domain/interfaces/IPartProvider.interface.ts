// - DOMAIN
import { Part } from '@domain/Part.domain';

export interface IPartProvider {
    findById(id: string): Part;
}
