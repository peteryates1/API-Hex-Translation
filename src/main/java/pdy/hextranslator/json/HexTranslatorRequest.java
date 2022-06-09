package pdy.hextranslator.json;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HexTranslatorRequest {
	@NotBlank(message = "Key is mandatory")
	@Pattern(regexp = "^([a-fA-F0-9])+$")
	private String key;
	
	@NotBlank(message = "Value is mandatory")
	@Pattern(regexp = "^([a-fA-F0-9])+$")
	private String value;
}
