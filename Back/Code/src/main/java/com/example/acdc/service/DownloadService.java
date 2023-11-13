package com.example.acdc.service;

import com.example.acdc.domain.Download;
import com.example.acdc.domain.SelectSoundtrack;
import com.example.acdc.domain.User;
import com.example.acdc.repository.DownloadRepository;
import com.example.acdc.repository.SelectSoundtrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DownloadService {

    private final DownloadRepository downloadRepository;

    @Transactional
    public void save(Download download) {
        download.setLastModifyDate(LocalDateTime.now());
        downloadRepository.save(download);
    }

    public Download findOne(Long id) {
        return downloadRepository.findOne(id);
    }

    public List<Download> findAllByUser(User user) {
        return downloadRepository.findAllByUser(user);
    }

    public Download getClosedOne(User user) {
        return downloadRepository.findAllByUser(user).get(0);
    }

}
