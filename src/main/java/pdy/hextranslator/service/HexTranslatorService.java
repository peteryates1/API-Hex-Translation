package pdy.hextranslator.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pdy.hextranslator.exception.InvalidKeyException;
import pdy.hextranslator.json.CookingNotifications;
import pdy.hextranslator.json.CurrentCookingParameters;
import pdy.hextranslator.json.DoorStatus;
import pdy.hextranslator.json.HexTranslatorRequest;

@Service
@Slf4j
public class HexTranslatorService {
	
	private static Map<String, Function<String, Map<String, Object>>> converters = new HashMap<>();
	
	static {
		converters.put(DoorStatus.KEY, DoorStatus::new);
		converters.put(CookingNotifications.KEY, CookingNotifications::new);
		converters.put(CurrentCookingParameters.KEY, CurrentCookingParameters::new);
	}
	
	public Map<String, Object> parse(HexTranslatorRequest request) {
		log.info("request: "+ request);
		return converters
			.computeIfAbsent(request.getKey(), k -> {
				throw new InvalidKeyException(k);
			})
			.apply(request.getValue());
	}
}
