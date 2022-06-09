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
import lombok.extern.slf4j.Slf4j;
import pdy.hextranslator.json.DoorStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=GEAHexTranslationApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
class DoorStatusTest extends AbstractParseRequestTest {

	@Autowired
	@Getter
	private TestRestTemplate template;
	
	@Getter
	private final String key = DoorStatus.KEY;
	
	@Test
	void doorStatusTest_Open() {
		final String value = String.format("%02X", DoorStatus.DoorStatusEnum.Open.ordinal());
		final var response = testPostOk(value);
		log.info(response.toString());
		assertEquals(DoorStatus.DoorStatusEnum.Open.toString(), response.get(DoorStatus.DOOR_STATUS));
	}

	@Test
	void doorStatusTest_Closed() {
		final String value = String.format("%02X", DoorStatus.DoorStatusEnum.Closed.ordinal());
		final var response = testPostOk(value);
		log.info(response.toString());
		assertEquals(DoorStatus.DoorStatusEnum.Closed.toString(), response.get(DoorStatus.DOOR_STATUS));
	}

	@Test
	void doorStatusTest_InvalidValue() {
		final String value = String.format("%02X", DoorStatus.DoorStatusEnum.values().length);
		testPost(HttpStatus.BAD_REQUEST, value);
	}

	@Test
	void doorStatusTest_InvalidLength() {
		testPost(HttpStatus.BAD_REQUEST, "0");
	}
}
