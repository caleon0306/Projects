/**
 * @author Connell Leon
 */
package assg9_leonc22;

import java.util.*;

public class PhoneBookManager {

	public static void main(String[] args) {
		Scanner kbd = new Scanner(System.in);
		int selection = 0;
		PhoneBook phoneBook = new PhoneBook();
		String inputFile = "assg9_phoneBook.txt";
		
		phoneBook.loadDate(inputFile);
		
		//Loop through options until Save and Exit selected
		while(selection != 6) {
			selection = menu(phoneBook, kbd);
			switch(selection) {
			case 1:
				addContact(phoneBook, kbd);
				break;
			case 2:
				deleteContact(phoneBook, kbd);
				break;
			case 3:
				searchNum(phoneBook, kbd);
				break;
			case 4:
				updateNum(phoneBook, kbd);
				break;
			case 5:
				phoneBook.printPhoneBook();
				break;
			case 6:
				phoneBook.Save();
				break;
			default:
				System.out.println("Invalid Selection");
			}
			if(selection != 6) {
				System.out.println();
				System.out.println("Enter to continue");
				kbd.nextLine();
			}
		}
		kbd.close();
	}

	/**
	 * Get user selection
	 * @param pb
	 * @param kbd
	 * @return
	 */
	public static int menu(PhoneBook pb, Scanner kbd) {
		int s;
		System.out.println("1. Add Contact");
		System.out.println("2. Delete Contact");
		System.out.println("3. Search Number");
		System.out.println("4. Update  Number");
		System.out.println("5. Display Sorted PhoneBook");
		System.out.println("6. Save and Exit");
		s = kbd.nextInt();
		kbd.nextLine();
		return s;
	}
	
	/**
	 * Get a name and number to send to PhoneBook to add contact
	 * @param pb
	 * @param kbd
	 */
	public static void addContact(PhoneBook pb, Scanner kbd) {
		String name, num;
		System.out.print("Enter Name: ");
		name = kbd.nextLine();
		//Check if name already taken
		if(!pb.exsistName(name)) {
			System.out.print("Enter Number: ");
			num = kbd.nextLine();
			pb.addContact(name, num);
			System.out.println("Contact Added");
		}
		else {
			System.out.println("Contact Alredt Exsists");
		}
	}
	
	/**
	 * Get name to delete and send to PhoneBook
	 * @param pb
	 * @param kbd
	 */
	public static void deleteContact(PhoneBook pb, Scanner kbd) {
		String name;
		System.out.print("Enter Name: ");
		name = kbd.nextLine();
		pb.deleteContact(name);
	}
	
	/**
	 * Search for a phone number based on name
	 * @param pb
	 * @param kbd
	 */
	public static void searchNum(PhoneBook pb, Scanner kbd) {
		String name, num = null;
		System.out.print("Enter Name: ");
		name = kbd.nextLine();
		num = pb.findNum(name);
		if(num != null) {
			System.out.println("Phone Number: " + num);
		}
		else {
			System.out.println("Name not found.");
		}
	}
	
	/**
	 * Update a phone number given Contact's name
	 * @param pb
	 * @param kbd
	 */
	public static void updateNum(PhoneBook pb, Scanner kbd) {
		String name, num;
		System.out.print("Enter Name: ");
		name = kbd.nextLine();
		if(pb.exsistName(name)) {
			System.out.print("Enter New Number: ");
			num = kbd.nextLine();
			pb.updateNum(name, num);
		}
		else {
			System.out.println("Name not found.");
		}
	}
}
