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
    private final SelectSoundtrackRepository selectSoundtrackRepository;

    @Transactional
    public void save(Download download) {
        download.updateModifyDate();
        downloadRepository.save(download);
    }

    public Download findOne(Long id) {
        return downloadRepository.findOne(id);
    }

    public List<Download> findAllByUser(User user) {
        return downloadRepository.findAllByUser(user);
    }

    public Download getClosedOne(User user) {

        List<Download> downmoadList = downloadRepository.findAllByUser(user);
        if(downmoadList == null) {
            return null;
        } else {
            return downmoadList.get(0);
        }
    }

    @Transactional
    public int addSelectSoundtrack(Download download, SelectSoundtrack selectSoundtrack) {
        List<SelectSoundtrack> selectSoundtracks = selectSoundtrackRepository.findByDownload(download);
        for(int i = 0; i < selectSoundtracks.size(); i++) {
            if(selectSoundtracks.get(i).getSoundtrack().getId() == selectSoundtrack.getSoundtrack().getId()) { //ok
                if(!selectSoundtracks.get(i).getDeleted()) { //ok
                    return 3;
                }
                else if (selectSoundtracks.get(i).getDeleted()) { //x
                    selectSoundtracks.get(i).delete();
                    selectSoundtrackRepository.save(selectSoundtracks.get(i));
                    downloadRepository.save(download);
                    return 2;
                }
            }
        }
        download.addSoundtrack(selectSoundtrack); //ok
        return 1;
    }

}
