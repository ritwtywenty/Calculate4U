package br.com.calculate4u.parser;

import java.util.ArrayList;
import java.util.Stack;

public class CalculatorParser {

	private String langSimbols;

	public CalculatorParser() {
		langSimbols = "1234567890+-*/().^";
	}

	private String removeSpace(String string) {
		String clean = "";
		for (int i = 0; i < string.length(); i++) {
			char current = string.charAt(i);
			if (current != ' ')
				clean += current;
		}
		return clean;
	}

	private boolean lexer(String string) {
		for (int i = 0; i < string.length(); i++) {
			char current = string.charAt(i);
			if (!langSimbols.contains("" + current))
				return false;
		}
		return true;
	}

	public boolean isDigit(char c) {
		if (c >= '0' && c <= '9')
			return true;
		return false;
	}

	public boolean isOperator(char c) {
		if (c == '+' || c == '~' || c == '*' || c == '/' || c == '^')
			return true;
		return false;
	}

	public boolean isOperator(String s) {
		if (s.equals("+") || s.equals("~") || s.equals("*") || s.equals("/") || s.equals("^"))
			return true;
		return false;
	}

	public boolean isOperand(char c) {
		if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
			return true;
		return false;
	}

	public int precedence(char c) {
		if (c == '^')
			return 3;
		if (c == '*' || c == '/')
			return 2;
		if (c == '+' || c == '~')
			return 1;
		return -1;
	}

	public String infToPos(String string) {
		Stack<Character> stack = new Stack<>();
		String output = "";
		Character c;
		int i;
		for (i = 0; i < string.length(); i++) {
			c = string.charAt(i);
			if (Character.isLetterOrDigit(c) || c == '.' || c == '-') {
				output += c;
			} else if (c == '(') {
				stack.push(c);
			} else if (c == ')') {
				while (!stack.empty() && stack.peek() != '(') {
					output += ' ';
					output += stack.pop();
				}
				if (!stack.empty() && stack.peek() != '(')
					return "Invalid OP";
				else
					stack.pop();
			} else {
				while (!stack.empty() && precedence(c) <= precedence(stack.peek())) {
					output += ' ';
					output += stack.pop();
				}
				output += ' ';
				stack.push(c);
			}
		}
		while (!stack.empty()) {
			output += ' ';
			output += stack.pop();
		}
		return output;
	}

	public double solve(String string) throws Exception {
		double result = 0;
		double op1 = 0, op2 = 0;
		ArrayList<String> array = new ArrayList<>();
		String[] splited = string.split(" ");
		int i;
		for (i = 0; i < splited.length; i++) {
			array.add(splited[i]);
		}
		while (array.size() > 1) {
			i = 0;
			while (!isOperator(array.get(i)))
				i++;
			i -= 2;
			op1 = Double.parseDouble(array.get(i));
			array.remove(i);
			op2 = Double.parseDouble(array.get(i));
			array.remove(i);
			switch (array.get(i)) {
			case "+":
				result = op1 + op2;
				break;
			case "~":
				result = op1 - op2;
				break;
			case "*":
				result = op1 * op2;
				break;
			case "/":
				result = op1 / op2;
				break;
			case "^":
				result = Math.pow(op1, op2);
			}
			array.remove(i);
			array.add(i, "" + result);
		}
		return Double.parseDouble(array.get(0));
	}

	public String parser(String string) {
		string = string.trim();
		string = removeSpace(string);
		if (!lexer(string))
			return "Lexical error";

		String string2 = "";
		int i;
		for (i = 0; i < string.length() - 1; i++) {
			if (string.charAt(i) == '-')
				try {
					if (isDigit(string.charAt(i + 1)) && !isDigit(string.charAt(i - 1)))
						string2 += string.charAt(i);
					else
						string2 += '~';
				}catch(Exception e) {
					string2 += string.charAt(i);
				}
			else
				string2 += string.charAt(i);
		}
		string2 += string.charAt(i);
		string = string2;
		
		string = infToPos(string);
		if (string == "Invalid OP")
			return "Invalid Operation";
		else {
			try {
				Double result = solve(string);
				return "" + result;
			} catch (Exception e) {
				return "Invalid Operation";
			}
		}
	}

}
