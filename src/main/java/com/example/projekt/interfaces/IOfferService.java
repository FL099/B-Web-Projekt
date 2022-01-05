package com.example.projekt.interfaces;

import com.example.projekt.dto.OfferDto;
import com.example.projekt.data.model.Offer;
import com.example.projekt.util.State;

import java.util.List;

public interface IOfferService {

    Iterable<OfferDto> getOffers();
    OfferDto getOffer(int id);
    List<Offer> getAAssociatedOffers(String aId);       //by Auction
    List<Offer> getUAssociatedOffers(String uId);       //by User
    State changeAccepted(int offerId, int state);
    OfferDto updateOffer(OfferDto offerDto, int id);
    Offer createOffer(Offer offer);
    OfferDto deleteOffer(int id);

}
