package com.example.projekt.interfaces;

import com.example.projekt.dto.AuctionDto;
import com.example.projekt.dto.OfferDto;
import com.example.projekt.data.model.Auction;

public interface IAuctionService {
    Iterable<AuctionDto> getAuctions();
    AuctionDto getAuction(int id);
    Auction createAuction(Auction auction);
    OfferDto addOfferForAuction(int id, OfferDto offerDto);
    AuctionDto updateAuction(AuctionDto auctionDto, Integer id);
    AuctionDto deleteAuction(int id);
    Auction getAuctionFromRepoOrThrow(int id);
}
