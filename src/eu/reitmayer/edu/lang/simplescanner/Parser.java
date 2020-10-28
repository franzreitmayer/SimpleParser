package eu.reitmayer.edu.lang.simplescanner;

import static eu.reitmayer.edu.lang.simplescanner.Token.TokenType.*;
import eu.reitmayer.edu.lang.simplescanner.Token.TokenType;

/**
 * Parser class
 * @author franz
 *
 */
public class Parser {

	/**
	 * Scanner
	 */
	private Scanner scanner;
	
	/**
	 * Current token
	 */
	private Token currentToken;
	
	/**
	 * Shortcut to current token
	 */
	private Token t;
	
	/**
	 * Constructor
	 * @param scanner an instance of the underlying scanner
	 */
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Starts the parsing
	 * @return the evaluated expression
	 */
	public Integer parse() {
		nextToken();
		return expr();
	}
	
	/** 
	 * expression rule
	 * @return value of rule expr
	 */
	private Integer expr() {
		if (t.tokenType == INT || t.tokenType == OPEN) {
			Integer f = fact();
			return expr1(f);
		} else {
			errorExit();
		}
		return null;
	}
	
	/**
	 * expr' rule
	 * @param firstInt the integer value provided at the preceding expr rule 
	 * @return the value of the expr'
	 */
	private Integer expr1(Integer firstInt) {
		// Token t = nextToken();
		if (t.tokenType == PLUS) {
			match(PLUS);
			Integer f = fact();
			return expr1(f + firstInt);
		} else if (t.tokenType == MINUS) {
			match(MINUS);
			Integer f = fact();
			return expr1(firstInt - f);
		} else if (t.tokenType == CLOSE || t.tokenType == EOF) {
			return firstInt;
		} else {
			errorExit();
		}
		return null;
	}
	
	/**
	 * The fact rule
	 * @return the value of the fact rule
	 */
	private Integer fact() {
		// Token t = nextToken();
		if (t.tokenType == INT || t.tokenType == OPEN) {
			Integer a = atom();
			return fact1(a);
		} else {
			errorExit();
		}
		return null;
	}
	
	/**
	 * The fact' rule
	 * @param firstInt
	 * @return the rules value
	 */
	private Integer fact1(Integer firstInt) {
		//Token t = nextToken();
		if (t.tokenType == TIMES) {
			match(TIMES);
			Integer a = atom();
			return fact1(firstInt * a);
		} else if (t.tokenType == DIVIDE) {
			match(DIVIDE);
			Integer a = atom();
			return fact1(firstInt / a);
		} else if (t.tokenType == CLOSE || t.tokenType == PLUS || t.tokenType == EOF || t.tokenType == MINUS) {
			return firstInt;
		} else {
			errorExit();
		}
		return null;
	}
	
	/**
	 * the rule atom
	 * @return the value of the rule
	 */
	private Integer atom() {
		//Token t = nextToken();
		if (t.tokenType == INT) {
			Integer i = Integer.parseInt(t.stringUnderToken);
			nextToken();
			return i;
		} else if (t.tokenType == OPEN) {
			match(OPEN);
			Integer e = expr();
			match(CLOSE);
			return e;
		} else {
			errorExit();
		}
		return null;
	}
	
	/**
	 * This method return the next token and sets it in the local
	 * variables currentToken and t
	 * @return the next token at the token stream provided by the scanner
	 */
	public Token nextToken() {
		return t = currentToken = scanner.nextToken();
	}

	/**
	 * Prints a parser error and exits with return code 1
	 */
	private void errorExit() {
		System.out.println("Parse error!");
		System.exit(1);
	}
	
	/**
	 * tries to match the given token type with the token type of the current token. 
	 * If the current token type is not of the same type as the given one @errorExit is
	 * called
	 * @param t the token type to match
	 */
	private void match(TokenType t) {
		if (currentToken.tokenType == t) {
			currentToken = this.t = nextToken();
			return;
		} else {
			errorExit();
		}
	}
}
