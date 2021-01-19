package com.controllers.dot;


import com.controllers.SessionService;
import com.model.Result;
import com.model.User;
import com.repository.ResultRepository;
import com.repository.UserRepository;
import com.utils.HitChecker;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class DotController
{
    private final ResultRepository result_repository;

    public DotController(ResultRepository result_repository)
    {
        this.result_repository = result_repository;
    }

    @GetMapping("/rest/clear")
    public ResponseEntity<String> clear(HttpSession session)
    {
        if (!SessionService.global.has(session.getId())) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        }

        List<Result> results = this.result_repository.getAllByUserId(SessionService.global.get(session.getId()));
        this.result_repository.deleteAll(results);
        return new ResponseEntity<String>(JsonResponse.data(null), HttpStatus.OK);
    }

    @GetMapping("/rest/dot")
    public ResponseEntity<String> dot(HttpSession session, @RequestParam("x") double x, @RequestParam("y") double y, @RequestParam("r") double r)
    {
        if (!SessionService.global.has(session.getId())) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        }

        long start = System.currentTimeMillis();

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        Result result = new Result(
                x,
                y,
                r,
                HitChecker.isInArea(x, y, r) ? "true" : "false",
                date,
                0,
                SessionService.global.get(session.getId())
        );

        result.setWorkTime(System.currentTimeMillis() - start);
        result_repository.save(result);
        return new ResponseEntity<String>(JsonResponse.data(result), HttpStatus.OK);
    }
}
