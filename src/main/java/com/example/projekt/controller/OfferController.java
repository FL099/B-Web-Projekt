package com.example.projekt.controller;

import com.example.projekt.dto.OfferDto;
import com.example.projekt.data.model.Offer;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.interfaces.IOfferService;
import com.example.projekt.services.OfferService;
import com.example.projekt.util.State;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/offers")
public class OfferController {

    private final IOfferService offerService;

    public OfferController(OfferService offerService){
        this.offerService = offerService;
    }

    @GetMapping
    public Iterable<OfferDto> getOffers() {
        return offerService.getOffers();
    }

    @GetMapping("/{id}")
    public OfferDto getOffer(@PathVariable Integer id) {
        return offerService.getOffer(id);
    }

    @GetMapping("/byAuction/{id}") //die ID der Auktion
    public List<Offer> getAAssociatedOffers(@PathVariable("id") String aId){
        return offerService.getAAssociatedOffers(aId);    //TODO: testen
    }

    @GetMapping("/byUser/{id}") //die ID des Users
    public List<Offer> getUAssociatedOffers(@PathVariable("id") String uId){
        return offerService.getUAssociatedOffers(uId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public State changeAccepted(@RequestParam("offer") Integer offerId, @RequestParam("state") int state){
        return offerService.changeAccepted(offerId, state);
    }

    @PutMapping("/{id}")
    public OfferDto updateOffer(@RequestBody @Valid OfferDto offerDto, @PathVariable Integer id) {
        return offerService.updateOffer(offerDto, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offer create(@RequestBody @Valid Offer offer){
        return offerService.createOffer(offer);
    }

    @DeleteMapping("/{id}")
    public OfferDto deleteOffer(@PathVariable("id") Integer id){
        return offerService.deleteOffer(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }


}