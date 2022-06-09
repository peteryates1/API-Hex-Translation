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
import pdy.hextranslator.json.CurrentCookingParameters;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=GEAHexTranslationApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
class CurrentCookingParametersTest extends AbstractParseRequestTest {

	@Autowired
	@Getter
	private TestRestTemplate template;
	
	@Getter
	private final String key = CurrentCookingParameters.KEY;
	
	@Test
	void testCurrentCookingParameters_Example() {
		testPostOk(0, CurrentCookingParameters.Size.Small, 400, 1200, 0, CurrentCookingParameters.CookMode.AirFry, 0);
	}
	
	@Test
	void currentCookingParameters_Maximums() {
		final var response = testPostOk("FF02FFFFFFFFFFFFFF10FFFF");
		assertEquals(Integer.toString((1<<8)-1), response.get(CurrentCookingParameters.SHADE));
		final var sizes = CurrentCookingParameters.Size.values();
		assertEquals(sizes[sizes.length-1].toString(), response.get(CurrentCookingParameters.SIZE));
		assertEquals(Integer.toString((1<<16)-1), response.get(CurrentCookingParameters.TEMPERATURE_F));
		assertEquals(Long.toString((1L<<32)-1), response.get(CurrentCookingParameters.COOK_TIME_SECONDS));
		assertEquals(Integer.toString((1<<8)-1), response.get(CurrentCookingParameters.COUNT));
		final var cookModes = CurrentCookingParameters.CookMode.values();
		assertEquals(cookModes[cookModes.length-1].toString(), response.get(CurrentCookingParameters.COOK_MODE));
		assertEquals(Integer.toString((1<<8)-1), response.get(CurrentCookingParameters.PREFERENCES));
	}
	
	@Test
	void currentCookingParameters_Zeros() {
		final var response = testPostOk("000000000000000000000000");
		assertEquals(Integer.toString(0), response.get(CurrentCookingParameters.SHADE));
		assertEquals(CurrentCookingParameters.Size.values()[0].toString(), response.get(CurrentCookingParameters.SIZE));
		assertEquals(Integer.toString(0), response.get(CurrentCookingParameters.TEMPERATURE_F));
		assertEquals(Long.toString(0L), response.get(CurrentCookingParameters.COOK_TIME_SECONDS));
		assertEquals(Integer.toString(0), response.get(CurrentCookingParameters.COUNT));
		assertEquals(CurrentCookingParameters.CookMode.values()[0].toString(), response.get(CurrentCookingParameters.COOK_MODE));
		assertEquals(Integer.toString(0), response.get(CurrentCookingParameters.PREFERENCES));
	}
	
	@Test
	void currentCookingParameters_BadSize() {
		final var sizes = CurrentCookingParameters.Size.values();
		testPost(HttpStatus.BAD_REQUEST, "00"+String.format("%02X", sizes.length)+"00000000000000000000");
	}
	
	@Test
	void currentCookingParameters_BadCookMode() {
		final var cookModes = CurrentCookingParameters.CookMode.values();
		testPost(HttpStatus.BAD_REQUEST, "000000000000000000"+String.format("%02X", cookModes.length)+"0000");
	}
	
	@Test
	void currentCookingParameters_InvalidLength() {
		testPost(HttpStatus.BAD_REQUEST, "0");
	}
	
	private String buildRequest(
		final int shade,
		final CurrentCookingParameters.Size size,
		final int temp,
		final long time,
		final int count,
		final CurrentCookingParameters.CookMode mode,
		final int pref
	) {
		return String.format("%02X", shade)
				+	String.format("%02X", size.ordinal())
				+	String.format("%04X", temp)
				+	String.format("%08X", time)
				+	String.format("%02X", count)
				+	String.format("%02X", mode.ordinal())
				+	String.format("%02X", pref)
				+	"00";
	}
	
	private void testPostOk(
		final int shade,
		final CurrentCookingParameters.Size size,
		final int temp,
		final long time,
		final int count,
		final CurrentCookingParameters.CookMode mode,
		final int pref
	) {
		final var response = super.testPostOk(buildRequest(shade, size, temp, time, count, mode, pref));
		log.info(response.toString());
		assertEquals(Integer.toString(shade), response.get(CurrentCookingParameters.SHADE));
		assertEquals(size.toString(), response.get(CurrentCookingParameters.SIZE));
		assertEquals(Integer.toString(temp), response.get(CurrentCookingParameters.TEMPERATURE_F));
		assertEquals(Long.toString(time), response.get(CurrentCookingParameters.COOK_TIME_SECONDS));
		assertEquals(Integer.toString(count), response.get(CurrentCookingParameters.COUNT));
		assertEquals(mode.toString(), response.get(CurrentCookingParameters.COOK_MODE));
		assertEquals(Integer.toString(pref), response.get(CurrentCookingParameters.PREFERENCES));
	}
	
}
