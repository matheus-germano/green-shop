package com.matheusgermano.greenshop.controller;

import com.matheusgermano.greenshop.models.User;
import com.matheusgermano.greenshop.repositories.UsersRepository;
import com.matheusgermano.greenshop.util.JwtUtil;
import com.matheusgermano.greenshop.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    private UsersRepository usersRepository;
    private PasswordUtil passwordUtil;
    private JwtUtil jwtUtil;

    public UserController(UsersRepository usersRepository, PasswordUtil passwordUtil, JwtUtil jwtUtil) {
        this.usersRepository = usersRepository;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody Map<String, String> signInData) {
        try {
            String encryptedPassword = passwordUtil.encrypt(signInData.get("password"));
            User userExists = usersRepository.findByEmailAndPassword(signInData.get("email"), encryptedPassword);

            if (userExists == null) {
                return new ResponseEntity("E-mail or password invalid", HttpStatus.BAD_REQUEST);
            }

            String generatedToken = jwtUtil.generateToken(userExists);
            HashMap<String, String> token = new HashMap<>();
            token.put("token", generatedToken);

            return ResponseEntity.ok(generatedToken);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) {
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

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<User> findById(@RequestHeader String token) {
        try {
            boolean isTokenValid = jwtUtil.isValid(token);

            if (!isTokenValid) {
                return new ResponseEntity("Invalid token", HttpStatus.FORBIDDEN);
            }

            String userId = jwtUtil.getTokenIdClaim(token);
            User user = usersRepository.findById(UUID.fromString(userId)).orElse(null);

            if (user == null) {
                return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
