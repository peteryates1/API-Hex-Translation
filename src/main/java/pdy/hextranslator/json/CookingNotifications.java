package pdy.hextranslator.json;

import java.math.BigInteger;
import java.util.HashMap;

import pdy.hextranslator.exception.InvalidLengthException;

public class CookingNotifications extends HashMap<String,Object> {
	private static final long serialVersionUID = 1834623827722453113L;

	public static final String KEY = "0000002";
	public static final int length = 2;
	
	public static final String TARGET_TEMPERATURE_ACHIEVED = "Target Temperature Achieved";
	public static final String COOKING_STARTED = "Cooking Started";
	public static final String COOKTIME_ONE_MINUTE_REMAINING = "Cooktime One Minute Remaining";
	public static final String COOKING_COMPLETE = "Cooking Complete";
	
	public CookingNotifications(String jsonValue) {
		final var jsonValueLength = jsonValue.length();
		if (jsonValueLength != length)
			throw new InvalidLengthException(length, jsonValueLength);
		
		BigInteger value = new BigInteger(jsonValue, 16);
		put(TARGET_TEMPERATURE_ACHIEVED, value.testBit(0));
		put(COOKING_STARTED, value.testBit(1));
		put(COOKTIME_ONE_MINUTE_REMAINING, value.testBit(2));
		put(COOKING_COMPLETE, value.testBit(3));
	}
}
