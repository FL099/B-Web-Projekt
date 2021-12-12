package com.example.projekt.controller;

import com.example.projekt.model.Offer;
import com.example.projekt.repository.OfferRepository;
import com.example.projekt.exceptions.Exceptionhandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/offers")
public class OfferController {

    private OfferRepository offerRepository;
    private Exceptionhandler exHandler;

    public OfferController(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public @ResponseBody String method2(@PathVariable String name){
        return "<div style=\"font-family: sans-serif; color: darkblue;\"><h1>Hi there " + name + "!</h1><hr/></div>";
    }

    @GetMapping("/{id}") //die ID der Auktion
    public List<Offer> getAssociatedOffers(@PathVariable("id") String aId){
        Integer Id = 11;//Integer.parseInt(aId);
        List<Offer> associatedOffers = Arrays.asList();
        associatedOffers = offerRepository.findOffersByAuct(Id);//findByPriceContaining(aId);
        return associatedOffers;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Offer create(@RequestBody @Valid Offer offer){
        return offerRepository.save(offer);
    }

    @DeleteMapping("/{id}")
    public Offer deleteOffer(@PathVariable("id") Offer offer){
        offerRepository.delete(offer);
        return offer;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return exHandler.handleGeneralExceptions(ex);
    }


}