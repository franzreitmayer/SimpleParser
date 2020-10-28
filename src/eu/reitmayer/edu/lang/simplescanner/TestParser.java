package eu.reitmayer.edu.lang.simplescanner;

public class TestParser {

	public static void main(String[] args) {

		Scanner scanner = new Scanner("(5+4*(1-11) /2) * (2*2+6)/2");
		Parser parser = new Parser(scanner);
		Integer i = parser.parse();
		System.out.println("Result is " + i);
	}

}
