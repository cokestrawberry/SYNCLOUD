package com.example.acdc.repository;

import com.example.acdc.domain.Song;
import com.example.acdc.domain.Soundtrack;
import com.example.acdc.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SoundtrackRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Soundtrack soundtrack) {
        em.persist(soundtrack);
    }

    public Soundtrack findOne(Long id) { return em.find(Soundtrack.class, id); }

    public List<Soundtrack> findBySong(Song song) {
        return em.createQuery("select T from Soundtrack as T where T.song.id = :songId order by T.uploadDate DESC ", Soundtrack.class)
                .setParameter("songId", song.getId())
                .getResultList();
    }

    public List<Soundtrack> findByUser(User user) {
        return em.createQuery("select T from Soundtrack as T where T.user.id = :userId order by T.uploadDate DESC ", Soundtrack.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

}
