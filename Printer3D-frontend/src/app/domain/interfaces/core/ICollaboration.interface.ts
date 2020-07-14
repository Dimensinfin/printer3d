// - DOMAIN
import { EVariant } from '../EPack.enumerated';

export interface ICollaboration {
    collaborate2View(variant?: string | EVariant): ICollaboration[];
}
