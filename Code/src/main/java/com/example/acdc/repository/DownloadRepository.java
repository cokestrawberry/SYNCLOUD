package com.example.acdc.repository;

import com.example.acdc.domain.Download;
import com.example.acdc.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DownloadRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Download download) {
        em.persist(download);
    }

    public Download findOne(Long id) {
        return em.find(Download.class, id);
    }

    public List<Download> findAllByUser(User user) {
        return em.createQuery("select d from Download as d where d.user.id = ?1 order by d.lastModifyDate DESC", Download.class)
                .setParameter(1, user.getId())
                .getResultList();
    }

}
