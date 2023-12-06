package com.example.acdc.service;

import com.example.acdc.domain.Download;
import com.example.acdc.domain.User;
import com.example.acdc.repository.DownloadRepository;
import com.example.acdc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final DownloadRepository downloadRepository;

    @Transactional
    public Long save(User user) {
        userRepository.save(user);

        return user.getId();
        //나중에 id, password 이런거로 회원가입/로그인 구현하면... 중복회원확인 과정 추가할 필요가 있다.
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findOne(Long id) { return userRepository.findOne(id); }

}
