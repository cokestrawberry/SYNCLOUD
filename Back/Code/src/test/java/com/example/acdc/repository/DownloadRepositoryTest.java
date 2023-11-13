package com.example.acdc.repository;

import com.example.acdc.AcdcApplication;
import com.example.acdc.domain.Download;
import com.example.acdc.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = AcdcApplication.class)
class DownloadRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired DownloadRepository downloadRepository;

    @Test
    public void test_save_and_findOne() {
        User user = User.createUser("Tester");
        Download download = new Download();
        user.addDownload(download);

        downloadRepository.save(download);
        userRepository.save(user);

        Long savedId = download.getId();

        assertEquals(download, downloadRepository.findOne(savedId));
    }

    @Test
    public void test_findAllByUser() throws InterruptedException {
        User user = User.createUser("Tester");

        Download download = new Download();
        download.setLastModifyDate(LocalDateTime.now());
        downloadRepository.save(download);
        user.addDownload(download);
        userRepository.save(user);

        Thread.sleep(100);

        Download download1 = new Download();
        download1.setLastModifyDate(LocalDateTime.now());
        downloadRepository.save(download1);
        user.addDownload(download1);
        userRepository.save(user);

        // [download, download1] , [download1, download]
        assertEquals(user.getDownloadList().get(1), downloadRepository.findAllByUser(user).get(0));
        assertEquals(user.getDownloadList().get(0), downloadRepository.findAllByUser(user).get(1));
    }

}