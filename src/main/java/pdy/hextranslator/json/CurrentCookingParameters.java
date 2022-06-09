package pdy.hextranslator.json;

import java.math.BigInteger;
import java.util.HashMap;

import pdy.hextranslator.exception.InvalidLengthException;
import pdy.hextranslator.exception.InvalidValueException;

public class CurrentCookingParameters extends HashMap<String,Object> {
	
	private static final long serialVersionUID = -2762239458847311146L;

	public enum Size { Small, Medium, Large }
	
	public enum CookMode { 
		AirFry(){
			@Override
			public String toString() {
				return "Air Fry";
			}
		},
		Bake, Broil, Roast, Reheat, Warm,
		SlowCook(){
			@Override
			public String toString() {
				return "Slow Cook";
			}
		},
		Dehydrate, Proof, Cookie, Pizza, Bagel, Toast,
		CrispFinish() {
			@Override
			public String toString() {
				return "Crisp Finish";
			}
		},
		Cake,
		CookieWithPreference(){
			@Override
			public String toString() {
				return "Cookie with Preference";
			}
		}, 
		PizzaWithPreference(){
			@Override
			public String toString() {
				return "Pizza with Preference";
			}
		};
	}
	
	public static final String KEY = "0000004";
	public static final int length = 24;
	
	public static final String SHADE = "Shade";
	public static final String SIZE = "Size";
	public static final String TEMPERATURE_F = "Temperature (F)";
	public static final String COOK_TIME_SECONDS = "Cook Time (Seconds)";
	public static final String COUNT = "Count";
	public static final String COOK_MODE = "Cook Mode";
	public static final String PREFERENCES = "Preferences";

	public CurrentCookingParameters(String jsonValue) {
		final var jsonValueLength = jsonValue.length();
		if (jsonValueLength != length)
			throw new InvalidLengthException(length, jsonValueLength);
		put(SHADE, new BigInteger(jsonValue.substring(0, 2), 16).toString());
		
		int size = new BigInteger(jsonValue.substring(2, 4), 16).intValue();
		Size[] sizes = Size.values();
		if(size >= sizes.length)
			throw new InvalidValueException(SIZE, size);
		put(SIZE, sizes[size].toString());
		
		put(TEMPERATURE_F, new BigInteger(jsonValue.substring(4, 8), 16).toString());
		put(COOK_TIME_SECONDS, new BigInteger(jsonValue.substring(8, 16), 16).toString());
		put(COUNT, new BigInteger(jsonValue.substring(16, 18), 16).toString());
		
		int cookMode = new BigInteger(jsonValue.substring(18, 20), 16).intValue();
		CookMode[] cookModes = CookMode.values();
		if(cookMode >= cookModes.length)
			throw new InvalidValueException(COOK_MODE, cookMode);
		put(COOK_MODE, cookModes[cookMode].toString());
		
		put(PREFERENCES, new BigInteger(jsonValue.substring(20, 22), 16).toString());
	}
}
