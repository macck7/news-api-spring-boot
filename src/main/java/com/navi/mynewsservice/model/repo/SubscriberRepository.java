package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber findByEmail(String email);
}

