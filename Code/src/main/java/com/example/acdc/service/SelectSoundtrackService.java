package com.example.acdc.service;

import com.example.acdc.domain.Download;
import com.example.acdc.domain.SelectSoundtrack;
import com.example.acdc.repository.SelectSoundtrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectSoundtrackService {

    private final SelectSoundtrackRepository selectSoundtrackRepository;
    private final DownloadService downloadService;

    @Transactional
    public Long save(SelectSoundtrack selectSoundtrack) {
        selectSoundtrackRepository.save(selectSoundtrack);
        return selectSoundtrack.getId();
    }

    public SelectSoundtrack findOne(Long id) {
        return selectSoundtrackRepository.findOne(id);
    }

    public List<SelectSoundtrack> findByDownload(Download download) {
        return selectSoundtrackRepository.findByDownload(download);
    }

    @Transactional
    public void delete(SelectSoundtrack selectSoundtrack) {
        selectSoundtrack.delete();
        selectSoundtrackRepository.save(selectSoundtrack);
    }
    //Delete할 때 Download에서 어떤 작업을 할지, 아니면 아무런 작업도 안하고 Soft delete만 구현할지 정하기

}
