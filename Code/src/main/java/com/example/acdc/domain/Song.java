package com.example.acdc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Song {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private int bpm;

    public static Song createSong(String title, String artist, int bpm) {
        Song song = new Song();

        song.setTitle(title);
        song.setArtist(artist);
        song.setBpm(bpm);

        return song;
    }
}