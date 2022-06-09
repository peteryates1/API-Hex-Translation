package pdy.hextranslator.exception;

public class InvalidKeyException extends IllegalArgumentException {
	private static final long serialVersionUID = 4140652821100169676L;

	public InvalidKeyException(String key) {
		super("Invalid key: " + key);
	}
}
