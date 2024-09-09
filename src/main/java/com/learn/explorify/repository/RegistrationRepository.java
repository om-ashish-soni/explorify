package com.learn.explorify.repository;

import com.learn.explorify.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Integer> {
}
