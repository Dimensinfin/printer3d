// - SERVICES
import { AppStoreService } from '@app/services/app-store.service';
// - DOMAIN
import { EVariant } from '../EPack.enumerated';

export interface ICollaboration {
    collaborate2View(appModelStore?: AppStoreService, variant?: string | EVariant): ICollaboration[];
}
