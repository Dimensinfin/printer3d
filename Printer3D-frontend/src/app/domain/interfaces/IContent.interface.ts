// - CORE
import { v4 as uuidv4 } from 'uuid';
// - DOMAIN
import { RequestContentType } from './EPack.enumerated';
import { ICollaboration } from './core/ICollaboration.interface';

/**
 * This interface defines the methos to make Parts and Models compatible at the level to be managed by the Requests.
 */
export interface IContent extends ICollaboration {
    getId(): string
    getLabel(): string
    getType(): RequestContentType
    getPrice(): number
    getStock(): number
}
