package com.ensta.myfilmlist.dao;
import com.ensta.myfilmlist.model.*;

import java.util.*;

public interface RealisateurDAO {
    public List<Realisateur> findAll();
    public Realisateur findByNomAndPrenom(String nom, String prenom);
    public Optional<Realisateur> findById(long id);
    public Realisateur update(Realisateur realisateur);
    public Realisateur save(Realisateur realisateur);
}