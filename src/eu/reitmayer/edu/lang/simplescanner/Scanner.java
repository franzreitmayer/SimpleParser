package eu.reitmayer.edu.lang.simplescanner;

import eu.reitmayer.edu.lang.simplescanner.Token.TokenType;

public class Scanner {

	/**
	 * end of file marker
	 */
	private final int EOF = -1;

	/**
	 * current character
	 */
	private char currentChar;

	/**
	 * position in buffer
	 */
	private int pos = 0;

	/**
	 * position in buffer as started with evaluation of nextToken
	 */
	private int startPos = 0;

	/**
	 * current state of automaton
	 */
	private int state = 0;

	/**
	 * state of automaton as started to evaluate current diagram
	 */
	private int startState = 0;

	/**
	 * input buffer
	 */
	private String buffer;

	public Scanner(String _buffer) {
		buffer = _buffer;
	}

	public Token nextToken() {
		startPos = pos;
		state = 0;
		startState = 0;
		int c;
		while (true) {
			switch (state) {
			case 0:
				c = nextChar();
				if (Character.isWhitespace((char) c)) {
					state = 1;
					break;
				} else {
					state = nextDiagram();
					break;
				}
			case 1:
				return nextToken();
			case 2:
				c = nextChar();
				if ((char) c == '(') {
					state = 3;
					break;
				} else {
					state = nextDiagram();
					break;
				}
			case 3:
				return new Token(Token.TokenType.OPEN, "(");
			case 4:
				c = nextChar();
				if ((char) c == ')') {
					state = 5;
					break;
				} else {
					state = nextDiagram();
					break;
				}
			case 5:
				return new Token(TokenType.CLOSE, ")");
			case 6:
				c = nextChar();
				if ((char) c == '+') {
					state = 7;
					break;
				} else {
					state = nextDiagram();
					break;
				}
			case 7:
				return new Token(TokenType.PLUS, "+");
			case 8:
				c = nextChar();
				if ((char) c == '*') {
					state = 9;
					break;
				} else {
					state = nextDiagram();
					break;
				}
			case 9:
				return new Token(TokenType.TIMES, "*");
			case 10:
				c = nextChar();
				if (Character.isDigit((char) c) && (char) c != '0') {
					state = 11;
					break;
				} else {
					state = nextDiagram();
					break;
				}
			case 11:
				c = nextChar();
				if (Character.isDigit((char) c)) {
					state = 11;
					break;
				} else {
					stepback();
					state = 12;
					break;
				}
			case 12:
				return new Token(TokenType.INT, buffer.substring(startPos, pos));
			case 13:
				c = nextChar();
				if (c == EOF) {
					return new Token(TokenType.EOF, "<EOF>");
				} else {
					state = nextDiagram();
					break;
				}
			case 14:
				c = nextChar();
				if (c == '-') {
					return new Token(TokenType.MINUS, "-");
				} else {
					state = nextDiagram();
					break;
				}
			case 15:
				c = nextChar();
				if (c == '/') {
					return new Token(TokenType.DIVIDE, "/");
				} else {
					state = nextDiagram();
					break;
				}
			default:
				System.out.print("No viable alt");
				System.exit(1);
			}
		}
	}

	private int nextDiagram() {
		pos = startPos;
		if (state == 0)
			startState = 2;
		else if (state == 2)
			startState = 4;
		else if (state == 4)
			startState = 6;
		else if (state == 6)
			startState = 8;
		else if (state == 8)
			startState = 10;
		else if (state == 10)
			startState = 13;
		else if (state == 13) startState = 14;
		else if (state == 14) startState = 15;
		else {
			System.out.println("No viable alt at input character "
					+ (char) currentChar + " at pos " + pos);
			System.exit(1);
		}
		return startState;
	}

	private void stepback() {
		pos--;
		// return currentChar = buffer.charAt(pos);
	}

	private int nextChar() {
		if (pos > buffer.length() - 1) {
			pos++;
			return EOF;
		}
		currentChar = buffer.charAt(pos);
		pos++;
		return currentChar;
	}

}
