package pdy.hextranslator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import pdy.hextranslator.json.HexTranslatorRequest;

@Slf4j
abstract class AbstractParseRequestTest {
	
	protected abstract TestRestTemplate getTemplate();
	protected abstract String getKey();
	
	protected static final String URL = "/parse";
	
	@SuppressWarnings("rawtypes")
	protected Map testPost(final HttpStatus httpStatus, final String value) {
		final var request = HexTranslatorRequest.builder()
		.key(getKey())
		.value(value)
		.build();
		ResponseEntity<Map> responseEntity = getTemplate().postForEntity(URL, request, Map.class);
		log.info(responseEntity.toString());
		assertEquals(httpStatus, responseEntity.getStatusCode());
		return responseEntity.getBody();
	}
	
	@SuppressWarnings("rawtypes")
	protected Map testPostOk(final String value) {
		return testPost(HttpStatus.OK, value);
	}
}
