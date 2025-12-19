package com.ensta.myfilmlist.dao;
import java.util.*;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.*;

public interface GenreDAO {
    List<Genre> findAll() throws ServiceException;
    Optional<Genre> findById(long id);
    Genre update(long id, String name) throws ServiceException;
}