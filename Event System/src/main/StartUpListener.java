package main;

import events.EventHandler;
import events.EventManager;
import events.main.StartUpEvent;

public class StartUpListener {
	public StartUpListener() {
		//Object registers itself so the parent class of this object doesn't have to.
		this.register();
	}
	
	/**
	 * Register this object as an event listener
	 */
	public void register() {
		EventManager.register(this);
	}
	
	/**
	 * Make sure event system recognizes private methods
	 * 
	 * @param e the event
	 */
	@EventHandler
	private void run2(StartUpEvent e) {
		System.out.println("private method ran");
	}
	
	/**
	 * Make sure event system recognizes public methods
	 * 
	 * @param e the event
	 */
	@EventHandler
	public void run(StartUpEvent e) {
		System.out.println("public method ran");
	}
}
