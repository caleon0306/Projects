/**
 * @author Connell Leon
 * Class to hold information of a person and their phone number
 */
package assg9_leonc22;

public class Person extends KeyedItem<String>{

	private String num;
	
	/**
	 * Constructor with name and num
	 * @param name
	 * @param num
	 */
	public Person(String name, String n) {
		super(name);
		num = n;
	}
	
	/**
	 * Set new number
	 * @param n
	 */
	public void setNum(String n) {
		num = n;
	}
	
	/**
	 * Return number
	 * @return
	 */
	public String getNum() {
		return num;
	}
	
	/**
	 * Return name
	 * @return
	 */
	public String getName() {
		return getKey();
	}
	
	/**
	 * Return name and number
	 */
	public String toString() {
		return getKey() + ": " + num;
	}
}
