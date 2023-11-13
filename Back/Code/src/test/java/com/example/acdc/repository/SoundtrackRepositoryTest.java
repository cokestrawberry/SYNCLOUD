package com.example.acdc.repository;

import com.example.acdc.AcdcApplication;
import com.example.acdc.domain.SessionState;
import com.example.acdc.domain.Song;
import com.example.acdc.domain.User;
import com.example.acdc.domain.Soundtrack;
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
class SoundtrackRepositoryTest {
    @Autowired SoundtrackRepository soundtrackRepository;
    @Autowired SongRepository songRepository;
    @Autowired UserRepository userRepository;

    @Test
    public void test_save() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);

        User user = new User();
        user.setName("BaeHyeonWoo");

        String note = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.GUITAR, 0, note, "https://test.com");

        soundtrackRepository.save(soundtrack);
        Long savedId = soundtrack.getId();

        assertEquals(soundtrack, soundtrackRepository.findOne(savedId));
    }

    @Test
    public void test_findBySong() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songRepository.save(song);

        Song song1 = new Song();
        song.setTitle("Kiss and tell");
        song.setArtist("The Black Skirts");
        song.setBpm(100);
        songRepository.save(song1);

        User user = new User();
        user.setName("BaeHyeonWoo");
        userRepository.save(user);

        String note = "live ver cover";

        String note1 = "live ver cover";

        String note2 = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.BASS, 0,note, "https://test.com");
        Soundtrack soundtrack1 = Soundtrack.createSoundtrack(user, song1, SessionState.BASS, 0, note1, "https://test2.com");
        Soundtrack soundtrack2 = Soundtrack.createSoundtrack(user, song1, SessionState.GUITAR, 0, note2, "https://test3.com");

        soundtrackRepository.save(soundtrack);
        soundtrackRepository.save(soundtrack1);
        soundtrackRepository.save(soundtrack2);

        List<Soundtrack> selectedTrack = soundtrackRepository.findBySong(song1);

        assertEquals(soundtrack2, selectedTrack.get(0));
        assertEquals(soundtrack1, selectedTrack.get(1));
    }

    @Test
    public void test_findByUser() {
        Song song = new Song();
        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songRepository.save(song);

        Song song1 = new Song();
        song.setTitle("Kiss and tell");
        song.setArtist("The Black Skirts");
        song.setBpm(100);
        songRepository.save(song1);

        User user = new User();
        user.setName("BaeHyeonWoo");
        userRepository.save(user);

        User user1 = new User();
        user.setName("Leejieun");
        userRepository.save(user1);

        String note = "live ver cover";

        String note1 = "live ver cover";

        String note2 = "Acoustic guitar cover";

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user1, song, SessionState.BASS, 0,note, "https://test.com");
        Soundtrack soundtrack1 = Soundtrack.createSoundtrack(user1, song1, SessionState.BASS, 0,note, "https://test2.com");
        Soundtrack soundtrack2 = Soundtrack.createSoundtrack(user, song1, SessionState.GUITAR, 0,note, "https://test3.com");

        soundtrackRepository.save(soundtrack);
        soundtrackRepository.save(soundtrack1);
        soundtrackRepository.save(soundtrack2);

        List<Soundtrack> selectedTrack = soundtrackRepository.findByUser(user1);

        assertEquals(soundtrack1, selectedTrack.get(0));
        assertEquals(soundtrack, selectedTrack.get(1));
    }

}