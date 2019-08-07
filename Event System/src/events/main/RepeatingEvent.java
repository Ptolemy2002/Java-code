package events.main;

import events.Event;

public class RepeatingEvent implements Event {

	@Override
	public Event getAfterwardEvent() {
		return null;
	}

	@Override
	public Event getCancelledEvent() {
		return null;
	}

	@Override
	public void call(Event e) {
	}

}
