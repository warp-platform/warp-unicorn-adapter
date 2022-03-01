package com.warp.unicorn.auth.dao.repository;

import com.warp.unicorn.auth.dao.model.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
    @Query("from Token")
    Optional<Token> find();
}
