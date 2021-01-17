package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
//    @GetMapping("/**")
//    public String redirect()
//    {
//        return "redirect:./index";
//    }

    @GetMapping("/")
    public String startPage()
    {
        return "index";
    }

    @GetMapping("/index")
    public String index()
    {
        return "index";
    }
}
