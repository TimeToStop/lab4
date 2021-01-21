package com.controllers.registration;

import com.model.User;

import com.repository.UserRepository;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RegistrationController
{
    private final UserRepository user_repository;

    public RegistrationController(UserRepository user_repository)
    {
        this.user_repository = user_repository;
    }

    @PostMapping("/rest/registration")
    public ResponseEntity<String> registration(@RequestBody Map<String, String> body)
    {
        if (body.get("username") == null || body.get("password") == null) {
            return new ResponseEntity<String>(
                    JsonResponse.error("Username or password is not set"),
                    HttpStatus.OK
            );
        }

        User user = user_repository.findByUsername(body.get("username"));

        if (user != null) {
            return new ResponseEntity<String>(
                    JsonResponse.error("User already exists"),
                    HttpStatus.OK
            );
        } else {
            user_repository.save(new User(body.get("username"), body.get("password")));

            return new ResponseEntity<String>(
                    JsonResponse.data(null),
                    HttpStatus.OK
            );
        }
    }
}
