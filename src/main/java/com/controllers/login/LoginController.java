package com.controllers.login;

import com.controllers.SessionService;
import com.model.User;

import com.repository.UserRepository;
import com.utils.json.JsonArray;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController
{
    private final UserRepository user_repository;

    public LoginController(UserRepository user_repository)
    {
        this.user_repository = user_repository;
    }

    @GetMapping("/rest/logout")
    public ResponseEntity<String> logout(HttpSession session)
    {
        SessionService.global.remove(session.getId());
        return new ResponseEntity<String>(JsonResponse.data(null), HttpStatus.OK);
    }

    @GetMapping("/rest/login")
    public ResponseEntity<String> login(HttpSession session, @RequestParam(name="username") String username, @RequestParam(name="password") String password)
    {
        User user = user_repository.findByUsername(username);

        if(user == null || !user.getPassword().equals(password)) {
            return new ResponseEntity<>(
                    JsonResponse.error("Username or password is incorrect"),
                    HttpStatus.OK
            );
        } else {
            SessionService.global.add(session.getId(), user.getId());
            return new ResponseEntity<>(JsonResponse.data(null), HttpStatus.OK);
        }
    }
}
