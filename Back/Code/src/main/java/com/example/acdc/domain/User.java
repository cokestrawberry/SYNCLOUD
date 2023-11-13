package com.example.acdc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Soundtrack> soundtrackList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Download> downloadList = new ArrayList<>();

    public static User createUser(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    public void addDownload(Download download) {
        this.downloadList.add(download);
        download.setUser(this);
    }
}
