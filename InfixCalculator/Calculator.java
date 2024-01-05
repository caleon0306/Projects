/**
 * @author Connell Leon
 */
package assg7_leonc22;

import java.util.*;

public class Calculator {

	private String infix = "";
	private String postfix = "";
	
	/**
	 * One parameter constructor
	 * @param exp
	 */
	public Calculator(String exp) {
		infix = exp;
	}
	
	/**
	 * @return infix
	 */
	public String toString() {
		return infix;
	}
	
	/**
	 * @return postfix
	 * @throws IllegalStateException
	 */
	public String getPostFix() throws IllegalStateException{
		if(convertPostFix()) {
			return postfix;
		}
		throw new IllegalStateException();
	}
	
	/**
	 * Convert from infix to postfix
	 * @return postfix successful or not
	 */
	private boolean convertPostFix() {
		Stack<Character> stack = new Stack<Character>();
		
		//see if there are open parenthesis
		boolean openPar = false;
		
		//check if number is multiple digits
		boolean runNum = false;
		
		//check for precedence
		boolean highPre = false;
		
		//hold multiple digit number
		int num = 0;
		
		//holds cur char in infix
		char cur;
		
		for(int i = 0; i < infix.length(); i++) {
			cur = infix.charAt(i);
			if(Character.isDigit(cur)) {
				if(!runNum) {
					num = Integer.parseInt(cur + "");
					runNum = true;
				}
				else {
					num = num * 10 + Integer.parseInt(cur + "");
				}
				if(i + 1 == infix.length()) {
					postfix = postfix + num + " ";
				}
			}
			else {
				if(runNum) {
					postfix = postfix + num + " ";
					runNum = false;
					num = 0;
				}
				if(cur == '(') {
					stack.push(cur);
					openPar = true;
				}
				else if(cur == ')') {
					if(!openPar) {
						return false;
					}
					while(stack.peek() != '(') {
						postfix = postfix + stack.pop();
					}
					stack.pop();
					openPar = false;
				}
				else if(cur == '-' || cur == '+' || cur == '*' || cur == '/') {
					highPre = false;
					while(!stack.isEmpty() && stack.peek() != '(' && !highPre) {
						if(cur == '-' || cur == '+') {
							postfix = postfix + stack.pop();
						}
						else {
							if(stack.peek() == '*' || stack.peek() == '/') {
								postfix = postfix + stack.pop();
							}
							else {
								highPre = true;
							}
						}
					}
					stack.push(cur);
				}
			}
		}
		if(openPar) {
			return false;
		}
		while(!stack.isEmpty()) {
			postfix = postfix + stack.pop();
		}
		return true;
	}
	
	/**
	 * Calculate postfix expression
	 * @return evaluated postfix
	 * @throws IllegalStateException
	 */
	public int evaluate() throws IllegalStateException{
		if(postfix == "") {
			throw new IllegalStateException();
		}
		
		Stack<Integer> stack = new Stack<Integer>();
		
		//holds cur char
		char cur;
		
		//know if looking at multidigit num
		boolean runNum = false;
		
		//hold multidigit nums
		int num = 0;
		
		//hold calculations
		int result, pop1, pop2;
			
		for(int i = 0; i < postfix.length(); i ++) {
			cur = postfix.charAt(i);
			if(Character.isDigit(cur)) {
				if(!runNum) {
					num = Integer.parseInt(cur + "");
					runNum = true;
				}
				else {
					num = num * 10 + Integer.parseInt(cur + "");
				}
			}
			else {
				if(runNum) {
					stack.push(num);
					runNum = false;
					num = 0;
				}
				switch (cur){
					case '+':
						pop1 = stack.pop();
						pop2 = stack.pop();
						result = pop2 + pop1;
						stack.push(result);
						break;
					case '-':
						pop1 = stack.pop();
						pop2 = stack.pop();
						result = pop2 - pop1;
						stack.push(result);
						break;
					case '*':
						pop1 = stack.pop();
						pop2 = stack.pop();
						result = pop2 * pop1;
						stack.push(result);
						break;
					case '/':
						pop1 = stack.pop();
						pop2 = stack.pop();
						result = pop2 / pop1;
						stack.push(result);
						break;
				}
			}
		}
		return stack.pop();
	}
}
