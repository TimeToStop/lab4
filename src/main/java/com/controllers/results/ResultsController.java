package com.controllers.results;


import com.controllers.SessionService;
import com.model.Result;
import com.model.User;

import com.repository.ResultRepository;
import com.repository.UserRepository;
import com.utils.json.JsonArray;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ResultsController
{
    private final ResultRepository result_repository;

    public ResultsController(ResultRepository result_repository,
                             UserRepository user_repository)
    {
        this.result_repository = result_repository;
    }

    @GetMapping("/rest/results")
    public ResponseEntity<String> results(HttpSession session)
    {
        if (!SessionService.global.has(session.getId())) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        }

        List<Result> results = this.result_repository.getAllByUserId(SessionService.global.get(session.getId()));

        System.out.println(JsonResponse.data(JsonArray.from(results)));

        return new ResponseEntity<>(
                JsonResponse.data(JsonArray.from(results)),
                HttpStatus.OK
        );
    }
}
