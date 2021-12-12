package com.example.projekt.repository;

import com.example.projekt.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer, Integer> {
    List<Offer> findOffersByAuctionId(Integer auctionId);
    List<Offer> findOffersByCreatorId(Integer creatorId);
    //Offer findOfferById(Integer Id);
}



