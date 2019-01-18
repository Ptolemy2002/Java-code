package events;

public interface Event {
	/**The event to call after this event is called. May be null.*/
	public Event getAfterwardEvent();
	
	/**The event to call after this event is unexpectantly cancelled. May be null.*/
	public Event getCancelledEvent();
}
