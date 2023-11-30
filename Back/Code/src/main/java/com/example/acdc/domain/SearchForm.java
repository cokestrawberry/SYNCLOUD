package com.example.acdc.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchForm {
    @NotEmpty(message = "target이 필요합니다.")
    public String searchTarget;

    public String searchOption;
}
