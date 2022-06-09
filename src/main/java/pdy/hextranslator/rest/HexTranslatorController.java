package pdy.hextranslator.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pdy.hextranslator.exception.InvalidKeyException;
import pdy.hextranslator.exception.InvalidLengthException;
import pdy.hextranslator.exception.InvalidValueException;
import pdy.hextranslator.json.HexTranslatorRequest;
import pdy.hextranslator.service.HexTranslatorService;

@RestController
public class HexTranslatorController {
	
	@Autowired
	private HexTranslatorService hexTranslatorService;
	
	@PostMapping(
		path = "/parse",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)

	public ResponseEntity<Map<String, Object>> parse(@Valid @RequestBody HexTranslatorRequest request) {
		return ResponseEntity.ok(hexTranslatorService.parse(request));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({InvalidKeyException.class, InvalidValueException.class, InvalidLengthException.class})
	public Map<String, String> handleValidationExceptions(Exception ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", ex.getLocalizedMessage());
		return errors;
	}
}
