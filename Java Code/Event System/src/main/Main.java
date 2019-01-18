package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import events.EventHandler;
import events.EventManager;
import events.main.AfterStartupEvent;
import events.main.CancelDayEvent;
import events.main.CancelEvent;
import events.main.DateEvent;
import events.main.RepeatingEvent;
import events.main.StartUpEvent;
import events.main.TestEvent;

public class Main {

	public static RepeatingEvent event1;
	public static CancelEvent event2;

	/** A listener for the start up event */
	public static StartUpListener listener = new StartUpListener();

	/**
	 * Make sure event system recognizes public static methods
	 * 
	 * @param e the event
	 */
	@EventHandler
	public static void run(StartUpEvent e) {
		System.out.println("public static method ran");
	}

	/**
	 * Make sure event system recognizes private static methods
	 * 
	 * @param e the event
	 */
	@EventHandler
	private static void run2(StartUpEvent e) {
		System.out.println("private static method ran");
	}

	@EventHandler
	public static void onTest(TestEvent e) {
		System.out.println("Test event was not cancelled.");
	}

	@EventHandler
	public static void onRepeat(RepeatingEvent e) {
		System.out.println("Hello World");
	}

	@EventHandler
	public static void onCancelledDay(DateEvent.onCancelled e) {
		System.out.println("Detected that alarm event was cancelled.");
	}

	@EventHandler
	public static void cancelAlarm(CancelDayEvent e) {
		EventManager.unscheduleEvent(Calendar.getInstance());
		System.out.println("Cancelled all events for today.");
	}

	@EventHandler
	public static void alarm(DateEvent e) {
		System.out.println("alarm");
		EventManager.scheduleEvent(new CancelDayEvent(), 4900L, true);
	}

	@EventHandler
	public static void cancelRepeat(CancelEvent e) {
		System.out.println("Hello World should not run anymore.");
		EventManager.unscheduleEvent(event1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm:ss a");
		Calendar date = Calendar.getInstance();
		System.out.println("The date is " + dateFormat.format(date.getTime()));
		date.add(Calendar.SECOND, 5);
		System.out.println("At " + dateFormat.format(date.getTime())
				+ ", 'alarm' will be printed. It will then be printed every 1 second afterward.");

		DateEvent event = new DateEvent();
		EventManager.scheduleEventWithInterval(event, date, 1000L, true);
	}

	@EventHandler
	public static void afterStart(AfterStartupEvent e) {
		// Test to see if private modifiers persisted through the event call
		try {
			if (Main.class.getDeclaredMethod("run2", StartUpEvent.class).isAccessible()) {
				System.out.println("Error! Private static method is now public");
			} else {
				System.out.println("Private static method is still private");
			}

			if (StartUpListener.class.getDeclaredMethod("run2", StartUpEvent.class).isAccessible()) {
				System.out.println("Error! Private method is now public");
			} else {
				System.out.println("Private method is still private");
			}
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}

		TestEvent event = new TestEvent();
		// This event should not call
		EventManager.scheduleEvent(event, 500L, true);
		// Cancelling should succeed
		EventManager.unscheduleEvent(event);

		// This event should call every second
		event1 = new RepeatingEvent();
		EventManager.scheduleEventWithInterval(event1, 1000L, 1000L, true);
		System.out.println("If \"Hello World\" is not printed 3 times at a 1 second interval, there is a problem.");

		// This event should cancel the "HelloWorld" event.
		CancelEvent event2 = new CancelEvent();
		EventManager.scheduleEvent(event2, 3000L, false);
	};

	public static void main(String[] args) {
		// Register this class as an event listener
		EventManager.register(Main.class);
		System.out.println("Program should start after 1 second");
		// Call the event
		EventManager.scheduleEvent(new StartUpEvent(), 500L, true);
	}
}
