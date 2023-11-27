package net.skhu.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentEdit {
    int id;

    @NotEmpty @NotBlank
    @Size(min=8, max=12)
    String studentNo;

    @NotEmpty @NotBlank
    @Size(min=2, max=20)
    String name;

    @NotEmpty @NotBlank
    @Pattern(regexp="010-[0-9]{3,4}-[0-9]{4}")
    String phone;

    @NotEmpty @Email
    String email;

    @NotEmpty @NotBlank
    @Pattern(regexp="남|여")
    String sex;

    int departmentId;
}

