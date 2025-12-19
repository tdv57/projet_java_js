package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Film;
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

    /**
     * Returns the list of all Realisateurs present in the database.
     * A ServicException is thrown in case of an error (can't get Realisateurs, list empty)
     *
     * @return      the list of Realisateurs
     */
    @Override
    public List<Realisateur> findAll(){
        return entityManager.createQuery("SELECT r FROM Realisateur r").getResultList();
    }

    /**
     * Returns the Realisateur corresponding to the name and surname arguments (or an empty option if there is none)
     *
     * @param  nom      the name of the realisateur to return
     * @param  prenom   the surname of the realisateur to return
     * @return          the corresponding realisateur
     */
    @Override
    public Optional<Realisateur> findByNomAndPrenom(String nom, String prenom){
        int realisateur_id =  entityManager.createQuery("SELECT r FROM Realisteur r WHERE nom = :nom AND prenom = :prenom").getFirstResult();
        return Optional.ofNullable(entityManager.find(Realisateur.class, realisateur_id));
    }

    /**
     * Returns the Realisateur corresponding to the id argument (or an empty option if there is none)
     *
     * @param  id   the id of the realisateur to return
     * @return      the corresponding realisateur
     */
    @Override
    public Optional<Realisateur> findById(long id){
        return  Optional.ofNullable(entityManager.find(Realisateur.class, id));
    }

    /**
     * Updates the Realisateur corresponding to the id argument with the new realisateur
     *
     * @param  id           the id of the realisateur to update
     * @param  realisateur  the state of the realisateur updated
     * @return              the corresponding realisateur
     */
    @Override
    public Realisateur update(long id, Realisateur realisateur) throws ServiceException {
        Optional<Realisateur> prev_realisateur = this.findById(id);
        if  (prev_realisateur.isEmpty()) {
            throw new ServiceException("Impossible de mettre à jour le réalisateur");
        }
        Realisateur realisateur_to_modify = entityManager.merge(prev_realisateur.get());
        realisateur_to_modify.setNom(realisateur.getNom());
        realisateur_to_modify.setPrenom(realisateur.getPrenom());
        realisateur_to_modify.setDateNaissance(realisateur.getDateNaissance());
        entityManager.merge(realisateur_to_modify);
        return realisateur_to_modify;
    }

    /**
     * Creates a Realisateur in the database based on a realisateur argument
     *
     * @param  realisateur  the realisateur to register
     * @return              the realisateur created
     */
    @Override
    public Realisateur save(Realisateur realisateur){
        entityManager.persist(realisateur);
        return realisateur;
    }

    /**
     * Deletes a Realisateur in the database based on the id argument
     *
     * @param  id   the id of the Realisateur to delete
     */
    @Override
    public void delete(long id) {
        Realisateur managedRealisateur = entityManager.find(Realisateur.class, id);
        if (managedRealisateur != null) {
            List<Film> films = entityManager
                    .createQuery("SELECT f FROM Film f WHERE realisateur.id = :realisateur_id")
                    .setParameter("realisateur_id", id)
                    .getResultList();
            films.forEach(film -> entityManager.remove(film));
            entityManager.remove(managedRealisateur);
        }
    }
}