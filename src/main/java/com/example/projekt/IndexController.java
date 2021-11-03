package com.example.projekt;

import com.example.projekt.model.Offer;
import com.example.projekt.repository.OfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class IndexController {

    private OfferRepository offerRepository;

    public IndexController(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @GetMapping("/")
    public @ResponseBody String index(){
        return "<div style=\"font-family: sans-serif; color: darkblue;\"><h1>Hi there! </h1><hr/></div>";
    }

    @GetMapping("/{name}")
    public @ResponseBody String method2(@PathVariable String name){
        return "<div style=\"font-family: sans-serif; color: darkblue;\"><h1>Hi there " + name + "!</h1><hr/></div>";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offer create(@RequestBody @Valid Offer offer){
        return offerRepository.save(offer);
    }
}