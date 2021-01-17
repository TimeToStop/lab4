package com.controllers.registration;

import com.model.User;

import com.repository.UserRepository;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController
{
    private final UserRepository user_repository;

    public RegistrationController(UserRepository user_repository)
    {
        this.user_repository = user_repository;
    }

    @GetMapping("/rest/registration")
    public ResponseEntity<String> registration(@RequestParam(name="username") String username, @RequestParam(name="password") String password)
    {
        User user = user_repository.findByUsername(username);

        if (user != null) {
            return new ResponseEntity<String>(
                    JsonResponse.error("User already exists"),
                    HttpStatus.OK
            );
        } else {
            user_repository.save(new User(username, password));

            return new ResponseEntity<String>(
                    JsonResponse.data(null),
                    HttpStatus.OK
            );
        }
    }
}
