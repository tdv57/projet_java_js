package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.TokenDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Token;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

public class JpaTokenDAO implements TokenDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Token> findById(long id) {
        return Optional.empty();
    }

    /**
     * Updates the Token corresponding to the id argument with the confirmed date
     *
     * @param id          the id of the token to update
     * @param confirmedAt the date of the confirmation
     * @return the corresponding token updated
     */
    @Override
    public Token updateConfirmedAt(long id, LocalDateTime confirmedAt) throws ServiceException {
        Optional<Token> prev_token = this.findById(id);
        if (prev_token.isEmpty()) {
            throw new ServiceException("Can't update Token.");
        }
        Token token_to_modify = entityManager.merge(prev_token.get());
        token_to_modify.setConfirmedAt(confirmedAt);
        entityManager.merge(token_to_modify);
        return token_to_modify;
    }
}
