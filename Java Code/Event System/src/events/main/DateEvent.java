package events.main;

import events.Event;

public class DateEvent implements Event {
	
	public static class onCancelled implements Event {

		@Override
		public Event getAfterwardEvent() {
			return null;
		}

		@Override
		public Event getCancelledEvent() {
			return null;
		}
		
	}
	
	@Override
	public Event getAfterwardEvent() {
		return null;
	}

	@Override
	public Event getCancelledEvent() {
		return new onCancelled();
	}
}