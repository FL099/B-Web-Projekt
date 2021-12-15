package com.example.projekt.controller;

import com.example.projekt.dto.OfferDto;
import com.example.projekt.model.Offer;
import com.example.projekt.repository.OfferRepository;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.util.State;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/offers")
public class OfferController {

    private final ModelMapper modelMapper = new ModelMapper();
    private OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public Iterable<OfferDto> getOffers() {
        return modelMapper.map(offerRepository.findAll(),
                new TypeToken<List<OfferDto>>(){}.getType());
    }

    @GetMapping("/{id}")
    public OfferDto getOffer(@PathVariable Integer id) {
        return modelMapper.map(getOfferFromRepoOrThrow(id), OfferDto.class);
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
        offer.setState(State.values()[state]);
        offerRepository.save(offer);
        //TODO: eleganter als mit Params lösen
        return State.values()[state];
    }

    @PutMapping("/{id}")
    public OfferDto updateOffer(@RequestBody @Valid OfferDto offerDto, @PathVariable Integer id) {
        Offer existingOffer = getOfferFromRepoOrThrow(id);

        Offer offer = modelMapper.map(offerDto, Offer.class);

        // check ranges (amount, price, ...)
        //existingOffer.getAuctionId().throwIfOutsideRanges(offer);

        // the following properties must not be updated
        offer.setId(existingOffer.getId());
        offer.setCreatorId(existingOffer.getCreatorId());
        offer.setAuctionId(existingOffer.getAuctionId());
        offerRepository.save(offer);

        return modelMapper.map(offer, OfferDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offer create(@RequestBody @Valid Offer offer){
        return offerRepository.save(offer);
    }

    @DeleteMapping("/{id}")
    public OfferDto deleteOffer(@PathVariable("id") Integer id){
        OfferDto existingOfferDto = getOffer(id);
        offerRepository.deleteById(id);
        return existingOfferDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }

    private Offer getOfferFromRepoOrThrow(Integer id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}