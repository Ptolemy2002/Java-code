package events.main;

import events.Event;

public class TestEvent implements Event {

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
		// TODO Auto-generated method stub
		
	}

}
