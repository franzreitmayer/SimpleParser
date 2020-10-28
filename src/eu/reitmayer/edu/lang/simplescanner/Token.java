package eu.reitmayer.edu.lang.simplescanner;

/**
 * The class Token is a container for an actual token. It holds the 
 * type of the token and the string that belongs to token as well.
 * @author Franz Reitmayer
 *
 */
public class Token {

	/**
	 * Defines the possible token types
	 * @author franz
	 *
	 */
	public enum TokenType {OPEN, CLOSE, PLUS, TIMES, INT, EOF, MINUS, DIVIDE};
	
	/**
	 * The actual token type
	 */
	public TokenType tokenType;
	
	/**
	 * The string that belongs to the token
	 */
	public String stringUnderToken = "";
	
	/**
	 * 
	 * @param _tokenType the actual token type
	 * @param _stringUnderToken the string that belongs to the token
	 */
	public Token(TokenType _tokenType, String _stringUnderToken) {
		tokenType = _tokenType;
		stringUnderToken = _stringUnderToken;
	}

	/**
	 * @return a string by the following pattern "Tokentype: [name of token type] '[string of token]'"
	 */
	@Override
	public String toString() {
		return "Tokentype: " + tokenType + " '" + stringUnderToken + "'";
	}
	
	
}
