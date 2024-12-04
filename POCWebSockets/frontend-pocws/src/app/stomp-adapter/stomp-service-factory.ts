import { StompConfig } from "./stomp-adapter.config";
import { StompMessagingService } from "./stomp-messaging-service.service";

export function StompServiceFactory() {
  const rxStomp = new StompMessagingService();
  rxStomp.configure(StompConfig);
  console.log("Activating stomp service...")
  rxStomp.activate();
  return rxStomp;
}
