package com.example.acdc.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SoundtrackForm {

    @NotEmpty(message = "노래 제목은 필수입니다.")
    private String title;

    @NotEmpty(message = "가수 이름은 필수입니다.")
    private String artist;

    private Integer bpm;

    @NotEmpty(message = "세션은 필수입니다.")
    private SessionState session;

    @NotEmpty(message = "업로드 할 파일은 필수입니다.")
    private String path;

    private String note;
}
