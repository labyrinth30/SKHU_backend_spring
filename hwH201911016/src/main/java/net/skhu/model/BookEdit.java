package net.skhu.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookEdit {
    int id;

    @NotEmpty @NotBlank
    @Size(min=2, max=12)
    String title;

    @NotEmpty @NotBlank
    @Size(min=2, max=20)
    String author;

    @NotEmpty @NotBlank
    @Size(min=2, max=20)
    String price;

    @NotEmpty @NotBlank
    @Size(min=2, max=20)
    String publisher;

    @Min(value=1, message="카테고리를 선택하세요.")
    int categoryId;
}

