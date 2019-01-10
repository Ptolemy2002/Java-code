package events.main;

import events.Event;

public class CancelEvent implements Event {

	@Override
	public Event getAfterwardEvent() {
		return null;
	}

	@Override
	public Event getCancelledEvent() {
		return null;
	}

}
