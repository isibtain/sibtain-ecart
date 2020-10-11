package com.sibtain.supercms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {

    @RequestMapping("/")
    public String first(){
        return "page";
    }
}
