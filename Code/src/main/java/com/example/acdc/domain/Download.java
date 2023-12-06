package com.example.acdc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Download {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "download_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "download")
    private List<SelectSoundtrack> selectSoundtracks = new ArrayList<>();

    @Column
    private LocalDateTime lastModifyDate;

    public void addSoundtrack(SelectSoundtrack selectSoundtrack) {
        selectSoundtracks.add(selectSoundtrack);
        selectSoundtrack.setDownload(this);
    }

    public void updateModifyDate() {
        this.lastModifyDate = LocalDateTime.now();
    }

}
