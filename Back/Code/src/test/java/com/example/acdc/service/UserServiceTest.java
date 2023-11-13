package com.example.acdc.service;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = AcdcApplication.class)
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired DownloadService downloadService;

    @Test
    public void test_save_and_findOne() {
        Download download = new Download();
        User user = User.createUser("Testor");
        user.addDownload(download);
        downloadService.save(download);

        Long savedId = userService.save(user);

        assertEquals(user, userService.findOne(savedId));
    }

    @Test
    public void test_findByName() {
        Download download = new Download();
        downloadService.save(download);
        User user = User.createUser("Name");
        user.addDownload(download);
        userService.save(user);

        Download download1 = new Download();
        downloadService.save(download1);
        User user1 = User.createUser("Name1");
        user.addDownload(download1);
        userService.save(user1);

        Download download2 = new Download();
        downloadService.save(download2);
        User user2 = User.createUser("Name");
        user.addDownload(download2);
        userService.save(user2);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        assertEquals(userList, userService.findByName("Name"));
    }

    @Test
    public void test_nullUser() {
        Long dummyId = (long) 1;
        assertEquals(null, userService.findOne(dummyId));
    }
}