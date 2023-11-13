package com.example.acdc.service;

import com.example.acdc.domain.Song;
import com.example.acdc.repository.SongRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;

    @Transactional
    public Long save(Song song) {
        validateDuplicateSong(song);

        songRepository.save(song);
        return song.getId();
    }

    public Song findOne(Long id) {
        return songRepository.findOne(id);
    }

    public List<Song> findByTitle(String title) {
        return songRepository.findByTitle(title);
    }

    public List<Song> findByArtist(String artist) {
        return songRepository.findByArtist(artist);
    }

    public Song findByTitleAndArtist(String title, String artist) {
        Song findsong = songRepository.findByTitleAndArtist(title, artist).orElseThrow(() -> new NoSuchElementException("Song not found"));
        return findsong;
    }

    private void validateDuplicateSong(Song song) {
        Optional<Song> findSong = songRepository.findByTitleAndArtist(song.getTitle(), song.getArtist());
        if (findSong.isPresent()) {
            throw new IllegalStateException("이미 존재하는 곡입니다.");
        }
    }
}
