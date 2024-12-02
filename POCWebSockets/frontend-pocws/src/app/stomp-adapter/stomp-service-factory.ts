import { StompConfig } from "./stomp-adapter.config";
import { StompMessagingService } from "./stomp-messaging-service.service";

export function StompServiceFactory() {
  const rxStomp = new StompMessagingService();
  rxStomp.configure(StompConfig);
  rxStomp.activate();
  return rxStomp;
}
