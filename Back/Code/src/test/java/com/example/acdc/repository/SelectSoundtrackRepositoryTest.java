package com.example.acdc.repository;

import com.example.acdc.AcdcApplication;
import com.example.acdc.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = AcdcApplication.class)
class SelectSoundtrackRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired DownloadRepository downloadRepository;
    @Autowired SelectSoundtrackRepository selectSoundtrackRepository;
    @Autowired SoundtrackRepository soundtrackRepository;
    @Autowired SongRepository songRepository;

    @Test
    public void save_and_findOne() {
        User user = new User();
        Download download = new Download();
        Song song = new Song();

        user.setName("HyeonWoo");
        user.addDownload(download);
        downloadRepository.save(download);
        userRepository.save(user);

        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songRepository.save(song);

        String note = "test";
        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.GUITAR, 0, note, "https://test.com");
        soundtrackRepository.save(soundtrack);

        Download targetDownload = downloadRepository.findAllByUser(user).get(0);

        SelectSoundtrack selectSoundtrack = SelectSoundtrack.createSelectSoundtrack(soundtrack);
        targetDownload.addSoundtrack(selectSoundtrack);
        downloadRepository.save(download);
        selectSoundtrackRepository.save(selectSoundtrack);

        Long savedId = selectSoundtrack.getId();

        assertEquals(selectSoundtrack, selectSoundtrackRepository.findOne(savedId));
    }

    @Test
    public void testFindByDownloadId() {
        User user = new User();
        Download download = new Download();
        Song song = new Song();

        user.setName("HyeonWoo");
        user.addDownload(download);
        downloadRepository.save(download);
        userRepository.save(user);

        song.setTitle("Antifreeze");
        song.setArtist("The Black Skirts");
        song.setBpm(128);
        songRepository.save(song);

        String note = "test";
        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.GUITAR, 0, note, "https://test.com");
        soundtrackRepository.save(soundtrack);

        Download targetDownload = downloadRepository.findAllByUser(user).get(0);
        SelectSoundtrack selectSoundtrack = SelectSoundtrack.createSelectSoundtrack(soundtrack);
        targetDownload.addSoundtrack(selectSoundtrack);
        downloadRepository.save(download);
        selectSoundtrackRepository.save(selectSoundtrack);

        Long downloadId = download.getId();
        assertEquals(selectSoundtrack, selectSoundtrackRepository.findByDownload(download).get(0));
    }

//    @Test
//    public void testDeleteById() {
//        User user = new User();
//        Download download = new Download();
//        Song song = new Song();
//
//        user.setName("HyeonWoo");
//        user.addDownload(download);
//        downloadRepository.save(download);
//        userRepository.save(user);
//
//        song.setTitle("Antifreeze");
//        song.setArtist("The Black Skirts");
//        song.setBpm(128);
//        songRepository.save(song);
//
//        String note = "test";
//        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, SessionState.GUITAR, 0, note, "https://test.com");
//        soundtrackRepository.save(soundtrack);
//
//        SelectSoundtrack selectSoundtrack = SelectSoundtrack.createSelectSoundtrack(download, soundtrack);
//        selectSoundtrackRepository.save(selectSoundtrack);
//
//        Long savedId = selectSoundtrack.getId();
//        assertEquals(selectSoundtrack, selectSoundtrackRepository.findOne(savedId));
//
//        selectSoundtrackRepository.deleteById(savedId);
//        assertEquals(true, selectSoundtrackRepository.findOne(savedId).getDeleted());
//    }

}