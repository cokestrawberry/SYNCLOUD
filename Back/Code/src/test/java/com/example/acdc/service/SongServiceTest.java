package com.example.acdc.service;

import com.example.acdc.AcdcApplication;
import com.example.acdc.domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = AcdcApplication.class)
class SongServiceTest {

    @Autowired SongService songService;

    @Test
    public void test_save_and_findOne() {
        Song song = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Antifreeze");
        Long savedId = songService.save(song);

        assertEquals(song, songService.findOne(savedId));
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void test_duplicateSong_error() {
        Song song = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Antifreeze");
        Long savedId = songService.save(song);

        Song song1 = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Antifreeze");
        Long savedId1 = songService.save(song1);
    }

    @Test
    public void test_findByTitle() {
        Song song = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Antifreeze");
        Long savedId = songService.save(song);

        Song song1 = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Kiss and tell");
        Long savedId1 = songService.save(song1);

        List<Song> findSong = songService.findByTitle(song.getTitle());

        assertEquals(song, findSong.get(0));
    }

    @Test
    public void test_findByArtist() {
        Song song = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Antifreeze");
        Long savedId = songService.save(song);

        Song song1 = new Song();
        song1.setBpm(128);
        song1.setArtist("The Black Skirts");
        song1.setTitle("Kiss and tell");
        Long savedId1 = songService.save(song1);

        Song song2 = new Song();
        song2.setBpm(90);
        song2.setArtist("Fish");
        song2.setArtist("BaekYeRin");
        Long savedId2 = songService.save(song2);

        List<Song> findSong = songService.findByArtist(song.getArtist());

        List<Song> expectedSong = new ArrayList<>();
        expectedSong.add(song);
        expectedSong.add(song1);

        assertEquals(expectedSong, findSong);
    }

    @Test
    public void test_findByTitleAndArtist() {
        Song song = new Song();
        song.setBpm(128);
        song.setArtist("The Black Skirts");
        song.setTitle("Antifreeze");
        Long savedId = songService.save(song);

        Song song1 = new Song();
        song1.setBpm(128);
        song1.setArtist("The Black Skirts");
        song1.setTitle("Kiss and tell");
        songService.save(song1);

        Song song2 = new Song();
        song2.setBpm(90);
        song2.setArtist("Fish");
        song2.setArtist("BaekYeRin");
        songService.save(song2);

        Song findSong = songService.findByTitleAndArtist(song1.getTitle() ,song1.getArtist());

        assertEquals(song1, findSong);
    }
}