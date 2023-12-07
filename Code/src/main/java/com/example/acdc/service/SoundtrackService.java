package com.example.acdc.service;

import com.example.acdc.domain.Song;
import com.example.acdc.domain.Soundtrack;
import com.example.acdc.domain.User;
import com.example.acdc.repository.SongRepository;
import com.example.acdc.repository.SoundtrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundtrackService {

    private final SoundtrackRepository soundtrackRepository;

    @Transactional
    public Long save(Soundtrack soundtrack) {
        if (soundtrack.getBpm() == 0) {
            Integer bpm = soundtrack.getSong().getBpm();
            soundtrack.setBpm(bpm);
        }
        soundtrackRepository.save(soundtrack);
        return soundtrack.getId();
    }

    public Soundtrack findOne(Long id) {
        return soundtrackRepository.findOne(id);
    }

    public List<Soundtrack> findBySong(Song song) {
        return soundtrackRepository.findBySong(song);
    }

    public List<Soundtrack> findByUser(User user) {
        return soundtrackRepository.findByUser(user);
    }

    //업로드 된 적 없는 Soundtrack에 대한 조치
}
