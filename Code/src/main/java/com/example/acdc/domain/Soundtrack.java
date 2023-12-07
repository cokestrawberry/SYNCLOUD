package com.example.acdc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Soundtrack {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soundtrack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @Enumerated(EnumType.STRING)
    private SessionState session;

    @Column
    private String path;

    @Column
    private Integer likes;

    @Column
    private Integer bpm;

    @Column
    private String note;

    @Column
    private LocalDateTime uploadDate;

    public static Soundtrack createSoundtrack(User user, Song song, SessionState session, Integer bpm, String note, String path) {
        Soundtrack soundtrack = new Soundtrack();

        soundtrack.setSong(song);
        soundtrack.setSession(session);
        soundtrack.setUploadDate(LocalDateTime.now());
        soundtrack.setPath(path);
        soundtrack.setNote(note);
        soundtrack.setLikes(0);
        soundtrack.setBpm(bpm);

        soundtrack.addUser(user);

        return soundtrack;
    }

    public void addUser(User user) {
        this.user = user;
        user.getSoundtrackList().add(this);
    }
}