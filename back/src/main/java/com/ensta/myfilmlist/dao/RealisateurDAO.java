package com.ensta.myfilmlist.dao;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.*;

import java.util.*;

public interface RealisateurDAO {
    List<Realisateur> findAll();
    Optional<Realisateur> findByNomAndPrenom(String nom, String prenom);
    Optional<Realisateur> findById(long id);
    Realisateur update(long id, Realisateur realisateur) throws ServiceException;
    Realisateur save(Realisateur realisateur);
    void delete(long id);
}