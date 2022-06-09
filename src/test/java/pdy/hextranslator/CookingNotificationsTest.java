package pdy.hextranslator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.Getter;
import pdy.hextranslator.json.CookingNotifications;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=GEAHexTranslationApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class CookingNotificationsTest extends AbstractParseRequestTest {
	
	@Autowired
	@Getter
	private TestRestTemplate template;
	
	@Getter
	private final String key = CookingNotifications.KEY;
	
	@Test
	void cookingNotifications_TARGET_TEMPERATURE_ACHIEVED() {
		final String value = "01";
		final var response = testPostOk(value);
		assertEquals(Boolean.TRUE, response.get(CookingNotifications.TARGET_TEMPERATURE_ACHIEVED));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKING_STARTED));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKTIME_ONE_MINUTE_REMAINING));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKING_COMPLETE));
	}
	
	@Test
	void cookingNotifications_COOKING_STARTED() {
		final String value = "02";
		final var response = testPostOk(value);
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.TARGET_TEMPERATURE_ACHIEVED));
		assertEquals(Boolean.TRUE, response.get(CookingNotifications.COOKING_STARTED));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKTIME_ONE_MINUTE_REMAINING));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKING_COMPLETE));
	}
	
	@Test
	void cookingNotifications_COOKTIME_ONE_MINUTE_REMAINING() {
		final String value = "04";
		final var response = testPostOk(value);
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.TARGET_TEMPERATURE_ACHIEVED));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKING_STARTED));
		assertEquals(Boolean.TRUE, response.get(CookingNotifications.COOKTIME_ONE_MINUTE_REMAINING));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKING_COMPLETE));
	}
	
	@Test
	void cookingNotifications_COOKING_COMPLETE() {
		final String value = "08";
		final var response = testPostOk(value);
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.TARGET_TEMPERATURE_ACHIEVED));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKING_STARTED));
		assertEquals(Boolean.FALSE, response.get(CookingNotifications.COOKTIME_ONE_MINUTE_REMAINING));
		assertEquals(Boolean.TRUE, response.get(CookingNotifications.COOKING_COMPLETE));
	}
	
	@Test
	void cookingNotifications_InvalidValue() {
		testPost(HttpStatus.BAD_REQUEST, "XX");
	}
	
	@Test
	void cookingNotifications_InvalidLength() {
		final String value = "0";
		testPost(HttpStatus.BAD_REQUEST, value);
	}
}
