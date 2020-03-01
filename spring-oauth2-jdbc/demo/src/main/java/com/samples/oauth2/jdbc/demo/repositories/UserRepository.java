package com.samples.oauth2.jdbc.demo.repositories;

import java.util.Optional;

import com.samples.oauth2.jdbc.demo.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);

}