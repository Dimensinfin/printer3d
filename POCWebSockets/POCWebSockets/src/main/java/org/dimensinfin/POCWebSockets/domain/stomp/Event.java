package org.dimensinfin.POCWebSockets.domain.stomp;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
public class Event {
	private String name;
}
