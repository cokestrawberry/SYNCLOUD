package com.example.acdc.repository;

import com.example.acdc.AcdcApplication;
import com.example.acdc.domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = AcdcApplication.class)
class SongRepositoryTest {

    @Autowired
    SongRepository songRepository;

    @Test
    public void test_save_and_findOne() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);

        songRepository.save(song);
        Long savedId = song.getId();

        assertEquals(song, songRepository.findOne(savedId));
    }

    @Test
    public void test_findByTitle() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);

        Song song1 = new Song();
        song1.setTitle("Antifreeze");
        song1.setArtist("BaekYeRin");
        song1.setBpm(128);

        Song song2 = new Song();
        song2.setTitle("Kiss and tell");
        song2.setArtist("The Black Skirts");
        song2.setBpm(100);

        songRepository.save(song);
        songRepository.save(song1);
        songRepository.save(song2);

        String nameForSong = "Antifreeze";
        List<Song> findSongList = songRepository.findByTitle(nameForSong);

        assertEquals(song1, findSongList.get(0));
        assertEquals(song, findSongList.get(1));
    }

    @Test
    public void test_findByArtist() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);

        Song song1 = new Song();
        song1.setTitle("Antifreeze");
        song1.setArtist("BaekYeRin");
        song1.setBpm(128);

        Song song2 = new Song();
        song2.setTitle("Kiss and tell");
        song2.setArtist("The Black Skirts");
        song2.setBpm(100);

        songRepository.save(song);
        songRepository.save(song1);
        songRepository.save(song2);

        String nameForArtist = "The Black Skirts";
        List<Song> findSongList = songRepository.findByArtist(nameForArtist);

        assertEquals(song, findSongList.get(0));
        assertEquals(song2, findSongList.get(1));
    }

    @Test
    public void test_findByTitleAndArtist() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);

        Song song1 = new Song();
        song1.setTitle("Antifreeze");
        song1.setArtist("BaekYeRin");
        song1.setBpm(128);

        Song song2 = new Song();
        song2.setTitle("Kiss and tell");
        song2.setArtist("The Black Skirts");
        song2.setBpm(100);

        songRepository.save(song);
        songRepository.save(song1);
        songRepository.save(song2);

        String nameForSong = "Kiss and tell";
        String nameForArtist = "The Black Skirts";
        Song findSong = songRepository.findByTitleAndArtist(nameForSong, nameForArtist).get();

        assertEquals(song2, findSong);
    }
}