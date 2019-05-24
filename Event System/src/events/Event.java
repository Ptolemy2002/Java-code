package events;

public interface Event {
	/**Have this return true if you would like to force this event to happen in a new thread.**/
	public default boolean forceNewThread() {
		return false;
	}
	
	/**The event to call before this event is called. May be null.*/
	public default Event getBeforeEvent() {
		return null;
	};
	
	/**The event to call after this event is called. May be null.*/
	public default Event getAfterwardEvent() {
		return null;
	};
	
	/**The event to call after this event is unexpectantly cancelled. May be null.*/
	public default Event getCancelledEvent() {
		return null;
	};
	
	/**The method that will be called when this event is run*/
	public void call(Event e);
}
