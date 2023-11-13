package com.example.acdc.service;

import com.example.acdc.AcdcApplication;
import com.example.acdc.domain.SessionState;
import com.example.acdc.domain.Song;
import com.example.acdc.domain.Soundtrack;
import com.example.acdc.domain.User;
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
class SoundtrackServiceTest {

    @Autowired SoundtrackService soundtrackService;
    @Autowired SongService songService;
    @Autowired UserService userService;

    @Test
    public void test_save_and_findOne() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songService.save(song);

        User user = new User();
        user.setName("BaeHyeonWoo");
        userService.save(user);

        String note = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.GUITAR, 150, note, "https://test.com");
        Long savedId = soundtrackService.save(soundtrack);

        assertEquals(soundtrack, soundtrackService.findOne(savedId));
    }

    @Test
    public void test_defaultBpm() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songService.save(song);

        User user = new User();
        user.setName("BaeHyeonWoo");
        userService.save(user);

        String note = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.GUITAR, 0, note, "https://test.com");
        Long savedId = soundtrackService.save(soundtrack);

        assertEquals(song.getBpm(), soundtrackService.findOne(savedId).getBpm());
    }

    @Test
    public void test_findBySong() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songService.save(song);

        Song song1 = new Song();
        song.setTitle("Kiss and tell");
        song.setArtist("The Black Skirts");
        song.setBpm(100);
        songService.save(song1);

        User user = new User();
        user.setName("BaeHyeonWoo");
        userService.save(user);

        User user1 = new User();
        user.setName("Leejieun");
        userService.save(user1);

        String note = "live ver cover";

        String note1 = "live ver cover";

        String note2 = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user1, song, SessionState.BASS, 0,note, "https://test.com");
        Soundtrack soundtrack1 = Soundtrack.createSoundtrack(user1, song1, SessionState.BASS, 0,note1, "https://test2.com");
        Soundtrack soundtrack2 = Soundtrack.createSoundtrack(user, song1, SessionState.GUITAR, 0,note2, "https://test3.com");

        soundtrackService.save(soundtrack);
        soundtrackService.save(soundtrack1);
        soundtrackService.save(soundtrack2);

        List<Soundtrack> expSoundtracks = new ArrayList<>();
        expSoundtracks.add(soundtrack2);
        expSoundtracks.add(soundtrack1);

        assertEquals(expSoundtracks, soundtrackService.findBySong(song1));
    }

    @Test
    public void test_findByUser() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songService.save(song);

        Song song1 = new Song();
        song.setTitle("Kiss and tell");
        song.setArtist("The Black Skirts");
        song.setBpm(100);
        songService.save(song1);

        User user = new User();
        user.setName("BaeHyeonWoo");
        userService.save(user);

        User user1 = new User();
        user.setName("Leejieun");
        userService.save(user1);

        String note = "live ver cover";

        String note1 = "live ver cover";

        String note2 = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user1, song, SessionState.BASS, 0,note, "https://test.com");
        Soundtrack soundtrack1 = Soundtrack.createSoundtrack(user1, song1, SessionState.BASS, 0,note1, "https://test2.com");
        Soundtrack soundtrack2 = Soundtrack.createSoundtrack(user, song1, SessionState.GUITAR, 0,note2, "https://test3.com");

        soundtrackService.save(soundtrack);
        soundtrackService.save(soundtrack1);
        soundtrackService.save(soundtrack2);

        List<Soundtrack> expSoundtrack = new ArrayList<>();
        expSoundtrack.add(soundtrack1);
        expSoundtrack.add(soundtrack);

        assertEquals(expSoundtrack, soundtrackService.findByUser(user1));

    }

}