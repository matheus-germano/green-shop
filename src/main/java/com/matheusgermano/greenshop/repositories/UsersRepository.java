package com.matheusgermano.greenshop.repositories;

import com.matheusgermano.greenshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<User, UUID> {
    User findByInCountryId(String inCountryId);
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
