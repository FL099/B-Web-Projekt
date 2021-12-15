package com.example.projekt.controller;

import com.example.projekt.model.Offer;
import com.example.projekt.repository.OfferRepository;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.util.State;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/offers")
public class OfferController {

    private OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public @ResponseBody String method2(){
        return "<div style=\"font-family: sans-serif; color: darkblue;\"><h1>Hi there!</h1><hr/></div>";
    }

    @GetMapping("/byAuction/{id}") //die ID der Auktion
    public List<Offer> getAAssociatedOffers(@PathVariable("id") String aId){
        Integer Id = Integer.parseInt(aId);
        List<Offer> associatedOffers ;//= Arrays.asList();
        associatedOffers = offerRepository.findOffersByAuctionId(Id);//findByPriceContaining(aId);
        if (associatedOffers != null){
            return associatedOffers;
        }
        return null;    //TODO: testen
    }

    @GetMapping("/byUser/{id}") //die ID des Users
    public List<Offer> getUAssociatedOffers(@PathVariable("id") String uId){
        Integer Id = Integer.parseInt(uId);
        List<Offer> associatedOffers ;//= Arrays.asList();
        associatedOffers = offerRepository.findOffersByCreatorId(Id);
        if (associatedOffers != null){
            return associatedOffers;
        }
        return null;    //TODO: testen
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public State changeAccepted(@RequestParam("offer") Integer offerId, @RequestParam("state") int state){
        Offer offer = offerRepository.findById(offerId).orElse(new Offer());
        offer.setAccepted(State.values()[state]);
        offerRepository.save(offer);
        //TODO: eleganter als mit Params lösen
        return State.values()[state];
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
        return Exceptionhandler.handleGeneralExceptions(ex);
    }


}