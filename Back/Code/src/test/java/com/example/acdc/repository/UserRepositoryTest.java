package com.example.acdc.repository;

import com.example.acdc.domain.Download;
import com.example.acdc.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired DownloadRepository downloadRepository;

    @Test
    public void test_save_and_findOne() throws Exception{

        User user = new User();
        Download download = new Download();

        user.setName("BaeHyeonWoo");
        user.addDownload(download);

        userRepository.save(user);
        Long savedId = user.getId();

        assertEquals(user, userRepository.findOne(savedId));
    }

    @Test
    public void test_find_all_by_name() throws Exception {
        User user1 = new User();
        Download download1 = new Download();
        user1.setName("name");
        user1.addDownload(download1);

        User user2 = new User();
        Download download2 = new Download();
        user2.setName("name");
        user2.addDownload(download2);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> findUserList = userRepository.findByName("name");

        assertEquals(user1, findUserList.get(0));
        assertEquals(user2, findUserList.get(1));
    }
}