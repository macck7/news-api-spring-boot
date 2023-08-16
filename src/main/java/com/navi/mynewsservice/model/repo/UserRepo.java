package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Integer> {
    UserDetails findByEmail(String email);
}
