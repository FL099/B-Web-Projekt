package com.example.projekt.controller;

import com.example.projekt.model.Auction;
import com.example.projekt.model.Offer;
import com.example.projekt.repository.AuctionRepository;
import com.example.projekt.repository.OfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionRepository auctionRepository;

    public AuctionController(OfferRepository offerRepository){
        this.auctionRepository = auctionRepository;
    }

    @GetMapping(produces = "raw/json")
    public @ResponseBody Auction index(){
        return new Auction("product", 2);
        //return "{\"startTime\":\"dd/mm/yyyy\",\"endTime\":\"dd/mm/yyyy\",\"product\":\"Product name\",\"amount\": 5}";
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Auction create(@RequestBody @Valid Auction auction){
        return auctionRepository.save(auction);
    }

    @DeleteMapping("/{id}")
    public Auction deleteAuction(@PathVariable("id") Auction auction){
        auctionRepository.delete(auction);
        return auction;
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
