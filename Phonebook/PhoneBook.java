/**
 * @author Connell Leon
 * Class to hold a phoneBook containing multiple Persons
 */
package assg9_leonc22;

import java.util.*;
import java.io.*;

public class PhoneBook {

	BinarySearchTree<Person, String> phoneBook;
	private String fileName;
	
	/**
	 * Default constructor
	 */
	public PhoneBook() {
		phoneBook = new BinarySearchTree<Person, String>();
		fileName = null;
	}
	
	/**
	 * Load data from input file into phoneBook
	 * @param file
	 */
	public void loadDate(String file) {
		Scanner input = null;
		String[] fileLine = new String[2]; 
		try {
			input = new Scanner(new File(file));
			fileName = file;
		}
		catch(FileNotFoundException e) {
			System.out.println("ERROR: " + file + " not found");
			System.exit(1);
		}
		if(file.length() != 0) {
			while(input.hasNextLine()) {
				fileLine = input.nextLine().split("\t");
				Person newC = new Person(fileLine[0], fileLine[1]);
				phoneBook.insert(newC);
			}
		}
	}
	
	/**
	 * Check if name is taken
	 * @param name
	 * @return
	 */
	public boolean exsistName(String name) {
		if(!phoneBook.isEmpty()) {
			if(phoneBook.retrieve(name) != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add Person to phoneBook
	 * @param name
	 * @param num
	 */
	public void addContact(String name, String num) {
		Person newP = new Person(name, num);
		phoneBook.insert(newP);
	}
	
	/**
	 * Delete contact from phoneBook
	 * @param name
	 */
	public void deleteContact(String name) {
		try {
			phoneBook.delete(name);
			System.out.println("Contact Removed.");
		}
		catch(TreeException e) {
			System.out.println("ERROR: " + name + " not found.");
		}
	}
	
	/**
	 * Print out phoneBook contents
	 */
	public void printPhoneBook() {
		TreeIterator<Person> iter = new TreeIterator<Person>(phoneBook);
		iter.setInorder();
		
		System.out.println();
		
		//Check for empty phoneBook
		if(!phoneBook.isEmpty()) {
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
		else {
			System.out.println("PhoneBook is empty");
		}
		
	}
	
	/**
	 * Return number of person based on name
	 * Return null if no name matches
	 * @param name
	 * @return
	 */
	public String findNum(String name) {
		TreeIterator<Person> iter = new TreeIterator<Person>(phoneBook);
		iter.setInorder();
		Person currC = null;
		
		while(iter.hasNext()) {
			currC = iter.next();
			if(currC.getKey().compareTo(name) == 0) {
				return currC.getNum();
			}
		}
		return null;
	}
	
	/**
	 * Update Contact's number
	 * @param name
	 * @param num
	 */
	public void updateNum(String name, String num) {
		TreeIterator<Person> iter = new TreeIterator<Person>(phoneBook);
		iter.setInorder();
		Person currC = null;
		
		while(iter.hasNext()) {
			currC = iter.next();
			if(currC.getKey().compareTo(name) == 0) {
				currC.setNum(num);
				System.out.println("Number Updated.");
			}
		}
	}
	
	/**
	 * Write back updated phoneBook to file that PhoneBook read from
	 */
	public void Save() {
		TreeIterator<Person> iter = new TreeIterator<Person>(phoneBook);
		iter.setInorder();
		Person currC = null;
		PrintWriter output = null;
		
		try {
			output = new PrintWriter(fileName);
		}
		catch(IOException e) {
			System.out.println("ERROR: Could not write to " + fileName);
			System.exit(1);
		}
		
		while(iter.hasNext()) {
			currC = iter.next();
			output.print(currC.getName() + "\t" + currC.getNum());
			if(iter.hasNext()) {
				output.println();
			}
		}
		
		output.close();
	}
}
