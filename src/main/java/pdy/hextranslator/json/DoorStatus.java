package pdy.hextranslator.json;

import java.math.BigInteger;
import java.util.HashMap;

import pdy.hextranslator.exception.InvalidLengthException;
import pdy.hextranslator.exception.InvalidValueException;

public class DoorStatus extends HashMap<String,Object> {

	private static final long serialVersionUID = -1011933191300036101L;
	
	public enum DoorStatusEnum { Closed, Open; }

	public static final String KEY = "0000001";
	public static final int length = 2;
	
	public static final String DOOR_STATUS = "Door status";
	
	public DoorStatus(String jsonValue) {
		if (jsonValue==null || jsonValue.length() != length)
			throw new InvalidLengthException(length, jsonValue.length());
		
		int doorStatus = new BigInteger(jsonValue, 16).intValue();
		DoorStatusEnum[] doorStatuses = DoorStatusEnum.values();
		if(doorStatus >= doorStatuses.length)
			throw new InvalidValueException(DOOR_STATUS, doorStatus);
		put(DOOR_STATUS, doorStatuses[doorStatus].toString());
	}
}
