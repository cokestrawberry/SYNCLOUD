package com.example.acdc.repository;

import com.example.acdc.domain.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class SongRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Song song) {
        em.persist(song);
    }

    public Song findOne(Long id) { return em.find(Song.class, id); }

    public List<Song> findByTitle(String title) {
        return em.createQuery("Select S from Song as S where S.title = :title order by S.artist ASC ", Song.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Song> findByArtist(String artist) {
        return em.createQuery("select S from Song as S where S.artist = :artist order by S.title ASC", Song.class)
                .setParameter("artist", artist)
                .getResultList();
    }

    public Optional<Song> findByTitleAndArtist(String title, String artist) {
        List<Song> songs = em.createQuery("select S from Song as S where S.title = :title and S.artist = :artist", Song.class)
                .setParameter("title", title)
                .setParameter("artist", artist)
                .getResultList();

        return songs.stream().findAny();
    }

}
