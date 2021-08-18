package com.ia.desafioia.repositories.interfaces;

import com.ia.desafioia.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User,String> {
    @Override
    List<User> findAll();
}
