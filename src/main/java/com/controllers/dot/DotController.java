package com.controllers.dot;


import com.controllers.SessionService;
import com.controllers.Validator;
import com.model.Result;
import com.repository.ResultRepository;
import com.utils.HitChecker;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
public class DotController
{
    private final ResultRepository result_repository;

    public DotController(ResultRepository result_repository)
    {
        this.result_repository = result_repository;
    }

    @PostMapping("/rest/clear")
    public ResponseEntity<String> clear(HttpSession session)
    {
        if (!SessionService.global.has(session.getId())) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        }
        List<Result> results = this.result_repository.getAllByUserId(SessionService.global.get(session.getId()));
        this.result_repository.deleteAll(results);
        return new ResponseEntity<String>(JsonResponse.data(null), HttpStatus.OK);
    }

    @PostMapping("/rest/dot")
    public ResponseEntity<String> dot(HttpSession session, @RequestBody Map<String, String> body)
    {
        long start = System.currentTimeMillis();

        if (!SessionService.global.has(session.getId())
                || body.get("x") == null
                || body.get("y") == null
                || body.get("r") == null) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        } else {
            try
            {
                Validator.validateX(body.get("x"));
                Validator.validateY(body.get("y"));
                Validator.validateR(body.get("r"));
            }
            catch (RuntimeException e)
            {
                return new ResponseEntity<String>(JsonResponse.error(e.getMessage()), HttpStatus.OK);
            }
        }

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        Result result = new Result(Double.parseDouble(body.get("x").replace(',', '.')),
                Double.parseDouble(body.get("y").replace(',', '.')),
                Double.parseDouble(body.get("r").replace(',', '.')),
                HitChecker.isInArea(Double.parseDouble(body.get("x").replace(',', '.')), Double.parseDouble(body.get("y").replace(',', '.')), Double.parseDouble(body.get("r").replace(',', '.'))) ? "true" : "false",
                date,
                0,
                SessionService.global.get(session.getId())
        );

        result.setWorkTime(System.currentTimeMillis() - start);
        result_repository.save(result);
        return new ResponseEntity<String>(JsonResponse.data(result), HttpStatus.OK);
    }

    @PostMapping("/rest/image")
    public ResponseEntity<String> image(HttpSession session, @RequestBody Map<String, String> body)
    {
        long start = System.currentTimeMillis();

        if (!SessionService.global.has(session.getId())
                || body.get("x") == null
                || body.get("y") == null
                || body.get("r") == null) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        } else {
            try
            {
                double x_value = Double.parseDouble(body.get("x").replace(',', '.'));
                double y_value = Double.parseDouble(body.get("y").replace(',', '.'));
                double r_value = Double.parseDouble(body.get("r").replace(',', '.'));


                String pattern = "MM-dd-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());

                Result result = new Result(x_value,
                        y_value,
                        r_value,
                        HitChecker.isInArea(x_value, y_value, r_value) ? "true" : "false",
                        date,
                        0,
                        SessionService.global.get(session.getId())
                );

                result.setWorkTime(System.currentTimeMillis() - start);
                result_repository.save(result);
                return new ResponseEntity<String>(JsonResponse.data(result), HttpStatus.OK);
            }
            catch (NumberFormatException e)
            {
                return new ResponseEntity<String>(JsonResponse.error("bad request"), HttpStatus.OK);
            }
        }
    }
}
