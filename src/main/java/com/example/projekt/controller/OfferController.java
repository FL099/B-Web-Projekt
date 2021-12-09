package com.example.projekt.controller;

import com.example.projekt.model.Offer;
import com.example.projekt.repository.OfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @GetMapping("/{name}")
    public @ResponseBody String method2(@PathVariable String name){
        return "<div style=\"font-family: sans-serif; color: darkblue;\"><h1>Hi there " + name + "!</h1><hr/></div>";
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
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}