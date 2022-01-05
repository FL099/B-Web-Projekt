package com.example.projekt.controller;

import com.example.projekt.dto.AuctionDto;
import com.example.projekt.dto.OfferDto;
import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.interfaces.IAuctionService;
import com.example.projekt.model.Auction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final IAuctionService auctionService;

    public AuctionController(IAuctionService auctionService){
        this.auctionService = auctionService;
    }

    @GetMapping
    public Iterable<AuctionDto> getAuctions() {
        return auctionService.getAuctions();
    }

    @GetMapping("/{id}")
    public AuctionDto getAuction(@PathVariable Integer id) {
        return auctionService.getAuction(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Auction create(@RequestBody @Valid Auction auction){
        return auctionService.createAuction(auction);
    }

    @PostMapping("/{id}/offers")
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDto addOfferForAuction(@PathVariable Integer id, @RequestBody @Valid OfferDto offerDto) {
        return auctionService.addOfferForAuction(id, offerDto);
    }

    @PutMapping("/{id}")
    public AuctionDto updateAuction(@RequestBody @Valid AuctionDto auctionDto, @PathVariable Integer id) {
        return auctionService.updateAuction(auctionDto, id);
    }

    @DeleteMapping("/{id}")
    public AuctionDto deleteAuction(@PathVariable Integer id) {
        return auctionService.deleteAuction(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }
}
