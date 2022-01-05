package com.example.projekt.data.repository;

import com.example.projekt.data.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IOfferRepository extends CrudRepository<Offer, Integer> {
    List<Offer> findOffersByAuctionId(Integer auctionId);
    List<Offer> findOffersByCreatorId(Integer creatorId);
    //Offer findOfferById(Integer Id);
}



