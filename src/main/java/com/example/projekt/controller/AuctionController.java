package com.example.projekt.controller;

import com.example.projekt.dto.AuctionDto;
import com.example.projekt.dto.OfferDto;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.model.Auction;
import com.example.projekt.model.Offer;
import com.example.projekt.repository.AuctionRepository;
import com.example.projekt.repository.OfferRepository;
import com.example.projekt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final ModelMapper modelMapper = new ModelMapper();

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    public AuctionController(AuctionRepository auctionRepository, UserRepository userRepository, OfferRepository offerRepository){
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public Iterable<AuctionDto> getAuctions() {
        return modelMapper.map(auctionRepository.findAll(),
                new TypeToken<List<AuctionDto>>(){}.getType());
    }

    @GetMapping("/{id}")
    public AuctionDto getAuction(@PathVariable Integer id) {
        return modelMapper.map(getAuctionFromRepoOrThrow(id), AuctionDto.class);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Auction create(@RequestBody @Valid Auction auction){
        return auctionRepository.save(auction);
    }

    @PostMapping("/{id}/offers")
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDto addOfferForAuction(@PathVariable Integer id, @RequestBody @Valid OfferDto offerDto) {
        Auction existingAuction = getAuctionFromRepoOrThrow(id);

        Offer offer = modelMapper.map(offerDto, Offer.class);

        // check ranges (amount, price, ...)
        //existingAuction.throwIfOutsideRanges(offer);

        //TODO: ändern (wenn creatorId auf User geändert wurde
        offer.setCreatorId(0); //userRepository.findByEmailContaining("erika@musterfrau.at").get(0)); // TODO: get user via authentication
        offer.setAuctionId(0);//existingAuction);
        offerRepository.save(offer);

        return modelMapper.map(offer, OfferDto.class);
    }

    @PutMapping("/{id}")
    public AuctionDto updateAuction(@RequestBody @Valid AuctionDto auctionDto, @PathVariable Integer id) {
        Auction existingAuction = getAuctionFromRepoOrThrow(id);

        Auction auction = modelMapper.map(auctionDto, Auction.class);

        auction.setId(existingAuction.getId());
        auction.setCreatorId(existingAuction.getCreatorId());
        auction.setProduct(existingAuction.getProduct());

        return modelMapper.map(auctionRepository.save(auction), AuctionDto.class);
    }

    @DeleteMapping("/{id}")
    public AuctionDto deleteAuction(@PathVariable Integer id) {
        AuctionDto existingAuctionDto = getAuction(id);
        auctionRepository.deleteById(id);
        return existingAuctionDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }

    private Auction getAuctionFromRepoOrThrow(Integer id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
