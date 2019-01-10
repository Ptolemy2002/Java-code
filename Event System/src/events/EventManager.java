package events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EventManager {
	/** Used To schedule events */
	private static Timer eventTimer = new Timer();

	/** Wraps a TimerTask and an event in one object */
	public static class ScheduledEvent {
		private TimerTask task;
		private Event event;
		private boolean cancellable;
		private Calendar date;

		public ScheduledEvent(TimerTask task, Event event, boolean cancellable, Calendar date) {
			this.task = task;
			this.event = event;
			this.cancellable = cancellable;
			this.date = date;
		}

		public TimerTask getTask() {
			return this.task;
		}

		public Calendar getDate() {
			return this.date;
		}

		public Event getEvent() {
			return this.event;
		}

		public boolean isCancellable() {
			return this.cancellable;
		}

		@Override
		public String toString() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm:ss a");

			return "scheduled at " + dateFormat.format(this.date.getTime());
		}
	}

	/** A list of events that have been scheduled to run at a specific time */
	private static List<ScheduledEvent> scheduledEvents = new ArrayList<ScheduledEvent>();

	/** The list of registered event listeners */
	private static List<Object> registeredListeners = new ArrayList<Object>();

	/**
	 * Get whether or not the event listener is in the list of event listeners.
	 * 
	 * @param listener the event listener to check
	 * @return whether or not the event listener is in the list of event listeners
	 */
	public static boolean isRegistered(Object listener) {
		return registeredListeners.contains(listener);
	}

	/**
	 * Get whether or not the scheduled event will run later on.
	 * 
	 * @param event the event to check
	 * @return whether or not the event is scheduled to run later on
	 */
	public static boolean isScheduled(ScheduledEvent event) {
		return scheduledEvents.contains(event);
	}

	/**
	 * Get whether or not the event is scheduled to run later on.
	 * 
	 * @param event the event to check
	 * @return whether or not the event is scheduled to run later on
	 */
	public static boolean isScheduled(Event event) {
		for (ScheduledEvent i : scheduledEvents) {
			if (i.getEvent() == event) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get whether or not an event is scheduled to run on the specified date.
	 * 
	 * @param date the date to check. Time will be ignored so all scheduled events
	 *             on the entire day will be cancelled.
	 * @return whether or not the timer task is scheduled to run later on
	 */
	public static boolean isScheduled(Calendar date) {
		for (ScheduledEvent i : scheduledEvents) {
			if (i.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)
					&& i.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& i.getDate().get(Calendar.DATE) == date.get(Calendar.DATE)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get whether or not the timer task is scheduled to run later on.
	 * 
	 * @param event the timer task to check
	 * @return whether or not the timer task is scheduled to run later on
	 */
	public static boolean isScheduled(TimerTask event) {
		for (ScheduledEvent i : scheduledEvents) {
			if (i.getTask() == event) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get all events scheduled to run later on
	 * 
	 * @return the list of events scheduled to run later on
	 */
	public static List<ScheduledEvent> getSceduledEvents() {
		return scheduledEvents;
	}

	/**
	 * forceefully unschedule the scheduled event that will run the specified
	 * TimerTask. Only EventManager can do this.
	 * 
	 * @param event the TimerTask that you want to remove all events corresponding
	 *              to.
	 */
	private static void forceUnscheduleEvent(TimerTask event) {
		List<ScheduledEvent> toRemove = new ArrayList<ScheduledEvent>();

		for (ScheduledEvent i : scheduledEvents) {
			if (i.getTask() == event) {
				toRemove.add(i);
			}
		}

		for (ScheduledEvent i : toRemove) {
			scheduledEvents.remove(i);
			i.getTask().cancel();
			// If this is not called, cancelled tasks will not be eligible for garbage
			// collection.
			eventTimer.purge();

			if (i.getEvent().getCancelledEvent() != null) {
				callEvent(i.getEvent().getCancelledEvent(), false);
			}
		}
	}

	/**
	 * unschedule the scheduled event that will run the specified TimerTask. If
	 * cancellable is false on the scheduled event, it will not be cancelled.
	 * 
	 * @param event the TimerTask that you want to remove all events corresponding
	 *              to.
	 */
	public static void unscheduleEvent(TimerTask event) {
		List<ScheduledEvent> toRemove = new ArrayList<ScheduledEvent>();

		for (ScheduledEvent i : scheduledEvents) {
			if (i.getTask() == event) {
				if (i.isCancellable()) {
					toRemove.add(i);
				}
			}
		}

		for (ScheduledEvent i : toRemove) {
			unscheduleEvent(i);
		}
	}

	/**
	 * unschedule the specific scheduled event you provide. If cancellable is false
	 * on the scheduled event, it will not be cancelled.
	 * 
	 * @param event the event to unschedule
	 */
	public static void unscheduleEvent(ScheduledEvent event) {
		if (event.isCancellable()) {
			scheduledEvents.remove(event);
			event.getTask().cancel();
			// If this is not called, cancelled tasks will not be eligible for garbage
			// collection.
			eventTimer.purge();

			if (event.getEvent().getCancelledEvent() != null) {
				callEvent(event.getEvent().getCancelledEvent(), false);
			}
		}
	}

	/**
	 * unschedule all scheduled events programmed to run the specified event. If
	 * cancellable is false on the scheduled event, it will not be cancelled.
	 * 
	 * @param event the event to unschedule
	 */
	public static void unscheduleEvent(Event event) {
		List<ScheduledEvent> toRemove = new ArrayList<ScheduledEvent>();

		for (ScheduledEvent i : scheduledEvents) {
			if (i.getEvent() == event) {
				if (i.isCancellable()) {
					toRemove.add(i);
				}
			}
		}

		for (ScheduledEvent i : toRemove) {
			unscheduleEvent(i);
		}
	}

	/**
	 * unschedule all scheduled events programmed to run at the specified date. If
	 * cancellable is false on the scheduled event, it will not be cancelled.
	 * 
	 * @param date the date to unschedule. Time will be ignored so all events on the
	 *             entire day will be cancelled.
	 */
	public static void unscheduleEvent(Calendar date) {
		List<ScheduledEvent> toRemove = new ArrayList<ScheduledEvent>();

		for (ScheduledEvent i : scheduledEvents) {
			if (i.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)
					&& i.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& i.getDate().get(Calendar.DATE) == date.get(Calendar.DATE)) {
				if (i.isCancellable()) {
					toRemove.add(i);
				}
			}
		}

		for (ScheduledEvent i : toRemove) {
			unscheduleEvent(i);
		}
	}

	/**
	 * Schedule an event to run "delay" milliseconds after the current time
	 * 
	 * @param event       the event to run
	 * @param delay       the time to wait before calling the event
	 * @param cancellable whether this event can be cancelled
	 * @return the event that was scheduled
	 */
	public static ScheduledEvent scheduleEvent(Event event, Long delay, boolean cancellable) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.SECOND, (int) Math.ceil(delay.doubleValue() / 1000));

		ScheduledEvent e = new ScheduledEvent(new TimerTask() {
			public void run() {
				call(event, registeredListeners.toArray());
				forceUnscheduleEvent(this);
			}
		}, event, cancellable, date);

		scheduledEvents.add(e);
		eventTimer.schedule(e.getTask(), delay);

		return e;
	}

	/**
	 * Schedule an event to run at the specified date.
	 * 
	 * @param event       the event to run
	 * @param date        the date to run it at
	 * @param cancellable whether this event can be cancelled
	 * @return the event that was scheduled
	 */
	public static ScheduledEvent scheduleEvent(Event event, Calendar date, boolean cancellable) {
		ScheduledEvent e = new ScheduledEvent(new TimerTask() {
			public void run() {
				call(event, registeredListeners.toArray());
				forceUnscheduleEvent(this);
			}
		}, event, cancellable, date);

		scheduledEvents.add(e);
		eventTimer.schedule(e.getTask(), date.getTime());

		return e;
	}

	/**
	 * Schedule an event to run "delay" milliseconds after the current time and run
	 * on an interval of "interval" milliseconds afterwards
	 * 
	 * @param event       the event to run
	 * @param delay       the time to wait before calling the event
	 * @param interval    the time to wait between calls.
	 * @param cancellable whether this event can be cancelled
	 * @return the event that was scheduled
	 */
	public static ScheduledEvent scheduleEventWithInterval(Event event, Long delay, Long interval,
			boolean cancellable) {
		Calendar date = Calendar.getInstance();
		// Change the date by the amount of seconds the delay is
		date.add(Calendar.SECOND, (int) Math.ceil(delay.doubleValue() / 1000));

		ScheduledEvent e = new ScheduledEvent(new TimerTask() {
			public void run() {
				call(event, registeredListeners.toArray());
				// Change the date by the amount of seconds the interval is
				date.add(Calendar.SECOND, (int) Math.ceil(interval.doubleValue() / 1000));
			}
		}, event, cancellable, date);

		scheduledEvents.add(e);
		eventTimer.scheduleAtFixedRate(e.getTask(), delay, interval);

		return e;
	}

	/**
	 * Schedule an event to run at the specified date and run on an interval of
	 * "interval" milliseconds afterwards
	 * 
	 * @param event       the event to run
	 * @param delay       the time to wait before calling the event
	 * @param interval    the time to wait between calls.
	 * @param cancellable whether this event can be cancelled
	 * @return the event that was scheduled
	 */
	public static ScheduledEvent scheduleEventWithInterval(Event event, Calendar date, Long interval,
			boolean cancellable) {
		ScheduledEvent e = new ScheduledEvent(new TimerTask() {
			public void run() {
				call(event, registeredListeners.toArray());
				// Change the date by the amount of seconds the interval is
				date.add(Calendar.SECOND, (int) Math.ceil(interval.doubleValue() / 1000));
			}
		}, event, cancellable, date);

		scheduledEvents.add(e);
		eventTimer.scheduleAtFixedRate(e.getTask(), date.getTime(), interval);

		return e;
	}

	/**
	 * add an event listener to the list of event listeners (if it exists in the
	 * list)
	 * 
	 * @param listener the event listener to add
	 */
	public static void register(Object listener) {
		if (!isRegistered(listener)) {
			registeredListeners.add(listener);
		}
	}

	/**
	 * Remove an event listener from the list of event listeners (if it exists in
	 * the list)
	 * 
	 * @param listener the event listener to remove
	 */
	public static void unregister(Object listener) {
		if (isRegistered(listener)) {
			registeredListeners.remove(listener);
		}
	}

	/**
	 * Get all objects that are registered as event listeners
	 * 
	 * @return the list of objects that are registered as event listeners
	 */
	public static List<Object> getRegisterdListeners() {
		return registeredListeners;
	}

	/**
	 * Call the event. This is what objects other than EventManager will use.
	 * 
	 * @param event     the event to call
	 * @param newThread whether to call the event in a new thread. WILL NOT call
	 *                  each method in a new thread.
	 */
	public static void callEvent(Event event, boolean newThread) {
		if (newThread) {
			new Thread() {
				public void run() {
					call(event, registeredListeners.toArray());
				}
			}.start();
		} else {
			call(event, registeredListeners.toArray());
		}
	}

	/**
	 * Call the event. This is what objects other than EventManager will use. This
	 * version of the method allows you to override registered listeners.
	 * 
	 * @param event     the event to call
	 * @param newThread whether to call the event in a new thread. WILL NOT call
	 *                  each method in a new thread.
	 * @param listeners the specific objects that will recieve the message. Objects
	 *                  do not need to be registered in order for them to recieve
	 *                  the message.
	 */
	public static void callEvent(Event event, boolean newThread, Object[] listeners) {
		if (newThread) {
			new Thread() {
				public void run() {
					call(event, listeners);
				}
			}.start();
		} else {
			call(event, listeners);
		}
	}

	/**
	 * Call the event. All Event Handling methods inside of listeners will be called
	 * with the event passed to it (if the method is applicable). This includes
	 * private and static methods.
	 * 
	 * @param event the event to call
	 */
	private static void call(Event event, Object[] listeners) {
		for (Object listener : listeners) {
			Method[] methods;

			if (listener instanceof Class) {
				// If a class was passed, getClass method is not nescessary
				methods = ((Class<?>) listener).getDeclaredMethods();
			} else {
				methods = listener.getClass().getDeclaredMethods();
			}

			for (Method method : methods) {
				if (method.isAnnotationPresent(EventHandler.class)) {
					// If this listener was not passed as a class
					if (!(listener instanceof Class)) {
						try {
							// Make sure this method is compatible with parameters
							Class<?>[] parameters = method.getParameterTypes();
							if (parameters.length == 1 && parameters[0].isAssignableFrom(event.getClass())) {
								method.invoke(listener, event);
							}
						} catch (IllegalAccessException e) {
							// Try setting the method to public so we can call upon it
							method.setAccessible(true);
							try {
								// Make sure this method is compatible with parameters
								Class<?>[] parameters = method.getParameterTypes();
								if (parameters.length == 1 && parameters[0].isAssignableFrom(event.getClass())) {
									method.invoke(listener, event);
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							// Set the method back to private
							method.setAccessible(false);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
					// If a class was passed the method must be static to invoke upon.
					else if (Modifier.isStatic(method.getModifiers())) {
						try {
							// Make sure this method is compatible with parameters
							Class<?>[] parameters = method.getParameterTypes();
							if (parameters.length == 1 && parameters[0].isAssignableFrom(event.getClass())) {
								// Because the method is static, obj is ignored.
								method.invoke(null, event);
							}
						} catch (IllegalAccessException e) {
							// Try setting the method to public so we can call upon it
							method.setAccessible(true);
							try {
								// Make sure this method is compatible with parameters
								Class<?>[] parameters = method.getParameterTypes();
								if (parameters.length == 1 && parameters[0].isAssignableFrom(event.getClass())) {
									// Because the method is static, obj is ignored.
									method.invoke(null, event);
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							// Set the method back to private
							method.setAccessible(false);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		if (event.getAfterwardEvent() != null) {
			call(event.getAfterwardEvent(), registeredListeners.toArray());
		}
	}
}