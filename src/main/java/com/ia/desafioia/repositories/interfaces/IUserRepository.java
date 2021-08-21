package com.ia.desafioia.repositories.interfaces;

import com.ia.desafioia.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User,String>{
    List<User> findAll();
    List<User> findAllByLogin(String login);
    List<User> findAllByEmail(String email);
}