package net.skhu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DocumentEdit {
	int id;
	
	@NotEmpty
	@NotBlank
	String fileName;
	
	@Min(value = 1, message = "크기를 입력하세요.")
	int size;

	
	@NotEmpty
	@NotBlank
	@Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "날짜를 입력하세요. 예:2013-10-18")
	String modifiedDate;
	
	@NotEmpty
	@NotBlank
	String state;
	
	@Min(value = 1, message = "폴더를 선택하세요.")
	int folderId;
}