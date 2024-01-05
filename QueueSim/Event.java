/**
 * @author Connell Leon
 * Create departure and arrival events for a bank
 */
package assg8_leonc22;

public class Event implements Comparable<Event>{

	// a char for arrival, d char for departure
	private char type;
	private int when;
	private int length;
	
	/**
	 * Constructor for arrival event
	 * @param t
	 * @param w
	 * @param l
	 */
	public Event(char t, int w, int l) {
		type = t;
		when = w;
		length = l;
	}
	
	/**
	 * Constructor for departure event
	 * @param t
	 * @param w
	 */
	public Event(char t, int w) {
		type = t;
		when = w;
		length = -1;
	}
	
	/**
	 * Get type of Event
	 * @return
	 */
	public char getType() {
		return type;
	}
	
	/**
	 * Get when event occurs
	 * @return
	 */
	public int getWhen() {
		return when;
	}
	
	/**
	 * Return length of transaction time for arrival Event
	 * Return -1 for departure Event
	 * @return
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Output arrival or departure details
	 */
	public String toString() {
		if(type == 'a') {
			return "Processing an arrival event at time " + when;
		}
		else if(type == 'd'){
			return "Processing a departure event at time " + when;
		}
		return "";
	}
	
	/**
	 * Compare two events with earlier times first
	 * Arrival before departure if they have the same time
	 */
	public int compareTo(Event diffEvent) {
		//compare times
		if(this.when < diffEvent.when) {
			return -1;
		}
		else if(this.when > diffEvent.when) {
			return 1;
		}
		//check for which event is an arrival
		if(this.type == 'a') {
			return -1;
		}
		return 1;
	}
}
