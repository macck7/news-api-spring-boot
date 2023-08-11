package com.navi.mynewsservice.dao.repo;

import com.navi.mynewsservice.dao.schema.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Integer> {
    UserDetails findByEmail(String email);
}
