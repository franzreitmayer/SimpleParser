package eu.reitmayer.edu.lang.simplescanner;

import eu.reitmayer.edu.lang.simplescanner.Token.TokenType;

public class TestScanner {

	public static void main(String[] args) {

		Scanner scanner = new Scanner("1332 + 2 * (123 + 14 * (4) - 43) / 3");
		Token token;
		while((token = scanner.nextToken()).tokenType != TokenType.EOF) {
			System.out.println(token);
		}
	}

}
