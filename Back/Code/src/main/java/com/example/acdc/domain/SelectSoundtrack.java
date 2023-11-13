package com.example.acdc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@SQLDelete(sql = "UPDATE SET deleted = true WHERE SelectSoundtrack_id = ?")
@Where(clause = "deleted = false")
public class SelectSoundtrack {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selectsoundtrack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "download_id")
    private Download download;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soundtrack_id")
    private Soundtrack soundtrack;

    @Column
    private Boolean deleted;

    public static SelectSoundtrack createSelectSoundtrack(Soundtrack soundtrack) {
        SelectSoundtrack selectSoundtrack = new SelectSoundtrack();
        selectSoundtrack.setSoundtrack(soundtrack);
        selectSoundtrack.setDeleted(false);

        return selectSoundtrack;
    }

    public void delete() {
        this.deleted = true;
    }

}
