package com.example.acdc.repository;

import com.example.acdc.domain.SelectSoundtrack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@SQLDelete(sql = "UPDATE SET deleted = true WHERE SelectSoundtrack_id = ?")
@Where(clause = "deleted = false")
public class SelectSoundtrackRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(SelectSoundtrack selectSoundtrack) {
        em.persist(selectSoundtrack);
    }

    public SelectSoundtrack findOne(Long id) { return em.find(SelectSoundtrack.class, id); }

    public List<SelectSoundtrack> findByDownloadId(Long downloadId) { //UserId로??? hmm...
        return em.createQuery("select S from SelectSoundtrack as S where S.download.id = :downloadId and S.deleted = false", SelectSoundtrack.class)
                .setParameter("downloadId", downloadId)
                .getResultList();
    }
}
