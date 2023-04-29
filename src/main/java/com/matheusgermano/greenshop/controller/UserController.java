package com.matheusgermano.greenshop.controller;

import com.matheusgermano.greenshop.models.User;
import com.matheusgermano.greenshop.repositories.UsersRepository;
import com.matheusgermano.greenshop.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    private UsersRepository usersRepository;
    private PasswordUtil passwordUtil;

    public UserController(UsersRepository usersRepository, PasswordUtil passwordUtil) {
        this.usersRepository = usersRepository;
        this.passwordUtil = passwordUtil;
    }

    @PostMapping
    public ResponseEntity<User> store(@RequestBody User user) throws NoSuchAlgorithmException {
        try {
            User inCountryIdAlreadyExists = usersRepository.findByInCountryId(user.getInCountryId());
            User emailAlreadyExists = usersRepository.findByEmail(user.getEmail());

            if (inCountryIdAlreadyExists != null) {
                return new ResponseEntity("This country identification is already registered", HttpStatus.BAD_REQUEST);
            }

            if (emailAlreadyExists != null) {
                return new ResponseEntity("This email is already in use", HttpStatus.BAD_REQUEST);
            }

            user.setPassword(passwordUtil.encrypt(user.getPassword()));
            User createdUser = usersRepository.save(user);

            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return (ResponseEntity<User>) ResponseEntity.internalServerError();
        }
    }
}
