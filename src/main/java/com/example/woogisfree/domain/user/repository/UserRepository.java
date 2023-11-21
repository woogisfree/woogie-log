package com.example.woogisfree.domain.user.repository;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);
}
