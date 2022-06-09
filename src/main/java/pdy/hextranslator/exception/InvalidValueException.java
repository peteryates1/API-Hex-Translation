package pdy.hextranslator.exception;

public class InvalidValueException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 9203020316502983358L;

	public InvalidValueException(String name, int actual) {
		super(name + " invalid value: " + actual);
	}
}
