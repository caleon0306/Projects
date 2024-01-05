/**
 * @author Connell Leon
 * Simulate arrival and departure of customers at a single teller bank
 */
package assg8_leonc22;

import java.io.*;
import java.util.*;

public class Simulation {

	public static void main(String[] args) {

		Scanner input = null;
		String inFile = "assg8_input.txt";
		//Queue
		ArrayDeque<Event> line = new ArrayDeque<Event>();
		//EventList
		EventList events = new EventList();
		//time of next event
		int currentTime = 0;
		//Place holder to transfer event details
		Event newEvent = null;	
		
		try {
			input = new Scanner(new File(inFile));
		}
		catch(FileNotFoundException e) {
			System.out.println("ERROR: " + inFile + " not found.");
			System.exit(0);
		}
		
		events.addEvent(new  Event('a', input.nextInt(), input.nextInt()));
		
		while(!events.isEmpty()) {
			newEvent = events.nextEvent();
			if(newEvent.getType() == 'a') {
				currentTime = processArrival(newEvent, events, line, inFile, currentTime, input);
			}
			else {
				currentTime = processDeparture(newEvent, events, line, currentTime);
			}
		}
		input.close();
		
		System.out.println("Simulation Over");
		System.out.println();
		System.out.println("Customers Served: " + events.customersServed());
		System.out.println("Average Wait Time: " + events.avgWaitTime());
	}
	
	/**
	 * Next event is an arrival
	 * @param newEvent
	 * @param events
	 * @param line
	 * @param inFile
	 * @param currentTime
	 * @param input
	 * @return
	 */
	public static int processArrival(Event newEvent, EventList events, ArrayDeque<Event>line, String inFile, int currentTime, Scanner input) {
		boolean atFront;
		System.out.println(newEvent);
		atFront = line.isEmpty();
		line.add(events.nextEvent());
		events.deleteEvent();
		if(atFront) {
			currentTime = newEvent.getWhen();
			events.addEvent(new Event('d', currentTime + newEvent.getLength()));
		}
		if(input.hasNextLine()) {
			events.addEvent(new Event('a', input.nextInt(), input.nextInt()));
		}
		return currentTime;
	}
	
	/**
	 * Next event is a departure
	 * @param newEvent
	 * @param events
	 * @param line
	 * @param currentTime
	 * @return
	 */
	public static int processDeparture(Event newEvent, EventList events, ArrayDeque<Event> line, int currentTime) {
		events.addWaitTime(currentTime - line.peek().getWhen());
		line.remove();
		System.out.println(events.nextEvent());
		currentTime = events.nextEvent().getWhen();
		events.deleteEvent();
		if(!line.isEmpty()) {
			events.addEvent(new Event('d', currentTime + line.peek().getLength()));
		}
		return currentTime;
	}
}
