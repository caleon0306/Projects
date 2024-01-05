/**
 * @author Connell Leon
 * Convert from in-fix to prefix or post fix
 */
package assg7_leonc22;

import  java.util.*;

public class CalculatorDemo {

	public static void main(String[] args) {
		Scanner kbd = new Scanner(System.in);
		boolean choice = true;
		Calculator input = null;
		
		System.out.println("Supports multi-digit numbers");
		while(choice) {
			if(choice) {
				System.out.println();
				System.out.print("Enter an infix expression:");
				input = new Calculator(kbd.nextLine());
				try {
					System.out.println("Postfix: " + input.getPostFix());
					System.out.println("Evaluate: " + input.evaluate());
					System.out.println();
				}
				catch(IllegalStateException e) {
					System.out.println("Conversion failed.");
				}
			}
			choice = choose(kbd);
		}
	}

	/**
	 * Ask for user input until correct input is entered
	 * @param kbd
	 * @return
	 */
	public static boolean choose(Scanner  kbd) {
		int choice = -1;
		while(true) {
			System.out.println("Enter 1 for another conversion");
			System.out.println("Enter 0 to quit");
			System.out.print("Choice:");
			choice = kbd.nextInt();
			kbd.nextLine();
			
			if(choice == 1) {
				return true;
			}
			else if(choice == 0) {
				return false;
			}
			System.out.println("Invalid Input.");
			System.out.println();
		}
	}
}
