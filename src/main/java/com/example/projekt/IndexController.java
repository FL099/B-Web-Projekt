package com.example.projekt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    public @ResponseBody String index(){
        return "<div style=\"font-family: sans-serif; color: darkblue;\"><h1>Hi there!</h1><hr/></div>";
    }

}