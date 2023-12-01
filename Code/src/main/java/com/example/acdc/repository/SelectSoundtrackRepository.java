package com.example.acdc.repository;

import com.example.acdc.domain.Download;
import com.example.acdc.domain.SelectSoundtrack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SelectSoundtrackRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(SelectSoundtrack selectSoundtrack) {
        em.persist(selectSoundtrack);
    }

    public SelectSoundtrack findOne(Long id) { return em.find(SelectSoundtrack.class, id); }

    public List<SelectSoundtrack> findByDownload(Download download) { //UserId로??? hmm...
        return em.createQuery("select S from SelectSoundtrack as S where S.download.id = :downloadId", SelectSoundtrack.class)
                .setParameter("downloadId", download.getId())
                .getResultList();
    }

}
