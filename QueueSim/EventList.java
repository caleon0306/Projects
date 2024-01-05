package assg8_leonc22;

import java.util.*;

public class EventList {

	private ArrayList<Event> events;
	private int customers;
	private double waitTime;
	
	/**
	 * Initialize EventList
	 */
	public EventList() {
		events = new ArrayList<Event>();
		customers = 0;
		waitTime = 0;
	}
	
	/**
	 * Check if events is empty
	 * @return
	 */
	public boolean isEmpty() {
		if(events.size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Add an Event to EventList and return true/false
	 * @param e
	 * @return
	 */
	public boolean addEvent(Event e) {
		//holds Event is there is one event in events
		Event nextEvent;
		//nothing in list add any event
		if(events.size() == 0) {
			events.add(e);
			return true;
		}
		else if(events.size() == 1) {
			nextEvent = events.get(0);
			//check to make sure not adding same Event type
			if(nextEvent.getType() == e.getType()) {
				return false;
			}
			//see which event occurs first and add accordingly
			if(nextEvent.compareTo(e) < 0) {
				events.add(e);
				return true;
			}
			else {
				events.set(0, e);
				events.add(nextEvent);
				return true;
			}
		}
		//more than 1 event in list, cannot add
		return false;
	}
	
	/**
	 * Delete first  Event
	 */
	public void deleteEvent() {
		if(events.get(0).getType() == 'a') {
			customers++;
		}
		events.remove(0);
	}
	
	/**
	 * get next Event
	 * @return
	 */
	public Event nextEvent() {
		return events.get(0);
	}
	
	/**
	 * Get amount of customers served
	 * @return
	 */
	public int customersServed() {
		return customers;
	}
	
	/**
	 * Add total waitTime
	 * @param time
	 */
	public void addWaitTime(double time) {
		waitTime = waitTime + time;
	}
	
	/**
	 * returnavg wait time
	 * @return
	 */
	public double avgWaitTime() {
		return waitTime/customers;
	}
}
