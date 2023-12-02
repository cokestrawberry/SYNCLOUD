package com.example.acdc.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchForm {
    @NotEmpty(message = "target이 필요합니다.")
    public String searchTarget;

    public List<String> searchOption = new ArrayList<>();
}
