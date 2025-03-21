package lab07;

import java.util.*;

//Extension of Chapter 14.4 Case Study: Expression Evaluator

public class Postfixer {


/**
*  Determines if the first operator has same or greater
    *  precedence than the second
*
* @param op1 the first operator
* @param op2 the second operator
* @return the boolean result
*/
	private static boolean hasPrecedence(String op1, String op2) {
		return opToPrcd(op1) >= opToPrcd(op2); // placeholder
	}


/**
* Converts an operator to its precedence priority
*
* We expect you to be able to handle +, -, *, /, ^, and (
* (why don't you need ")" as well? see algorithm in part 4)
*
* The order of these is as follows:
*    ^, * and /, + and -, (
*
* @param op a string representing an operator, e.g. "+" or "-"
* @return an integer value reflecting its precedence
*/
	private static int opToPrcd(String op) {
		switch (op){
		case ("^"):
			return 4; 
		case ("*"):
		case ("/"):
			return 3;
		case ("+"):
		case ("-"):
			return 2;
		case ("("):
			return 1;
		default:
			return 0;
		} 
// placeholder
	}

/**
* determines if the input token is an operator
*
* @param token the string token to check
* @return a boolean reflecting the result
*/
	private static boolean isOperator(String token) {
		return (token.equals("^") || token.equals("*") || token.equals("/") || token.equals("+") || token.equals("-") || token.equals("(") || token.equals(")"));
	}

/**
    * Evaluates an expression
    *
    * NOTE Beware the order of pop and evaluation when receiving operand1
    * and operand2 as input.
    *
    * @param operand1 the first operand
    * @param operator the operator to apply
    * @param operand2 the second operand
    * @return a double expressing the result
    * @throws IllegalArgumentException if operator passed is not one of
    *  "+", "-", "*", "/", or "^"
    *
*/
	private static double evaluate(double operand1, String operator, double operand2){
		switch (operator){
		case ("^"):
			return Math.pow(operand1, operand2);
		case ("*"):
			return operand1 * operand2;
		case ("/"):
			return operand1 / operand2;
		case ("+"):
			return operand1 + operand2;
		case ("-"):
			return operand1 - operand2;
		default:
			throw new RuntimeException("IllegalArgumentException");
		}
	}


/**
* give a description of the purpose of this method
* @param line fill in
* @return fill in
*/
	public static double infixEvaluator(String line){
		StringSplitter data = new StringSplitter(line);
		Stack<String> operators = new Stack<String>();
		Stack<Double> operands = new Stack<Double>();

		while (data.hasNext()){
			String str = data.next();
			if (!isOperator(str)){
				operands.push(Double.parseDouble(str));
			} 
			
			else if (str.equals("(")) {
				operators.push(str);
			} 
			
			else if (str.equals(")")) {
				while (!operators.peek().equals("(")){
					String operator = operators.pop();
					double operand2 = operands.pop();
					double operand1 = operands.pop();
					operands.push(evaluate(operand1, operator, operand2));
				}
				operators.pop();
			} 
			
			else if (isOperator(str)) {
				while (operators.size() > 0 && hasPrecedence(operators.peek(), str) && operands.size() >= 2){
					String operator = operators.pop();
					double operand2 = operands.pop();
					double operand1 = operands.pop();
					operands.push(evaluate(operand1, operator, operand2));
				}
				operators.push(str);
			}
		}

		while (operators.size() > 0 && operands.size() >= 2){
			String operator = operators.pop();
			double operand2 = operands.pop();
			double operand1 = operands.pop();
			operands.push(evaluate(operand1, operator, operand2));
		}

		if (operators.isEmpty() == true && operands.size() == 1){
			return operands.pop();
		} 
		return -1; // placeholder
	}

/**
* give a description of the purpose of this method
* @param line fill in
* @return fill in
*/
	public static String toPostfix(String line){
		StringSplitter data = new StringSplitter(line);
		Stack<String> operators = new Stack<String>();
		String res = "";

		while (data.hasNext()){
			String str = data.next();

			if (!isOperator(str)){
				res += str;
			}

			if (str.equals("(")){
				operators.push(str);
			}

			if (str.equals(")")){
				while (!operators.peek().equals("(") && !operators.peek().equals(")")){
					res += operators.pop();
				}
				operators.pop();
			}

			if (isOperator(str) && !str.equals("(") && !str.equals(")")){
				while (!operators.peek().equals("(") && hasPrecedence(operators.peek(), str)){
					res += operators.pop();
				}
				operators.push(str);
			}
		}
		return res; // placeholder
	}


	public static void main(String[] args){

        if (infixEvaluator("10 + 2") != 12)
            System.err.println("test1 failed --> your answer should have been 12");

        if (infixEvaluator("10 - 2 * 2 + 1") != 7)
            System.err.println("test2 failed --> your answer should have been 7");

        if (infixEvaluator("100 * 2 + 12") != 212)
            System.err.println("test3 failed --> your answer should have been 212");

        if (infixEvaluator("100 * ( 2 + 12 )") != 1400)
            System.err.println("test4 failed --> your answer should have been 1400");

        if (infixEvaluator("100 * ( 2 + 12 ) / 14") != 100)
            System.err.println("test5 failed --> your answer should have been 100");


        System.out.println("Lab Testing Done!!!");

        /* uncomment the below lines for assignment */
        if (!toPostfix(new String("(4+5)")).equals("45+"))
        	System.err.println("test1 failed --> should have been 45+");


        if (!toPostfix(new String("((4+5)*6)")).equals("45+6*"))
        	System.err.println("test2 failed --> should have been 45+6*");

        if (!toPostfix(new String("((4+((5*6)/7))-8)")).equals("456*7/+8-"))
        	System.err.println("test3 failed --> should have been 456*7/+8-");

        if (!toPostfix(new String("((4+5*(6-7))/8)")).equals("4567-*+8/"))
        	System.err.println("test4 failed --> should have been 4567-*+8/");

        if (!toPostfix(new String("(9+(8*7-(6/5^4)*3)*2)")).equals("987*654^/3*-2*+"))
        	System.err.println("test5 failed --> should have been 987*654^/3*-2*+");


         System.out.println("Assignment Testing Done!!!");


	}

}