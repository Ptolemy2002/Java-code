package events.main;

import events.Event;

public class StartUpEvent implements Event {
	@Override
	public Event getAfterwardEvent() {
		return new AfterStartupEvent();
	}

	@Override
	public Event getCancelledEvent() {
		return null;
	}

	@Override
	public void call(Event e) {
		// TODO Auto-generated method stub
		
	}
}
