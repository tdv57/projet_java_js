package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Realisateur;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JpaRealisateurDAO implements RealisateurDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Realisateur> findAll(){
        return entityManager.createQuery("SELECT r FROM Realisateur r").getResultList();
    }

    public Realisateur findByNomAndPrenom(String nom, String prenom){
        int realisateur_id =  entityManager.createQuery("SELECT r FROM Realisteur r WHERE nom = :nom AND prenom = :prenom").getFirstResult();
        return entityManager.find(Realisateur.class, realisateur_id);
    }

    public Optional<Realisateur> findById(long id){
        return  Optional.ofNullable(entityManager.find(Realisateur.class, id));
    }

    public Realisateur update(Realisateur realisateur){
        return entityManager.merge(realisateur);
    }
    public Realisateur save(Realisateur realisateur){
        entityManager.persist(realisateur);
        return realisateur;
    }
}