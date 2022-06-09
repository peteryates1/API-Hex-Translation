package pdy.hextranslator.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import pdy.hextranslator.json.DoorStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HexTranslatorServiceTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	void invalidKey_BadRequest() throws Exception {
		mvc.perform(
			post("/parse")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{ \"key\" : \"XXX\" , \"value\" : \"01\" }")
		)
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		;
	}

	@Test
	void missingValue_BadRequest() throws Exception {
		mvc.perform(
			post("/parse")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{ \"key\" : \"" + DoorStatus.KEY + "\" }")
		)
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		;
	}

	@Test
	void missingKey_BadRequest() throws Exception {
		mvc.perform(
			post("/parse")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{ \"value\" : \"01\" }")
		)
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
		;
	}
}
