package net.skhu.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BusEdit {
    int id;

    @NotEmpty @NotBlank
    @Size(min=1, max=12)
    String busNo;

    @NotEmpty @NotBlank
    @Size(min=1, max=20)
    String firstStop;

    @NotEmpty @NotBlank
    @Size(min=1, max=20)
    String lastStop;

    @NotEmpty @NotBlank
    @Size(min=1, max=20)
    String firstBus;

    @NotEmpty @NotBlank
    @Size(min=1, max=20)
    String lastBus;
    
    @Min(value=1, message="버스 종류를 선택하세요.")
    int categoryId;
}

