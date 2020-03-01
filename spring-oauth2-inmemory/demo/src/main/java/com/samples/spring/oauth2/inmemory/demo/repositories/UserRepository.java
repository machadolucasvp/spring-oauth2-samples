package com.samples.spring.oauth2.inmemory.demo.repositories;

import java.util.Optional;

import com.samples.spring.oauth2.inmemory.demo.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);

}