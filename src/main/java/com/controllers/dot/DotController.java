package com.controllers.dot;


import com.controllers.SessionService;
import com.controllers.Validator;
import com.model.Result;
import com.repository.ResultRepository;
import com.utils.HitChecker;
import com.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> dot(HttpSession session, @RequestParam("x") String x, @RequestParam("y") String y, @RequestParam("r") String r)
    {
        long start = System.currentTimeMillis();

        if (!SessionService.global.has(session.getId())) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        } else {
            try
            {
                Validator.validateX(x);
                Validator.validateY(y);
                Validator.validateR(r);
            }
            catch (RuntimeException e)
            {
                return new ResponseEntity<String>(JsonResponse.error(e.getMessage()), HttpStatus.OK);
            }
        }

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        Result result = new Result(Double.parseDouble(x.replace(',', '.')),
                Double.parseDouble(y.replace(',', '.')),
                Double.parseDouble(r.replace(',', '.')),
                HitChecker.isInArea(Double.parseDouble(x.replace(',', '.')), Double.parseDouble(y.replace(',', '.')), Double.parseDouble(r.replace(',', '.'))) ? "true" : "false",
                date,
                0,
                SessionService.global.get(session.getId())
        );

        result.setWorkTime(System.currentTimeMillis() - start);
        result_repository.save(result);
        return new ResponseEntity<String>(JsonResponse.data(result), HttpStatus.OK);
    }

    @GetMapping("/rest/image")
    public ResponseEntity<String> image(HttpSession session, @RequestParam("x") String x, @RequestParam("y") String y, @RequestParam("r") String r)
    {
        long start = System.currentTimeMillis();

        if (!SessionService.global.has(session.getId())) {
            return new ResponseEntity<String>(JsonResponse.error("you are not logged in"), HttpStatus.OK);
        } else {
            try
            {
                double x_value = Double.parseDouble(x.replace(',', '.'));
                double y_value = Double.parseDouble(y.replace(',', '.'));
                double r_value = Double.parseDouble(r.replace(',', '.'));


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
