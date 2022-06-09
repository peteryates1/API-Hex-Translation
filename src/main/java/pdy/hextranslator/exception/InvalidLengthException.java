package pdy.hextranslator.exception;

public class InvalidLengthException extends IllegalArgumentException {
	
	private static final long serialVersionUID = -8651716585629564268L;

	public InvalidLengthException(int expected, int actual) {
		super("Invalid Value - length invalid, expected: "+expected+", actual: "+actual);
	}
}
